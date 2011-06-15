package com.pekalicious.starplanner.tests;

import org.bwapi.unit.BwapiTestCase;
import org.bwapi.unit.BwapiTestInformation;
import org.bwapi.unit.model.BroodwarGameType;
import org.bwapi.unit.model.BroodwarPlayer;
import org.bwapi.unit.model.BroodwarRace;
import org.bwapi.unit.model.BroodwarStreamMap;
import org.junit.Test;

import com.pekalicious.starplanner.Main;
import com.pekalicious.starplanner.StarPlannerBridgeBot;

/**
 * Draw regions
 * 
 * @author Chad Retz
 */
public class StarPlannerUnit extends BwapiTestCase {
    
    @Test
    public void runTest(String map) throws Exception {
    	execute(new BwapiTestInformation("c:/program files/starcraft", 
    			"c:/program files/chaoslauncher", 
    			StarPlannerBridgeBot.class, 
    				new BroodwarStreamMap(map, 
    				Main.class.getResourceAsStream("maps/" + map)), 
    			BroodwarGameType.USE_MAP_SETTINGS, 
    			new BroodwarPlayer(true, BroodwarRace.TERRAN),
    			new BroodwarPlayer(false, BroodwarRace.ZERG)));
    }
    
}
