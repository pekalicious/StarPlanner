package org.cretz.swig;

/**
 * Base for testing swig utils
 * 
 * @author Chad Retz
 */
public abstract class SwigUtilsTestBase {

    static {
        System.loadLibrary("SwigUtils");
    }
}
