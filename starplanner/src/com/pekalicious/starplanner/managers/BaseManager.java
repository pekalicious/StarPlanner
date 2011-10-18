package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.BaseLocation;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Player;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.model.GasPatch;
import com.pekalicious.starplanner.model.MineralPatch;
import com.pekalicious.starplanner.util.UnitUtils;

public class BaseManager {
	private Unit 				base;
	private BaseLocation 		location;
	private List<MineralPatch> 	mineralPatches;
	private List<GasPatch> 		gasPatches;
	
	private List<Unit> 			buildingOrders;
	private List<Worker> 		workers;
	

	private int 				maxMineralGatherers;
	private int 				maxRefineryWorkers;

	private StarBlackboard 		blackboard;
	private Player 				owner;
	
	private TrainingOrder 		workerOrder;
	private BuildOrder 			refineryOrder;
	private List<BuildOrder> 	orders;
	private List<BuildOrder> 	ordersToRemove;

	public BaseManager(BaseLocation location, StarBlackboard blackboard) {
		this.location = location;
		this.blackboard = blackboard;
		this.workers = new ArrayList<Worker>();
		this.buildingOrders = new ArrayList<Unit>();
		this.workerOrder = null;
		this.refineryOrder = null;
		this.orders = new ArrayList<BuildOrder>();
		this.ordersToRemove = new ArrayList<BuildOrder>();
		
		//Initialize resources near base location
		this.setMineralPatches(new ArrayList<MineralPatch>());
		for (Unit patch : location.getMinerals())
			this.getMineralPatches().add(new MineralPatch(patch));
		this.maxMineralGatherers = this.getMineralPatches().size() * 2;

		this.setGasPatches(new ArrayList<GasPatch>());
		for (Unit patch : location.getGeysers())
			this.getGasPatches().add(new GasPatch(patch));
		this.maxRefineryWorkers = this.getGasPatches().size() * 3;
		Logger.Debug("BaseMngr:\tResources found: " + getMineralPatches().size() + "M/" + getGasPatches().size() + "G\n", 1);
		
		//Does this base have a command center?
		int x = location.getTilePosition().x();
		int y = location.getTilePosition().y();
		Unit baseUnit = UnitUtils.getUnitAtPosition(x, y, UnitType.TERRAN_COMMAND_CENTER);
		if ( baseUnit != null ) {
			Logger.Debug("BaseMngr:\tWith command center!\n", 1);
			this.base = baseUnit;
			this.owner = baseUnit.getPlayer();
		}else{
			Logger.Debug("BaseMngr:\tWithout command center...\n", 1);
		}
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean isPlayersBase() {
		return this.owner != null && this.owner.equals(Game.getInstance().self());
	}
	
	public boolean assignBuildOrderToWorker(BuildOrder newOrder){
		if (this.workers.size() == 0) {
			Logger.Debug("BaseMngr:\tNo workers!\n", 1);
			return false;
		}
		
		for (Worker worker : workers) {
			if (!worker.hasBuildQueued()) {
				worker.startBuilding(newOrder);
				return true;
			}
		}
		
		Logger.Debug("BaseMngr:\tCould not find free worker!\n", 1);
		return false;
	}
	
	public boolean hasBase() {
		return base != null;
	}
	
	public Position getPosition() {
		return this.location.getPosition();
	}
	
	public void update() {
		for (Worker worker : this.workers)
			worker.update();
		
		this.ordersToRemove.clear();
		for (BuildOrder order : this.orders) {
			if (order.status == OrderStatus.Ended) {
				this.buildingOrders.add(order.completedUnit);
				this.ordersToRemove.add(order);
				//goBackToWork(order.worker);
				Logger.Debug("BaseMngr:\tBase has now " + this.buildingOrders.size() + " buildings\n", 3);
			}
		}
		for (BuildOrder order : this.ordersToRemove)
			this.orders.remove(order);
		
		if (this.base == null) return;
		if (isFull()) return;
		Logger.Debug("BaseMngr:\tBase not full\n", 5);
		
		/*
		if (this.oldWorkers.size() >= this.maxRefineryWorkers && needRefinery()) {
			if (this.refineryOrder == null) {
				GasPatch freePatch = null;
				for (GasPatch patch : this.gasPatches) {
					if (!patch.hasRefinery()) {
						freePatch = patch;
						break;
					}
				}
				if (freePatch == null) {
					Logger.Debug("BaseMngr:\tCould not find gas patch!\n", 1);
				}else{
					Logger.Debug("BaseMngr:\tCreating refinery...\n", 3);
					this.refineryOrder = new BuildOrder(UnitUtils.Type.TERRAN_REFINERY, 1);
					this.refineryOrder.startPosition = getTilePosition();
					this.refineryOrder.onUnit = freePatch.getGasUnit();
					this.refineryOrder.baseManager = this;
					addBuildOrder(this.refineryOrder);
					this.blackboard.buildQueue.add(this.refineryOrder);
				}
			}else{
				if (this.refineryOrder.status == OrderStatus.Ended) {
					for (GasPatch patch : this.gasPatches) {
						if (!patch.hasRefinery()) {
							Logger.Debug("BaseMngr:\tRefinery done. Back to work!\n", 3);
							patch.setRefinery(this.refineryOrder.completedUnits.get(0));
							goBackToWork(this.refineryOrder.worker);
							this.refineryOrder = null;
							break;
						}
					}
					
					if (this.refineryOrder != null)
						Logger.Debug("BaseMngr:\tCould not assign refinery!\n", 1);
				}
			}
		}else{
		*/
			if (this.workerOrder == null) {
				Logger.Debug("BaseMngr:\tTraining new worker\n", 3);
				this.workerOrder = new TrainingOrder(UnitUtils.Type.TERRAN_SCV, 1);
				this.blackboard.trainingQueue.add(this.workerOrder);
			}else{
				if (this.workerOrder.status == OrderStatus.Ended) {
					for (Unit unit : this.workerOrder.completedUnits) {
						Worker worker = new Worker(unit, this);
						assignToWork(worker);
						workers.add(worker);
					}
					this.workerOrder = null;
				}
			}
			/*
		}
		*/
	}
	
	private boolean needRefinery() {
		for (GasPatch patch : this.getGasPatches())
			if (!patch.hasRefinery()) return true;
		
		return false;
	}
	
	private boolean canAssignToGas() {
		if (isGasFull()) return false;
		
		for (GasPatch patch : this.getGasPatches())
			if (patch.hasRefinery()) return true;
		
		return false;
	}
	
	public boolean isFull() {
		return isMineralsFull() && isGasFull();
	}
	
	private boolean isMineralsFull() {
		for (MineralPatch patch : this.getMineralPatches())
			if (!patch.isFull()) return false;
		
		return true;
	}
	
	private boolean isGasFull() {
		for (GasPatch patch : this.getGasPatches())
			if (!patch.isFull()) return false;
		
		return true;
	}
	
	public boolean isAssignedToThisBase(Unit unit) {
		for (Worker worker : this.workers)
			if (worker.getUnit().equals(unit)) return true;
		
		return false;
	}
	
	public void assignToWork(Worker worker) {
		this.workers.add(worker);
	}


	public int getBuildingsCount() {
		return this.buildingOrders.size();
	}

	public TilePosition getTilePosition() {
		return this.location.getTilePosition();
	}

	public List<MineralPatch> getMineralPatches() {
		return mineralPatches;
	}

	public void setMineralPatches(List<MineralPatch> mineralPatches) {
		this.mineralPatches = mineralPatches;
	}

	public List<GasPatch> getGasPatches() {
		return gasPatches;
	}

	public void setGasPatches(List<GasPatch> gasPatches) {
		this.gasPatches = gasPatches;
	}
}
