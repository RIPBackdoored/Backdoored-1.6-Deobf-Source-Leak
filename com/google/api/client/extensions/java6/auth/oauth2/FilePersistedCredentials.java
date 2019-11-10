package com.google.api.client.extensions.java6.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.util.store.*;
import com.google.api.client.auth.oauth2.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.*;

@Deprecated
@Beta
public class FilePersistedCredentials extends GenericJson
{
    @Key
    private Map<String, FilePersistedCredential> credentials;
    
    public FilePersistedCredentials() {
        super();
        this.credentials = (Map<String, FilePersistedCredential>)Maps.newHashMap();
    }
    
    void store(final String userId, final Credential credential) {
        Preconditions.checkNotNull(userId);
        FilePersistedCredential fileCredential = this.credentials.get(userId);
        if (fileCredential == null) {
            fileCredential = new FilePersistedCredential();
            this.credentials.put(userId, fileCredential);
        }
        fileCredential.store(credential);
    }
    
    boolean load(final String userId, final Credential credential) {
        Preconditions.checkNotNull(userId);
        final FilePersistedCredential fileCredential = this.credentials.get(userId);
        if (fileCredential == null) {
            return false;
        }
        fileCredential.load(credential);
        return true;
    }
    
    void delete(final String userId) {
        Preconditions.checkNotNull(userId);
        this.credentials.remove(userId);
    }
    
    @Override
    public FilePersistedCredentials set(final String fieldName, final Object value) {
        return (FilePersistedCredentials)super.set(fieldName, value);
    }
    
    @Override
    public FilePersistedCredentials clone() {
        return (FilePersistedCredentials)super.clone();
    }
    
    void migrateTo(final DataStore<StoredCredential> typedDataStore) throws IOException {
        for (final Map.Entry<String, FilePersistedCredential> entry : this.credentials.entrySet()) {
            typedDataStore.set(entry.getKey(), entry.getValue().toStoredCredential());
        }
    }
    
    @Override
    public /* bridge */ GenericJson set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
