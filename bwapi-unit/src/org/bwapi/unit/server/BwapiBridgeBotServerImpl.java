package org.bwapi.unit.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.bwapi.bridge.BridgeBot;

/**
 * Implementation of {@link BwapiBridgeBotServer}
 * 
 * @author Chad Retz
 */
@SuppressWarnings("serial")
public final class BwapiBridgeBotServerImpl extends UnicastRemoteObject
        implements BwapiBridgeBotServer {
    
    private static final Registry registry;
    
    static {
        try {
            registry = LocateRegistry.createRegistry(PORT);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static void startServer(Class<? extends BridgeBot> bridgeBotClass,
            BwapiBridgeBotServerCallback callback,
            boolean consideredOverWhenUnitsGone) throws Exception {
        new BwapiBridgeBotServerImpl(callback, bridgeBotClass, consideredOverWhenUnitsGone);
    }
    
    private final BwapiBridgeBotServerCallback callback;
    private final Class<? extends BridgeBot> bridgeBotClass;
    private final boolean consideredOverWhenUnitsGone;
    
    private BwapiBridgeBotServerImpl(BwapiBridgeBotServerCallback callback,
            Class<? extends BridgeBot> bridgeBotClass, 
            boolean consideredOverWhenUnitsGone) throws RemoteException {
        this.callback = callback;
        this.bridgeBotClass = bridgeBotClass;
        this.consideredOverWhenUnitsGone = consideredOverWhenUnitsGone;
        registry.rebind(NAME, this);
    }
    
    @Override
    public String getBridgeBotClass() throws RemoteException {
        return bridgeBotClass.getName();
    }
    
    private void unbind() throws RemoteException {
        //unbind...
        try {
            registry.unbind(NAME);
        } catch (NotBoundException e) {
            throw new RemoteException("Unable to unbind", e);
        }
    }

    @Override
    public void onEnd() throws RemoteException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onEnd();
            }
        }).start();
        unbind();
    }

    @Override
    public void onError(Throwable throwable) throws RemoteException {
        callback.onFailure(throwable);
        unbind();
    }
    
    @Override
    public boolean isConsideredOverWhenUnitsGone() throws RemoteException {
        return consideredOverWhenUnitsGone;
    }
}
