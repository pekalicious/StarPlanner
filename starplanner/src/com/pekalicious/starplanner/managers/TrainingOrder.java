package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.util.UnitUtils;

public class TrainingOrder {
	public UnitUtils.Type unitType;
	public int unitCount;
	public OrderStatus status;
	public List<Unit> units;
	public int waitingFor;
	
	public TrainingOrder(UnitUtils.Type unitType, int unitCount) {
		this.unitType = unitType;
		this.unitCount = unitCount;
		this.waitingFor = 0;
		this.status = OrderStatus.Idle;
		this.units = new ArrayList<Unit>(unitCount);
	}

	public TrainingOrder() {
	}
}
