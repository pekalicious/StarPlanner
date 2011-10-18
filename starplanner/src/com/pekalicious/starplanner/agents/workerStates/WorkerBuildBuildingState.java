package com.pekalicious.starplanner.agents.workerStates;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.managers.BuildOrder;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.statemachine.StateMachineState;

public class WorkerBuildBuildingState extends StateMachineState {

	public static String WORKER_BUILD_START = "WORKER_BUILD_START";
	
	private Worker worker;
	private BuildOrder order;
	
	public WorkerBuildBuildingState(Object owner) {
		super(owner, WORKER_BUILD_START);
		this.worker = (Worker)owner;
		this.order = this.worker.getBuildOrder();
	}

	@Override
	public void update() {
		if (this.worker.getBuildOrder().buildUnit == null) {
			this.worker.getBuildOrder().buildUnit = this.worker.getUnit().getBuildUnit();
		}else{
			if (this.order.buildUnit.isCompleted()) {
				Logger.Debug("BUILDING COMPLETE! :D\n", 1);
				this.worker.getBuildOrder().status = OrderStatus.Ended;
			}
		}
	}
	
	@Override
	public void transitionOut() {
		this.worker.clearBuildOrder();
	}
}
