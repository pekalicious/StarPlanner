package com.pekalicious.starplanner;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.goap.AStarGoalPlanner;
import com.pekalicious.goap.PlannerGoal;

public class StarGoalPlanner extends AStarGoalPlanner {

	public StarGoalPlanner(Agent ai, PlannerGoal aGoal) {
		super(ai, aGoal);
	}

	@Override
	public WorldStateValue<?> getDefaultValue(String key) {
		return new WorldStateValue<Boolean>(false);
	}

}
