package org.cretz.swig.collection;

import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

/**
 * Wrapper for std::vector from SWIG
 * 
 * @author Chad Retz
 *
 * @param <T>
 * @param <U>
 */
public class NativeList<T, U> extends AbstractList<T> implements List<T> {

    private final Transformer<T, U> from;
    private final Transformer<U, T> to;
    private final Object listWrapper;
    private final Method sizeMethod;
    private final Method addMethod;
    private final Method clearMethod;
    private final Method setMethod;
    private final Method removeMethod;
    private final Method getMethod;
    
    /**
     * Construct native list from std::vector wrappers
     * 
     * @param nativeClass The native class
     * @param nativeList The SWIG vector
     * @param listWrapperClass The SWIG vector class wrapper
     */
    public NativeList(Class<U> nativeClass, Object nativeList, Class<?> listWrapperClass,
            Transformer<T, U> from, Transformer<U, T> to) {
        this.from = from;
        this.to = to;
        try {
            listWrapper = listWrapperClass.getConstructor(nativeList.getClass()).
                    newInstance(nativeList);
            sizeMethod = listWrapperClass.getDeclaredMethod("size");
            addMethod = listWrapperClass.getDeclaredMethod("add", 
                    Integer.TYPE, nativeClass);
            clearMethod = listWrapperClass.getDeclaredMethod("clear");
            setMethod = listWrapperClass.getDeclaredMethod("set", 
                    Integer.TYPE, nativeClass);
            removeMethod = listWrapperClass.getDeclaredMethod("remove", Integer.TYPE);
            getMethod = listWrapperClass.getDeclaredMethod("get", Integer.TYPE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void add(int index, T item) {
        try {
            addMethod.invoke(listWrapper, index, from.transform(item));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void clear() {
        try {
            clearMethod.invoke(listWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T item) {
        try {
            return to.transform((U) setMethod.invoke(listWrapper, 
                    index, from.transform(item)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        try {
            return to.transform((U) removeMethod.invoke(listWrapper, index));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object item) {
        try {
            return (Boolean) removeMethod.invoke(listWrapper, 
                    from.transform((T) item));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        try {
            return to.transform((U) getMethod.invoke(listWrapper, index));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        try {
            return (Integer) sizeMethod.invoke(listWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
