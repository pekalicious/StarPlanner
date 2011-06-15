package com.pekalicious.starplanner.model;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.Logger;

public class MineralPatch {
	private Unit patch;
	private List<Unit> gatherers;
	
	public MineralPatch(Unit patch) {
		this.patch = patch;
		this.gatherers = new ArrayList<Unit>();
	}
	
	public boolean isFull() {
		return gatherers.size() == 2 || patch.getResources() <= 0;
	}
	
	public void assignWorker(Unit unit) {
		if (isFull()) {
			Logger.Debug("MineralPtch:\tPatch is full!\n", 1);
			return;
		}
		
		Logger.Debug("MineralPtch:\tSent " + unit + " to gather\n", 3);
		gatherers.add(unit);
		unit.rightClick(patch);
	}
	
	private List<Unit> died = new ArrayList<Unit>();
	public void update() {
		died.clear();
		for (Unit unit : gatherers)
			if (unit.getHitPoints()<=0)
				died.add(unit);
		
		for (Unit unit:died)
			gatherers.add(unit);
	}

	public boolean contains(Unit unit) {
		return this.gatherers.contains(unit);
	}

	public boolean isVisible() {
		return this.patch.isVisible();
	}

	public Position getPosition() {
		return this.patch.getPosition();
	}
	
	public Unit getPatch() {
		return this.patch;
	}
}
