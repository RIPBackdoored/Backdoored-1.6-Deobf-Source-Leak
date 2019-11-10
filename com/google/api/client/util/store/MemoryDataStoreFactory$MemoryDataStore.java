package com.google.api.client.util.store;

import java.io.*;

static class MemoryDataStore<V extends Serializable> extends AbstractMemoryDataStore<V>
{
    MemoryDataStore(final MemoryDataStoreFactory dataStore, final String id) {
        super(dataStore, id);
    }
    
    @Override
    public MemoryDataStoreFactory getDataStoreFactory() {
        return (MemoryDataStoreFactory)super.getDataStoreFactory();
    }
    
    @Override
    public /* bridge */ DataStoreFactory getDataStoreFactory() {
        return this.getDataStoreFactory();
    }
}
