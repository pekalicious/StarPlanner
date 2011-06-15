package com.pekalicious.starplanner.goals.terran;

import org.bwapi.bridge.model.Game;

import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.starplanner.StarPlanner;
import com.pekalicious.starplanner.WSKey;
import com.pekalicious.starplanner.goals.StrategicGoal;

public class StrGoalBuildExpansion extends StrategicGoal {
	private static final long serialVersionUID = -3572313678593604305L;

	public StrGoalBuildExpansion() {
		this.goalState.setProperty(WSKey.STR_BUILD_EXPASION, new WorldStateValue<Boolean>(true));
	}
	
	public String toString() {
		return "BuildExpansion";
	}

	@Override
	public void updateRelevancy(StarPlanner starPlanner) {
		if (Game.getInstance().self().minerals() >= 600) {
			this.relevancy = 0.5;
		}else{
			this.relevancy = 1.0;
		}
	}

}
