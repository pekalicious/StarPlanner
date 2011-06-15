package org.bwapi.bridge.sal;

import java.util.Map.Entry;

/**
 * Simple pair class (ref std::pair)
 * 
 * @author Chad Retz
 *
 * @param <T>
 * @param <U>
 * 
 * @since 0.3
 */
class Pair<T, U> implements Entry<T, U> {
    
    protected final T one;
    protected final U two;
    
    public Pair(T one, U two) {
        this.one = one;
        this.two = two;
    }

    public T getOne() {
        return one;
    }

    public U getTwo() {
        return two;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((one == null) ? 0 : one.hashCode());
        result = prime * result + ((two == null) ? 0 : two.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pair other = (Pair) obj;
        if (one == null) {
            if (other.one != null) {
                return false;
            }
        } else if (!one.equals(other.one)) {
            return false;
        }
        if (two == null) {
            if (other.two != null) {
                return false;
            }
        } else if (!two.equals(other.two)) {
            return false;
        }
        return true;
    }

    @Override
    public T getKey() {
        return one;
    }

    @Override
    public U getValue() {
        return two;
    }

    @Override
    public U setValue(U value) {
        throw new UnsupportedOperationException();
    }
}
