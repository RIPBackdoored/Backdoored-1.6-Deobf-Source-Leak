package com.google.api.client.auth.oauth2;

import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class BrowserClientRequestUrl extends AuthorizationRequestUrl
{
    public BrowserClientRequestUrl(final String encodedAuthorizationServerUrl, final String clientId) {
        super(encodedAuthorizationServerUrl, clientId, Collections.singleton("token"));
    }
    
    @Override
    public BrowserClientRequestUrl setResponseTypes(final Collection<String> responseTypes) {
        return (BrowserClientRequestUrl)super.setResponseTypes(responseTypes);
    }
    
    @Override
    public BrowserClientRequestUrl setRedirectUri(final String redirectUri) {
        return (BrowserClientRequestUrl)super.setRedirectUri(redirectUri);
    }
    
    @Override
    public BrowserClientRequestUrl setScopes(final Collection<String> scopes) {
        return (BrowserClientRequestUrl)super.setScopes(scopes);
    }
    
    @Override
    public BrowserClientRequestUrl setClientId(final String clientId) {
        return (BrowserClientRequestUrl)super.setClientId(clientId);
    }
    
    @Override
    public BrowserClientRequestUrl setState(final String state) {
        return (BrowserClientRequestUrl)super.setState(state);
    }
    
    @Override
    public BrowserClientRequestUrl set(final String fieldName, final Object value) {
        return (BrowserClientRequestUrl)super.set(fieldName, value);
    }
    
    @Override
    public BrowserClientRequestUrl clone() {
        return (BrowserClientRequestUrl)super.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setState(final String state) {
        return this.setState(state);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setClientId(final String clientId) {
        return this.setClientId(clientId);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ AuthorizationRequestUrl setResponseTypes(final Collection responseTypes) {
        return this.setResponseTypes(responseTypes);
    }
    
    @Override
    public /* bridge */ GenericUrl set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ GenericUrl clone() {
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
