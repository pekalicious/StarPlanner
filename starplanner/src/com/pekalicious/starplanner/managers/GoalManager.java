package com.pekalicious.starplanner.managers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.pekalicious.Logger;
import com.pekalicious.goap.ActionManager;
import com.pekalicious.goap.PlannerAction;
import com.pekalicious.goap.PlannerGoal;
import com.pekalicious.starplanner.StarPlanner;
import com.pekalicious.starplanner.StarPlannerData;
import com.pekalicious.starplanner.actions.StarAction;
import com.pekalicious.starplanner.actions.terran.ActionBuildAcademy;
import com.pekalicious.starplanner.actions.terran.ActionBuildArmory;
import com.pekalicious.starplanner.actions.terran.ActionBuildBarrack;
import com.pekalicious.starplanner.actions.terran.ActionBuildBarrackAggressive;
import com.pekalicious.starplanner.actions.terran.ActionBuildCommandCenter;
import com.pekalicious.starplanner.actions.terran.ActionBuildEngineeringBay;
import com.pekalicious.starplanner.actions.terran.ActionBuildFactory;
import com.pekalicious.starplanner.actions.terran.ActionBuildStarport;
import com.pekalicious.starplanner.actions.terran.ActionTrainFirebat;
import com.pekalicious.starplanner.actions.terran.ActionTrainGoliath;
import com.pekalicious.starplanner.actions.terran.ActionTrainMarine;
import com.pekalicious.starplanner.actions.terran.StrActionAttackLocation;
import com.pekalicious.starplanner.actions.terran.StrActionBringSquadToLocation;
import com.pekalicious.starplanner.actions.terran.StrActionBringWorkerToLocation;
import com.pekalicious.starplanner.actions.terran.StrActionBuildExpansion;
import com.pekalicious.starplanner.actions.terran.StrActionCreateSquad;
import com.pekalicious.starplanner.actions.terran.StrActionCreateUnits;
import com.pekalicious.starplanner.actions.terran.StrActionFindEnemyLocation;
import com.pekalicious.starplanner.actions.terran.StrActionProtectLocation;
import com.pekalicious.starplanner.actions.terran.StrActionSelectWorker;
import com.pekalicious.starplanner.goals.StrGoalComparator;
import com.pekalicious.starplanner.goals.StrategicGoal;
import com.pekalicious.starplanner.goals.terran.BuiGoalMake;
import com.pekalicious.starplanner.goals.terran.StrGoalAttackLocation;
import com.pekalicious.starplanner.goals.terran.StrGoalBuildExpansion;

public class GoalManager {
	public enum GoalType { BuildOrder, Strategic }
	private StarPlannerData data;
	private String filePath;
	
	public GoalManager() {
		this("starplanner.data");
	}
	
	public GoalManager(String dataPath) {
		this.filePath = dataPath;
		load();
	}
	
	public void updateGoalRelevancies(StarPlanner starPlanner) {
		for (StrategicGoal goal : data.strategicGoals)
			goal.updateRelevancy(starPlanner);
	}
	
	public PlannerGoal getMostRelevantGoal(boolean recalculate) {
		Collections.sort(data.strategicGoals, new StrGoalComparator());
		return data.strategicGoals.get(0);
	}

	public boolean hasAction(PlannerAction otherAction) {
		for (PlannerAction action : data.buildOrderActions)
			if (otherAction.getClass().isInstance(action))
				return true;
		
		for (PlannerAction action : data.strategicActions)
			if (otherAction.getClass().isInstance(action))
				return true;
		
		return false;
	}
	
	public boolean hasGoal(PlannerGoal otherGoal) {
		for (PlannerGoal goal : data.buildOrderGoals)
			if (otherGoal.getClass().isInstance(goal))
				return true;
		
		for (PlannerGoal goal : data.strategicGoals)
			if (otherGoal.getClass().isInstance(goal))
				return true;
		
		return false;
	}
	
	public void addAction(StarAction action, GoalType type) {
		add(action, type == GoalType.BuildOrder ? data.buildOrderActions : data.strategicActions );
		Logger.Debug("GoalManager:\tActivated action " + action.toString() + "\n", 1);
	}
	
