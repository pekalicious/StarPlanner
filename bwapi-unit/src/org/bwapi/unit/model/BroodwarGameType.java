package org.bwapi.unit.model;

/**
 * Representation of a game type in Broodwar
 * 
 * @author Chad Retz
 */
public enum BroodwarGameType {
    MELEE(315),
    FREE_FOR_ALL(332),
    USE_MAP_SETTINGS(350);
    
    private final int y;
    
    private BroodwarGameType(int y) {
        this.y = y;
    }
    
    public int getY() {
        return y;
    }
}
