package org.bwapi.unit.model;

import java.io.InputStream;

import org.junit.Assert;

/**
 * Broodwar map that copies from the given stream. This
 * cannot be reused.
 * 
 * @author Chad Retz
 */
public class BroodwarStreamMap implements BroodwarMap {

    private final String name;
    private InputStream stream;
    
    public BroodwarStreamMap(String name, InputStream stream) {
        this.name = name;
        this.stream = stream;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream open() {
        Assert.assertNotNull("This cannot object be used more than once", stream);
        InputStream ret = stream;
        stream = null;
        return ret;
    }

}
