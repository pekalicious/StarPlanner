package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.BaseLocation;
import org.bwapi.bridge.model.Bwta;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.util.UnitUtils;

public class ResourceManager {
	private List<BaseManager> baseManagers;
	@SuppressWarnings("unused")
	private StarBlackboard blackboard;
	private Game game;
	
	public ResourceManager(Game game, StarBlackboard blackboard) {
		this.game = game;
		this.blackboard = blackboard;

		this.baseManagers = new ArrayList<BaseManager>();
		for (BaseLocation base : Bwta.getBaseLocations())
			this.baseManagers.add(new BaseManager(base, blackboard));
	}
	
	public List<BaseManager> getBaseManagers() {
		return this.baseManagers;
	}
	
	public void update() {
		for (BaseManager baseManager : this.baseManagers)
			baseManager.update();
		
		for (Unit unit : UnitUtils.getUnitList(game, UnitType.TERRAN_SCV)) {
			boolean found = false;
			for (BaseManager baseManager : this.baseManagers) {
				if (baseManager.isAssignedToThisBase(unit)) {
					found = true;
					continue;
				}
			}
			if (!found) {
				assingToClosestBase(unit);
			}
		}
	}
	
	private void assingToClosestBase(Unit unit) {
		Logger.Debug("ResMngr:\tAssigning to closest base\n", 3);
		BaseManager base = null;
		double closest = Double.MAX_VALUE;
		
		for (BaseManager baseManager : this.baseManagers) {
			if (baseManager.isFull()) continue;
			if (!baseManager.hasBase()) continue;
			
			double dx = unit.getPosition().x() - baseManager.getPosition().x();
			double dy = unit.getPosition().y() - baseManager.getPosition().y();
			double dist = Math.sqrt(dx*dx + dy*dy); 

			if (dist < closest) {
				base = baseManager;
				closest = dist;
			}
		}
		
		if (base != null) {
			Logger.Debug("ResMngr:\tFound base.\n", 3);
			base.assignToWork(unit);
		}else{
			Logger.Debug("ResMngr:\tCould not find base for hanging units!\n", 1);
		}
	}
}
