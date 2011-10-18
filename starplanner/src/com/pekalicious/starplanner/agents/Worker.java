package com.pekalicious.starplanner.agents;

import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.agents.workerStates.WorkerGatherState;
import com.pekalicious.starplanner.agents.workerStates.WorkerIdleState;
import com.pekalicious.starplanner.managers.BaseManager;
import com.pekalicious.starplanner.managers.BuildOrder;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.model.GasPatch;
import com.pekalicious.starplanner.model.ResourcePatch;
import com.pekalicious.starplanner.model.MineralPatch;
import com.pekalicious.starplanner.statemachine.StateMachine;

/**
 * Represents a SCV worker.
 * 
 * @author Panagiotis Peikidis
 */
public class Worker extends Agent {
	
	public static final String WORKER_STATE_MACHINE_ID = "WORKER_STATE_MACHINE";
	
	/**
	 * If the worker has been assigned to build, this is the order.
	 */
	private BuildOrder buildOrder;
	/**
	 * If the worker is gathering resources, this is the resource to gather.
	 */
	private ResourcePatch resourcePatch;
	/**
	 * This is the base this worker is assigned to.
	 */
	private BaseManager base;
	
	/**
	 * Constructs a worker agent.
	 * @param unit the BWAPI unit that this class wraps around
	 * @param base the base of the worker
	 */
	public Worker(Unit unit, BaseManager base) {
		super(unit);
		this.setBase(base);
		
		stateMachine = new StateMachine(this, WORKER_STATE_MACHINE_ID);
		stateMachine.setInitialState(new WorkerIdleState(this));
	}

	/**
	 * Returns the position of this unit.
	 * @return the position of this unit.
	 */
	public Position getPosition() {
		return bwapiUnit.getPosition();
	}
	
	/**
	 * Commands the unit to start gathering minerals.
	 * @param mineralPatch the mineral patch to gather from.
	 */
	public void startGatheringMinerals(MineralPatch mineralPatch) {
		this.resourcePatch = mineralPatch;
		if (this.bwapiUnit.rightClick(this.resourcePatch.getResourceUnit())) {
			mineralPatch.assignWorker(this);
		}
	}
	
	/**
	 * Commands the unit to start gathering gas.
	 * @param gasPatch the gas patch to gather from.
	 */
	public void startGatheringGas(GasPatch gasPatch) {
		this.resourcePatch = gasPatch;
		if (this.bwapiUnit.rightClick(gasPatch.getRefinery())) {
			gasPatch.assignWorker(this);
		}
	}
	
	/**
	 * Take the worker await from gathering resources.
	 */
	public void leaveGathering() {
		if (this.resourcePatch != null) {
			this.resourcePatch.removeWorker(this);
			this.resourcePatch = null;
		}
	}

	public boolean isGathering() {
		return this.stateMachine.getCurrentState().getId().equals(WorkerGatherState.WORKER_GATHER_STATE_ID);
	}
	
	public boolean hasBuildQueued() {
		return buildOrder != null;
	}

	public void startBuilding(BuildOrder newOrder) {
		this.buildOrder = newOrder;
	}

	public BaseManager getBase() {
		return base;
	}

	public void setBase(BaseManager base) {
		this.base = base;
	}

	public boolean hasResourceAssigned() {
		return this.resourcePatch != null;
	}

	public BuildOrder getBuildOrder() {
		return this.buildOrder;
	}

	/**
	 * Clears the build order. Effectively this means that the worker is free to gather resources.
	 */
	public void clearBuildOrder() {
		this.buildOrder = null;
	}

	public boolean hasEndedBuilding() {
		return this.buildOrder.status == OrderStatus.Ended;
	}

}
