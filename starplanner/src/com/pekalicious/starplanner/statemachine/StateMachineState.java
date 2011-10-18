package com.pekalicious.starplanner.statemachine;

public abstract class StateMachineState extends IStateMachineState {
	public StateMachineState(Object owner, String id) {
		super(owner, id);
	}
}
