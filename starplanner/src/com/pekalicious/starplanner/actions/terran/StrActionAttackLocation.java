package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.managers.SquadOrder;
import com.pekalicious.starplanner.model.Squad;

public class StrActionAttackLocation extends StarAction implements Serializable {
	private static final long serialVersionUID = 8620447313607327372L;
	private Squad squad;
	
	@Override
	public void activateAction(Agent agent, WorldState state) {
		this.squad = ((StarBlackboard)agent.getBlackBoard()).squad;
		this.squad.order = SquadOrder.AttackNearbyEnemies;
		this.squad.orderStatus = OrderStatus.Idle;
	}

	@Override
	public boolean interrupt() {
		return true;
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return this.squad.orderStatus.equals(OrderStatus.Ended);
	}

	@Override
	public void setupConditions() {
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.STR_ATTACK_LOCATION, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
		this.preconditions.setProperty(WSKey.STR_BRING_SQUAD_TO_LOCATION, new WorldStateValue<Boolean>(true));
	}

	@Override
	public boolean validateContextPreconditions(Agent agent, WorldState agentState, WorldState acitonState, boolean planning) {
		if (!planning) {
			//return ((StarBlackboard)agent.getBlackBoard()).squad.enemyNearDestination;
		}
		return true;
	}
	
	@Override
	public boolean validateAction(Agent agent) {
		return !this.squad.orderStatus.equals(OrderStatus.Invalid);
	}
	
	public String toString() {
		return "AttackLocation";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
