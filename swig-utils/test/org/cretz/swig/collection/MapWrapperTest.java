package org.cretz.swig.collection;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.functors.NOPTransformer;
import org.cretz.swig.gen.StringMapIterator;
import org.cretz.swig.gen.StringMapWrapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for map wrapper
 * 
 * @author Chad Retz
 */
public class MapWrapperTest extends SwigCollectionTestBase {

    @Test
    public void testCpp() {
        String value = test.testMapWrapper();
        Assert.assertTrue(value, value.isEmpty());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testJava() {
        Map<String, String> map = new NativeMap<String, String, String, String>(
                String.class, String.class, StringMapIterator.class, 
                test.getStringMap(), StringMapWrapper.class,
                NOPTransformer.INSTANCE, NOPTransformer.INSTANCE,
                NOPTransformer.INSTANCE, NOPTransformer.INSTANCE);
        //test add
        map.put("6", "6");
        //test size
        Assert.assertTrue("Wrong size!", map.size() == 6);
        //test remove
        Assert.assertTrue("Couldn't remove!", map.remove("6").equals("6"));
        Assert.assertTrue("Remove size wrong!", map.size() == 5);
        System.out.println("Size 1: " + map.size());
        System.out.println("Size 2: " + map.entrySet().size());
        for (Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        //test iterator
        int count = 0;
        for (Entry<String, String> entry : map.entrySet()) {
            Assert.assertTrue("Invalid key length!", entry.getKey().length() == 1);
            Assert.assertTrue("Invalid value length!", entry.getValue().length() == 1);
            count++;
        }
        Assert.assertTrue("Invalid count!", count == 5);
        //test clear
        map.clear();
        Assert.assertTrue("Couldn't clear!", map.isEmpty());
    }
}
