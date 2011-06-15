package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;

public class StrActionProtectLocation extends StarAction implements Serializable {
	private static final long serialVersionUID = -1214923529797058043L;

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
		this.effects.setProperty(WSKey.STR_PROTECT_LOCATION, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
		this.preconditions.setProperty(WSKey.STR_BRING_SQUAD_TO_LOCATION, new WorldStateValue<Boolean>(true));
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
		return "ProtectLocation";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
