package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_UpgradeType;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__UpgradeType_const_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__UpgradeType_t;
import org.bwapi.bridge.swig.SWIG_UnitType;
import org.bwapi.bridge.swig.SWIG_UpgradeType;
import org.bwapi.bridge.swig.UpgradeTypeConstantSet;
import org.bwapi.bridge.swig.UpgradeTypeConstantSetIterator;
import org.bwapi.bridge.swig.UpgradeTypeSet;
import org.bwapi.bridge.swig.UpgradeTypeSetIterator;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Upgrade type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/UpgradeType">UpgradeType</a>
 * 
 * @author Chad Retz
 */
public final class UpgradeType extends BwapiComparable<UpgradeType> {

    private static final Transformer<UpgradeType, SWIG_UpgradeType> FROM = 
        new Transformer<UpgradeType, SWIG_UpgradeType>() {
            @Override
            public SWIG_UpgradeType transform(UpgradeType upgradeType) {
                return upgradeType.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_UpgradeType, UpgradeType> TO = 
        new Transformer<SWIG_UpgradeType, UpgradeType>() {
            @Override
            public UpgradeType transform(SWIG_UpgradeType upgradeType) {
                return new UpgradeType(upgradeType);
            }
        };

    public static final UpgradeType TERRAN_INFANTRY_ARMOR = new UpgradeType(bridge.getTerran_Infantry_Armor());
    public static final UpgradeType TERRAN_VEHICLE_PLATING = new UpgradeType(bridge.getTerran_Vehicle_Plating());
    public static final UpgradeType TERRAN_SHIP_PLATING = new UpgradeType(bridge.getTerran_Ship_Plating());
    public static final UpgradeType ZERG_CARAPACE = new UpgradeType(bridge.getZerg_Carapace());
    public static final UpgradeType ZERG_FLYER_CARAPACE = new UpgradeType(bridge.getZerg_Flyer_Carapace());
    public static final UpgradeType PROTOSS_ARMOR = new UpgradeType(bridge.getProtoss_Armor());
    public static final UpgradeType PROTOSS_PLATING = new UpgradeType(bridge.getProtoss_Plating());
    public static final UpgradeType TERRAN_INFANTRY_WEAPONS = new UpgradeType(bridge.getTerran_Infantry_Weapons());
    public static final UpgradeType TERRAN_VEHICLE_WEAPONS = new UpgradeType(bridge.getTerran_Vehicle_Weapons());
    public static final UpgradeType TERRAN_SHIP_WEAPONS = new UpgradeType(bridge.getTerran_Ship_Weapons());
    public static final UpgradeType ZERG_MELEE_ATTACKS = new UpgradeType(bridge.getZerg_Melee_Attacks());
    public static final UpgradeType ZERG_MISSLE_ATTACKS = new UpgradeType(bridge.getZerg_Missile_Attacks());
    public static final UpgradeType ZERG_FLYER_ATTACKS = new UpgradeType(bridge.getZerg_Flyer_Attacks());
    public static final UpgradeType PROTOSS_GROUND_WEAPONS = new UpgradeType(bridge.getProtoss_Ground_Weapons());
    public static final UpgradeType PROTOSS_AIR_WEAPONS = new UpgradeType(bridge.getProtoss_Air_Weapons());
    public static final UpgradeType PROTOSS_PLASMA_SHIELDS = new UpgradeType(bridge.getProtoss_Plasma_Shields());
    public static final UpgradeType U_238_SHELLS = new UpgradeType(bridge.getU_238_Shells());
    public static final UpgradeType ION_THRUSTERS = new UpgradeType(bridge.getIon_Thrusters());
    public static final UpgradeType TITAN_REACTOR = new UpgradeType(bridge.getTitan_Reactor());
    public static final UpgradeType OCULAR_IMPLANTS = new UpgradeType(bridge.getOcular_Implants());
    public static final UpgradeType MOEBIUS_REACTOR = new UpgradeType(bridge.getMoebius_Reactor());
    public static final UpgradeType APOLLO_REACTOR = new UpgradeType(bridge.getApollo_Reactor());
    public static final UpgradeType COLOSSUS_REACTOR = new UpgradeType(bridge.getColossus_Reactor());
    public static final UpgradeType VENTRAL_SACS = new UpgradeType(bridge.getVentral_Sacs());
    public static final UpgradeType ANTENNAE = new UpgradeType(bridge.getAntennae());
    public static final UpgradeType PNEUMATIZED_CARAPACE = new UpgradeType(bridge.getPneumatized_Carapace());
    public static final UpgradeType METABOLIC_BOOST = new UpgradeType(bridge.getMetabolic_Boost());
    public static final UpgradeType ADRENAL_GLANDS = new UpgradeType(bridge.getAdrenal_Glands());
    public static final UpgradeType MUSCULAR_AUGMENTS = new UpgradeType(bridge.getMuscular_Augments());
    public static final UpgradeType GROOVED_SPINES = new UpgradeType(bridge.getGrooved_Spines());
    public static final UpgradeType GAMETE_MEIOSIS = new UpgradeType(bridge.getGamete_Meiosis());
    public static final UpgradeType METASYNAPTIC_NODE = new UpgradeType(bridge.getMetasynaptic_Node());
    public static final UpgradeType SINGULARITY_CHARGE = new UpgradeType(bridge.getSingularity_Charge());
    public static final UpgradeType LEG_ENHANCEMENTS = new UpgradeType(bridge.getLeg_Enhancements());
    public static final UpgradeType SCARAB_DAMAGE = new UpgradeType(bridge.getScarab_Damage());
    public static final UpgradeType REAVER_CAPACITY = new UpgradeType(bridge.getReaver_Capacity());
    public static final UpgradeType GRAVITIC_DRIVE = new UpgradeType(bridge.getGravitic_Drive());
    public static final UpgradeType SENSOR_ARRAY = new UpgradeType(bridge.getSensor_Array());
    public static final UpgradeType GRAVITIC_BOOSTERS = new UpgradeType(bridge.getGravitic_Boosters());
    public static final UpgradeType KHAYDARIN_AMULET = new UpgradeType(bridge.getKhaydarin_Amulet());
    public static final UpgradeType APIAL_SENSORS = new UpgradeType(bridge.getApial_Sensors());
    public static final UpgradeType GRAVITIC_THRUSTERS = new UpgradeType(bridge.getGravitic_Thrusters());
    public static final UpgradeType CARRIER_CAPACITY = new UpgradeType(bridge.getCarrier_Capacity());
    public static final UpgradeType KHAYDARIN_CORE = new UpgradeType(bridge.getKhaydarin_Core());
    public static final UpgradeType ARGUS_JEWEL = new UpgradeType(bridge.getArgus_Jewel());
    public static final UpgradeType ARGUS_TALISMAN = new UpgradeType(bridge.getArgus_Talisman());
    public static final UpgradeType CADUCEUS_REACTOR = new UpgradeType(bridge.getCaduceus_Reactor());
    public static final UpgradeType CHITINOUS_PLATING = new UpgradeType(bridge.getChitinous_Plating());
    public static final UpgradeType ANABOLIC_SYNTHESIS = new UpgradeType(bridge.getAnabolic_Synthesis());
    public static final UpgradeType CHARON_BOOSTER = new UpgradeType(bridge.getCharon_Booster());
    public static final UpgradeType NONE = new UpgradeType(bridge.getUpgradeTypeNone());
    public static final UpgradeType UNKNOWN = new UpgradeType(bridge.getUpgradeTypeUnknown());
    
    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public static Set<UpgradeType> allUpgradeTypes() {
        return newSet(bridge.allUpgradeTypes());
    }
    
    static Set<UpgradeType> newSet(SWIGTYPE_p_std__setT_BWAPI__UpgradeType_t nativeSet) {
        return new NativeSet<UpgradeType, SWIG_UpgradeType>(
                SWIG_UpgradeType.class, UpgradeTypeSetIterator.class, 
                nativeSet, UpgradeTypeSet.class, FROM, TO);
    }
    
    static Set<UpgradeType> newSet(SWIGTYPE_p_std__setT_BWAPI__UpgradeType_const_p_t nativeSet) {
        return new NativeSet<UpgradeType, SWIG_UpgradeType>(
                SWIG_UpgradeType.class, UpgradeTypeConstantSetIterator.class, 
                nativeSet, UpgradeTypeConstantSet.class, FROM, TO);
    }
    
    private final SWIG_UpgradeType upgradeType;
    
    UpgradeType(SWIG_UpgradeType upgradeType) {
        this.upgradeType = upgradeType;
    }

    UpgradeType(SWIGTYPE_p_UpgradeType pointer) {
        this(new SWIG_UpgradeType(pointer.getCPtr(), false));
    }
    
    public UpgradeType() {
        this(new SWIG_UpgradeType());
    }

    public UpgradeType(int id) {
        this(new SWIG_UpgradeType(id));
    }

    public UpgradeType(UpgradeType other) {
        this(new SWIG_UpgradeType(other.getOriginalObject()));
    }
    
    SWIGTYPE_p_UpgradeType getPointer() {
        return new SWIGTYPE_p_UpgradeType(upgradeType.getCPtr(), false);
    }
    
    @Override
    SWIG_UpgradeType getOriginalObject() {
        return upgradeType;
    }

    @Override
    public UpgradeType opAssign(UpgradeType other) {
        return new UpgradeType(upgradeType.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(UpgradeType other) {
        return upgradeType.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(UpgradeType other) {
        return upgradeType.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(UpgradeType other) {
        return upgradeType.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return upgradeType.getID();
    }

    public String getName() {
        return upgradeType.getName();
    }

    public Race getRace() {
        return new Race(upgradeType.getRace());
    }

    public int mineralPriceBase() {
        return upgradeType.mineralPriceBase();
    }

    public int mineralPriceFactor() {
        return upgradeType.mineralPriceFactor();
    }

    public int gasPriceBase() {
        return upgradeType.gasPriceBase();
    }

    public int gasPriceFactor() {
        return upgradeType.gasPriceFactor();
    }

    public int upgradeTimeBase() {
        return upgradeType.upgradeTimeBase();
    }

    public int upgradeTimeFactor() {
        return upgradeType.upgradeTimeFactor();
    }

    public int maxRepeats() {
        return upgradeType.maxRepeats();
    }

    public UnitType whatUpgrades() {
        SWIG_UnitType type = upgradeType.whatUpgrades();
        return type == null ? null : new UnitType(type);
    }
    
    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public Set<UnitType> whatUses() {
        return UnitType.newSet(upgradeType.whatUses());
    }

}
