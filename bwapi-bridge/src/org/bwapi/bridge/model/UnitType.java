package org.bwapi.bridge.model;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.NOPTransformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_UnitType;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__listT_BWAPI__UnitType_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__mapT_BWAPI__UnitType_const_p_int_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__UnitType_const_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__UnitType_t;
import org.bwapi.bridge.swig.SWIG_TechType;
import org.bwapi.bridge.swig.SWIG_UnitType;
import org.bwapi.bridge.swig.SWIG_UpgradeType;
import org.bwapi.bridge.swig.SWIG_WeaponType;
import org.bwapi.bridge.swig.UnitTypeConstantSet;
import org.bwapi.bridge.swig.UnitTypeConstantSetIterator;
import org.bwapi.bridge.swig.UnitTypeList;
import org.bwapi.bridge.swig.UnitTypeListIterator;
import org.bwapi.bridge.swig.UnitTypeMap;
import org.bwapi.bridge.swig.UnitTypeMapIterator;
import org.bwapi.bridge.swig.UnitTypeSet;
import org.bwapi.bridge.swig.UnitTypeSetIterator;
import org.bwapi.bridge.swig.UnitTypeWhatBuildsPair;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeMap;
import org.cretz.swig.collection.NativeSet;

/**
 * Unit type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/UnitType">UnitType</a>
 * 
 * @author Chad Retz
 */
public final class UnitType extends BwapiComparable<UnitType> {
    
