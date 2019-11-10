package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import java.io.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.*;

public class GoogleRefreshTokenRequest extends RefreshTokenRequest
{
    public GoogleRefreshTokenRequest(final HttpTransport transport, final JsonFactory jsonFactory, final String refreshToken, final String clientId, final String clientSecret) {
        super(transport, jsonFactory, new GenericUrl("https://accounts.google.com/o/oauth2/token"), refreshToken);
        this.setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
    }
    
    @Override
    public GoogleRefreshTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return (GoogleRefreshTokenRequest)super.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public GoogleRefreshTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return (GoogleRefreshTokenRequest)super.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public GoogleRefreshTokenRequest setScopes(final Collection<String> scopes) {
        return (GoogleRefreshTokenRequest)super.setScopes(scopes);
    }
    
    @Override
    public GoogleRefreshTokenRequest setGrantType(final String grantType) {
        return (GoogleRefreshTokenRequest)super.setGrantType(grantType);
    }
    
    @Override
    public GoogleRefreshTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return (GoogleRefreshTokenRequest)super.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public GoogleRefreshTokenRequest setRefreshToken(final String refreshToken) {
        return (GoogleRefreshTokenRequest)super.setRefreshToken(refreshToken);
    }
    
    @Override
    public GoogleTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(GoogleTokenResponse.class);
    }
    
    @Override
    public GoogleRefreshTokenRequest set(final String fieldName, final Object value) {
        return (GoogleRefreshTokenRequest)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setRefreshToken(final String refreshToken) {
        return this.setRefreshToken(refreshToken);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setGrantType(final String grantType) {
        return this.setGrantType(grantType);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ RefreshTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return this.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public /* bridge */ TokenRequest set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ TokenResponse execute() throws IOException {
        return this.execute();
    }
    
    @Override
    public /* bridge */ TokenRequest setGrantType(final String grantType) {
        return this.setGrantType(grantType);
    }
    
    @Override
    public /* bridge */ TokenRequest setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ TokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ TokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ TokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return this.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public /* bridge */ GenericData set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
}
