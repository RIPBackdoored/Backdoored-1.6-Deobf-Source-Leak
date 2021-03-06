package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class AuthorizationCodeTokenRequest extends TokenRequest
{
    @Key
    private String code;
    @Key("redirect_uri")
    private String redirectUri;
    
    public AuthorizationCodeTokenRequest(final HttpTransport transport, final JsonFactory jsonFactory, final GenericUrl tokenServerUrl, final String code) {
        super(transport, jsonFactory, tokenServerUrl, "authorization_code");
        this.setCode(code);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return (AuthorizationCodeTokenRequest)super.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return (AuthorizationCodeTokenRequest)super.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setScopes(final Collection<String> scopes) {
        return (AuthorizationCodeTokenRequest)super.setScopes(scopes);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setGrantType(final String grantType) {
        return (AuthorizationCodeTokenRequest)super.setGrantType(grantType);
    }
    
    @Override
    public AuthorizationCodeTokenRequest setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return (AuthorizationCodeTokenRequest)super.setClientAuthentication(clientAuthentication);
    }
    
    public final String getCode() {
        return this.code;
    }
    
    public AuthorizationCodeTokenRequest setCode(final String code) {
        this.code = Preconditions.checkNotNull(code);
        return this;
    }
    
    public final String getRedirectUri() {
        return this.redirectUri;
    }
    
    public AuthorizationCodeTokenRequest setRedirectUri(final String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }
    
    @Override
    public AuthorizationCodeTokenRequest set(final String fieldName, final Object value) {
        return (AuthorizationCodeTokenRequest)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ TokenRequest set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
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
