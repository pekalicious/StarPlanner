package com.pekalicious.starplanner.actuators;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Order;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;
import com.pekalicious.starplanner.managers.BaseManager;
import com.pekalicious.starplanner.managers.BuildOrder;
import com.pekalicious.starplanner.managers.OrderStatus;

public class BuilderManager {
	private Game game;
	private StarBlackboard blackBoard;
	private List<TilePosition> reservedPositions;
	private List<BaseManager> baseManagers;

	private int buildDistance;

	public BuilderManager(Game game, StarBlackboard blackBoard, List<BaseManager> baseManagers) {
		this.game = game;
		this.blackBoard = blackBoard;
		this.buildDistance = 1;
		this.reservedPositions = new ArrayList<TilePosition>();
		this.baseManagers = baseManagers;
	}

	int cleanerThreshold = 1000000;
	int cleanerCounter = cleanerThreshold;
	List<BuildOrder> toRemove = new ArrayList<BuildOrder>();
	public void update() {
		if (--cleanerCounter == 0) {
			Logger.Debug("BuilderMngr:\tCleaning reserved positions...\n", 2);
			cleanerCounter = cleanerThreshold;
			if (reservedPositions.size() > 1)
				reservedPositions.remove(0);
		}
		
		toRemove.clear();
		for (BuildOrder order : blackBoard.buildQueue) {
			Logger.Debug("BuilderMngr:\tUpdating order " + order.unitType.bwapiType.getName() + "\n", 5);
			int mineralsDiff = game.self().minerals() - order.unitType.bwapiType.mineralPrice();
			int gasDiff = game.self().gas() - order.unitType.bwapiType.gasPrice();
			
			if (order.worker != null)
				if (order.worker.getHitPoints() <= 0) {
					Logger.Debug("BuilderMngr:\tWorker died! Assigning new one...\n", 1);
					order.worker = null;
					order.status = OrderStatus.Idle;
				}
			switch (order.status) {
				case Idle:
					
					// Assign worker
					/*
					if (order.worker == null) {
						Unit worker = UnitUtils.getIdleWorker(game, blackBoard);
						if (worker == null) {
							Logger.Debug("!!! NO WORKER FOUND\n", 1);
						}else{
							Logger.Debug("Assigned new worker for " + order.unitType.getName() + "(" + worker + ")\n", 3);
							order.worker = worker;
						}
					}
					*/

					if (order.worker == null) {
						order.startPosition = addToBestBase(order);
					}
					
					// If enough minerals and gas start building...
					if (mineralsDiff >= 0) {
						Logger.Debug("BuilderMngr:\tMinerals OK: " + order.unitType.bwapiType.getName() 
								+ " (Need:" + order.unitType.bwapiType.mineralPrice() + "/Have:" + game.self().minerals() +")\n", 5);
						if (gasDiff >= 0) {
							Logger.Debug("BuilderMngr:\tGas OK: " + order.unitType.bwapiType.getName() 
									+ " (Need:" + order.unitType.bwapiType.gasPrice() + "/Have:" + game.self().gas() +")\n", 5);
							order.status = OrderStatus.Next;
						}
					}
				break;
				case Next:
					// If not enough minerals or gas, go back and wait...
					if (mineralsDiff < 0) {
						Logger.Debug("BuilderMngr:\tNot enough minerals (NEXT): " + order.unitType.bwapiType.getName() 
								+ " (Need:" + order.unitType.bwapiType.mineralPrice() + "/Have:" + game.self().minerals() +")\n", 3);
						order.status = OrderStatus.Idle;
						break;
					}else if (gasDiff <0) {
						Logger.Debug("BuilderMngr:\tNot enough gas (NEXT): " + order.unitType.bwapiType.getName() 
								+ " (Need:" + order.unitType.bwapiType.gasPrice() + "/Have:" + game.self().gas() +")\n", 3);
						order.status = OrderStatus.Idle;
						break;
					}
					
					if (order.worker == null) {
						Logger.Debug("BuilderMngr:\tWorker null!\n", 1);
					}
					if (order.startPosition == null) {
						Logger.Debug("BuilderMngr:\tStart position is null\n", 1);
					}
					if (order.unitType == null) {
						Logger.Debug("BuilderMngr:\tUnit type null!\n", 1);
					}

					
					if (order.buildPosition == null) {
						TilePosition buildPosition = null;
						if (order.onUnit == null) {
							Logger.Debug("BuilderMngr:\tLooking for new position: " + order.unitType.bwapiType.getName() + "\n", 3);
							try {
								do {
									buildPosition = getBuildLocationNear(order.worker, order.startPosition, order.unitType.bwapiType);
								} while (reservedPositions.contains(buildPosition));
							} catch (Exception e) {
								Logger.Debug("BuilderMngr:\tException : " + order.unitType.bwapiType.getName() + " " 
										+ e.getMessage() + " " + Logger.toString(e.getStackTrace()) + "\n", 3);
							}
						}else{
							Logger.Debug("BuilderMngr:\tPosition: " + order.unitType.bwapiType.getName() 
									+ " on top of " + order.onUnit.getType().getName() + "\n", 3);
							buildPosition = order.onUnit.getTilePosition();
						}
						
						if (buildPosition == null) {
							Logger.Debug("!!! No location found\n", 3);
						}else{
							Logger.Debug("BuilderMngr:\tAssigned work: " + order.unitType.bwapiType.getName() 
									+ " at " + buildPosition.x() + "," + buildPosition.y() + "\n", 3);
							order.buildPosition = buildPosition;
							if (order.worker.build(buildPosition, order.unitType.bwapiType)) {
								Logger.Debug("BuilderMngr:\tWork accepted: " + order.unitType.bwapiType.getName() + "\n", 3);
								order.status = OrderStatus.Started;
							}else{
								Logger.Debug("BuilderMngr:\tWork Declined:" + order.unitType.bwapiType.getName() + "\n", 3);
							}
						}
					}else{
						if (order.buildUnit != null) {
							Logger.Debug("BuilderMngr:\tHas position & worker & building, continuing construction...\n", 3);
							order.status = OrderStatus.Started;
							order.worker.repair(order.buildUnit);
						}else{
							if (canBuildHereWithSpace(order.worker, order.buildPosition, order.unitType.bwapiType)) {
								Logger.Debug("BuilderMngr:\tNo location & build unit???\n", 3);
								if (order.worker.build(order.buildPosition, order.unitType.bwapiType)) {
									Logger.Debug("BuilderMngr:\tWork accepted: " + order.unitType.bwapiType.getName() + "\n", 3);
									order.status = OrderStatus.Started;
								}else{
									Logger.Debug("BuilderMngr:\tWork Declined:" + order.unitType.bwapiType.getName() + "\n", 3);
								}
							}else{
								Logger.Debug("BuilderMngr:\tChanging location..\n", 1);
								order.buildPosition = null;
								order.status = OrderStatus.Idle;
							}
						}
					}
				break;
				case Started:
					Order wOrder = order.worker.getOrder();
					//int id = order.worker.getType().getID();
					
					if ((order.buildUnit != null) && (order.buildUnit.isCompleted())) {
						Logger.Debug("BuilderMngr:\tJob done: " + order.unitType.bwapiType.getName() + "\n", 3);
						order.completedUnits.add(order.buildUnit);
						order.buildCount--;
						order.status = order.buildCount <= 0 ? OrderStatus.Ended : OrderStatus.Next;
						order.buildPosition = null;
						Logger.Debug("BuilderMngr:\tNew Order (" + order.buildCount + ")= " + order.status.toString() + "\n", 3);
					}
					
					if (order.previousOrder != null 
							&& order.previousOrder.equals(Order.BUILD_TERRAN) 
							&& !wOrder.equals(Order.BUILD_TERRAN)
							&& !wOrder.equals(Order.CONSTRUCTING_BUILDING)
							&& !wOrder.equals(Order.RESET_COLLISION_1)
							&& !wOrder.equals(Order.RESET_COLLISION_2)) {
						Logger.Debug("BuilderMngr:\tCould not build there: " + order.unitType.bwapiType.getName() + " " + wOrder.getName() + "\n", 3);
					}
					
						/*
						if (order.buildUnit == null && order.onUnit == null) {
							reservedPositions.add(order.buildPosition);
							Logger.Debug("Could not build there.\n", 2);
							order.status = OrderStatus.Idle;
							break;
						}
						*/
					
					if (wOrder.equals(Order.BUILD_TERRAN)){
						if (mineralsDiff <= 0) {
							Logger.Debug("BuilderMngr:\tNot enough minerals (BUILD_TERRAN): " + order.unitType.bwapiType.getName() 
									+ " (Need:" + order.unitType.bwapiType.mineralPrice() + "/Have:" + game.self().minerals() +")\n", 3);
							order.status = OrderStatus.Idle;
							break;
						}else{
							if (order.previousOrder != null 
									&& !order.previousOrder.equals(Order.BUILD_TERRAN)
									&& !order.previousOrder.equals(Order.RESET_COLLISION_1)
									&& !order.previousOrder.equals(Order.RESET_COLLISION_2)) {
								
								Logger.Debug(order.previousOrder.getName() + "\n", 1);
								Logger.Debug("BuilderMngr:\tGoing to build (" + order.unitType.bwapiType.getName() + ")\n", 3);
							}
						}
					}else if ((wOrder.equals(Order.REPAIR_1)) || (wOrder.equals(Order.REPAIR_2))) {
						Logger.Debug("BuilderMngr:\tRepairing...\n", 3);
					}else if (wOrder.equals(Order.CONSTRUCTING_BUILDING)) {
						if (order.buildUnit == null) {
							order.buildUnit = order.worker.getBuildUnit();
							Logger.Debug("BuilderMngr:\tBuild Started (" + order.buildUnit.getType().getName() + ")\n", 3);
						}
					}else if ((!wOrder.equals(Order.RESET_COLLISION_1)) && (!wOrder.equals(Order.RESET_COLLISION_2))) {
						Logger.Debug("BuilderMngr:\tSomething happened! WorkOrder: " + wOrder.getName() + ". Reseting order.\n", 3);
						if (order.onUnit==null)
							order.buildPosition = null;
						order.status = OrderStatus.Idle;
					}
				break;
				case Ended:
					Logger.Debug("BuilderMngr:\tAdding to remove list: " + order.unitType.bwapiType.getName() + "\n", 3);
					order.worker = null;
					order.buildUnit = null;
					toRemove.add(order);
				break;
			}

			if (order.worker != null)
				order.previousOrder = order.worker.getOrder();
		}
		
		for (BuildOrder order : toRemove) {
			Logger.Debug("BuilderMngr:\tRemoving order: " + order.unitType.bwapiType.getName() + "\n", 3);
			blackBoard.buildQueue.remove(order);
		}
		
		this.blackBoard.buildPriority = Integer.MAX_VALUE;
		for (BuildOrder order : this.blackBoard.buildQueue)
			if (order.buildUnit == null)
				if (this.blackBoard.buildPriority > order.unitType.priority)
					this.blackBoard.buildPriority = order.unitType.priority;
	}
	
