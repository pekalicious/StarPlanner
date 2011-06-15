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
import org.bwapi.bridge.model.TechType;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

/**
 * Tech manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/TechManager">TechManager</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.3
 */
public class TechManager extends ArbitratedManager {

    protected BuildingPlacer placer;
    protected final Map<UnitType, List<TechType>> researchQueues =
        new HashMap<UnitType, List<TechType>>();
    protected final Map<Unit, TechType> researchingUnits =
        new HashMap<Unit, TechType>();
    protected final Set<TechType> plannedTech = new HashSet<TechType>();
    
    public TechManager(Arbitrator<Unit, Double> arbitrator) {
        super(arbitrator);
    }
    
    public void setBuildingPlacer(BuildingPlacer buildingPlacer) {
        placer = buildingPlacer;
    }
    
    @Override
    public void onOffer(Set<Unit> units) {
        for (Unit unit : units) {
            List<TechType> types = researchQueues.get(unit.getType());
            if (types != null) {
                boolean used = false;
                Iterator<TechType> typeIterator = types.iterator();
                while (typeIterator.hasNext()) {
                    TechType type = typeIterator.next();
                    if (Game.getInstance().canResearch(unit, type) && unit.isIdle()) {
                        researchingUnits.put(unit, type);
                        typeIterator.remove();
                        arbitrator.accept(this, unit);
                        arbitrator.setBid(this, unit, 100D);
                        used = true;
                        break;
                    }
                }
                if (!used) {
                    arbitrator.decline(this, unit, 0D);
                    arbitrator.removeBid(this, unit);
                }
            }
        }
    }
    
    @Override
    public void onRevoke(Unit unit, Double bid) {
        onRemoveUnit(unit);
    }
    
    @Override
    public void update() {
        for (Unit unit : Game.getInstance().self().getUnits()) {
            List<TechType> types = researchQueues.get(unit.getType());
            if (types != null && !types.isEmpty()) {
                arbitrator.setBid(this, unit, 50D);
            }
        }
        Iterator<Entry<Unit, TechType>> unitIterator = researchingUnits.entrySet().iterator();
        while (unitIterator.hasNext()) {
            Entry<Unit, TechType> researchingUnit = unitIterator.next();
            if (researchingUnit.getKey().isResearching()) {
                if (!researchingUnit.getKey().getTech().equals(researchingUnit.getValue())) {
                    researchingUnit.getKey().cancelResearch();
                }
            } else {
                if (Game.getInstance().self().hasResearched(researchingUnit.getValue())) {
                    unitIterator.remove();
                    arbitrator.removeBid(this, researchingUnit.getKey());
                } else {
                    if (researchingUnit.getKey().isLifted()) {
                        if (researchingUnit.getKey().isIdle()) {
                            researchingUnit.getKey().land(placer.getBuildLocationNear(researchingUnit.getKey().
                                    getTilePosition().opAdd(new TilePosition(0, 1)), researchingUnit.getKey().getType()));
                        }
                    } else if (Game.getInstance().canResearch(researchingUnit.getKey(), researchingUnit.getValue())) {
                        researchingUnit.getKey().research(researchingUnit.getValue());
                    }
                }
            }
        }
    }
    
    public String getName() {
        return "Tech Manager";
    }
    
    public void onRemoveUnit(Unit unit) {
        TechType type = researchingUnits.get(unit);
        if (type != null) {
            if (!Game.getInstance().self().hasResearched(type)) {
                List<TechType> types = researchQueues.get(type.whatResearches());
                if (types == null) {
                    types = new ArrayList<TechType>();
                    researchQueues.put(type.whatResearches(), types);
                }
                types.add(0, type);
            }
            researchingUnits.remove(unit);
        }
    }
    
    public boolean research(TechType type) {
        List<TechType> types = researchQueues.get(type.whatResearches());
        if (types == null) {
            types = new ArrayList<TechType>();
            researchQueues.put(type.whatResearches(), types);
        }
        types.add(type);
        plannedTech.add(type);
        return true;
    }
    
    public boolean planned(TechType type) {
        return plannedTech.contains(type);
    }
}
