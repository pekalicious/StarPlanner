package com.pekalicious.starplanner.sensors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.agent.WorkingMemory;
import com.pekalicious.agent.WorkingMemoryFact;
import com.pekalicious.starplanner.StarMemoryFact;
import com.pekalicious.starplanner.StarMemoryFactType;
import com.pekalicious.starplanner.managers.BaseManager;
import com.pekalicious.starplanner.managers.ResourceManager;

public class EnemySensor {
	private WorkingMemory workingMemory;
    
	private static final int distanceThreshold = 40;
	
    public EnemySensor(ResourceManager resourceManager, WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
        
        List<BaseManager> bases = new ArrayList<BaseManager>(resourceManager.getBaseManagers());
        Collections.reverse(bases);
        for (BaseManager baseManager : bases) {
        	if (!baseManager.isPlayers()) {
        		StarMemoryFact fact = new StarMemoryFact(StarMemoryFactType.ENEMY_BASE);
        		// TODO: If base is visible and enemy is near, confidence = 1.0
        		fact.confidence = 0.5;
        		fact.basePosition = baseManager.getPosition();
        		fact.baseTilePosition = baseManager.getTilePosition();
        		workingMemory.addFact(fact);
        	}
        }
    }
    
    public void onEnemyUnitShow(Unit enemyUnit) {
    	// Is the enemy already spotted?
    	WorkingMemoryFact[] enemyFacts = workingMemory.getFacts(StarMemoryFactType.ENEMY_UNIT, null);
    	boolean newEnemy = true;
    	for (WorkingMemoryFact fact : enemyFacts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (starFact.enemyUnit.equals(enemyUnit)) {
    			starFact.confidence = 1.0;
    			newEnemy = false;
    		}
    	}
    	if (newEnemy) {
        	Logger.Debug("EnemySnsr:\tNew " + enemyUnit.getType().getName() + " enemy sighted!\n", 2);
    		StarMemoryFact newFact = new StarMemoryFact(StarMemoryFactType.ENEMY_UNIT);
    		newFact.confidence = 1.0;
    		newFact.enemyUnit = enemyUnit;
    		workingMemory.addFact(newFact);

    		StarMemoryFact typeFact = getUnitTypeFact(enemyUnit.getType());
			if (typeFact == null) {
				StarMemoryFact newTypeFact = new StarMemoryFact(StarMemoryFactType.ENEMY_TYPE);
				newTypeFact.enemyType = enemyUnit.getType();
				newTypeFact.enemyTypeCount = 1;
				workingMemory.addFact(newTypeFact);
			}else{
				typeFact.enemyTypeCount++;
			}
    	}
    	
    	// If not, is it near a base?
    	WorkingMemoryFact[] baseFacts = workingMemory.getFacts(StarMemoryFactType.ENEMY_BASE, null);
    	boolean nearBase = false;
    	for (WorkingMemoryFact fact : baseFacts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (enemyUnit.getDistance(starFact.basePosition) < 400) {
        		Logger.Debug("EnemySnsr:\tEnemy spotted near base!\n", 2);
    			starFact.confidence = 1.0;
    			nearBase = true;
    			break;
    		}
    	}
    	if (!nearBase) {
    		if (!isNearEnemyLocation(enemyUnit)) {
        		Logger.Debug("EnemySnsr:\tNew enemy location.\n", 2);
        		StarMemoryFact newEnemyLocation = new StarMemoryFact(StarMemoryFactType.ENEMY_LOCATION);
        		newEnemyLocation.enemyLocation = enemyUnit.getPosition();
        		newEnemyLocation.enemyTileLocation = enemyUnit.getTilePosition();
        		this.workingMemory.addFact(newEnemyLocation);
    		}
    	}
    }
    
