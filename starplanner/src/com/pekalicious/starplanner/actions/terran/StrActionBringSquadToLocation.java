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

public class StrActionBringSquadToLocation extends StarAction implements Serializable {
	private static final long serialVersionUID = 5918203758307568178L;
	private Squad squad;
	private boolean isValid = true;

	@Override
	public void activateAction(Agent agent, WorldState state) {
		this.squad = ((StarBlackboard)agent.getBlackBoard()).squad;
		Position destination = null;
		
		WorkingMemoryFact[] locationFacts = agent.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_LOCATION, null);
		if (locationFacts.length > 0) {
			Logger.Debug("BringToLoc:\tSelected Enemy Location\n", 3);
			destination = ((StarMemoryFact)locationFacts[0]).enemyLocation;
		}else{
			//TODO: Sort by confidence
			WorkingMemoryFact[] facts = agent.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_BASE, null);
			if (facts.length > 0) {
				Logger.Debug("BringToLoc:\tSelected Base\n", 3);
				destination = ((StarMemoryFact)facts[0]).basePosition;
			}
		}
		if (destination != null) {
			Position pos = destination;
			squad.destination = pos;
			squad.order = SquadOrder.GoTo;
			squad.orderStatus = OrderStatus.Idle;
		}else{
			Logger.Debug("BringToLoc:\tNo location to attack!\n", 1);
			isValid = false;
		}
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return squad.orderStatus.equals(OrderStatus.Ended);
	}

	@Override
	public void setupConditions() {
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.STR_BRING_SQUAD_TO_LOCATION, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
		
		this.cost = 0.1f;
	}

	@Override
	public boolean validateContextPreconditions(Agent agent, WorldState agentState, WorldState acitonState, boolean planning) {
		if (planning) {
			Squad squad = ((StarBlackboard)agent.getBlackBoard()).squad;
			if (squad == null || squad.units.size() == 0) {
				this.preconditions.setProperty(WSKey.STR_CREATE_SQUAD, new WorldStateValue<Boolean>(true));
			}
			
			if (agent.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_LOCATION, null).length > 0) return true;
			Logger.Debug("BringToLoc:\tNo enemy locations!\n", 2);
			WorkingMemoryFact[] facts = agent.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_BASE, null);
			Position foundPosition = null;
			for (WorkingMemoryFact fact : facts) {
				StarMemoryFact starFact = (StarMemoryFact)fact;
				if (starFact.confidence > 0.9) {
					foundPosition = starFact.basePosition;
					break;
				}
			}
			
			if (foundPosition == null) return false;
			
			return true;
		}
		
		return true;
	}
	
	@Override
	public boolean validateAction(Agent agent) {
		return !squad.orderStatus.equals(OrderStatus.Invalid) && isValid;
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
		return "BringSquadToLocation";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
