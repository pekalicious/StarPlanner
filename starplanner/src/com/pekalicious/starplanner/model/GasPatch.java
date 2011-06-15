package com.pekalicious.starplanner.model;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.Logger;

public class GasPatch {
	private Unit patch;
	private Unit refinery;
	private List<Unit> gatherers;
	
	public GasPatch(Unit patch) {
		this.patch = patch;
		this.gatherers = new ArrayList<Unit>();
	}
	
	public boolean isFull() {
		return this.gatherers.size() == 3;
	}
	
	private List<Unit> died = new ArrayList<Unit>();
	public void update() {
		this.died.clear();
		for (Unit unit : this.gatherers)
			if (unit.getHitPoints()<=0)
				this.died.add(unit);
		
		for (Unit unit:this.died)
			this.gatherers.add(unit);
	}

	public boolean hasRefinery() {
		return this.refinery != null;
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
	
	public Unit getGasUnit() {
		return this.patch;
	}

	public void assignWorker(Unit unit) {
		if (!hasRefinery()) {
			Logger.Debug("GasPatch:\tCannot gather gas!\n", 1);
			return;
		}
		
		this.gatherers.add(unit);
		unit.rightClick(this.refinery);
	}

	public void setRefinery(Unit unit) {
		if (this.refinery != null) {
			Logger.Debug("GasPatch:\tRefinery already set!\n", 1);
		}else{
			this.refinery = unit;
		}
	}

	public Unit getPatch() {
		return this.patch;
	}
}
