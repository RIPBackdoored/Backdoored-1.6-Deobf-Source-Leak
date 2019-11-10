package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.googleapis.util.*;
import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.json.webtoken.*;
import java.security.spec.*;
import java.security.*;
import com.google.api.client.util.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.auth.oauth2.*;

public class GoogleCredential extends Credential
{
    static final String USER_FILE_TYPE = "authorized_user";
    static final String SERVICE_ACCOUNT_FILE_TYPE = "service_account";
    @Beta
    private static DefaultCredentialProvider defaultCredentialProvider;
    private String serviceAccountId;
    private String serviceAccountProjectId;
    private Collection<String> serviceAccountScopes;
    private PrivateKey serviceAccountPrivateKey;
    private String serviceAccountPrivateKeyId;
    private String serviceAccountUser;
    
    @Beta
    public static GoogleCredential getApplicationDefault() throws IOException {
        return getApplicationDefault(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }
    
    @Beta
    public static GoogleCredential getApplicationDefault(final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(transport);
        Preconditions.checkNotNull(jsonFactory);
        return GoogleCredential.defaultCredentialProvider.getDefaultCredential(transport, jsonFactory);
    }
    
    @Beta
    public static GoogleCredential fromStream(final InputStream credentialStream) throws IOException {
        return fromStream(credentialStream, Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }
    
    @Beta
    public static GoogleCredential fromStream(final InputStream credentialStream, final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(credentialStream);
        Preconditions.checkNotNull(transport);
        Preconditions.checkNotNull(jsonFactory);
        final JsonObjectParser parser = new JsonObjectParser(jsonFactory);
        final GenericJson fileContents = parser.parseAndClose(credentialStream, OAuth2Utils.UTF_8, GenericJson.class);
        final String fileType = (String)fileContents.get("type");
        if (fileType == null) {
            throw new IOException("Error reading credentials from stream, 'type' field not specified.");
        }
        if ("authorized_user".equals(fileType)) {
            return fromStreamUser(fileContents, transport, jsonFactory);
        }
        if ("service_account".equals(fileType)) {
            return fromStreamServiceAccount(fileContents, transport, jsonFactory);
        }
        throw new IOException(String.format("Error reading credentials from stream, 'type' value '%s' not recognized. Expecting '%s' or '%s'.", fileType, "authorized_user", "service_account"));
    }
    
    public GoogleCredential() {
        this(new Builder());
    }
    
    protected GoogleCredential(final Builder builder) {
        super(builder);
        if (builder.serviceAccountPrivateKey == null) {
            Preconditions.checkArgument(builder.serviceAccountId == null && builder.serviceAccountScopes == null && builder.serviceAccountUser == null);
        }
        else {
            this.serviceAccountId = Preconditions.checkNotNull(builder.serviceAccountId);
            this.serviceAccountProjectId = builder.serviceAccountProjectId;
            this.serviceAccountScopes = (Collection<String>)((builder.serviceAccountScopes == null) ? Collections.emptyList() : Collections.unmodifiableCollection((Collection<?>)builder.serviceAccountScopes));
            this.serviceAccountPrivateKey = builder.serviceAccountPrivateKey;
            this.serviceAccountPrivateKeyId = builder.serviceAccountPrivateKeyId;
            this.serviceAccountUser = builder.serviceAccountUser;
        }
    }
    
    @Override
    public GoogleCredential setAccessToken(final String accessToken) {
        return (GoogleCredential)super.setAccessToken(accessToken);
    }
    
    @Override
    public GoogleCredential setRefreshToken(final String refreshToken) {
        if (refreshToken != null) {
            Preconditions.checkArgument(this.getJsonFactory() != null && this.getTransport() != null && this.getClientAuthentication() != null, (Object)"Please use the Builder and call setJsonFactory, setTransport and setClientSecrets");
        }
        return (GoogleCredential)super.setRefreshToken(refreshToken);
    }
    
    @Override
    public GoogleCredential setExpirationTimeMilliseconds(final Long expirationTimeMilliseconds) {
        return (GoogleCredential)super.setExpirationTimeMilliseconds(expirationTimeMilliseconds);
    }
    
    @Override
    public GoogleCredential setExpiresInSeconds(final Long expiresIn) {
        return (GoogleCredential)super.setExpiresInSeconds(expiresIn);
    }
    
    @Override
    public GoogleCredential setFromTokenResponse(final TokenResponse tokenResponse) {
        return (GoogleCredential)super.setFromTokenResponse(tokenResponse);
    }
    
    @Beta
    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.serviceAccountPrivateKey == null) {
            return super.executeRefreshToken();
        }
        final JsonWebSignature.Header header = new JsonWebSignature.Header();
        header.setAlgorithm("RS256");
        header.setType("JWT");
        header.setKeyId(this.serviceAccountPrivateKeyId);
        final JsonWebToken.Payload payload = new JsonWebToken.Payload();
        final long currentTime = this.getClock().currentTimeMillis();
        payload.setIssuer(this.serviceAccountId);
        payload.setAudience(this.getTokenServerEncodedUrl());
        payload.setIssuedAtTimeSeconds(currentTime / 1000L);
        payload.setExpirationTimeSeconds(currentTime / 1000L + 3600L);
        payload.setSubject(this.serviceAccountUser);
        payload.put("scope", Joiner.on(' ').join(this.serviceAccountScopes));
        try {
            final String assertion = JsonWebSignature.signUsingRsaSha256(this.serviceAccountPrivateKey, this.getJsonFactory(), header, payload);
            final TokenRequest request = new TokenRequest(this.getTransport(), this.getJsonFactory(), new GenericUrl(this.getTokenServerEncodedUrl()), "urn:ietf:params:oauth:grant-type:jwt-bearer");
            request.put("assertion", assertion);
            return request.execute();
        }
        catch (GeneralSecurityException exception) {
            final IOException e = new IOException();
            e.initCause(exception);
            throw e;
        }
    }
    
    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }
    
    public final String getServiceAccountProjectId() {
        return this.serviceAccountProjectId;
    }
    
    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }
    
    public final String getServiceAccountScopesAsString() {
        return (this.serviceAccountScopes == null) ? null : Joiner.on(' ').join(this.serviceAccountScopes);
    }
    
    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }
    
    @Beta
    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }
    
    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }
    
    @Beta
    public boolean createScopedRequired() {
        return this.serviceAccountPrivateKey != null && (this.serviceAccountScopes == null || this.serviceAccountScopes.isEmpty());
    }
    
    @Beta
    public GoogleCredential createScoped(final Collection<String> scopes) {
        if (this.serviceAccountPrivateKey == null) {
            return this;
        }
        return new Builder().setServiceAccountPrivateKey(this.serviceAccountPrivateKey).setServiceAccountPrivateKeyId(this.serviceAccountPrivateKeyId).setServiceAccountId(this.serviceAccountId).setServiceAccountProjectId(this.serviceAccountProjectId).setServiceAccountUser(this.serviceAccountUser).setServiceAccountScopes(scopes).setTokenServerEncodedUrl(this.getTokenServerEncodedUrl()).setTransport(this.getTransport()).setJsonFactory(this.getJsonFactory()).setClock(this.getClock()).build();
    }
    
    @Beta
    private static GoogleCredential fromStreamUser(final GenericJson fileContents, final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        final String clientId = (String)fileContents.get("client_id");
        final String clientSecret = (String)fileContents.get("client_secret");
        final String refreshToken = (String)fileContents.get("refresh_token");
        if (clientId == null || clientSecret == null || refreshToken == null) {
            throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        }
        final GoogleCredential credential = new Builder().setClientSecrets(clientId, clientSecret).setTransport(transport).setJsonFactory(jsonFactory).build();
        credential.setRefreshToken(refreshToken);
        credential.refreshToken();
        return credential;
    }
    
    @Beta
    private static GoogleCredential fromStreamServiceAccount(final GenericJson fileContents, final HttpTransport transport, final JsonFactory jsonFactory) throws IOException {
        final String clientId = (String)fileContents.get("client_id");
        final String clientEmail = (String)fileContents.get("client_email");
        final String privateKeyPem = (String)fileContents.get("private_key");
        final String privateKeyId = (String)fileContents.get("private_key_id");
        if (clientId == null || clientEmail == null || privateKeyPem == null || privateKeyId == null) {
            throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        }
        final PrivateKey privateKey = privateKeyFromPkcs8(privateKeyPem);
        final Collection<String> emptyScopes = (Collection<String>)Collections.emptyList();
        final Builder credentialBuilder = new Builder().setTransport(transport).setJsonFactory(jsonFactory).setServiceAccountId(clientEmail).setServiceAccountScopes(emptyScopes).setServiceAccountPrivateKey(privateKey).setServiceAccountPrivateKeyId(privateKeyId);
        final String tokenUri = (String)fileContents.get("token_uri");
        if (tokenUri != null) {
            credentialBuilder.setTokenServerEncodedUrl(tokenUri);
        }
        final String projectId = (String)fileContents.get("project_id");
        if (projectId != null) {
            credentialBuilder.setServiceAccountProjectId(projectId);
        }
        return credentialBuilder.build();
    }
    
    @Beta
    private static PrivateKey privateKeyFromPkcs8(final String privateKeyPem) throws IOException {
        final Reader reader = new StringReader(privateKeyPem);
        final PemReader.Section section = PemReader.readFirstSectionAndClose(reader, "PRIVATE KEY");
        if (section == null) {
            throw new IOException("Invalid PKCS8 data.");
        }
        final byte[] bytes = section.getBase64DecodedBytes();
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        Exception unexpectedException = null;
        try {
            final KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
            final PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        }
        catch (NoSuchAlgorithmException exception) {
            unexpectedException = exception;
        }
        catch (InvalidKeySpecException exception2) {
            unexpectedException = exception2;
        }
        throw OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), unexpectedException);
    }
    
    @Override
    public /* bridge */ Credential setFromTokenResponse(final TokenResponse fromTokenResponse) {
        return this.setFromTokenResponse(fromTokenResponse);
    }
    
    @Override
    public /* bridge */ Credential setExpiresInSeconds(final Long expiresInSeconds) {
        return this.setExpiresInSeconds(expiresInSeconds);
    }
    
    @Override
    public /* bridge */ Credential setExpirationTimeMilliseconds(final Long expirationTimeMilliseconds) {
        return this.setExpirationTimeMilliseconds(expirationTimeMilliseconds);
    }
    
    @Override
    public /* bridge */ Credential setRefreshToken(final String refreshToken) {
        return this.setRefreshToken(refreshToken);
    }
    
    @Override
    public /* bridge */ Credential setAccessToken(final String accessToken) {
        return this.setAccessToken(accessToken);
    }
    
    static {
        GoogleCredential.defaultCredentialProvider = new DefaultCredentialProvider();
    }
    
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
}
