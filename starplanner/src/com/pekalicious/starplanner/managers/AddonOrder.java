package com.pekalicious.starplanner.managers;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.util.UnitUtils;

/**
 * A Resource Order to create an addon on a building.
 * 
 * @author Panagiotis Peikidis
 *
 */
public class AddonOrder extends ResourceOrder {
	/**
	 * The building on which the addon will be created.
	 */
	public Unit building;
	
	/**
	 * Empty default constructor.
	 */
	public AddonOrder() {
		
	}
	
	/**
	 * Creates an Addon Order with the give addon type to create
	 * @param unitType the addon type to create.
	 */
	public AddonOrder(UnitUtils.Type unitType) {
		this.status = OrderStatus.Idle;
		this.unitType = unitType;
	}
}
