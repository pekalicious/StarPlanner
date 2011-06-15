package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__UnitSizeType_t;
import org.bwapi.bridge.swig.SWIG_UnitSizeType;
import org.bwapi.bridge.swig.UnitSizeTypeSet;
import org.bwapi.bridge.swig.UnitSizeTypeSetIterator;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Unit size type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/UnitSizeType">UnitSizeType</a>
 * 
 * @author Chad Retz
 */
public final class UnitSizeType extends BwapiComparable<UnitSizeType> {
    
    private static final Transformer<UnitSizeType, SWIG_UnitSizeType> FROM = 
        new Transformer<UnitSizeType, SWIG_UnitSizeType>() {
            @Override
            public SWIG_UnitSizeType transform(UnitSizeType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_UnitSizeType, UnitSizeType> TO = 
        new Transformer<SWIG_UnitSizeType, UnitSizeType>() {
            @Override
            public UnitSizeType transform(SWIG_UnitSizeType type) {
                return new UnitSizeType(type);
            }
        };

    public static final UnitSizeType INDEPENDENT = new UnitSizeType(bridge.getUnitSizeTypeIndependent());
    public static final UnitSizeType SMALL = new UnitSizeType(bridge.getSmall());
    public static final UnitSizeType MEDIUM = new UnitSizeType(bridge.getMedium());
    public static final UnitSizeType LARGE = new UnitSizeType(bridge.getLarge());
    public static final UnitSizeType NONE = new UnitSizeType(bridge.getUnitSizeTypeNone());
    public static final UnitSizeType UNKNOWN = new UnitSizeType(bridge.getUnitSizeTypeUnknown());
    
    public static Set<UnitSizeType> allUnitSizeTypes() {
        return newSet(bridge.allUnitSizeTypes());
    }
    
    /**
     * Not implemented
     * 
     * @param name
     * @return
     * 
     * @deprecated Not implemented
     */
    @Deprecated
    public static UnitSizeType getUnitSizeType(String name) {
        throw new UnsupportedOperationException();
    }
    
    static Set<UnitSizeType> newSet(SWIGTYPE_p_std__setT_BWAPI__UnitSizeType_t nativeSet) {
        return new NativeSet<UnitSizeType, SWIG_UnitSizeType>(
                SWIG_UnitSizeType.class, UnitSizeTypeSetIterator.class, 
                nativeSet, UnitSizeTypeSet.class, FROM, TO);
    }
    
    private final SWIG_UnitSizeType type;
    
    UnitSizeType(SWIG_UnitSizeType type) {
        this.type = type;
    }

    public UnitSizeType() {
        this(new SWIG_UnitSizeType());
    }

    public UnitSizeType(int id) {
        this(new SWIG_UnitSizeType(id));
    }

    public UnitSizeType(UnitSizeType other) {
        this(new SWIG_UnitSizeType(other.getOriginalObject()));
    }
    
    @Override
    SWIG_UnitSizeType getOriginalObject() {
        return type;
    }

    @Override
    public UnitSizeType opAssign(UnitSizeType other) {
        return new UnitSizeType(type.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(UnitSizeType other) {
        return type.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(UnitSizeType other) {
        return type.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(UnitSizeType other) {
        return type.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return type.getID();
    }

    public String getName() {
        return type.getName();
    }
}
