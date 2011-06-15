package org.bwapi.bridge.sal;

import java.util.Set;

/**
 * Controller
 * 
 * @see <a href="http://code.google.com/p/bwsal/wiki/Controller">Controller</a>
 * 
 * @author Chad Retz
 *
 * @param <T>
 * @param <U>
 * 
 * @since 0.3
 */
public interface Controller<T, U extends Comparable<U>> {
    void onOffer(Set<T> objects);
    void onRevoke(T object, U bid);
    String getName();
    void update();
}
