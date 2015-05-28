/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.40
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.cretz.swig.gen;

public class StringMapWrapper {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected StringMapWrapper(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(StringMapWrapper obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigutilsJNI.delete_StringMapWrapper(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public StringMapWrapper(SWIGTYPE_p_std__mapT_std__string_std__string_t original) {
    this(swigutilsJNI.new_StringMapWrapper(SWIGTYPE_p_std__mapT_std__string_std__string_t.getCPtr(original)), true);
  }

  public int size() {
    return swigutilsJNI.StringMapWrapper_size(swigCPtr, this);
  }

  public boolean add(String key, String value) {
    return swigutilsJNI.StringMapWrapper_add(swigCPtr, this, key, value);
  }

  public void clear() {
    swigutilsJNI.StringMapWrapper_clear(swigCPtr, this);
  }

  public boolean remove(String key) {
    return swigutilsJNI.StringMapWrapper_remove(swigCPtr, this, key);
  }

}