	private TilePosition addToBestBase(BuildOrder order) {
		BaseManager best = null;
		for (BaseManager base : this.baseManagers) {
			if (!base.hasBase()) continue;
			if (best == null)
				best = base;
			else
				if (base.getBuildingsCount() < best.getBuildingsCount())
					best = base;
		}
		
		if (best != null) {
			best.addBuildOrder(order);
			return best.getTilePosition();
		}else{
			Logger.Debug("BuilderMngr:\tCould not find base!\n", 1);
			return null;
		}
	}
	
	private TilePosition getBuildLocationNear(Unit worker, TilePosition position, UnitType type) {
		// returns a valid build location near the specified tile startPosition.
		// searches outward in a spiral.
		int x = position.x();
		int y = position.y();
		int length = 1;
		int j = 0;
		boolean first = true;
		int dx = 0;
		int dy = 1;
		while (length < game.mapWidth()) // We'll ride the spiral to the end
		{
			// if we can build here, return this tile startPosition
			if (x >= 0 && x < game.mapWidth() && y >= 0 && y < game.mapHeight())
				if (canBuildHereWithSpace(worker, new TilePosition(x, y), type))
					return new TilePosition(x, y);

			// otherwise, move to another startPosition
			x = x + dx;
			y = y + dy;
			// count how many steps we take in this direction
			j++;
			if (j == length) // if we've reached the end, its time to turn
			{
				// reset step counter
				j = 0;

				// Spiral out. Keep going.
				if (!first)
					length++; // increment step counter if needed

				// first=true for every other turn so we spiral out at the right
				// rate
				first = !first;

				// turn counter clockwise 90 degrees:
				if (dx == 0) {
					dx = dy;
					dy = 0;
				} else {
					dy = -dx;
					dx = 0;
				}
			}
			// Spiral out. Keep going.
		}
		return TilePosition.NONE;
	}