	public void addGoal(PlannerGoal goal, GoalType type) {
		if (type == GoalType.BuildOrder)
			add(goal, data.buildOrderGoals);
		else
			add((StrategicGoal)goal, data.strategicGoals);
		Logger.Debug("GoalManager:\tActivated goal " + goal.toString() + "\n", 1);
	}
	
	public <T> void add(T newObj, List<T> list) {
		list.add(newObj);
		
		updateActionManager();
	}
	
	public void removeAction(StarAction action, GoalType type) {
		remove(action, type == GoalType.BuildOrder ? data.buildOrderActions : data.strategicActions);
		Logger.Debug("GoalManager:\tDeactivated action " + action.toString() + "\n", 1);
	}
	
	public void removeGoal(PlannerGoal goal, GoalType type) {
		if (type == GoalType.BuildOrder)
			remove(goal, data.buildOrderGoals);
		else
			remove((StrategicGoal)goal, data.strategicGoals);
		Logger.Debug("GoalManager:\tDeactivated goal " + goal.toString() + "\n", 1);
	}
	
	private <T> void remove(T otherObj, List<T> list) {
		T toDelete = null;
		
		for (T obj : list)
			if (obj.getClass().isInstance(otherObj))
				toDelete = obj;

		if (toDelete != null)
			list.remove(toDelete);
		
		updateActionManager();
	}
	
	private void updateActionManager() {
		ActionManager.Instance.clearActions();
		
		for (PlannerAction action : data.buildOrderActions)
			ActionManager.Instance.addAction(action);

		for (PlannerAction action : data.strategicActions)
			ActionManager.Instance.addAction(action);

		//ActionManager.Instance.buildEffectsTable();
		save();
	}
	
	private void load() {
		try {
			FileInputStream fIn = new FileInputStream(filePath);
			ObjectInputStream objIn = new ObjectInputStream (fIn);
			data = (StarPlannerData)objIn.readObject();
			//System.out.println("GoalManager:\tSuccessfully loaded 'goals.data'");
		}catch(Exception e) {
			//System.out.println("GoalManager:\tError: " + e.getMessage());
			initialize();
		}
		updateActionManager();
	}
	
	private void save() {
		try {
			FileOutputStream fOut = new FileOutputStream(filePath);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			objOut.writeObject (data);
		}catch(Exception e) {
			Logger.Debug("GoalManager:\tCould not save data.\n", 1);
		}
	}
	
	private void initialize() {
		data = new StarPlannerData();

		data.buildOrderActions.addAll(Arrays.asList(getAllBuildOrderActions()));
		data.buildOrderGoals.addAll(Arrays.asList(getAllBuildOrderGoals()));
		data.strategicActions.addAll(Arrays.asList(getAllStrategicActions()));
		data.strategicGoals.addAll(Arrays.asList(getAllStrategicGoals()));
		
		updateActionManager();
	}
	
	public static PlannerGoal[] getAllBuildOrderGoals() {
		return new PlannerGoal[] {
			new BuiGoalMake()
		};
	}
	
	public static StrategicGoal[] getAllStrategicGoals() {
		return new StrategicGoal[] {
			new StrGoalBuildExpansion(),
			new StrGoalAttackLocation(),
		};
	}
	
	public static StarAction[] getAllBuildOrderActions() {
		return new StarAction[] {
			new ActionTrainGoliath(),
			new ActionTrainFirebat(),
			new ActionBuildBarrackAggressive(),
			new ActionBuildBarrack(),
			new ActionTrainMarine(),
			new ActionBuildEngineeringBay(),
			new ActionBuildCommandCenter(),
			new ActionBuildArmory(),
			new ActionBuildAcademy(),
			new ActionBuildStarport(),
			new ActionBuildFactory(),
		};
	}
	
	public static StarAction[] getAllStrategicActions() {
		return new StarAction[] {
			new StrActionAttackLocation(),
			new StrActionBringSquadToLocation(),
			new StrActionBringWorkerToLocation(),
			new StrActionBuildExpansion(),
			new StrActionCreateSquad(),
			new StrActionProtectLocation(),
			new StrActionSelectWorker(),
			new StrActionCreateUnits(),
			new StrActionFindEnemyLocation()
		};
	}
}
