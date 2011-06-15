package com.pekalicious.starplanner.actuators;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.goap.AStarPlanPlanner;
import com.pekalicious.goap.GOAPPlanner;
import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.StarGoalPlanner;

public class BuildPlanManager {
	private GOAPPlanner planner;
	private AStarPlanPlanner plan;
	private StarBlackboard blackboard;
	
	public BuildPlanManager(Agent agent) {
		this.planner = new GOAPPlanner(agent);
		this.blackboard = (StarBlackboard)agent.getBlackBoard();
	}
	
	public void update() {
		if (this.blackboard.buildPlanReplan) {
			Logger.Debug("BuildPlanner:\tReplanning detected!\n", 1);
			PlannerGoal goal = this.blackboard.buildGoal;
			if (goal == null) {
				Logger.Debug("BuildPlanner:\tNo goal for BuildPlanManager!\n", 1);
				this.blackboard.buildPlanReplan = false;
				this.blackboard.buildPlanInvalid = true;
				return;
			}
			Logger.Debug("BuildPlanner:\tCreating plan for " + goal + "\n", 1);
			
			if (plan != null)
				plan.deactivatePlan();
			this.blackboard.buildPlanReplan = false;
			this.blackboard.buildPlanInvalid = false;
			generatePlan(goal, true);
			return;
		}
		
		if (plan != null) {
			if (plan.isPlanComplete()) {
				plan.deactivatePlan();
				plan = null;
				this.blackboard.buildPlanComplete = true;
				this.blackboard.buildPlanReplan = true;
				
				Logger.Debug("BuildPlanner:\tPlan Complete\n", 1);
				return;
			}
			
			if (plan.isPlanStepComplete()) {
				if (!plan.advancePlan()) {
					plan.deactivatePlan();
					plan = null;
					this.blackboard.buildPlanComplete = true;
					this.blackboard.buildPlanReplan = true;
				}
			}else{
				if (!plan.isPlanValid()) {
					plan.deactivatePlan();
					plan = null;
					this.blackboard.buildPlanReplan = true;
					Logger.Debug("BuildPlanner:\tPlan invalidated.\n", 1);
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
	}

	public void generatePlan(PlannerGoal goal, boolean activate) {
		try {
			this.plan = (AStarPlanPlanner) planner.createPlan(new StarGoalPlanner(planner.agent, goal));
			if (this.plan != null && this.plan.Size() > 0) {
				Logger.Debug("BuildPlanner:\tCreated plan: " + this.plan.toString() + "\n\n", 1);
				if (activate) {
					this.plan.activatePlan();
					this.blackboard.buildPlanComplete = false;
				}
			}else{
				Logger.Debug("BuildPlanner:\tCould not create a plan.\n",1);
				this.plan = null;
				this.blackboard.buildPlanInvalid = true;
			}
		}catch(Exception e) {
			Logger.Debug("BuildPlanner:\tException!" + e.getMessage() + "\n", 1);
			this.blackboard.buildPlanInvalid = true;
		}
	}
}
