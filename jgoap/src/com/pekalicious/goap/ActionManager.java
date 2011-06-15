package com.pekalicious.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pekalicious.Logger;
import com.pekalicious.agent.WorldStateProperty;

public enum ActionManager {
	Instance;
	
	//private List<PlannerAction> actions;
	private Map<String, List<PlannerAction>> effectsTable;

	private ActionManager() {
		//actions = new ArrayList<PlannerAction>();
		effectsTable = new HashMap<String, List<PlannerAction>>();
	}

	public ActionManager addAction(PlannerAction action) {
        for (WorldStateProperty<?> effect : action.effects.getValues()) {
            if (!effectsTable.containsKey(effect.key))
                effectsTable.put(effect.key, new ArrayList<PlannerAction>());
            
            effectsTable.get(effect.key).add(action);
        }
		return this;
	}

//	public void buildEffectsTable() {
//		Logger.Debug("ActionManager:\tBuilding Effects Table...\n", 2);
//		effectsTable = new HashMap<String, List<PlannerAction>>();
//
//		for (PlannerAction action : actions)
//			for (WorldStateProperty<?> effect : action.effects.getValues()) {
//				if (!effectsTable.containsKey(effect.key))
//					effectsTable.put(effect.key, new ArrayList<PlannerAction>());
//				
//				effectsTable.get(effect.key).add(action);
//			}
//		
//		Logger.Debug("ActionManager:\tDone Building Effects Table.\n", 2);
//		Logger.Debug("ActionManager:\tEffectsTable: " + Logger.toString(effectsTable) + "\n", 1);
//	}
	
	public List<PlannerAction> getActionsByKey(String key) {
		List<PlannerAction> actionList = new ArrayList<PlannerAction>();
		if (!effectsTable.containsKey(key)) return actionList; 

		List<PlannerAction> tmp = effectsTable.get(key);
		for (PlannerAction action : tmp) {
			try {
				actionList.add(action.clone());
			} catch (Exception e) {
				if (Logger.DEBUG_LEVEL > 1)
					e.printStackTrace();
			}
		}
		
		return actionList;
	}
	
	public void clearActions() {
//		actions.clear();
		effectsTable.clear();
		Logger.Debug("ActionManager:\tCleared actions and effects table.\n", 3);
	}
}