package org.bwapi.unit;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.unit.model.BroodwarGameType;
import org.bwapi.unit.model.BroodwarMap;
import org.bwapi.unit.model.BroodwarPlayer;

/**
 * Simple POJO representing test information for
 * a bot execution
 * 
 * @author Chad Retz
 */
public class BwapiTestInformation {

    private String starcraftFolder;
    private String chaosLauncherFolder;
    private Class<? extends BridgeBot> bridgeBotClass;
    private BroodwarMap map;
    private BroodwarGameType gameType;
    private BroodwarPlayer[] players;
    private boolean consideredOverWhenUnitsGone = false;

    public BwapiTestInformation() {
    }

    public BwapiTestInformation(String starcraftFolder,
            String chaosLauncherFolder, Class<? extends BridgeBot> bridgeBotClass, 
            BroodwarMap map, BroodwarGameType gameType, BroodwarPlayer... players) {
        this(starcraftFolder, chaosLauncherFolder, bridgeBotClass, map, gameType, 
                false, players);
    }
    
    public BwapiTestInformation(String starcraftFolder,
            String chaosLauncherFolder, Class<? extends BridgeBot> bridgeBotClass, 
            BroodwarMap map, BroodwarGameType gameType, 
            boolean consideredOverWhenUnitsGone, BroodwarPlayer... players) {
        this.starcraftFolder = starcraftFolder;
        this.chaosLauncherFolder = chaosLauncherFolder;
        this.bridgeBotClass = bridgeBotClass;
        this.map = map;
        this.gameType = gameType;
        this.consideredOverWhenUnitsGone = consideredOverWhenUnitsGone;
        this.players = players;
    }

    public String getStarcraftFolder() {
        return starcraftFolder;
    }

    public void setStarcraftFolder(String starcraftFolder) {
        this.starcraftFolder = starcraftFolder;
    }

    public String getChaosLauncherFolder() {
        return chaosLauncherFolder;
    }

    public void setChaosLauncherFolder(String chaosLauncherFolder) {
        this.chaosLauncherFolder = chaosLauncherFolder;
    }

    public Class<? extends BridgeBot> getBridgeBotClass() {
        return bridgeBotClass;
    }
    
    public void setBridgeBotClass(Class<? extends BridgeBot> bridgeBotClass) {
        this.bridgeBotClass = bridgeBotClass;
    }

    public BroodwarMap getMap() {
        return map;
    }

    public void setMap(BroodwarMap map) {
        this.map = map;
    }
    
    public BroodwarGameType getGameType() {
        return gameType;
    }
    
    public void setGameType(BroodwarGameType gameType) {
        this.gameType = gameType;
    }
    
    public boolean isConsideredOverWhenUnitsGone() {
        return consideredOverWhenUnitsGone;
    }
    
    public void setConsideredOverWhenUnitsGone(
            boolean consideredOverWhenUnitsGone) {
        this.consideredOverWhenUnitsGone = consideredOverWhenUnitsGone;
    }
    
    public BroodwarPlayer[] getPlayers() {
        return players;
    }
    
    public void setPlayers(BroodwarPlayer[] players) {
        this.players = players;
    }
}
