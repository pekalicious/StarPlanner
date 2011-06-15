package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.DamageTypeSet;
import org.bwapi.bridge.swig.DamageTypeSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__DamageType_t;
import org.bwapi.bridge.swig.SWIG_DamageType;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;


/**
 * Damage type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/DamageType">DamageType</a>
 * 
 * @author Chad Retz
 */
public final class DamageType extends BwapiComparable<DamageType> {
    
    private static final Transformer<DamageType, SWIG_DamageType> FROM = 
        new Transformer<DamageType, SWIG_DamageType>() {
            @Override
            public SWIG_DamageType transform(DamageType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_DamageType, DamageType> TO = 
        new Transformer<SWIG_DamageType, DamageType>() {
            @Override
            public DamageType transform(SWIG_DamageType type) {
                return new DamageType(type);
            }
        };
    
    public static final DamageType INDEPENDENT = new DamageType(bridge.getDamageTypeIndependent());
    public static final DamageType EXPLOSIVE = new DamageType(bridge.getExplosive());
    public static final DamageType CONCUSSIVE = new DamageType(bridge.getConcussive());
    public static final DamageType NORMAL = new DamageType(bridge.getDamageTypeNormal());
    public static final DamageType IGNORE_ARMOR = new DamageType(bridge.getIgnore_Armor());
    public static final DamageType NONE = new DamageType(bridge.getDamageTypeNone());
    public static final DamageType UNKNOWN = new DamageType(bridge.getDamageTypeUnknown());
    
    public static Set<DamageType> allDamageTypes() {
        return newSet(bridge.allDamageTypes());
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
    public static DamageType getDamageType(String name) {
        throw new UnsupportedOperationException();
    }
    
    static Set<DamageType> newSet(SWIGTYPE_p_std__setT_BWAPI__DamageType_t nativeSet) {
        return new NativeSet<DamageType, SWIG_DamageType>(
                SWIG_DamageType.class, DamageTypeSetIterator.class, 
                nativeSet, DamageTypeSet.class, FROM, TO);
    }
    
    private final SWIG_DamageType damageType;
    
    DamageType(SWIG_DamageType damageType) {
        this.damageType = damageType;
    }
    
    public DamageType() {
        this(new SWIG_DamageType());
    }

    public DamageType(int id) {
        this(new SWIG_DamageType(id));
    }

    public DamageType(DamageType other) {
        this(new SWIG_DamageType(other.getOriginalObject()));
    }
    
    @Override
    SWIG_DamageType getOriginalObject() {
        return damageType;
    }

    @Override
    public DamageType opAssign(DamageType other) {
        return new DamageType(damageType.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(DamageType other) {
        return damageType.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(DamageType other) {
        return damageType.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(DamageType other) {
        return damageType.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return damageType.getID();
    }

    public String getName() {
        return damageType.getName();
    }
}
