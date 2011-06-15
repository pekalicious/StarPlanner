package org.bwapi.unit.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bwapi.bridge.BridgeRuntimeException;

/**
 * Broodwar map to be copied from a File. This can be reused
 * 
 * @author Chad Retz
 */
public class BroodwarFileMap implements BroodwarMap {

    private final File file;
    
    public BroodwarFileMap(File file) {
        this.file = file;
    }
    
    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public InputStream open() {
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new BridgeRuntimeException("Unable to open file", e);
        }
    }

}
