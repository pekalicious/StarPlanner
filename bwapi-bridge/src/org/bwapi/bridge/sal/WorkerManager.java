package org.bwapi.bridge.sal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Order;
import org.bwapi.bridge.model.Unit;

/**
 * Worker manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/WorkerManager">WorkerManager</a>
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public class WorkerManager extends ArbitratedManager {
    protected static final List<Order> WORKER_RESOURCES = Arrays.asList(
            Order.MOVE_TO_MINERALS, Order.WAIT_FOR_MINERALS,
            Order.MOVE_TO_GAS, Order.WAIT_FOR_GAS, Order.PLAYER_GUARD);
    
    protected BaseManager baseManager;
    protected final Map<Unit, WorkerData> workers =
        new HashMap<Unit, WorkerData>();
    protected final Map<Unit, Set<Unit>> currentWorkers =
        new HashMap<Unit, Set<Unit>>();
    protected final Map<Unit, Base> resourceBase =
        new HashMap<Unit, Base>();
    protected final Map<Unit, Integer> desiredWorkerCount =
        new HashMap<Unit, Integer>();
    protected final List<Entry<Unit, Integer>> mineralOrder =
        new ArrayList<Entry<Unit, Integer>>();
    protected int mineralOrderIndex;
    protected int lastScvBalance = 0;
    protected Set<Base> basesCache;
    protected int workersPerGas = 3;

    public WorkerManager(Arbitrator<Unit, Double> arbitrator) {
        super(arbitrator);
    }
    
    public void setBaseManager(BaseManager baseManager) {
        this.baseManager = baseManager;
    }
    
    @Override
    public void onOffer(Set<Unit> units) {
        for (Unit unit : units) {
            if (unit.getType().isWorker() && !mineralOrder.isEmpty()) {
                arbitrator.accept(this, unit);
                WorkerData data = new WorkerData();
                Unit worker = mineralOrder.get(mineralOrderIndex).getKey();
                incrementMapValue(desiredWorkerCount, worker);
                Set<Unit> workerSet = currentWorkers.get(worker);
                if (workerSet == null) {
                    workerSet = new HashSet<Unit>();
                    currentWorkers.put(worker, workerSet);
                }
                workerSet.add(unit);
                data.newResource = worker;
                mineralOrderIndex = (mineralOrderIndex + 1) % mineralOrder.size();
                workers.put(unit, data);
            } else {
                arbitrator.decline(this, unit, 0D);
            }
        }
    }
    
    @Override
    public void onRevoke(Unit unit, Double bid) {
        onRemoveUnit(unit);
    }
    
    public void updateWorkerAssignments() {
        //determine current worker assignments
        //  also workers that are mining from resources that don't
        //  belong to any of our bases will be set to free
        for (Entry<Unit, WorkerData> worker : workers.entrySet()) {
            if (worker.getValue().newResource != null) {
                if (!resourceBase.containsKey(worker.getValue().newResource)) {
                    worker.getValue().newResource = null;
                } else {
                    Set<Unit> units = currentWorkers.get(worker.getValue().newResource);
                    if  (units == null) {
                        units = new HashSet<Unit>();
                        currentWorkers.put(worker.getValue().newResource, units);
                    }
                    units.add(worker.getKey());
                }
            }
        }
        //get free workers
        Set<Unit> freeWorkers = new HashSet<Unit>();
        for (Entry<Unit, WorkerData> worker : workers.entrySet()) {
            if (worker.getValue().newResource == null) {
                freeWorkers.add(worker.getKey());
            }
        }
        //free workers from resources w/ too many workers
        for (Entry<Unit, Integer> desired : desiredWorkerCount.entrySet()) {
            if (currentWorkers.containsKey(desired.getKey()) && 
                    desired.getValue() < currentWorkers.get(desired.getKey()).size()) {
                //desired worker count is less that the current worker count for
                //  this resource, so lets remove some workers
                int amountToRemove = currentWorkers.get(desired.getKey()).size() -
                    desired.getValue();
                for (int i = 0; i < amountToRemove; i++) {
                    Unit worker = currentWorkers.get(desired.getKey()).iterator().next();
                    freeWorkers.add(worker);
                    //TODO: check for worker presence?
                    workers.get(worker).newResource = null;
                    currentWorkers.get(desired.getKey()).remove(worker);
                }
            }
        }
        for (Entry<Unit, Integer> desired : desiredWorkerCount.entrySet()) {
            //assign workers to resources that need more workers
            if (currentWorkers.containsKey(desired.getKey()) && 
                    desired.getValue() > currentWorkers.get(desired.getKey()).size()) {
                //desired worker count is more than the current worker
                //  count for this resource, so lets assign some workers
                int amountToAdd = desired.getValue() - currentWorkers.get(desired.getKey()).size();
                for (int i = 0; i < amountToAdd; i++) {
                    Unit worker = freeWorkers.iterator().next();
                    freeWorkers.remove(worker);
                    WorkerData data = workers.get(worker);
                    if (data != null) {
                        data.newResource = desired.getKey();
                        Set<Unit> units = currentWorkers.get(desired.getKey());
                        if (units == null) {
                            units = new HashSet<Unit>();
                            currentWorkers.put(desired.getKey(), units);
                        }
                        units.add(worker);
                    }
                }
            }
        }
    }
    
    public boolean mineralCompare(Entry<Unit, Integer> one, Entry<Unit, Integer> two) {
        return one.getValue() > two.getValue();
    }
    
    public void rebalanceWorkers() {
        mineralOrder.clear();
        desiredWorkerCount.clear();
        currentWorkers.clear();
        resourceBase.clear();
        int remainingWorkers = workers.size();
        //iterate over all the resources of each active base
        for (Base base : basesCache) {
            for (Unit baseMineral : base.getMinerals()) {
                resourceBase.put(baseMineral, base);
                desiredWorkerCount.put(baseMineral, 0);
                currentWorkers.remove(baseMineral);
                mineralOrder.add(new Pair<Unit, Integer>(baseMineral, baseMineral.getResources() - 2 * 
                        (int) baseMineral.getPosition().getDistance(base.getBaseLocation().getPosition())));
                
            }
            for (Unit geyser : base.getGeysers()) {
                resourceBase.put(geyser, base);
                desiredWorkerCount.put(geyser, 0);
                if (geyser.getType().isRefinery() && geyser.getPlayer().equals(
                        Game.getInstance().self()) && geyser.isCompleted()) {
                    for (int i = 0; i < workersPerGas && remainingWorkers > 0; i++) {
                        incrementMapValue(desiredWorkerCount, geyser);
                        remainingWorkers--;
                    }
                }
                currentWorkers.remove(geyser);
            }
        }
        //if no resources exist, return
        if (!mineralOrder.isEmpty()) {
            //order minerals by score (based on distance and resource amount)
            Collections.sort(mineralOrder, new Comparator<Entry<Unit, Integer>>() {
                @Override
                public int compare(Entry<Unit, Integer> one, Entry<Unit, Integer> two) {
                    return mineralCompare(one, two) ? -1 : 1;
                }
            });
            //calculate optimal worker count for each mineral patch
            mineralOrderIndex = 0;
            while (remainingWorkers > 0) {
                incrementMapValue(desiredWorkerCount, mineralOrder.get(mineralOrderIndex).getKey());
                remainingWorkers--;
                mineralOrderIndex = (mineralOrderIndex + 1) % mineralOrder.size();
            }
        }
        updateWorkerAssignments();
    }
    
    @Override
    public void update() {
        //bid a constant value of 10 on all completed workers
        for (Unit unit : Game.getInstance().self().getUnits()) {
            if (unit.isCompleted() && unit.getType().isWorker()) {
                arbitrator.setBid(this, unit, 10D);
            }
        }
        //rebalance workers when necessary
        Collection<Base> bases = baseManager.getActiveBases();
        //TODO: should I really compare entire sets here?
        if (Game.getInstance().getFrameCount() > lastScvBalance + 5 * 25 || lastScvBalance == 0 ||
                !bases.equals(basesCache)) {
            basesCache = new HashSet<Base>(bases);
            lastScvBalance = Game.getInstance().getFrameCount();
            rebalanceWorkers();
        }
        //order workers to gather from their assigned resources
        for (Entry<Unit, WorkerData> worker : workers.entrySet()) {
            //switch current resource to newResource when appropriate
            if (worker.getValue().resource == null || (worker.getKey().getTarget() != null &&
                    worker.getKey().getTarget().getType().isResourceDepot())) {
                worker.getValue().resource = worker.getValue().newResource;
            }
            if (WORKER_RESOURCES.contains(worker.getKey().getOrder()) &&
                    (worker.getKey().getTarget() == null || !worker.getKey().getTarget().exists() ||
                            !worker.getKey().getTarget().getType().isResourceDepot()) && 
                            !worker.getKey().getTarget().equals(worker.getValue().resource)) {
                worker.getKey().rightClick(worker.getValue().resource);
            }
        }
    }
    
    @Override
    public String getName() {
        return "Worker Manager";
    }
    
    public void onRemoveUnit(Unit unit) {
        workers.remove(unit);
    }
    
    public void setWorkersPerGas(int workersPerGas) {
        this.workersPerGas = workersPerGas;
    }
    
    /**
     * Worker data
     * 
     * @author Chad Retz
     *
     * @since 0.3
     */
    protected static final class WorkerData {
        protected Unit resource;
        protected Unit newResource;
        //TODO: figure out the purpose of this
        protected int lastFrameSpam = 0;
        
        protected WorkerData() {
        }
    }
}
