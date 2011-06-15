package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import org.bwapi.bridge.model.Game;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.StarMemoryFactType;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.managers.SquadOrder;
import com.pekalicious.starplanner.model.Squad;
import com.pekalicious.starplanner.util.UnitUtils;

public class StrActionCreateSquad extends StarAction implements Serializable {
	private static final long serialVersionUID = -6657193165955807057L;
	private Squad squad;

	@Override
	public void activateAction(Agent agent, WorldState state) {
		this.squad = ((StarBlackboard)agent.getBlackBoard()).squad;
		this.squad.order = SquadOrder.Create;
		this.squad.orderStatus = OrderStatus.Idle;
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return this.squad.orderStatus.equals(OrderStatus.Ended);
	}

	@Override
	public void setupConditions() {
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.STR_CREATE_SQUAD, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
	}
	
	@Override
	public boolean validateContextPreconditions(Agent agent, WorldState agentState, WorldState acitonState, boolean planning) {
		if (!planning) return true;
		
		if (agent.getWorkingMemory().getFacts(StarMemoryFactType.NEED_TYPE, null).length > 0) {
			Logger.Debug("CreateSquad:\tFound need facts!\n", 2);
			this.preconditions.setProperty(WSKey.STR_CREATE_UNITS, new WorldStateValue<Boolean>(true));
		}else{
			if (UnitUtils.countBattleUnits(Game.getInstance()) == 0) {
				Logger.Debug("CreateSquad:\tNo battle units!\n", 2);
				this.preconditions.setProperty(WSKey.STR_CREATE_UNITS, new WorldStateValue<Boolean>(true));
			}else{
				Logger.Debug("CreateSquad:\tNo need for units!\n", 2);
			}
		}
		return true;
	}
	
	@Override
	public boolean validateAction(Agent agent) {
		return !this.squad.orderStatus.equals(OrderStatus.Invalid);
	}

	@Override
	public void deactivateAction(Agent agent) {
		squad.orderStatus = OrderStatus.Waiting;
	}
	
	@Override
	public boolean interrupt() {
		return true;
	}
	
	public String toString() {
		return "CreateSquad";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
