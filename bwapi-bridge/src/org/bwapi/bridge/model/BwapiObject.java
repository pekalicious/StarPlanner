package org.bwapi.bridge.model;

/**
 * Base object for all BWAPI objects. Needed
 * for equals and hashCode implementation
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public abstract class BwapiObject {

    abstract BwapiPointable getOriginalObject();
    
    @Override
    public int hashCode() {
        return ((Long) getOriginalObject().getCPtr()).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass().isAssignableFrom(
                obj.getClass()) && hashCode() == obj.hashCode();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + 
            " [" + getOriginalObject().getCPtr() + "]";
    }
}
