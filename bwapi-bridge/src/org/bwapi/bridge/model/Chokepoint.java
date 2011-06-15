package org.bwapi.bridge.model;

import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.ChokepointGetRegionsPair;
import org.bwapi.bridge.swig.ChokepointGetSidesPair;
import org.bwapi.bridge.swig.ChokepointSet;
import org.bwapi.bridge.swig.ChokepointSetIterator;
import org.bwapi.bridge.swig.ChokepointSpacelessSet;
import org.bwapi.bridge.swig.ChokepointSpacelessSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_Chokepoint;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWTA__Chokepoint_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_Chokepoint_p_t;
import org.bwapi.bridge.swig.SWIG_Chokepoint;
import org.bwapi.bridge.swig.SWIG_Position;
import org.bwapi.bridge.swig.SWIG_Region;
import org.cretz.swig.collection.NativeSet;

/**
 * Chokepoint
 * 
 * @see <a href="http://code.google.com/p/bwta/wiki/Chokepoint">Chokepoint</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.2
 */
public final class Chokepoint extends BwapiObject {
    
    private static final Transformer<Chokepoint, SWIG_Chokepoint> FROM = 
        new Transformer<Chokepoint, SWIG_Chokepoint>() {
            @Override
            public SWIG_Chokepoint transform(Chokepoint chokepoint) {
                return chokepoint.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Chokepoint, Chokepoint> TO = 
        new Transformer<SWIG_Chokepoint, Chokepoint>() {
            @Override
            public Chokepoint transform(SWIG_Chokepoint chokepoint) {
                return new Chokepoint(chokepoint);
            }
        };

    private static final Transformer<Chokepoint, SWIGTYPE_p_Chokepoint> FROM_SPACELESS = 
        new Transformer<Chokepoint, SWIGTYPE_p_Chokepoint>() {
            @Override
            public SWIGTYPE_p_Chokepoint transform(Chokepoint chokepoint) {
                return chokepoint.getPointer();
            }
        };
        
    private static final Transformer<SWIGTYPE_p_Chokepoint, Chokepoint> TO_SPACELESS = 
        new Transformer<SWIGTYPE_p_Chokepoint, Chokepoint>() {
            @Override
            public Chokepoint transform(SWIGTYPE_p_Chokepoint chokepoint) {
                return new Chokepoint(chokepoint);
            }
        };
        
    static Set<Chokepoint> newSet(SWIGTYPE_p_std__setT_BWTA__Chokepoint_p_t nativeSet) {
        return new NativeSet<Chokepoint, SWIG_Chokepoint>(
                SWIG_Chokepoint.class, ChokepointSetIterator.class, 
                nativeSet, ChokepointSet.class, FROM, TO);
    }
        
    static Set<Chokepoint> newSet(SWIGTYPE_p_std__setT_Chokepoint_p_t nativeSet) {
        return new NativeSet<Chokepoint, SWIGTYPE_p_Chokepoint>(
                SWIGTYPE_p_Chokepoint.class, ChokepointSpacelessSetIterator.class, 
                nativeSet, ChokepointSpacelessSet.class, FROM_SPACELESS, TO_SPACELESS);
    }

    private final SWIG_Chokepoint chokepoint;

    Chokepoint(SWIG_Chokepoint chokepoint) {
        this.chokepoint = chokepoint;
    }
    
    Chokepoint(SWIGTYPE_p_Chokepoint chokepoint) {
        this(new SWIG_Chokepoint(chokepoint.getCPtr(), false));
    }

    @Override
    SWIG_Chokepoint getOriginalObject() { 
        return chokepoint;
    }
    
    SWIGTYPE_p_Chokepoint getPointer() {
        return new SWIGTYPE_p_Chokepoint(chokepoint.getCPtr(), false);
    }

    public Entry<Region, Region> getRegions() {
        final ChokepointGetRegionsPair pair = chokepoint.getRegions();
        return new Entry<Region, Region>() {
            @Override
            public Region getKey() {
                SWIG_Region region = pair.getFirst();
                return region == null ? null : new Region(region);
            }

            @Override
            public Region getValue() {
                SWIG_Region region = pair.getSecond();
                return region == null ? null : new Region(region);
            }

            @Override
            public Region setValue(Region value) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public Entry<Position, Position> getSides() {
        final ChokepointGetSidesPair pair = chokepoint.getSides();
        return new Entry<Position, Position>() {
            @Override
            public Position getKey() {
                SWIG_Position position = pair.getFirst();
                return position == null ? null : new Position(position);
            }

            @Override
            public Position getValue() {
                SWIG_Position position = pair.getSecond();
                return position == null ? null : new Position(position);
            }

            @Override
            public Position setValue(Position value) {
                throw new UnsupportedOperationException();
            }
        };        
    }

    public Position getCenter() {
        return new Position(chokepoint.getCenter());
    }

    public double getWidth() {
        return chokepoint.getWidth();
    }

}
