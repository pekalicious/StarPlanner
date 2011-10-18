package com.pekalicious.starplanner.agents.workerStates;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.model.GasPatch;
import com.pekalicious.starplanner.model.MineralPatch;
import com.pekalicious.starplanner.statemachine.StateMachineState;
import com.pekalicious.starplanner.util.LocationServices;

/**
 * A State Machine State for a Worker to gather resources
 * @author Panagiotis Peikidis
 *
 */
public class WorkerGatherState extends StateMachineState {

	/**
	 * The state's ID
	 */
	public static final String WORKER_GATHER_STATE_ID = "WORKER_GATHER_STATE";
	
	/**
	 * The worker that is assigned this state
	 */
	private Worker worker;
	
	/**
	 * Creates the state
	 * @param owner the owner of this state
	 */
	public WorkerGatherState(Object owner) {
		super(owner, WORKER_GATHER_STATE_ID);
		this.worker = (Worker)owner;
	}

	@Override
	public void update() {
		if (!worker.hasResourceAssigned()) {
			transitionState = new WorkerIdleState(owner);
		}
		if (worker.hasBuildQueued()) {
			transitionState = new WorkerBuildStateMachine(this.worker);
		}
	}

	@Override
	public void transitionIn() {
		MineralPatch closestMinerals = LocationServices.Instance.findClosestMineralPatch(this.worker);
		if (closestMinerals != null) {
			worker.startGatheringMinerals(closestMinerals);
		}else{
			GasPatch closestGas = LocationServices.Instance.findClosestGasPatch(this.worker);
			if (closestGas != null) {
				worker.startGatheringGas(closestGas);
			}else{
				Logger.Debug("Worker:\tNo resources nearby\n", 1);
				transitionState = new WorkerIdleState(this.worker);
			}
		}
	}
	
	@Override
	public void transitionOut() {
		//If he is not leaving to build, then something else is going on, so leave gathering!
		if (!this.worker.hasBuildQueued()) {
			this.worker.leaveGathering();
		}
	}
	
}
