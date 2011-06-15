package org.bwapi.unit.model;

import java.io.InputStream;

/**
 * Interface for loading a Broodwar map
 * 
 * @author Chad Retz
 */
public interface BroodwarMap {

    String getName();
    
    InputStream open();
}
