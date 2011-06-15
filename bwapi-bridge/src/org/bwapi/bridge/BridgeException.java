package org.bwapi.bridge;

/**
 * Checked exception that can occur 
 * inside the bridge
 * 
 * @author Chad Retz
 */
@SuppressWarnings("serial")
public class BridgeException extends Exception {

    public BridgeException() {
    }

    public BridgeException(String message) {
        super(message);
    }

    public BridgeException(Throwable cause) {
        super(cause);
    }

    public BridgeException(String message, Throwable cause) {
        super(message, cause);
    }

}
