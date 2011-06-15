package com.pekalicious.genericastar;

import java.util.List;

import com.pekalicious.Logger;

public abstract class AStarEngine {
	private AStarStorage storage;
	protected AStarGoal goal;

	public void initialize(AStarStorage aStorage, AStarGoal aGoal) {
		storage = aStorage;
		goal = aGoal;
	}

	public AStarPlan runAStar(AStarNode endNode) {
		storage.reset();
		storage.addToOpenList(endNode);

		AStarNode currentNode = endNode;
		float h = goal.getHeuristicDistance(currentNode, true);
		currentNode.g = 0.0f;
		currentNode.h = h;
		currentNode.f = h;

		AStarNode tmpNode;
		while (true) {
			Logger.Debug("AStarEngine:\tFetching cheapest node...\n", 4);
			tmpNode = storage.removeCheapestOpenNode();
			Logger.Debug(tmpNode == null ? "AStarEngine:\tNo more nodes.\n" : "AStarEngine:\tSelected " + tmpNode + ".\n", 4);

			// If there are no other nodes, break
			if (tmpNode == null)
				break;

			currentNode = tmpNode;

			storage.addToClosedList(currentNode);

			// If search is done, break
			if (goal.isAStarFinished(currentNode))
				break;

			List<AStarNode> neighbors = currentNode.getNeighbors();
			for (AStarNode neighbor : neighbors) {
				if (currentNode.parent == neighbor)
					continue;

				float g = currentNode.g + (goal.getCost(currentNode, neighbor));
				h = goal.getHeuristicDistance(neighbor, false);

				float f = g + h;
				if ( f >= neighbor.f )
					continue;

				neighbor.f = f;
				neighbor.g = g;
				neighbor.h = h;

				if (neighbor.position == ListPosition.Closed)
					storage.removeFromClosedList(neighbor);

				storage.addToOpenList(neighbor);
				neighbor.parent = currentNode;
			}
		}

		return createPlan(currentNode);
	}

	public void cleanUp() {
		goal = null;
		storage = null;
	}

	protected abstract AStarPlan createPlan(AStarNode lastNode);
}