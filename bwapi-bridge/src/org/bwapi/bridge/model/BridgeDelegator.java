package org.bwapi.bridge.model;

import java.util.ServiceLoader;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.bridge.BridgeBotFactory;
import org.bwapi.bridge.swig.SWIG_Player;
import org.bwapi.bridge.swig.SWIG_Position;
import org.bwapi.bridge.swig.SWIG_Unit;
import org.bwapi.bridge.util.NopBot;

/**
 * Delegation method to load the bot. For internal use only.
 * 
 * @author Chad Retz
 */
public final class BridgeDelegator {
    
    private static final ServiceLoader<BridgeBotFactory> SERVICE_LOADER =
        ServiceLoader.load(BridgeBotFactory.class);
    
    /**
     * Load the bot; if not bots are found, {@link NopBot} is used.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public static BridgeBot load() {
        //wait a minute, is my test factory on the classpath?
        try {
            Class<? extends BridgeBotFactory> factory = (Class<? extends BridgeBotFactory>) 
                    Class.forName("org.bwapi.unit.client.BwapiTestBotFactory");
            Game.getInstance().printf("Found test factory");
            //oh it is?
            try {
                BridgeBot bot = factory.newInstance().getBridgeBot(Game.getInstance());
                if (bot == null) {
                    Game.getInstance().printf("No test bot found!");
                } else {
                    return bot;
                }
            } catch (Exception e) {
                Game.getInstance().printf("Can't instantiate test: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            //who cares...
        }
        for (BridgeBotFactory factory : SERVICE_LOADER) {
            BridgeBot bot = factory.getBridgeBot(Game.getInstance());
            if (bot != null) {
                return bot;
            }
        }
        return new NopBot();
    }

    public static void onPlayerLeft(BridgeBot bot, long pointer) {
        bot.onPlayerLeft(new Player(new SWIG_Player(pointer, false)));
    }

    public static void onNukeDetect(BridgeBot bot, long pointer) {
        bot.onNukeDetect(new Position(new SWIG_Position(pointer, false)));
    }

    public static void onUnitCreate(BridgeBot bot, long pointer) {
        bot.onUnitCreate(new Unit(new SWIG_Unit(pointer, false)));
    }

    public static void onUnitDestroy(BridgeBot bot, long pointer) {
        bot.onUnitDestroy(new Unit(new SWIG_Unit(pointer, false)));
    }

    public static void onUnitMorph(BridgeBot bot, long pointer) {
        bot.onUnitMorph(new Unit(new SWIG_Unit(pointer, false)));
    }

    public static void onUnitShow(BridgeBot bot, long pointer) {
        bot.onUnitShow(new Unit(new SWIG_Unit(pointer, false)));
    }

    public static void onUnitHide(BridgeBot bot, long pointer) {
        bot.onUnitHide(new Unit(new SWIG_Unit(pointer, false)));
    }

    public static void onUnitRenegade(BridgeBot bot, long pointer) {
        bot.onUnitRenegade(new Unit(new SWIG_Unit(pointer, false)));
    }

    private BridgeDelegator() {
        
    }
}
