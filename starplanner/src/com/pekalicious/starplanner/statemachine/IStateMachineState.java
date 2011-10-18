package com.pekalicious.starplanner.statemachine;

/**
 * State Machines are implementated using the Composite pattern. At the very top level there
 * is the IStateMachineState interface that provides the basic functionality of any state. From this
 * interface, the StateMachineState is a leaf node that simply implements a state in the state
 * machine while the StateMachine is a composite nodes that contains more IStateMachine (that
 * can be composite state machines as well). Thus, you can create a state machine as many levels
 * down as required. 
 * 
 * I.e. a Worker has a Gather State which is a leaf state that simply gathers resources. It also
 * has a Build state machine that is a composite of many steps to build a building (Wait For Resources
 * State, Find Location State, Build State, etc.)
 * 
 * @author Panagiotis Peikidis
 */
public abstract class IStateMachineState {
	/**
	 * 
	 */
	protected Object owner;
	
	/**
	 * The id of the state
	 */
	protected String id;
	
	/**
	 * The next state to transition. Null means it will not transition.
	 */
	protected IStateMachineState transitionState;
	
	/**
	 * Creates an instance of a state.
	 * @param owner the owner of this state
	 * @param id the id of the state
	 */
	public IStateMachineState(Object owner, String id) {
		this.owner = owner;
		this.id = id;
	}
	
	/**
	 * Update method of the state
	 */
	public abstract void update();
	
	/**
	 * When this state is activated, this is called to set up
	 */
	public void transitionOut() {}
	
	/**
	 * When this state is being transitioned out, this method is called for cleanup.
	 */
	public void transitionIn() {}
	
	/**
	 * Get the next state to transition. Null will not transition.
	 * @return
	 */
	public IStateMachineState getTransitionState() { return this.transitionState; }
	
	/**
	 * Returns the owner of this state
	 * @return the owner of this state
	 */
	public Object getOwner() { return this.owner; }
	
	/**
	 * Returns the id of this state
	 * @return the id of this state
	 */
	public String getId() { return this.id; }
}
