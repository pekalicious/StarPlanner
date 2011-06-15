package org.bwapi.unit.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.bridge.BridgeBotFactory;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Player;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;
import org.bwapi.unit.server.BwapiBridgeBotServer;

/**
 * Factory used for connecting to RMI and loading the test bot
 * 
 * @author Chad Retz
 */
public class BwapiTestBotFactory implements BridgeBotFactory {
    
    @Override
    public BridgeBot getBridgeBot(Game game) {
        //first I gotta connect to the server
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(BwapiBridgeBotServer.PORT);
        } catch (Exception e) {
            game.printf("Unable to connect: " + e.getMessage());
            return null;
        }
        final BwapiBridgeBotServer server;
        try {
            server = (BwapiBridgeBotServer) registry.lookup(BwapiBridgeBotServer.NAME);
        } catch (Exception e) {
            game.printf("Unable to find server: " + e.getMessage());
            return null;
        }
        final BridgeBot bot;
        final boolean consideredOverWhenUnitsGone;
        try {
            bot = (BridgeBot) Class.forName(server.getBridgeBotClass()).newInstance();
            consideredOverWhenUnitsGone = server.isConsideredOverWhenUnitsGone();
        } catch (Exception e) {
            game.printf("Unable to instantiate bot: " + e.getMessage());
            return null;
        }
        return new BridgeBot() {
            private boolean failed = false;
            
            private <T> T wrap(Callable<T> callable) {
                if (!failed) {
                    try {
                        return callable.call();
                    } catch (Throwable e) {
                        failed = true;
                        try {
                            server.onError(e);
                        } catch (RemoteException re) {
                            //who cares
                        }
                    }
                }
                return null;
            }
            
            @Override
            public void onStart() {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onStart();
                        return null;
                    }
                });
            }
            
            @Override
            public boolean onSendText(final String string) {
                return wrap(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return bot.onSendText(string);
                    }
                });
            }
            
            @Override
            public void onFrame() {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onFrame();
                        return null;
                    }
                });
            }
            
            @Override
            public void onEnd(final boolean isWinner) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onEnd(isWinner);
                        server.onEnd();
                        return null;
                    }
                });
            }

            @Override
            public void onNukeDetect(final Position target) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onNukeDetect(target);
                        return null;
                    }
                });
            }

            @Override
            public void onPlayerLeft(final Player player) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onPlayerLeft(player);
                        return null;
                    }
                });
            }

            @Override
            public void onUnitCreate(final Unit unit) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onUnitCreate(unit);
                        return null;
                    }
                });
            }

            @Override
            public void onUnitDestroy(final Unit unit) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onUnitDestroy(unit);
                        if (consideredOverWhenUnitsGone) {
                            //check me
                            if (Game.getInstance().self().getUnits().isEmpty()) {
                                onEnd(false);
                            } else {
                                //check enemies
                                Player me = Game.getInstance().self();
                                boolean enemyWithUnits = false;
                                for (Player player : Game.getInstance().getPlayers()) {
                                    if (!player.equals(me) && player.isEnemy(me)) {
                                        if (!player.getUnits().isEmpty()) {
                                            enemyWithUnits = true;
                                            break;
                                        }
                                    }
                                }
                                if (!enemyWithUnits) {
                                    onEnd(true);
                                }
                            }
                        }
                        return null;
                    }
                });
            }

            @Override
            public void onUnitHide(final Unit unit) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onUnitHide(unit);
                        return null;
                    }
                });
            }

            @Override
            public void onUnitMorph(final Unit unit) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onUnitMorph(unit);
                        return null;
                    }
                });
            }

            @Override
            public void onUnitRenegade(final Unit unit) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onUnitRenegade(unit);
                        return null;
                    }
                });
            }

            @Override
            public void onUnitShow(final Unit unit) {
                wrap(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        bot.onUnitShow(unit);
                        return null;
                    }
                });
            }            
        };
    }

}
