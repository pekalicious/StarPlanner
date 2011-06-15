package com.pekalicious.starplanner.goals;

import java.util.Comparator;


public class StrGoalComparator implements Comparator<StrategicGoal>{

	@Override
	public int compare(StrategicGoal a, StrategicGoal b) {
		return a.relevancy == b.relevancy ? 0 : a.relevancy < b.relevancy ? -1 : +1;
	}
}
