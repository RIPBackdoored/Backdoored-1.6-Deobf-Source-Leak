package com.google.api.client.extensions.java6.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.*;

@Deprecated
@Beta
public class FilePersistedCredential extends GenericJson
{
    @Key("access_token")
    private String accessToken;
    @Key("refresh_token")
    private String refreshToken;
    @Key("expiration_time_millis")
    private Long expirationTimeMillis;
    
    public FilePersistedCredential() {
        super();
    }
    
    void store(final Credential credential) {
        this.accessToken = credential.getAccessToken();
        this.refreshToken = credential.getRefreshToken();
        this.expirationTimeMillis = credential.getExpirationTimeMilliseconds();
    }
    
    void load(final Credential credential) {
        credential.setAccessToken(this.accessToken);
        credential.setRefreshToken(this.refreshToken);
        credential.setExpirationTimeMilliseconds(this.expirationTimeMillis);
    }
    
    @Override
    public FilePersistedCredential set(final String fieldName, final Object value) {
        return (FilePersistedCredential)super.set(fieldName, value);
    }
    
    @Override
    public FilePersistedCredential clone() {
        return (FilePersistedCredential)super.clone();
    }
    
    StoredCredential toStoredCredential() {
        return new StoredCredential().setAccessToken(this.accessToken).setRefreshToken(this.refreshToken).setExpirationTimeMilliseconds(this.expirationTimeMillis);
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
