package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

public final class GoogleClientSecrets extends GenericJson
{
    @Key
    private Details installed;
    @Key
    private Details web;
    
    public GoogleClientSecrets() {
        super();
    }
    
    public Details getInstalled() {
        return this.installed;
    }
    
    public GoogleClientSecrets setInstalled(final Details installed) {
        this.installed = installed;
        return this;
    }
    
    public Details getWeb() {
        return this.web;
    }
    
    public GoogleClientSecrets setWeb(final Details web) {
        this.web = web;
        return this;
    }
    
    public Details getDetails() {
        Preconditions.checkArgument(this.web == null != (this.installed == null));
        return (this.web == null) ? this.installed : this.web;
    }
    
    @Override
    public GoogleClientSecrets set(final String fieldName, final Object value) {
        return (GoogleClientSecrets)super.set(fieldName, value);
    }
    
    @Override
    public GoogleClientSecrets clone() {
        return (GoogleClientSecrets)super.clone();
    }
    
    public static GoogleClientSecrets load(final JsonFactory jsonFactory, final Reader reader) throws IOException {
        return jsonFactory.fromReader(reader, GoogleClientSecrets.class);
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
    
    public static final class Details extends GenericJson
    {
        @Key("client_id")
        private String clientId;
        @Key("client_secret")
        private String clientSecret;
        @Key("redirect_uris")
        private List<String> redirectUris;
        @Key("auth_uri")
        private String authUri;
        @Key("token_uri")
        private String tokenUri;
        
        public Details() {
            super();
        }
        
        public String getClientId() {
            return this.clientId;
        }
        
        public Details setClientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }
        
        public String getClientSecret() {
            return this.clientSecret;
        }
        
        public Details setClientSecret(final String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }
        
        public List<String> getRedirectUris() {
            return this.redirectUris;
        }
        
        public Details setRedirectUris(final List<String> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }
        
        public String getAuthUri() {
            return this.authUri;
        }
        
        public Details setAuthUri(final String authUri) {
            this.authUri = authUri;
            return this;
        }
        
        public String getTokenUri() {
            return this.tokenUri;
        }
        
        public Details setTokenUri(final String tokenUri) {
            this.tokenUri = tokenUri;
            return this;
        }
        
        @Override
        public Details set(final String fieldName, final Object value) {
            return (Details)super.set(fieldName, value);
        }
        
        @Override
        public Details clone() {
            return (Details)super.clone();
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
}
