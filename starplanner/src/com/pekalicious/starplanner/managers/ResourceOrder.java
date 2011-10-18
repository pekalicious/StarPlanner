package com.pekalicious.starplanner.managers;

import com.pekalicious.starplanner.util.UnitUtils;

/**
 * Represents an order that consumes resources and needs to be carried out.
 * @author Panagiotis Peikidis
 *
 */
public abstract class ResourceOrder {
	public UnitUtils.Type unitType;
	
	public OrderStatus status;
	
	public int getMineralPrice() {
		return unitType.bwapiType.mineralPrice();
	}
	public int getGasPrice() {
		return unitType.bwapiType.gasPrice();
	}
	public int getSupplyPrice() {
		return unitType.bwapiType.supplyRequired();
	}
}
