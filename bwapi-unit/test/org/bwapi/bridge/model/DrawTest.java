package org.bwapi.bridge.model;

import java.util.Set;
import java.util.Map.Entry;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.unit.BwapiTestCase;
import org.bwapi.unit.BwapiTestInformation;
import org.bwapi.unit.model.BroodwarGameType;
import org.bwapi.unit.model.BroodwarPlayer;
import org.bwapi.unit.model.BroodwarRace;
import org.bwapi.unit.model.BroodwarStreamMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * Draw regions
 * 
 * @author Chad Retz
 */
public class DrawTest extends BwapiTestCase {
    
    @Test
    public void testSimple() throws Exception {
        execute(new BwapiTestInformation(
                "c:/program files/starcraft", 
                "c:/dls/lib/scapi/chaoslauncher", 
                TestBridgeBot.class, new BroodwarStreamMap("ICCup python 1.3_ob.scm", 
                        DrawTest.class.getResourceAsStream("ICCup python 1.3_ob.scm")), 
                        BroodwarGameType.MELEE, new BroodwarPlayer(true, BroodwarRace.RANDOM),
                        new BroodwarPlayer(false, BroodwarRace.RANDOM)));
    }
    
    /**
     * Simple test bot
     * 
     * @author Chad Retz
     */
    public static class TestBridgeBot implements BridgeBot {

        private boolean analyzed = false;
        
        public void onStart() {
            Game.getInstance().printf("Started test!");
            Game.getInstance().enableFlag(Flag.USER_INPUT);
            Bwta.readMap();
        }
        
        public void onFrame() {
            if (analyzed) {
                for (BaseLocation location : Bwta.getBaseLocations()) {
                    //outline location
                    TilePosition p = location.getTilePosition();
                    Game.getInstance().drawBoxMap(p.x() * 32, p.y() * 32, 
                            p.x() * 32 + 4 * 32, p.y() * 32 + 3 * 32, Color.BLUE);
                    //static minerals
                    for (Unit mineral : location.getStaticMinerals()) {
                        Position pos = mineral.getInitialPosition();
                        Game.getInstance().drawCircleMap(pos.x(), pos.y(),
                                30, Color.CYAN, false);
                    }
                    //geysers
                    for (Unit geyser : location.getGeysers()) {
                        TilePosition pos = geyser.getInitialTilePosition();
                        Game.getInstance().drawBoxMap(pos.x() * 32, pos.y() * 32, pos.x() * 32 + 4 * 32, 
                                pos.y() * 32 + 2 * 32, Color.ORANGE, false);
                    }
                    //island
                    if (location.isIsland()) {
                        Position pos = location.getPosition();
                        Game.getInstance().drawCircleMap(pos.x(), pos.y(), 80, Color.YELLOW, false);
                    }
                }
                //polygons
                Set<Region> regions = Bwta.getRegions();
                for (Region region : regions) {
                    Polygon poly = region.getPolygon();
                    for (int i = 0; i < poly.size(); i++) {
                        Position pos1 = poly.get(i);
                        Position pos2 = poly.get((i + 1) % poly.size());
                        Game.getInstance().drawLineMap(pos1.x(), pos1.y(), pos2.x(), pos2.y(), Color.GREEN);
                    }
                }
                //chokepoints
                for (Region region : regions) {
                    for (Chokepoint chokepoint : region.getChokepoints()) {
                        Entry<Position, Position> pos = chokepoint.getSides();
                        Game.getInstance().drawLineMap(pos.getKey().x(), pos.getKey().y(), 
                                pos.getKey().x(), pos.getValue().y(), Color.RED);
                    }
                }
            }
        }
        
        public boolean onSendText(String string) {
            if ("fail".equals(string)) {
                Assert.fail("Entered fail string!");
            } else if ("map".equals(string)) {
                Game.getInstance().printf("Analyzing map...");
                Bwta.analyze();
                Game.getInstance().printf("Done analyzing");
                analyzed = true;
            } else {
                Game.getInstance().printf("You typed: " + string);
            }
            return true;
        }

        @Override
        public void onEnd(boolean isWinner) {
        }

        @Override
        public void onNukeDetect(Position target) {
        }

        @Override
        public void onPlayerLeft(Player player) {
        }

        @Override
        public void onUnitCreate(Unit unit) {
            Game.getInstance().printf("Unit created: " + unit.getType().getName());
        }

        @Override
        public void onUnitDestroy(Unit unit) {
            Game.getInstance().printf("Unit destroyed: " + unit.getType().getName());
        }

        @Override
        public void onUnitHide(Unit unit) {
            Game.getInstance().printf("Unit hidden: " + unit.getType().getName());
        }

        @Override
        public void onUnitMorph(Unit unit) {
            Game.getInstance().printf("Unit morphed: " + unit.getType().getName());
        }

        @Override
        public void onUnitRenegade(Unit unit) {
            Game.getInstance().printf("Unit renegade: " + unit.getType().getName());
        }

        @Override
        public void onUnitShow(Unit unit) {
            Game.getInstance().printf("Unit shown: " + unit.getType().getName());
        }
    }
}
