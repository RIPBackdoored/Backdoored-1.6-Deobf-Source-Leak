package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.*;
import java.io.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.util.*;

public class GoogleAuthorizationCodeTokenRequest extends AuthorizationCodeTokenRequest
{
    public GoogleAuthorizationCodeTokenRequest(final HttpTransport transport, final JsonFactory jsonFactory, final String clientId, final String clientSecret, final String code, final String redirectUri) {
        this(transport, jsonFactory, "https://accounts.google.com/o/oauth2/token", clientId, clientSecret, code, redirectUri);
    }
    
    public GoogleAuthorizationCodeTokenRequest(final HttpTransport transport, final JsonFactory jsonFactory, final String tokenServerEncodedUrl, final String clientId, final String clientSecret, final String code, final String redirectUri) {
        super(transport, jsonFactory, new GenericUrl(tokenServerEncodedUrl), code);
        this.setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
        this.setRedirectUri(redirectUri);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return (GoogleAuthorizationCodeTokenRequest)super.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return (GoogleAuthorizationCodeTokenRequest)super.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setScopes(final Collection<String> scopes) {
        return (GoogleAuthorizationCodeTokenRequest)super.setScopes(scopes);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setGrantType(final String grantType) {
        return (GoogleAuthorizationCodeTokenRequest)super.setGrantType(grantType);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        Preconditions.checkNotNull(clientAuthentication);
        return (GoogleAuthorizationCodeTokenRequest)super.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setCode(final String code) {
        return (GoogleAuthorizationCodeTokenRequest)super.setCode(code);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest setRedirectUri(final String redirectUri) {
        Preconditions.checkNotNull(redirectUri);
        return (GoogleAuthorizationCodeTokenRequest)super.setRedirectUri(redirectUri);
    }
    
    @Override
    public GoogleTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(GoogleTokenResponse.class);
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest set(final String fieldName, final Object value) {
        return (GoogleAuthorizationCodeTokenRequest)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setCode(final String code) {
        return this.setCode(code);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setGrantType(final String grantType) {
        return this.setGrantType(grantType);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
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