	private boolean canBuildHereWithSpace(Unit worker, TilePosition position, UnitType type) {
		// returns true if we can build this type of unit here with the
		// specified amount of space.
		// space value is stored in this->buildDistance.

		// if we can't build here, we of course can't build here with space
		if (!canBuildHere(worker, position, type))
			return false;

		int width = type.tileWidth();
		int height = type.tileHeight();

		// make sure we leave space for add-ons. These types of units can have
		// addons:
		if (type == UnitType.TERRAN_COMMAND_CENTER
				|| type == UnitType.TERRAN_FACTORY
				|| type == UnitType.TERRAN_STARPORT
				|| type == UnitType.TERRAN_SCIENCE_FACILITY) {
			width += 2;
		}

		int startx = position.x() - buildDistance;
		if (startx < 0)
			startx = 0;
		int starty = position.y() - buildDistance;
		if (starty < 0)
			starty = 0;
		int endx = position.x() + width + buildDistance;
		if (endx > game.mapWidth())
			endx = game.mapWidth();
		if (endx < position.x() + width)
			return false;
		int endy = position.y() + height + buildDistance;
		if (endy > game.mapHeight())
			endy = game.mapHeight();

		for (int x = startx; x < endx; x++)
			for (int y = starty; y < endy; y++)
				if (!type.isRefinery())
					if (!buildable(x, y) || isTileOccupied(x, y))
						return false;

		if (position.x() > 3) {
			int startx2 = startx - 2;
			if (startx2 < 0)
				startx2 = 0;
			for (int x = startx2; x < startx; x++)
				for (int y = starty; y < endy; y++) {
					for (Unit unit : game.unitsOnTile(x, y)) {
						if (!unit.isLifted()) {
							UnitType uType = unit.getType();
							if (uType == UnitType.TERRAN_COMMAND_CENTER
									|| uType == UnitType.TERRAN_FACTORY
									|| uType == UnitType.TERRAN_STARPORT
									|| uType == UnitType.TERRAN_SCIENCE_FACILITY) {
								return false;
							}
						}
					}
				}
		}
		return true;
	}

	private boolean canBuildHere(Unit worker, TilePosition position, UnitType type) {
		// returns true if we can build this type of unit here. Takes into
		// account reserved tiles.
		if (!Game.getInstance().canBuildHere(worker, position, type))
			return false;

		if (reservedPositions.contains(position)) return false;
		
		return !isTileOccupied(position);
	}

	private boolean buildable(int x, int y) {
		if (!game.isBuildable(x, y))
			return false;
		for (Unit unit : game.unitsOnTile(x, y))
			if (unit.getType().isBuilding() && !unit.isLifted())
				return false;

		return true;
	}

	private boolean isTileOccupied(TilePosition position) {
		for (Unit unit : Game.getInstance().getAllUnits())
			if (unit.getTilePosition().equals(position))
				return true;

		return false;
	}

	private boolean isTileOccupied(int x, int y) {
		for (Unit unit : Game.getInstance().getAllUnits())
			if (unit.getTilePosition().x() == x
					&& unit.getTargetPosition().y() == y)
				return true;

		return false;
	}

}