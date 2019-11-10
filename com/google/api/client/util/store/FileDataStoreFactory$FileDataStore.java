package com.google.api.client.util.store;

import com.google.api.client.util.*;
import java.util.*;
import java.io.*;

static class FileDataStore<V extends Serializable> extends AbstractMemoryDataStore<V>
{
    private final File dataFile;
    
    FileDataStore(final FileDataStoreFactory dataStore, final File dataDirectory, final String id) throws IOException {
        super(dataStore, id);
        this.dataFile = new File(dataDirectory, id);
        if (IOUtils.isSymbolicLink(this.dataFile)) {
            throw new IOException("unable to use a symbolic link: " + this.dataFile);
        }
        if (this.dataFile.createNewFile()) {
            this.keyValueMap = Maps.newHashMap();
            this.save();
        }
        else {
            this.keyValueMap = IOUtils.deserialize(new FileInputStream(this.dataFile));
        }
    }
    
    @Override
    public void save() throws IOException {
        IOUtils.serialize(this.keyValueMap, new FileOutputStream(this.dataFile));
    }
    
    @Override
    public FileDataStoreFactory getDataStoreFactory() {
        return (FileDataStoreFactory)super.getDataStoreFactory();
    }
    
    @Override
    public /* bridge */ DataStoreFactory getDataStoreFactory() {
        return this.getDataStoreFactory();
    }
}
