/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.40
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.bwapi.bridge.swig;
import org.bwapi.bridge.model.BwapiPointable;
public class ForceSet implements BwapiPointable {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public ForceSet(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(ForceSet obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public long getCPtr() {
    return swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        bridgeJNI.delete_ForceSet(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ForceSet(SWIGTYPE_p_std__setT_BWAPI__Force_p_t original) {
    this(bridgeJNI.new_ForceSet(SWIGTYPE_p_std__setT_BWAPI__Force_p_t.getCPtr(original)), true);
  }

  public int size() {
    return bridgeJNI.ForceSet_size(swigCPtr, this);
  }

  public boolean contains(SWIG_Force item) {
    return bridgeJNI.ForceSet_contains(swigCPtr, this, SWIG_Force.getCPtr(item), item);
  }

  public boolean add(SWIG_Force item) {
    return bridgeJNI.ForceSet_add(swigCPtr, this, SWIG_Force.getCPtr(item), item);
  }

  public void clear() {
    bridgeJNI.ForceSet_clear(swigCPtr, this);
  }

  public boolean remove(SWIG_Force item) {
    return bridgeJNI.ForceSet_remove(swigCPtr, this, SWIG_Force.getCPtr(item), item);
  }

}