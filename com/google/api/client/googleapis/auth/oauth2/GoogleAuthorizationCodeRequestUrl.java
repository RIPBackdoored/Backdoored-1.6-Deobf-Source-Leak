package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;

public class GoogleAuthorizationCodeRequestUrl extends AuthorizationCodeRequestUrl
{
    @Key("approval_prompt")
    private String approvalPrompt;
    @Key("access_type")
    private String accessType;
    
    public GoogleAuthorizationCodeRequestUrl(final String clientId, final String redirectUri, final Collection<String> scopes) {
        this("https://accounts.google.com/o/oauth2/auth", clientId, redirectUri, scopes);
    }
    
    public GoogleAuthorizationCodeRequestUrl(final String authorizationServerEncodedUrl, final String clientId, final String redirectUri, final Collection<String> scopes) {
        super(authorizationServerEncodedUrl, clientId);
        this.setRedirectUri(redirectUri);
        this.setScopes(scopes);
    }
    
    public GoogleAuthorizationCodeRequestUrl(final GoogleClientSecrets clientSecrets, final String redirectUri, final Collection<String> scopes) {
        this(clientSecrets.getDetails().getClientId(), redirectUri, scopes);
    }
    
    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }
    
    public GoogleAuthorizationCodeRequestUrl setApprovalPrompt(final String approvalPrompt) {
        this.approvalPrompt = approvalPrompt;
        return this;
    }
    
    public final String getAccessType() {
        return this.accessType;
    }
    
    public GoogleAuthorizationCodeRequestUrl setAccessType(final String accessType) {
        this.accessType = accessType;
        return this;
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setResponseTypes(final Collection<String> responseTypes) {
        return (GoogleAuthorizationCodeRequestUrl)super.setResponseTypes(responseTypes);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setRedirectUri(final String redirectUri) {
        Preconditions.checkNotNull(redirectUri);
        return (GoogleAuthorizationCodeRequestUrl)super.setRedirectUri(redirectUri);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setScopes(final Collection<String> scopes) {
        Preconditions.checkArgument(scopes.iterator().hasNext());
        return (GoogleAuthorizationCodeRequestUrl)super.setScopes(scopes);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setClientId(final String clientId) {
        return (GoogleAuthorizationCodeRequestUrl)super.setClientId(clientId);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl setState(final String state) {
        return (GoogleAuthorizationCodeRequestUrl)super.setState(state);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl set(final String fieldName, final Object value) {
        return (GoogleAuthorizationCodeRequestUrl)super.set(fieldName, value);
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl clone() {
        return (GoogleAuthorizationCodeRequestUrl)super.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setState(final String state) {
        return this.setState(state);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setClientId(final String clientId) {
        return this.setClientId(clientId);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setScopes(final Collection scopes) {
        return this.setScopes(scopes);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setRedirectUri(final String redirectUri) {
        return this.setRedirectUri(redirectUri);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl setResponseTypes(final Collection responseTypes) {
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
