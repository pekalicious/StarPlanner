package com.pekalicious.starplanner.agents.workerStates;

import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.statemachine.StateMachineState;

public class WorkerIdleState extends StateMachineState {

	public static final String WORKER_IDLE_STATE_ID = "WORKER_IDLE_STATE";
	
	private Worker worker;
	
	public WorkerIdleState(Object owner) {
		super(owner, WORKER_IDLE_STATE_ID);
		this.worker = (Worker)owner;
	}

	@Override
	public void update() {
		if (this.worker.hasBuildQueued()) {
			this.transitionState = new WorkerBuildStateMachine(this.worker);
		}else if (!this.worker.isGathering()) {
			transitionState = new WorkerGatherState(owner);
		}
	}
}