    private static final Transformer<UnitType, SWIG_UnitType> FROM = 
        new Transformer<UnitType, SWIG_UnitType>() {
            @Override
            public SWIG_UnitType transform(UnitType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_UnitType, UnitType> TO = 
        new Transformer<SWIG_UnitType, UnitType>() {
            @Override
            public UnitType transform(SWIG_UnitType type) {
                return new UnitType(type);
            }
        };

    private static final Transformer<UnitType, SWIGTYPE_p_UnitType> FROM_SPACELESS = 
        new Transformer<UnitType, SWIGTYPE_p_UnitType>() {
            @Override
            public SWIGTYPE_p_UnitType transform(UnitType type) {
                return type.getPointer();
            }
        };
        
    private static final Transformer<SWIGTYPE_p_UnitType, UnitType> TO_SPACELESS = 
        new Transformer<SWIGTYPE_p_UnitType, UnitType>() {
            @Override
            public UnitType transform(SWIGTYPE_p_UnitType type) {
                return new UnitType(type);
            }
        };
    
    public static final UnitType TERRAN_MARINE = new UnitType(bridge.getTerran_Marine());
    public static final UnitType TERRAN_GHOST = new UnitType(bridge.getTerran_Ghost());
    public static final UnitType TERRAN_VULTURE = new UnitType(bridge.getTerran_Vulture());
    public static final UnitType TERRAN_GOLIATH = new UnitType(bridge.getTerran_Goliath());
    public static final UnitType TERRAN_SIEGE_TANK_TANK_MODE = new UnitType(bridge.getTerran_Siege_Tank_Tank_Mode());
    public static final UnitType TERRAN_SCV = new UnitType(bridge.getTerran_SCV());
    public static final UnitType TERRAN_WRAITH = new UnitType(bridge.getTerran_Wraith());
    public static final UnitType TERRAN_SCIENCE_VESSEL = new UnitType(bridge.getTerran_Science_Vessel());
    public static final UnitType TERRAN_DROPSHIP = new UnitType(bridge.getTerran_Dropship());
    public static final UnitType TERRAN_BATTLECRUISER = new UnitType(bridge.getTerran_Battlecruiser());
    public static final UnitType TERRAN_VULTURE_SPIDER_MINE = new UnitType(bridge.getTerran_Vulture_Spider_Mine());
    public static final UnitType TERRAN_NUCLEAR_MISSILE = new UnitType(bridge.getTerran_Nuclear_Missile());
    public static final UnitType TERRAN_SIEGE_TANK_SIEGE_MODE = new UnitType(bridge.getTerran_Siege_Tank_Siege_Mode());
    public static final UnitType TERRAN_FIREBAT = new UnitType(bridge.getTerran_Firebat());
    public static final UnitType SPELL_SCANNER_SWEEP = new UnitType(bridge.getSpell_Scanner_Sweep());
    public static final UnitType TERRAN_MEDIC = new UnitType(bridge.getTerran_Medic());
    public static final UnitType ZERG_LARVA = new UnitType(bridge.getZerg_Larva());
    public static final UnitType ZERG_EGG = new UnitType(bridge.getZerg_Egg());
    public static final UnitType ZERG_ZERGLING = new UnitType(bridge.getZerg_Zergling());
    public static final UnitType ZERG_HYDRALISK = new UnitType(bridge.getZerg_Hydralisk());
    public static final UnitType ZERG_ULTRALISK = new UnitType(bridge.getZerg_Ultralisk());
    public static final UnitType ZERG_BROODLING = new UnitType(bridge.getZerg_Broodling());
    public static final UnitType ZERG_DRONE = new UnitType(bridge.getZerg_Drone());
    public static final UnitType ZERG_OVERLORD = new UnitType(bridge.getZerg_Overlord());
    public static final UnitType ZERG_MUTALISK = new UnitType(bridge.getZerg_Mutalisk());
    public static final UnitType ZERG_GUARDIAN = new UnitType(bridge.getZerg_Guardian());
    public static final UnitType ZERG_QUEEN = new UnitType(bridge.getZerg_Queen());
    public static final UnitType ZERG_DEFILER = new UnitType(bridge.getZerg_Defiler());
    public static final UnitType ZERG_SCOURGE = new UnitType(bridge.getZerg_Scourge());
    public static final UnitType ZERG_INFESTED_TERRAN = new UnitType(bridge.getZerg_Infested_Terran());
    public static final UnitType TERRAN_VALKYRIE = new UnitType(bridge.getTerran_Valkyrie());
    public static final UnitType ZERG_COCOON = new UnitType(bridge.getZerg_Cocoon());
    public static final UnitType PROTOSS_CORSAIR = new UnitType(bridge.getProtoss_Corsair());
    public static final UnitType PROTOSS_DARK_TEMPLAR = new UnitType(bridge.getProtoss_Dark_Templar());
    public static final UnitType ZERG_DEVOURER = new UnitType(bridge.getZerg_Devourer());
    public static final UnitType PROTOSS_DARK_ARCHON = new UnitType(bridge.getProtoss_Dark_Archon());
    public static final UnitType PROTOSS_PROBE = new UnitType(bridge.getProtoss_Probe());
    public static final UnitType PROTOSS_ZEALOT = new UnitType(bridge.getProtoss_Zealot());
    public static final UnitType PROTOSS_DRAGOON = new UnitType(bridge.getProtoss_Dragoon());
    public static final UnitType PROTOSS_HIGH_TEMPLAR = new UnitType(bridge.getProtoss_High_Templar());
    public static final UnitType PROTOSS_ARCHON = new UnitType(bridge.getProtoss_Archon());
    public static final UnitType PROTOSS_SHUTTLE = new UnitType(bridge.getProtoss_Shuttle());
    public static final UnitType PROTOSS_SCOUT = new UnitType(bridge.getProtoss_Scout());
    public static final UnitType PROTOSS_ARBITER = new UnitType(bridge.getProtoss_Arbiter());
    public static final UnitType PROTOSS_CARRIER = new UnitType(bridge.getProtoss_Carrier());
    public static final UnitType PROTOSS_INTERCEPTOR = new UnitType(bridge.getProtoss_Interceptor());
    public static final UnitType PROTOSS_REAVER = new UnitType(bridge.getProtoss_Reaver());
    public static final UnitType PROTOSS_OBSERVER = new UnitType(bridge.getProtoss_Observer());
    public static final UnitType PROTOSS_SCARAB = new UnitType(bridge.getProtoss_Scarab());
    public static final UnitType CRITTER_RHYNADON = new UnitType(bridge.getCritter_Rhynadon());
    public static final UnitType CRITTER_BENGALAAS = new UnitType(bridge.getCritter_Bengalaas());
    public static final UnitType CRITTER_SCANTID = new UnitType(bridge.getCritter_Scantid());
    public static final UnitType CRITTER_KAKARU = new UnitType(bridge.getCritter_Kakaru());
    public static final UnitType CRITTER_RAGNASAUR = new UnitType(bridge.getCritter_Ragnasaur());
    public static final UnitType CRITTER_URSADON = new UnitType(bridge.getCritter_Ursadon());
    public static final UnitType ZERG_LURKER_EGG = new UnitType(bridge.getZerg_Lurker_Egg());
    public static final UnitType ZERG_LURKER = new UnitType(bridge.getZerg_Lurker());
    public static final UnitType SPELL_DISRUPTION_WEB = new UnitType(bridge.getSpell_Disruption_Web());
    public static final UnitType TERRAN_COMMAND_CENTER = new UnitType(bridge.getTerran_Command_Center());
    public static final UnitType TERRAN_COMSAT_STATION = new UnitType(bridge.getTerran_Comsat_Station());
    public static final UnitType TERRAN_NUCLEAR_SILO = new UnitType(bridge.getTerran_Nuclear_Silo());
    public static final UnitType TERRAN_SUPPLY_DEPOT = new UnitType(bridge.getTerran_Supply_Depot());
    public static final UnitType TERRAN_REFINERY = new UnitType(bridge.getTerran_Refinery());
    public static final UnitType TERRAN_BARRACKS = new UnitType(bridge.getTerran_Barracks());
    public static final UnitType TERRAN_ACADEMY = new UnitType(bridge.getTerran_Academy());
    public static final UnitType TERRAN_FACTORY = new UnitType(bridge.getTerran_Factory());
    public static final UnitType TERRAN_STARPORT = new UnitType(bridge.getTerran_Starport());
    public static final UnitType TERRAN_CONTROL_TOWER = new UnitType(bridge.getTerran_Control_Tower());
    public static final UnitType TERRAN_SCIENCE_FACILITY = new UnitType(bridge.getTerran_Science_Facility());
    public static final UnitType TERRAN_COVERT_OPS = new UnitType(bridge.getTerran_Covert_Ops());
    public static final UnitType TERRAN_PHYSICS_LAB = new UnitType(bridge.getTerran_Physics_Lab());
    public static final UnitType TERRAN_MACHINE_SHOP = new UnitType(bridge.getTerran_Machine_Shop());
    public static final UnitType TERRAN_ENGINEERING_BAY = new UnitType(bridge.getTerran_Engineering_Bay());
    public static final UnitType TERRAN_ARMORY = new UnitType(bridge.getTerran_Armory());
    public static final UnitType TERRAN_MISSILE_TURRET = new UnitType(bridge.getTerran_Missile_Turret());
    public static final UnitType TERRAN_BUNKER = new UnitType(bridge.getTerran_Bunker());
    public static final UnitType SPECIAL_CRASHED_NORAD_II = new UnitType(bridge.getSpecial_Crashed_Norad_II());
    public static final UnitType SPECIAL_ION_CANNON = new UnitType(bridge.getSpecial_Ion_Cannon());
    public static final UnitType ZERG_INFESTED_COMMAND_CENTER = new UnitType(bridge.getZerg_Infested_Command_Center());
    public static final UnitType ZERG_HATCHERY = new UnitType(bridge.getZerg_Hatchery());
    public static final UnitType ZERG_LAIR = new UnitType(bridge.getZerg_Lair());
    public static final UnitType ZERG_HIVE = new UnitType(bridge.getZerg_Hive());
    public static final UnitType ZERG_NYDUS_CANAL = new UnitType(bridge.getZerg_Nydus_Canal());
    public static final UnitType ZERG_HYDRALISK_DEN = new UnitType(bridge.getZerg_Hydralisk_Den());
    public static final UnitType ZERG_DEFILER_MOUND = new UnitType(bridge.getZerg_Defiler_Mound());
    public static final UnitType ZERG_GREATER_SPIRE = new UnitType(bridge.getZerg_Greater_Spire());
    public static final UnitType ZERG_QUEENS_NEST = new UnitType(bridge.getZerg_Queens_Nest());
    public static final UnitType ZERG_EVOLUTION_CHAMBER = new UnitType(bridge.getZerg_Evolution_Chamber());
    public static final UnitType ZERG_ULTRALISK_CAVERN = new UnitType(bridge.getZerg_Ultralisk_Cavern());
    public static final UnitType ZERG_SPIRE = new UnitType(bridge.getZerg_Spire());
    public static final UnitType ZERG_SPAWNING_POOL = new UnitType(bridge.getZerg_Spawning_Pool());
    public static final UnitType ZERG_CREEP_COLONY = new UnitType(bridge.getZerg_Creep_Colony());
    public static final UnitType ZERG_SPORE_COLONY = new UnitType(bridge.getZerg_Spore_Colony());
    public static final UnitType ZERG_SUNKEN_COLONY = new UnitType(bridge.getZerg_Sunken_Colony());
    public static final UnitType SPECIAL_OVERMIND_WITH_SHELL = new UnitType(bridge.getSpecial_Overmind_With_Shell());
    public static final UnitType SPECIAL_OVERMIND = new UnitType(bridge.getSpecial_Overmind());
    public static final UnitType ZERG_EXTRACTOR = new UnitType(bridge.getZerg_Extractor());
    public static final UnitType SPECIAL_MATURE_CHRYSALIS = new UnitType(bridge.getSpecial_Mature_Chrysalis());
    public static final UnitType SPECIAL_CELEBRATE = new UnitType(bridge.getSpecial_Cerebrate());
    public static final UnitType SPECIAL_CELEBRATE_DAGGOTH = new UnitType(bridge.getSpecial_Cerebrate_Daggoth());
    public static final UnitType PROTOSS_NEXUS = new UnitType(bridge.getProtoss_Nexus());
    public static final UnitType PROTOSS_ROBOTICS_FACILITY = new UnitType(bridge.getProtoss_Robotics_Facility());
    public static final UnitType PROTOSS_PYLON = new UnitType(bridge.getProtoss_Pylon());
    public static final UnitType PROTOSS_ASSIMILATOR = new UnitType(bridge.getProtoss_Assimilator());
    public static final UnitType PROTOSS_OBSERVATORY = new UnitType(bridge.getProtoss_Observatory());
    public static final UnitType PROTOSS_GATEWAY = new UnitType(bridge.getProtoss_Gateway());
    public static final UnitType PHOTON_CANNON = new UnitType(bridge.getProtoss_Photon_Cannon());
    public static final UnitType PROTOSS_CITADEL_OF_ADUN = new UnitType(bridge.getProtoss_Citadel_of_Adun());
    public static final UnitType PROTOSS_CYBERNETICS_CORE = new UnitType(bridge.getProtoss_Cybernetics_Core());
    public static final UnitType PROTOSS_TEMPLAR_ARCHIVES = new UnitType(bridge.getProtoss_Templar_Archives());
    public static final UnitType PROTOSS_FORGE = new UnitType(bridge.getProtoss_Forge());
    public static final UnitType PROTOSS_STARGATE = new UnitType(bridge.getProtoss_Stargate());
    public static final UnitType SPECIAL_STASIS_CELL_PRISON = new UnitType(bridge.getSpecial_Stasis_Cell_Prison());
    public static final UnitType PROTOSS_FLEET_BEACON = new UnitType(bridge.getProtoss_Fleet_Beacon());
    public static final UnitType PROTOSS_ARBITER_TRIBUNAL = new UnitType(bridge.getProtoss_Arbiter_Tribunal());
    public static final UnitType PROTOSS_ROBOTICS_SUPPORT_BAY = new UnitType(bridge.getProtoss_Robotics_Support_Bay());
    public static final UnitType PROTOSS_SHIELD_BATTERY = new UnitType(bridge.getProtoss_Shield_Battery());
    public static final UnitType SPECIAL_KHAYDARIN_CRYSTAL_FORM = new UnitType(bridge.getSpecial_Khaydarin_Crystal_Form());
    public static final UnitType SPECIAL_PROTOSS_TEMPLE = new UnitType(bridge.getSpecial_Protoss_Temple());
    public static final UnitType SPECIAL_XELNAGA_TEMPLE = new UnitType(bridge.getSpecial_XelNaga_Temple());
    public static final UnitType RESOURCE_MINERAL_FIELD = new UnitType(bridge.getResource_Mineral_Field());
    public static final UnitType RESOURCE_VESPENE_GEYSER = new UnitType(bridge.getResource_Vespene_Geyser());
    public static final UnitType SPECIAL_WARP_GATE = new UnitType(bridge.getSpecial_Warp_Gate());
    public static final UnitType SPECIAL_PSI_DISRUPTER = new UnitType(bridge.getSpecial_Psi_Disrupter());
    public static final UnitType SPECIAL_POWER_GENERATOR = new UnitType(bridge.getSpecial_Power_Generator());
    public static final UnitType SPECIAL_OVERMIND_COCOON = new UnitType(bridge.getSpecial_Overmind_Cocoon());
    public static final UnitType SPELL_DARK_SWARM = new UnitType(bridge.getSpell_Dark_Swarm());
    public static final UnitType NONE = new UnitType(bridge.getUnitTypeNone());
    public static final UnitType UNKNOWN = new UnitType(bridge.getUnitTypeUnknown());
    
    public static Set<UnitType> allUnitTypes() {
        return newSet(bridge.allUnitTypes());
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
    public static UnitType getUnitType(String name) {
        throw new UnsupportedOperationException();
    }

    static Set<UnitType> newSet(SWIGTYPE_p_std__setT_BWAPI__UnitType_t nativeSet) {
        return new NativeSet<UnitType, SWIG_UnitType>(
                SWIG_UnitType.class, UnitTypeSetIterator.class, 
                nativeSet, UnitTypeSet.class, FROM, TO);
    }
    
    static Set<UnitType> newSet(SWIGTYPE_p_std__setT_BWAPI__UnitType_const_p_t nativeSet) {
        return new NativeSet<UnitType, SWIG_UnitType>(
                SWIG_UnitType.class, UnitTypeConstantSetIterator.class, 
                nativeSet, UnitTypeConstantSet.class, FROM, TO);
    }

    static Set<UnitType> newSet(SWIGTYPE_p_std__listT_BWAPI__UnitType_t nativeSet) {
        return new NativeSet<UnitType, SWIG_UnitType>(
                SWIG_UnitType.class, UnitTypeListIterator.class, 
                nativeSet, UnitTypeList.class, FROM, TO);
    }
    
    @SuppressWarnings("unchecked")
    static Map<UnitType, Integer> newMap(SWIGTYPE_p_std__mapT_BWAPI__UnitType_const_p_int_t nativeMap) {
        return new NativeMap<UnitType, Integer, SWIGTYPE_p_UnitType, Integer>(
                SWIGTYPE_p_UnitType.class, Integer.class,
                UnitTypeMapIterator.class, nativeMap, UnitTypeMap.class,
                FROM_SPACELESS, TO_SPACELESS, NOPTransformer.INSTANCE, NOPTransformer.INSTANCE);
    }

    private final SWIG_UnitType unitType;
    
    UnitType(SWIG_UnitType unitType) {
        this.unitType = unitType;
    }

    UnitType(SWIGTYPE_p_UnitType unitType) {
        this(new SWIG_UnitType(unitType.getCPtr(), false));
    }

    public UnitType() {
        this(new SWIG_UnitType());
    }

    public UnitType(int id) {
        this(new SWIG_UnitType(id));
    }

    public UnitType(UnitType other) {
        this(new SWIG_UnitType(other.getOriginalObject()));
    }

    @Override
    SWIG_UnitType getOriginalObject() {
        return unitType;
    }
    
    SWIGTYPE_p_UnitType getPointer() {
        return new SWIGTYPE_p_UnitType(unitType.getCPtr(), false);
    }
    
    @Override
    public UnitType opAssign(UnitType other) {
        return new UnitType(unitType.opAssign(other.getOriginalObject()));
    }
    
    @Override
    public boolean opEquals(UnitType other) {
        return unitType.opEquals(other.getOriginalObject());
    }
    
    @Override
    public boolean opNotEquals(UnitType other) {
        return unitType.opNotEquals(other.getOriginalObject());
    }
    
    @Override
    public boolean opLessThan(UnitType other) {
        return unitType.opLessThan(other.getOriginalObject());
    }
    
    @Override
    public int getID() {
        return unitType.getID();
    }

    public String getName() {
        return unitType.getName();
    }

    public String getSubLabel() {
        return unitType.getSubLabel();
    }

    public Race getRace() {
        return new Race(unitType.getRace());
    }

    public Entry<UnitType, Integer> whatBuilds() {
        return new Entry<UnitType, Integer>() {
            private final UnitTypeWhatBuildsPair whatBuilds = unitType.whatBuilds();

            @Override
            public UnitType getKey() {
                SWIG_UnitType key = whatBuilds.getFirst();
                return key == null ? null : new UnitType(key);
            }

            @Override
            public Integer getValue() {
                return whatBuilds.getSecond();
            }
            
            @Override
            public Integer setValue(Integer value) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public Map<UnitType, Integer> requiredUnits() {
        return newMap(unitType.requiredUnits());
    }

    public TechType requiredTech() {
        SWIG_TechType type = unitType.requiredTech();
        return type == null ? null : new TechType(type);
    }

    public Set<TechType> abilities() {
        return TechType.newSet(unitType.abilities());
    }
    
    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public Set<UpgradeType> upgrades() {
        return UpgradeType.newSet(unitType.upgrades());
    }

    public UpgradeType armorUpgrade() {
        SWIG_UpgradeType type = unitType.armorUpgrade();
        return type == null ? null : new UpgradeType(type);
    }

    public int maxHitPoints() {
        return unitType.maxHitPoints();
    }

    public int maxShields() {
        return unitType.maxShields();
    }

    public int maxEnergy() {
        return unitType.maxEnergy();
    }

    public int armor() {
        return unitType.armor();
    }

    public int mineralPrice() {
        return unitType.mineralPrice();
    }

    public int gasPrice() {
        return unitType.gasPrice();
    }

    public int buildTime() {
        return unitType.buildTime();
    }

    public int supplyRequired() {
        return unitType.supplyRequired();
    }

    public int supplyProvided() {
        return unitType.supplyProvided();
    }

    public int spaceRequired() {
        return unitType.spaceRequired();
    }

    public int spaceProvided() {
        return unitType.spaceProvided();
    }

    public int buildScore() {
        return unitType.buildScore();
    }

    public int destroyScore() {
        return unitType.destroyScore();
    }

    public UnitSizeType size() {
        return new UnitSizeType(unitType.size());
    }

    public int tileWidth() {
        return unitType.tileWidth();
    }

    public int tileHeight() {
        return unitType.tileHeight();
    }

    public int dimensionLeft() {
        return unitType.dimensionLeft();
    }

    public int dimensionUp() {
        return unitType.dimensionUp();
    }

    public int dimensionRight() {
        return unitType.dimensionRight();
    }

    public int dimensionDown() {
        return unitType.dimensionDown();
    }

    public int seekRange() {
        return unitType.seekRange();
    }

    public int sightRange() {
        return unitType.sightRange();
    }

    public WeaponType groundWeapon() {
        SWIG_WeaponType type = unitType.groundWeapon();
        return type == null ? null : new WeaponType(type);
    }

    public int maxGroundHits() {
        return unitType.maxGroundHits();
    }

    public WeaponType airWeapon() {
        SWIG_WeaponType type = unitType.airWeapon();
        return type == null ? null : new WeaponType(type);
    }

    public int maxAirHits() {
        return unitType.maxAirHits();
    }

    /**
     * 
     * @return
     * 
     * @since 0.2, previous version returned int
     */
    public double topSpeed() {
        return unitType.topSpeed();
    }

    public int acceleration() {
        return unitType.acceleration();
    }

    public int haltDistance() {
        return unitType.haltDistance();
    }

    public int turnRadius() {
        return unitType.turnRadius();
    }

    public boolean canProduce() {
        return unitType.canProduce();
    }

    public boolean canAttack() {
        return unitType.canAttack();
    }

    public boolean canMove() {
        return unitType.canMove();
    }

    public boolean isFlyer() {
        return unitType.isFlyer();
    }

    public boolean regeneratesHP() {
        return unitType.regeneratesHP();
    }

    public boolean isSpellcaster() {
        return unitType.isSpellcaster();
    }

    public boolean hasPermanentCloak() {
        return unitType.hasPermanentCloak();
    }

    public boolean isInvincible() {
        return unitType.isInvincible();
    }

    public boolean isOrganic() {
        return unitType.isOrganic();
    }

    public boolean isMechanical() {
        return unitType.isMechanical();
    }

    public boolean isRobotic() {
        return unitType.isRobotic();
    }

    public boolean isDetector() {
        return unitType.isDetector();
    }

    public boolean isResourceContainer() {
        return unitType.isResourceContainer();
    }

    public boolean isResourceDepot() {
        return unitType.isResourceDepot();
    }

    public boolean isRefinery() {
        return unitType.isRefinery();
    }

    public boolean isWorker() {
        return unitType.isWorker();
    }

    public boolean requiresPsi() {
        return unitType.requiresPsi();
    }

    public boolean requiresCreep() {
        return unitType.requiresCreep();
    }

    public boolean isTwoUnitsInOneEgg() {
        return unitType.isTwoUnitsInOneEgg();
    }

    public boolean isBurrowable() {
        return unitType.isBurrowable();
    }

    public boolean isCloakable() {
        return unitType.isCloakable();
    }

    public boolean isBuilding() {
        return unitType.isBuilding();
    }

    public boolean isAddon() {
        return unitType.isAddon();
    }

    public boolean isFlyingBuilding() {
        return unitType.isFlyingBuilding();
    }

    public boolean isNeutral() {
        return unitType.isNeutral();
    }
}
