package org.bwapi.unit.model;

/**
 * Representation of a player in Broodwar
 * 
 * @author Chad Retz
 */
public class BroodwarPlayer {

    private final boolean me;
    private final BroodwarRace race;
    
    public BroodwarPlayer(boolean me, BroodwarRace race) {
        this.me = me;
        this.race = race;
    }
    
    public boolean isMe() {
        return me;
    }
    
    public BroodwarRace getRace() {
        return race;
    }
}
