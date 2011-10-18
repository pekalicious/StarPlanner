package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.util.UnitUtils;

/**
 * A Resource Order to train a unit.
 * 
 * @author Panagiotis Peikidis
 *
 */
public class TrainingOrder extends ResourceOrder {
	/**
	 * The number of unit to train.
	 */
	public int unitCount;
	/**
	 * The list of completed trained units.
	 */
	public List<Unit> completedUnits;
	public int waitingFor;
	
	public TrainingOrder(UnitUtils.Type unitType, int unitCount) {
		this.unitType = unitType;
		this.unitCount = unitCount;
		this.waitingFor = 0;
		this.status = OrderStatus.Idle;
		this.completedUnits = new ArrayList<Unit>(unitCount);
	}

	public TrainingOrder() {
	}
}
