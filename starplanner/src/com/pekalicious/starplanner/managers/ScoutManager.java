package com.pekalicious.starplanner.managers;

import org.bwapi.bridge.model.Bwta;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Position;

import com.pekalicious.Logger;
import com.pekalicious.starplanner.StarBlackboard;

/**
 * This class manages the scouting squad.
 * 
 * WARNING: THIS DOES NOT WORK YET!!!!
 * 
 * @author Panagiotis Peikidis
 */
public class ScoutManager {
	enum State {
		Scout, RunningAway
	}

	private Game game;
	private StarBlackboard blackBoard;
	private State state;

	public ScoutManager(Game game, StarBlackboard blackBoard) {
		this.game = game;
		this.blackBoard = blackBoard;
		this.state = State.Scout;
	}

	int runCounter = runThreshold;
	static int runThreshold = 600;
	int previousHitPoints;
	public void update() {
		if (blackBoard.scout == null || blackBoard.scout.getHitPoints() <= 0) {
			//blackBoard.scout = UnitUtils.getIdleWorker(game, blackBoard);
			if (blackBoard.scout == null) {
				Logger.Debug("Could no assign scout!\n", 1);
				return;
			}
			previousHitPoints = blackBoard.scout.getHitPoints();
		}

		if (blackBoard.scout.getHitPoints() < previousHitPoints) {
			if (state == State.RunningAway) {
				runCounter = runThreshold;
				previousHitPoints = blackBoard.scout.getHitPoints();
			}else{
				Logger.Debug("I'm being attacked!\n", 1);
				state = State.RunningAway;
				previousHitPoints = blackBoard.scout.getHitPoints();
			}
		}
		
		switch (state) {
			case Scout:
				Position enemyPos = Bwta.getStartPosition(game.enemy()).getPosition();
				blackBoard.scout.rightClick(enemyPos);
			break;
			case RunningAway:
				Position basePos = Bwta.getStartPosition(game.self()).getPosition();
				blackBoard.scout.rightClick(basePos);
				if (--runCounter == 0) {
					runCounter = runThreshold;
					state = State.Scout;
				}
			break;
		}

		/*
		double maxDist = 100;
		double closest = Double.MAX_VALUE;
		Unit closestUnit = null;
		Position pos = blackBoard.scout.getPosition();
		for (Unit enemy : game.enemy().getUnits()) {
			if (enemy.getType().isBuilding()) continue;
			if (enemy.getType().isAddon()) continue;
			double dist = enemy.getDistance(blackBoard.scout);
			if (dist < closest) {
				closestUnit = enemy;
				closest = dist;
			}
		}
		
		if (closestUnit != null) {
			int distDiff = new Double(maxDist - closest).intValue();
			if (distDiff > 0) {
				Logger.Debug("Closest: " + closest + ", DistDiff = " + distDiff +"\n", 1);
				blackBoard.scout.rightClick(new Position(pos.x() + distDiff, pos.y() + distDiff));
			}
		}
		*/
	}
}
