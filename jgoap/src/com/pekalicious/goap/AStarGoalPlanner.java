package com.pekalicious.goap;

import java.util.List;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateProperty;
import com.pekalicious.agent.WorldStateValue;
import com.pekalicious.genericastar.AStarGoal;
import com.pekalicious.genericastar.AStarNode;

public abstract class AStarGoalPlanner implements AStarGoal {
	public PlannerGoal goal;
	public Agent agent;

	public AStarGoalPlanner(Agent ai, PlannerGoal aGoal) {
		agent = ai;
		goal = aGoal;
	}

	@Override
	public float getHeuristicDistance(AStarNode fromNode, boolean firstRun) {
		AStarNodePlanner node = (AStarNodePlanner) fromNode;
		if (firstRun) {
			Logger.Debug("GoalPlanner:\tInitializing plan goal.\n", 2);
			node.goalState.resetWorldState();
			goal.setWorldStateSatisfaction(node.goalState);
		} else {
			Logger.Debug("GoalPlanner:\tCurrent State:" + Logger.toString(node.currentState.getProperties()) + "\n", 3);
			Logger.Debug("GoalPlanner:\tGoal State:" + Logger.toString(node.goalState.getProperties()) + "\n", 3);
			PlannerAction action = node.action;

			action.solvePlanWorldStateVariable(node.currentState, node.goalState);
			Logger.Debug("GoalPlanner:\tNew Current State:" + Logger.toString(node.currentState.getProperties()) + "\n", 3);
			Logger.Debug("GoalPlanner:\tNew Goal State:" + Logger.toString(node.goalState.getProperties()) + "\n", 3);
			
			action.setPlanWorldStatePreconditions(node.goalState);
			Logger.Debug("GoalPlanner:\tNew Current State:" + Logger.toString(node.currentState.getProperties()) + "\n", 3);
			Logger.Debug("GoalPlanner:\tNew Goal State:" + Logger.toString(node.goalState.getProperties()) + "\n", 3);
		}

		mergeStates(node.currentState, node.goalState);
		Logger.Debug("GoalPlanner:\tNew Current State:" + Logger.toString(node.currentState.getProperties()) + "\n", 3);
		Logger.Debug("GoalPlanner:\tNew Goal State:" + Logger.toString(node.goalState.getProperties()) + "\n", 3);
		
		return node.goalState.getNumWorldStateDifferences(node.currentState);
	}

	@SuppressWarnings("unchecked")
	private void mergeStates(WorldState currentState, WorldState goalState) {
		Logger.Debug("GoalPlanner:\tMerging states: For every goal state that is not in current, set from agent\n", 3);
		List<WorldStateProperty> props = goalState.getValues();
		for (WorldStateProperty prop : props) {
			if (!currentState.containsKey(prop.key)) {
				if (agent.containsWorldStateProperty(prop.key)) {
					currentState.setProperty(prop.key, agent.getWorldStateValue(prop.key));
				} else {
					currentState.setProperty(prop.key, getDefaultValue(prop.key));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public abstract WorldStateValue getDefaultValue(String key);

	@Override
	public boolean isAStarFinished(AStarNode node) {
		AStarNodePlanner currentNode = (AStarNodePlanner) node;

		if (currentNode.goalState.getNumWorldStateDifferences(currentNode.currentState) != 0) {
			Logger.Debug("GoalPlanner:\tGoal was not reached.\n\n",3);
			return false;
		}

		Logger.Debug("GoalPlanner:\tGoal reached!\n\n",3);
		return true;
	}
	
	public boolean isPlanValid(AStarNode node) {
		Logger.Debug("GoalPlanner:\tValidating plan.\n",3);

		AStarNodePlanner currentNode = (AStarNodePlanner) node;
		WorldState tempState = new WorldState(agent.getWorldState());
		PlannerAction parentNode;

		while (currentNode != null) {
			parentNode = currentNode.action;
			if (parentNode == null) break;
			Logger.Debug("GoalPlanner:\tAction: " + parentNode + "\n", 4);
			
			Logger.Debug("GoalPLanner:\tValidating World State Effects\n", 4);
			if (parentNode.validateWorldStateEffects(tempState)) {
				Logger.Debug("GoalPlanner:\tWorld State Effects not valid!\n", 4);
				return false;
			}
			
			Logger.Debug("GoalPlanner:\tWorld State Effects valid.\n", 4);

			Logger.Debug("GoalPlanner:\tValidating World State Preconditions\n", 4);
			if (!parentNode.validateWorldStatePreconditions(tempState)) {
				Logger.Debug("GoalPlanner:\tWorld State Preconditions not valid!\n", 4);
				return false;
			}
			Logger.Debug("GoalPlanner:\tWorld State Preconditions valid.\n", 4);

			Logger.Debug("GoalPlanner:\tApplying world state effects:" + Logger.toString(parentNode.effects.getProperties()) + "\n", 4);
			parentNode.applyWorldStateEffects(tempState);
			Logger.Debug("GoalPLanner:\tNew tempState: " + tempState + "\n", 4);

			currentNode = (AStarNodePlanner)currentNode.parent;
		}

		boolean isWorldStateSatisfied = goal.isWorldStateSatisfied(tempState);
		if (isWorldStateSatisfied)
			Logger.Debug("GoalPlanner:\tWorldState is satisfied.\n\n", 3);
		else
			Logger.Debug("GoalPlanner:\tWorldState is NOT satisfied!\n\n", 3);
			
		return isWorldStateSatisfied;
	}

	@Override
	public float getCost(AStarNode nodeA, AStarNode nodeB) {
		return ((AStarNodePlanner)nodeB).action.cost;
	}
}