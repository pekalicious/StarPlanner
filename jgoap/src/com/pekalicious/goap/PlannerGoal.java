package com.pekalicious.goap;

import java.io.Serializable;

import com.pekalicious.Logger;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateProperty;

public class PlannerGoal implements Serializable {
	private static final long serialVersionUID = -8320863464676810350L;

	protected WorldState goalState;

	public PlannerGoal() {
		goalState = new WorldState();
	}

	@SuppressWarnings("unchecked")
	public void setWorldStateSatisfaction(WorldState worldState) throws GoapError {
		if (goalState.getValues().size() == 0)
			throw new GoapError("No symbols in goal " + toString());

		for (WorldStateProperty property : goalState.getValues())
			worldState.setProperty(property.key, property.value);
	}

	@SuppressWarnings("unchecked")
	public boolean isWorldStateSatisfied(WorldState otherState) {
		Logger.Debug("PlannerGoal:\tComparing goal state:" + goalState + "\n", 3);
		Logger.Debug("PlannerGoal:\tComparing other state:" + otherState + "\n", 3);
		for (WorldStateProperty property : goalState.getValues()) {
			if (!otherState.containsKey(property.key)) continue;
			if (!otherState.getPropertyValue(property.key).equals(property.value)) return false;
		}

		return true;
	}
}