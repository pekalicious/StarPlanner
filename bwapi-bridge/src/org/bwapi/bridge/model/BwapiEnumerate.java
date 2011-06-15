package org.bwapi.bridge.model;

import java.util.HashMap;
import java.util.Map;

/**
 * BWAPI enumerate. Simply returns the underlying ordinal 
 * that BWAPI uses
 * 
 * @author Chad Retz
 */
public interface BwapiEnumerate {

    int getBwapiOrdinal();
    
    /**
     * Builder for mapping enumerates
     * 
     * @author Chad Retz
     */
    static final class Builder {
        public static <T extends Enum<T> & BwapiEnumerate> Map<Integer, T> buildMap(
                T[] values) {
            Map<Integer, T> ret = new HashMap<Integer, T>(values.length);
            for (T value : values) {
                ret.put(value.getBwapiOrdinal(), value);
            }
            return ret;
        }
        
        private Builder() {
        }
    }
}
