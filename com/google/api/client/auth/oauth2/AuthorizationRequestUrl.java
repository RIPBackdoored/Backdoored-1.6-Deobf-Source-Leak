package com.google.api.client.auth.oauth2;

import com.google.api.client.http.*;
import java.util.*;
import com.google.api.client.util.*;

public class AuthorizationRequestUrl extends GenericUrl
{
    @Key("response_type")
    private String responseTypes;
    @Key("redirect_uri")
    private String redirectUri;
    @Key("scope")
    private String scopes;
    @Key("client_id")
    private String clientId;
    @Key
    private String state;
    
    public AuthorizationRequestUrl(final String authorizationServerEncodedUrl, final String clientId, final Collection<String> responseTypes) {
        super(authorizationServerEncodedUrl);
        Preconditions.checkArgument(this.getFragment() == null);
        this.setClientId(clientId);
        this.setResponseTypes(responseTypes);
    }
    
    public final String getResponseTypes() {
        return this.responseTypes;
    }
    
    public AuthorizationRequestUrl setResponseTypes(final Collection<String> responseTypes) {
        this.responseTypes = Joiner.on(' ').join(responseTypes);
        return this;
    }
    
    public final String getRedirectUri() {
        return this.redirectUri;
    }
    
    public AuthorizationRequestUrl setRedirectUri(final String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }
    
    public final String getScopes() {
        return this.scopes;
    }
    
    public AuthorizationRequestUrl setScopes(final Collection<String> scopes) {
        this.scopes = ((scopes == null || !scopes.iterator().hasNext()) ? null : Joiner.on(' ').join(scopes));
        return this;
    }
    
    public final String getClientId() {
        return this.clientId;
    }
    
    public AuthorizationRequestUrl setClientId(final String clientId) {
        this.clientId = Preconditions.checkNotNull(clientId);
        return this;
    }
    
    public final String getState() {
        return this.state;
    }
    
    public AuthorizationRequestUrl setState(final String state) {
        this.state = state;
        return this;
    }
    
    @Override
    public AuthorizationRequestUrl set(final String fieldName, final Object value) {
        return (AuthorizationRequestUrl)super.set(fieldName, value);
    }
    
    @Override
    public AuthorizationRequestUrl clone() {
        return (AuthorizationRequestUrl)super.clone();
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
