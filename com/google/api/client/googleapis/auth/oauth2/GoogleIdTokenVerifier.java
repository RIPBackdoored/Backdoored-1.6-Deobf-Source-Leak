package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import java.security.*;
import java.io.*;
import com.google.api.client.auth.openidconnect.*;
import java.util.*;
import com.google.api.client.util.*;

@Beta
public class GoogleIdTokenVerifier extends IdTokenVerifier
{
    private final GooglePublicKeysManager publicKeys;
    
    public GoogleIdTokenVerifier(final HttpTransport transport, final JsonFactory jsonFactory) {
        this(new Builder(transport, jsonFactory));
    }
    
    public GoogleIdTokenVerifier(final GooglePublicKeysManager publicKeys) {
        this(new Builder(publicKeys));
    }
    
    protected GoogleIdTokenVerifier(final Builder builder) {
        super(builder);
        this.publicKeys = builder.publicKeys;
    }
    
    public final GooglePublicKeysManager getPublicKeysManager() {
        return this.publicKeys;
    }
    
    public final HttpTransport getTransport() {
        return this.publicKeys.getTransport();
    }
    
    public final JsonFactory getJsonFactory() {
        return this.publicKeys.getJsonFactory();
    }
    
    @Deprecated
    public final String getPublicCertsEncodedUrl() {
        return this.publicKeys.getPublicCertsEncodedUrl();
    }
    
    @Deprecated
    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        return this.publicKeys.getPublicKeys();
    }
    
    @Deprecated
    public final long getExpirationTimeMilliseconds() {
        return this.publicKeys.getExpirationTimeMilliseconds();
    }
    
    public boolean verify(final GoogleIdToken googleIdToken) throws GeneralSecurityException, IOException {
        if (!super.verify(googleIdToken)) {
            return false;
        }
        for (final PublicKey publicKey : this.publicKeys.getPublicKeys()) {
            if (googleIdToken.verifySignature(publicKey)) {
                return true;
            }
        }
        return false;
    }
    
    public GoogleIdToken verify(final String idTokenString) throws GeneralSecurityException, IOException {
        final GoogleIdToken idToken = GoogleIdToken.parse(this.getJsonFactory(), idTokenString);
        return this.verify(idToken) ? idToken : null;
    }
    
    @Deprecated
    public GoogleIdTokenVerifier loadPublicCerts() throws GeneralSecurityException, IOException {
        this.publicKeys.refresh();
        return this;
    }
    
    @Beta
    public static class Builder extends IdTokenVerifier.Builder
    {
        GooglePublicKeysManager publicKeys;
        
        public Builder(final HttpTransport transport, final JsonFactory jsonFactory) {
            this(new GooglePublicKeysManager(transport, jsonFactory));
        }
        
        public Builder(final GooglePublicKeysManager publicKeys) {
            super();
            this.publicKeys = Preconditions.checkNotNull(publicKeys);
            this.setIssuers(Arrays.asList("accounts.google.com", "https://accounts.google.com"));
        }
        
        @Override
        public GoogleIdTokenVerifier build() {
            return new GoogleIdTokenVerifier(this);
        }
        
        public final GooglePublicKeysManager getPublicCerts() {
            return this.publicKeys;
        }
        
        public final HttpTransport getTransport() {
            return this.publicKeys.getTransport();
        }
        
        public final JsonFactory getJsonFactory() {
            return this.publicKeys.getJsonFactory();
        }
        
        @Deprecated
        public final String getPublicCertsEncodedUrl() {
            return this.publicKeys.getPublicCertsEncodedUrl();
        }
        
        @Deprecated
        public Builder setPublicCertsEncodedUrl(final String publicKeysEncodedUrl) {
            this.publicKeys = new GooglePublicKeysManager.Builder(this.getTransport(), this.getJsonFactory()).setPublicCertsEncodedUrl(publicKeysEncodedUrl).setClock(this.publicKeys.getClock()).build();
            return this;
        }
        
        @Override
        public Builder setIssuer(final String issuer) {
            return (Builder)super.setIssuer(issuer);
        }
        
        @Override
        public Builder setIssuers(final Collection<String> issuers) {
            return (Builder)super.setIssuers(issuers);
        }
        
        @Override
        public Builder setAudience(final Collection<String> audience) {
            return (Builder)super.setAudience(audience);
        }
        
        @Override
        public Builder setAcceptableTimeSkewSeconds(final long acceptableTimeSkewSeconds) {
            return (Builder)super.setAcceptableTimeSkewSeconds(acceptableTimeSkewSeconds);
        }
        
        @Override
        public Builder setClock(final Clock clock) {
            return (Builder)super.setClock(clock);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setAcceptableTimeSkewSeconds(final long acceptableTimeSkewSeconds) {
            return this.setAcceptableTimeSkewSeconds(acceptableTimeSkewSeconds);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setAudience(final Collection audience) {
            return this.setAudience(audience);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setIssuers(final Collection issuers) {
            return this.setIssuers(issuers);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setIssuer(final String issuer) {
            return this.setIssuer(issuer);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier.Builder setClock(final Clock clock) {
            return this.setClock(clock);
        }
        
        @Override
        public /* bridge */ IdTokenVerifier build() {
            return this.build();
        }
    }
}
