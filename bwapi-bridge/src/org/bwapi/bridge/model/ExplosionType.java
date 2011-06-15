package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.ExplosionTypeSet;
import org.bwapi.bridge.swig.ExplosionTypeSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__ExplosionType_t;
import org.bwapi.bridge.swig.SWIG_ExplosionType;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Explosion type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/ExplosionType">ExplosionType</a>
 * 
 * @author Chad Retz
 */
public final class ExplosionType extends BwapiComparable<ExplosionType> {
    
    private static final Transformer<ExplosionType, SWIG_ExplosionType> FROM = 
        new Transformer<ExplosionType, SWIG_ExplosionType>() {
            @Override
            public SWIG_ExplosionType transform(ExplosionType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_ExplosionType, ExplosionType> TO = 
        new Transformer<SWIG_ExplosionType, ExplosionType>() {
            @Override
            public ExplosionType transform(SWIG_ExplosionType type) {
                return new ExplosionType(type);
            }
        };
    
    public static final ExplosionType NONE = new ExplosionType(bridge.getExplosionTypeNone());
    public static final ExplosionType NORMAL = new ExplosionType(bridge.getExplosionTypeNormal());
    public static final ExplosionType RADIAL_SPLASH = new ExplosionType(bridge.getRadial_Splash());
    public static final ExplosionType ENEMY_SPLASH = new ExplosionType(bridge.getEnemy_Splash());
    public static final ExplosionType LOCKDOWN = new ExplosionType(bridge.getExplosionTypeLockdown());
    public static final ExplosionType NUCLEAR_MISSILE = new ExplosionType(bridge.getNuclear_Missile());
    public static final ExplosionType PARASITE = new ExplosionType(bridge.getExplosionTypeParasite());
    public static final ExplosionType BROODLINGS = new ExplosionType(bridge.getBroodlings());
    public static final ExplosionType EMP_SHOCKWAVE = new ExplosionType(bridge.getExplosionTypeEMP_Shockwave());
    public static final ExplosionType IRRADIATE = new ExplosionType(bridge.getExplosionTypeIrradiate());
    public static final ExplosionType ENSNARE = new ExplosionType(bridge.getExplosionTypeEnsnare());
    public static final ExplosionType PLAGUE = new ExplosionType(bridge.getExplosionTypePlague());
    public static final ExplosionType STASIS_FIELD = new ExplosionType(bridge.getExplosionTypeStasis_Field());
    public static final ExplosionType DARK_SWARM = new ExplosionType(bridge.getExplosionTypeDark_Swarm());
    public static final ExplosionType CONSUME = new ExplosionType(bridge.getExplosionTypeConsume());
    public static final ExplosionType YAMATO_GUN = new ExplosionType(bridge.getExplosionTypeYamato_Gun());
    public static final ExplosionType RESTORATION = new ExplosionType(bridge.getExplosionTypeRestoration());
    public static final ExplosionType DISRUPTION_WEB = new ExplosionType(bridge.getExplosionTypeDisruption_Web());
    public static final ExplosionType CORROSIVE_ACID = new ExplosionType(bridge.getExplosionTypeCorrosive_Acid());
    public static final ExplosionType MIND_CONTROL = new ExplosionType(bridge.getExplosionTypeMind_Control());
    public static final ExplosionType FEEDBACK = new ExplosionType(bridge.getExplosionTypeFeedback());
    public static final ExplosionType OPTICAL_FLARE = new ExplosionType(bridge.getExplosionTypeOptical_Flare());
    public static final ExplosionType MAELSTROM = new ExplosionType(bridge.getExplosionTypeMaelstrom());
    public static final ExplosionType AIR_SPLASH = new ExplosionType(bridge.getAir_Splash());
    public static final ExplosionType UNKNOWN = new ExplosionType(bridge.getExplosionTypeUnknown());
    
    public static Set<ExplosionType> allExplosionTypes() {
        return newSet(bridge.allExplosionTypes());
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
    public static ExplosionType getExplosionType(String name) {
        throw new UnsupportedOperationException();
    }

    static Set<ExplosionType> newSet(SWIGTYPE_p_std__setT_BWAPI__ExplosionType_t nativeSet) {
        return new NativeSet<ExplosionType, SWIG_ExplosionType>(
                SWIG_ExplosionType.class, ExplosionTypeSetIterator.class, 
                nativeSet, ExplosionTypeSet.class, FROM, TO);
    }
    
    private final SWIG_ExplosionType explosionType;
    
    ExplosionType(SWIG_ExplosionType explosionType) {
        this.explosionType = explosionType;
    }

    public ExplosionType() {
        this(new SWIG_ExplosionType());
    }

    public ExplosionType(int id) {
        this(new SWIG_ExplosionType(id));
    }

    public ExplosionType(ExplosionType other) {
        this(new SWIG_ExplosionType(other.getOriginalObject()));
    }
    
    SWIG_ExplosionType getOriginalObject() {
        return explosionType;
    }

    @Override
    public ExplosionType opAssign(ExplosionType other) {
        return new ExplosionType(explosionType.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(ExplosionType other) {
        return explosionType.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(ExplosionType other) {
        return explosionType.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(ExplosionType other) {
        return explosionType.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return explosionType.getID();
    }

    public String getName() {
        return explosionType.getName();
    }
}
