package org.cretz.swig.collection;

import java.util.List;

import org.apache.commons.collections15.functors.NOPTransformer;
import org.cretz.swig.gen.StringVectorWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for VectorWrapper
 * 
 * @author Chad Retz
 */
public class VectorWrapperTest extends SwigCollectionTestBase {

    @Test
    public void testCpp() {
        String value = test.testVectorWrapper();
        Assert.assertTrue(value, value.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testJava() {
        List<String> list = new NativeList<String, String>(String.class,
                test.getStringVector(), StringVectorWrapper.class,
                NOPTransformer.INSTANCE, NOPTransformer.INSTANCE);
        //test add
        list.add("6");
        //test get
        Assert.assertTrue("Couldn't get!", list.get(5).equals("6"));
        //test size
        Assert.assertTrue("Wrong size!", list.size() == 6);
        //test remove
        Assert.assertTrue("Couldn't remove!", list.remove(5).equals("6"));
        Assert.assertTrue("Wrong size!", list.size() == 5);
        //test clear
        list.clear();
        Assert.assertTrue("Couldn't clear!", list.isEmpty());
    }
}
