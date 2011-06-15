package com.pekalicious.starplanner.goals.terran;

import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarPlanner;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.goals.StrategicGoal;

public class StrGoalAttackLocation extends StrategicGoal {
	private static final long serialVersionUID = 4028850700864864840L;

	public StrGoalAttackLocation() {
		this.relevancy = 0.1;
		this.goalState.setProperty(WSKey.STR_ATTACK_LOCATION, new WorldStateValue<Boolean>(true));
	}
	
	public String toString() {
		return "AttackLocation";
	}

	@Override
	public void updateRelevancy(StarPlanner starPlanner) {
		/*
		WorkingMemoryFact[] facts = starPlanner.getWorkingMemory().getFacts(StarMemoryFactType.ENEMY_LOCATION, null);
		if (facts.length > 0)
			this.relevancy = 0.1;
		else
			this.relevancy = 0.5;
		*/
		
	}
}
