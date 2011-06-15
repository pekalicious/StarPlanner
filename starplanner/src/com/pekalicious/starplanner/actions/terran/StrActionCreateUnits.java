package com.pekalicious.starplanner.actions.terran;

import java.io.Serializable;

import org.bwapi.bridge.model.UnitType;

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
import com.pekalicious.starplanner.goals.terran.BuiGoalMake;

public class StrActionCreateUnits extends StarAction implements Serializable {
	private static final long serialVersionUID = -2870734191761983261L;
	private StarBlackboard blackBoard;

	@Override
	public void activateAction(Agent agent, WorldState state) {
		this.blackBoard = (StarBlackboard) agent.getBlackBoard();
		
		BuiGoalMake make = new BuiGoalMake();
		WorkingMemoryFact[] needFacts = agent.getWorkingMemory().getFacts(StarMemoryFactType.NEED_TYPE, null);
		if (needFacts.length > 0) {
			for (WorkingMemoryFact fact : needFacts) {
				StarMemoryFact starFact = (StarMemoryFact)fact;
				if (starFact.needTypeCount > 0) {
					Logger.Debug("CreateUnit:\tWe need " + starFact.needType.getName() + "\n", 1);
					make.addUnitType(starFact.needType.getName());
				}else{
					Logger.Debug("CreateUnit:\tNeed " + starFact.needType.getName() + " with no count!\n", 1);
				}
			}
		}else{
			make.addUnitType(UnitType.TERRAN_MARINE.getName());
		}
		
		this.blackBoard.buildGoal = make;
		this.blackBoard.buildPlanComplete = false;
		this.blackBoard.buildPlanReplan = true;
		this.blackBoard.buildPlanInvalid = false;
	}

	@Override
	public boolean interrupt() {
		return true;
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return blackBoard.buildPlanComplete;
	}

	@Override
	public void setupConditions() {
		this.effects = new WorldState();
		this.effects.setProperty(WSKey.STR_CREATE_UNITS, new WorldStateValue<Boolean>(true));
		
		this.preconditions = new WorldState();
	}

	@Override
	public boolean validateAction(Agent agent) {
		return !this.blackBoard.buildPlanInvalid;
	}
	
	public String toString() {
		return "CreateUnits";
	}

	@Override
	public boolean canBeDisabled() {
		return false;
	}

}
