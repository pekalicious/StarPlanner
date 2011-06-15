#include <BWTA.h>
#include <ScoutManager.h>

std::pair<std::list<BWTA::BaseLocation*>, double> getBestPath(std::set<BWTA::BaseLocation* > baseLocations)
{
  std::pair<std::list<BWTA::BaseLocation*>, double> shortest_path;
  shortest_path.second=0;
  if (baseLocations.empty()) return shortest_path;
  if (baseLocations.size()==1)
  {
    shortest_path.first.push_back(*baseLocations.begin());
    return shortest_path;
  }
  for(std::set<BWTA::BaseLocation*>::iterator i=baseLocations.begin();i!=baseLocations.end();i++)
  {
    BWTA::BaseLocation* node=*i;
    std::set<BWTA::BaseLocation* > baseLocations2=baseLocations;
    baseLocations2.erase(*i);
    std::pair<std::list<BWTA::BaseLocation*>, double> path_result=getBestPath(baseLocations2);
    double dist=path_result.second+node->getGroundDistance(path_result.first.front());
    if (dist<shortest_path.second || shortest_path.first.empty())
    {
      path_result.first.push_front(node);
      shortest_path=std::make_pair(path_result.first,dist);
    }
  }
  return shortest_path;
}
ScoutManager::ScoutManager(Arbitrator::Arbitrator<BWAPI::Unit*,double> *arbitrator)
{
  this->arbitrator = arbitrator;
  desiredScoutCount = 0;

  myStartLocation = BWTA::getStartLocation(BWAPI::Broodwar->self());
  std::set<BWTA::BaseLocation *> startLocations = BWTA::getStartLocations();
  startLocations.erase(myStartLocation);
  std::set<BWTA::BaseLocation *>::iterator l_next;
  for(std::set<BWTA::BaseLocation *>::iterator l = startLocations.begin(); l != startLocations.end(); l=l_next)
  {
    l_next=l;
    l_next++;
    if (myStartLocation->getGroundDistance(*l) <= 0)
    {
      startLocations.erase(*l);
    }
  }
  std::list<BWTA::BaseLocation*> path=getBestPath(startLocations).first;
  for(std::list<BWTA::BaseLocation*>::iterator p=path.begin();p!=path.end();p++)
    positionsToScout.push_back((*p)->getPosition());
}

void ScoutManager::onOffer(std::set<BWAPI::Unit*> units)
{
  for(std::set<BWAPI::Unit*>::iterator u = units.begin(); u != units.end(); u++)
  {
    if ((*u)->getType().isWorker() && needMoreScouts())
    {
      arbitrator->accept(this, *u);
      addScout(*u);
    }
    else
      arbitrator->decline(this, *u, 0);
  }
}

void ScoutManager::onRevoke(BWAPI::Unit *unit, double bid)
{
  scouts.erase(unit);
}

void ScoutManager::update()
{
  if (needMoreScouts())
  {
    requestScout(/* bid = */ 12); // Bid 12.
  }
  else
  {
    while (scouts.size()>desiredScoutCount)
    {
      arbitrator->setBid(this, scouts.begin()->first,0);
      scouts.erase(scouts.begin());
    }
  }
  updateScoutAssignments();
}

std::string ScoutManager::getName() const
{
  return "Scout Manager";
}

void ScoutManager::onRemoveUnit(BWAPI::Unit* unit)
{
  scouts.erase(unit);
}

void ScoutManager::setScoutCount(int count)
{
  this->desiredScoutCount=count;
}

bool ScoutManager::isScouting() const
{
  return scouts.size() >= 1;
}

bool ScoutManager::needMoreScouts() const
{
  return scouts.size() < desiredScoutCount;
}

void ScoutManager::requestScout(double bid)
{
  // Bid on all completed workers.
  std::set<BWAPI::Unit*> myPlayerUnits=BWAPI::Broodwar->self()->getUnits();
  for(std::set<BWAPI::Unit*>::iterator u = myPlayerUnits.begin(); u != myPlayerUnits.end(); u++)
  {
    if ((*u)->isCompleted() && (*u)->getType().isWorker())
    {
      arbitrator->setBid(this, *u, bid);
    }
  }
}

void ScoutManager::addScout(BWAPI::Unit* u)
{
  ScoutData temp;
  scouts.insert(std::make_pair(u,temp));
}

void ScoutManager::updateScoutAssignments()
{
  // Remove scout positions if the enemy is not there.
  std::map<BWAPI::Unit*, ScoutData>::iterator u;
  for(u = scouts.begin(); u != scouts.end(); u++)
  {
    if ( (*u).second.mode == ScoutData::Searching
      && (*u).first->getPosition().getDistance((*u).second.target) < BWAPI::TILE_SIZE*(*u).first->getType().sightRange())
    {
      for(std::list<BWAPI::Position>::iterator p=positionsToScout.begin();p!=positionsToScout.end();p++)
      {
        if (*p==(*u).second.target)
        {
          positionsToScout.erase(p);
          break;
        }
      }
      (*u).second.mode = ScoutData::Idle;
    }
  }

  // Set scouts to scout.
  if (positionsToScout.size() > 0) // are there still positions to scout?
  {
    std::list<BWAPI::Position>::iterator p;
    for( u = scouts.begin(), p = positionsToScout.begin()
         ;
         u != scouts.end() && p != positionsToScout.end()
         ;
         u++) 
    { // for
      if ((*u).second.mode == ScoutData::Idle)
      {
        
        (*u).second.mode = ScoutData::Searching;
        (*u).first->rightClick(*p);
        (*u).second.target = *p;
        p++;
      }
    } // for
  }

}