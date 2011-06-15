package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.ErrorSet;
import org.bwapi.bridge.swig.ErrorSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__Error_t;
import org.bwapi.bridge.swig.SWIG_Error;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Error
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Error">Error</a>
 * 
 * @author Chad Retz
 */
public class Error extends BwapiComparable<Error> {
    
    private static final Transformer<Error, SWIG_Error> FROM = 
        new Transformer<Error, SWIG_Error>() {
            @Override
            public SWIG_Error transform(Error error) {
                return error.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Error, Error> TO = 
        new Transformer<SWIG_Error, Error>() {
            @Override
            public Error transform(SWIG_Error error) {
                return new Error(error);
            }
        };
    
    public static final Error UNIT_DOES_NOT_EXIST = new Error(bridge.getUnit_Does_Not_Exist());
    public static final Error UNIT_NOT_VISIBLE = new Error(bridge.getUnit_Not_Visible());
    public static final Error UNIT_NOT_OWNED = new Error(bridge.getUnit_Not_Owned());
    public static final Error UNIT_BUSY = new Error(bridge.getUnit_Busy());
    public static final Error INCOMPATIBLE_UNIT_TYPE = new Error(bridge.getIncompatible_UnitType());
    public static final Error INCOMPATIBLE_TECH_TYPE = new Error(bridge.getIncompatible_TechType());
    public static final Error ALREADY_RESEARCHED = new Error(bridge.getAlready_Researched());
    public static final Error FULLY_UPGRADED = new Error(bridge.getFully_Upgraded());
    public static final Error INSUFFICIENT_MINERALS = new Error(bridge.getInsufficient_Minerals());
    public static final Error INSUFFICIENT_GAS = new Error(bridge.getInsufficient_Gas());
    public static final Error INSUFFICIENT_SUPPLY = new Error(bridge.getInsufficient_Supply());
    public static final Error INSUFFICIENT_ENERGY = new Error(bridge.getInsufficient_Energy());
    public static final Error INSUFFICIENT_TECH = new Error(bridge.getInsufficient_Tech());
    public static final Error INSUFFICIENT_AMMO = new Error(bridge.getInsufficient_Ammo());
    public static final Error INSUFFICIENT_SPACE = new Error(bridge.getInsufficient_Space());
    public static final Error UNBUILDABLE_LOCATION = new Error(bridge.getUnbuildable_Location());
    public static final Error OUT_OF_RANGE = new Error(bridge.getOut_Of_Range());
    public static final Error UNABLE_TO_HIT = new Error(bridge.getUnable_To_Hit());
    public static final Error ACCESS_DENIED = new Error(bridge.getAccess_Denied());
    public static final Error NONE = new Error(bridge.getErrorNone());
    public static final Error UNKNOWN = new Error(bridge.getErrorUnknown());
    
    public static Set<Error> allErrors() {
        return newSet(bridge.allErrors());
    }
    
    static Set<Error> newSet(SWIGTYPE_p_std__setT_BWAPI__Error_t nativeSet) {
        return new NativeSet<Error, SWIG_Error>(
                SWIG_Error.class, ErrorSetIterator.class, 
                nativeSet, ErrorSet.class, FROM, TO);
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
    public static Error getError(String name) {
        throw new UnsupportedOperationException();
    }

    private final SWIG_Error error;
    
    Error(SWIG_Error error) {
        this.error = error;
    }

    public Error() {
        this(new SWIG_Error());
    }

    public Error(int id) {
        this(new SWIG_Error(id));
    }

    public Error(Error other) {
        this(new SWIG_Error(other.getOriginalObject()));
    }
    
    @Override
    SWIG_Error getOriginalObject() {
        return error;
    }

    @Override
    public Error opAssign(Error other) {
        return new Error(error.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(Error other) {
        return error.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(Error other) {
        return error.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(Error other) {
        return error.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return error.getID();
    }

    public String toErrorString() {
        return error.toErrorString();
    }
}
