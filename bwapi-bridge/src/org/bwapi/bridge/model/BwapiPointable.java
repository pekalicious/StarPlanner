package org.bwapi.bridge.model;

/**
 * Base class for objects containing pointers.
 * For internal use only
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public interface BwapiPointable {
    
    long getCPtr();
}
