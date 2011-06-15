package com.pekalicious.genericastar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pekalicious.Logger;

public class DefaultAStarStorage implements AStarStorage {
	private List<AStarNode> openList;
	private List<AStarNode> closedList;
	private Comparator<AStarNode> comparator;

	public DefaultAStarStorage(Comparator<AStarNode> comparator) {
		this.comparator = comparator;
		openList = new ArrayList<AStarNode>();
		closedList = new ArrayList<AStarNode>();
	}
	
	public DefaultAStarStorage() {
		this(new DefaultNodeComparator());
	}

	public void reset() {
		openList.clear();
		closedList.clear();
	}

	public void addToOpenList(AStarNode node) {
		node.position = ListPosition.Opened;
		openList.add(node);
		Logger.Debug("Before sorting:" + Logger.toString(openList) + "\n", 3);
		if (comparator != null)
			Collections.sort(openList,comparator);
		Logger.Debug("After sorting:" + Logger.toString(openList) + "\n", 3);
	}

	public void addToClosedList(AStarNode node) {
		node.position = ListPosition.Closed;
		closedList.add(node);
	}

	public void removeFromClosedList(AStarNode node) {
		closedList.remove(node);
	}

	public AStarNode removeCheapestOpenNode() {
		if (openList.size() == 0) return null;

		AStarNode retNode = openList.get(0);
		openList.remove(retNode);
		return retNode;
	}

	@Override
	public boolean isInOpenList(AStarNode node) {
		return openList.contains(node);
	}
}