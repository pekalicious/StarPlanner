package org.bwapi.bridge.model;

import java.util.Map;

import org.bwapi.bridge.swig.bridgeConstants;


/**
 * Latency
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Misc#Latency::Enum">Latency</a>
 * 
 * @author Chad Retz
 */
public enum Latency implements BwapiEnumerate {
    
    SINGLE_PLAYER(bridgeConstants.SinglePlayer),
    LAN_LOW(bridgeConstants.LanLow),
    LAN_MEDIUM(bridgeConstants.LanMedium),
    LAN_HIGH(bridgeConstants.LanHigh),
    BATTLENET_LOW(bridgeConstants.BattlenetLow),
    BATTLENET_MEDIUM(bridgeConstants.BattlenetMedium),
    BATTLENET_HIGH(bridgeConstants.BattlenetHigh);
    
    private static final Map<Integer, Latency> allValues =
        Builder.buildMap(Latency.values());
    
    public static Map<Integer, Latency> getAllValues() {
        return allValues;
    }
    
    private final int bwapiOrdinal;
    
    private Latency(int bwapiOrdinal) {
        this.bwapiOrdinal = bwapiOrdinal;
    }
    
    @Override
    public int getBwapiOrdinal() {
        return bwapiOrdinal;
    }
}
