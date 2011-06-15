package org.cretz.swig.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Common utilities for use with SWIG
 * 
 * @author Chad Retz
 */
public final class SwigUtils {

    public static long getSwigPointer(Object object) {
        try {
            Field field = object.getClass().getDeclaredField("swigCPtr");
            field.setAccessible(true);
            return field.getLong(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static boolean isSwigMemoryOwned(Object object) {
        try {
            Field field = object.getClass().getDeclaredField("swigCMemOwn");
            field.setAccessible(true);
            return field.getBoolean(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T getSwigPointer(Object object, Class<T> pointer) {
        return getSwigPointer(getSwigPointer(object), isSwigMemoryOwned(object), pointer);
    }
    
    public static <T> T getSwigPointer(long swigPtr, boolean swigOwned, Class<T> pointer) {
        try {
            Constructor<T> constructor = pointer.getDeclaredConstructor(
                    Long.TYPE, Boolean.TYPE);
            constructor.setAccessible(true);
            return constructor.newInstance(swigPtr, swigOwned);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private SwigUtils() {
    }
}
