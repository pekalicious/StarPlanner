package org.bwapi.bridge.model;

/**
 * BWAPI common comparable object. Hash code is
 * based on {@link #getID()} and equality is 
 * based on {@link #opEquals(Object)}
 * 
 * @author Chad Retz
 *
 * @param <T>
 */
public abstract class BwapiComparable<T> extends BwapiIdentifiable implements Comparable<T> {
    
    public abstract T opAssign(T other);

    public abstract boolean opEquals(T other);

    public abstract boolean opNotEquals(T other);

    public abstract boolean opLessThan(T other);

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        return obj != null && getClass().isAssignableFrom(
                obj.getClass()) && opEquals((T) obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    public int compareTo(T o) {
        if (opEquals(o)) {
            return 0;
        } else if (opLessThan(o)) {
            return -1;
        } else {
            return 1;
        }
    }
}
