package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bwapi.bridge.model.BaseLocation;
import org.bwapi.bridge.model.Bwta;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.util.UnitUtils;

public enum ResourceManager {
	Instance;
	
	private List<BaseManager> baseManagers;
	@SuppressWarnings("unused")
	private StarBlackboard blackboard;
	private Game game;
	
	/**
	 * A set of orders that are currently being processed.
	 */
	private Set<ResourceOrder> queuedOrders;
	/**
	 * 
	 * The minerals available to the player taking account the queued orders.
	 */
	private int freeMinerals;
	/**
	 * The gas available to the player taking account the queued orders;
	 */
	private int freeGas;
	
	private boolean hasInitialized;
	
	public void Init(Game game, StarBlackboard blackboard) {
		this.game = game;
		this.blackboard = blackboard;
		this.hasInitialized = false;
		this.queuedOrders = new HashSet<ResourceOrder>();

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
		
		//Managed queued orders and calculate free resources.
		this.freeMinerals = game.self().minerals();
		this.freeGas = game.self().gas();
		
		for (ResourceOrder order : this.queuedOrders) {
			this.freeMinerals -= order.getMineralPrice();
			this.freeGas -= order.getGasPrice();
		}
		
		Game.getInstance().drawTextScreen(417, 18, "Free: " + this.freeMinerals);
		Game.getInstance().drawTextScreen(485, 18, "Free: " + this.freeGas);
		
		//Create workers for each SCV we find in the wild the very first time
		//the game starts. This does not have to happen anymore.
		if (hasInitialized) return;
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
		hasInitialized = true;
	}
	
	/**
	 * Returns the minerals available to the player (taking into account the queued orders).
	 * @return the minerals available to the player.
	 */
	public int getFreeMinerals() {
		return freeMinerals;
	}

	/**
	 * Returns the gas available to the player (taking into account the queued orders).
	 * @return the gas available to the player.
	 */
	public int getFreeGas() {
		return freeGas;
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
			base.assignToWork(new Worker(unit, base));
		}else{
			Logger.Debug("ResMngr:\tCould not find base for hanging units!\n", 1);
		}
	}

	/**
	 * Adds an order to the queue.
	 * @param order the order to add.
	 */
	public void addToOrdersQueue(ResourceOrder order) {
		this.queuedOrders.add(order);
	}
	
	/**
	 * Removes an order from the queue.
	 * @param order the order to remove
	 */
	public void removeFromOrdersQueue(ResourceOrder order) {
		this.queuedOrders.remove(order);
	}
}
