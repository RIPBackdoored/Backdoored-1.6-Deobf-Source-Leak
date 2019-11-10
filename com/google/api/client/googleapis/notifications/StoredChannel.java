package com.google.api.client.googleapis.notifications;

import java.util.concurrent.locks.*;
import java.io.*;
import com.google.api.client.util.store.*;
import com.google.api.client.util.*;

@Beta
public final class StoredChannel implements Serializable
{
    public static final String DEFAULT_DATA_STORE_ID;
    private static final long serialVersionUID = 1L;
    private final Lock lock;
    private final UnparsedNotificationCallback notificationCallback;
    private String clientToken;
    private Long expiration;
    private final String id;
    private String topicId;
    
    public StoredChannel(final UnparsedNotificationCallback notificationCallback) {
        this(notificationCallback, NotificationUtils.randomUuidString());
    }
    
    public StoredChannel(final UnparsedNotificationCallback notificationCallback, final String id) {
        super();
        this.lock = new ReentrantLock();
        this.notificationCallback = Preconditions.checkNotNull(notificationCallback);
        this.id = Preconditions.checkNotNull(id);
    }
    
    public StoredChannel store(final DataStoreFactory dataStoreFactory) throws IOException {
        return this.store(getDefaultDataStore(dataStoreFactory));
    }
    
    public StoredChannel store(final DataStore<StoredChannel> dataStore) throws IOException {
        this.lock.lock();
        try {
            dataStore.set(this.getId(), this);
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public UnparsedNotificationCallback getNotificationCallback() {
        this.lock.lock();
        try {
            return this.notificationCallback;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public String getClientToken() {
        this.lock.lock();
        try {
            return this.clientToken;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredChannel setClientToken(final String clientToken) {
        this.lock.lock();
        try {
            this.clientToken = clientToken;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public Long getExpiration() {
        this.lock.lock();
        try {
            return this.expiration;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredChannel setExpiration(final Long expiration) {
        this.lock.lock();
        try {
            this.expiration = expiration;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    public String getId() {
        this.lock.lock();
        try {
            return this.id;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public String getTopicId() {
        this.lock.lock();
        try {
            return this.topicId;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public StoredChannel setTopicId(final String topicId) {
        this.lock.lock();
        try {
            this.topicId = topicId;
        }
        finally {
            this.lock.unlock();
        }
        return this;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(StoredChannel.class).add("notificationCallback", this.getNotificationCallback()).add("clientToken", this.getClientToken()).add("expiration", this.getExpiration()).add("id", this.getId()).add("topicId", this.getTopicId()).toString();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StoredChannel)) {
            return false;
        }
        final StoredChannel o = (StoredChannel)other;
        return this.getId().equals(o.getId());
    }
    
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
    
    public static DataStore<StoredChannel> getDefaultDataStore(final DataStoreFactory dataStoreFactory) throws IOException {
        return dataStoreFactory.getDataStore(StoredChannel.DEFAULT_DATA_STORE_ID);
    }
    
    static {
        DEFAULT_DATA_STORE_ID = StoredChannel.class.getSimpleName();
    }
}
