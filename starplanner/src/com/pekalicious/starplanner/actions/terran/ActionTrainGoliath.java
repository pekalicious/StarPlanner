package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.StarPlanner;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.managers.TrainingOrder;
import com.pekalicious.starplanner.util.UnitUtils;

public class ActionTrainGoliath extends StarAction implements Serializable {
	private static final long serialVersionUID = -8234233806582355638L;

	TrainingOrder order;

	@Override
	public void setupConditions() {
		this.preconditions = new WorldState();
		this.preconditions.setProperty(WSKey.T_ARMORY, new WorldStateValue<Boolean>(true));
		
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.T_GOLIATH, new WorldStateValue<Boolean>(true));
	}

	@Override
	public void activateAction(Agent aiManager, WorldState state) {
		int count = 12;
		count -= Game.getInstance().self().completedUnitCount(UnitType.TERRAN_GOLIATH);

		if (count > 0) {
			StarBlackboard bb = (StarBlackboard)((StarPlanner)aiManager).getBlackBoard(); 
			order = bb.addToTrainingQueue(UnitUtils.Type.TERRAN_GOLIATH, count);
		}else{
			order = new TrainingOrder();
			order.status = OrderStatus.Ended;
		}
	}

	@Override
	public boolean isActionComplete(Agent aiManager) {
		return order.status == OrderStatus.Ended;
	}

	@Override
	public boolean validateAction(Agent aiManager) {
		return true;
	}
	
	@Override
	public boolean validateContextPreconditions(Agent aiManager, WorldState currentState, WorldState goalState, boolean planning) {
		
		if (planning) {
		}
		
		return true;
	}
	
	public String toString() {
		return "TrainGoliath";
	}

	@Override
	public boolean interrupt() {
		return true;
	}

	@Override
	public boolean canBeDisabled() {
		return true;
	}
	
}
