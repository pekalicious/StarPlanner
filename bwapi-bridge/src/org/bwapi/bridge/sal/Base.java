package org.bwapi.bridge.sal;

import java.util.Set;

import org.bwapi.bridge.model.BaseLocation;
import org.bwapi.bridge.model.Unit;

/**
 * Base
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/BaseManager">BaseManager</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.3 
 */
public class Base {

    protected final BaseLocation baseLocation;
    protected Unit resourceDepot;
    protected boolean active;
    protected boolean beingConstructed;
    
    public Base(BaseLocation baseLocation) {
        this.baseLocation = baseLocation;
    }

    public BaseLocation getBaseLocation() {
        return baseLocation;
    }

    public Unit getResourceDepot() {
        return resourceDepot;
    }

    public void setResourceDepot(Unit resourceDepot) {
        this.resourceDepot = resourceDepot;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBeingConstructed() {
        return beingConstructed;
    }

    public void setBeingConstructed(boolean beingConstructed) {
        this.beingConstructed = beingConstructed;
    }
    
    public Set<Unit> getMinerals() {
        return baseLocation.getMinerals();
    }
    
    public Set<Unit> getGeysers() {
        return baseLocation.getGeysers();
    }
}
