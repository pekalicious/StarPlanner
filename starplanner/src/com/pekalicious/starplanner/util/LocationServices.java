package com.pekalicious.starplanner.util;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.agents.Worker;
import com.pekalicious.starplanner.model.GasPatch;
import com.pekalicious.starplanner.model.MineralPatch;

public enum LocationServices {
	Instance;
	
	private Game game;
	private int buildDistance;
	private List<TilePosition> reservedPositions;
	
	private int cleanerThreshold = 1000000;
	private int cleanerCounter = cleanerThreshold;
	
	private LocationServices() {
		this.buildDistance = 1;
		this.reservedPositions = new ArrayList<TilePosition>();
		this.game = Game.getInstance();
	}
	
	public void update() {
		if (--cleanerCounter == 0) {
			Logger.Debug("BuilderMngr:\tCleaning reserved positions...\n", 2);
			cleanerCounter = cleanerThreshold;
			//if (reservedPositions.size() > 1)
				//reservedPositions.remove(0);
			//reservedPositions.clear();
		}
		
	}
	
	/**
	 * Finds the closest mineral patch based on the worker's base mineral patches
	 * @return the closest mineral patch found. This may return null if there are 
	 * no minerals left in the base or all of them are full
	 */
	public MineralPatch findClosestMineralPatch(Worker worker) {
		MineralPatch patch = null;
		double closest = Double.MAX_VALUE;
		
		for (MineralPatch mineralPatch : worker.getBase().getMineralPatches()) {
			if (!mineralPatch.isVisible()) continue;
			if (mineralPatch.isFull()) continue;
			
			double dx = worker.getPosition().x() - mineralPatch.getPosition().x();
			double dy = worker.getPosition().y() - mineralPatch.getPosition().y();
			double dist = Math.sqrt(dx*dx + dy*dy); 

			if (dist < closest) {
				patch = mineralPatch;
				closest = dist;
			}
		}
		
		return patch;
	}

	/**
	 * Finds the closest gas patch based on the worker's base gas patches
	 * @return the closest gas patch found. This may return null if there are 
	 * no gaysers left in the base or all of them are full
	 */
	public GasPatch findClosestGasPatch(Worker worker) {
		GasPatch patch = null;
		double closest = Double.MAX_VALUE;
		
		for (GasPatch gasPatch : worker.getBase().getGasPatches()) {
			if (!gasPatch.isVisible()) continue;
			if (gasPatch.isFull()) continue;
			
			double dx = worker.getPosition().x() - gasPatch.getPosition().x();
			double dy = worker.getPosition().y() - gasPatch.getPosition().y();
			double dist = Math.sqrt(dx*dx + dy*dy); 

			if (dist < closest) {
				patch = gasPatch;
				closest = dist;
			}
			
		}
		return patch;
	}

	/**
	 * Returns a valid build location near the specified tile startPosition.
	 * @param worker
	 * @param position
	 * @param type
	 * @return
	 */
	public TilePosition getBuildLocationNear(Unit worker, TilePosition position, UnitType type) {
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
		return null;
	}

	/**
	 * Returns true if we can build this type of unit here with the
	 * specified amount of space.
	 * space value is stored in this->buildDistance.
	 *  
	 * @param worker
	 * @param position
	 * @param type
	 * @return
	 */
	private boolean canBuildHereWithSpace(Unit worker, TilePosition position, UnitType type) {
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
		
		boolean isOccupied = isTileOccupied(position);
		if (isOccupied)
			this.reservedPositions.add(position);
		
		return !isOccupied;
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
		return isTileOccupied(position.x(), position.y());
	}

	private boolean isTileOccupied(int x, int y) {
		for (Unit unit : Game.getInstance().unitsOnTile(x, y))
			if (unit.getType().isBuilding())
				return true;

		return false;
	}

	
}
