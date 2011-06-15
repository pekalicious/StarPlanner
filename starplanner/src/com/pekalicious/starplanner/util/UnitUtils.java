package com.pekalicious.starplanner.util;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;

public class UnitUtils {
	public static Unit getUnitType(Game game, UnitType type) {
		for (Unit unit : game.self().getUnits())
			if (unit.getType().equals(type))
				return unit;
		
		return null;
	}
	
	public static List<Unit> getUnitList(Game game, UnitType type) {
		List<Unit> unitList = new ArrayList<Unit>();
		for (Unit unit : game.self().getUnits())
			if (unit.getType().equals(type))
				unitList.add(unit);
		
		return unitList;
	}

	public static Unit getIdleWorker(Game game, StarBlackboard blackBoard) {
		for (Unit unit : UnitUtils.getUnitList(game, UnitType.TERRAN_SCV))
			if (!blackBoard.isOccupied(unit))
				return unit;

		return null;
	}
	
	public static Unit getClosestUnitTo(Game game, Unit unit, UnitType type) {
		Unit closestUnit = null;
		double closest = Double.MAX_VALUE;
		
		for (Unit otherUnit : getUnitList(game, type)) {
			double dx = unit.getPosition().x() - otherUnit.getPosition().x();
			double dy = unit.getPosition().y() - otherUnit.getPosition().y();
			double dist = Math.sqrt(dx*dx + dy*dy); 

			if (dist < closest) {
				closestUnit = otherUnit;
				closest = dist;
			}
			
		}
		
		return closestUnit;
	}
	
	public static int countBattleUnits(Game game) {
		int count = 0;
		for (Unit unit : game.self().getUnits()) {
			if (unit.getType().isBuilding()) continue;
			if (unit.getType().equals(UnitType.TERRAN_SCV)) continue;
			if (unit.getType().equals(UnitType.RESOURCE_MINERAL_FIELD)) continue;
			if (unit.getType().equals(UnitType.RESOURCE_VESPENE_GEYSER)) continue;
			if (unit.getHitPoints() <= 0) continue;
			
			Logger.Debug("UnitUtils:\tCounting " + unit.getType().getName() + "\n", 3);
			
			count++;
		}
		
		return count;
	}
	
	public enum Type {
		NONE(UnitType.NONE, -1), 

		TERRAN_SCV(UnitType.TERRAN_SCV, 5), 
		TERRAN_GOLIATH(UnitType.TERRAN_GOLIATH, 4), 
		TERRAN_MARINE(UnitType.TERRAN_MARINE, 4),
		
		TERRAN_SUPPLY_DEPOT(UnitType.TERRAN_SUPPLY_DEPOT, 1), 
		TERRAN_REFINERY(UnitType.TERRAN_REFINERY, 1),
		
		TERRAN_ARMORY(UnitType.TERRAN_ARMORY, 4), 
		TERRAN_BARRACKS(UnitType.TERRAN_BARRACKS, 4), 
		TERRAN_ENGINEERING_BAY(UnitType.TERRAN_ENGINEERING_BAY, 4), 
		TERRAN_FACTORY(UnitType.TERRAN_FACTORY, 4), 
		TERRAN_FIREBAT(UnitType.TERRAN_FIREBAT, 4), 
		TERRAN_ACADEMY(UnitType.TERRAN_ACADEMY, 4); 
		
		//TODO: Make these fields private and provide delegates.
		public int priority;
		public UnitType bwapiType;
		
		private Type(UnitType type, int priority) {
			this.bwapiType = type;
			this.priority = priority;
		}
	}

	public static List<Unit> getCompletedUnitList(Game game, UnitType key) {
		List<Unit> tmpUnits = getUnitList(game, key);
		List<Unit> completedUnitList = new ArrayList<Unit>();
		for (Unit tmp : tmpUnits)
			if (tmp.isCompleted())
				completedUnitList.add(tmp);
		
		return completedUnitList;
	}
}
