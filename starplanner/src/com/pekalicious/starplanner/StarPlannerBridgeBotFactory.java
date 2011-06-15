package com.pekalicious.starplanner;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.bridge.BridgeBotFactory;
import org.bwapi.bridge.model.Game;

public class StarPlannerBridgeBotFactory implements BridgeBotFactory {

	@Override
	public BridgeBot getBridgeBot(Game game) {
		return new StarPlannerBridgeBot();
	}
}
