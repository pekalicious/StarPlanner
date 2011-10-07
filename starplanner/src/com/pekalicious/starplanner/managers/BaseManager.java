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
import com.pekalicious.starplanner.model.GasPatch;
import com.pekalicious.starplanner.model.MineralPatch;
import com.pekalicious.starplanner.util.UnitUtils;

public class BaseManager {
	private List<MineralPatch> mineralPatches;
	private int maxMineralGatherers;
	private int maxRefineryWorkers;
	private List<GasPatch> gasPatches;
	@SuppressWarnings("unused")
	private int maxGasGatherers;
	private Unit base;
	private BaseLocation location;
	private List<Unit> buildings;
	private StarBlackboard blackboard;
	private List<Unit> workers;
	private Player owner;
	
	public BaseManager(BaseLocation location, StarBlackboard blackboard) {
		this.location = location;
		this.blackboard = blackboard;
		this.workers = new ArrayList<Unit>();
		this.buildings = new ArrayList<Unit>();
		
		this.mineralPatches = new ArrayList<MineralPatch>();
		for (Unit patch : location.getMinerals())
			this.mineralPatches.add(new MineralPatch(patch));
		this.maxMineralGatherers = this.mineralPatches.size() * 2;
		this.maxRefineryWorkers = 10;
		if (this.maxMineralGatherers < this.maxRefineryWorkers)
			this.maxRefineryWorkers = this.maxMineralGatherers;
		
		this.gasPatches = new ArrayList<GasPatch>();
		for (Unit patch : location.getGeysers())
			this.gasPatches.add(new GasPatch(patch));
		this.maxGasGatherers = this.gasPatches.size() * 3;
		
		Logger.Debug("BaseMngr:\tInitialized base with " + mineralPatches.size() + "M/" + gasPatches.size() + "G\n", 1);
		int x = location.getTilePosition().x();
		int y = location.getTilePosition().y();
		boolean found = false;
		for (Unit unit : Game.getInstance().unitsOnTile(x, y)) {
			if (unit.getType().equals(UnitType.TERRAN_COMMAND_CENTER)) {
				base = unit;
				found = true;
				break;
			}
		}
		
		if (found) {
			Logger.Debug("BaseMngr:\tWith command center!\n", 1);
			this.owner = Game.getInstance().self();
		}else{
			Logger.Debug("BaseMngr:\tWithout command center...\n", 1);
		}
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean isPlayers() {
		return this.owner != null && this.owner.equals(Game.getInstance().self());
	}
	
	private List<BuildOrder> orders = new ArrayList<BuildOrder>();
	public void addBuildOrder(BuildOrder newOrder){
		if (this.workers.size() == 0) {
			Logger.Debug("BaseMngr:\tNo workers!\n", 1);
			return;
		}
		
		Unit worker = null;
		for (Unit unit : this.workers) {
			boolean free = true;
			for (BuildOrder order : orders)
				if (order.worker.equals(unit))
					free = false;
			if (free) {
				worker = unit;
				break;
			}
		}
		
		if (worker != null) {
			newOrder.worker = worker;
			newOrder.baseManager = this;
			if (!this.orders.contains(newOrder))
				this.orders.add(newOrder);
		}else{
			Logger.Debug("BaseMngr:\tCould not find free worker!\n", 1);
		}
	}
	
	public boolean hasBase() {
		return base != null;
	}
	
	public Position getPosition() {
		return this.location.getPosition();
	}
	
	private TrainingOrder workerOrder;
	private BuildOrder refineryOrder;
	private List<BuildOrder> toRemove = new ArrayList<BuildOrder>();
	public void update() {
		for (MineralPatch mineral : this.mineralPatches)
			mineral.update();
		for (GasPatch gas : this.gasPatches)
			gas.update();
		
		this.toRemove.clear();
		for (BuildOrder order : this.orders) {
			if (order.status == OrderStatus.Ended) {
				for (Unit unit : order.completedUnits) {
					this.buildings.add(unit);
				}
				this.toRemove.add(order);
				goBackToWork(order.worker);
				Logger.Debug("BaseMngr:\tBase has now " + this.buildings.size() + " buildings\n", 3);
			}
		}
		for (BuildOrder order : this.toRemove)
			this.orders.remove(order);
		
		if (this.base == null) return;
		if (isFull()) return;
		Logger.Debug("BaseMngr:\tBase not full\n", 5);
		
		if (this.workers.size() >= this.maxRefineryWorkers && needRefinery()) {
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
			if (this.workerOrder == null) {
				Logger.Debug("BaseMngr:\tTraining new worker\n", 3);
				this.workerOrder = new TrainingOrder(UnitUtils.Type.TERRAN_SCV, 1);
				this.blackboard.trainingQueue.add(this.workerOrder);
			}else{
				if (this.workerOrder.status == OrderStatus.Ended) {
					for (Unit unit : this.workerOrder.units)
						assignToWork(unit);
					this.workerOrder = null;
				}
			}
		}
	}
	
	private boolean needRefinery() {
		for (GasPatch patch : this.gasPatches)
			if (!patch.hasRefinery()) return true;
		
		return false;
	}
	
	private boolean canAssignToGas() {
		if (isGasFull()) return false;
		
		for (GasPatch patch : this.gasPatches)
			if (patch.hasRefinery()) return true;
		
		return false;
	}
	
	public boolean isFull() {
		return isMineralsFull() && isGasFull();
	}
	
	private boolean isMineralsFull() {
		for (MineralPatch patch : this.mineralPatches)
			if (!patch.isFull()) return false;
		
		return true;
	}
	
	private boolean isGasFull() {
		for (GasPatch patch : this.gasPatches)
			if (!patch.isFull()) return false;
		
		return true;
	}
	
	public boolean isAssignedToThisBase(Unit unit) {
		for (MineralPatch patch : this.mineralPatches)
			if (patch.contains(unit))
				return true;
		
		for (GasPatch patch : this.gasPatches)
			if (patch.contains(unit))
				return true;
		
		return false;
	}
	
	private void goBackToWork(Unit unit) {
		for (MineralPatch patch : this.mineralPatches) {
			if (patch.contains(unit)) {
				unit.rightClick(patch.getPatch());
				return;
			}
		}

		for (GasPatch patch : this.gasPatches) {
			if (patch.contains(unit)) {
				unit.rightClick(patch.getPatch());
				return;
			}
		}
		
		Logger.Debug("BaseMngr:\tWorker not in this base!\n", 1);
	}
	
	public void assignToWork(Unit unit) {
		if (isFull()) {
			Logger.Debug("BaseMngr:\tBase Full!\n", 1);
			return;
		}
		
		if (canAssignToGas())
			assignToClosestGas(unit);
		else if (!isMineralsFull())
			assignToClosestMineral(unit);
		else
			Logger.Debug("BaseMngr:\tCould not assign worker!\n", 1);
	}

	private void assignToClosestMineral(Unit unit) {
		if (isMineralsFull()) {
			Logger.Debug("BaseMngr:\tMinerals full!\n", 1);
			return;
		}
		
		MineralPatch patch = null;
		double closest = Double.MAX_VALUE;
		
		for (MineralPatch mineralPatch : this.mineralPatches) {
			if (!mineralPatch.isVisible()) continue;
			if (mineralPatch.isFull()) continue;
			
			double dx = unit.getPosition().x() - mineralPatch.getPosition().x();
			double dy = unit.getPosition().y() - mineralPatch.getPosition().y();
			double dist = Math.sqrt(dx*dx + dy*dy); 

			if (dist < closest) {
				patch = mineralPatch;
				closest = dist;
			}
		}
		
		if (patch != null) {
			Logger.Debug("BaseMngr:\tAssigning " + unit + " to patch " + patch + "\n", 3);
			patch.assignWorker(unit);
			this.workers.add(unit);
		}else{
			Logger.Debug("BaseMngr:\tCould not find mineral patch!\n", 1);
		}
	}

	private void assignToClosestGas(Unit unit) {
		if (isGasFull()) {
			Logger.Debug("BaseMngr:\tGas full!\n", 1);
			return;
		}
		
		GasPatch patch = null;
		double closest = Double.MAX_VALUE;
		
		for (GasPatch gasPatch : this.gasPatches) {
			if (!gasPatch.isVisible()) continue;
			if (gasPatch.isFull()) continue;
			
			double dx = unit.getPosition().x() - gasPatch.getPosition().x();
			double dy = unit.getPosition().y() - gasPatch.getPosition().y();
			double dist = Math.sqrt(dx*dx + dy*dy); 

			if (dist < closest) {
				patch = gasPatch;
				closest = dist;
			}
			
		}
		
		if (patch != null) {
			Logger.Debug("BaseMngr:\tAssigning " + unit + " to patch " + patch + "\n", 3);
			patch.assignWorker(unit);
			this.workers.add(unit);
		}else{
			Logger.Debug("BaseMngr:\tCould not find mineral patch!\n", 1);
		}
		
	}

	public int getBuildingsCount() {
		return this.buildings.size();
	}

	public TilePosition getTilePosition() {
		return this.location.getTilePosition();
	}
}
