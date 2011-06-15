#pragma once
#include <map>
#include <set>
#include <BWAPI.h>
#include <Arbitrator.h>
#include <BWTA.h>
#include <BuildOrderManager.h>
#include "Base.h"
class BaseManager
{
  public:
  BaseManager();
  void setBuildOrderManager(BuildOrderManager* builder);
  void update();
  void addBase(BWTA::BaseLocation* location);
  void expand(BWTA::BaseLocation* location);
  std::set<Base*> getActiveBases() const;
  std::set<Base*> getAllBases() const;

  std::string getName();
  void onRemoveUnit(BWAPI::Unit* unit);

  BuildOrderManager* builder;
  std::map<BWTA::BaseLocation*,Base*> location2base;
  std::set<Base*> allBases;
};