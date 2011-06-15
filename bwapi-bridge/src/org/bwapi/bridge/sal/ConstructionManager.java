package org.bwapi.bridge.sal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Race;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

/**
 * Construction manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/ConstructionManager">ConstructionManager</a>
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public class ConstructionManager extends ArbitratedManager {
    
    protected final BuildingPlacer placer;
    protected final Map<Unit, Building> builders = new HashMap<Unit, Building>();
    protected final List<Building> incompleteBuildings = new ArrayList<Building>();
    protected final Map<UnitType, Set<Building>> buildingsNeedingBuilders = 
        new HashMap<UnitType, Set<Building>>();
    protected final Map<UnitType, Integer> plannedCount;
    protected final Map<UnitType, Integer> startedCount;
    
    public ConstructionManager(Arbitrator<Unit, Double> arbitrator, BuildingPlacer placer) {
        super(arbitrator);
        this.placer = placer;
        Set<UnitType> allUnitTypes = UnitType.allUnitTypes();
        plannedCount = new HashMap<UnitType, Integer>(allUnitTypes.size());
        startedCount = new HashMap<UnitType, Integer>(allUnitTypes.size());
        for (UnitType type : allUnitTypes) {
            plannedCount.put(type, 0);
            startedCount.put(type, 0);
        }
    }
    
    @Override
    public void onOffer(Set<Unit> objects) {
        Set<Unit> units = new HashSet<Unit>(objects);
        for (Set<Building> buildings : buildingsNeedingBuilders.values()) {
            for (Building building : buildings) {
                double minDist = 1000000;
                Unit builder = null;
                for (Unit unit : units) {
                    double dist = unit.getPosition().getDistance(building.position);
                    if (dist < minDist) {
                        minDist = dist;
                        builder = unit;
                    }
                }
                if (builder != null) {
                    arbitrator.accept(this, builder);
                    arbitrator.setBid(this, builder, 100D);
                    builders.put(builder, building);
                    building.builderUnit = builder;
                    if (building.type.isAddon()) {
                        building.tilePosition = builder.getTilePosition();
                        if (builder.isLifted()) {
                            if (!placer.canBuildHereWithSpace(building.tilePosition, building.type)) {
                                building.tilePosition = placer.getBuildLocationNear(building.tilePosition, 
                                        building.type.whatBuilds().getKey());
                            }
                        } else {
                            boolean buildable = true;
                            for (int x = building.tilePosition.x() + 4; x < building.tilePosition.x() + 6; x++) {
                                for (int y = building.tilePosition.y() + 1; y < building.tilePosition.y() + 3; y++) {
                                    if (!placer.buildable(x, y)) {
                                        buildable = false;
                                    }
                                }
                            }
                            if (!buildable) {
                                placer.freeTiles(building.tilePosition, 4, 3);
                                building.tilePosition = placer.getBuildLocationNear(building.tilePosition, 
                                        building.type.whatBuilds().getKey());
                            }
                        }
                        placer.reserveTiles(building.tilePosition, 4, 3);
                        placer.reserveTiles(building.tilePosition.opAdd(new TilePosition(4, 1)), 2, 2);
                    }
                    units.remove(builder);
                    buildings.remove(building);
                }
            }
        }
        for (Unit unit : units) {
            arbitrator.decline(this, unit, 0D);
            arbitrator.removeBid(this, unit);
        }
    }
    
    @Override
    public void onRevoke(Unit unit, Double bid) {
        onRemoveUnit(unit);
    }
    
    @Override
    public void update() {
        Set<Unit> myPlayerUnits = Game.getInstance().self().getUnits();
        for (Entry<UnitType, Set<Building>> buildings : buildingsNeedingBuilders.entrySet()) {
            if (!buildings.getValue().isEmpty()) {
                for (Unit unit : myPlayerUnits) {
                    if (unit.isCompleted() && unit.getType().equals(buildings.getKey()) && 
                            unit.getAddon() == null && !builders.containsKey(unit)) {
                        if (!unit.getType().isWorker()) {
                            arbitrator.setBid(this, unit, 80D);
                        } else {
                            double minDist = 1000000;
                            for (Building building : buildings.getValue()) {
                                double dist = unit.getPosition().getDistance(building.position);
                                if (dist < minDist) {
                                    minDist = dist;
                                }
                            }
                            minDist = Math.max(minDist, 10);
                            minDist = Math.min(minDist, 256 * 32 + 10);
                            arbitrator.setBid(this, unit, 80 - (minDist - 10) / (256 * 32) * 60);
                        }
                    }
                }
            }
        }
        int index = 0;
        Iterator<Building> iterator = incompleteBuildings.iterator();
        while (iterator.hasNext()) {
            Building building = iterator.next();
            index++;
            if (!building.started && building.buildingUnit != null) {
                incrementMapValue(startedCount, building.type);
                building.started = true;
            }
            if (building.type.isAddon()) {
                if (building.builderUnit != null) {
                    building.buildingUnit = building.builderUnit.getAddon();
                }
                if (building.buildingUnit != null && building.buildingUnit.isCompleted()) {
                    decrementMapValue(startedCount, building.type);
                    decrementMapValue(plannedCount, building.type);
                    if (building.builderUnit != null) {
                        builders.remove(building.builderUnit);
                        arbitrator.removeBid(this, building.builderUnit);
                    }
                    placer.freeTiles(building.tilePosition, 4, 3);
                    placer.freeTiles(building.tilePosition.opAdd(new TilePosition(4, 1)), 2, 2);
                    //if the building is complete, we can forget about it
                    iterator.remove();
                } else if (Game.getInstance().canMake(null, building.type)) {
                    if (building.builderUnit == null) {
                        Set<Building> buildings = buildingsNeedingBuilders.get(
                                building.type.whatBuilds().getKey());
                        if (buildings == null) {
                            buildings = new HashSet<Building>();
                            buildingsNeedingBuilders.put(building.type.
                                    whatBuilds().getKey(), buildings);
                        }
                    } else if (building.builderUnit.getAddon() == null) {
                        if (building.builderUnit.isLifted() && Game.getInstance().getFrameCount() > building.lastOrderFrame +
                            Game.getInstance().getLatency().getBwapiOrdinal() * 2) {
                            if (!placer.canBuildHereWithSpace(building.tilePosition, building.type)) {
                                placer.freeTiles(building.tilePosition, 4, 3);
                                placer.freeTiles(building.tilePosition.opAdd(new TilePosition(4, 1)), 2, 2);
                                building.tilePosition = placer.getBuildLocationNear(
                                        building.tilePosition, building.type.whatBuilds().getKey());
                                placer.reserveTiles(building.tilePosition, 4, 3);
                                placer.reserveTiles(building.tilePosition.opAdd(new TilePosition(4, 1)), 2, 2);
                            }
                            building.builderUnit.land(building.tilePosition);
                            building.lastOrderFrame = Game.getInstance().getFrameCount();
                        } else if (building.builderUnit.isTraining()) {
                            building.builderUnit.cancelTrain();
                        } else if (!building.builderUnit.getTilePosition().equals(building.tilePosition)) {
                            building.builderUnit.lift();
                            building.lastOrderFrame = Game.getInstance().getFrameCount();
                        } else {
                            boolean buildable = true;
                            for (int x = building.tilePosition.x() + 4; x < building.tilePosition.x() + 6; x++) {
                                for (int y = building.tilePosition.y() + 1; y < building.tilePosition.y() + 3; y++) {
                                    if (!placer.buildable(x, y) || Game.getInstance().hasCreep(x, y)) {
                                        buildable = false;
                                    }
                                }
                            }
                            if (buildable) {
                                building.builderUnit.buildAddon(building.type);
                            } else {
                                building.builderUnit.lift();
                                building.lastOrderFrame = Game.getInstance().getFrameCount();
                            }
                        }
                    }
                }
            } else {
                if (building.tilePosition == null || building.tilePosition.equals(TilePosition.NONE)) {
                    if ((Game.getInstance().getFrameCount() + index) % 25 == 0 && Game.getInstance().canMake(null, building.type)) {
                        building.tilePosition = placer.getBuildLocationNear(building.goalPosition, building.type);
                        if (building.tilePosition != null && !building.tilePosition.equals(TilePosition.NONE)) {
                            building.position = new Position(building.tilePosition.x() * 32 + building.type.tileWidth() * 16,
                                    building.tilePosition.y() * 32 + building.type.tileHeight() * 16);
                            placer.reserveTiles(building.tilePosition, building.type.tileWidth(), building.type.tileHeight());
                        }
                    }
                    if (building.tilePosition == null || building.tilePosition.equals(TilePosition.NONE)) {
                        continue;
                    }
                }
                if (building.builderUnit != null && !building.builderUnit.exists()) {
                    building.builderUnit = null;
                }
                if (building.buildingUnit != null && (!building.buildingUnit.exists() || 
                        !building.buildingUnit.getType().equals(building.type))) {
                    building.buildingUnit = null;
                }
                if (building.buildingUnit == null) {
                    for (Unit unitOnTile : Game.getInstance().unitsOnTile(building.tilePosition.x(), building.tilePosition.y())) {
                        if (unitOnTile.getType().equals(building.type) && !unitOnTile.isLifted()) {
                            building.buildingUnit = unitOnTile;
                            break;
                        }
                    }
                    if (building.buildingUnit == null && building.builderUnit != null && building.builderUnit.getType().isBuilding()) {
                        building.buildingUnit = building.builderUnit;
                    }
                }
                if (building.buildingUnit != null && building.buildingUnit.isCompleted()) {
                    decrementMapValue(startedCount, building.type);
                    decrementMapValue(plannedCount, building.type);
                    if (building.builderUnit != null) {
                        builders.remove(building.builderUnit);
                        arbitrator.removeBid(this, building.builderUnit);
                    }
                    placer.freeTiles(building.tilePosition, building.type.tileWidth(), building.type.tileHeight());
                    iterator.remove();
                } else {
                    if (building.buildingUnit == null && Game.getInstance().canMake(null, building.type)) {
                        if (building.builderUnit == null) {
                            Set<Building> buildings = buildingsNeedingBuilders.get(
                                    building.type.whatBuilds().getKey());
                            if (buildings == null) {
                                buildings = new HashSet<Building>();
                                buildingsNeedingBuilders.put(building.type.whatBuilds().
                                        getKey(), buildings);
                            }
                            buildings.add(building);
                        } else if (!building.builderUnit.isConstructing()) {
                            double dist = building.builderUnit.getPosition().getDistance(building.position);
                            if (dist > 100) {
                                building.builderUnit.rightClick(building.position);
                            } else if (Game.getInstance().canBuildHere(building.builderUnit, building.tilePosition, building.type)) {
                                if (Game.getInstance().canMake(building.builderUnit, building.type)) {
                                    building.builderUnit.build(building.tilePosition, building.type);
                                }
                            } else {
                                placer.freeTiles(building.tilePosition, building.type.tileWidth(), building.type.tileHeight());
                                building.tilePosition = TilePosition.NONE;
                                building.position = Position.NONE;
                            }
                        }
                    } else if (!building.type.getRace().equals(Race.TERRAN)) {
                        if (building.builderUnit != null) {
                            builders.remove(building.builderUnit);
                            arbitrator.removeBid(this, building.builderUnit);
                            building.builderUnit = null;
                        }
                    } else if (building.builderUnit == null) {
                        Set<Building> buildings = buildingsNeedingBuilders.get(building.type.
                                whatBuilds().getKey());
                        if (buildings == null) {
                            buildings = new HashSet<Building>();
                            buildingsNeedingBuilders.put(building.type.whatBuilds().
                                    getKey(), buildings);
                        }
                        buildings.add(building);
                    } else if (Game.getInstance().getFrameCount() % (4 * Game.getInstance().getLatency().getBwapiOrdinal()) == 0 && 
                            !building.builderUnit.isConstructing() || !building.buildingUnit.isBeingConstructed()) {
                        building.builderUnit.rightClick(building.buildingUnit);
                        building.buildingUnit.rightClick(building.builderUnit);
                    }
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return "Construction Manager";
    }
    
    public void onRemoveUnit(Unit unit) {
        Building building = builders.get(unit);
        if (building != null) {
            building.builderUnit = null;
            builders.remove(unit);
        }
    }
    
    public boolean build(UnitType type, TilePosition position) {
        if (!type.isBuilding()) {
            return false;
        }
        Building building = new Building();
        building.type = type;
        building.goalPosition = position;
        building.tilePosition = TilePosition.NONE;
        building.position = Position.NONE;
        building.lastOrderFrame = 0;
        building.started = false;
        incrementMapValue(plannedCount, type);
        incompleteBuildings.add(building);
        return true;
    }
    
    public int getPlannedCount(UnitType type) {
        Integer count = plannedCount.get(type);
        return count == null ? 0 : count;
    }
    
    public int getStartedCount(UnitType type) {
        Integer count = startedCount.get(type);
        return count == null ? 0 : count;
    }
    
    /**
     * Building
     * 
     * @author Chad Retz
     *
     * @since 0.3
     */
    protected static final class Building {
        protected TilePosition goalPosition;
        protected TilePosition tilePosition;
        protected Position position;
        protected UnitType type;
        protected Unit buildingUnit;
        protected Unit builderUnit;
        protected int lastOrderFrame;
        protected boolean started;
        
        protected Building() {
        }
    }
}
