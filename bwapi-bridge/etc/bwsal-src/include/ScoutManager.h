#pragma once
#include <Arbitrator.h>
#include <BWAPI.h>

class ScoutManager : public Arbitrator::Controller<BWAPI::Unit*,double>
{
  public:
    class ScoutData
    {
      public:
        enum ScoutMode
        {
          Idle,
          Searching,
          Roaming,
          Harassing,
          Fleeing
        };
        ScoutData(){ mode = Idle; }
        BWAPI::Position target;
        ScoutMode mode;
    };
    ScoutManager(Arbitrator::Arbitrator<BWAPI::Unit*,double>* arbitrator);
    virtual void onOffer(std::set<BWAPI::Unit*> units);
    virtual void onRevoke(BWAPI::Unit* unit, double bid);
    virtual void update();

    virtual std::string getName() const;
    void onRemoveUnit(BWAPI::Unit* unit);

    // Non-Controller methods.
    bool isScouting() const;
    void setScoutCount(int count);

    std::map<BWAPI::Unit*, ScoutData> scouts;
    Arbitrator::Arbitrator<BWAPI::Unit*,double>* arbitrator;
    
    std::list<BWAPI::Position> positionsToScout;
    BWTA::BaseLocation *myStartLocation;
        
  private:
    bool needMoreScouts() const;
    void requestScout(double bid);
    void addScout(BWAPI::Unit* unit);
    void updateScoutAssignments();

    size_t desiredScoutCount;
    int scoutingStartFrame;
};