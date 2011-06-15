package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.BaseLocationSet;
import org.bwapi.bridge.swig.BaseLocationSetIterator;
import org.bwapi.bridge.swig.BaseLocationSpacelessSet;
import org.bwapi.bridge.swig.BaseLocationSpacelessSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_BaseLocation;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWTA__BaseLocation_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BaseLocation_p_t;
import org.bwapi.bridge.swig.SWIG_BaseLocation;
import org.bwapi.bridge.swig.SWIG_Region;
import org.cretz.swig.collection.NativeSet;

/**
 * Base location
 * 
 * @see <a href="http://code.google.com/p/bwta/wiki/BaseLocation">BaseLocation</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.2
 */
public final class BaseLocation extends BwapiObject {
    
    private static final Transformer<BaseLocation, SWIG_BaseLocation> FROM = 
        new Transformer<BaseLocation, SWIG_BaseLocation>() {
            @Override
            public SWIG_BaseLocation transform(BaseLocation location) {
                return location.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_BaseLocation, BaseLocation> TO = 
        new Transformer<SWIG_BaseLocation, BaseLocation>() {
            @Override
            public BaseLocation transform(SWIG_BaseLocation location) {
                return new BaseLocation(location);
            }
        };
        
    private static final Transformer<BaseLocation, SWIGTYPE_p_BaseLocation> FROM_SPACELESS = 
        new Transformer<BaseLocation, SWIGTYPE_p_BaseLocation>() {
            @Override
            public SWIGTYPE_p_BaseLocation transform(BaseLocation location) {
                return location.getPointer();
            }
        };
        
    private static final Transformer<SWIGTYPE_p_BaseLocation, BaseLocation> TO_SPACELESS = 
        new Transformer<SWIGTYPE_p_BaseLocation, BaseLocation>() {
            @Override
            public BaseLocation transform(SWIGTYPE_p_BaseLocation location) {
                return new BaseLocation(location);
            }
        };
        
    static Set<BaseLocation> newSet(SWIGTYPE_p_std__setT_BWTA__BaseLocation_p_t nativeSet) {
        return new NativeSet<BaseLocation, SWIG_BaseLocation>(
                SWIG_BaseLocation.class, BaseLocationSetIterator.class, 
                nativeSet, BaseLocationSet.class, FROM, TO);
    }
    
    static Set<BaseLocation> newSet(SWIGTYPE_p_std__setT_BaseLocation_p_t nativeSet) {
        return new NativeSet<BaseLocation, SWIGTYPE_p_BaseLocation>(
                SWIGTYPE_p_BaseLocation.class, BaseLocationSpacelessSetIterator.class, 
                nativeSet, BaseLocationSpacelessSet.class, FROM_SPACELESS, TO_SPACELESS);
    }

    private final SWIG_BaseLocation location;

    BaseLocation(SWIG_BaseLocation location) {
        this.location = location;
    }
    
    BaseLocation(SWIGTYPE_p_BaseLocation location) {
        this(new SWIG_BaseLocation(location.getCPtr(), false));
    }
    
    @Override
    SWIG_BaseLocation getOriginalObject() {
        return location;
    }
    
    SWIGTYPE_p_BaseLocation getPointer() {
        return new SWIGTYPE_p_BaseLocation(location.getCPtr(), false);
    }

    public Position getPosition() {
        return new Position(location.getPosition());
    }

    public TilePosition getTilePosition() {
        return new TilePosition(location.getTilePosition());
    }

    public Region getRegion() {
        SWIG_Region region = location.getRegion();
        return region == null ? null : new Region(region);
    }

    public int minerals() {
        return location.minerals();
    }

    public int gas() {
        return location.gas();
    }

    public Set<Unit> getMinerals() {
        return Unit.newSet(location.getMinerals());
    }

    public Set<Unit> getStaticMinerals() {
        return Unit.newSet(location.getStaticMinerals());
    }

    public Set<Unit> getGeysers() {
        return Unit.newSet(location.getGeysers());
    }

    public double getGroundDistance(BaseLocation other) {
        return location.getGroundDistance(other.getOriginalObject());
    }

    public double getAirDistance(BaseLocation other) {
        return location.getAirDistance(other.getOriginalObject());
    }

    public boolean isIsland() {
        return location.isIsland();
    }

    public boolean isMineralOnly() {
        return location.isMineralOnly();
    }

    public boolean isStartLocation() {
        return location.isStartLocation();
    }
}
