package com.pekalicious.tests;

import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.goap.PlannerAction;

public class ExampleAction extends PlannerAction {
	private static final long serialVersionUID = -7642659782503805397L;

	@Override
    public void setupConditions() {
        this.precedence = 1;
        this.cost = 2;

        this.preconditions = new WorldState();
        this.preconditions.setProperty("IsWeaponLoaded",
                new WorldStateValue<Boolean>(true));

        this.effects = new WorldState();
        this.effects.setProperty("EnemyIsDead", 
                new WorldStateValue<Boolean>(true));
    }

	@Override
	public void activateAction(Agent agent, WorldState state) {
	}

	@Override
	public boolean isActionComplete(Agent agent) {
		return false;
	}

	@Override
	public boolean validateAction(Agent agent) {
		return false;
	}

	@Override
	public boolean interrupt() {
		return true;
	}

}
