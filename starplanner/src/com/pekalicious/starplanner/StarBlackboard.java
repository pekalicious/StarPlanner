package com.pekalicious.starplanner;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.Logger;
import com.pekalicious.agent.BlackBoard;
import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.managers.AddonOrder;
import com.pekalicious.starplanner.managers.BuildOrder;
import com.pekalicious.starplanner.managers.ResourceManager;
import com.pekalicious.starplanner.managers.TrainingOrder;
import com.pekalicious.starplanner.model.Squad;
import com.pekalicious.starplanner.util.UnitUtils;


public class StarBlackboard implements BlackBoard {
	public List<BuildOrder> buildQueue;
	public int buildPriority;
	public List<TrainingOrder> trainingQueue;
	public Unit scout;
	public List<AddonOrder> addonQueue;

	public boolean buildPlanReplan;
	public PlannerGoal buildGoal;
	public boolean buildPlanInvalid;
	public boolean buildPlanComplete;
	
	public int minerals;
	public int gas;
    public int supplyUsed;
    public int supplyTotal;
    
    public Squad squad;
	public boolean squadIgnoreUnitDistance;
	
	public StarBlackboard() {
		resetValues();
		squad = new Squad();
	}
	
	public void resetValues() {
		buildQueue = new ArrayList<BuildOrder>();
		trainingQueue = new ArrayList<TrainingOrder>();
		addonQueue = new ArrayList<AddonOrder>();
	}
	
	public BuildOrder addToBuildQueue(UnitUtils.Type type) {
		BuildOrder order = new BuildOrder(type);
		this.buildQueue.add(order);
		Logger.Debug("Blackboard:\tAdded to buildQueue: " + type.bwapiType.getName() + "\n", 2);
		return order;
	}
	
	public AddonOrder addToAddonQueue(UnitUtils.Type type) {
		AddonOrder order = new AddonOrder(type);
		this.addonQueue.add(order);
		Logger.Debug("Blackboard:\tAdded to addonQueue: " + type.bwapiType.getName() + "\n", 2);
		return order;
	}
	
	public BuildOrder addToBuildQueue(UnitUtils.Type type, Unit onUnit) {
		Logger.Debug("Blackboard:\tAdded to buildQueue: " + type.bwapiType.getName() + "\n", 2);
		BuildOrder order = new BuildOrder(type);
		order.onUnit = onUnit;
		this.buildQueue.add(order);
		return order;
	}
	
	public TrainingOrder addToTrainingQueue(UnitUtils.Type type, int trainingCount) {
		Logger.Debug("Blackboard:\tAdded to trainQueue: " + trainingCount + " " + type.bwapiType.getName() + "\n", 2);
		TrainingOrder order = new TrainingOrder(type, trainingCount);
		trainingQueue.add(order);
		return order;
	}

	/*
	public boolean isInBuildQueue(UnitType type) {
		for (BuildOrder order : buildQueue)
			if (order.worker != null && order.worker.equals(type)) return true;
		
		return false;
	}

	public boolean isOccupied(Unit unit) {
		for (BuildOrder order : buildQueue)
			if (order.worker != null 
					&& order.worker.equals(unit) 
					&& order.status != OrderStatus.Ended)
				return true;
		
		if (unit.equals(scout))
			return true;
		
		return false;
	}
	*/

}
