package com.pekalicious.starplanner.sensors;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.agent.WorkingMemory;
import com.pekalicious.agent.WorkingMemoryFact;
import com.pekalicious.starplanner.StarMemoryFact;
import com.pekalicious.starplanner.StarMemoryFactType;

public class UnitSensor {
	private WorkingMemory workingMemory;
    
    public UnitSensor(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }
    
    private int countdown = time;
    private static final int time = 200;
    public void update() {
    	if (--countdown > 0) return;
    	countdown = time;
    	
    	WorkingMemoryFact[] typeFacts = workingMemory.getFacts(StarMemoryFactType.ENEMY_TYPE, null);
    	for (WorkingMemoryFact fact : typeFacts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (starFact.enemyType.isBuilding()) continue;
//    		if (starFact.enemyType.equals(UnitType.ZERG_DRONE)) continue;
//    		if (starFact.enemyType.equals(UnitType.ZERG_LAIR)) continue;

    		UnitType needType = null;
    		int needCount = 0;
    		
    		if (starFact.enemyType.equals(UnitType.ZERG_ZERGLING)) {
    			needType = UnitType.TERRAN_MARINE;
    			needCount = (int) (starFact.enemyTypeCount * 1.5);
    		}else if (starFact.enemyType.equals(UnitType.ZERG_HYDRALISK)) {
    			needType = UnitType.TERRAN_FIREBAT;
    			needCount = starFact.enemyTypeCount;
    		}
    		
    		if (needType == null) {
    			Logger.Debug("UnitSnsr:\tNo need type for " + starFact.enemyType.getName() + "\n", 1);
    		}else{
        		StarMemoryFact needFact = getNeedTypeFact(needType);
        		if (needFact == null) {
        			needFact = new StarMemoryFact(StarMemoryFactType.NEED_TYPE);
        			workingMemory.addFact(needFact);
        		}

        		int countDiff = needCount - Game.getInstance().self().completedUnitCount(needType);
    			if (countDiff > 0) {
    				Logger.Debug("UnitSnsr:\t" + countDiff + " " + needType.getName() + " needed!\n", 3);
            		needFact.needType = needType;
        			needFact.needTypeCount = countDiff;
    			}else{
    				workingMemory.removeFact(needFact);
    			}
    		}
    	}
    }
    
    private StarMemoryFact getNeedTypeFact(UnitType type) {
    	WorkingMemoryFact[] facts = workingMemory.getFacts(StarMemoryFactType.NEED_TYPE, null);
    	for (WorkingMemoryFact fact : facts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (starFact.needType.equals(type)) return starFact;
    	}
    	return null;	
    }
    
}
