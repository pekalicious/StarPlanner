package org.bwapi.bridge;

import org.bwapi.bridge.model.Game;

/**
 * Factory for loading a bot based on the given game. In order
 * to register a factory, use the <i>service provider</i> mechanism
 * as mentioned in {@link java.util.ServiceLoader}. If no factories
 * return a valid bot, the {@link org.bwapi.bridge.util.NopBot} is used.
 * 
 * @author Chad Retz
 */
public interface BridgeBotFactory {

    /**
     * Get the bot based on the given game. If the result is
     * null, the next factory is tried.
     * 
     * @param game
     * @return
     */
    BridgeBot getBridgeBot(Game game);
}
