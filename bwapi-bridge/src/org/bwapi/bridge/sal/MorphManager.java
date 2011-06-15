package org.bwapi.bridge.sal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

/**
 * Morph manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/MorphManager">MorphManager</a>
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public class MorphManager extends ArbitratedManager {
    
    protected final Map<UnitType, List<UnitType>> morphQueues = 
        new HashMap<UnitType, List<UnitType>>();
    protected final Map<Unit, MorphUnit> morphingUnits = new HashMap<Unit, MorphUnit>();
    protected final Map<UnitType, Integer> plannedCount;
    protected final Map<UnitType, Integer> startedCount;
    
    public MorphManager(Arbitrator<Unit, Double> arbitrator) {
        super(arbitrator);
        Set<UnitType> allUnitTypes = UnitType.allUnitTypes();
        plannedCount = new HashMap<UnitType, Integer>(allUnitTypes.size());
        startedCount = new HashMap<UnitType, Integer>(allUnitTypes.size());
        for (UnitType type : allUnitTypes) {
            plannedCount.put(type, 0);
            startedCount.put(type, 0);
        }
    }
    
    @Override
    public void onOffer(Set<Unit> units) {
        for (Unit unit : units) {
            List<UnitType> types = morphQueues.get(unit.getType());
            boolean used = false;
            if (types != null) {
                Iterator<UnitType> typeIterator = types.iterator();
                while (typeIterator.hasNext()) {
                    UnitType type = typeIterator.next();
                    if (Game.getInstance().canMake(unit, type)) {
                        MorphUnit morphUnit = new MorphUnit();
                        morphUnit.type = type;
                        morphUnit.started = false;
                        morphingUnits.put(unit, morphUnit);
                        typeIterator.remove();
                        arbitrator.accept(this, unit);
                        arbitrator.setBid(this, unit, 100D);
                        used = true;
                        break;
                    }
                }
            }
            if (used) {
                arbitrator.decline(this, unit, 0D);
                arbitrator.removeBid(this, unit);
            }
        }
    }
    
    @Override
    public void onRevoke(Unit unit, Double bid) {
        MorphUnit morphingUnit = morphingUnits.get(unit);
        if (morphingUnit != null) {
            if (!unit.getType().equals(morphingUnit.type) || unit.isMorphing()) {
                List<UnitType> types = morphQueues.get(morphingUnit.type.whatBuilds().getKey());
                if (types == null) {
                    types = new ArrayList<UnitType>();
                    morphQueues.put(morphingUnit.type.whatBuilds().getKey(), types);
                }
                types.add(0, morphingUnit.type);
            }
            morphingUnits.remove(morphingUnit);
        }
    }
    
    @Override
    public void update() {
        for (Unit unit : Game.getInstance().self().getUnits()) {
            List<UnitType> types = morphQueues.get(unit.getType());
            if (types != null && !types.isEmpty() && unit.isCompleted() && morphingUnits.containsKey(unit)) {
                arbitrator.setBid(this, unit, 50D);
            }
        }
        Iterator<Entry<Unit, MorphUnit>> iterator = morphingUnits.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Unit, MorphUnit> morphingUnit = iterator.next();
            if (morphingUnit.getKey().isCompleted()) {
                if (morphingUnit.getKey().getType().equals(morphingUnit.getValue().type)) {
                    iterator.remove();
                    arbitrator.removeBid(this, morphingUnit.getKey());
                    decrementMapValue(plannedCount, morphingUnit.getValue().type);
                    decrementMapValue(startedCount, morphingUnit.getValue().type);
                } else if (Game.getInstance().canMake(null, morphingUnit.getValue().type)) {
                    morphingUnit.getKey().morph(morphingUnit.getValue().type);
                }
            }
            if (morphingUnit.getKey().isMorphing() && !morphingUnit.getValue().started) {
                incrementMapValue(startedCount, morphingUnit.getValue().type);
                morphingUnit.getValue().started = true;
            }
        }
    }
    
    @Override
    public String getName() {
        return "Morph Manager";
    }
    
    public void onRemoveUnit(Unit unit) {
        MorphUnit morphUnit = morphingUnits.get(unit);
        if (morphUnit != null) {
            List<UnitType> types = morphQueues.get(morphUnit.type.whatBuilds().getKey());
            if (types == null) {
                types = new ArrayList<UnitType>();
                morphQueues.put(morphUnit.type.whatBuilds().getKey(), types);
            }
            types.add(0, morphUnit.type);
            morphingUnits.remove(unit);
        }
    }
    
    public boolean morph(UnitType type) {
        if (type.isBuilding() != type.whatBuilds().getKey().isBuilding()) {
            return false;
        }
        List<UnitType> types = morphQueues.get(type.whatBuilds().getKey());
        if (types == null) {
            types = new ArrayList<UnitType>();
            morphQueues.put(type.whatBuilds().getKey(), types);
        }
        types.add(type);
        incrementMapValue(plannedCount, type);
        return true;
    }
    
    public int getPlannedCount(UnitType type) {
        return plannedCount.get(type);
    }
    
    public int getStartedCount(UnitType type) {
        return startedCount.get(type);
    }
    
    /**
     * Simple POJO holding morph unit
     * 
     * @author Chad Retz
     */
    protected static final class MorphUnit {
        protected UnitType type;
        protected boolean started;
        
        protected MorphUnit() {
        }
    }
}
