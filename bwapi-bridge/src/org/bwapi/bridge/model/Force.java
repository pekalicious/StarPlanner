package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.ForceSet;
import org.bwapi.bridge.swig.ForceSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__Force_p_t;
import org.bwapi.bridge.swig.SWIG_Force;
import org.cretz.swig.collection.NativeSet;

/**
 * Force
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Force">Force</a>
 * 
 * @author Chad Retz
 */
public final class Force extends BwapiObject {
    
    private static final Transformer<Force, SWIG_Force> FROM = 
        new Transformer<Force, SWIG_Force>() {
            @Override
            public SWIG_Force transform(Force force) {
                return force.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Force, Force> TO = 
        new Transformer<SWIG_Force, Force>() {
            @Override
            public Force transform(SWIG_Force force) {
                return new Force(force);
            }
        };
    
    static Set<Force> newSet(SWIGTYPE_p_std__setT_BWAPI__Force_p_t nativeSet) {
        return new NativeSet<Force, SWIG_Force>(
                SWIG_Force.class, ForceSetIterator.class, 
                nativeSet, ForceSet.class, FROM, TO);
    }

    private final SWIG_Force force;
    
    Force(SWIG_Force force) {
        this.force = force;
    }
    
    @Override
    SWIG_Force getOriginalObject() {
        return force;
    }
    
    public String getName() {
        return force.getName();
    }
    
    public Set<Player> getPlayers() {
        return Player.newSet(force.getPlayers());
    }
}
