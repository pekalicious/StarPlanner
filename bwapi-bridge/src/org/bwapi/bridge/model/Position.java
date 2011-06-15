package org.bwapi.bridge.model;

import java.util.List;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.PositionVector;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__vectorT_BWAPI__Position_t;
import org.bwapi.bridge.swig.SWIG_Position;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeList;

/**
 * Position
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Misc#Position">Position</a>
 * 
 * @author Chad Retz
 */
public final class Position extends BwapiObject {
    
    private static final Transformer<Position, SWIG_Position> FROM = 
        new Transformer<Position, SWIG_Position>() {
            @Override
            public SWIG_Position transform(Position position) {
                return position.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Position, Position> TO = 
        new Transformer<SWIG_Position, Position>() {
            @Override
            public Position transform(SWIG_Position position) {
                return new Position(position);
            }
        };

    public static final Position INVALID = new Position(bridge.getPositionInvalid());
    public static final Position NONE = new Position(bridge.getPositionNone());
    public static final Position UNKNOWN = new Position(bridge.getPositionUnknown());
    
    static List<Position> newList(SWIGTYPE_p_std__vectorT_BWAPI__Position_t nativeList) {
        return new NativeList<Position, SWIG_Position>(
                SWIG_Position.class, nativeList, PositionVector.class, 
                FROM, TO);
    }

    private final SWIG_Position position;
    
    Position(SWIG_Position position) {
        this.position = position;
    }

    public Position() {
        this(new SWIG_Position());
    }

    public Position(TilePosition position) {
        this(new SWIG_Position(position.getOriginalObject()));
    }

    public Position(int x, int y) {
        this(new SWIG_Position(x, y));
    }
    
    @Override
    SWIG_Position getOriginalObject() {
        return position;
    }

    public boolean opEquals(Position position) {
        return this.position.opEquals(position.getOriginalObject());
    }

    public boolean opNotEquals(Position position) {
        return this.position.opNotEquals(position.getOriginalObject());
    }

    public boolean opLessThan(Position position) {
        return this.position.opLessThan(position.getOriginalObject());
    }

    public Position opPlus(Position position) {
        return new Position(this.position.opPlus(position.getOriginalObject()));
    }

    public Position opMinus(Position position) {
        return new Position(this.position.opMinus(position.getOriginalObject()));
    }

    public Position opAdd(Position position) {
        return new Position(this.position.opAdd(position.getOriginalObject()));
    }

    public Position opSubtract(Position position) {
        return new Position(this.position.opSubtract(position.getOriginalObject()));
    }

    public double getDistance(Position position) {
        return this.position.getDistance(position.getOriginalObject());
    }

    public double getLength() {
        return position.getLength();
    }
    
    //TODO: add util to set int from pointer

    public int x() {
        return position.xConst();
    }

    public int y() {
        return position.yConst();
    }
}
