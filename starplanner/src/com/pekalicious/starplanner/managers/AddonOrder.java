package com.pekalicious.starplanner.managers;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.util.UnitUtils;

public class AddonOrder {
	public OrderStatus status;
	public Unit building;
	public UnitUtils.Type unitType;
	
	public AddonOrder() {
		
	}
	
	public AddonOrder(UnitUtils.Type unitType) {
		this.status = OrderStatus.Idle;
		this.unitType = unitType;
	}
}
