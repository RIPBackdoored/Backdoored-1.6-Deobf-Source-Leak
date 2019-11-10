package com.google.api.client.googleapis.auth.oauth2;

import java.util.*;
import com.google.api.client.json.*;
import java.security.*;
import com.google.api.client.util.*;
import java.io.*;
import java.security.spec.*;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.*;

public static class Builder extends Credential.Builder
{
    String serviceAccountId;
    Collection<String> serviceAccountScopes;
    PrivateKey serviceAccountPrivateKey;
    String serviceAccountPrivateKeyId;
    String serviceAccountProjectId;
    String serviceAccountUser;
    
    public Builder() {
        super(BearerToken.authorizationHeaderAccessMethod());
        this.setTokenServerEncodedUrl("https://accounts.google.com/o/oauth2/token");
    }
    
    @Override
    public GoogleCredential build() {
        return new GoogleCredential(this);
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
    public Builder setClock(final Clock clock) {
        return (Builder)super.setClock(clock);
    }
    
    public Builder setClientSecrets(final String clientId, final String clientSecret) {
        this.setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
        return this;
    }
    
    public Builder setClientSecrets(final GoogleClientSecrets clientSecrets) {
        final GoogleClientSecrets.Details details = clientSecrets.getDetails();
        this.setClientAuthentication(new ClientParametersAuthentication(details.getClientId(), details.getClientSecret()));
        return this;
    }
    
    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }
    
    public Builder setServiceAccountId(final String serviceAccountId) {
        this.serviceAccountId = serviceAccountId;
        return this;
    }
    
    public final String getServiceAccountProjectId() {
        return this.serviceAccountProjectId;
    }
    
    public Builder setServiceAccountProjectId(final String serviceAccountProjectId) {
        this.serviceAccountProjectId = serviceAccountProjectId;
        return this;
    }
    
    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }
    
    public Builder setServiceAccountScopes(final Collection<String> serviceAccountScopes) {
        this.serviceAccountScopes = serviceAccountScopes;
        return this;
    }
    
    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }
    
    public Builder setServiceAccountPrivateKey(final PrivateKey serviceAccountPrivateKey) {
        this.serviceAccountPrivateKey = serviceAccountPrivateKey;
        return this;
    }
    
    @Beta
    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }
    
    @Beta
    public Builder setServiceAccountPrivateKeyId(final String serviceAccountPrivateKeyId) {
        this.serviceAccountPrivateKeyId = serviceAccountPrivateKeyId;
        return this;
    }
    
    public Builder setServiceAccountPrivateKeyFromP12File(final File p12File) throws GeneralSecurityException, IOException {
        this.serviceAccountPrivateKey = SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), new FileInputStream(p12File), "notasecret", "privatekey", "notasecret");
        return this;
    }
    
    @Beta
    public Builder setServiceAccountPrivateKeyFromPemFile(final File pemFile) throws GeneralSecurityException, IOException {
        final byte[] bytes = PemReader.readFirstSectionAndClose(new FileReader(pemFile), "PRIVATE KEY").getBase64DecodedBytes();
        this.serviceAccountPrivateKey = SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(bytes));
        return this;
    }
    
    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }
    
    public Builder setServiceAccountUser(final String serviceAccountUser) {
        this.serviceAccountUser = serviceAccountUser;
        return this;
    }
    
    @Override
    public Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return (Builder)super.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
        return (Builder)super.addRefreshListener(refreshListener);
    }
    
    @Override
    public Builder setRefreshListeners(final Collection<CredentialRefreshListener> refreshListeners) {
        return (Builder)super.setRefreshListeners(refreshListeners);
    }
    
    @Override
    public Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return (Builder)super.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
        return (Builder)super.setTokenServerEncodedUrl(tokenServerEncodedUrl);
    }
    
    @Override
    public Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return (Builder)super.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ Credential.Builder setRefreshListeners(final Collection refreshListeners) {
        return this.setRefreshListeners(refreshListeners);
    }
    
    @Override
    public /* bridge */ Credential.Builder addRefreshListener(final CredentialRefreshListener refreshListener) {
        return this.addRefreshListener(refreshListener);
    }
    
    @Override
    public /* bridge */ Credential.Builder setRequestInitializer(final HttpRequestInitializer requestInitializer) {
        return this.setRequestInitializer(requestInitializer);
    }
    
    @Override
    public /* bridge */ Credential.Builder setClientAuthentication(final HttpExecuteInterceptor clientAuthentication) {
        return this.setClientAuthentication(clientAuthentication);
    }
    
    @Override
    public /* bridge */ Credential.Builder setTokenServerEncodedUrl(final String tokenServerEncodedUrl) {
        return this.setTokenServerEncodedUrl(tokenServerEncodedUrl);
    }
    
    @Override
    public /* bridge */ Credential.Builder setTokenServerUrl(final GenericUrl tokenServerUrl) {
        return this.setTokenServerUrl(tokenServerUrl);
    }
    
    @Override
    public /* bridge */ Credential.Builder setJsonFactory(final JsonFactory jsonFactory) {
        return this.setJsonFactory(jsonFactory);
    }
    
    @Override
    public /* bridge */ Credential.Builder setClock(final Clock clock) {
        return this.setClock(clock);
    }
    
    @Override
    public /* bridge */ Credential.Builder setTransport(final HttpTransport transport) {
        return this.setTransport(transport);
    }
    
    @Override
    public /* bridge */ Credential build() {
        return this.build();
    }
}
