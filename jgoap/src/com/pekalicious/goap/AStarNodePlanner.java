package com.pekalicious.goap;

import java.util.ArrayList;
import java.util.List;

import com.pekalicious.Logger;
import com.pekalicious.agent.Agent;
import com.pekalicious.agent.WorldState;
import com.pekalicious.agent.WorldStateProperty;
import com.pekalicious.genericastar.AStarNode;

class AStarNodePlanner extends AStarNode {
	WorldState currentState;
	WorldState goalState;
	PlannerAction action;
	public Agent agent;

	private List<AStarNode> neighbors;

	public AStarNodePlanner() {
		neighbors = new ArrayList<AStarNode>();
		currentState = new WorldState();
		goalState = new WorldState();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AStarNode> getNeighbors() {
		neighbors.clear();

		Logger.Debug("NodePlanner:\tLooking for neihbors...\n", 4);
		Logger.Debug("NodePlanner:\tCurrentState:" + Logger.toString(currentState.getProperties()) + "\n", 4);
		Logger.Debug("NodePlanner:\tGoalState:" + Logger.toString(goalState.getProperties()) + "\n", 4);
		
		//Map<Class, PlannerAction> tmpMap = new HashMap<Class, PlannerAction>();
		List<WorldStateProperty> currProps = currentState.getValues();
		for (WorldStateProperty currProp : currProps) {  
			if (currProp.value.equals(goalState.getPropertyValue(currProp.key))) continue;
			
			Logger.Debug("NodePlanner:\tLooking for actions that can solve the symbol " + currProp.key + "...\n", 4);
			List<PlannerAction> actionsFromManager = ActionManager.Instance.getActionsByKey(currProp.key);
			/*
			for (PlannerAction act : actionsFromManager)
				tmpMap.put(act.getClass(), act);
			*/
			
			Logger.Debug("NodePlanner:\tFound " + actionsFromManager.size() + " valid action(s):" + Logger.toString(actionsFromManager) + "\n", 4);
			for (PlannerAction action : actionsFromManager) {
		//Collection<PlannerAction> actions = tmpMap.values();
		//Logger.Debug("NodePlanner:\t" + actions.size() + " unique action(s):" + Logger.toString(actions) + "\n", 4);
		//for (PlannerAction action : actions) {
		//boolean validAction = true;
				if (!agent.hasAction(action)) continue;
				Logger.Debug("NodePlanner:\tValidating action " + action + "...\n", 3);
				Logger.Debug("NodePlanner:\tValidating context preconditions...\n", 4);
				boolean context = action.validateContextPreconditions(agent, currentState, goalState, true);
				Logger.Debug(context ? "NodePlanner:\tValid context preconditions.\n" : "NodePlanner:\tNot valid context preconditions.\n", 4);
				if (Logger.DEBUG_LEVEL == 3) Logger.Debug(context ? "" : "NodePlanner:\tAction is not valid.\n", 3);
			
				if (!context) continue;
			
				Logger.Debug("NodePlanner:\tNew Effects:" + Logger.toString(action.effects.getProperties()) + "\n", 4);
				Logger.Debug("NodePlanner:\tNew Preconditions:" + Logger.toString(action.preconditions.getProperties()) + "\n", 4);
			
			//for (WorldStateProperty currProp : currProps) {  
				//if (currProp.value.equals(goalState.getPropertyValue(currProp.key))) continue;
				//if (!action.effects.containsKey(currProp.key)) continue;
				//boolean containsKey = action.effects.containsKey(currProp.key);
				//if (Logger.DEBUG_LEVEL > 3)
					//Logger.Debug(containsKey ? "" : "NodePlanner:\tAction does not contain key " + currProp.key + "\n", 3);
				//Logger.Debug(containsKey ? "" : "NodePlanner:\tAction is not valid.\n", 3);
				//if (!containsKey) {
					//validAction = false; 
					//break; 
				//}
				Logger.Debug("NodePlanner:\tAction contains effect " + currProp.key + " = " + action.effects.getPropertyValue(currProp.key).getValue() + "\n", 3);
				Logger.Debug("NodePlanner:\tGoal state needs " + currProp.key + " = " + goalState.getPropertyValue(currProp.key).getValue() + "\n", 3);
				boolean correctValue = action.effects.getPropertyValue(currProp.key).equals((goalState.getPropertyValue(currProp.key)));
				Logger.Debug(correctValue ? "NodePlanner:\tAction is valid.\n" : "NodePlanner:\tAction is not valid.\n", 3);
				if (!correctValue) continue;
					//validAction = false;
					//break;
				//}
			//}
			//if (validAction) {
				AStarNodePlanner newNode = new AStarNodePlanner();
				newNode.currentState.copyWorldState(currentState);
				newNode.goalState.copyWorldState(goalState);
				newNode.action = action.clone();
				newNode.agent = agent;
				Logger.Debug("NodePlanner:\tNew Node created.\n", 3);
	
				/*
				Logger.Debug("NodePlanner:\tAdd all preconditions that are not in goal state.\n", 3);
				List<WorldStateProperty> preconds = action.preconditions.getValues();
				for (WorldStateProperty precond : preconds)
					if (!newNode.goalState.containsKey(precond.key))
						newNode.goalState.setProperty(precond.key, (WorldStateValue)precond.value.clone());
	
				Logger.Debug("NodePlanner:\tNew Node Current State:" + Logger.toString(newNode.currentState.getProperties(), ",") + "\n", 3);
				Logger.Debug("NodePlanner:\tNew Node Goal State:" + Logger.toString(newNode.goalState.getProperties(), ",") + "\n", 3);
				*/
				
				neighbors.add(newNode);
			}
		}

		Logger.Debug("NodePlanner:\tReturning " + neighbors.size() + " neighbor(s):" + Logger.toString(neighbors, ",") + "\n\n", 3);
		
		return neighbors;
	}
	
	public String toString() {
		return action == null ? super.toString() : action.toString();
	}

}