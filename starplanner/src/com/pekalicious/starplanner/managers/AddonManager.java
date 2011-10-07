package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.util.UnitUtils;

public class AddonManager {
	private Game game;
	private StarBlackboard blackboard;
	
	public AddonManager(Game game, StarBlackboard blackboard) {
		this.game = game;
		this.blackboard = blackboard;
	}

	List<AddonOrder> toRemove = new ArrayList<AddonOrder>();
	public void update() {
		toRemove.clear();
		for (AddonOrder order : blackboard.addonQueue) {
			int mineralsDiff = game.self().minerals() - order.unitType.bwapiType.mineralPrice();
			int gasDiff = game.self().gas() - order.unitType.bwapiType.gasPrice();

			switch (order.status) {
			case Idle:
				List<Unit> factories = UnitUtils.getUnitList(Game.getInstance(), UnitType.TERRAN_FACTORY);
				Unit emptyFactory = null;
				if (factories.size() > 0) {
					for (Unit factory : factories) {
						if (factory.getAddon() == null) {
							emptyFactory = factory;
						}
					}
					
					if (emptyFactory != null) {
						order.building = emptyFactory;
						order.status = OrderStatus.Next;
					}else{
						Logger.Debug("ActMachineShop\t:No empty factory found!\n", 1);
					}
				}else{
					Logger.Debug("ActMachineShop\t:No factories!\n", 1);
				}
				break;
			case Next:
				if (mineralsDiff >= 0 && gasDiff >= 0) {
					if (order.building.buildAddon(order.unitType.bwapiType)) {
						order.status = OrderStatus.Started;
					}else{
						Logger.Debug("AddonMngr:\tBuild declined!\n", 1);
					}
				}
				break;
			case Started:
				Unit addon = order.building.getAddon();
				if (addon != null) {
					if (addon.isCompleted()) {
						order.status = OrderStatus.Ended;
					}
				}else{
					order.status = OrderStatus.Idle;
				}
				break;
			case Ended:
				toRemove.add(order);
				break;
			}
		}
		
		for (AddonOrder order : toRemove)
			blackboard.addonQueue.remove(order);

	}

}
