package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_TilePosition;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__TilePosition_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_TilePosition_t;
import org.bwapi.bridge.swig.SWIG_TilePosition;
import org.bwapi.bridge.swig.TilePositionSet;
import org.bwapi.bridge.swig.TilePositionSetIterator;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Tile position
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Misc">TilePosition</a>
 * 
 * @author Chad Retz
 */
public final class TilePosition extends BwapiObject implements Comparable<TilePosition> {
    
    private static final Transformer<TilePosition, SWIG_TilePosition> FROM = 
        new Transformer<TilePosition, SWIG_TilePosition>() {
            @Override
            public SWIG_TilePosition transform(TilePosition position) {
                return position.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_TilePosition, TilePosition> TO = 
        new Transformer<SWIG_TilePosition, TilePosition>() {
            @Override
            public TilePosition transform(SWIG_TilePosition position) {
                return new TilePosition(position);
            }
        };

    public static final TilePosition INVALID = new TilePosition(bridge.getTilePositionInvalid());
    public static final TilePosition NONE = new TilePosition(bridge.getTilePositionNone());
    public static final TilePosition UNKNOWN = new TilePosition(bridge.getTilePositionUnknown());
    
    static Set<TilePosition> newSet(SWIGTYPE_p_std__setT_BWAPI__TilePosition_t nativeSet) {
        return new NativeSet<TilePosition, SWIG_TilePosition>(
                SWIG_TilePosition.class, TilePositionSetIterator.class, 
                nativeSet, TilePositionSet.class, FROM, TO);
    }
    
    //hope this works...
    static Set<TilePosition> newSet(SWIGTYPE_p_std__setT_TilePosition_t nativeSet) {
        return newSet(new SWIGTYPE_p_std__setT_BWAPI__TilePosition_t(
                nativeSet.getCPtr(), false));
    }

    private final SWIG_TilePosition position;
    
    TilePosition(SWIG_TilePosition position) {
        this.position = position;
    }
    
    TilePosition(SWIGTYPE_p_TilePosition pointer) {
        this(new SWIG_TilePosition(pointer.getCPtr(), false));
    }
    
    public TilePosition() {
        this(new SWIG_TilePosition());
    }
    
    public TilePosition(Position position) {
        this(new SWIG_TilePosition(position.getOriginalObject()));
    }
    
    public TilePosition(int x, int y) {
        this(new SWIG_TilePosition(x, y));
    }
    
    @Override
    SWIG_TilePosition getOriginalObject() {
        return position;
    }
    
    SWIGTYPE_p_TilePosition getPointer() {
        return new SWIGTYPE_p_TilePosition(position.getCPtr(), false);
    }

    public boolean opEquals(TilePosition other) {
        return position.opEquals(other.getOriginalObject());
    }

    public boolean opNotEquals(TilePosition other) {
        return position.opNotEquals(other.getOriginalObject());
    }

    public boolean opLessThan(TilePosition other) {
        return position.opLessThan(other.getOriginalObject());
    }

    public TilePosition opPlus(TilePosition other) {
        return new TilePosition(position.opPlus(other.getOriginalObject()));
    }

    public TilePosition opMinus(TilePosition other) {
        return new TilePosition(position.opMinus(other.getOriginalObject()));
    }

    public TilePosition opAdd(TilePosition other) {
        return new TilePosition(position.opAdd(other.getOriginalObject()));
    }

    public TilePosition opSubtract(TilePosition other) {
        return new TilePosition(position.opSubtract(other.getOriginalObject()));
    }

    public double getDistance(TilePosition other) {
        return position.getDistance(other.getOriginalObject());
    }

    public double getLength() {
        return position.getLength();
    }

    public boolean isValid() {
        return position.isValid();
    }
    
    //TODO: add util to set int from pointer
    
    public int x() {
        return position.xConst();
    }

    public int y() {
        return position.yConst();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof TilePosition &&
                opEquals((TilePosition) obj);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x();
        result = prime * result + y();
        return result;
    }
    
    @Override
    public int compareTo(TilePosition o) {
        if (opEquals(o)) {
            return 0;
        } else if (opLessThan(o)) {
            return -1;
        } else {
            return 1;
        }
    }
}
