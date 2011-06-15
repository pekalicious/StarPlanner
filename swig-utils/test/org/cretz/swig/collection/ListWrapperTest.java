package org.cretz.swig.collection;

import java.util.Set;

import org.apache.commons.collections15.functors.NOPTransformer;
import org.cretz.swig.gen.StringListIterator;
import org.cretz.swig.gen.StringListWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for ListWrapper
 * 
 * @author Chad Retz
 */
public class ListWrapperTest extends SwigCollectionTestBase {

    @Test
    public void testCpp() {
        String value = test.testListWrapper();
        Assert.assertTrue(value, value.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testJava() {
        Set<String> list = new NativeSet<String, String>(
                String.class, StringListIterator.class, 
                test.getStringList(), StringListWrapper.class,
                NOPTransformer.INSTANCE, NOPTransformer.INSTANCE);
        //test contains
        Assert.assertTrue("Invalid contains!", list.contains("5"));
        //test add
        Assert.assertTrue("Add failed!", list.add("6"));
        //test size
        Assert.assertTrue("Wrong size!", list.size() == 6);
        //test remove
        Assert.assertTrue("Couldn't remove!", list.remove("6"));
        //test iterator
        int count = 0;
        for (String item : list) {
            Assert.assertTrue("Invalid length!", item.length() == 1);
            count++;
        }
        Assert.assertTrue("Invalid iterator!", count == 5);
        //test clear
        list.clear();
        Assert.assertTrue("Couldn't clear", list.isEmpty());
    }
}
