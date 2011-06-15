package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_TechType;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__TechType_const_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__TechType_t;
import org.bwapi.bridge.swig.SWIG_TechType;
import org.bwapi.bridge.swig.SWIG_UnitType;
import org.bwapi.bridge.swig.SWIG_WeaponType;
import org.bwapi.bridge.swig.TechTypeConstantSet;
import org.bwapi.bridge.swig.TechTypeConstantSetIterator;
import org.bwapi.bridge.swig.TechTypeSet;
import org.bwapi.bridge.swig.TechTypeSetIterator;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;


/**
 * Tech type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/TechType">TechType</a>
 * 
 * @author Chad Retz
 */
public final class TechType extends BwapiComparable<TechType> {
    
    private static final Transformer<TechType, SWIG_TechType> FROM = 
        new Transformer<TechType, SWIG_TechType>() {
            @Override
            public SWIG_TechType transform(TechType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_TechType, TechType> TO = 
        new Transformer<SWIG_TechType, TechType>() {
            @Override
            public TechType transform(SWIG_TechType type) {
                return new TechType(type);
            }
        };
    
    public static final TechType STIM_PACK = new TechType(bridge.getStim_Packs());
    public static final TechType LOCKDOWN = new TechType(bridge.getTechTypeLockdown());
    public static final TechType EMP_SHOCKWAVE = new TechType(bridge.getTechTypeEMP_Shockwave());
    public static final TechType SPIDER_MINES = new TechType(bridge.getSpider_Mines());
    public static final TechType SCANNER_SWEEP = new TechType(bridge.getScanner_Sweep());
    public static final TechType TANK_SIEGE_MODE = new TechType(bridge.getTank_Siege_Mode());
    public static final TechType DEFENSIVE_MATRIX = new TechType(bridge.getDefensive_Matrix());
    public static final TechType IRRADIATE = new TechType(bridge.getTechTypeIrradiate());
    public static final TechType YAMATO_GUN = new TechType(bridge.getTechTypeYamato_Gun());
    public static final TechType CLOAKING_GUN = new TechType(bridge.getCloaking_Field());
    public static final TechType PERSONNEL_CLOAKING = new TechType(bridge.getPersonnel_Cloaking());
    public static final TechType BURROWING = new TechType(bridge.getBurrowing());
    public static final TechType INFESTATION = new TechType(bridge.getInfestation());
    public static final TechType SPAWN_BROODLINGS = new TechType(bridge.getTechTypeSpawn_Broodlings());
    public static final TechType DARK_SWARM = new TechType(bridge.getTechTypeDark_Swarm());
    public static final TechType PLAGUE = new TechType(bridge.getTechTypePlague());
    public static final TechType CONSUME = new TechType(bridge.getTechTypeConsume());
    public static final TechType ENSNARE = new TechType(bridge.getTechTypeEnsnare());
    public static final TechType PARASITE = new TechType(bridge.getTechTypeParasite());
    public static final TechType PSIONIC_STORM = new TechType(bridge.getTechTypePsionic_Storm());
    public static final TechType HALLUCINATION = new TechType(bridge.getHallucination());
    public static final TechType RECALL = new TechType(bridge.getRecall());
    public static final TechType STASIS_FIELD = new TechType(bridge.getTechTypeStasis_Field());
    public static final TechType ARCHON_WARP = new TechType(bridge.getArchon_Warp());
    public static final TechType RESTORATION = new TechType(bridge.getTechTypeRestoration());
    public static final TechType DISTRUPTION_WEB = new TechType(bridge.getTechTypeDisruption_Web());
    public static final TechType MIND_CONTROL = new TechType(bridge.getTechTypeMind_Control());
    public static final TechType DARK_ARCHON_MELD = new TechType(bridge.getDark_Archon_Meld());
    public static final TechType FEEDBACK = new TechType(bridge.getTechTypeFeedback());
    public static final TechType OPTICAL_FLARE = new TechType(bridge.getTechTypeOptical_Flare());
    public static final TechType MAELSTROM = new TechType(bridge.getTechTypeMaelstrom());
    public static final TechType LURKER_ASPECT = new TechType(bridge.getLurker_Aspect());
    public static final TechType HEALING = new TechType(bridge.getHealing());
    public static final TechType NONE = new TechType(bridge.getTechTypeNone());
    public static final TechType UNKNOWN = new TechType(bridge.getTechTypeUnknown());
    public static final TechType NUCLEAR_STRIKE = new TechType(bridge.getTechTypeNuclear_Strike());
    
    public static Set<TechType> allTechTypes() {
        return newSet(bridge.allTechTypes());
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
    public static TechType getTechType(String name) {
        throw new UnsupportedOperationException();
    }

    static Set<TechType> newSet(SWIGTYPE_p_std__setT_BWAPI__TechType_t nativeSet) {
        return new NativeSet<TechType, SWIG_TechType>(
                SWIG_TechType.class, TechTypeSetIterator.class, 
                nativeSet, TechTypeSet.class, FROM, TO);
    }
    
    static Set<TechType> newSet(SWIGTYPE_p_std__setT_BWAPI__TechType_const_p_t nativeSet) {
        return new NativeSet<TechType, SWIG_TechType>(
                SWIG_TechType.class, TechTypeConstantSetIterator.class, 
                nativeSet, TechTypeConstantSet.class, FROM, TO);
    }
    
    private final SWIG_TechType techType;
    
    TechType(SWIG_TechType techType) {
        this.techType = techType;
    }

    public TechType() {
        this(new SWIG_TechType());
    }

    public TechType(int id) {
        this(new SWIG_TechType(id));
    }

    public TechType(TechType other) {
        this(new SWIG_TechType(other.getOriginalObject()));
    }
    
    SWIG_TechType getOriginalObject() {
        return techType;
    }
    
    SWIGTYPE_p_TechType getPointer() {
        return new SWIGTYPE_p_TechType(techType.getCPtr(), false);
    }

    @Override
    public TechType opAssign(TechType other) {
        return new TechType(techType.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(TechType other) {
        return techType.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(TechType other) {
        return techType.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(TechType other) {
        return techType.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return techType.getID();
    }

    public String getName() {
        return techType.getName();
    }

    public int mineralPrice() {
        return techType.mineralPrice();
    }

    public int gasPrice() {
        return techType.gasPrice();
    }

    public int energyUsed() {
        return techType.energyUsed();
    }

    public UnitType whatResearches() {
        SWIG_UnitType what = techType.whatResearches();
        return new UnitType(what);
    }

    public WeaponType getWeapon() {
        SWIG_WeaponType type = techType.getWeapon();
        return type == null ? null : new WeaponType(type);
    }

    public Set<UnitType> whatUses() {
        return UnitType.newSet(techType.whatUses());
    }
}
