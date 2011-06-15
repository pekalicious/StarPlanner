package org.bwapi.bridge.sal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;
import org.bwapi.bridge.model.UpgradeType;

/**
 * Upgrade manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/UpgradeManager">UpgradeManager</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.3
 */
public class UpgradeManager extends ArbitratedManager {
    
    protected BuildingPlacer placer;
    protected final Map<UnitType, List<Upgrade>> upgradeQueues =
        new HashMap<UnitType, List<Upgrade>>();
    protected final Map<Unit, Upgrade> upgradingUnits = 
        new HashMap<Unit, Upgrade>();
    protected final Map<UpgradeType, Integer> plannedLevel =
        new HashMap<UpgradeType, Integer>();
    protected final Map<UpgradeType, Integer> startedLevel =
        new HashMap<UpgradeType, Integer>();
    
    public UpgradeManager(Arbitrator<Unit, Double> arbitrator) {
        super(arbitrator);
        for (UpgradeType type : UpgradeType.allUpgradeTypes()) {
            plannedLevel.put(type, 0);
            startedLevel.put(type, 0);
        }
    }
    
    public void setBuildingPlacer(BuildingPlacer buildingPlacer) {
        placer = buildingPlacer;
    }
    
    @Override
    public void onOffer(Set<Unit> units) {
        for (Unit unit : units) {
            boolean used = false;
            List<Upgrade> upgrades = upgradeQueues.get(unit.getType());
            if (upgrades != null) {
                Iterator<Upgrade> upgradeIterator = upgrades.iterator();
                while (upgradeIterator.hasNext()) {
                    Upgrade upgrade = upgradeIterator.next();
                    if (Game.getInstance().canUpgrade(unit, upgrade.type) &&
                            unit.isIdle()) {
                        upgradingUnits.put(unit, upgrade);
                        upgradeIterator.remove();
                        arbitrator.accept(this, unit);
                        arbitrator.setBid(this, unit, 100D);
                        used = true;
                        break;
                    }
                }
            }
            if (!used) {
                arbitrator.decline(this, unit, 0D);
                arbitrator.removeBid(this, unit);
            }
        }
    }
    
    @Override
    public void onRevoke(Unit unit, Double bid) {
        onRemoveUnit(unit);
    }
    
    @Override
    public void update() {
        for (Unit unit : Game.getInstance().self().getUnits()) {
            List<Upgrade> upgrades = upgradeQueues.get(unit.getType());
            if (upgrades != null && !upgrades.isEmpty()) {
                arbitrator.setBid(this, unit, 50D);
            }
        }
        Iterator<Entry<Unit, Upgrade>> upgradeIterator = upgradingUnits.entrySet().iterator();
        while (upgradeIterator.hasNext()) {
            Entry<Unit, Upgrade> upgradingUnit = upgradeIterator.next();
            if (upgradingUnit.getKey().isUpgrading()) {
                if (!upgradingUnit.getKey().getUpgrade().equals(
                        upgradingUnit.getValue().type)) {
                    upgradingUnit.getKey().cancelUpgrade();
                } else if (startedLevel.get(upgradingUnit.getValue().type) <
                        upgradingUnit.getValue().level) {
                    startedLevel.put(upgradingUnit.getValue().type, 
                            upgradingUnit.getValue().level);
                }
            } else {
                if (Game.getInstance().self().getUpgradeLevel(upgradingUnit.getValue().type) >=
                        upgradingUnit.getValue().level) {
                    upgradeIterator.remove();
                    arbitrator.removeBid(this, upgradingUnit.getKey());
                } else {
                    if (upgradingUnit.getKey().isLifted()) {
                        if (upgradingUnit.getKey().isIdle()) {
                            upgradingUnit.getKey().land(placer.getBuildLocationNear(upgradingUnit.
                                    getKey().getTilePosition().opAdd(new TilePosition(0, 1)), 
                                    upgradingUnit.getKey().getType()));
                        }
                    } else {
                        if (Game.getInstance().canUpgrade(upgradingUnit.getKey(), upgradingUnit.getValue().type)) {
                            upgradingUnit.getKey().upgrade(upgradingUnit.getValue().type);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public String getName() {
        return "Upgrade Manager";
    }
    
    public void onRemoveUnit(Unit unit) {
        Upgrade upgrade = upgradingUnits.get(unit);
        if (upgrade != null) {
            if (Game.getInstance().self().getUpgradeLevel(upgrade.type) < upgrade.level) {
                List<Upgrade> upgrades = upgradeQueues.get(upgrade.type.whatUpgrades());
                if (upgrades == null) {
                    upgrades = new ArrayList<Upgrade>();
                    upgradeQueues.put(upgrade.type.whatUpgrades(), upgrades);
                }
                upgrades.add(0, upgrade);
            }
            upgradingUnits.remove(unit);
        }
    }
    
    public boolean upgrade(UpgradeType upgrade) {
        int level = Game.getInstance().self().getUpgradeLevel(upgrade) + 1;
        if (level > upgrade.maxRepeats()) {
            return false;
        }
        Upgrade newUpgrade = new Upgrade();
        newUpgrade.type = upgrade;
        newUpgrade.level = level;
        List<Upgrade> upgrades = upgradeQueues.get(upgrade.whatUpgrades());
        if (upgrades == null) {
            upgrades = new ArrayList<Upgrade>();
            upgradeQueues.put(upgrade.whatUpgrades(), upgrades);
        }
        upgrades.add(newUpgrade);
        plannedLevel.put(upgrade, level);
        return true;
    }
    
    public int getPlannedLevel(UpgradeType type) {
        return plannedLevel.get(type);
    }
    
    public int getStartedLevel(UpgradeType type) {
        return startedLevel.get(type);
    }
    
    public int getCompletedLevel(UpgradeType type) {
        return Game.getInstance().self().getUpgradeLevel(type);
    }

    /**
     * SAL version of an upgrade
     * 
     * @author Chad Retz
     *
     * @since 0.3
     */
    protected static final class Upgrade {
        protected UpgradeType type;
        protected int level;
        
        protected Upgrade() {
        }
    }
}
