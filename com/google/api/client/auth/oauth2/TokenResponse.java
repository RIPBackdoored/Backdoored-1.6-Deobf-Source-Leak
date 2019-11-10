package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class TokenResponse extends GenericJson
{
    @Key("access_token")
    private String accessToken;
    @Key("token_type")
    private String tokenType;
    @Key("expires_in")
    private Long expiresInSeconds;
    @Key("refresh_token")
    private String refreshToken;
    @Key
    private String scope;
    
    public TokenResponse() {
        super();
    }
    
    public final String getAccessToken() {
        return this.accessToken;
    }
    
    public TokenResponse setAccessToken(final String accessToken) {
        this.accessToken = Preconditions.checkNotNull(accessToken);
        return this;
    }
    
    public final String getTokenType() {
        return this.tokenType;
    }
    
    public TokenResponse setTokenType(final String tokenType) {
        this.tokenType = Preconditions.checkNotNull(tokenType);
        return this;
    }
    
    public final Long getExpiresInSeconds() {
        return this.expiresInSeconds;
    }
    
    public TokenResponse setExpiresInSeconds(final Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
        return this;
    }
    
    public final String getRefreshToken() {
        return this.refreshToken;
    }
    
    public TokenResponse setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
    
    public final String getScope() {
        return this.scope;
    }
    
    public TokenResponse setScope(final String scope) {
        this.scope = scope;
        return this;
    }
    
    @Override
    public TokenResponse set(final String fieldName, final Object value) {
        return (TokenResponse)super.set(fieldName, value);
    }
    
    @Override
    public TokenResponse clone() {
        return (TokenResponse)super.clone();
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
