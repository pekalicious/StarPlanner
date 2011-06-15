package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__WeaponType_t;
import org.bwapi.bridge.swig.SWIG_DamageType;
import org.bwapi.bridge.swig.SWIG_ExplosionType;
import org.bwapi.bridge.swig.SWIG_TechType;
import org.bwapi.bridge.swig.SWIG_UnitType;
import org.bwapi.bridge.swig.SWIG_UpgradeType;
import org.bwapi.bridge.swig.SWIG_WeaponType;
import org.bwapi.bridge.swig.WeaponTypeSet;
import org.bwapi.bridge.swig.WeaponTypeSetIterator;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Weapon type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/WeaponType">WeaponType</a>
 * 
 * @author Chad Retz
 */
public final class WeaponType extends BwapiComparable<WeaponType> {
    
    private static final Transformer<WeaponType, SWIG_WeaponType> FROM = 
        new Transformer<WeaponType, SWIG_WeaponType>() {
            @Override
            public SWIG_WeaponType transform(WeaponType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_WeaponType, WeaponType> TO = 
        new Transformer<SWIG_WeaponType, WeaponType>() {
            @Override
            public WeaponType transform(SWIG_WeaponType type) {
                return new WeaponType(type);
            }
        };
    
    public static final WeaponType GUASS_RIFLE = new WeaponType(bridge.getGauss_Rifle());
    public static final WeaponType C_10_CANISTER_RIFLE = new WeaponType(bridge.getC_10_Canister_Rifle());
    public static final WeaponType FRAGMENTATION_GRENADE = new WeaponType(bridge.getWeaponTypeFragmentation_Grenade());
    public static final WeaponType SPIDER_MINES = new WeaponType(bridge.getWeaponTypeSpider_Mines());
    public static final WeaponType TWIN_AUTOCANNONS = new WeaponType(bridge.getTwin_Autocannons());
    public static final WeaponType HELLFIRE_MISSILE_PACK = new WeaponType(bridge.getHellfire_Missile_Pack());
    public static final WeaponType ARCLITE_CANNON = new WeaponType(bridge.getArclite_Cannon());
    public static final WeaponType FUSION_CUTTER = new WeaponType(bridge.getFusion_Cutter());
    public static final WeaponType GEMENI_MISSILES = new WeaponType(bridge.getWeaponTypeGemini_Missiles());
    public static final WeaponType BURST_LASERS = new WeaponType(bridge.getWeaponTypeBurst_Lasers());
    public static final WeaponType ATS_LASER_BATTERY = new WeaponType(bridge.getATS_Laser_Battery());
    public static final WeaponType ATA_LASER_BATTERY = new WeaponType(bridge.getATA_Laser_Battery());
    public static final WeaponType FLAME_THROWER = new WeaponType(bridge.getFlame_Thrower());
    public static final WeaponType ARCLITE_SHOCK_CANNON = new WeaponType(bridge.getArclite_Shock_Cannon());
    public static final WeaponType LONGBOLT_MISSILE = new WeaponType(bridge.getWeaponTypeLongbolt_Missile());
    public static final WeaponType CLAWS = new WeaponType(bridge.getClaws());
    public static final WeaponType NEEDLE_SPINES = new WeaponType(bridge.getNeedle_Spines());
    public static final WeaponType KAISER_BLADES = new WeaponType(bridge.getKaiser_Blades());
    public static final WeaponType TOXIC_SPORES = new WeaponType(bridge.getToxic_Spores());
    public static final WeaponType SPINES = new WeaponType(bridge.getSpines());
    public static final WeaponType ACID_SPORE = new WeaponType(bridge.getWeaponTypeAcid_Spore());
    public static final WeaponType GLAVE_WURM = new WeaponType(bridge.getWeaponTypeGlave_Wurm());
    public static final WeaponType SEEKER_SPORES = new WeaponType(bridge.getWeaponTypeSeeker_Spores());
    public static final WeaponType SUBTERRANEAN_TENTACLE = new WeaponType(bridge.getSubterranean_Tentacle());
    public static final WeaponType SUICIDE_INFESTED_TERRAN = new WeaponType(bridge.getSuicide_Infested_Terran());
    public static final WeaponType SUICIDE_SCOURGE = new WeaponType(bridge.getSuicide_Scourge());
    public static final WeaponType PARTICLE_BEAM = new WeaponType(bridge.getParticle_Beam());
    public static final WeaponType PSI_BLADES = new WeaponType(bridge.getPsi_Blades());
    public static final WeaponType PHASE_DISRUPTOR = new WeaponType(bridge.getWeaponTypePhase_Disruptor());
    public static final WeaponType PSIONIC_SHOCKWAVE = new WeaponType(bridge.getPsionic_Shockwave());
    public static final WeaponType DUAL_PHOTON_BLASTERS = new WeaponType(bridge.getDual_Photon_Blasters());
    public static final WeaponType ANTI_MATTER_MISSILES = new WeaponType(bridge.getAnti_Matter_Missiles());
    public static final WeaponType PHASE_DISRUPTOR_CANNON = new WeaponType(bridge.getPhase_Disruptor_Cannon());
    public static final WeaponType PULSE_CANNON = new WeaponType(bridge.getWeaponTypePulse_Cannon());
    public static final WeaponType STS_PHOTON_CANNON = new WeaponType(bridge.getSTS_Photon_Cannon());
    public static final WeaponType STA_PHOTON_CANNON = new WeaponType(bridge.getSTA_Photon_Cannon());
    public static final WeaponType SCARAB = new WeaponType(bridge.getScarab());
    public static final WeaponType NEUTRON_FLARE = new WeaponType(bridge.getWeaponTypeNeutron_Flare());
    public static final WeaponType HALO_ROCKETS = new WeaponType(bridge.getWeaponTypeHalo_Rockets());
    public static final WeaponType CORROSIVE_ACID = new WeaponType(bridge.getWeaponTypeCorrosive_Acid());
    public static final WeaponType SUBTERRANEAN_SPINES = new WeaponType(bridge.getWeaponTypeSubterranean_Spines());
    public static final WeaponType WARP_BLADES = new WeaponType(bridge.getWarp_Blades());

    public static final WeaponType YAMATO_GUN = new WeaponType(bridge.getWeaponTypeYamato_Gun());
    public static final WeaponType NUCLEAR_STRIKE = new WeaponType(bridge.getWeaponTypeNuclear_Strike());
    public static final WeaponType LOCKDOWN = new WeaponType(bridge.getLockdown());
    public static final WeaponType EMP_SHOCKWAVE = new WeaponType(bridge.getEMP_Shockwave());
    public static final WeaponType IRRADIATE = new WeaponType(bridge.getWeaponTypeIrradiate());
    public static final WeaponType PARASITE = new WeaponType(bridge.getParasite());
    public static final WeaponType SPAWN_BROODLINGS = new WeaponType(bridge.getSpawn_Broodlings());
    public static final WeaponType ENSNARE = new WeaponType(bridge.getWeaponTypeEnsnare());
    public static final WeaponType DARK_SWARM = new WeaponType(bridge.getDark_Swarm());
    public static final WeaponType PLAGUE = new WeaponType(bridge.getWeaponTypePlague());
    public static final WeaponType CONSUME = new WeaponType(bridge.getWeaponTypeConsume());
    public static final WeaponType STASIS_FIELD = new WeaponType(bridge.getStasis_Field());
    public static final WeaponType PSIONIC_STORM = new WeaponType(bridge.getWeaponTypePsionic_Storm());
    public static final WeaponType DISRUPTION_WEB = new WeaponType(bridge.getDisruption_Web());
    public static final WeaponType RESTORATION = new WeaponType(bridge.getWeaponTypeRestoration());
    public static final WeaponType MIND_CONTROL = new WeaponType(bridge.getWeaponTypeMind_Control());
    public static final WeaponType FEEDBACK = new WeaponType(bridge.getFeedback());
    public static final WeaponType OPTICAL_FLARE = new WeaponType(bridge.getOptical_Flare());
    public static final WeaponType MAELSTROM = new WeaponType(bridge.getMaelstrom());

    public static final WeaponType NONE = new WeaponType(bridge.getWeaponTypeNone());
    public static final WeaponType UNKNOWN = new WeaponType(bridge.getWeaponTypeUnknown());
    
    public static Set<WeaponType> allWeaponTypes() {
        return newSet(bridge.allWeaponTypes());
    }
    
    public static Set<WeaponType> normalWeaponTypes() {
        return newSet(bridge.normalWeaponTypes());
    }
    
    public static Set<WeaponType> specialWeaponTypes() {
        return newSet(bridge.specialWeaponTypes());
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
    public static WeaponType getWeaponType(String name) {
        throw new UnsupportedOperationException();
    }
    
    static Set<WeaponType> newSet(SWIGTYPE_p_std__setT_BWAPI__WeaponType_t nativeSet) {
        return new NativeSet<WeaponType, SWIG_WeaponType>(
                SWIG_WeaponType.class, WeaponTypeSetIterator.class, 
                nativeSet, WeaponTypeSet.class, FROM, TO);
    }

    private final SWIG_WeaponType weaponType;
    
    WeaponType(SWIG_WeaponType weaponType) {
        this.weaponType = weaponType;
    }
    
    public WeaponType() {
        this(new SWIG_WeaponType());
    }

    public WeaponType(int id) {
        this(new SWIG_WeaponType(id));
    }

    public WeaponType(WeaponType other) {
        this(new SWIG_WeaponType(other.getOriginalObject()));
    }
    
    @Override
    SWIG_WeaponType getOriginalObject() {
        return weaponType;
    }

    @Override
    public WeaponType opAssign(WeaponType other) {
        return new WeaponType(weaponType.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(WeaponType other) {
        return weaponType.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(WeaponType other) {
        return weaponType.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(WeaponType other) {
        return weaponType.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return weaponType.getID();
    }

    public String getName() {
        return weaponType.getName();
    }

    public TechType getTech() {
        SWIG_TechType type = weaponType.getTech();
        return type == null ? null : new TechType(type);
    }

    public UnitType whatUses() {
        SWIG_UnitType type = weaponType.whatUses();
        return type == null ? null : new UnitType(type);
    }

    public int damageAmount() {
        return weaponType.damageAmount();
    }

    public int damageBonus() {
        return weaponType.damageBonus();
    }

    public int damageCooldown() {
        return weaponType.damageCooldown();
    }

    public int damageFactor() {
        return weaponType.damageFactor();
    }

    public UpgradeType upgradeType() {
        SWIG_UpgradeType type = weaponType.upgradeType();
        return type == null ? null : new UpgradeType(type);
    }

    public DamageType damageType() {
        SWIG_DamageType type = weaponType.damageType();
        return type == null ? null : new DamageType(type);
    }

    public ExplosionType explosionType() {
        SWIG_ExplosionType type = weaponType.explosionType();
        return type == null ? null : new ExplosionType(type);
    }

    public int minRange() {
        return weaponType.minRange();
    }

    public int maxRange() {
        return weaponType.maxRange();
    }

    public int innerSplashRadius() {
        return weaponType.innerSplashRadius();
    }

    public int medianSplashRadius() {
        return weaponType.medianSplashRadius();
    }

    public int outerSplashRadius() {
        return weaponType.outerSplashRadius();
    }

    public boolean targetsAir() {
        return weaponType.targetsAir();
    }

    public boolean targetsGround() {
        return weaponType.targetsGround();
    }

    public boolean targetsMechanical() {
        return weaponType.targetsMechanical();
    }

    public boolean targetsOrganic() {
        return weaponType.targetsOrganic();
    }

    public boolean targetsNonBuilding() {
        return weaponType.targetsNonBuilding();
    }

    public boolean targetsNonRobotic() {
        return weaponType.targetsNonRobotic();
    }

    public boolean targetsTerrain() {
        return weaponType.targetsTerrain();
    }

    public boolean targetsOrgOrMech() {
        return weaponType.targetsOrgOrMech();
    }

    public boolean targetsOwn() {
        return weaponType.targetsOwn();
    }

}