    private List<WorkingMemoryFact> factsToRemove = new ArrayList<WorkingMemoryFact>();
    public void update() {
    	factsToRemove.clear();
    	
    	WorkingMemoryFact[] enemyFacts = workingMemory.getFacts(StarMemoryFactType.ENEMY_UNIT, null);
    	for (WorkingMemoryFact fact : enemyFacts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (!starFact.enemyUnit.isVisible()) {
    			starFact.confidence = new BigDecimal(starFact.confidence).subtract(new BigDecimal(0.001f)).doubleValue();
        		if (starFact.confidence < 0.2) {
        			Logger.Debug("EnemySnsr:\tRemoving enemy fact with small confidence\n", 3);
        			factsToRemove.add(fact);
        		}
    		}else{
    			if (starFact.enemyUnit.getHitPoints() <= 0) {
    				Logger.Debug("EnemySnsr:\tEnemy is dead!\n", 3);
    				factsToRemove.add(fact);
    				StarMemoryFact typeFact = getUnitTypeFact(starFact.enemyUnit.getType());
    				if (typeFact == null) {
    					Logger.Debug("EnemySnsr:\tNo type fact for " + starFact.enemyUnit.getType().getName() + "!!!\n", 1);
    				}else{
    					typeFact.enemyTypeCount--;
    				}
    			}else{
    				if (!isNearEnemyLocation(starFact.enemyUnit)) {
    	        		//Logger.Debug("EnemySnsr:\tNew enemy location.\n", 1);
    	        		StarMemoryFact newEnemyLocation = new StarMemoryFact(StarMemoryFactType.ENEMY_LOCATION);
    	        		newEnemyLocation.enemyLocation = starFact.enemyUnit.getPosition();
    	        		newEnemyLocation.enemyTileLocation = starFact.enemyUnit.getTilePosition();
    	        		this.workingMemory.addFact(newEnemyLocation);
    				}
    			}
    		}
    	}
    	for (WorkingMemoryFact toRemove : factsToRemove)
    		this.workingMemory.removeFact(toRemove);
    	
    	factsToRemove.clear();
    	
    	WorkingMemoryFact[] locationFacts = workingMemory.getFacts(StarMemoryFactType.ENEMY_LOCATION, null);
    	for (WorkingMemoryFact fact : locationFacts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		
    		if (Game.getInstance().isVisible(starFact.enemyTileLocation)) {
        		boolean foundNearEnemy = false;
    			for (WorkingMemoryFact enemyFact : enemyFacts) {
    				StarMemoryFact starEnemyFact = (StarMemoryFact)enemyFact;
    				if (starEnemyFact.enemyUnit.getDistance(starFact.enemyLocation) < distanceThreshold) {
    					foundNearEnemy = true;
    					break;
    				}
    			}
    			if (!foundNearEnemy) {
    				//Logger.Debug("EnemySnsr:\tNo enemies near location!\n", 1);
    				factsToRemove.add(starFact);
    			}
    		}
    	}
    	
    	WorkingMemoryFact[] baseFacts = workingMemory.getFacts(StarMemoryFactType.ENEMY_BASE, null);
    	for (WorkingMemoryFact fact : baseFacts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (Game.getInstance().isVisible(starFact.baseTilePosition)) {
        		boolean foundNearEnemy = false;
    			for (WorkingMemoryFact enemyFact : enemyFacts) {
    				StarMemoryFact starEnemyFact = (StarMemoryFact)enemyFact;
    				if (starEnemyFact.enemyUnit.getDistance(starFact.basePosition) < 300) {
    					foundNearEnemy = true;
    					break;
    				}
    			}
    			if (!foundNearEnemy) {
    				//Logger.Debug("EnemySnsr:\tNo enemies near location!\n", 1);
    				factsToRemove.add(starFact);
    			}
    		}
    	}
    	
    	for (WorkingMemoryFact toRemove : factsToRemove)
    		this.workingMemory.removeFact(toRemove);
    	
    }
    
    private boolean isNearEnemyLocation(Unit enemyUnit) {
    	WorkingMemoryFact[] facts = workingMemory.getFacts(StarMemoryFactType.ENEMY_LOCATION, null);
    	for (WorkingMemoryFact fact : facts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (enemyUnit.getDistance(starFact.enemyLocation) < distanceThreshold) return true;
    	}
    	
    	return false;
    }
    
    private StarMemoryFact getUnitTypeFact(UnitType type) {
    	WorkingMemoryFact[] facts = workingMemory.getFacts(StarMemoryFactType.ENEMY_TYPE, null);
    	for (WorkingMemoryFact fact : facts) {
    		StarMemoryFact starFact = (StarMemoryFact)fact;
    		if (starFact.enemyType.equals(type)) return starFact;
    	}
    	return null;	
    }
    
}
