package org.bwapi.bridge.model;

import org.bwapi.bridge.swig.SWIG_Color;
import org.bwapi.bridge.swig.bridge;

/**
 * Color
 * 
 * @see <a href="http://code.google.com/p/bwapi/wiki/Color">Color</a>
 * 
 * @author Chad Retz
 */
public final class Color extends BwapiComparable<Color> {

    public static final Color RED = new Color(bridge.getRed());
    public static final Color GREEN = new Color(bridge.getGreen());
    public static final Color BLUE = new Color(bridge.getBlue());
    public static final Color YELLOW = new Color(bridge.getYellow());
    public static final Color CYAN = new Color(bridge.getCyan());
    public static final Color PURPLE = new Color(bridge.getPurple());
    public static final Color ORANGE = new Color(bridge.getOrange());
    public static final Color BLACK = new Color(bridge.getBlack());
    public static final Color WHITE = new Color(bridge.getWhite());
    public static final Color GREY = new Color(bridge.getGrey());
    
    private final SWIG_Color color;
    
    Color(SWIG_Color color) {
        this.color = color;
    }
    
    public Color() {
        this(new SWIG_Color());
    }

    public Color(int id) {
        this(new SWIG_Color(id));
    }

    public Color(Color other) {
        this(new SWIG_Color(other.getOriginalObject()));
    }
    
    @Override
    SWIG_Color getOriginalObject() {
        return color;
    }

    public Color(int red, int green, int blue) {
        this(new SWIG_Color(red, green, blue));
    }

    @Override
    public Color opAssign(Color other) {
        return new Color(color.opAssign(other.getOriginalObject()));
    }

    @Override
    public boolean opEquals(Color other) {
        return color.opEquals(other.getOriginalObject());
    }

    @Override
    public boolean opNotEquals(Color other) {
        return color.opNotEquals(other.getOriginalObject());
    }

    @Override
    public boolean opLessThan(Color other) {
        return color.opLessThan(other.getOriginalObject());
    }

    @Override
    public int getID() {
        return color.getID();
    }

    public int red() {
        return color.red();
    }

    public int green() {
        return color.green();
    }

    public int blue() {
        return color.blue();
    }
}
