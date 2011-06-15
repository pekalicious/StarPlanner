#pragma once
#include <Arbitrator.h>
#include <BWAPI.h>
class BuildingPlacer;
class ConstructionManager;
class ProductionManager;
class MorphManager;
class BuildManager
{
  public:
    BuildManager(Arbitrator::Arbitrator<BWAPI::Unit*,double>* arbitrator);
    ~BuildManager();
    void update();
    std::string getName() const;
    BuildingPlacer* getBuildingPlacer() const;
    void onRemoveUnit(BWAPI::Unit* unit);
    bool build(BWAPI::UnitType type);
    bool build(BWAPI::UnitType type, BWAPI::TilePosition goalPosition);
    int getPlannedCount(BWAPI::UnitType type) const;
    int getStartedCount(BWAPI::UnitType type) const;
    int getCompletedCount(BWAPI::UnitType type) const;
    void setBuildDistance(int distance);

  private:
    Arbitrator::Arbitrator<BWAPI::Unit*,double>* arbitrator;
    BuildingPlacer* buildingPlacer;
    ConstructionManager* constructionManager;
    ProductionManager* productionManager;
    MorphManager* morphManager;
};