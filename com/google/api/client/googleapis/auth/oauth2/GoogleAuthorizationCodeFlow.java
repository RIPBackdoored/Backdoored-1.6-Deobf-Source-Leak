package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.store.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import com.google.api.client.auth.oauth2.*;

public class GoogleAuthorizationCodeFlow extends AuthorizationCodeFlow
{
    private final String approvalPrompt;
    private final String accessType;
    
    public GoogleAuthorizationCodeFlow(final HttpTransport transport, final JsonFactory jsonFactory, final String clientId, final String clientSecret, final Collection<String> scopes) {
        this(new Builder(transport, jsonFactory, clientId, clientSecret, scopes));
    }
    
    protected GoogleAuthorizationCodeFlow(final Builder builder) {
        super(builder);
        this.accessType = builder.accessType;
        this.approvalPrompt = builder.approvalPrompt;
    }
    
    @Override
    public GoogleAuthorizationCodeTokenRequest newTokenRequest(final String authorizationCode) {
        return new GoogleAuthorizationCodeTokenRequest(this.getTransport(), this.getJsonFactory(), this.getTokenServerEncodedUrl(), "", "", authorizationCode, "").setClientAuthentication(this.getClientAuthentication()).setRequestInitializer(this.getRequestInitializer()).setScopes(this.getScopes());
    }
    
    @Override
    public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new GoogleAuthorizationCodeRequestUrl(this.getAuthorizationServerEncodedUrl(), this.getClientId(), "", this.getScopes()).setAccessType(this.accessType).setApprovalPrompt(this.approvalPrompt);
    }
    
    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }
    
    public final String getAccessType() {
        return this.accessType;
    }
    
    @Override
    public /* bridge */ AuthorizationCodeTokenRequest newTokenRequest(final String authorizationCode) {
        return this.newTokenRequest(authorizationCode);
    }
    
    @Override
    public /* bridge */ AuthorizationCodeRequestUrl newAuthorizationUrl() {
        return this.newAuthorizationUrl();
    }
    
    public static class Builder extends AuthorizationCodeFlow.Builder
    {
        String approvalPrompt;
        String accessType;
        
        public Builder(final HttpTransport transport, final JsonFactory jsonFactory, final String clientId, final String clientSecret, final Collection<String> scopes) {
            super(BearerToken.authorizationHeaderAccessMethod(), transport, jsonFactory, new GenericUrl("https://accounts.google.com/o/oauth2/token"), new ClientParametersAuthentication(clientId, clientSecret), clientId, "https://accounts.google.com/o/oauth2/auth");
            this.setScopes(scopes);
        }
        
        public Builder(final HttpTransport transport, final JsonFactory jsonFactory, final GoogleClientSecrets clientSecrets, final Collection<String> scopes) {
            super(BearerToken.authorizationHeaderAccessMethod(), transport, jsonFactory, new GenericUrl("https://accounts.google.com/o/oauth2/token"), new ClientParametersAuthentication(clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret()), clientSecrets.getDetails().getClientId(), "https://accounts.google.com/o/oauth2/auth");
            this.setScopes(scopes);
        }
        
        @Override
        public GoogleAuthorizationCodeFlow build() {
            return new GoogleAuthorizationCodeFlow(this);
        }
        
        @Override
        public Builder setDataStoreFactory(final DataStoreFactory dataStore) throws IOException {
            return (Builder)super.setDataStoreFactory(dataStore);
        }
        
        @Override
        public Builder setCredentialDataStore(final DataStore<StoredCredential> typedDataStore) {
            return (Builder)super.setCredentialDataStore(typedDataStore);
        }
        
        @Override
        public Builder setCredentialCreatedListener(final CredentialCreatedListener credentialCreatedListener) {
            return (Builder)super.setCredentialCreatedListener(credentialCreatedListener);
        }
        
        @Deprecated
        @Beta
        @Override
        public Builder setCredentialStore(final CredentialStore credentialStore) {
            return (Builder)super.setCredentialStore(credentialStore);
        }
        
        @Override
        public Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            return (Builder)super.setRequestInitializer(requestInitializer);
        }
        
        @Override
        public Builder setScopes(final Collection<String> scopes) {
            Preconditions.checkState(!scopes.isEmpty());
            return (Builder)super.setScopes(scopes);
        }
        
        @Override
        public Builder setMethod(final Credential.AccessMethod method) {
            return (Builder)super.setMethod(method);
        }
        
        @Override
        public Builder setTransport(final HttpTransport transport) {
            return (Builder)super.setTransport(transport);
        }
        
        @Override
        public Builder setJsonFactory(final JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(jsonFactory);
        }
        
        @Override
        public Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            return (Builder)super.setTokenServerUrl(tokenServerUrl);
        }
        
        @Override
        public Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return (Builder)super.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public Builder setClientId(final String clientId) {
            return (Builder)super.setClientId(clientId);
        }
        
        @Override
        public Builder setAuthorizationServerEncodedUrl(final String authorizationServerEncodedUrl) {
            return (Builder)super.setAuthorizationServerEncodedUrl(authorizationServerEncodedUrl);
        }
        
        @Override
        public Builder setClock(final Clock clock) {
            return (Builder)super.setClock(clock);
        }
        
        @Override
        public Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
            return (Builder)super.addRefreshListener(refreshListener);
        }
        
        @Override
        public Builder setRefreshListeners(final Collection<CredentialRefreshListener> refreshListeners) {
            return (Builder)super.setRefreshListeners(refreshListeners);
        }
        
        public Builder setApprovalPrompt(final String approvalPrompt) {
            this.approvalPrompt = approvalPrompt;
            return this;
        }
        
        public final String getApprovalPrompt() {
            return this.approvalPrompt;
        }
        
        public Builder setAccessType(final String accessType) {
            this.accessType = accessType;
            return this;
        }
        
        public final String getAccessType() {
            return this.accessType;
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setRefreshListeners(final Collection refreshListeners) {
            return this.setRefreshListeners(refreshListeners);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
            return this.addRefreshListener(refreshListener);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setCredentialCreatedListener(final CredentialCreatedListener credentialCreatedListener) {
            return this.setCredentialCreatedListener(credentialCreatedListener);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setScopes(final Collection scopes) {
            return this.setScopes(scopes);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
            return this.setRequestInitializer(requestInitializer);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setCredentialDataStore(final DataStore credentialDataStore) {
            return this.setCredentialDataStore(credentialDataStore);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setDataStoreFactory(final DataStoreFactory dataStoreFactory) throws IOException {
            return this.setDataStoreFactory(dataStoreFactory);
        }
        
        @Deprecated
        @Beta
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setCredentialStore(final CredentialStore credentialStore) {
            return this.setCredentialStore(credentialStore);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setAuthorizationServerEncodedUrl(final String authorizationServerEncodedUrl) {
            return this.setAuthorizationServerEncodedUrl(authorizationServerEncodedUrl);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setClientId(final String clientId) {
            return this.setClientId(clientId);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
            return this.setClientAuthentication(clientAuthentication);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
            return this.setTokenServerUrl(tokenServerUrl);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setJsonFactory(final JsonFactory jsonFactory) {
            return this.setJsonFactory(jsonFactory);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setTransport(final HttpTransport transport) {
            return this.setTransport(transport);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow.Builder setMethod(final Credential.AccessMethod method) {
            return this.setMethod(method);
        }
        
        @Override
        public /* bridge */ AuthorizationCodeFlow build() {
            return this.build();
        }
    }
}
