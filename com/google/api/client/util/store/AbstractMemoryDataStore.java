package com.google.api.client.util.store;

import java.util.concurrent.locks.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

public class AbstractMemoryDataStore<V extends Serializable> extends AbstractDataStore<V>
{
    private final Lock lock;
    protected HashMap<String, byte[]> keyValueMap;
    
    protected AbstractMemoryDataStore(final DataStoreFactory dataStoreFactory, final String id) {
        super(dataStoreFactory, id);
        this.lock = new ReentrantLock();
        this.keyValueMap = Maps.newHashMap();
    }
    
    @Override
    public final Set<String> keySet() throws IOException {
        this.lock.lock();
        try {
            return Collections.unmodifiableSet((Set<? extends String>)this.keyValueMap.keySet());
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final Collection<V> values() throws IOException {
        this.lock.lock();
        try {
            final List<V> result = (List<V>)Lists.newArrayList();
            for (final byte[] bytes : this.keyValueMap.values()) {
                result.add(IOUtils.deserialize(bytes));
            }
            return (Collection<V>)Collections.unmodifiableList((List<?>)result);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final V get(final String key) throws IOException {
        if (key == null) {
            return null;
        }
        this.lock.lock();
        try {
            return IOUtils.deserialize(this.keyValueMap.get(key));
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final DataStore<V> set(final String key, final V value) throws IOException {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        this.lock.lock();
        try {
            this.keyValueMap.put(key, IOUtils.serialize(value));
            this.save();
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public DataStore<V> delete(final String key) throws IOException {
        if (key == null) {
            return this;
        }
        this.lock.lock();
        try {
            this.keyValueMap.remove(key);
            this.save();
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public final DataStore<V> clear() throws IOException {
        this.lock.lock();
        try {
            this.keyValueMap.clear();
            this.save();
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public boolean containsKey(final String key) throws IOException {
        if (key == null) {
            return false;
        }
        this.lock.lock();
        try {
            return this.keyValueMap.containsKey(key);
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public boolean containsValue(final V value) throws IOException {
        if (value == null) {
            return false;
        }
        this.lock.lock();
        try {
            final byte[] serialized = IOUtils.serialize(value);
            for (final byte[] bytes : this.keyValueMap.values()) {
                if (Arrays.equals(serialized, bytes)) {
                    return true;
                }
            }
            return false;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public boolean isEmpty() throws IOException {
        this.lock.lock();
        try {
            return this.keyValueMap.isEmpty();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public int size() throws IOException {
        this.lock.lock();
        try {
            return this.keyValueMap.size();
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public void save() throws IOException {
    }
    
    @Override
    public String toString() {
        return DataStoreUtils.toString(this);
    }
}
