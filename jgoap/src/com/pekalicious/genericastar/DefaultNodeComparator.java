package com.pekalicious.genericastar;

import java.util.Comparator;

public class DefaultNodeComparator implements Comparator<AStarNode>{

	@Override
	public int compare(AStarNode a, AStarNode b) {
		return a.f == b.f ? 0 : a.f < b.f ? -1 : +1;
	}

}
