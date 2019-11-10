package com.google.api.client.util.store;

import java.util.*;
import java.util.regex.*;
import java.util.concurrent.locks.*;
import com.google.api.client.util.*;
import java.io.*;

public abstract class AbstractDataStoreFactory implements DataStoreFactory
{
    private final Lock lock;
    private final Map<String, DataStore<? extends Serializable>> dataStoreMap;
    private static final Pattern ID_PATTERN;
    
    public AbstractDataStoreFactory() {
        super();
        this.lock = new ReentrantLock();
        this.dataStoreMap = (Map<String, DataStore<? extends Serializable>>)Maps.newHashMap();
    }
    
    @Override
    public final <V extends Serializable> DataStore<V> getDataStore(final String id) throws IOException {
        Preconditions.checkArgument(AbstractDataStoreFactory.ID_PATTERN.matcher(id).matches(), "%s does not match pattern %s", id, AbstractDataStoreFactory.ID_PATTERN);
        this.lock.lock();
        try {
            DataStore<V> dataStore = (DataStore<V>)this.dataStoreMap.get(id);
            if (dataStore == null) {
                dataStore = (DataStore<V>)this.createDataStore(id);
                this.dataStoreMap.put(id, dataStore);
            }
            return dataStore;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    protected abstract <V extends Serializable> DataStore<V> createDataStore(final String p0) throws IOException;
    
    static {
        ID_PATTERN = Pattern.compile("\\w{1,30}");
    }
}
