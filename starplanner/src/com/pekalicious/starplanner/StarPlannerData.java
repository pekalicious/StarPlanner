package com.pekalicious.starplanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.goals.StrategicGoal;

public class StarPlannerData implements Serializable {
	private static final long serialVersionUID = -1662579210252181698L;
	
	public List<PlannerGoal> buildOrderGoals;
	public List<StarAction> buildOrderActions;
	public List<StrategicGoal> strategicGoals;
	public List<StarAction> strategicActions;
	
	public StarPlannerData() {
		buildOrderGoals = new ArrayList<PlannerGoal>();
		buildOrderActions = new ArrayList<StarAction>();
		strategicGoals = new ArrayList<StrategicGoal>();
		strategicActions = new ArrayList<StarAction>();
	}
}