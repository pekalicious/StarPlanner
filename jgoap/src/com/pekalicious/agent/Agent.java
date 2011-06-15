package com.pekalicious.agent;

import java.util.ArrayList;
import java.util.List;

import com.pekalicious.goap.PlannerAction;

public abstract class Agent {
	protected WorldState worldState;
	protected List<PlannerAction> actions;
	protected BlackBoard blackBoard;
	protected WorkingMemory workingMemory;
	
	public Agent(BlackBoard blackBoard) {
		this.blackBoard = blackBoard;
		this.worldState = new WorldState();
		this.actions = new ArrayList<PlannerAction>();
		this.workingMemory = new WorkingMemory();
	}
	
	public Agent() {
	    this(null);
	}
	
	public BlackBoard getBlackBoard() {
		return this.blackBoard;
	}
	
	public WorkingMemory getWorkingMemory() {
		return this.workingMemory;
	}

	public void setWorldStateProperty(String key, WorldStateValue<?> value) {
		this.worldState.setProperty(key, value);
	}
	
	public <T> WorldStateValue<T> getWorldStateValue(String key) {
		return this.worldState.<T>getPropertyValue(key);
	}
	
	public boolean containsWorldStateProperty(String key) {
		return worldState.containsKey(key);
	}
	
	public WorldState getWorldState() {
		return this.worldState.clone();
	}
	
	public boolean hasAction(PlannerAction action) {
		return this.actions.contains(action);
	}
	
	public PlannerAction addAction(PlannerAction action) {
		this.actions.add(action);
		return action;
	}
}