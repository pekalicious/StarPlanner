package org.cretz.swig.collection;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

/**
 * Wrapper for std::map from SWIG. This is basically
 * the same as std::set and impl w/ {@link org.cretz.swig.collection.NativeSet}
 * 
 * @author Chad Retz
 *
 * @param <K>
 * @param <V>
 * @param <KT>
 * @param <VT>
 */
public class NativeMap<K, V, KT, VT> extends AbstractMap<K, V> implements Map<K, V> {

    private final Transformer<K, KT> fromKey;
    private final Transformer<KT, K> toKey;
    private final Transformer<V, VT> fromValue;
    private final Transformer<VT, V> toValue;
    private final Class<?> mapIteratorClass;
    private final Object nativeMap;
    private final Object mapWrapper;
    private final Method sizeMethod;
    private final Method addMethod;
    private final Method clearMethod;
    private final Method removeMethod;
    private final NativeMapSet nativeMapSet; 
    private final Method hasNextMethod;
    private final Method nextMethod;
    private final Method keyMethod;
    private final Method valueMethod;
    
    /**
     * Construct native map from std::map wrappers
     * 
     * @param nativeKeyClass
     * @param nativeValueClass
     * @param mapIteratorClass
     * @param nativeMap
     * @param mapWrapperClass
     * @param fromKey
     * @param toKey
     * @param fromValue
     * @param toValue
     */
    public NativeMap(Class<KT> nativeKeyClass, Class<VT> nativeValueClass, 
            Class<?> mapIteratorClass, Object nativeMap,
            Class<?> mapWrapperClass, Transformer<K, KT> fromKey,
            Transformer<KT, K> toKey, Transformer<V, VT> fromValue,
            Transformer<VT, V> toValue) {
        this.mapIteratorClass = mapIteratorClass;
        this.nativeMap = nativeMap;
        this.fromKey = fromKey;
        this.toKey = toKey;
        this.fromValue = fromValue;
        this.toValue = toValue;
        try {
            mapWrapper = mapWrapperClass.getConstructor(nativeMap.getClass()).
                    newInstance(nativeMap);
            sizeMethod = mapWrapperClass.getDeclaredMethod("size");
            addMethod = mapWrapperClass.getDeclaredMethod("add", 
                    nativeKeyClass, nativeValueClass);
            clearMethod = mapWrapperClass.getDeclaredMethod("clear");
            removeMethod = mapWrapperClass.getDeclaredMethod("remove", 
                    nativeKeyClass);
            hasNextMethod = mapIteratorClass.getDeclaredMethod("hasNext");
            nextMethod = mapIteratorClass.getDeclaredMethod("next");
            keyMethod = mapIteratorClass.getDeclaredMethod("getKey");
            valueMethod = mapIteratorClass.getDeclaredMethod("getValue");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        nativeMapSet = new NativeMapSet();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return nativeMapSet;
    }
    
    @Override
    public V put(K key, V value) {
        V old = get(key);
        entrySet().add(new NativeEntry(key, value));
        return old;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        V old = get(key);
        if (old != null) {
            entrySet().remove(new NativeEntry((K) key, old));
        }
        return old;
    }

    @Override
    public int size() {
        return entrySet().size();
    }
    
    /**
     * Wrapper for a native entry
     * 
     * @author Chad Retz
     */
    protected class NativeEntry implements Entry<K, V> {
        private final K key;
        private V value;
        
        protected NativeEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public K getKey() {
            return key;
        }
        
        @Override
        public V getValue() {
            return value;
        }
        
        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            put(key, value);
            return old;
        }
    }
    
    /**
     * Wrapper for native entry set
     * 
     * @author Chad Retz
     */
    protected class NativeMapSet extends AbstractSet<Entry<K, V>> 
            implements Set<Entry<K, V>> {
        
        @Override
        public boolean add(Entry<K, V> item) {
            try {
                return (Boolean) addMethod.invoke(mapWrapper, 
                        fromKey.transform(item.getKey()), 
                        fromValue.transform(item.getValue()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void clear() {
            try {
                clearMethod.invoke(mapWrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new NativeMapSetIterator();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object item) {
            try {
                if (item instanceof Entry<?, ?>) {
                    return (Boolean) removeMethod.invoke(mapWrapper, 
                            fromKey.transform((K) ((Entry<?, ?>) item).getKey()));
                }
                return false;
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
            List<Entry<K, V>> toRemove = new ArrayList<Entry<K, V>>(this.size());
            for (Entry<K, V> item : this) {
                if (!collection.contains(item)) {
                    toRemove.add(item);
                }
            }
            return removeAll(toRemove);
        }

        @Override
        public int size() {
            try {
                return (Integer) sizeMethod.invoke(mapWrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Map entry set iterator
     * 
     * @author Chad Retz
     */
    protected class NativeMapSetIterator implements Iterator<Entry<K, V>> {

        private final Object setIterator;
        
        protected NativeMapSetIterator() {
            try {
                setIterator = mapIteratorClass.getConstructor(
                        nativeMap.getClass()).newInstance(nativeMap);
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
        public NativeEntry next() {
            try {
                nextMethod.invoke(setIterator);
                return new NativeEntry(toKey.transform((KT) keyMethod.invoke(setIterator)), 
                        toValue.transform((VT) valueMethod.invoke(setIterator)));
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
