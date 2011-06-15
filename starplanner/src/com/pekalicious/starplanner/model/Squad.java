package com.pekalicious.starplanner.model;

import java.util.ArrayList;
import java.util.List;

import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

import com.pekalicious.starplanner.managers.OrderStatus;
import com.pekalicious.starplanner.managers.SquadOrder;

public class Squad {
	public SquadOrder order;
	public OrderStatus orderStatus;
	public List<Unit> units;
	public Position destination;
	public Unit leader;
	public boolean enemyNearDestination;
	
	public int gotoTimer;
	public Unit enemyTarget;
	
	public Squad() {
		orderStatus = OrderStatus.Waiting;
		order = SquadOrder.None;
		units = new ArrayList<Unit>();
	}
}
