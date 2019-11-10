package com.google.api.client.util.store;

import com.google.api.client.util.*;
import java.io.*;

public abstract class AbstractDataStore<V extends Serializable> implements DataStore<V>
{
    private final DataStoreFactory dataStoreFactory;
    private final String id;
    
    protected AbstractDataStore(final DataStoreFactory dataStoreFactory, final String id) {
        super();
        this.dataStoreFactory = Preconditions.checkNotNull(dataStoreFactory);
        this.id = Preconditions.checkNotNull(id);
    }
    
    @Override
    public DataStoreFactory getDataStoreFactory() {
        return this.dataStoreFactory;
    }
    
    @Override
    public final String getId() {
        return this.id;
    }
    
    @Override
    public boolean containsKey(final String key) throws IOException {
        return this.get(key) != null;
    }
    
    @Override
    public boolean containsValue(final V value) throws IOException {
        return this.values().contains(value);
    }
    
    @Override
    public boolean isEmpty() throws IOException {
        return this.size() == 0;
    }
    
    @Override
    public int size() throws IOException {
        return this.keySet().size();
    }
}
