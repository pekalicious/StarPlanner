package org.bwapi.bridge.model;

import java.util.Set;

import org.bwapi.bridge.swig.SWIGTYPE_p_BaseLocation;
import org.bwapi.bridge.swig.bridge;

/**
 * BWTA
 * 
 * @see <a href="http://code.google.com/p/bwta/wiki/BWTA">BWTA</a>
 * 
 * @author Chad Retz
 *
 * @since 0.2
 */
public final class Bwta {

    public static void readMap() {
        bridge.readMap();
    }

    public static void analyze() {
        bridge.analyze();
    }
    
    public static Set<Region> getRegions() {
        return Region.newSet(bridge.getRegions());
    }
    
    public static Set<Chokepoint> getChokepoints() {
        return Chokepoint.newSet(bridge.getChokepoints());
    }
    
    public static Set<BaseLocation> getBaseLocations() {
        return BaseLocation.newSet(bridge.getBaseLocations());
    }
    
    public static Set<Polygon> getPolygons() {
        return Polygon.newSet(bridge.getUnwalkablePolygons());
    }
    
    public static Position getNearestWalkablePosition(Position position) {
        return new Position(bridge.getNearestUnwalkablePosition(position.getOriginalObject()));
    }
    
    public static BaseLocation getStartPosition(Player player) {
        SWIGTYPE_p_BaseLocation location = bridge.
                getStartLocation(player.getOriginalObject());
        return location == null ? null : new BaseLocation(location);
    }
    
    public static BaseLocation getNearestBaseLocation(TilePosition position) {
        SWIGTYPE_p_BaseLocation location = bridge.
                getNearestBaseLocation(position.getOriginalObject());
        return location == null ? null : new BaseLocation(location);
    }
    
    private Bwta() {
    }
}
