#pragma once
#include <BWAPI.h>
#include <vector>
namespace BWTA
{
  class Polygon : public std::vector <BWAPI::Position>
  {
    public:
    Polygon();
    double getArea() const;
    double getPerimeter() const;
    BWAPI::Position getCenter() const;
    bool isInside(BWAPI::Position p) const;
    BWAPI::Position getNearestPoint(BWAPI::Position p) const;
  };
}