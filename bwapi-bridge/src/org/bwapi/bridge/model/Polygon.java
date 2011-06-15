package org.bwapi.bridge.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.bwapi.bridge.swig.PolygonSet;
import org.bwapi.bridge.swig.PolygonSetIterator;
import org.bwapi.bridge.swig.PolygonSpacelessSet;
import org.bwapi.bridge.swig.PolygonSpacelessSetIterator;
import org.bwapi.bridge.swig.SWIGTYPE_p_Polygon;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_BWTA__Polygon_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__setT_Polygon_p_t;
import org.bwapi.bridge.swig.SWIGTYPE_p_std__vectorT_BWAPI__Position_t;
import org.bwapi.bridge.swig.SWIG_Polygon;
import org.cretz.swig.collection.NativeSet;

/**
 * Polygon
 * 
 * @see <a href="http://code.google.com/p/bwta/wiki/Polygon">Polygon</a>
 * 
 * @author Chad Retz
 *
 * @since 0.2
 */
public final class Polygon extends BwapiObject implements List<Position> {
    
    private static final Transformer<Polygon, SWIG_Polygon> FROM = 
        new Transformer<Polygon, SWIG_Polygon>() {
            @Override
            public SWIG_Polygon transform(Polygon polygon) {
                return polygon.getOriginalObject();
            }
        };
        
    private static final Transformer<SWIG_Polygon, Polygon> TO = 
        new Transformer<SWIG_Polygon, Polygon>() {
            @Override
            public Polygon transform(SWIG_Polygon polygon) {
                return new Polygon(polygon);
            }
        };

    private static final Transformer<Polygon, SWIGTYPE_p_Polygon> FROM_SPACELESS = 
        new Transformer<Polygon, SWIGTYPE_p_Polygon>() {
            @Override
            public SWIGTYPE_p_Polygon transform(Polygon polygon) {
                return polygon.getPointer();
            }
        };
        
    private static final Transformer<SWIGTYPE_p_Polygon, Polygon> TO_SPACELESS = 
        new Transformer<SWIGTYPE_p_Polygon, Polygon>() {
            @Override
            public Polygon transform(SWIGTYPE_p_Polygon polygon) {
                return new Polygon(polygon);
            }
        };

        
    static Set<Polygon> newSet(SWIGTYPE_p_std__setT_BWTA__Polygon_p_t nativeSet) {
        return new NativeSet<Polygon, SWIG_Polygon>(
                SWIG_Polygon.class, PolygonSetIterator.class, 
                nativeSet, PolygonSet.class, FROM, TO);
    }
    
    static Set<Polygon> newSet(SWIGTYPE_p_std__setT_Polygon_p_t nativeSet) {
        return new NativeSet<Polygon, SWIGTYPE_p_Polygon>(
                SWIGTYPE_p_Polygon.class, PolygonSpacelessSetIterator.class, 
                nativeSet, PolygonSpacelessSet.class, FROM_SPACELESS, TO_SPACELESS);
    }

    private final SWIG_Polygon polygon;
    private final List<Position> list;

    Polygon(SWIG_Polygon polygon) {
        this.polygon = polygon;
        list = Position.newList(new SWIGTYPE_p_std__vectorT_BWAPI__Position_t(
                polygon.getCPtr(), false));
    }
    
    Polygon(SWIGTYPE_p_Polygon polygon) {
        this(new SWIG_Polygon(polygon.getCPtr(), false));
    }

    public Polygon() {
        this(new SWIG_Polygon());
    }
    
    @Override
    SWIG_Polygon getOriginalObject() {
        return polygon;
    }
    
    SWIGTYPE_p_Polygon getPointer() {
        return new SWIGTYPE_p_Polygon(polygon.getCPtr(), false);
    }

    public double getArea() {
        return polygon.getArea();
    }

    public double getPerimeter() {
        return polygon.getPerimeter();
    }

    public Position getCenter() {
        return new Position(polygon.getCenter());
    }

    public boolean isInside(Position p) {
        return polygon.isInside(p.getOriginalObject());
    }

    public Position getNearestPoint(Position p) {
        return new Position(polygon.getNearestPoint(p.getOriginalObject()));
    }

    @Override
    public boolean add(Position e) {
        return list.add(e);
    }

    @Override
    public void add(int index, Position element) {
        list.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends Position> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Position> c) {
        return list.addAll(index, c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public Position get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<Position> iterator() {
        return list.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Position> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Position> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public Position remove(int index) {
        return list.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public Position set(int index, Position element) {
        return list.set(index, element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<Position> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
}
