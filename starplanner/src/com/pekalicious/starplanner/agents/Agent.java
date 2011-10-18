package com.pekalicious.starplanner.agents;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.statemachine.StateMachine;

public abstract class Agent {
	protected StateMachine stateMachine;
	protected Unit bwapiUnit;
	
	public Agent(Unit unit) {
		this.bwapiUnit = unit;
	}
	
	public void update() {
		stateMachine.update();
	}
	
	public Unit getUnit() {
		return this.bwapiUnit;
	}
}
