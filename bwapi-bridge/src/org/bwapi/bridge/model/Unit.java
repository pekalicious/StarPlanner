package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__listT_BWAPI__Unit_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__Unit_p_t;
import org.bwapi.bridge.swig.SWIG_Player;
import org.bwapi.bridge.swig.SWIG_Unit;
import org.bwapi.bridge.swig.UnitList;
import org.bwapi.bridge.swig.UnitListIterator;
import org.bwapi.bridge.swig.UnitSet;
import org.bwapi.bridge.swig.UnitSetIterator;
import org.cretz.swig.collection.NativeSet;

/**
 * Unit
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Unit">Unit</a>
 * 
 * @author Chad Retz
 */
public final class Unit extends BwapiObject {
    
    private static final Transformer<Unit, SWIG_Unit> FROM = 
        new Transformer<Unit, SWIG_Unit>() {
            @Override
            public SWIG_Unit transform(Unit unit) {
                return unit.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Unit, Unit> TO = 
        new Transformer<SWIG_Unit, Unit>() {
            @Override
            public Unit transform(SWIG_Unit unit) {
                return new Unit(unit);
            }
        };
    
    static Set<Unit> newSet(SWIGTYPE_p_std__setT_BWAPI__Unit_p_t nativeSet) {
        return new NativeSet<Unit, SWIG_Unit>(
                SWIG_Unit.class, UnitSetIterator.class, 
                nativeSet, UnitSet.class, FROM, TO);
    }
    
    static Set<Unit> newSet(SWIGTYPE_p_std__listT_BWAPI__Unit_p_t nativeList) {
        return new NativeSet<Unit, SWIG_Unit>(
                SWIG_Unit.class, UnitListIterator.class, 
                nativeList, UnitList.class, FROM, TO);
    }
    
    private final SWIG_Unit unit;
    
    Unit(SWIG_Unit unit) {
        this.unit = unit;
    }
    
    @Override
    SWIG_Unit getOriginalObject() {
        return unit;
    }

    public Player getPlayer() {
        SWIG_Player player = unit.getPlayer();
        return player == null ? null : new Player(player);
    }

    public UnitType getType() {
        return new UnitType(unit.getType());
    }

    public UnitType getInitialType() {
        return new UnitType(unit.getInitialType());
    }

    public int getHitPoints() {
        return unit.getHitPoints();
    }

    public int getInitialHitPoints() {
        return unit.getInitialHitPoints();
    }

    public int getShields() {
        return unit.getShields();
    }

    public int getEnergy() {
        return unit.getEnergy();
    }

    public int getResources() {
        return unit.getResources();
    }

    public int getInitialResources() {
        return unit.getInitialResources();
    }

    public int getKillCount() {
        return unit.getKillCount();
    }

    public int getGroundWeaponCooldown() {
        return unit.getGroundWeaponCooldown();
    }

    public int getAirWeaponCooldown() {
        return unit.getAirWeaponCooldown();
    }

    public int getSpellCooldown() {
        return unit.getSpellCooldown();
    }

    public int getDefenseMatrixPoints() {
        return unit.getDefenseMatrixPoints();
    }

    public int getDefenseMatrixTimer() {
        return unit.getDefenseMatrixTimer();
    }

    public int getEnsnareTimer() {
        return unit.getEnsnareTimer();
    }

    public int getIrradiateTimer() {
        return unit.getIrradiateTimer();
    }

    public int getLockdownTimer() {
        return unit.getLockdownTimer();
    }

    public int getMaelstromTimer() {
        return unit.getMaelstromTimer();
    }

    public int getPlagueTimer() {
        return unit.getPlagueTimer();
    }

    public int getRemoveTimer() {
        return unit.getRemoveTimer();
    }

    public int getStasisTimer() {
        return unit.getStasisTimer();
    }

    public int getStimTimer() {
        return unit.getStimTimer();
    }

    public Position getPosition() {
        return new Position(unit.getPosition());
    }

    public Position getInitialPosition() {
        return new Position(unit.getInitialPosition());
    }

    public TilePosition getTilePosition() {
        return new TilePosition(unit.getTilePosition());
    }

    public TilePosition getInitialTilePosition() {
        return new TilePosition(unit.getTilePosition());
    }

    public double getDistance(Unit target) {
        return unit.getDistance(target.getOriginalObject());
    }

    public double getDistance(Position target) {
        return unit.getDistance(target.getOriginalObject());
    }

    /**
     * 
     * @return
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #getAngle()}
     */
    @Deprecated
    public int getCurrentDirection() {
        int dir = (int) Math.toDegrees(getAngle());
        //not even sure this is right
        if (dir > 270) {
            dir -= 270;
        } else {
            dir += 64;
        }
        return dir * (255 / 360);
    }
    
    /**
     * 
     * @return
     */
    public double getAngle() {
        return unit.getAngle();
    }
    
    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public double getVelocityX() {
        return unit.getVelocityX();
    }
    
    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public double getVelocityY() {
        return unit.getVelocityY();
    }
    
    /**
     * 
     * @param upgrade
     * @return
     * 
     * @since 0.2
     */
    public int getUpgradeLevel(UpgradeType upgrade) {
        return unit.getUpgradeLevel(upgrade.getPointer());
    }

    public Unit getTarget() {
        return new Unit(unit.getTarget());
    }

    public Position getTargetPosition() {
        return new Position(unit.getTargetPosition());
    }

    public Order getOrder() {
        return new Order(unit.getOrder());
    }

    public Unit getOrderTarget() {
        SWIG_Unit target = unit.getOrderTarget();
        return target == null ? null : new Unit(target);
    }

    public int getOrderTimer() {
        return unit.getOrderTimer();
    }

    public Order getSecondaryOrder() {
        return new Order(unit.getSecondaryOrder());
    }

    public Unit getBuildUnit() {
        SWIG_Unit buildUnit = unit.getBuildUnit();
        return buildUnit == null ? null : new Unit(buildUnit);
    }

    public int getRemainingBuildTime() {
        return unit.getRemainingBuildTime();
    }

    public int getRemainingTrainTime() {
        return unit.getRemainingTrainTime();
    }

    public Unit getChild() {
        SWIG_Unit child = unit.getChild();
        return child == null ? null : new Unit(child);
    }

    public Set<UnitType> getTrainingQueue() {
        return UnitType.newSet(unit.getTrainingQueue());
    }

    public Unit getTransport() {
        SWIG_Unit transport = unit.getTransport();
        return transport == null ? null : new Unit(transport);
    }

    public Set<Unit> getLoadedUnits() {
        return Unit.newSet(unit.getLoadedUnits());
    }

    public int getInterceptorCount() {
        return unit.getInterceptorCount();
    }

    public int getScarabCount() {
        return unit.getScarabCount();
    }

    public int getSpiderMineCount() {
        return unit.getSpiderMineCount();
    }

    public TechType getTech() {
        return new TechType(unit.getTech());
    }

    public UpgradeType getUpgrade() {
        return new UpgradeType(unit.getUpgrade());
    }

    public int getRemainingResearchTime() {
        return unit.getRemainingResearchTime();
    }

    public int getRemainingUpgradeTime() {
        return unit.getRemainingUpgradeTime();
    }

    public Position getRallyPosition() {
        return new Position(unit.getRallyPosition());
    }

    public Unit getRallyUnit() {
        SWIG_Unit rallyUnit = unit.getRallyUnit();
        return rallyUnit == null ? null : new Unit(rallyUnit);
    }

    public Unit getAddon() {
        SWIG_Unit addon = unit.getAddon();
        return addon == null ? null : new Unit(addon);
    }

    public boolean exists() {
        return unit.exists();
    }

    public boolean isAccelerating() {
        return unit.isAccelerating();
    }

    public boolean isBeingConstructed() {
        return unit.isBeingConstructed();
    }

    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public boolean isBeingGathered() {
        return unit.isBeingGathered();
    }

    public boolean isBeingHealed() {
        return unit.isBeingHealed();
    }

    public boolean isBlind() {
        return unit.isBlind();
    }

    public boolean isBraking() {
        return unit.isBraking();
    }

    public boolean isBurrowed() {
        return unit.isBurrowed();
    }

    public boolean isCarryingGas() {
        return unit.isCarryingGas();
    }

    public boolean isCarryingMinerals() {
        return unit.isCarryingMinerals();
    }

    public boolean isCloaked() {
        return unit.isCloaked();
    }

    public boolean isCompleted() {
        return unit.isCompleted();
    }

    public boolean isConstructing() {
        return unit.isConstructing();
    }

    public boolean isDefenseMatrixed() {
        return unit.isDefenseMatrixed();
    }

    public boolean isEnsnared() {
        return unit.isEnsnared();
    }

    public boolean isFollowing() {
        return unit.isFollowing();
    }

    public boolean isGatheringGas() {
        return unit.isGatheringGas();
    }

    public boolean isGatheringMinerals() {
        return unit.isGatheringMinerals();
    }

    public boolean isHallucination() {
        return unit.isHallucination();
    }

    public boolean isIdle() {
        return unit.isIdle();
    }

    public boolean isIrradiated() {
        return unit.isIrradiated();
    }

    public boolean isLifted() {
        return unit.isLifted();
    }

    public boolean isLoaded() {
        return unit.isLoaded();
    }

    public boolean isLockedDown() {
        return unit.isLockedDown();
    }

    public boolean isMaelstrommed() {
        return unit.isMaelstrommed();
    }

    public boolean isMorphing() {
        return unit.isMorphing();
    }

    public boolean isMoving() {
        return unit.isMoving();
    }

    public boolean isParasited() {
        return unit.isParasited();
    }

    public boolean isPatrolling() {
        return unit.isPatrolling();
    }

    public boolean isPlagued() {
        return unit.isPlagued();
    }

    public boolean isRepairing() {
        return unit.isRepairing();
    }

    public boolean isResearching() {
        return unit.isResearching();
    }

    public boolean isSelected() {
        return unit.isSelected();
    }

    public boolean isSieged() {
        return unit.isSieged();
    }

    public boolean isStartingAttack() {
        return unit.isStartingAttack();
    }

    public boolean isStasised() {
        return unit.isStasised();
    }

    public boolean isStimmed() {
        return unit.isStimmed();
    }

    public boolean isTraining() {
        return unit.isTraining();
    }

    public boolean isUnderStorm() {
        return unit.isUnderStorm();
    }

    public boolean isUnpowered() {
        return unit.isUnpowered();
    }

    public boolean isUpgrading() {
        return unit.isUpgrading();
    }

    public boolean isVisible() {
        return unit.isVisible();
    }

    public boolean attackMove(Position position) {
        return unit.attackMove(position.getOriginalObject());
    }

    public boolean attackUnit(Unit target) {
        return unit.attackUnit(target.getOriginalObject());
    }

    public boolean rightClick(Position position) {
        return unit.rightClick(position.getOriginalObject());
    }

    public boolean rightClick(Unit target) {
        return unit.rightClick(target.getOriginalObject());
    }

    public boolean train(UnitType type) {
        return unit.train(type.getOriginalObject());
    }

    public boolean build(TilePosition position, UnitType type) {
        return unit.build(position.getOriginalObject(), type.getOriginalObject());
    }

    public boolean buildAddon(UnitType type) {
        return unit.buildAddon(type.getOriginalObject());
    }

    public boolean research(TechType tech) {
        return unit.research(tech.getOriginalObject());
    }

    public boolean upgrade(UpgradeType upgrade) {
        return unit.upgrade(upgrade.getPointer());
    }

    public boolean stop() {
        return unit.stop();
    }

    public boolean holdPosition() {
        return unit.holdPosition();
    }

    public boolean patrol(Position position) {
        return unit.patrol(position.getOriginalObject());
    }

    public boolean follow(Unit target) {
        return unit.follow(target.getOriginalObject());
    }

    public boolean setRallyPosition(Position target) {
        return unit.setRallyPosition(target.getOriginalObject());
    }

    public boolean setRallyUnit(Unit target) {
        return unit.setRallyUnit(target.getOriginalObject());
    }

    public boolean repair(Unit target) {
        return unit.repair(target.getOriginalObject());
    }

    public boolean morph(UnitType type) {
        return unit.morph(type.getOriginalObject());
    }

    public boolean burrow() {
        return unit.burrow();
    }

    public boolean unburrow() {
        return unit.unburrow();
    }

    public boolean siege() {
        return unit.siege();
    }

    public boolean unsiege() {
        return unit.unsiege();
    }

    public boolean cloak() {
        return unit.cloak();
    }

    public boolean decloak() {
        return unit.decloak();
    }

    public boolean lift() {
        return unit.lift();
    }

    public boolean land(TilePosition position) {
        return unit.land(position.getOriginalObject());
    }

    public boolean load(Unit target) {
        return unit.load(target.getOriginalObject());
    }

    public boolean unload(Unit target) {
        return unit.unload(target.getOriginalObject());
    }

    public boolean unloadAll() {
        return unit.unloadAll();
    }

    public boolean unloadAll(Position position) {
        return unit.unloadAll(position.getOriginalObject());
    }

    public boolean cancelConstruction() {
        return unit.cancelConstruction();
    }

    public boolean haltConstruction() {
        return unit.haltConstruction();
    }

    public boolean cancelMorph() {
        return unit.cancelMorph();
    }

    public boolean cancelTrain() {
        return unit.cancelTrain();
    }

    public boolean cancelTrain(int slot) {
        return unit.cancelTrain(slot);
    }

    public boolean cancelAddon() {
        return unit.cancelAddon();
    }

    public boolean cancelResearch() {
        return unit.cancelResearch();
    }

    public boolean cancelUpgrade() {
        return unit.cancelUpgrade();
    }

    public boolean useTech(TechType tech) {
        return unit.useTech(tech.getOriginalObject());
    }

    public boolean useTech(TechType tech, Position position) {
        return unit.useTech(tech.getOriginalObject(), position.getOriginalObject());
    }

    public boolean useTech(TechType tech, Unit target) {
        return unit.useTech(tech.getOriginalObject(), target.getOriginalObject());
    }
    
    @Override
    public String toString() {
        return getType().getName() + " [" +
            getOriginalObject().getCPtr() + "]";
    }
}
