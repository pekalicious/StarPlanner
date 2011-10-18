package com.pekalicious.starplanner.statemachine;

import com.pekalicious.Logger;

public class StateMachine extends IStateMachineState {
	protected IStateMachineState currentState;
	protected IStateMachineState globalState;
	
	private IStateMachineState transState;
	
	public StateMachine(Object owner, String id) {
		super(owner, id);
	}
	
	public void update() {
		this.transState = null;
		
		if (this.globalState != null) {
			this.globalState.update();
			this.transState = this.globalState.getTransitionState();
		}
		
		if (this.transState != null) {
			setNewState(this.transState);
		} else {
			this.currentState.update();
			this.transState = this.currentState.getTransitionState();
			if (this.transState != null) {
				setNewState(this.transState);
			}
		}
	}
	
	private void setNewState(IStateMachineState newState) {
		Logger.Debug("StateMachine:\tTransitioning out from " + currentState.getId() + "\n", 1);
		currentState.transitionOut();
		currentState = newState;
		Logger.Debug("StateMachine:\tTransitioning in to " + currentState.getId() + "\n", 1);
		currentState.transitionIn();
	}
	
	public void setInitialState(IStateMachineState initialState) {
		if (currentState != null) {
			Logger.Debug("This is not an initial state!!!", 1);
		}
		currentState = initialState;
	}
	
	@Override
	public void transitionOut() {
		currentState.transitionOut();
	}

	@Override
	public void transitionIn() {
		currentState.transitionIn();
	}

	@Override
	public IStateMachineState getTransitionState() {
		if (transitionState != null) return this.transitionState;
		return currentState.getTransitionState();
	}

	public IStateMachineState getCurrentState() {
		return currentState;
	}
}
