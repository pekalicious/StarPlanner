package com.pekalicious.goap;

import com.pekalicious.agent.Agent;
import com.pekalicious.genericastar.AStarEngine;
import com.pekalicious.genericastar.AStarNode;
import com.pekalicious.genericastar.AStarPlan;

public class AStarEnginePlanner extends AStarEngine {
	private Agent aiManager;

	public AStarEnginePlanner(Agent agent) {
		aiManager = agent;
	}
	
	@Override
	protected AStarPlan createPlan(AStarNode lastNode) {
		if (!((AStarGoalPlanner)this.goal).isPlanValid(lastNode)) return null;
		
		AStarNode currentNode = lastNode;

		if (currentNode == null) return null;

		AStarPlanPlanner plan = new AStarPlanPlanner();
		plan.agent = aiManager;
		AStarPlanStepPlanner step;
		PlannerAction action;
		while (true) {
			if (currentNode.parent == null) break;

			step = new AStarPlanStepPlanner();
			action = ((AStarNodePlanner)currentNode).action;

			if (action == null) return null;

			step.node = currentNode;
			step.worldState = ((AStarNodePlanner)currentNode).goalState;

			plan.addStep(step);

			currentNode = currentNode.parent;
		}

		plan.goal = ((AStarGoalPlanner)this.goal).goal; 
		
		return plan;
	}
}