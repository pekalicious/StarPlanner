package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.RegionSet;
import org.bwapi.bridge.swig.RegionSetIterator;
import org.bwapi.bridge.swig.RegionSpacelessSet;
import org.bwapi.bridge.swig.RegionSpacelessSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_Region;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWTA__Region_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_Region_p_t;
import org.bwapi.bridge.swig.SWIG_Region;
import org.cretz.swig.collection.NativeSet;

/**
 * Region
 * 
 * @see <a href="http://code.google.com/p/bwta/wiki/Region">Region</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.2
 */
public final class Region extends BwapiObject {
    
    private static final Transformer<Region, SWIG_Region> FROM = 
        new Transformer<Region, SWIG_Region>() {
            @Override
            public SWIG_Region transform(Region region) {
                return region.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Region, Region> TO = 
        new Transformer<SWIG_Region, Region>() {
            @Override
            public Region transform(SWIG_Region region) {
                return new Region(region);
            }
        };
        
    private static final Transformer<Region, SWIGTYPE_p_Region> FROM_SPACELESS = 
        new Transformer<Region, SWIGTYPE_p_Region>() {
            @Override
            public SWIGTYPE_p_Region transform(Region region) {
                return region.getPointer();
            }
        };
        
    private static final Transformer<SWIGTYPE_p_Region, Region> TO_SPACELESS = 
        new Transformer<SWIGTYPE_p_Region, Region>() {
            @Override
            public Region transform(SWIGTYPE_p_Region region) {
                return new Region(region);
            }
        };
        
    static Set<Region> newSet(SWIGTYPE_p_std__setT_BWTA__Region_p_t nativeSet) {
        return new NativeSet<Region, SWIG_Region>(
                SWIG_Region.class, RegionSetIterator.class, 
                nativeSet, RegionSet.class, FROM, TO);
    }
        
    static Set<Region> newSet(SWIGTYPE_p_std__setT_Region_p_t nativeSet) {
        return new NativeSet<Region, SWIGTYPE_p_Region>(
                SWIGTYPE_p_Region.class, RegionSpacelessSetIterator.class, 
                nativeSet, RegionSpacelessSet.class, FROM_SPACELESS, TO_SPACELESS);
    }

    private final SWIG_Region region;

    Region(SWIG_Region region) {
        this.region = region;
    }
    
    Region(SWIGTYPE_p_Region region) {
        this(new SWIG_Region(region.getCPtr(), false));
    }
    
    SWIG_Region getOriginalObject() {
        return region;
    }
    
    SWIGTYPE_p_Region getPointer() {
        return new SWIGTYPE_p_Region(region.getCPtr(), false);
    }

    public Polygon getPolygon() {
        return new Polygon(region.getPolygon());
    }

    public Position getCenter() {
        return new Position(region.getCenter());
    }

    public Set<Chokepoint> getChokepoints() {
        return Chokepoint.newSet(region.getChokepoints());
    }

    public Set<BaseLocation> getBaseLocations() {
        return BaseLocation.newSet(region.getBaseLocations());
    }
}
