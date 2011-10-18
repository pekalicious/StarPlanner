package com.pekalicious.starplanner;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

import com.pekalicious.agent.Agent;
import com.pekalicious.goap.PlannerAction;
import com.pekalicious.starplanner.actuators.BattleManager;
import com.pekalicious.starplanner.actuators.BuildPlanManager;
import com.pekalicious.starplanner.actuators.BuilderManager;
import com.pekalicious.starplanner.managers.AddonManager;
import com.pekalicious.starplanner.managers.GoalManager;
import com.pekalicious.starplanner.managers.ResourceManager;
import com.pekalicious.starplanner.managers.ScoutManager;
import com.pekalicious.starplanner.managers.SquadManager;
import com.pekalicious.starplanner.managers.TrainingManager;
import com.pekalicious.starplanner.sensors.EnemySensor;
import com.pekalicious.starplanner.sensors.UnitSensor;
import com.pekalicious.starplanner.util.LocationServices;

public class StarPlanner extends Agent {
	protected GoalManager goalManager;
	protected StrategicPlanManager strategicManager;

	// Sensors
	private UnitSensor unitSensor;
	private EnemySensor enemySensor;
	
	// Actuators
    protected BuildPlanManager buildPlanManager;
    private TrainingManager trainingManager;
    private AddonManager addonManager;
    
    // Managers
	@SuppressWarnings("unused")
	private ScoutManager scoutManager;
	private BattleManager battleManager;
	private SquadManager squadManager;
	
	protected Game game;
	
	private StarPlanner(Game game, String path) {
		super(new StarBlackboard());
		
		this.game = game;
		if (path == null)
			this.goalManager = new GoalManager();
		else
			this.goalManager = new GoalManager(path);
		this.strategicManager = new StrategicPlanManager(this, goalManager);
		this.buildPlanManager = new BuildPlanManager(this);
		
		if (game == null) return;
		ResourceManager.Instance.Init(this.game, (StarBlackboard) this.blackBoard);
		BuilderManager.Instance.Init(this.game, (StarBlackboard) this.blackBoard, ResourceManager.Instance.getBaseManagers());
		this.trainingManager = new TrainingManager(this.game, (StarBlackboard) this.blackBoard);
		//this.scoutManager = new ScoutManager(this.game, (StarBlackboard) this.blackBoard);
		this.addonManager = new AddonManager(this.game, (StarBlackboard) this.blackBoard);
		this.squadManager = new SquadManager(this);
		
		this.battleManager = new BattleManager(this.game);
		this.unitSensor = new UnitSensor(this.workingMemory);
		this.enemySensor = new EnemySensor(this.workingMemory);
	}
	
	public StarPlanner(String path) {
		this(null, path);
	}
	
	public StarPlanner(Game game) {
		this(game, null);
	}

	@Override
	public boolean hasAction(PlannerAction action) {
		return goalManager.hasAction(action);
	}
	
	public void update(Game game) {
		updateWorldState(game);
		
		LocationServices.Instance.update();
		ResourceManager.Instance.update();

		this.buildPlanManager.update();
		this.strategicManager.update();

		BuilderManager.Instance.update();
		this.trainingManager.update();
		this.addonManager.update();
		//this.scoutManager.update();
		this.battleManager.update();
		this.enemySensor.update();
		this.squadManager.update();
        this.unitSensor.update();
	}
	
	private void updateWorldState(Game game) {
		if (game == null) {
			System.out.println("GAME NULL!");
			return;
		}
	}

    public void onUnitCreate(Unit unit) {
        this.trainingManager.onUnitCreate(unit);
    }

    public void onUnitDestroy(Unit unit) {
    }

	public void onUnitShow(Unit enemyUnit) {
    	if (enemyUnit.getPlayer().equals(Game.getInstance().self())) return;
    	if (enemyUnit.getType().equals(UnitType.RESOURCE_VESPENE_GEYSER)) return;
    	if (enemyUnit.getType().equals(UnitType.RESOURCE_MINERAL_FIELD)) return;
    	if (enemyUnit.getType().equals(UnitType.ZERG_LARVA)) return;
    	
		this.enemySensor.onEnemyUnitShow(enemyUnit);
		this.squadManager.onEnemyUnitShow(enemyUnit);
	}
}
