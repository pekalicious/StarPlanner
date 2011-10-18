package com.pekalicious.starplanner.model;

import org.bwapi.bridge.model.Unit;

/**
 * Represents a mineral patch.
 * 
 * @author Panagiotis Peikidis
 *
 */
public class MineralPatch extends ResourcePatch {
	/**
	 * The maximum number of workers allowed to gather minerals. 
	 */
	public final static int TOTAL_WORKERS_PER_MINERAL_PATCH = 3;
	
	/**
	 * Creates a mineral patch
	 *  
	 * @param patch the BWAPI unit of this patch to wrap around
	 */
	public MineralPatch(Unit patch) {
		super(patch, TOTAL_WORKERS_PER_MINERAL_PATCH);
	}
}
