package org.bwapi.bridge.model;

import java.util.Map;

import org.bwapi.bridge.swig.bridgeConstants;


/**
 * Coordinate type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Misc#::Enum">CoordinateType</a>
 * 
 * @author Chad Retz
 */
public enum CoordinateType implements BwapiEnumerate {
    
    SCREEN(bridgeConstants.Screen),
    MAP(bridgeConstants.Map),
    MOUSE(bridgeConstants.Mouse);
    
    private static final Map<Integer, CoordinateType> allValues =
        Builder.buildMap(CoordinateType.values());
    
    public static Map<Integer, CoordinateType> getAllValues() {
        return allValues;
    }
    
    private final int bwapiOrdinal;
    
    private CoordinateType(int bwapiOrdinal) {
        this.bwapiOrdinal = bwapiOrdinal;
    }
    
    @Override
    public int getBwapiOrdinal() {
        return bwapiOrdinal;
    }
}