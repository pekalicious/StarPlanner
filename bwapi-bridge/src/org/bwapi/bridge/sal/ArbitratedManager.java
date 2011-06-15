package org.bwapi.bridge.sal;

import java.util.Map;

import org.bwapi.bridge.BridgeRuntimeException;
import org.bwapi.bridge.model.Unit;

/**
 * Base class for arbitrated managers
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public abstract class ArbitratedManager implements Controller<Unit, Double> {

    protected static <T> void decrementMapValue(Map<T, Integer> map, T key) {
        Integer val = map.get(key);
        if (val == null) {
            throw new BridgeRuntimeException("Key not found");
        }
        map.put(key, val - 1);
    }
    
    protected static <T> void incrementMapValue(Map<T, Integer> map, T key) {
        Integer val = map.get(key);
        if (val == null) {
            throw new BridgeRuntimeException("Key not found");
        }
        map.put(key, val + 1);
    }

    protected final Arbitrator<Unit, Double> arbitrator;
    
    protected ArbitratedManager(Arbitrator<Unit, Double> arbitrator) {
        this.arbitrator = arbitrator;
    }
}
