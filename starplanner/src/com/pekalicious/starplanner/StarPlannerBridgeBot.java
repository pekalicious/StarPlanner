package com.pekalicious.starplanner;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.bridge.model.BaseLocation;
import org.bwapi.bridge.model.Bwta;
import org.bwapi.bridge.model.Chokepoint;
import org.bwapi.bridge.model.Color;
import org.bwapi.bridge.model.Flag;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Player;
import org.bwapi.bridge.model.Polygon;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Region;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.tests.TestBot;

public class StarPlannerBridgeBot implements BridgeBot {

	private boolean analyzed = false;
	private StarPlanner starPlanner;
	private TestBot testBot;
	private JStarPlanner plannerGui;
	private boolean useTestBot = false;
	private boolean useStarPlanner;
	
	@Override
	public void onFrame() {
		if (!analyzed) {
			initializeMap();
			initializeManagers();
			initializeFrame();
			initializeLogger();
		}
    	drawMapAnalysis();
    	if (Game.getInstance().isPaused()) return;
    	
		try {
	    	if (useTestBot)
	    		testBot.update(Game.getInstance());
	    	else if (plannerGui != null && this.useStarPlanner)
	    		starPlanner.update(Game.getInstance());
		}catch (Exception e) {
			Logger.Debug("Could not update: " + e + " " + Logger.toString(e.getStackTrace()) + "\n", 1);
		}
	}
	
	private void drawMapAnalysis() {
		if (!analyzed) return;
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

	@Override
	public boolean onSendText(String text) {
		if (text.equals("show starplanner"))
			if (this.plannerGui != null && !this.plannerGui.isVisible())
				this.plannerGui.setVisible(true);
		
		return false;
	}

	@Override
	public void onStart() {
		Game.getInstance().enableFlag(Flag.USER_INPUT);
	}
	
	private void initializeMap() {
		try {
	        Bwta.readMap();
			Bwta.analyze();
			analyzed = true;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " : " + e.getStackTrace()[0].toString(), "InitMap: Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void initializeManagers() {
		try {
			starPlanner = new StarPlanner(Game.getInstance());
			testBot = new TestBot();
			setPlannerStatus(true);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " : " + e.getStackTrace()[0].toString(), "InitBot: Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initializeFrame() {
		try {
			Logger.DEBUG_LEVEL = 1;
			plannerGui = new JStarPlanner(this);
			plannerGui.setVisible(true);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " : " + e.getStackTrace()[0].toString(), "InitFrame: Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void initializeLogger() {
		try {
			//Logger.DEBUG_LEVEL = 1;
			Logger.printer = plannerGui;
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					Logger.Debug(e.getMessage(), 1);
				}
			});
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e + " : " + e.getStackTrace()[0].toString(), "InitLogger: Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void onEnd(boolean arg0) {
		Logger.Debug("Game ended!\n", 1);
		System.exit(0);
	}

	@Override
	public void onNukeDetect(Position arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerLeft(Player arg0) {
	}

	@Override
	public void onUnitCreate(Unit unit) {
		//Game.getInstance().printf(unit.getPlayer().getName() + ": " + unit.getType().getName() + " created");
	    if (this.starPlanner != null && this.useStarPlanner)
	        starPlanner.onUnitCreate(unit);
	}

	@Override
	public void onUnitDestroy(Unit unit) {
		//Game.getInstance().printf(unit.getPlayer().getName() + ": " + unit.getType().getName() + " created");
	    if (this.starPlanner != null && this.useStarPlanner)
	        starPlanner.onUnitDestroy(unit);
	}

	@Override
	public void onUnitHide(Unit arg0) {
	}

	@Override
	public void onUnitMorph(Unit arg0) {
	}

	@Override
	public void onUnitRenegade(Unit arg0) {
	}

	@Override
	public void onUnitShow(Unit unit) {
		if (this.starPlanner != null && this.useStarPlanner)
			starPlanner.onUnitShow(unit);
	}
	
	public void setPlannerStatus(boolean newStatus) {
		this.useStarPlanner = newStatus;
	}
	
	public boolean getPlannerStatus() {
		return this.useStarPlanner;
	}
	
	public StarPlanner getStarPlanner() {
		return this.starPlanner;
	}

}
