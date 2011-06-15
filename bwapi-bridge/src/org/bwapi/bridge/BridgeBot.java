package org.bwapi.bridge;

import org.bwapi.bridge.model.Player;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

/**
 * Interface to handle bot messages
 * 
 * @author Chad Retz
 */
public interface BridgeBot {
    
    /**
     * Called when this bot (and the game) is started. All initialization
     * code should be in here, not in the constructor
     */
    void onStart();
    
    /**
     * Called when this bot (and the game) has ended
     * 
     * @param isWinner
     */
    void onEnd(boolean isWinner);
    
    /**
     * Called every frame of the game
     */
    void onFrame();
    
    /**
     * Called when some text is entered and {@link org.bwapi.bridge.model.Flag#USER_INPUT}
     * is enabled
     * 
     * @param string
     * @return
     */
    boolean onSendText(String string);
    
    /**
     * Called when a player leaves the game
     * 
     * @param player
     * 
     * @since 0.3
     */
    void onPlayerLeft(Player player);
    
    /**
     * Called when a nuke is detected
     * 
     * @param target
     * 
     * @since 0.3
     */
    void onNukeDetect(Position target);
    
    /**
     * Called when a unit is created
     * 
     * @param unit
     * 
     * @since 0.3
     */
    void onUnitCreate(Unit unit);
    
    /**
     * Called when a unit is destroyed
     * 
     * @param unit
     * 
     * @since 0.3
     */
    void onUnitDestroy(Unit unit);
    
    /**
     * Called when a unit morphs
     * 
     * @param unit
     * 
     * @since 0.3
     */
    void onUnitMorph(Unit unit);
    
    /**
     * Called when a unit is shown
     * 
     * @param unit
     * 
     * @since 0.3
     */
    void onUnitShow(Unit unit);
    
    /**
     * Called when a unit becomes hidden
     * 
     * @param unit
     * 
     * @since 0.3
     */
    void onUnitHide(Unit unit);
    
    /**
     * Called when a unit switches sides
     * 
     * @param unit
     * 
     * @since 0.3
     */
    void onUnitRenegade(Unit unit);
}
