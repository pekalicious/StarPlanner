package org.bwapi.bridge.sal;

import java.util.Arrays;
import java.util.List;

import org.bwapi.bridge.model.Game;
import org.bwapi.bridge.model.TilePosition;
import org.bwapi.bridge.model.Unit;
import org.bwapi.bridge.model.UnitType;

/**
 * Building placer
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/BuildingPlacer">BuildingPlacer</a>
 * 
 * @author Chad Retz
 * 
 * @since 0.3
 */
public class BuildingPlacer {
    
    protected static final List<UnitType> EXTRA_SPACED =
        Arrays.asList(UnitType.TERRAN_COMMAND_CENTER,
                UnitType.TERRAN_FACTORY,
                UnitType.TERRAN_STARPORT,
                UnitType.TERRAN_SCIENCE_FACILITY);

    protected int buildDistance;
    protected final boolean[][] reserves = 
        new boolean[Game.getInstance().mapWidth()][Game.getInstance().mapHeight()];
    
    public BuildingPlacer() {
        buildDistance = 1;
        //java defaults booleans to false, so we're good here
    }
    
    public int getBuildDistance() {
        return buildDistance;
    }
    
    public void setBuildDistance(int buildDistance) {
        this.buildDistance = buildDistance;
    }
    
    public boolean canBuildHere(TilePosition position, UnitType type) {
        if (!Game.getInstance().canBuildHere(null, position, type)) {
            return false;
        }
        for (int x = position.x(); x < position.x() + type.tileWidth(); x++) {
            for (int y = position.y(); y < position.y() + type.tileWidth(); y++) {
                if (reserves[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean canBuildHereWithSpace(TilePosition position, UnitType type) {
        if (!canBuildHere(position, type)) {
            return false;
        }
        int width = type.tileWidth();
        int height = type.tileHeight();
        if (EXTRA_SPACED.contains(type)) {
            width += 2;
        }
        int startx = Math.max(position.x() - buildDistance, 0);
        int starty = Math.max(position.y() - buildDistance, 0);
        int endx = Math.min(position.x() + width + buildDistance, 
                Game.getInstance().mapWidth());
        int endy = Math.min(position.y() + height + buildDistance, 
                Game.getInstance().mapHeight());
        //TODO: be smarter than these nested loops
        for (int x = startx; x < endx; x++) {
            for (int y = starty; y < endy; y++) {
                if (!type.isRefinery() && !buildable(x, y)) {
                    return false;
                }
            }
        }
        if (position.x() > 3) {
            int startx2 = Math.max(startx - 2, 0);
            for (int x = startx2; x < startx; x++) {
                for (int y = starty; y < endy; y++) {
                    for (Unit unit : Game.getInstance().unitsOnTile(x, y)) {
                        if (!unit.isLifted() && EXTRA_SPACED.contains(unit.getType())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public TilePosition getBuildLocation(UnitType type) {
        //I don't really like this performance wise
        //TODO: update TilePosition to include setters for x and y
        //  so I don't have all this object creation here
        for (int x = 0; x < Game.getInstance().mapWidth(); x++) {
            for (int y = 0; y < Game.getInstance().mapHeight(); y++) {
                TilePosition position = new TilePosition(x, y);
                if (canBuildHere(position, type)) {
                    return position;
                }
            }
        }
        return TilePosition.NONE;
    }
    
    public TilePosition getBuildLocationNear(TilePosition position, UnitType type) {
        int x = position.x();
        int y = position.y();
        int length = 1;
        int j = 0;
        boolean first = true;
        int dx = 0;
        int dy = 0;
        while (length < Game.getInstance().mapWidth()) {
            if (x >= 0 && x < Game.getInstance().mapWidth() &&
                    y >= 0 && y < Game.getInstance().mapHeight() &&
                    canBuildHereWithSpace(new TilePosition(x, y), type)) {
                return new TilePosition(x, y);
            }
            x += dx;
            y += dy;
            j++;
            if (j == length) {
                j = 0;
                if (!first) {
                    length++;
                }
                first = !first;
                if (dx == 0) {
                    dx = dy;
                    dy = 0;
                } else {
                    dy = -dx;
                    dx = 0;
                }
            }
        }
        return TilePosition.NONE;
    }
    
    public boolean buildable(int x, int y) {
        if (!Game.getInstance().isBuildable(x, y)) {
            return false;
        }
        for (Unit unit : Game.getInstance().unitsOnTile(x, y)) {
            if (unit.getType().isBuilding() && !unit.isLifted()) {
                return false;
            }
        }
        return true;
    }
    
    public void reserveTiles(TilePosition position, int width, int height) {
        for (int x = position.x(); x < position.x() + width && x < Game.getInstance().mapWidth(); x++) {
            for (int y = position.y(); y < position.y() + height && y < Game.getInstance().mapHeight(); y++) {
                reserves[x][y] = true;
            }
        }
    }
    
    public void freeTiles(TilePosition position, int width, int height) {
        for (int x = position.x(); x < position.x() + width && x < Game.getInstance().mapWidth(); x++) {
            for (int y = position.y(); y < position.y() + height && y < Game.getInstance().mapHeight(); y++) {
                reserves[x][y] = false;
            }
        }
    }
}
