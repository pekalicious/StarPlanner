package com.pekalicious.starplanner.managers;

import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.util.UnitUtils;

/**
 * A building resource order.
 * 
 * @author Panagiotis Peikidis
 */
public class BuildOrder extends ResourceOrder {
	//public TilePosition startPosition;
	public TilePosition buildPosition;
	/**
	 * The building unit used during construction. The building in this state isn't completed yet.
	 */
	public Unit buildUnit;
	/**
	 * The actual unit when the building has ended.
	 */
	public Unit completedUnit;
	/**
	 * Used when a building is built on top of another unit (i.e. refinery on top of gas geysers)
	 */
	public Unit onUnit;
	/**
	 * The base manager that the building will be assigned to when finished.
	 */
	public BaseManager baseManager;

	/**
	 * Constructs empty Build Order
	 */
	public BuildOrder() {
		
	}
	
	/**
	 * Constructs a build order with the associated unit type.
	 * 
	 * @param unitType the unit type to build.
	 */
	public BuildOrder(UnitUtils.Type unitType) {
		this.unitType = unitType;
		this.status = OrderStatus.Idle;
	}
}
