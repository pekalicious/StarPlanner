package org.bwapi.unit.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI remote interface for the bot server
 * 
 * @author Chad Retz
 */
public interface BwapiBridgeBotServer extends Remote {
    
    String NAME = "BwapiBridgeBotServer";
    int PORT = 12345;

    String getBridgeBotClass() throws RemoteException;
    
    void onEnd() throws RemoteException;
    
    void onError(Throwable throwable) throws RemoteException;
    
    boolean isConsideredOverWhenUnitsGone() throws RemoteException;
}
