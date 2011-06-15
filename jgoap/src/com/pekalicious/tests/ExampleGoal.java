package com.pekalicious.tests;

import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.goap.PlannerGoal;

@SuppressWarnings("serial")
public class ExampleGoal extends PlannerGoal {
    public ExampleGoal() {
        this.goalState.setProperty("EnemyIsDead", new WorldStateValue<Boolean>(true));
    }
}
