#pragma once
#include <BWAPI.h>
#include <BWTA/Chokepoint.h>
#include <BWTA/Polygon.h>
#include <BWTA/Region.h>
#include <BWTA/BaseLocation.h>
namespace BWTA
{
  void readMap();
  void analyze();
  const std::set<Region*>& getRegions();
  const std::set<Chokepoint*>& getChokepoints();
  const std::set<BaseLocation*>& getBaseLocations();
  const std::set<BaseLocation*>& getStartLocations();
  const std::set<Polygon*>& getUnwalkablePolygons();
  BWAPI::Position getNearestUnwalkablePosition(BWAPI::Position position);
  BaseLocation* getStartLocation(BWAPI::Player* player);
  BaseLocation* getNearestBaseLocation(BWAPI::TilePosition position);
}