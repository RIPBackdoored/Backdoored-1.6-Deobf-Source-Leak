package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class GoogleTokenResponse extends TokenResponse
{
    @Key("id_token")
    private String idToken;
    
    public GoogleTokenResponse() {
        super();
    }
    
    @Override
    public GoogleTokenResponse setAccessToken(final String accessToken) {
        return (GoogleTokenResponse)super.setAccessToken(accessToken);
    }
    
    @Override
    public GoogleTokenResponse setTokenType(final String tokenType) {
        return (GoogleTokenResponse)super.setTokenType(tokenType);
    }
    
    @Override
    public GoogleTokenResponse setExpiresInSeconds(final Long expiresIn) {
        return (GoogleTokenResponse)super.setExpiresInSeconds(expiresIn);
    }
    
    @Override
    public GoogleTokenResponse setRefreshToken(final String refreshToken) {
        return (GoogleTokenResponse)super.setRefreshToken(refreshToken);
    }
    
    @Override
    public GoogleTokenResponse setScope(final String scope) {
        return (GoogleTokenResponse)super.setScope(scope);
    }
    
    @Beta
    public final String getIdToken() {
        return this.idToken;
    }
    
    @Beta
    public GoogleTokenResponse setIdToken(final String idToken) {
        this.idToken = Preconditions.checkNotNull(idToken);
        return this;
    }
    
    @Beta
    public GoogleIdToken parseIdToken() throws IOException {
        return GoogleIdToken.parse(this.getFactory(), this.getIdToken());
    }
    
    @Override
    public GoogleTokenResponse set(final String fieldName, final Object value) {
        return (GoogleTokenResponse)super.set(fieldName, value);
    }
    
    @Override
    public GoogleTokenResponse clone() {
        return (GoogleTokenResponse)super.clone();
    }
    
    @Override
    public /* bridge */ TokenResponse clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ TokenResponse set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ TokenResponse setScope(final String scope) {
        return this.setScope(scope);
    }
    
    @Override
    public /* bridge */ TokenResponse setRefreshToken(final String refreshToken) {
        return this.setRefreshToken(refreshToken);
    }
    
    @Override
    public /* bridge */ TokenResponse setExpiresInSeconds(final Long expiresInSeconds) {
        return this.setExpiresInSeconds(expiresInSeconds);
    }
    
    @Override
    public /* bridge */ TokenResponse setTokenType(final String tokenType) {
        return this.setTokenType(tokenType);
    }
    
    @Override
    public /* bridge */ TokenResponse setAccessToken(final String accessToken) {
        return this.setAccessToken(accessToken);
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
