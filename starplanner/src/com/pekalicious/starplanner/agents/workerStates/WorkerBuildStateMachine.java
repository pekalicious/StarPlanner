package com.pekalicious.starplanner.agents.workerStates;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.statemachine.StateMachine;

public class WorkerBuildStateMachine extends StateMachine {

	public static String WORKER_BUILD_STATE_MACHINE = "WORKER_BUILD_STATE_MACHINE";
	
	private Worker worker;
	
	public WorkerBuildStateMachine(Object owner) {
		super(owner, WORKER_BUILD_STATE_MACHINE);
		this.worker = (Worker)owner;
		this.currentState = new WorkerBuildWaitingForResources(this.worker);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (this.worker.hasBuildQueued() && this.worker.hasEndedBuilding()) {
			Logger.Debug("Completion detected. Transitioning out...\n", 1);
			transitionState = new WorkerIdleState(this.worker);
		}
	}
	
}
