package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;

public class StrActionBringWorkerToLocation extends StarAction implements Serializable {
	private static final long serialVersionUID = 8100199606680661125L;

	@Override
	public void activateAction(Agent agent, WorldState state) {
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return false;
	}

	@Override
	public void setupConditions() {
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.STR_BRING_WORKER_TO_LOCATION, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
		this.preconditions.setProperty(WSKey.STR_PROTECT_LOCATION, new WorldStateValue<Boolean>(true));
		this.preconditions.setProperty(WSKey.STR_SELECT_WORKER, new WorldStateValue<Boolean>(true));
	}

	@Override
	public boolean validateAction(Agent agent) {
		return false;
	}

	@Override
	public boolean interrupt() {
		return true;
	}
	
	public String toString() {
		return "BringWorkerToLocation";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
