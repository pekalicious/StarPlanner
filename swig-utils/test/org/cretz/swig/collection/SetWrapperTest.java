package org.cretz.swig.collection;

import java.util.Set;

import org.apache.commons.collections15.functors.NOPTransformer;
import org.cretz.swig.gen.StringSetIterator;
import org.cretz.swig.gen.StringSetWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for set wrapper
 * 
 * @author Chad Retz
 */
public class SetWrapperTest extends SwigCollectionTestBase {

    @Test
    public void testCpp() {
        String value = test.testSetWrapper();
        Assert.assertTrue(value, value.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testJava() {
        Set<String> set = new NativeSet<String, String>(
                String.class, StringSetIterator.class, 
                test.getStringSet(), StringSetWrapper.class, NOPTransformer.INSTANCE,
                NOPTransformer.INSTANCE);
        //test contains
        Assert.assertTrue("Invalid contains!", set.contains("5"));
        //test add
        Assert.assertTrue("Add failed!", set.add("6"));
        //test size
        Assert.assertTrue("Wrong size!", set.size() == 6);
        //test remove
        Assert.assertTrue("Couldn't remove!", set.remove("6"));
        //test iterator
        int count = 0;
        for (String item : set) {
            Assert.assertTrue("Invalid length!", item.length() == 1);
            count++;
        }
        Assert.assertTrue("Invalid iterator!", count == 5);
        //test clear
        set.clear();
        Assert.assertTrue("Couldn't clear", set.isEmpty());
    }
}
