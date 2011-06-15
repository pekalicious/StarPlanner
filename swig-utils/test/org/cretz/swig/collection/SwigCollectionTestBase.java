package org.cretz.swig.collection;

import org.cretz.swig.SwigUtilsTestBase;
import org.cretz.swig.gen.TestCpp;
import org.junit.BeforeClass;

/**
 * Test base for collection tests
 * 
 * @author Chad Retz
 */
public class SwigCollectionTestBase extends SwigUtilsTestBase {

    protected static TestCpp test;
    
    @BeforeClass
    public static void setupClass() {
        test = new TestCpp();
    }
}
