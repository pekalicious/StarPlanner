package com.pekalicious.starplanner.actions;

import com.pekalicious.goap.PlannerAction;

public abstract class StarAction extends PlannerAction {
	private static final long serialVersionUID = 121589307735431875L;

	public abstract boolean canBeDisabled();
}
