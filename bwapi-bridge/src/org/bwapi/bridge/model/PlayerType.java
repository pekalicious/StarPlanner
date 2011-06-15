package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.PlayerTypeSet;
import org.bwapi.bridge.swig.PlayerTypeSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_PlayerType;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__PlayerType_t;
import org.bwapi.bridge.swig.SWIG_PlayerType;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Player type
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/PlayerType">PlayerType</a>
 * 
 * @author Chad Retz
 */
public final class PlayerType extends BwapiComparable<PlayerType> {
    
    private static final Transformer<PlayerType, SWIG_PlayerType> FROM = 
        new Transformer<PlayerType, SWIG_PlayerType>() {
            @Override
            public SWIG_PlayerType transform(PlayerType type) {
                return type.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_PlayerType, PlayerType> TO = 
        new Transformer<SWIG_PlayerType, PlayerType>() {
            @Override
            public PlayerType transform(SWIG_PlayerType type) {
                return new PlayerType(type);
            }
        };    

    public static final PlayerType NOT_USED = new PlayerType(bridge.getNotUsed());
    public static final PlayerType COMPUTER = new PlayerType(bridge.getComputer());
    public static final PlayerType HUMAN = new PlayerType(bridge.getHuman());
    public static final PlayerType RESCUABLE = new PlayerType(bridge.getRescuable());
    public static final PlayerType COMPUTER_SLOT = new PlayerType(bridge.getComputerSlot());
    public static final PlayerType OPEN_SLOT = new PlayerType(bridge.getOpenSlot());
    public static final PlayerType NEUTRAL = new PlayerType(bridge.getPlayerTypeNeutral());
    public static final PlayerType CLOSED_SLOT = new PlayerType(bridge.getClosedSlot());
    public static final PlayerType HUMAN_DEFEATED = new PlayerType(bridge.getHumanDefeated());
    public static final PlayerType COMPUTER_DEFEATED = new PlayerType(bridge.getComputerDefeated());
    public static final PlayerType NONE = new PlayerType(bridge.getPlayerTypeNone());
    public static final PlayerType UNKNOWN = new PlayerType(bridge.getPlayerTypeUnknown());

    public static Set<PlayerType> allPlayerTypes() {
        return newSet(bridge.allPlayerTypes());
    }
    
    static Set<PlayerType> newSet(SWIGTYPE_p_std__setT_BWAPI__PlayerType_t nativeSet) {
        return new NativeSet<PlayerType, SWIG_PlayerType>(
                SWIG_PlayerType.class, PlayerTypeSetIterator.class, 
                nativeSet, PlayerTypeSet.class, FROM, TO);
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
    public static PlayerType getPlayerType(String name) {
        throw new UnsupportedOperationException();
    }
    
    private final SWIG_PlayerType playerType;
    
    PlayerType(SWIG_PlayerType playerType) {
        this.playerType = playerType;
    }
    
    PlayerType(SWIGTYPE_p_PlayerType pointer) {
        this(new SWIG_PlayerType(
                pointer.getCPtr(), false));
    }

    public PlayerType() {
        this(new SWIG_PlayerType());
    }

    public PlayerType(int id) {
        this(new SWIG_PlayerType(id));
    }

    public PlayerType(PlayerType other) {
        this(new SWIG_PlayerType(other.getOriginalObject()));
    }
    
    SWIG_PlayerType getOriginalObject() {
        return playerType;
    }

    @Override
    public PlayerType opAssign(PlayerType other) {
        return new PlayerType(playerType.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(PlayerType other) {
        return playerType.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(PlayerType other) {
        return playerType.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(PlayerType other) {
        return playerType.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return playerType.getID();
    }

    public String getName() {
        return playerType.getName();
    }
}
