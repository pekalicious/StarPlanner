package com.pekalicious.starplanner.model;

import java.util.HashSet;
import java.util.Set;

import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.agents.Worker;

/**
 * An abstract class that contains common behavior between resources.
 * 
 * @author Panagiotis Peikidis
 */
public abstract class ResourcePatch {
	/**
	 * The resource patch this class wraps around.
	 */
	protected Unit resourceUnit;
	/**
	 * The set of workers that are currently mining this resource.
	 */
	protected Set<Worker> assignedWorkers;
	
	/**
	 * The number of workers assigned for this patch to be considered full.
	 */
	protected int totalAllowedAssignedWorkers;
	
	/**
	 * Constructs a resource patch for the specified unit.
	 * @param unit the unit that represents this patch
	 * @param totalAssignedWorkers the total amount of workers that can be assigned
	 */
	public ResourcePatch(Unit unit, int totalAssignedWorkers) {
		this.resourceUnit = unit;
		this.totalAllowedAssignedWorkers = totalAssignedWorkers;
		this.assignedWorkers = new HashSet<Worker>(this.totalAllowedAssignedWorkers);
	}
	
	/**
	 * Assigns a worker to this patch.
	 * @param worker the worker to assign
	 */
	public void assignWorker(Worker worker) {
		this.assignedWorkers.add(worker);
	}
	
	/**
	 * Removes the worker from the assigned set.
	 * @param worker the worker to remove.
	 */
	public void removeWorker(Worker worker) {
		this.assignedWorkers.remove(worker);
	}
	
	/**
	 * Returns the patch
	 * @return the patch
	 */
	public Unit getResourceUnit() {
		return this.resourceUnit;
	}
	
	/**
	 * Returns true if the number of assigned workers to this patch
	 * is bigger or equal to the numbered allowed.
	 * @return true if full, otherwise false.
	 */
	public boolean isFull() {
		return this.assignedWorkers.size() >= this.totalAllowedAssignedWorkers;
	}

	/**
	 * Returns whether or not a particular worker is assigned to this patch.
	 * @param worker the worker to check
	 * @return true if the worker is assigned, otherwise false.
	 */
	public boolean contains(Worker worker) {
		return this.assignedWorkers.contains(worker);
	}
	
	/**
	 * Returns whether or not this resource patch is visible to the player.
	 * @return whether or not this resource patch is visible to the player.
	 */
	public boolean isVisible() {
		return this.resourceUnit.isVisible();
	}

	/**
	 * Returns the position of this resource unit.
	 * @return the position of this resource unit.
	 */
	public Position getPosition() {
		return this.resourceUnit.getPosition();
	}
}
