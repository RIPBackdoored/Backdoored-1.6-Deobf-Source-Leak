package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class GoogleBrowserClientRequestUrl extends BrowserClientRequestUrl
{
    @Key("approval_prompt")
    private String approvalPrompt;
    
    public GoogleBrowserClientRequestUrl(final String clientId, final String redirectUri, final Collection<String> scopes) {
        super("https://accounts.google.com/o/oauth2/auth", clientId);
        this.setRedirectUri(redirectUri);
        this.setScopes(scopes);
    }
    
    public GoogleBrowserClientRequestUrl(final GoogleClientSecrets clientSecrets, final String redirectUri, final Collection<String> scopes) {
        this(clientSecrets.getDetails().getClientId(), redirectUri, scopes);
    }
    
    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }
    
    public GoogleBrowserClientRequestUrl setApprovalPrompt(final String approvalPrompt) {
        this.approvalPrompt = approvalPrompt;
        return this;
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setResponseTypes(final Collection<String> responseTypes) {
        return (GoogleBrowserClientRequestUrl)super.setResponseTypes(responseTypes);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setRedirectUri(final String redirectUri) {
        return (GoogleBrowserClientRequestUrl)super.setRedirectUri(redirectUri);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setScopes(final Collection<String> scopes) {
        Preconditions.checkArgument(scopes.iterator().hasNext());
        return (GoogleBrowserClientRequestUrl)super.setScopes(scopes);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setClientId(final String clientId) {
        return (GoogleBrowserClientRequestUrl)super.setClientId(clientId);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl setState(final String state) {
        return (GoogleBrowserClientRequestUrl)super.setState(state);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl set(final String fieldName, final Object value) {
        return (GoogleBrowserClientRequestUrl)super.set(fieldName, value);
    }
    
    @Override
    public GoogleBrowserClientRequestUrl clone() {
        return (GoogleBrowserClientRequestUrl)super.clone();
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setState(final String state) {
        return this.setState(state);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setClientId(final String clientId) {
        return this.setClientId(clientId);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ BrowserClientRequestUrl setResponseTypes(final Collection responseTypes) {
        return this.setResponseTypes(responseTypes);
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
