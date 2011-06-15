package com.pekalicious.genericastar;

import java.util.List;

public abstract class AStarNode {
	public ListPosition position;
	public float h;
	public float g;
	public float f;
	public AStarNode parent;

	public AStarNode() {
		f = Float.MAX_VALUE;
		g = 0.0f;
		h = 0.0f;
	}

	public abstract List<AStarNode> getNeighbors();
	
	@Override
	public String toString() {
		return "f=" + f;
	}
}