package com.pekalicious.genericastar;

public interface AStarGoal {
	public float getHeuristicDistance(AStarNode fromNode, boolean firstRun);
	public boolean isAStarFinished(AStarNode node);
	public float getCost(AStarNode nodeA, AStarNode nodeB);
}