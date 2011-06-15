package com.pekalicious.starplanner.managers;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Order;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.util.UnitUtils;

public class BuildOrder {
	public Unit worker;
	public UnitUtils.Type unitType;
	public TilePosition startPosition;
	public TilePosition buildPosition;
	public Unit buildUnit;
	public OrderStatus status;
	public int buildCount;
	public Unit onUnit;
	public Order previousOrder;
	public List<Unit> completedUnits;
	public BaseManager baseManager;

	public BuildOrder() {
		
	}
	
	public BuildOrder(UnitUtils.Type unitType, int buildCount) {
		this.unitType = unitType;
		this.status = OrderStatus.Idle;
		this.buildCount = buildCount;
		this.completedUnits = new ArrayList<Unit>();
	}
}
