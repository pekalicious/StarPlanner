package com.pekalicious.goap;

import java.util.Comparator;

import com.pekalicious.agent.Agent;
import com.pekalicious.genericastar.AStarNode;
import com.pekalicious.genericastar.AStarPlan;
import com.pekalicious.genericastar.AStarStorage;
import com.pekalicious.genericastar.DefaultAStarStorage;

public class GOAPPlanner {
	public AStarGoalPlanner aStarGoal;
	public AStarStorage storage;
	public AStarEnginePlanner aStarEngine;
	public Agent agent;

	public GOAPPlanner(Agent ai, Comparator<AStarNode> comparator) {
		agent = ai;
		storage = new DefaultAStarStorage(comparator);
		aStarEngine = new AStarEnginePlanner(agent);
	}
	
	public GOAPPlanner(Agent ai) {
		agent = ai;
		storage = new DefaultAStarStorage();
		aStarEngine = new AStarEnginePlanner(agent);
	}
	
	public AStarPlan createPlan(AStarGoalPlanner goalPlanner) {
		aStarEngine.initialize(storage, goalPlanner);
		AStarNodePlanner endNode = new AStarNodePlanner();
		endNode.agent = agent;
		AStarPlan plan = aStarEngine.runAStar(endNode);

		return plan;
	}
}