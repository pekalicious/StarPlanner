package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.PlayerSet;
import org.bwapi.bridge.swig.PlayerSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__Player_p_t;
import org.bwapi.bridge.swig.SWIG_Force;
import org.bwapi.bridge.swig.SWIG_Player;
import org.cretz.swig.collection.NativeSet;

/**
 * Player
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Player">Player</a>
 * 
 * @author Chad Retz
 */
public final class Player extends BwapiIdentifiable {
    
    private static final Transformer<Player, SWIG_Player> FROM = 
        new Transformer<Player, SWIG_Player>() {
            @Override
            public SWIG_Player transform(Player player) {
                return player.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Player, Player> TO = 
        new Transformer<SWIG_Player, Player>() {
            @Override
            public Player transform(SWIG_Player player) {
                return new Player(player);
            }
        };

    static Set<Player> newSet(SWIGTYPE_p_std__setT_BWAPI__Player_p_t nativeSet) {
        return new NativeSet<Player, SWIG_Player>(
                SWIG_Player.class, PlayerSetIterator.class, 
                nativeSet, PlayerSet.class, FROM, TO);
    }
    
    private final SWIG_Player player;
    
    Player(SWIG_Player player) {
        this.player = player;
    }
    
    @Override
    SWIG_Player getOriginalObject() {
        return player;
    }

    @Override
    public int getID() {
        return player.getID();
    }

    public String getName() {
        return player.getName();
    }

    public Set<Unit> getUnits() {
        return Unit.newSet(player.getUnits());
    }

    public Race getRace() {
        return new Race(player.getRace());
    }

    public PlayerType playerType() {
        return new PlayerType(player.playerType());
    }

    public Force getForce() {
        SWIG_Force force = player.getForce();
        return force == null ? null : new Force(force);
    }

    public boolean isAlly(Player player) {
        return this.player.isAlly(player.getOriginalObject());
    }

    public boolean isEnemy(Player player) {
        return this.player.isEnemy(player.getOriginalObject());
    }

    public boolean isNeutral() {
        return player.isNeutral();
    }

    public TilePosition getStartLocation() {
        return new TilePosition(player.getStartLocation());
    }
    
    /**
     * 
     * @return
     * 
     * @since 0.2
     */
    public boolean leftGame() {
        return player.leftGame();
    }

    public int minerals() {
        return player.minerals();
    }

    public int gas() {
        return player.gas();
    }

    public int cumulativeMinerals() {
        return player.cumulativeMinerals();
    }

    public int cumulativeGas() {
        return player.cumulativeGas();
    }

    public int supplyTotal() {
        return player.supplyTotal();
    }

    public int supplyUsed() {
        return player.supplyUsed();
    }

    public int supplyTotal(Race race) {
        return player.supplyTotal(race.getPointer());
    }

    public int supplyUsed(Race race) {
        return player.supplyUsed(race.getPointer());
    }

    public int allUnitCount(UnitType unit) {
        return player.allUnitCount(unit.getOriginalObject());
    }

    public int completedUnitCount(UnitType unit) {
        return player.completedUnitCount(unit.getOriginalObject());
    }

    public int incompleteUnitCount(UnitType unit) {
        return player.incompleteUnitCount(unit.getOriginalObject());
    }

    public int deadUnitCount(UnitType unit) {
        return player.deadUnitCount(unit.getOriginalObject());
    }

    public int killedUnitCount(UnitType unit) {
        return player.killedUnitCount(unit.getOriginalObject());
    }

    /**
     * 
     * @param tech
     * @return
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #isResearching(TechType)}
     */
    @Deprecated
    public boolean researching(TechType tech) {
        return isResearching(tech);
    }
    
    /**
     * 
     * @param tech
     * @return
     * 
     * @since 0.2
     */
    public boolean isResearching(TechType tech) {
        return player.isResearching(tech.getPointer());
    }

    /**
     * 
     * @param tech
     * @return
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #hasResearched(TechType)}
     */
    @Deprecated
    public boolean researched(TechType tech) {
        return hasResearched(tech);
    }

    /**
     * 
     * @param tech
     * @return
     * 
     * @since 0.2
     */
    public boolean hasResearched(TechType tech) {
        return player.hasResearched(tech.getPointer());
    }

    /**
     * 
     * @param upgrade
     * @return
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #isUpgrading(UpgradeType)}
     */
    @Deprecated
    public boolean upgrading(UpgradeType upgrade) {
        return isUpgrading(upgrade);
    }
    
    /**
     * 
     * @param upgrade
     * @return
     * 
     * @since 0.2
     */
    public boolean isUpgrading(UpgradeType upgrade) {
        return player.isUpgrading(upgrade.getPointer());
    }

    /**
     * 
     * @param upgrade
     * @return
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #getUpgradeLevel(UpgradeType)}
     */
    @Deprecated
    public int upgradeLevel(UpgradeType upgrade) {
        return getUpgradeLevel(upgrade);
    }

    /**
     * 
     * @param upgrade
     * @return
     * 
     * @since 0.2
     */
    public int getUpgradeLevel(UpgradeType upgrade) {
        return player.getUpgradeLevel(upgrade.getPointer());
    }
    
    @Override
    public String toString() {
        return getName() + " [" + getRace().getName() + "]";
    }
}
