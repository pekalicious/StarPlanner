package org.bwapi.bridge.sal;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

/**
 * Supply manager
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/SupplyManager">SupplyManager</a>
 * 
 * @author Chad Retz
 *
 * @since 0.3
 */
public class SupplyManager {

    protected BuildManager buildManager;
    protected BuildOrderManager buildOrderManager;
    protected int lastFrameCheck;
    
    public SupplyManager() {
        
    }
    
    public void setBuildManager(BuildManager buildManager) {
        this.buildManager = buildManager;
    }
    
    public void setBuildOrderManager(BuildOrderManager buildOrderManager) {
        this.buildOrderManager = buildOrderManager;
    }
    
    public void update() {
        if (Game.getInstance().getFrameCount() > lastFrameCheck + 25) {
            int productionCapacity = 0;
            lastFrameCheck = Game.getInstance().getFrameCount();
            for (Unit unit : Game.getInstance().self().getUnits()) {
                if (unit.getType().canProduce()) {
                    productionCapacity += 4;
                }
                if (getPlannedSupply() <= Game.getInstance().self().
                        supplyUsed() + productionCapacity) {
                    buildManager.build(Game.getInstance().self().
                            getRace().getSupplyProvider());
                    buildOrderManager.spendResources(Game.getInstance().
                            self().getRace().getSupplyProvider());
                }
            }
        }
    }
    
    public String getName() {
        return "Supply Manager";
    }
    
    public int getPlannedSupply() {
        int plannedSupply = 0;
        plannedSupply += buildManager.getPlannedCount(UnitType.TERRAN_SUPPLY_DEPOT) * UnitType.TERRAN_SUPPLY_DEPOT.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.TERRAN_COMMAND_CENTER) * UnitType.TERRAN_COMMAND_CENTER.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.PROTOSS_PYLON) * UnitType.PROTOSS_PYLON.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.PROTOSS_NEXUS) * UnitType.PROTOSS_NEXUS.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.ZERG_OVERLORD) * UnitType.ZERG_OVERLORD.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.ZERG_HATCHERY) * UnitType.ZERG_HATCHERY.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.ZERG_LAIR) * UnitType.ZERG_LAIR.supplyProvided();
        plannedSupply += buildManager.getPlannedCount(UnitType.ZERG_HIVE) * UnitType.ZERG_HIVE.supplyProvided();
        return plannedSupply;
    }
}
