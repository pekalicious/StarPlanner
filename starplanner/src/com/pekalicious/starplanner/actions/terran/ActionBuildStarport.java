package com.pekalicious.starplanner.actions.terran;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.starplanner.actions.StarAction;

public class ActionBuildStarport extends StarAction {
    private static final long serialVersionUID = 1390895072138487772L;

    @Override
	public void activateAction(Agent agent, WorldState state) {
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return false;
	}

	@Override
	public void setupConditions() {
		this.preconditions = new WorldState();
		
		this.effects = new WorldState();
	}

	@Override
	public boolean validateAction(Agent agent) {
		return false;
	}

	@Override
	public boolean validateContextPreconditions(Agent agent,
			WorldState currentState, WorldState goalState, boolean planning) {
		return false;
	}

	public String toString() {
		return "BuildStarport";
	}

	@Override
	public boolean interrupt() {
		return true;
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}
}
