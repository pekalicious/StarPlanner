package org.bwapi.bridge.model;

import java.util.Map;

import org.bwapi.bridge.swig.bridgeConstants;


/**
 * Flag
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Misc#Flag::Enum">Flag</a>
 * 
 * @author Chad Retz
 */
public enum Flag implements BwapiEnumerate {

    COMPLETE_MAP_INFORMATION(bridgeConstants.CompleteMapInformation),
    USER_INPUT(bridgeConstants.UserInput);
    
    private static final Map<Integer, Flag> allValues =
        Builder.buildMap(Flag.values());
    
    public static Map<Integer, Flag> getAllValues() {
        return allValues;
    }
    
    private final int bwapiOrdinal;
    
    private Flag(int bwapiOrdinal) {
        this.bwapiOrdinal = bwapiOrdinal;
    }
    
    @Override
    public int getBwapiOrdinal() {
        return bwapiOrdinal;
    }
}
