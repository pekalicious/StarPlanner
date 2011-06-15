package org.bwapi.unit.model;

/**
 * Representation of a button in Broodwar
 * 
 * @author Chad Retz
 */
public enum BroodwarButton {

    SINGLE_PLAYER(210, 130, -16776192),
    EXPANSION_PACK(360, 320, -16243704),
    ID_OK(515, 425, -16777216),
    PLAY_CUSTOM(350, 445, -16777216);

    private final int x;
    private final int y;
    private final int rgb;

    private BroodwarButton(int x, int y, int rgb) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRgb() {
        return rgb;
    }

}
