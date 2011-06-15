package org.bwapi.bridge.sal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bwapi.bridge.model.BaseLocation;
import org.bwapi.bridge.model.Bwta;
import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Position;
import org.bwapi.bridge.model.Unit;

/**
 * Scout manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/ScoutManager">ScoutManager</a>
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public class ScoutManager extends ArbitratedManager {
    
    protected static Entry<List<BaseLocation>, Double> getBestPath(
            Set<BaseLocation> baseLocations) {
        Pair<List<BaseLocation>, Double> shortestPath = new Pair<List<BaseLocation>, Double>(
                new ArrayList<BaseLocation>(), 0D);
        if (baseLocations.isEmpty()) {
            return shortestPath;
        } else if (baseLocations.size() == 1) {
            shortestPath.getKey().add(baseLocations.iterator().next());
            return shortestPath;
        } else {
            for (BaseLocation location : baseLocations) {
                Set<BaseLocation> locations = new HashSet<BaseLocation>(baseLocations);
                locations.remove(location);
                Entry<List<BaseLocation>, Double> result = getBestPath(locations);
                double dist = result.getValue() + location.getGroundDistance(
                        result.getKey().get(0));
                if (dist < shortestPath.getValue() || shortestPath.getKey().isEmpty()) {
                    result.getKey().add(0, location);
                    shortestPath = new Pair<List<BaseLocation>, Double>(result.getKey(), dist);
                }
            }
            return shortestPath;
        }
    }

    protected final Map<Unit, ScoutData> scouts = new HashMap<Unit, ScoutData>();
    protected final List<Position> positionsToScout = new ArrayList<Position>();
    protected final BaseLocation myStartLocation;
    protected int desiredScoutCount;
    
    //what is this?
    protected int scoutingStartFrame;
    
    public ScoutManager(Arbitrator<Unit, Double> arbitrator) {
        super(arbitrator);
        desiredScoutCount = 0;
        myStartLocation = Bwta.getStartPosition(Game.getInstance().self());
        Set<BaseLocation> startLocations = new HashSet<BaseLocation>(Bwta.getBaseLocations());
        startLocations.remove(myStartLocation);
        Iterator<BaseLocation> locationIterator = startLocations.iterator();
        while (locationIterator.hasNext()) {
            if (myStartLocation.getGroundDistance(locationIterator.next()) <= 0) {
                locationIterator.remove();
            }
        }
        Entry<List<BaseLocation>, Double> path = getBestPath(startLocations);
        for (BaseLocation location : path.getKey()) {
            positionsToScout.add(location.getPosition());
        }
    }
    
    @Override
    public void onOffer(Set<Unit> units) {
        for (Unit unit : units) {
            if (unit.getType().isWorker() && needMoreScouts()) {
                arbitrator.accept(this, unit);
                addScout(unit);
            } else {
                arbitrator.decline(this, unit, 0D);
            }
        }
    }
    
    @Override
    public void onRevoke(Unit unit, Double bid) {
        scouts.remove(unit);
    }
    
    @Override
    public void update() {
        if (needMoreScouts()) {
            requestScout(12D);
        } else {
            while (scouts.size() > desiredScoutCount) {
                Entry<Unit, ScoutData> scout = scouts.entrySet().iterator().next();
                arbitrator.setBid(this, scout.getKey(), 0D);
                scouts.remove(scout.getKey());
            }
        }
        updateScoutAssignments();
    }
    
    @Override
    public String getName() {
        return "Scout Manager";
    }
    
    public void onRemoveUnit(Unit unit) {
        scouts.remove(unit);
    }
    
    public void setScoutCount(int count) {
        desiredScoutCount = count;
    }
    
    public boolean isScouting() {
        return !scouts.isEmpty();
    }
    
    protected boolean needMoreScouts() {
        return scouts.size() < desiredScoutCount;
    }
    
    protected void requestScout(double bid) {
        //bid on all completed workers
        for (Unit unit : Game.getInstance().self().getUnits()) {
            if (unit.isCompleted() && unit.getType().isWorker()) {
                arbitrator.setBid(this, unit, bid);
            }
        }
    }
    
    protected void addScout(Unit unit) {
        scouts.put(unit, new ScoutData());
    }
    
    protected void updateScoutAssignments() {
        //remove scout positions if the enemy is not there
        for (Entry<Unit, ScoutData> scout : scouts.entrySet()) {
            if (scout.getValue().mode == ScoutMode.SEARCHING &&
                    scout.getKey().getPosition().getDistance(scout.getValue().target) <
                    Game.TILE_SIZE * scout.getKey().getType().sightRange()) {
                Iterator<Position> positions = positionsToScout.iterator();
                while (positions.hasNext()) {
                    if (positions.next().equals(scout.getValue().target)) {
                        positions.remove();
                        break;
                    }
                }
                scout.getValue().mode = ScoutMode.IDLE;
            }
        }
        //set scouts to scout
        Iterator<Entry<Unit, ScoutData>> scoutIterator = scouts.entrySet().iterator();
        Iterator<Position> positionIterator = positionsToScout.iterator();
        if (positionIterator.hasNext()) {
            Position position = positionIterator.next();
            while (scoutIterator.hasNext() && positionIterator.hasNext()) {
                Entry<Unit, ScoutData> scout = scoutIterator.next();
                if (scout.getValue().mode == ScoutMode.IDLE) {
                    scout.getValue().mode = ScoutMode.SEARCHING;
                    scout.getKey().rightClick(position);
                    scout.getValue().target = position;
                    position = positionIterator.next();
                }
            }
        }
    }

    /**
     * Scout data for SAL
     * 
     * @author Chad Retz
     * 
     * @since 0.3
     */
    protected static final class ScoutData {
        protected Position target;
        protected ScoutMode mode = ScoutMode.IDLE;
        
        protected ScoutData() {
        }
    }
    
    /**
     * Scout mode for SAL
     * 
     * @author Chad Retz
     * 
     * @since 0.3
     */
    protected static enum ScoutMode {
        IDLE,
        SEARCHING,
        ROAMING,
        HARASSING,
        FLEEING
    }
}
