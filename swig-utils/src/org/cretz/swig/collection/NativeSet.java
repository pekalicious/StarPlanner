package org.cretz.swig.collection;

import java.lang.reflect.Method;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

/**
 * Wrapper for std::set and std::list from SWIG. This does
 * not support removal during iteration.
 * 
 * @author Chad Retz
 * 
 * @param <T>
 * @param <U>
 */
public class NativeSet<T, U> extends AbstractSet<T> implements Set<T> {
    
    private final Transformer<T, U> from;
    private final Transformer<U, T> to;
    private final Class<?> nativeClass;
    private final Class<?> setIteratorClass;
    private final Object nativeSet;
    private final Object setWrapper;
    private final Method sizeMethod;
    private final Method containsMethod;
    private final Method addMethod;
    private final Method clearMethod;
    private final Method removeMethod;
    private final Method hasNextMethod;
    private final Method nextMethod;
    
    /**
     * Instantiate the native set
     * 
     * @param nativeClass The native class
     * @param setIteratorClass The class for the SetIterator
     * @param nativeSet The native set object
     * @param setWrapperClass The class for the Set
     * @param from
     * @param to
     */
    public NativeSet(Class<U> nativeClass, 
            Class<?> setIteratorClass, Object nativeSet,
            Class<?> setWrapperClass, Transformer<T, U> from,
            Transformer<U, T> to) {
        this.nativeClass = nativeClass;
        this.setIteratorClass = setIteratorClass;
        this.nativeSet = nativeSet;
        this.from = from;
        this.to = to;
        try {
            setWrapper = setWrapperClass.getConstructor(nativeSet.getClass()).
                    newInstance(nativeSet);
            sizeMethod = setWrapperClass.getDeclaredMethod("size");
            containsMethod = setWrapperClass.getDeclaredMethod("contains", 
                    nativeClass);
            addMethod = setWrapperClass.getDeclaredMethod("add", 
                    nativeClass);
            clearMethod = setWrapperClass.getDeclaredMethod("clear");
            removeMethod = setWrapperClass.getDeclaredMethod("remove", 
                    nativeClass);
            hasNextMethod = setIteratorClass.getDeclaredMethod("hasNext");
            nextMethod = setIteratorClass.getDeclaredMethod("next");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean add(T item) {
        try {
            return (Boolean) addMethod.invoke(setWrapper, from.transform(item));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            clearMethod.invoke(setWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object item) {
        try {
            Object transformed = from.transform((T) item);
            return nativeClass.isAssignableFrom(transformed.getClass()) &&
                    (Boolean) containsMethod.invoke(setWrapper, transformed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new NativeSetIterator();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object item) {
        try {
            return (Boolean) removeMethod.invoke(setWrapper, from.transform((T) item));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object item : collection) {
            modified |= remove(item);
        }
        return modified;
    }
    
    @Override
    public boolean retainAll(Collection<?> collection) {
        //best way?
        List<T> toRemove = new ArrayList<T>(this.size());
        for (T item : this) {
            if (!collection.contains(item)) {
                toRemove.add(item);
            }
        }
        return removeAll(toRemove);
    }

    @Override
    public int size() {
        try {
            return (Integer) sizeMethod.invoke(setWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set iterator
     * 
     * @author Chad Retz
     */
    protected class NativeSetIterator implements Iterator<T> {

        private final Object setIterator;
        
        protected NativeSetIterator() {
            try {
                setIterator = setIteratorClass.getConstructor(
                        nativeSet.getClass()).newInstance(nativeSet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        @Override
        public boolean hasNext() {
            try {
                return (Boolean) hasNextMethod.invoke(setIterator);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            try {
                return (T) to.transform((U) nextMethod.invoke(setIterator));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * {@inheritDoc}
         * <p>
         * Unsupported
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
}
