package com.pekalicious.goap;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.genericastar.AStarPlan;

public class AStarPlanPlanner extends AStarPlan {
	protected Agent agent;
	public PlannerGoal goal;

	public void activatePlan() {
		Logger.Debug("GOAPPlan:\tActivating plan with " + steps.size() + " steps:" + Logger.toString(steps, ",") + "\n", 1);
		if (steps.size() > 0) {
			AStarPlanStepPlanner step = (AStarPlanStepPlanner)steps.get(0);
			currentStep = 0;
			PlannerAction action = ((AStarNodePlanner) step.node).action;

			if (action == null) return;
			Logger.Debug("GOAPPlan:\tAction:" + action + "\n", 4);
			if (!action.validateContextPreconditions(agent, null, step.worldState, false)) {
				Logger.Debug("GOAPPlan:\tContext Preconditions are not valid!\n", 4);
				return;
			}
			Logger.Debug("GOAPPlan:\tContext Preconditions are valid.\n", 4);

			Logger.Debug("GOAPPlan:\tActivating action: " + action + ".\n", 1);
			action.activateAction(agent, step.worldState);
			if (action.isActionComplete(agent))
				advancePlan();
		}
	}

	public void deactivatePlan() {
		PlannerAction action = getCurrentAction();
		if (action != null)
			action.deactivateAction(agent);
		currentStep = 0;
	}

	@Override
	public boolean isPlanStepComplete() {
		if (super.isPlanComplete()) return true;

		PlannerAction action = getCurrentAction();
		if (action == null)
			Logger.Debug("GOAPPlan:\tAction is null!\n", 1);
		
		return action != null ? action.isActionComplete(agent) : false;
	}

	public boolean advancePlan() {
		Logger.Debug("GOAPPlan:\tAdvancing plan.\n", 2);
		PlannerAction action;
		AStarPlanStepPlanner step;

		while (true) {
			step = (AStarPlanStepPlanner)steps.get(currentStep);

			if (step != null) {
				action = ((AStarNodePlanner) step.node).action;

				if (action != null) {
					action.applyContextEffect(agent, agent.getWorldState(), step.worldState);
					action.deactivateAction(agent);
				}
			}

			currentStep++;
			if (currentStep > steps.size() - 1) {
				Logger.Debug("GOAPPlan:\tNo more steps(" + currentStep + "/" + steps.size() + ")\n", 4);
				currentStep--;
				return false;
			}

			step = (AStarPlanStepPlanner)steps.get(currentStep);

			if (step != null) {
				action = ((AStarNodePlanner)step.node).action;
				Logger.Debug("GOAPPlan:\tAction:" + action + "\n", 4);
				if (action != null) {
					if (!action.validateContextPreconditions(agent, null, step.worldState, false)) {
						Logger.Debug("GOAPPlan:\tContext Preconditions are not valid!\n", 4);
						return false;
					}
					Logger.Debug("GOAPPlan:\tContext Preconditions are valid.\n", 4);

					Logger.Debug("GOAPPlan:\tActivating action: " + action + "\n", 1);
					action.activateAction(agent, step.worldState);
					if (!action.isActionComplete(agent))
						return true;

					action.deactivateAction(agent);
				}
			}
		}
	}
	
	public boolean isPlanValid() {
		return getCurrentAction().validateAction(agent);
	}

	private PlannerAction getCurrentAction() {
		return ((AStarNodePlanner) steps.get(currentStep).node).action;
	}
}