%include "stl.i"
%include "typemaps.i"
%javaconst(1);
%include "enumsimple.swg"

//Expose goodies in proxy classes
%typemap(javabody) SWIGTYPE %{
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public $javaclassname(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr($javaclassname obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public long getCPtr() {
    return swigCPtr;
  }
%}

//And in type wrappers
%typemap(javabody) SWIGTYPE *, SWIGTYPE &, SWIGTYPE [], SWIGTYPE (CLASS::*) %{
  private long swigCPtr;

  public $javaclassname(long cPtr, boolean bFutureUse) {
    swigCPtr = cPtr;
  }

  protected $javaclassname() {
    swigCPtr = 0;
  }

  public static long getCPtr($javaclassname obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }
  
  public long getCPtr() {
    return swigCPtr;
  }
%}

%typemap(javaimports) SWIGTYPE, SWIGTYPE *, SWIGTYPE &, SWIGTYPE [], SWIGTYPE (CLASS::*) "import org.bwapi.bridge.model.BwapiPointable;"
%typemap(javainterfaces) SWIGTYPE, SWIGTYPE *, SWIGTYPE &, SWIGTYPE [], SWIGTYPE (CLASS::*) "BwapiPointable"

%module(directors="1") bridge
%{
#include "ListWrapper.h"
#include "MapWrapper.h"
#include "SetWrapper.h"
#include "VectorWrapper.h"
#include "BWAPI.h"
#include "BWTA.h"
using namespace BWAPI;
%}

//Simple renames
%rename (SWIG_AttackType) BWAPI::AttackType;
%rename (SWIG_Color) BWAPI::Color;
%rename (SWIG_CoordinateType) BWAPI::CoordinateType;
%rename (SWIG_DamageType) BWAPI::DamageType;
%rename (SWIG_Error) BWAPI::Error;
%rename (SWIG_ExplosionType) BWAPI::ExplosionType;
%rename (SWIG_Flag) BWAPI::Flag;
%rename (SWIG_Force) BWAPI::Force;
%rename (SWIG_Game) BWAPI::Game;
%rename (SWIG_Latency) BWAPI::Latency;
%rename (SWIG_Order) BWAPI::Order;
%rename (SWIG_Player) BWAPI::Player;
%rename (SWIG_PlayerType) BWAPI::PlayerType;
%rename (SWIG_Position) BWAPI::Position;
%rename (SWIG_Race) BWAPI::Race;
%rename (SWIG_TechType) BWAPI::TechType;
%rename (SWIG_TilePosition) BWAPI::TilePosition;
%rename (SWIG_Unit) BWAPI::Unit;
%rename (SWIG_UnitSizeType) BWAPI::UnitSizeType;
%rename (SWIG_UnitType) BWAPI::UnitType;
%rename (SWIG_UpgradeType) BWAPI::UpgradeType;
%rename (SWIG_WeaponType) BWAPI::WeaponType;
%rename (SWIG_BaseLocation) BWTA::BaseLocation;
%rename (SWIG_Chokepoint) BWTA::Chokepoint;
%rename (SWIG_Polygon) BWTA::Polygon;
%rename (SWIG_Region) BWTA::Region;

//Abiguities
%rename (AttackTypeAcid_Spore) BWAPI::AttackTypes::Acid_Spore;
%rename (AttackTypeBurst_Lasers) BWAPI::AttackTypes::Burst_Lasers;
%rename (AttackTypeHalo_Rockets) BWAPI::AttackTypes::Halo_Rockets;
%rename (AttackTypeFragmentation_Grenade) BWAPI::AttackTypes::Fragmentation_Grenade;
%rename (AttackTypeGemeni_Missiles) BWAPI::AttackTypes::Gemini_Missiles;
%rename (AttackTypeGlave_Wurm) BWAPI::AttackTypes::Glave_Wurm;
%rename (initAttackTypes) BWAPI::AttackTypes::init;
%rename (AttackTypeLongbolt_Missile) BWAPI::AttackTypes::Longbolt_Missile;
%rename (AttackTypeNone) BWAPI::AttackTypes::None;
%rename (AttackTypePhase_Disruptor) BWAPI::AttackTypes::Phase_Disruptor;
%rename (AttackTypePulse_Cannon) BWAPI::AttackTypes::Pulse_Cannon;
%rename (AttackTypeSeeker_Spores) BWAPI::AttackTypes::Seeker_Spores;
%rename (AttackTypeSubterranean_Spines) BWAPI::AttackTypes::Subterranean_Spines;
%rename (AttackTypeUnknown) BWAPI::AttackTypes::Unknown;
%rename (AttackTypeYamato_Gun) BWAPI::AttackTypes::Yamato_Gun;

%rename (initColors) BWAPI::Colors::init;

%rename (CoordinateType) BWAPI::CoordinateType::Enum;

%rename (DamageTypeIndependent) BWAPI::DamageTypes::Independent;
%rename (initDamageTypes) BWAPI::DamageTypes::init;
%rename (DamageTypeNone) BWAPI::DamageTypes::None;
%rename (DamageTypeNormal) BWAPI::DamageTypes::Normal;
%rename (DamageTypeUnknown) BWAPI::DamageTypes::Unknown;

%rename (toErrorString) BWAPI::Error::toString;
%rename (initErrors) BWAPI::Errors::init;
%rename (ErrorNone) BWAPI::Errors::None;
%rename (ErrorUnknown) BWAPI::Errors::Unknown;

%rename (ExplosionTypeConsume) BWAPI::ExplosionTypes::Consume;
%rename (ExplosionTypeCorrosive_Acid) BWAPI::ExplosionTypes::Corrosive_Acid;
%rename (ExplosionTypeDark_Swarm) BWAPI::ExplosionTypes::Dark_Swarm;
%rename (ExplosionTypeDisruption_Web) BWAPI::ExplosionTypes::Disruption_Web;
%rename (ExplosionTypeEMP_Shockwave) BWAPI::ExplosionTypes::EMP_Shockwave;
%rename (ExplosionTypeEnsnare) BWAPI::ExplosionTypes::Ensnare;
%rename (ExplosionTypeFeedback) BWAPI::ExplosionTypes::Feedback;
%rename (initExplosionTypes) BWAPI::ExplosionTypes::init;
%rename (ExplosionTypeIrradiate) BWAPI::ExplosionTypes::Irradiate;
%rename (ExplosionTypeLockdown) BWAPI::ExplosionTypes::Lockdown;
%rename (ExplosionTypeMaelstrom) BWAPI::ExplosionTypes::Maelstrom;
%rename (ExplosionTypeMind_Control) BWAPI::ExplosionTypes::Mind_Control;
%rename (ExplosionTypeNone) BWAPI::ExplosionTypes::None;
%rename (ExplosionTypeNormal) BWAPI::ExplosionTypes::Normal;
%rename (ExplosionTypeOptical_Flare) BWAPI::ExplosionTypes::Optical_Flare;
%rename (ExplosionTypeParasite) BWAPI::ExplosionTypes::Parasite;
%rename (ExplosionTypePlague) BWAPI::ExplosionTypes::Plague;
%rename (ExplosionTypeRestoration) BWAPI::ExplosionTypes::Restoration;
%rename (ExplosionTypeStasis_Field) BWAPI::ExplosionTypes::Stasis_Field;
%rename (ExplosionTypeUnknown) BWAPI::ExplosionTypes::Unknown;
%rename (ExplosionTypeYamato_Gun) BWAPI::ExplosionTypes::Yamato_Gun;

%rename (Flag) BWAPI::Flag::Enum;

%rename (Latency) BWAPI::Latency::Enum;

%rename (OrderConsume) BWAPI::Orders::Consume;
%rename (OrderEnsnare) BWAPI::Orders::Ensnare;
%rename (initOrders) BWAPI::Orders::init;
%rename (OrderIrradiate) BWAPI::Orders::Irradiate;
%rename (OrderNeutral) BWAPI::Orders::Neutral;
%rename (OrderNone) BWAPI::Orders::None;
%rename (OrderPlague) BWAPI::Orders::Plague;
%rename (OrderRestoration) BWAPI::Orders::Restoration;
%rename (OrderUnknown) BWAPI::Orders::Unknown;

%rename (initPlayerTypes) BWAPI::PlayerTypes::init;
%rename (PlayerTypeNeutral) BWAPI::PlayerTypes::Neutral;
%rename (PlayerTypeNone) BWAPI::PlayerTypes::None;
%rename (PlayerTypeUnknown) BWAPI::PlayerTypes::Unknown;

%rename (xConst) BWAPI::Position::x() const;
%rename (yConst) BWAPI::Position::y() const;
%rename (PositionInvalid) BWAPI::Positions::Invalid;
%rename (PositionNone) BWAPI::Positions::None;
%rename (PositionUnknown) BWAPI::Positions::Unknown;

%rename (initRaces) BWAPI::Races::init;
%rename (RaceNone) BWAPI::Races::None;
%rename (RaceUnknown) BWAPI::Races::Unknown;

%rename (TechTypeConsume) BWAPI::TechTypes::Consume;
%rename (TechTypeDark_Swarm) BWAPI::TechTypes::Dark_Swarm;
%rename (TechTypeDisruption_Web) BWAPI::TechTypes::Disruption_Web;
%rename (TechTypeEMP_Shockwave) BWAPI::TechTypes::EMP_Shockwave;
%rename (TechTypeEnsnare) BWAPI::TechTypes::Ensnare;
%rename (TechTypeFeedback) BWAPI::TechTypes::Feedback;
%rename (initTechTypes) BWAPI::TechTypes::init;
%rename (TechTypeIrradiate) BWAPI::TechTypes::Irradiate;
%rename (TechTypeLockdown) BWAPI::TechTypes::Lockdown;
%rename (TechTypeMaelstrom) BWAPI::TechTypes::Maelstrom;
%rename (TechTypeMind_Control) BWAPI::TechTypes::Mind_Control;
%rename (TechTypeNone) BWAPI::TechTypes::None;
%rename (TechTypeNuclear_Strike) BWAPI::TechTypes::Nuclear_Strike;
%rename (TechTypeOptical_Flare) BWAPI::TechTypes::Optical_Flare;
%rename (TechTypeParasite) BWAPI::TechTypes::Parasite;
%rename (TechTypePlague) BWAPI::TechTypes::Plague;
%rename (TechTypePsionic_Storm) BWAPI::TechTypes::Psionic_Storm;
%rename (TechTypeRestoration) BWAPI::TechTypes::Restoration;
%rename (TechTypeSpawn_Broodlings) BWAPI::TechTypes::Spawn_Broodlings;
%rename (TechTypeStasis_Field) BWAPI::TechTypes::Stasis_Field;
%rename (TechTypeUnknown) BWAPI::TechTypes::Unknown;
%rename (TechTypeYamato_Gun) BWAPI::TechTypes::Yamato_Gun;
//arg!
%ignore BWAPI::TechType::getRace;

%rename (xConst) BWAPI::TilePosition::x() const;
%rename (yConst) BWAPI::TilePosition::y() const;
%rename (TilePositionInvalid) BWAPI::TilePositions::Invalid;
%rename (TilePositionNone) BWAPI::TilePositions::None;
%rename (TilePositionUnknown) BWAPI::TilePositions::Unknown;

%rename (UnitSizeTypeIndependent) BWAPI::UnitSizeTypes::Independent;
%rename (initUnitSizeTypes) BWAPI::UnitSizeTypes::init;
%rename (UnitSizeTypeNone) BWAPI::UnitSizeTypes::None;
%rename (UnitSizeTypeUnknown) BWAPI::UnitSizeTypes::Unknown;

%rename (initUnitTypes) BWAPI::UnitTypes::init;
%rename (UnitTypeNone) BWAPI::UnitTypes::None;
%rename (UnitTypeUnknown) BWAPI::UnitTypes::Unknown;

%rename (initUpgradeTypes) BWAPI::UpgradeTypes::init;
%rename (UpgradeTypeNone) BWAPI::UpgradeTypes::None;
%rename (UpgradeTypeUnknown) BWAPI::UpgradeTypes::Unknown;

%rename (WeaponTypeAcid_Spore) BWAPI::WeaponTypes::Acid_Spore;
%rename (WeaponTypeBurst_Lasers) BWAPI::WeaponTypes::Burst_Lasers;
%rename (WeaponTypeConsume) BWAPI::WeaponTypes::Consume;
%rename (WeaponTypeCorrosive_Acid) BWAPI::WeaponTypes::Corrosive_Acid;
%rename (WeaponTypeEnsnare) BWAPI::WeaponTypes::Ensnare;
%rename (WeaponTypeFragmentation_Grenade) BWAPI::WeaponTypes::Fragmentation_Grenade;
%rename (WeaponTypeGemini_Missiles) BWAPI::WeaponTypes::Gemini_Missiles;
%rename (WeaponTypeGlave_Wurm) BWAPI::WeaponTypes::Glave_Wurm;
%rename (WeaponTypeHalo_Rockets) BWAPI::WeaponTypes::Halo_Rockets;
%rename (initWeaponTypes) BWAPI::WeaponTypes::init;
%rename (WeaponTypeIrradiate) BWAPI::WeaponTypes::Irradiate;
%rename (WeaponTypeLongbolt_Missile) BWAPI::WeaponTypes::Longbolt_Missile;
%rename (WeaponTypeMind_Control) BWAPI::WeaponTypes::Mind_Control;
%rename (WeaponTypeNone) BWAPI::WeaponTypes::None;
%rename (WeaponTypeNuclear_Strike) BWAPI::WeaponTypes::Nuclear_Strike;
%rename (WeaponTypeNeutron_Flare) BWAPI::WeaponTypes::Neutron_Flare;
%rename (WeaponTypePhase_Disruptor) BWAPI::WeaponTypes::Phase_Disruptor;
%rename (WeaponTypePulse_Cannon) BWAPI::WeaponTypes::Pulse_Cannon;
%rename (WeaponTypePlague) BWAPI::WeaponTypes::Plague;
%rename (WeaponTypePsionic_Storm) BWAPI::WeaponTypes::Psionic_Storm;
%rename (WeaponTypeRestoration) BWAPI::WeaponTypes::Restoration;
%rename (WeaponTypeSeeker_Spores) BWAPI::WeaponTypes::Seeker_Spores;
%rename (WeaponTypeSpawn_Broodlings) BWAPI::WeaponTypes::SpawnBroodlings;
%rename (WeaponTypeSpider_Mines) BWAPI::WeaponTypes::Spider_Mines;
%rename (WeaponTypeSubterranean_Spines) BWAPI::WeaponTypes::Subterranean_Spines;
%rename (WeaponTypeUnknown) BWAPI::WeaponTypes::Unknown;
%rename (WeaponTypeYamato_Gun) BWAPI::WeaponTypes::Yamato_Gun;

//Operators
%rename (opAdd) *::operator+=;
%rename (opAssign) *::operator=;
%rename (opEquals) *::operator==;
%rename (opLessThan) *::operator<;
%rename (opMinus) *::operator-;
%rename (opNotEquals) *::operator!=;
%rename (opPlus) *::operator+;
%rename (opSubtract) *::operator-=;

%include "ListWrapper.h"
%include "MapWrapper.h"
%include "SetWrapper.h"
%include "VectorWrapper.h"

%include "BWAPI.h"
%include "BWAPI/AIModule.h"
%include "BWAPI/AttackType.h"
%include "BWAPI/Color.h"
%include "BWAPI/Constants.h"
%include "BWAPI/CoordinateType.h"
%include "BWAPI/DamageType.h"
%include "BWAPI/Error.h"
%include "BWAPI/ExplosionType.h"
%include "BWAPI/Flag.h"
%include "BWAPI/Force.h"
%include "BWAPI/Game.h"
%include "BWAPI/Latency.h"
%include "BWAPI/Order.h"
%include "BWAPI/Player.h"
%include "BWAPI/PlayerType.h"
%include "BWAPI/Position.h"
%include "BWAPI/Race.h"
%include "BWAPI/TechType.h"
%include "BWAPI/TilePosition.h"
%include "BWAPI/Unit.h"
%include "BWAPI/UnitSizeType.h"
%include "BWAPI/UnitType.h"
%include "BWAPI/UpgradeType.h"
%include "BWAPI/WeaponType.h"

%include "BWTA.h"
%include "BWTA/BaseLocation.h"
%include "BWTA/Chokepoint.h"
%include "BWTA/Polygon.h"
%include "BWTA/Region.h"

%template (DamageTypeSet) SetWrapper<BWAPI::DamageType>;
%template (DamageTypeSetIterator) SetIterator<BWAPI::DamageType>;
%template (ErrorSet) SetWrapper<BWAPI::Error>;
%template (ErrorSetIterator) SetIterator<BWAPI::Error>;
%template (ExplosionTypeSet) SetWrapper<BWAPI::ExplosionType>;
%template (ExplosionTypeSetIterator) SetIterator<BWAPI::ExplosionType>;
%template (ForceSet) SetWrapper<BWAPI::Force*>;
%template (ForceSetIterator) SetIterator<BWAPI::Force*>;
%template (OrderSet) SetWrapper<BWAPI::Order>;
%template (OrderSetIterator) SetIterator<BWAPI::Order>;
%template (PlayerSet) SetWrapper<BWAPI::Player*>;
%template (PlayerSetIterator) SetIterator<BWAPI::Player*>;
%template (PlayerTypeSet) SetWrapper<BWAPI::PlayerType>;
%template (PlayerTypeSetIterator) SetIterator<BWAPI::PlayerType>;
%template (RaceSet) SetWrapper<BWAPI::Race>;
%template (RaceSetIterator) SetIterator<BWAPI::Race>;
%template (TechTypeConstantSet) SetWrapper<const BWAPI::TechType*>;
%template (TechTypeConstantSetIterator) SetIterator<const BWAPI::TechType*>;
%template (TechTypeSet) SetWrapper<BWAPI::TechType>;
%template (TechTypeSetIterator) SetIterator<BWAPI::TechType>;
%template (UnitList) ListWrapper<BWAPI::Unit*>;
%template (UnitListIterator) ListIterator<BWAPI::Unit*>;
%template (UnitSet) SetWrapper<BWAPI::Unit*>;
%template (UnitSetIterator) SetIterator<BWAPI::Unit*>;
%template (UnitSizeTypeSet) SetWrapper<BWAPI::UnitSizeType>;
%template (UnitSizeTypeSetIterator) SetIterator<BWAPI::UnitSizeType>;
%template (UnitTypeConstantSet) SetWrapper<const BWAPI::UnitType*>;
%template (UnitTypeConstantSetIterator) SetIterator<const BWAPI::UnitType*>;
%template (UnitTypeSet) SetWrapper<BWAPI::UnitType>;
%template (UnitTypeSetIterator) SetIterator<BWAPI::UnitType>;
%template (UnitTypeWhatBuildsPair) std::pair<const BWAPI::UnitType*,int>;
%template (UnitTypeMap) MapWrapper<const UnitType*, int>;
%template (UnitTypeMapIterator) MapIterator<const UnitType*, int>;
%template (UnitTypeList) ListWrapper<BWAPI::UnitType>;
%template (UnitTypeListIterator) ListIterator<BWAPI::UnitType>;
%template (UpgradeTypeConstantSet) SetWrapper<const BWAPI::UpgradeType*>;
%template (UpgradeTypeConstantSetIterator) SetIterator<const BWAPI::UpgradeType*>;
%template (UpgradeTypeSet) SetWrapper<BWAPI::UpgradeType>;
%template (UpgradeTypeSetIterator) SetIterator<BWAPI::UpgradeType>;
%template (WeaponTypeSet) SetWrapper<BWAPI::WeaponType>;
%template (WeaponTypeSetIterator) SetIterator<BWAPI::WeaponType>;
%template (TilePositionSet) SetWrapper<BWAPI::TilePosition>;
%template (TilePositionSetIterator) SetIterator<BWAPI::TilePosition>;

%template (RegionSet) SetWrapper<BWTA::Region*>;
%template (RegionSetIterator) SetIterator<BWTA::Region*>;
%template (RegionSpacelessSet) SetWrapper<Region*>;
%template (RegionSpacelessSetIterator) SetIterator<Region*>;
%template (ChokepointSet) SetWrapper<BWTA::Chokepoint*>;
%template (ChokepointSetIterator) SetIterator<BWTA::Chokepoint*>;
%template (ChokepointSpacelessSet) SetWrapper<Chokepoint*>;
%template (ChokepointSpacelessSetIterator) SetIterator<Chokepoint*>;
%template (ChokepointGetRegionsPair) std::pair<BWTA::Region*, BWTA::Region*>;
%template (ChokepointGetSidesPair) std::pair<BWAPI::Position, BWAPI::Position>;
%template (BaseLocationSet) SetWrapper<BWTA::BaseLocation*>;
%template (BaseLocationSetIterator) SetIterator<BWTA::BaseLocation*>;
%template (BaseLocationSpacelessSet) SetWrapper<BaseLocation*>;
%template (BaseLocationSpacelessSetIterator) SetIterator<BaseLocation*>;
%template (PolygonSet) SetWrapper<BWTA::Polygon*>;
%template (PolygonSetIterator) SetIterator<BWTA::Polygon*>;
%template (PolygonSpacelessSet) SetWrapper<Polygon*>;
%template (PolygonSpacelessSetIterator) SetIterator<Polygon*>;
%template (PositionVector) VectorWrapper<BWAPI::Position>;