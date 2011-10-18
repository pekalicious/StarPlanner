package com.pekalicious.starplanner.agents.workerStates;

import org.bwapi.bridge.model.Order;
import org.bwapi.bridge.model.TilePosition;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.managers.BuildOrder;
import com.pekalicious.starplanner.managers.ResourceManager;
import com.pekalicious.starplanner.statemachine.StateMachineState;
import com.pekalicious.starplanner.util.LocationServices;

/**
 * This is the state where the worker looks for a location and starts building. 
 * @author Panagiotis Peikidis
 */
public class WorkerBuildBeginState extends StateMachineState {

	/**
	 * The id of this state.
	 */
	public static String WORKER_BUILD_LOCATION = "WORKER_BUILD_LOCATION";
	
	/**
	 * The worker assigned to this state.
	 */
	private Worker worker;
	/**
	 * A reference to the build order of this worker.
	 */
	private BuildOrder order;
	
	/**
	 * Creates the state.
	 * @param owner the worker that owns this state.
	 */
	public WorkerBuildBeginState(Object owner) {
		super(owner, WORKER_BUILD_LOCATION);
		
		this.worker = (Worker)owner;
		this.order = this.worker.getBuildOrder();
	}

	@Override
	public void update() {
		// If the worker has begun building, then he should have a reference to the unit he is building.
		if (this.worker.getUnit().getBuildUnit() != null) {
			Logger.Debug("We are here! Start building!!!\n", 1);
			ResourceManager.Instance.removeFromOrdersQueue(this.worker.getBuildOrder());
			transitionState = new WorkerBuildBuildingState(this.worker);
		}else if (!this.worker.getUnit().getOrder().equals(Order.BUILD_TERRAN)){
			//If he doesn't have a reference, then he hasn't begun building. So, insist on commanding him to start building!
			if (!this.worker.getUnit().build(this.order.buildPosition, this.order.unitType.bwapiType)) {
				//However, if we command him and there is a problem, reset back to the Waiting For Resources state.
				this.transitionState = new WorkerBuildWaitingForResources(this.worker);
			}
		}
	}
	
	@Override
	public void transitionIn() {
		// Look for a position in a spiral fashion around the base of this worker.
		TilePosition buildPosition = null;
		if (order.onUnit == null) {
			Logger.Debug("BuilderMngr:\tLooking for new position: " + order.unitType.bwapiType.getName() + "\n", 3);
			do {
				buildPosition = LocationServices.Instance.getBuildLocationNear(this.worker.getUnit(), 
						this.worker.getBase().getTilePosition(), this.order.unitType.bwapiType);
			}while(buildPosition == null);
		}else{
			Logger.Debug("BuilderMngr:\tPosition: " + order.unitType.bwapiType.getName() 
					+ " on top of " + order.onUnit.getType().getName() + "\n", 3);
			buildPosition = order.onUnit.getTilePosition();
		}
		this.order.buildPosition = buildPosition;
		if (this.order.buildPosition == null) {
			Logger.Debug("FindLocation:\tDidn't find a build location!!!!\n", 1);
		}else{
			Logger.Debug("Location found: " + this.order.buildPosition + "\n" ,1);
		}
	}

}
