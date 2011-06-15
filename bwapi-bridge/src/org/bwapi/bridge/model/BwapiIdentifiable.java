package org.bwapi.bridge.model;

/**
 * Base class for all objects with an ID
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public abstract class BwapiIdentifiable extends BwapiObject {
    
    public abstract int getID();
    
    @Override
    public int hashCode() {
        return getID();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + 
            " [" + getID() + "]";
    }
}
