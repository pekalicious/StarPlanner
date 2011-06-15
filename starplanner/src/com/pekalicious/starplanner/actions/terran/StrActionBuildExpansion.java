package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;

public class StrActionBuildExpansion extends StarAction implements Serializable {
	private static final long serialVersionUID = 7179792376994800039L;

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
		this.effects.setProperty(WSKey.STR_BUILD_EXPASION, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
		this.preconditions.setProperty(WSKey.STR_BRING_WORKER_TO_LOCATION, new WorldStateValue<Boolean>(true));
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
		return "BuildExpansion";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
