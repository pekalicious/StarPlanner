package com.pekalicious.genericastar;

import java.util.ArrayList;
import java.util.List;

import com.pekalicious.Logger;

public class AStarPlan {
	protected List<AStarPlanStep> steps;
	protected int currentStep;

	public AStarPlan() {
		steps = new ArrayList<AStarPlanStep>();
	}

	public void addStep(AStarPlanStep step) {
		steps.add(step);
	}

	public boolean isPlanStepComplete() {
		return currentStep >= steps.size();
	}

	public String toString() {
		return Logger.toString(steps, ",");
	}
	
	public int Size() {
		return steps.size();
	}
	
	public boolean isPlanComplete() {
		return this.currentStep >= steps.size();
	}
}