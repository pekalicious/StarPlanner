package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;

public class StrActionSelectWorker extends StarAction implements Serializable {
	private static final long serialVersionUID = 6874349751666491905L;

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
		this.effects.setProperty(WSKey.STR_SELECT_WORKER, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
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
		return "SelectWorker";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
