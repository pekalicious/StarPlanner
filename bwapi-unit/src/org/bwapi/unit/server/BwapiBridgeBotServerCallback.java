package org.bwapi.unit.server;

/**
 * Callback for handling bot server issues
 * 
 * @author Chad Retz
 */
public interface BwapiBridgeBotServerCallback {

    void onFailure(Throwable error);
    
    void onEnd();
}
