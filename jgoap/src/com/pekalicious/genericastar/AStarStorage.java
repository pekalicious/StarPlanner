package com.pekalicious.genericastar;

public interface AStarStorage {
	void reset();
	void addToOpenList(AStarNode node);
	void addToClosedList(AStarNode node);
	void removeFromClosedList(AStarNode node);
	AStarNode removeCheapestOpenNode();
	boolean isInOpenList(AStarNode node);
}