package com.pekalicious.starplanner.agents.workerStates;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.managers.ResourceManager;
import com.pekalicious.starplanner.statemachine.StateMachineState;

/**
 * In this state the worker gathers resources until there are enough to
 * carry out his build order.
 * 
 * @author Panagiotis Peikidis
 */
public class WorkerBuildWaitingForResources extends StateMachineState {

	public static String WORKER_BUILD_RESOURCE = "WORKER_BUILD_RESOURCE";
	
	private Worker worker;
	private int mineralsNeeded;
	private int gasNeeded;
	
	public WorkerBuildWaitingForResources(Object owner) {
		super(owner, WORKER_BUILD_RESOURCE);
		this.worker = (Worker)owner;
	}

	@Override
	public void update() {
		int currentMinerals = ResourceManager.Instance.getFreeMinerals();
		int currentGas = ResourceManager.Instance.getFreeGas();
		if (currentMinerals >= this.mineralsNeeded && currentGas >= this.gasNeeded) {
			Logger.Debug("Waiting:\tEnough resources.. Look for location...\n", 1);
			this.worker.getBuildOrder().status = OrderStatus.Started;
			this.worker.leaveGathering();
			ResourceManager.Instance.addToOrdersQueue(this.worker.getBuildOrder());
			transitionState = new WorkerBuildBeginState(this.worker);
		}
	}

	@Override
	public void transitionIn() {
		this.mineralsNeeded = this.worker.getBuildOrder().unitType.bwapiType.mineralPrice();
		this.gasNeeded = this.worker.getBuildOrder().unitType.bwapiType.gasPrice();
	}

}
