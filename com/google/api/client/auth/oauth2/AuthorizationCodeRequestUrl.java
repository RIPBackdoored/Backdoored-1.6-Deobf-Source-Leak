package com.google.api.client.auth.oauth2;

import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class AuthorizationCodeRequestUrl extends AuthorizationRequestUrl
{
    public AuthorizationCodeRequestUrl(final String authorizationServerEncodedUrl, final String clientId) {
        super(authorizationServerEncodedUrl, clientId, Collections.singleton("code"));
    }
    
    @Override
    public AuthorizationCodeRequestUrl setResponseTypes(final Collection<String> responseTypes) {
        return (AuthorizationCodeRequestUrl)super.setResponseTypes(responseTypes);
    }
    
    @Override
    public AuthorizationCodeRequestUrl setRedirectUri(final String redirectUri) {
        return (AuthorizationCodeRequestUrl)super.setRedirectUri(redirectUri);
    }
    
    @Override
    public AuthorizationCodeRequestUrl setScopes(final Collection<String> scopes) {
        return (AuthorizationCodeRequestUrl)super.setScopes(scopes);
    }
    
    @Override
    public AuthorizationCodeRequestUrl setClientId(final String clientId) {
        return (AuthorizationCodeRequestUrl)super.setClientId(clientId);
    }
    
    @Override
    public AuthorizationCodeRequestUrl setState(final String state) {
        return (AuthorizationCodeRequestUrl)super.setState(state);
    }
    
    @Override
    public AuthorizationCodeRequestUrl set(final String fieldName, final Object value) {
        return (AuthorizationCodeRequestUrl)super.set(fieldName, value);
    }
    
    @Override
    public AuthorizationCodeRequestUrl clone() {
        return (AuthorizationCodeRequestUrl)super.clone();
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
