package com.pekalicious.goap;

import java.io.Serializable;
import java.util.List;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateProperty;
import com.pekalicious.agent.WorldStateValue;

public abstract class PlannerAction implements Cloneable, Serializable {
	private static final long serialVersionUID = 3698931676619422759L;

	public WorldState preconditions;
	public WorldState effects;
	public float cost;
	public int precedence;
	
	public abstract void setupConditions();
	public abstract void activateAction(Agent agent, WorldState state);
	public abstract boolean validateAction(Agent agent);
	public abstract boolean isActionComplete(Agent agent);
	public abstract boolean interrupt();
	
	public PlannerAction clone() {
		PlannerAction clone = null;
		try {
			clone = (PlannerAction)super.clone();
			clone.preconditions = new WorldState();
			clone.preconditions.copyWorldState(preconditions);
			clone.effects = new WorldState();
			clone.effects.copyWorldState(effects);
			clone.cost = cost;
			clone.precedence = precedence;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return clone;
	}
	
	public PlannerAction() {
		setupConditions();
	}
	
	@SuppressWarnings("unchecked")
	public void solvePlanWorldStateVariable(WorldState currentState, WorldState goalState) {
		Logger.Debug("PlannerAction:\tFor every effect that is in goal state, set current state from goal state.\n", 3);
		List<WorldStateProperty> effs = effects.getValues();
		for (WorldStateProperty effect : effs)
			currentState.setProperty(effect.key, (WorldStateValue)effect.value.clone());
	}

	@SuppressWarnings("unchecked")
	public void setPlanWorldStatePreconditions(WorldState goalState) {
		Logger.Debug("PlannerAction:\tApply all preconditions to goal state.\n", 3);
		List<WorldStateProperty> preconds = preconditions.getValues();
		for (WorldStateProperty precond : preconds)
			goalState.setProperty(precond.key, (WorldStateValue)precond.value.clone());
	}

	public boolean validateWorldStateEffects(WorldState otherState) {
		Logger.Debug("PlannerAction:\tEffects: " + effects + "\n", 4);
		Logger.Debug("PlannerAction:\tOtherState: " + otherState + "\n", 4);
		return effects.getNumUnsatisfiedWorldStateProperties(otherState) == 0;
	}

	public boolean validateWorldStatePreconditions(WorldState otherState) {
		Logger.Debug("PlannerAction:\tPreconditions: " + preconditions + "\n", 4);
		Logger.Debug("PlannerAction:\tOtherState: " + otherState + "\n", 4);
		return preconditions.getNumUnsatisfiedWorldStateProperties(otherState) == 0;
	}

	@SuppressWarnings("unchecked")
	public void applyWorldStateEffects(WorldState currentState) {
		List<WorldStateProperty> effs = effects.getValues();
		for (WorldStateProperty effect : effs)
			currentState.setProperty(effect.key, effect.value);
	}

	public void deactivateAction(Agent agent) { 

	}
	
	public void applyContextEffect(Agent agent, WorldState state, WorldState worldState) { 
		
	}

	public boolean validateContextPreconditions(Agent agent, WorldState agentState, WorldState acitonState, boolean planning) {
		return true;
	}
}