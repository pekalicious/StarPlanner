package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.StarPlanner;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.managers.BuildOrder;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.util.UnitUtils;

public class ActionBuildEngineeringBay extends StarAction implements Serializable {
	private static final long serialVersionUID = -1889179429079965196L;

	BuildOrder order;
	
	@Override
	public void setupConditions() {
		this.preconditions = new WorldState();
		
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.T_ENGINEERINGBAY, new WorldStateValue<Void>());

		this.cost = 1.0f;
		this.precedence = 1;
	}

	@Override
	public void activateAction(Agent aiManager, WorldState state) {
		StarBlackboard bb = (StarBlackboard)((StarPlanner)aiManager).getBlackBoard();
		order = bb.addToBuildQueue(UnitUtils.Type.TERRAN_ENGINEERING_BAY);
	}

	int count;
	@Override
	public boolean isActionComplete(Agent aiManager) {
		//int currentBarracks = ((StarPlanner)aiManager).<Integer>getWorldStateValue(WSKey.T_BARRACKS).getValue();
		return order.status == OrderStatus.Ended;
	}
	
	@Override
	public boolean validateAction(Agent ai) {
		return true;
	}

	@Override
	public boolean validateContextPreconditions(Agent aiManager, WorldState currentState, WorldState goalState, boolean planning) {
		int barracksGoal = goalState.<Integer>getPropertyValue(WSKey.T_ENGINEERINGBAY).getValue();
		this.effects.setProperty(WSKey.T_ENGINEERINGBAY, new WorldStateValue<Integer>(barracksGoal));
		return true;
	}
	
	@Override
	public String toString() {
		return "BuildEngineeringBay";
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
