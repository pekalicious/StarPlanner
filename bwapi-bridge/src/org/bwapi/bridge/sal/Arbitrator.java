package org.bwapi.bridge.sal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Arbitrator
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/Arbitrator">Arbitrator</a>
 * 
 * @author Chad Retz
 *
 * @param <T>
 * @param <U>
 * 
 * @since 0.3
 */
public class Arbitrator<T, U extends Comparable<U>> {
    
    protected final Map<T, Heap<Controller<T, U>, U>> bids = 
        new HashMap<T, Heap<Controller<T, U>, U>>();
    protected final Map<T, Controller<T, U>> owner = new HashMap<T, Controller<T, U>>();
    protected final Map<Controller<T, U>, Set<T>> objects = new HashMap<Controller<T, U>, Set<T>>();
    protected final Set<T> updatedObjects = new HashSet<T>();
    
    public boolean setBid(Controller<T, U> controller, T object, U bid) {
        if (controller == null || object == null || bid == null) {
            return false;
        }
        Heap<Controller<T, U>, U> val = bids.get(object);
        if (val == null) {
            val = new Heap<Controller<T, U>, U>();
            bids.put(object, val);
        }
        val.set(controller, bid);
        updatedObjects.add(object);
        return true;
    }
    
    public boolean removeBid(Controller<T, U> controller, T object) {
        if (controller == null || object == null) {
            return false;
        }
        Heap<Controller<T, U>, U> val = bids.get(object);
        if (val != null && val.erase(controller)) {
            updatedObjects.add(object);
        }
        return true;
    }
    
    public boolean decline(Controller<T, U> controller, T object, U bid) {
        if (controller == null || object == null || bid == null) {
            return false;
        }
        Heap<Controller<T, U>, U> val = bids.get(object);
        if (val == null || val.isEmpty() || !val.top().getKey().equals(controller)) {
            return false;
        }
        val.set(controller, bid);
        updatedObjects.add(object);
        return true;
    }
    
    public boolean accept(Controller<T, U> controller, T object) {
        if (controller == null || object == null) {
            return false;
        }
        Heap<Controller<T, U>, U> val = bids.get(object);
        if (val == null || val.isEmpty() || !val.top().getKey().equals(controller)) {
            return false;
        }
        Controller<T, U> own = owner.get(object);
        if (own != null) {
            own.onRevoke(object, val.top().getValue());
            Set<T> objs = objects.get(own);
            if (objs != null) {
                objs.remove(object);
            }
        }
        owner.put(object, controller);
        Set<T> objs = objects.get(controller);
        if (objs == null) {
            objs = new HashSet<T>();
            objects.put(controller, objs);
        }
        objs.add(object);
        return true;
    }
    
    public boolean accept(Controller<T, U> controller, T object, U bid) {
        if (controller == null || object == null || bid == null) {
            return false;
        }
        Heap<Controller<T, U>, U> val = bids.get(object);
        if (val == null || val.isEmpty() || !val.top().getKey().equals(controller)) {
            return false;
        }
        Controller<T, U> own = owner.get(object);
        if (own != null) {
            own.onRevoke(object, val.top().getValue());
            Set<T> objs = objects.get(own);
            if (objs != null) {
                objs.remove(object);
            }
        }
        val.set(controller, bid);
        owner.put(object, controller);
        Set<T> objs = objects.get(controller);
        if (objs == null) {
            objs = new HashSet<T>();
            objects.put(controller, objs);
        }
        objs.add(object);
        updatedObjects.add(object);
        return true;
    }
    
    public boolean hasBid(T object) {
        return bids.containsKey(object);
    }
    
    public Entry<Controller<T, U>, U> getHighestBidder(T object) {
        Heap<Controller<T, U>, U> val = bids.get(object);
        return val == null ? null : val.top();
    }
    
    public List<Entry<Controller<T, U>, U>> getAllBidders(T object) {
        Heap<Controller<T, U>, U> val = bids.get(object);
        if (val == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(val.getData());
        }
    }
    
    public Set<T> getObjects(Controller<T, U> controller) {
        Set<T> ret = objects.get(controller);
        return ret == null ? null : Collections.unmodifiableSet(ret);
    }
    
    public void onRemoveObject(T object) {
        bids.remove(object);
        owner.remove(object);
        updatedObjects.remove(object);
        for (Set<T> set : objects.values()) {
            set.remove(object);
        }
    }
    
    public U getBid(Controller<T, U> controller, T object) {
        Heap<Controller<T, U>, U> val = bids.get(object);
        return val == null ? null : val.get(controller);
    }
    
    public void update() {
        Map<Controller<T, U>, Set<T>> objectsToOffer = 
            new HashMap<Controller<T, U>, Set<T>>(updatedObjects.size());
        for (T object : updatedObjects) {
            Heap<Controller<T, U>, U> val = bids.get(object);
            Controller<T, U> own = owner.get(object);
            if (val != null) {
                if (own == null || !val.top().getKey().equals(own)) {
                    Set<T> objects = objectsToOffer.get(val.top().getKey());
                    if (objects == null) {
                        objects = new HashSet<T>();
                        objectsToOffer.put(val.top().getKey(), objects);
                    }
                    objects.add(object);
                }
            } else if (own != null) {
                own.onRevoke(object, null);
                owner.remove(object);
            }
        }
        updatedObjects.clear();
        for (Entry<Controller<T, U>, Set<T>> entry : objectsToOffer.entrySet()) {
            entry.getKey().onOffer(entry.getValue());
        }
    }
}
