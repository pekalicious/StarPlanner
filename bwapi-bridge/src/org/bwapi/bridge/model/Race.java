package org.bwapi.bridge.model;

import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.RaceSet;
import org.bwapi.bridge.swig.RaceSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_Race;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWAPI__Race_t;
import org.bwapi.bridge.swig.SWIG_Race;
import org.bwapi.bridge.swig.SWIG_UnitType;
import org.bwapi.bridge.swig.bridge;
import org.cretz.swig.collection.NativeSet;

/**
 * Race
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Race">Race</a>
 * 
 * @author Chad Retz
 */
public final class Race extends BwapiComparable<Race> {
    
    private static final Transformer<Race, SWIG_Race> FROM = 
        new Transformer<Race, SWIG_Race>() {
            @Override
            public SWIG_Race transform(Race race) {
                return race.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Race, Race> TO = 
        new Transformer<SWIG_Race, Race>() {
            @Override
            public Race transform(SWIG_Race race) {
                return new Race(race);
            }
        };

    public static final Race ZERG = new Race(bridge.getZerg());
    public static final Race TERRAN = new Race(bridge.getTerran());
    public static final Race PROTOSS = new Race(bridge.getProtoss());
    public static final Race RANDOM = new Race(bridge.getRandom());
    public static final Race OTHER = new Race(bridge.getOther());
    public static final Race NONE = new Race(bridge.getRaceNone());
    public static final Race UNKNOWN = new Race(bridge.getRaceUnknown());
    
    public static Set<Race> allRaces() {
        return newSet(bridge.allRaces());
    }
    
    static Set<Race> newSet(SWIGTYPE_p_std__setT_BWAPI__Race_t nativeSet) {
        return new NativeSet<Race, SWIG_Race>(
                SWIG_Race.class, RaceSetIterator.class, 
                nativeSet, RaceSet.class, FROM, TO);
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
    public static Race getRace(String name) {
        throw new UnsupportedOperationException();
    }
    
    private final SWIG_Race race;
    
    Race(SWIG_Race race) {
        this.race = race;
    }
    
    Race(SWIGTYPE_p_Race pointer) {
        this(new SWIG_Race(pointer.getCPtr(), false));
    }

    public Race() {
        this(new SWIG_Race());
    }

    public Race(int id) {
        this(new SWIG_Race(id));
    }

    public Race(Race other) {
        this(new SWIG_Race(other.getOriginalObject()));
    }
    
    SWIG_Race getOriginalObject() {
        return race;
    }
    
    SWIGTYPE_p_Race getPointer() {
        return new SWIGTYPE_p_Race(race.getCPtr(), false);
    }

    @Override
    public Race opAssign(Race other) {
        return new Race(race.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(Race other) {
        return race.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(Race other) {
        return race.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(Race other) {
        return race.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return race.getID();
    }

    public String getName() {
        return race.getName();
    }

    public UnitType getWorker() {
        SWIG_UnitType worker = race.getWorker();
        return worker == null ? null : new UnitType(worker);
    }

    public UnitType getCenter() {
        SWIG_UnitType center = race.getCenter();
        return center == null ? null : new UnitType(center);
    }

    public UnitType getRefinery() {
        SWIG_UnitType refinery = race.getRefinery();
        return refinery == null ? null : new UnitType(refinery);
    }

    public UnitType getTransport() {
        SWIG_UnitType transport = race.getTransport();
        return transport == null ? null : new UnitType(transport);
    }

    public UnitType getSupplyProvider() {
        SWIG_UnitType provider = race.getSupplyProvider();
        return provider == null ? null : new UnitType(provider);
    }
}
