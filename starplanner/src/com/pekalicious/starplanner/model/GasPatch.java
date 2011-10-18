package com.pekalicious.starplanner.model;

import org.bwapi.bridge.model.Unit;

import com.pekalicious.Logger;

/**
 * Represents a gas patch.
 * 
 * @author Panagiotis Peikidis
 */
public class GasPatch extends ResourcePatch {
	/**
	 * The maximum number of workers that can be assigned to gather gas.
	 */
	public static final int TOTAL_WORKERS_PER_GAS_PATCH = 3;
	
	/**
	 * The refinery unit of this gas patch (if available).
	 */
	private Unit refinery;
	
	/**
	 * Creates a gas patch.
	 * @param patch the BWAPI unit to wrap around this class
	 */
	public GasPatch(Unit patch) {
		super(patch, TOTAL_WORKERS_PER_GAS_PATCH);
	}
	
	/**
	 * Returns whether or not a refinery has been assigned to this patch.
	 * @return true if there is a refinery assigned, otherwise false.
	 */
	public boolean hasRefinery() {
		return this.refinery != null;
	}

	/**
	 * Assigns a refinery to this patch.
	 * @param unit the refinery to assign.
	 */
	public void setRefinery(Unit unit) {
		if (this.refinery != null) {
			Logger.Debug("GasPatch:\tRefinery already set!\n", 1);
		}else{
			this.refinery = unit;
		}
	}
	
	/**
	 * Returns the refinery unit of this patch.
	 * @return the refinery unit of this patch.
	 */
	public Unit getRefinery() {
		return this.refinery;
	}
}
