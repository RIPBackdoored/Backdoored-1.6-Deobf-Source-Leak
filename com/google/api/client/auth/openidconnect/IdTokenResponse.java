package com.google.api.client.auth.openidconnect;

import java.io.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public class IdTokenResponse extends TokenResponse
{
    @Key("id_token")
    private String idToken;
    
    public IdTokenResponse() {
        super();
    }
    
    public final String getIdToken() {
        return this.idToken;
    }
    
    public IdTokenResponse setIdToken(final String idToken) {
        this.idToken = Preconditions.checkNotNull(idToken);
        return this;
    }
    
    @Override
    public IdTokenResponse setAccessToken(final String accessToken) {
        super.setAccessToken(accessToken);
        return this;
    }
    
    @Override
    public IdTokenResponse setTokenType(final String tokenType) {
        super.setTokenType(tokenType);
        return this;
    }
    
    @Override
    public IdTokenResponse setExpiresInSeconds(final Long expiresIn) {
        super.setExpiresInSeconds(expiresIn);
        return this;
    }
    
    @Override
    public IdTokenResponse setRefreshToken(final String refreshToken) {
        super.setRefreshToken(refreshToken);
        return this;
    }
    
    @Override
    public IdTokenResponse setScope(final String scope) {
        super.setScope(scope);
        return this;
    }
    
    public IdToken parseIdToken() throws IOException {
        return IdToken.parse(this.getFactory(), this.idToken);
    }
    
    public static IdTokenResponse execute(final TokenRequest tokenRequest) throws IOException {
        return tokenRequest.executeUnparsed().parseAs(IdTokenResponse.class);
    }
    
    @Override
    public IdTokenResponse set(final String fieldName, final Object value) {
        return (IdTokenResponse)super.set(fieldName, value);
    }
    
    @Override
    public IdTokenResponse clone() {
        return (IdTokenResponse)super.clone();
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
