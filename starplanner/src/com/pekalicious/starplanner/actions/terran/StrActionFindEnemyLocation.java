package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import org.bwapi.bridge.model.Position;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorkingMemoryFact;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.StarMemoryFact;
import com.pekalicious.starplanner.StarMemoryFactType;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.managers.SquadOrder;
import com.pekalicious.starplanner.model.Squad;

public class StrActionFindEnemyLocation extends StarAction implements Serializable {
	private static final long serialVersionUID = -8440842433550866101L;
	private Squad squad;
	private WorkingMemoryFact[] baseLocations;
	private int baseIndex;
	private boolean completed = false;

	public void activateAction(Agent agent, WorldState state) {
		this.squad = ((StarBlackboard)agent.getBlackBoard()).squad;
		//TODO: Sort by confidence
		baseLocations = agent.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_BASE, null);
		if (baseLocations.length > 0) {
			Logger.Debug("FindBase:\tFound " + baseLocations.length + " base locations\n", 3);
			baseIndex = 0;
			setNextBase();
		}else{
			Logger.Debug("FindBase:\tNo enemy base facts found!\n", 1);
		}
	}
	
	private void setNextBase() {
		Position pos = ((StarMemoryFact)baseLocations[baseIndex]).basePosition;
		squad.destination = pos;
		squad.order = SquadOrder.GoTo;
		squad.orderStatus = OrderStatus.Idle;
		baseIndex++;
	}

	@Override
	public boolean interrupt() {
		return true;
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return this.completed;
	}

	@Override
	public void setupConditions() {
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.STR_BRING_SQUAD_TO_LOCATION, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
		this.preconditions.setProperty(WSKey.STR_CREATE_SQUAD, new WorldStateValue<Boolean>(true));
		
		this.cost = 0.5f;
	}

	@Override
	public boolean validateAction(Agent agent) {
		if (squad.orderStatus.equals(OrderStatus.Next)) {
			if (baseIndex == baseLocations.length) {
				Logger.Debug("FindBase:\tNo more bases to search!\n", 1);
				return false;
			}else{
				setNextBase();
				return true;
			}
		}else if (squad.orderStatus.equals(OrderStatus.Ended)) {
			if (squad.enemyNearDestination) { 
				this.completed = true;
			}else{
				if (baseIndex == baseLocations.length) {
					Logger.Debug("FindBase:\tNo more bases to search!\n", 1);
					return false;
				}else{
					setNextBase();
				}
			}
			return true;
		}else if (squad.orderStatus.equals(OrderStatus.Invalid)) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public boolean validateContextPreconditions(Agent agent, WorldState agentState, WorldState acitonState, boolean planning) {
		((StarBlackboard)agent.getBlackBoard()).squadIgnoreUnitDistance = true;
		if (!planning) return true;
		if (agent.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_LOCATION, null).length > 0) return false;
		
		return true;
	}
	
	public String toString() {
		return "FindEnemyLocation";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
