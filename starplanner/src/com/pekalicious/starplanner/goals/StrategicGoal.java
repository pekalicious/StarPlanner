package com.pekalicious.starplanner.goals;


import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.StarPlanner;

public abstract class StrategicGoal extends PlannerGoal {
	private static final long serialVersionUID = 3353494944665812143L;

	protected double relevancy;
	protected boolean enabled;
	
	public StrategicGoal() {
		this.relevancy = 1.0;
		this.enabled = true;
	}

	public abstract void updateRelevancy(StarPlanner starPlanner);
	
	public double getRelevancy() {
		return this.relevancy;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
}
