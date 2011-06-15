package org.bwapi.bridge.util;

import org.bwapi.bridge.BridgeBot;
import org.bwapi.bridge.model.Player;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

/**
 * Simple no-op bot
 * 
 * @author Chad Retz
 */
public class NopBot implements BridgeBot {

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd(boolean isWinner) {

    }

    @Override
    public void onFrame() {

    }

    @Override
    public boolean onSendText(String string) {
        return true;
    }

    @Override
    public void onNukeDetect(Position target) {
        
    }

    @Override
    public void onPlayerLeft(Player player) {

    }

    @Override
    public void onUnitCreate(Unit unit) {
        
    }

    @Override
    public void onUnitDestroy(Unit unit) {
        
    }

    @Override
    public void onUnitMorph(Unit unit) {
        
    }

    @Override
    public void onUnitShow(Unit unit) {
        
    }

    @Override
    public void onUnitHide(Unit unit) {
        
    }

    @Override
    public void onUnitRenegade(Unit unit) {
        
    }

}
