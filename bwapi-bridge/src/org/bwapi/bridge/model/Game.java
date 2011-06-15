package org.bwapi.bridge.model;

import java.util.Set;

import org.bwapi.bridge.swig.SWIG_Game;
import org.bwapi.bridge.swig.SWIG_Player;
import org.bwapi.bridge.swig.bridge;

/**
 * Overall game. This is a singleton that returns the game.
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Game">Game</a>
 * 
 * @author Chad Retz
 */
public final class Game extends BwapiObject {
    
    public static final int TILE_SIZE = bridge.getTILE_SIZE();
    public static final int PYLON_X_RADIUS = bridge.getPYLON_X_RADIUS();
    public static final int PYLON_Y_RADIUS = bridge.getPYLON_Y_RADIUS();
    
    private static final Game instance = new Game(bridge.getBroodwar());
    
    public static Game getInstance() {
        return instance;
    }

    private final SWIG_Game game;
    
    private Game(SWIG_Game game) {
        this.game = game;
    }
    
    @Override
    SWIG_Game getOriginalObject() {
        return game;
    }
    
    public Set<Force> getForces() {
        return Force.newSet(game.getForces());
    }
    
    public Set<Player> getPlayers() {
        return Player.newSet(game.getPlayers());
    }
    
    public Set<Unit> getAllUnits() {
        return Unit.newSet(game.getAllUnits());
    }
    
    public Set<Unit> getMinerals() {
        return Unit.newSet(game.getMinerals());
    }
    
    public Set<Unit> getGeysers() {
        return Unit.newSet(game.getGeysers());
    }
    
    public Set<Unit> getNeutralUnits() {
        return Unit.newSet(game.getNeutralUnits());
    }
    
    public Set<Unit> getStaticMinerals() {
        return Unit.newSet(game.getStaticMinerals());
    }
    
    public Set<Unit> getStaticGeysers() {
        return Unit.newSet(game.getStaticGeysers());
    }
    
    public Set<Unit> getStaticNeutralUnits() {
        return Unit.newSet(game.getStaticNeutralUnits());
    }
    
    public Latency getLatency() {
        return Latency.getAllValues().get(game.getLatency());
    }
    
    public int getFrameCount() {
        return game.getFrameCount();
    }
    
    public int getMouseX() {
        return game.getMouseX();
    }
    
    public int getMouseY() {
        return game.getMouseY();
    }
    
    public int getScreenX() {
        return game.getScreenX();
    }
    
    public int getScreenY() {
        return game.getScreenY();
    }
    
    public boolean isFlagEnabled(Flag flag) {
        return game.isFlagEnabled(flag.getBwapiOrdinal());
    }

    public void enableFlag(Flag flag) {
        game.enableFlag(flag.getBwapiOrdinal());
    }
    
    public Set<Unit> unitsOnTile(int x, int y) {
        return Unit.newSet(game.unitsOnTile(x, y));
    }
    
    public Error getLastError() {
        return new Error(game.getLastError());
    }
    
    public int mapWidth() {
        return game.mapWidth();
    }
    
    public int mapHeight() {
        return game.mapHeight();
    }
    
    public String mapFilename() {
        return game.mapFilename();
    }
    
