package com.pekalicious.starplanner;

import com.pekalicious.Logger;
import com.pekalicious.goap.AStarPlanPlanner;
import com.pekalicious.goap.GOAPPlanner;
import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.managers.GoalManager;

public class StrategicPlanManager {
	private GOAPPlanner planner;
	private AStarPlanPlanner plan;
	private GoalManager goalManager;
	private StarPlanner agent;
	
	public StrategicPlanManager(StarPlanner agent, GoalManager goalManager) {
		this.agent = agent;
		this.planner = new GOAPPlanner(agent);
		this.goalManager = goalManager;
	}
	
	public void update() {
		if (plan != null) {
			if (plan.isPlanComplete()) {
				plan.deactivatePlan();
				plan = null;
				
				Logger.Debug("StrPlanner:\tPlan Complete\n", 1);
				return;
			}
			
			if (plan.isPlanStepComplete()) {
				if (!plan.advancePlan()) {
					plan.deactivatePlan();
					plan = null;
				}
			}else{
				if (!plan.isPlanValid()) {
					plan.deactivatePlan();
					plan = null;
					Logger.Debug("StrPlanner:\tPlan invalidated.\n", 1);
					/*
					if (!plan.advancePlan()) {
						plan.deactivatePlan();
						plan = null;
					}
					*/
				}
			}
			return;
		}
		
		goalManager.updateGoalRelevancies(this.agent);
		PlannerGoal goal = selectGoal();
		Logger.Debug("StrPlanner:\tGoal Selected = " + goal.toString() + "\n\n", 1);
		generatePlan(selectGoal(), true);
	}
	
	public void generatePlan(PlannerGoal goal, boolean activate) {
		try {
			this.plan = (AStarPlanPlanner) planner.createPlan(new StarGoalPlanner(planner.agent, goal));
			if (this.plan != null && this.plan.Size() > 0) {
				Logger.Debug("StrPlanner:\tCreated plan: " + this.plan.toString() + "\n", 1);
				if (activate)
					this.plan.activatePlan();
			}else{
				Logger.Debug("StrPlanner:\tCould not create a plan.\n",1);
				this.plan = null;
			}
		}catch(Exception e) {
			Logger.Debug("StrManager:\tRuntime exception! " + e.getMessage() + "\n", 1);
		}
	}
	
	private PlannerGoal selectGoal() {
		return goalManager.getMostRelevantGoal(false);
	}
}
