package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.OrderSet;
import org.bwapi.bridge.swig.OrderSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__Order_t;
import org.bwapi.bridge.swig.SWIG_Order;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Order
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Order">Order</a>
 * 
 * @author Chad Retz
 */
public final class Order extends BwapiComparable<Order> {
    
    private static final Transformer<Order, SWIG_Order> FROM = 
        new Transformer<Order, SWIG_Order>() {
            @Override
            public SWIG_Order transform(Order order) {
                return order.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Order, Order> TO = 
        new Transformer<SWIG_Order, Order>() {
            @Override
            public Order transform(SWIG_Order order) {
                return new Order(order);
            }
        };
    
    public static final Order DIE = new Order(bridge.getDie());
    public static final Order STOP = new Order(bridge.getStop());
    public static final Order GUARD = new Order(bridge.getGuard());
    public static final Order PLAYER_GUARD = new Order(bridge.getPlayerGuard());
    public static final Order TURRET_GUARD = new Order(bridge.getTurretGuard());
    public static final Order BUNKER_GUARD = new Order(bridge.getBunkerGuard());
    public static final Order MOVE = new Order(bridge.getMove());
    public static final Order REAVER_STOP = new Order(bridge.getReaverStop());
    public static final Order ATTACK_1 = new Order(bridge.getAttack1());
    public static final Order ATTACK_2 = new Order(bridge.getAttack2());
    public static final Order ATTACK_UNIT = new Order(bridge.getAttackUnit());
    public static final Order ATTACK_FIXED_RANGE = new Order(bridge.getAttackFixedRange());
    public static final Order ATTACK_TILE = new Order(bridge.getAttackTile());
    public static final Order HOVER = new Order(bridge.getHover());
    public static final Order ATTACK_MOVE = new Order(bridge.getAttackMove());
    public static final Order INFEST_MINE_1 = new Order(bridge.getInfestMine1());
    public static final Order NOTHING_1 = new Order(bridge.getNothing1());
    public static final Order POWERUP_1 = new Order(bridge.getPowerup1());
    public static final Order TOWER_GUARD = new Order(bridge.getTowerGuard());
    public static final Order TOWER_ATTACK = new Order(bridge.getTowerAttack());
    public static final Order VULTURE_MINE = new Order(bridge.getVultureMine());
    public static final Order STAYIN_RANGE = new Order(bridge.getStayinRange());
    public static final Order TURRET_ATTACK = new Order(bridge.getTurretAttack());
    public static final Order NOTHING_2 = new Order(bridge.getNothing2());
    public static final Order NOTHING_3 = new Order(bridge.getNothing3());
    public static final Order DRONE_START_BUILD = new Order(bridge.getDroneStartBuild());
    public static final Order DRONE_BUILD = new Order(bridge.getDroneBuild());
    public static final Order INFEST_MINE_2 = new Order(bridge.getInfestMine2());
    public static final Order INFEST_MINE_3 = new Order(bridge.getInfestMine3());
    public static final Order INFEST_MINE_4 = new Order(bridge.getInfestMine4());
    public static final Order BUILD_TERRAN = new Order(bridge.getBuildTerran());
    public static final Order BUILD_PROTOSS_1 = new Order(bridge.getBuildProtoss1());
    public static final Order BUILD_PROTOSS_2 = new Order(bridge.getBuildProtoss2());
    public static final Order CONSTRUCTING_BUILDING = new Order(bridge.getConstructingBuilding());
    public static final Order REPAIR_1 = new Order(bridge.getRepair1());
    public static final Order REPAIR_2 = new Order(bridge.getRepair2());
    public static final Order PLACE_ADDON = new Order(bridge.getPlaceAddon());
    public static final Order BUILD_ADDON = new Order(bridge.getBuildAddon());
    public static final Order TRAIN = new Order(bridge.getTrain());
    public static final Order RALLY_POINT = new Order(bridge.getRallyPoint1());
    public static final Order RALLY_POINT_2 = new Order(bridge.getRallyPoint2());
    public static final Order ZERG_BIRTH = new Order(bridge.getZergBirth());
    public static final Order MORPH_1 = new Order(bridge.getMorph1());
    public static final Order MORPH_2 = new Order(bridge.getMorph2());
    public static final Order BUILD_SELF_1 = new Order(bridge.getBuildSelf1());
    public static final Order ZERG_BUILD_SELF = new Order(bridge.getZergBuildSelf());
    public static final Order BUILD_5 = new Order(bridge.getBuild5());
    public static final Order ETERNYDUSCANAL = new Order(bridge.getEnternyduscanal());
    public static final Order BUILD_SELF_2 = new Order(bridge.getBuildSelf2());
    public static final Order FOLLOW = new Order(bridge.getFollow());
    public static final Order CARRIER = new Order(bridge.getCarrier());
    public static final Order CARRIER_IGNORE_1 = new Order(bridge.getCarrierIgnore1());
    public static final Order CARRIER_STOP = new Order(bridge.getCarrierStop());
    public static final Order CARRIER_ATTACK_1 = new Order(bridge.getCarrierAttack1());
    public static final Order CARRIER_ATTACK_2 = new Order(bridge.getCarrierAttack2());
    public static final Order CARRIER_IGNORE_2 = new Order(bridge.getCarrierIgnore2());
    public static final Order CARRIER_FIGHT = new Order(bridge.getCarrierFight());
    public static final Order HOLD_POSITION_1 = new Order(bridge.getHoldPosition1());
    public static final Order REAVER = new Order(bridge.getReaver());
    public static final Order REAVER_ATTACK_1 = new Order(bridge.getReaverAttack1());
    public static final Order REAVER_ATTACK_2 = new Order(bridge.getReaverAttack2());
    public static final Order REAVER_FIGHT = new Order(bridge.getReaverFight());
    public static final Order REAVER_HOLD = new Order(bridge.getReaverHold());
    public static final Order TRAIN_FIGHTER = new Order(bridge.getTrainFighter());
    public static final Order STRAFE_UNIT_1 = new Order(bridge.getStrafeUnit1());
    public static final Order STRAFE_UNIT_2 = new Order(bridge.getStrafeUnit2());
    public static final Order RECHARGE_SHIELDS_1 = new Order(bridge.getRechargeShields1());
    public static final Order RECHARGE_SHIELDS_2 = new Order(bridge.getRechargeshields2());
    public static final Order SHIELD_BATTERY = new Order(bridge.getShieldBattery());
    public static final Order RETURN = new Order(bridge.getReturn());
    public static final Order DRONE_LAND = new Order(bridge.getDroneLand());
    public static final Order BUILDING_LAND = new Order(bridge.getBuildingLand());
    public static final Order BUILDING_LIFTOFF = new Order(bridge.getBuildingLiftoff());
    public static final Order DRONE_LIFTOFF = new Order(bridge.getDroneLiftoff());
    public static final Order LIFTOFF = new Order(bridge.getLiftoff());
    public static final Order RESEARCH_TECH = new Order(bridge.getResearchTech());
    public static final Order UPGRADE = new Order(bridge.getUpgrade());
    public static final Order LARVA = new Order(bridge.getLarva());
    public static final Order SPAWNING_LARVA = new Order(bridge.getSpawningLarva());
    public static final Order HARVEST_1 = new Order(bridge.getHarvest1());
    public static final Order HARVEST_2 = new Order(bridge.getHarvest2());
    public static final Order MOVE_TO_GAS = new Order(bridge.getMoveToGas());
    public static final Order WAIT_FOR_GAS = new Order(bridge.getWaitForGas());
    public static final Order HARVEST_GAS = new Order(bridge.getHarvestGas());
    public static final Order RETURN_GAS = new Order(bridge.getReturnGas());
    public static final Order MOVE_TO_MINERALS = new Order(bridge.getMoveToMinerals());
    public static final Order WAIT_FOR_MINERALS = new Order(bridge.getWaitForMinerals());
    public static final Order MINING_MINERALS = new Order(bridge.getMiningMinerals());
    public static final Order HARVEST_3 = new Order(bridge.getHarvest3());
    public static final Order HARVEST_4 = new Order(bridge.getHarvest4());
    public static final Order RETURN_MINERALS = new Order(bridge.getReturnMinerals());
    public static final Order HARVEST_5 = new Order(bridge.getHarvest5());
    public static final Order ENTER_TRANSPORT = new Order(bridge.getEnterTransport());
    public static final Order PICKUP_1 = new Order(bridge.getPickup1());
    public static final Order PICKUP_2 = new Order(bridge.getPickup2());
    public static final Order PICKUP_3 = new Order(bridge.getPickup3());
    public static final Order PICKUP_4 = new Order(bridge.getPickup4());
    public static final Order POWERUP_2 = new Order(bridge.getPowerup2());
    public static final Order SIEGE_MODE = new Order(bridge.getSiegeMode());
    public static final Order TANK_MODE = new Order(bridge.getTankMode());
    public static final Order WATCH_TARGET = new Order(bridge.getWatchTarget());
    public static final Order INIT_CREEP_GROWTH = new Order(bridge.getInitCreepGrowth());
    public static final Order SPREAD_CREEP = new Order(bridge.getSpreadCreep());
    public static final Order STOPPING_CREEP_GROWTH = new Order(bridge.getStoppingCreepGrowth());
    public static final Order GUARDIAN_ASPECT = new Order(bridge.getGuardianAspect());
    public static final Order WARPING_ARCHON = new Order(bridge.getWarpingArchon());
    public static final Order COMPLETING_ARCHONSUMMON = new Order(bridge.getCompletingArchonsummon());
    public static final Order HOLD_POSITION_2 = new Order(bridge.getHoldPosition2());
    public static final Order HOLD_POSITION_3 = new Order(bridge.getHoldPosition3());
    public static final Order CLOAK = new Order(bridge.getCloak());
    public static final Order DECLOAK = new Order(bridge.getDecloak());
    public static final Order UNLOAD = new Order(bridge.getUnload());
    public static final Order MOVE_UNLOAD = new Order(bridge.getMoveUnload());
    public static final Order FIRE_YAMATO_GUN_1 = new Order(bridge.getFireYamatoGun1());
    public static final Order FIRE_YAMATO_GUN_2 = new Order(bridge.getFireYamatoGun2());
    public static final Order MAGNA_PULSE = new Order(bridge.getMagnaPulse());
    public static final Order BURROW = new Order(bridge.getBurrow());
    public static final Order BURROWED = new Order(bridge.getBurrowed());
    public static final Order UNBURROW = new Order(bridge.getUnburrow());
    public static final Order DARK_SWARM = new Order(bridge.getDarkSwarm());
    public static final Order CAST_PARASITE = new Order(bridge.getCastParasite());
    public static final Order SUMMON_BROODLINGS = new Order(bridge.getSummonBroodlings());
    public static final Order EMP_SHOCKWAVE = new Order(bridge.getEmpShockwave());
    public static final Order NUKE_WAIT = new Order(bridge.getNukeWait());
    public static final Order NUKE_TRAIN = new Order(bridge.getNukeTrain());
    public static final Order NUKE_LAUNCH = new Order(bridge.getNukeLaunch());
    public static final Order NUKE_POINT = new Order(bridge.getNukePaint());
    public static final Order NUKE_UNIT = new Order(bridge.getNukeUnit());
    public static final Order NUKE_GROUND = new Order(bridge.getNukeGround());
    public static final Order NUKE_TRACK = new Order(bridge.getNukeTrack());
    public static final Order INIT_ARBITER = new Order(bridge.getInitArbiter());
    public static final Order CLOAK_NEARBY_UNITS = new Order(bridge.getCloakNearbyUnits());
    public static final Order PLACE_MINE = new Order(bridge.getPlaceMine());
    public static final Order RIGHT_CLICK_ACTION = new Order(bridge.getRightclickaction());
    public static final Order SAP_UNIT = new Order(bridge.getSapUnit());
    public static final Order SAP_LOCATION = new Order(bridge.getSapLocation());
    public static final Order HOLD_POSITION_4 = new Order(bridge.getHoldPosition4());
    public static final Order TELEPORT = new Order(bridge.getTeleport());
    public static final Order TELEPORT_TO_LOCATION = new Order(bridge.getTeleporttoLocation());
    public static final Order PLACE_SCANNER = new Order(bridge.getPlaceScanner());
    public static final Order SCANNER = new Order(bridge.getScanner());
    public static final Order DEFENSIVE_MATRIX = new Order(bridge.getDefensiveMatrix());
    public static final Order PSI_STORM = new Order(bridge.getPsiStorm());
    public static final Order IRRADIATE = new Order(bridge.getOrderIrradiate());
    public static final Order PLAGUE = new Order(bridge.getOrderPlague());
    public static final Order CONSUME = new Order(bridge.getOrderConsume());
    public static final Order ENSNARE = new Order(bridge.getOrderEnsnare());
    public static final Order STASIS_FIELD = new Order(bridge.getStasisField());
    public static final Order HALLUCINATION_1 = new Order(bridge.getHallucination1());
    public static final Order HALLUCINATION_2 = new Order(bridge.getHallucination2());
    public static final Order RESET_COLLISION_1 = new Order(bridge.getResetCollision1());
    public static final Order RESET_COLLISION_2 = new Order(bridge.getResetCollision2());
    public static final Order PATROL = new Order(bridge.getPatrol());
    public static final Order CTF_COP_INIT = new Order(bridge.getCTFCOPInit());
    public static final Order CTF_COP_1 = new Order(bridge.getCTFCOP1());
    public static final Order CTF_COP_2 = new Order(bridge.getCTFCOP2());
    public static final Order COMPUTER_AI = new Order(bridge.getComputerAI());
    public static final Order ATK_MOVE_EP = new Order(bridge.getAtkMoveEP());
    public static final Order HARASS_MOVE = new Order(bridge.getHarassMove());
    public static final Order AI_PATROL = new Order(bridge.getAIPatrol());
    public static final Order GUARD_POST = new Order(bridge.getGuardPost());
    public static final Order RESCUE_PASSIVE = new Order(bridge.getRescuePassive());
    public static final Order NEUTRAL = new Order(bridge.getOrderNeutral());
    public static final Order COMPUTER_RETURN = new Order(bridge.getComputerReturn());
    public static final Order INIT_PSI_PROVIDER = new Order(bridge.getInitPsiProvider());
    public static final Order SELF_DESTRUCTING = new Order(bridge.getSelfDestrucing());
    public static final Order CRITTER = new Order(bridge.getCritter());
    public static final Order HIDDEN_GUN = new Order(bridge.getHiddenGun());
    public static final Order OPEN_DOOR = new Order(bridge.getOpenDoor());
    public static final Order CLOSE_DOOR = new Order(bridge.getCloseDoor());
    public static final Order HIDE_TRAP = new Order(bridge.getHideTrap());
    public static final Order REVEAL_TRAP = new Order(bridge.getRevealTrap());
    public static final Order ENABLE_DOODAD = new Order(bridge.getEnabledoodad());
    public static final Order DISABLE_DOODAD = new Order(bridge.getDisabledoodad());
    public static final Order WARPIN = new Order(bridge.getWarpin());
    public static final Order MEDIC = new Order(bridge.getMedic());
    public static final Order MEDIC_HEAL_1 = new Order(bridge.getMedicHeal1());
    public static final Order HEAL_MOVE = new Order(bridge.getHealMove());
    public static final Order MEDIC_HOLD_POSITION = new Order(bridge.getMedicHoldPosition());
    public static final Order MEDIC_HEAL_2 = new Order(bridge.getMedicHeal2());
    public static final Order RESTORATION = new Order(bridge.getOrderRestoration());
    public static final Order CAST_DISRUPTION_WEB = new Order(bridge.getCastDisruptionWeb());
    public static final Order CAST_MIND_CONTROL = new Order(bridge.getCastMindControl());
    public static final Order WARPING_DARK_ARCHON = new Order(bridge.getWarpingDarkArchon());
    public static final Order CAST_FEEDBACK = new Order(bridge.getCastFeedback());
    public static final Order CAST_OPTICAL_FLARE = new Order(bridge.getCastOpticalFlare());
    public static final Order CAST_MAELSTROM = new Order(bridge.getCastMaelstrom());
    public static final Order JUNK_YARD_DOG = new Order(bridge.getJunkYardDog());
    public static final Order FATAL = new Order(bridge.getFatal());
    public static final Order NONE = new Order(bridge.getOrderNone());
    public static final Order UNKNOWN = new Order(bridge.getOrderUnknown());
    
    public static Set<Order> allOrders() {
        return newSet(bridge.allOrders());
    }
    
    static Set<Order> newSet(SWIGTYPE_p_std__setT_BWAPI__Order_t nativeSet) {
        return new NativeSet<Order, SWIG_Order>(
                SWIG_Order.class, OrderSetIterator.class, 
                nativeSet, OrderSet.class, FROM, TO);
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
    public static Order getOrder(String name) {
        throw new UnsupportedOperationException();
    }

    private final SWIG_Order order;
    
    Order(SWIG_Order order) {
        this.order = order;
    }

    public Order() {
        this(new SWIG_Order());
    }

    public Order(int id) {
        this(new SWIG_Order(id));
    }

    public Order(Order other) {
        this(new SWIG_Order(other.getOriginalObject()));
    }
    
    @Override
    SWIG_Order getOriginalObject() {
        return order;
    }

    @Override
    public Order opAssign(Order other) {
        return new Order(order.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(Order other) {
        return order.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(Order other) {
        return order.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(Order other) {
        return order.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return order.getID();
    }

    public String getName() {
        return order.getName();
    }
}