    public String mapName() {
        return game.mapName();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @deprecated Changed in BWAPI 2.4, use {@link #isBuildable(int, int)}
     */
    @Deprecated
    public boolean buildable(int x, int y) {
        return isBuildable(x, y);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @since 0.2
     */
    public boolean isBuildable(int x, int y) {
        return game.isBuildable(x, y);
    }
    
    /**
     * 
     * @param position
     * @return
     * 
     * @since 0.2
     */
    public boolean isBuildable(TilePosition position) {
        return game.isBuildable(position.getPointer());
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @deprecated Changed in BWAPI 2.4, use {@link #isWalkable(int, int)}
     */
    @Deprecated
    public boolean walkable(int x, int y) {
        return isWalkable(x, y);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @since 0.2
     */
    public boolean isWalkable(int x, int y) {
        return game.isWalkable(x, y);
    }

    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @deprecated Changed in BWAPI 2.4, use {@link #isWalkable(int, int)}
     */
    @Deprecated
    public boolean visible(int x, int y) {
        return isVisible(x, y);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @since 0.2
     */
    public boolean isVisible(int x, int y) {
        return game.isVisible(x, y);
    }
    
    /**
     * 
     * @param position
     * @return
     * 
     * @since 0.2
     */
    public boolean isVisible(TilePosition position) {
        return game.isVisible(position.getPointer());
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @since 0.2
     */
    public boolean isExplored(int x, int y) {
        return game.isExplored(x, y);
    }
    
    /**
     * 
     * @param position
     * @return
     * 
     * @since 0.2
     */
    public boolean isExplored(TilePosition position) {
        return game.isExplored(position.getPointer());
    }
    
    public boolean hasCreep(int x, int y) {
        return game.hasCreep(x, y);
    }
    
    /**
     * 
     * @param position
     * @return
     * 
     * @since 0.2
     */
    public boolean hasCreep(TilePosition position) {
        return game.hasCreep(position.getPointer());
    }

    public boolean hasPower(int x, int y, int tileWidth, int tileHeight) {
        return game.hasPower(x, y, tileWidth, tileHeight);
    }
    
    /**
     * 
     * @param position
     * @param tileWidth
     * @param tileHeight
     * @return
     * 
     * @since 0.2
     */
    public boolean hasPower(TilePosition position, int tileWidth, int tileHeight) {
        return game.hasPower(position.getPointer(), tileWidth, tileHeight);
    }

    public boolean canBuildHere(Unit builder, TilePosition position,
            UnitType type) {
        return game.canBuildHere(builder.getOriginalObject(), position.getPointer(), 
                type.getOriginalObject());
    }

    public boolean canMake(Unit builder, UnitType type) {
        return game.canMake(builder.getOriginalObject(), type.getOriginalObject());
    }

    public boolean canResearch(Unit unit, TechType type) {
        return game.canResearch(unit.getOriginalObject(), type.getPointer());
    }

    public boolean canUpgrade(Unit unit, UpgradeType type) {
        return game.canUpgrade(unit.getOriginalObject(), type.getPointer());
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @deprecated Changed in BWAPI 2.4, use {@link #getGroundHeight(int, int)}
     */
    @Deprecated
    public int groundHeight(int x, int y) {
        return getGroundHeight(x, y);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     * 
     * @since 0.2
     */
    public int getGroundHeight(int x, int y) {
        return game.getGroundHeight(x, y);
    }

    public Set<TilePosition> getStartLocations() {
        return TilePosition.newSet(game.getStartLocations());
    }

    public int getMapHash() {
        return game.getMapHash();
    }

    public void printf(String text) {
        game.printf(text);
    }

    public void sendText(String text) {
        game.sendText(text);
    }

    public void changeRace(Race race) {
        game.changeRace(race.getPointer());
    }

    public boolean isMultiplayer() {
        return game.isMultiplayer();
    }

    public boolean isPaused() {
        return game.isPaused();
    }

    public boolean isReplay() {
        return game.isReplay();
    }

    public void startGame() {
        game.startGame();
    }

    public void pauseGame() {
        game.pauseGame();
    }

    public void resumeGame() {
        game.resumeGame();
    }

    public void leaveGame() {
        game.leaveGame();
    }
    
    /**
     * 
     * @since 0.2
     */
    public void restartGame() {
        game.restartGame();
    }

    public void setLocalSpeed(int speed) {
        game.setLocalSpeed(speed);
    }

    public void setLocalSpeed() {
        game.setLocalSpeed();
    }

    public Set<Unit> getSelectedUnits() {
        return Unit.newSet(game.getSelectedUnits());
    }

    public Player self() {
        SWIG_Player player = game.self();
        return player == null ? null : new Player(game.self());
    }

    public Player enemy() {
        SWIG_Player player = game.enemy();
        return player == null ? null : new Player(game.enemy());
    }

    public void drawText(CoordinateType ctype, int x, int y, String text) {
        game.drawText(ctype.getBwapiOrdinal(), x, y, text);
    }

    public void drawTextScreen(int x, int y, String text) {
        game.drawTextScreen(x, y, text);
    }

    public void drawTextMap(int x, int y, String text) {
        game.drawTextMap(x, y, text);
    }

    public void drawTextMouse(int x, int y, String text) {
        game.drawTextMouse(x, y, text);
    }
    
    public void drawBox(CoordinateType ctype, int left, int top, int right,
            int bottom, Color color, boolean isSolid) {
        game.drawBox(ctype.getBwapiOrdinal(), left, top, right, bottom, 
                color.getOriginalObject(), isSolid);
    }

    public void drawBox(CoordinateType ctype, int left, int top, int right,
            int bottom, Color color) {
        game.drawBox(ctype.getBwapiOrdinal(), left, top, right, 
                bottom, color.getOriginalObject());
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawBoxScreen(int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawScreenBox(int left, int top, int right, int bottom,
            Color color, boolean isSolid) {
        drawBoxScreen(left, top, right, bottom, color, isSolid);
    }
    
    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawBoxScreen(int left, int top, int right, int bottom,
            Color color, boolean isSolid) {
        game.drawBoxScreen(left, top, right, bottom, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawBoxScreen(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawScreenBox(int left, int top, int right, int bottom,
            Color color) {
        drawBoxScreen(left, top, right, bottom, color);
    }
    
    public void drawBoxScreen(int left, int top, int right, int bottom,
            Color color) {
        game.drawBoxScreen(left, top, right, bottom, color.getOriginalObject());
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawBoxMap(int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMapBox(int left, int top, int right, int bottom,
            Color color, boolean isSolid) {
        drawBoxMap(left, top, right, bottom, color, isSolid);
    }
    
    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawBoxMap(int left, int top, int right, int bottom,
            Color color, boolean isSolid) {
        game.drawBoxMap(left, top, right, bottom, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawMapBox(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMapBox(int left, int top, int right, int bottom, Color color) {
        drawBoxMap(left, top, right, bottom, color);
    }
    
    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * 
     * @since 0.2
     */
    public void drawBoxMap(int left, int top, int right, int bottom, Color color) {
        game.drawBoxMap(left, top, right, bottom, color.getOriginalObject());
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawBoxMouse(int, int, int, int, Color, boolean)} 
     */
    @Deprecated
    public void drawMouseBox(int left, int top, int right, int bottom,
            Color color, boolean isSolid) {
        drawBoxMouse(left, top, right, bottom, color, isSolid);
    }
    
    public void drawBoxMouse(int left, int top, int right, int bottom,
            Color color, boolean isSolid) {
        game.drawBoxMouse(left, top, right, bottom, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawBoxMouse(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMouseBox(int left, int top, int right, int bottom,
            Color color) {
        drawBoxMouse(left, top, right, bottom, color);
    }

    /**
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     * 
     * @since 0.2
     */
    public void drawBoxMouse(int left, int top, int right, int bottom,
            Color color) {
        game.drawBoxMouse(left, top, right, bottom, color.getOriginalObject());
    }

    public void drawTriangle(CoordinateType ctype, int ax, int ay, int bx,
            int by, int cx, int cy, Color color, boolean isSolid) {
        game.drawTriangle(ctype.getBwapiOrdinal(), ax, ay, bx, by, cx, 
                cy, color.getOriginalObject(), isSolid);
    }
    
    public void drawTriangle(CoordinateType ctype, int ax, int ay, int bx,
            int by, int cx, int cy, Color color) {
        game.drawTriangle(ctype.getBwapiOrdinal(), ax, ay, bx, by, cx, 
                cy, color.getOriginalObject());
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawTriangleScreen(int, int, int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawScreenTriangle(int ax, int ay, int bx, int by, int cx,
            int cy, Color color, boolean isSolid) {
        drawTriangleScreen(ax, ay, bx, by, cx, cy, color, isSolid);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawTriangleScreen(int ax, int ay, int bx, int by, int cx,
            int cy, Color color, boolean isSolid) {
        game.drawTriangleScreen(ax, ay, bx, by, cx, cy, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * 
     * @deprecated Change in BWAPI 2.3, use {@link #drawTriangleScreen(int, int, int, int, int, int, Color)}
     */
    @Deprecated
    public void drawScreenTriangle(int ax, int ay, int bx, int by, int cx,
            int cy, Color color) {
        drawTriangleScreen(ax, ay, bx, by, cx, cy, color);
    }
    
    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * 
     * @since 0.2
     */
    public void drawTriangleScreen(int ax, int ay, int bx, int by, int cx,
            int cy, Color color) {
        game.drawTriangleScreen(ax, ay, bx, by, cx, cy, color.getOriginalObject());
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawTriangleMap(int, int, int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMapTriangle(int ax, int ay, int bx, int by, int cx, int cy,
            Color color, boolean isSolid) {
        drawTriangleMap(ax, ay, bx, by, cx, cy, color, isSolid);
    }
    
    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawTriangleMap(int ax, int ay, int bx, int by, int cx, int cy,
            Color color, boolean isSolid) {
        game.drawTriangleMap(ax, ay, bx, by, cx, cy, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawTriangleMap(int, int, int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMapTriangle(int ax, int ay, int bx, int by, int cx, int cy,
            Color color) {
        drawTriangleMap(ax, ay, bx, by, cx, cy, color);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * 
     * @since 0.2
     */
    public void drawTriangleMap(int ax, int ay, int bx, int by, int cx, int cy,
            Color color) {
        game.drawTriangleMap(ax, ay, bx, by, cx, cy, color.getOriginalObject());
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawTriangleMouse(int, int, int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMouseTriangle(int ax, int ay, int bx, int by, int cx,
            int cy, Color color, boolean isSolid) {
        drawTriangleMouse(ax, ay, bx, by, cx, cy, color, isSolid);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawTriangleMouse(int ax, int ay, int bx, int by, int cx,
            int cy, Color color, boolean isSolid) {
        game.drawTriangleMouse(ax, ay, bx, by, cx, cy, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawTriangleMap(int, int, int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMouseTriangle(int ax, int ay, int bx, int by, int cx,
            int cy, Color color) {
        drawTriangleMouse(ax, ay, bx, by, cx, cy, color);
    }

    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param cx
     * @param cy
     * @param color
     * 
     * @since 0.2
     */
    public void drawTriangleMouse(int ax, int ay, int bx, int by, int cx,
            int cy, Color color) {
        game.drawTriangleMouse(ax, ay, bx, by, cx, cy, color.getOriginalObject());
    }

    public void drawCircle(CoordinateType ctype, int x, int y, int radius,
            Color color, boolean isSolid) {
        game.drawCircle(ctype.getBwapiOrdinal(), x, y, radius, 
                color.getOriginalObject(), isSolid);
    }

    public void drawCircle(CoordinateType ctype, int x, int y, int radius,
            Color color) {
        game.drawCircle(ctype.getBwapiOrdinal(), x, y, radius, 
                color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawCircleScreen(int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawScreenCircle(int x, int y, int radius, Color color,
            boolean isSolid) {
        drawCircleScreen(x, y, radius, color, isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawCircleScreen(int x, int y, int radius, Color color,
            boolean isSolid) {
        game.drawCircleScreen(x, y, radius, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawCircleScreen(int, int, int, Color)}
     */
    @Deprecated
    public void drawScreenCircle(int x, int y, int radius, Color color) {
        drawCircleScreen(x, y, radius, color);
    }

    public void drawCircleScreen(int x, int y, int radius, Color color) {
        game.drawCircleScreen(x, y, radius, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawCircleMap(int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMapCircle(int x, int y, int radius, Color color,
            boolean isSolid) {
        drawCircleMap(x, y, radius, color, isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawCircleMap(int x, int y, int radius, Color color,
            boolean isSolid) {
        game.drawCircleMap(x, y, radius, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawCircleMap(int, int, int, Color)}
     */
    @Deprecated
    public void drawMapCircle(int x, int y, int radius, Color color) {
        drawCircleMap(x, y, radius, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * 
     * @since 0.2
     */
    public void drawCircleMap(int x, int y, int radius, Color color) {
        game.drawCircleMap(x, y, radius, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawCircleMouse(int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMouseCircle(int x, int y, int radius, Color color,
            boolean isSolid) {
        drawCircleMouse(x, y, radius, color, isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawCircleMouse(int x, int y, int radius, Color color,
            boolean isSolid) {
        game.drawCircleMouse(x, y, radius, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawCircleMap(int, int, int, Color)}
     */
    @Deprecated
    public void drawMouseCircle(int x, int y, int radius, Color color) {
        drawCircleMouse(x, y, radius, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param radius
     * @param color
     * 
     * @since 0.2
     */
    public void drawCircleMouse(int x, int y, int radius, Color color) {
        game.drawCircleMouse(x, y, radius, color.getOriginalObject());
    }

    public void drawEllipse(CoordinateType ctype, int x, int y, int xrad,
            int yrad, Color color, boolean isSolid) {
        game.drawEllipse(ctype.getBwapiOrdinal(), x, y, xrad, 
                yrad, color.getOriginalObject(), isSolid);
    }

    public void drawEllipse(CoordinateType ctype, int x, int y, int xrad,
            int yrad, Color color) {
        game.drawEllipse(ctype.getBwapiOrdinal(), x, y, xrad, 
                yrad, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawEllipseScreen(int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawScreenEllipse(int x, int y, int xrad, int yrad,
            Color color, boolean isSolid) {
        drawEllipseScreen(x, y, xrad, yrad, color, isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawEllipseScreen(int x, int y, int xrad, int yrad,
            Color color, boolean isSolid) {
        game.drawEllipseScreen(x, y, xrad, yrad, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawEllipseScreen(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawScreenEllipse(int x, int y, int xrad, int yrad, Color color) {
        drawEllipseScreen(x, y, xrad, yrad, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * 
     * @since 0.2
     */
    public void drawEllipseScreen(int x, int y, int xrad, int yrad, Color color) {
        game.drawEllipseScreen(x, y, xrad, yrad, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawEllipseMap(int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMapEllipse(int x, int y, int xrad, int yrad, Color color,
            boolean isSolid) {
        drawEllipseMap(x, y, xrad, yrad, color, isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawEllipseMap(int x, int y, int xrad, int yrad, Color color,
            boolean isSolid) {
        game.drawEllipseMap(x, y, xrad, yrad, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawEllipseMap(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMapEllipse(int x, int y, int xrad, int yrad, Color color) {
        drawEllipseMap(x, y, xrad, yrad, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * 
     * @since 0.2
     */
    public void drawEllipseMap(int x, int y, int xrad, int yrad, Color color) {
        game.drawEllipseMap(x, y, xrad, yrad, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * @param isSolid
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawEllipseMouse(int, int, int, int, Color, boolean)}
     */
    @Deprecated
    public void drawMouseEllipse(int x, int y, int xrad, int yrad, Color color,
            boolean isSolid) {
        drawEllipseMouse(x, y, xrad, yrad, color, isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * @param isSolid
     * 
     * @since 0.2
     */
    public void drawEllipseMouse(int x, int y, int xrad, int yrad, Color color,
            boolean isSolid) {
        game.drawEllipseMouse(x, y, xrad, yrad, color.getOriginalObject(), isSolid);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawEllipseMouse(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMouseEllipse(int x, int y, int xrad, int yrad, Color color) {
        drawEllipseMouse(x, y, xrad, yrad, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param xrad
     * @param yrad
     * @param color
     * 
     * @since 0.2
     */
    public void drawEllipseMouse(int x, int y, int xrad, int yrad, Color color) {
        game.drawEllipseMouse(x, y, xrad, yrad, color.getOriginalObject());
    }

    public void drawDot(CoordinateType ctype, int x, int y, Color color) {
        game.drawDot(ctype.getBwapiOrdinal(), x, y, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawDotScreen(int, int, Color)}
     */
    @Deprecated
    public void drawScreenDot(int x, int y, Color color) {
        drawDotScreen(x, y, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param color
     * 
     * @since 0.2
     */
    public void drawDotScreen(int x, int y, Color color) {
        game.drawDotScreen(x, y, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawDotMap(int, int, Color)}
     */
    @Deprecated
    public void drawMapDot(int x, int y, Color color) {
        drawMapDot(x, y, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param color
     * 
     * @since 0.2
     */
    public void drawDotMap(int x, int y, Color color) {
        game.drawDotMap(x, y, color.getOriginalObject());
    }

    /**
     * 
     * @param x
     * @param y
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawDotMouse(int, int, Color)}
     */
    @Deprecated
    public void drawMouseDot(int x, int y, Color color) {
        drawDotMouse(x, y, color);
    }

    /**
     * 
     * @param x
     * @param y
     * @param color
     * 
     * @since 0.2
     */
    public void drawDotMouse(int x, int y, Color color) {
        game.drawDotMouse(x, y, color.getOriginalObject());
    }

    public void drawLine(CoordinateType ctype, int x1, int y1, int x2, int y2,
            Color color) {
        game.drawLine(ctype.getBwapiOrdinal(), x1, y1, x2, y2, color.getOriginalObject());
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawLineScreen(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawScreenLine(int x1, int y1, int x2, int y2, Color color) {
        drawLineScreen(x1, y1, x2, y2, color);
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * 
     * @since 0.2
     */
    public void drawLineScreen(int x1, int y1, int x2, int y2, Color color) {
        game.drawLineScreen(x1, y1, x2, y2, color.getOriginalObject());
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawLineMap(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMapLine(int x1, int y1, int x2, int y2, Color color) {
        drawLineMap(x1, y1, x2, y2, color);
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * 
     * @since 0.2
     */
    public void drawLineMap(int x1, int y1, int x2, int y2, Color color) {
        game.drawLineMap(x1, y1, x2, y2, color.getOriginalObject());
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * 
     * @deprecated Changed in BWAPI 2.3, use {@link #drawLineMouse(int, int, int, int, Color)}
     */
    @Deprecated
    public void drawMouseLine(int x1, int y1, int x2, int y2, Color color) {
        drawLineMouse(x1, y1, x2, y2, color);
    }

    /**
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * 
     * @since 0.2
     */
    public void drawLineMouse(int x1, int y1, int x2, int y2, Color color) {
        game.drawLineMouse(x1, y1, x2, y2, color.getOriginalObject());
    }
}
