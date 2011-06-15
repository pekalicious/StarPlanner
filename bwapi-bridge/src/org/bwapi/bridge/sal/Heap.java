package org.bwapi.bridge.sal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Representation of BWSAL's Heap class
 * 
 * @author Chad Retz
 *
 * @param <T>
 * @param <U>
 * 
 * @since 0.3
 */
class Heap<T, U extends Comparable<U>> {

    protected final List<Entry<T, U>> data = new ArrayList<Entry<T, U>>();
    protected final Map<T, Integer> mapping = new HashMap<T, Integer>();
    
    protected int percolateUp(int index) {
        if (index < 0 || index  >= data.size()) {
            return -1;
        }
        int parent = Math.abs((index - 1) / 2);
        while (index > 0 && data.get(parent).getValue().compareTo(
                data.get(index).getValue()) < 0) {
            Entry<T, U> temp = data.get(parent);
            data.set(parent, data.get(index));
            data.set(index, temp);
            mapping.put(temp.getKey(), index);
            index = parent;
        }
        mapping.put(data.get(index).getKey(), index);
        return index;
    }
    
    protected int percolateDown(int index) {
        if (index < 0 || index  >= data.size()) {
            return -1;
        }
        int lchild = index * 2 + 1;
        int rchild = index * 2 + 2;
        int mchild;
        while ((data.size() > lchild && data.get(index).getValue().compareTo(
                data.get(lchild).getValue()) < 0) || (data.size() > rchild &&
                data.get(index).getValue().compareTo(data.get(rchild).getValue()) < 0)) {
            mchild = lchild;
            if (data.size() > rchild && data.get(rchild).getValue().
                    compareTo(data.get(lchild).getValue()) > 0) {
                mchild = rchild;
            }
            Entry<T, U> temp = data.get(mchild);
            data.set(mchild, data.get(index));
            data.set(index, temp);
            mapping.put(temp.getKey(), index);
            index = mchild;
            lchild = index * 2 + 1;
            rchild = index * 2 + 2;
        }
        mapping.put(data.get(index).getKey(), index);
        return index;
    }
    
    public void push(Entry<T, U> entry) {
        int index = data.size();
        mapping.put(entry.getKey(), index);
        data.add(entry);
        percolateUp(index);
    }
    
    public void pop() {
        if (data.isEmpty()) {
            return;
        }
        mapping.remove(data.get(0).getKey());
        data.set(0, data.get(data.size() - 1));
        data.remove(data.size() - 1);
        if (data.isEmpty()) {
            return;
        }
        if (mapping.containsKey(data.get(0).getKey())) {
            mapping.put(data.get(0).getKey(), 0);
            percolateDown(0);
        }
    }
    
    public Entry<T, U> top() {
        return data.get(0);
    }
    
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    public boolean set(T object, U val) {
        Integer index = mapping.get(object); 
        if (index == null) {
            push(new Pair<T, U>(object, val));
            return true;
        }
        data.set(index, new Pair<T, U>(object, val));
        index = percolateDown(percolateUp(index));
        return index >= 0 && index < data.size();
    }
    
    public U get(T object) {
        Integer index = mapping.get(object);
        return index == null ? null : data.get(index).getValue();
    }
    
    public boolean contains(T object) {
        return mapping.containsKey(object);
    }
    
    public int size() {
        return data.size();
    }
    
    public void clear() {
        data.clear();
        mapping.clear();
    }
    
    public boolean erase(T object) {
        Integer index = mapping.get(object);
        if (index == null) {
            return false;
        } else if (data.size() == 1) {
            clear();
        } else {
            data.set(index, data.get(data.size() - 1));
            data.remove(data.size() - 1);
            mapping.remove(object);
            percolateDown(index);
        }
        return true;
    }
    
    public List<Entry<T, U>> getData() {
        return data;
    }
}
