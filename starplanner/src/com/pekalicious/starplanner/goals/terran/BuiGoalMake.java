package com.pekalicious.starplanner.goals.terran;

import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.goap.PlannerGoal;

public class BuiGoalMake extends PlannerGoal {
	private static final long serialVersionUID = -2895915858185949058L;

	public void addUnitType(String unitType) {
		this.goalState.setProperty(unitType, new WorldStateValue<Boolean>(true));
	}
	
	public String toString() {
		return "Make";
	}
}
