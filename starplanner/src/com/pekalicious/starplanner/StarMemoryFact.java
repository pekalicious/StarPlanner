package com.pekalicious.starplanner;

import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.agent.WorkingMemoryFact;

public class StarMemoryFact extends WorkingMemoryFact {

	public Position basePosition;
	public TilePosition baseTilePosition;
	
	public double confidence;
	
	public Unit enemyUnit;
	public Position enemyLocation;
	public TilePosition enemyTileLocation;
	public UnitType enemyType;
	public int enemyTypeCount;
	
	public UnitType needType;
	public int needTypeCount;
	
	public StarMemoryFact(int type) {
		super(type);
	}

}
