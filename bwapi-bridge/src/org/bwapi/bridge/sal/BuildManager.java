package org.bwapi.bridge.sal;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Race;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;


/**
 * Build manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/BuildManager">BuildManager</a>
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public class BuildManager {
    
    //TODO: why is this here?
    protected final Arbitrator<Unit, Double> arbitrator;
    protected final BuildingPlacer buildingPlacer;
    protected final ConstructionManager constructionManager;
    protected final ProductionManager productionManager;
    protected final MorphManager morphManager;
    
    public BuildManager(Arbitrator<Unit, Double> arbitrator) {
        this.arbitrator = arbitrator;
        buildingPlacer = new BuildingPlacer();
        constructionManager = new ConstructionManager(arbitrator, buildingPlacer);
        productionManager = new ProductionManager(arbitrator, buildingPlacer);
        morphManager = new MorphManager(arbitrator);
    }
    
    public void update() {
        constructionManager.update();
        productionManager.update();
        morphManager.update();
    }
    
    public String getName() {
        return "Build Manager";
    }
    
    public BuildingPlacer getBuildingPlacer() {
        return buildingPlacer;
    }
    
    public void onRemoveUnit(Unit unit) {
        constructionManager.onRemoveUnit(unit);
        productionManager.onRemoveUnit(unit);
        morphManager.onRemoveUnit(unit);
    }

    public boolean build(UnitType type) {
        return build(type, Game.getInstance().self().getStartLocation());
    }
    
    public boolean build(UnitType type, TilePosition goalPosition) {
        if (type.equals(UnitType.NONE) || type.equals(UnitType.UNKNOWN)) {
            return false;
        }
        if (type.getRace().equals(Race.ZERG) && type.isBuilding() == type.
                whatBuilds().getKey().isBuilding()) {
            return morphManager.morph(type);
        } else if (type.isBuilding()) {
            return constructionManager.build(type, goalPosition);
        } else {
            return productionManager.train(type);
        }
    }
    
    public int getPlannedCount(UnitType type) {
        if (type.getRace().equals(Race.ZERG) && type.isBuilding() == 
                type.whatBuilds().getKey().isBuilding()) {
            return Game.getInstance().self().completedUnitCount(type) +
                morphManager.getPlannedCount(type);
        } else if (type.isBuilding()) {
            return Game.getInstance().self().completedUnitCount(type) +
                constructionManager.getPlannedCount(type);
        } else {
            return Game.getInstance().self().completedUnitCount(type) +
                productionManager.getPlannedCount(type);
        }
    }
    
    public int getStartedCount(UnitType type) {
        if (type.getRace().equals(Race.ZERG) && type.isBuilding() == 
                type.whatBuilds().getKey().isBuilding()) {
            return Game.getInstance().self().completedUnitCount(type) +
                morphManager.getStartedCount(type);
        } else if (type.isBuilding()) {
            return Game.getInstance().self().completedUnitCount(type) +
                constructionManager.getStartedCount(type);
        } else {
            return Game.getInstance().self().completedUnitCount(type) +
                productionManager.getStartedCount(type);
        }
    }

    public int getCompletedCount(UnitType type) {
        return Game.getInstance().self().completedUnitCount(type);
    }
    
    public void setBuildDistance(int distance) {
        buildingPlacer.setBuildDistance(distance);
    }
}
