package com.google.api.client.auth.openidconnect;

import com.google.api.client.json.webtoken.*;
import java.io.*;
import java.util.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

@Beta
public class IdToken extends JsonWebSignature
{
    public IdToken(final Header header, final Payload payload, final byte[] signatureBytes, final byte[] signedContentBytes) {
        super(header, payload, signatureBytes, signedContentBytes);
    }
    
    @Override
    public Payload getPayload() {
        return (Payload)super.getPayload();
    }
    
    public final boolean verifyIssuer(final String expectedIssuer) {
        return this.verifyIssuer(Collections.singleton(expectedIssuer));
    }
    
    public final boolean verifyIssuer(final Collection<String> expectedIssuer) {
        return expectedIssuer.contains(this.getPayload().getIssuer());
    }
    
    public final boolean verifyAudience(final Collection<String> trustedClientIds) {
        final Collection<String> audience = this.getPayload().getAudienceAsList();
        return !audience.isEmpty() && trustedClientIds.containsAll(audience);
    }
    
    public final boolean verifyTime(final long currentTimeMillis, final long acceptableTimeSkewSeconds) {
        return this.verifyExpirationTime(currentTimeMillis, acceptableTimeSkewSeconds) && this.verifyIssuedAtTime(currentTimeMillis, acceptableTimeSkewSeconds);
    }
    
    public final boolean verifyExpirationTime(final long currentTimeMillis, final long acceptableTimeSkewSeconds) {
        return currentTimeMillis <= (this.getPayload().getExpirationTimeSeconds() + acceptableTimeSkewSeconds) * 1000L;
    }
    
    public final boolean verifyIssuedAtTime(final long currentTimeMillis, final long acceptableTimeSkewSeconds) {
        return currentTimeMillis >= (this.getPayload().getIssuedAtTimeSeconds() - acceptableTimeSkewSeconds) * 1000L;
    }
    
    public static IdToken parse(final JsonFactory jsonFactory, final String idTokenString) throws IOException {
        final JsonWebSignature jws = JsonWebSignature.parser(jsonFactory).setPayloadClass(Payload.class).parse(idTokenString);
        return new IdToken(jws.getHeader(), (Payload)jws.getPayload(), jws.getSignatureBytes(), jws.getSignedContentBytes());
    }
    
    @Override
    public /* bridge */ JsonWebToken.Payload getPayload() {
        return this.getPayload();
    }
    
    @Beta
    public static class Payload extends JsonWebToken.Payload
    {
        @Key("auth_time")
        private Long authorizationTimeSeconds;
        @Key("azp")
        private String authorizedParty;
        @Key
        private String nonce;
        @Key("at_hash")
        private String accessTokenHash;
        @Key("acr")
        private String classReference;
        @Key("amr")
        private List<String> methodsReferences;
        
        public Payload() {
            super();
        }
        
        public final Long getAuthorizationTimeSeconds() {
            return this.authorizationTimeSeconds;
        }
        
        public Payload setAuthorizationTimeSeconds(final Long authorizationTimeSeconds) {
            this.authorizationTimeSeconds = authorizationTimeSeconds;
            return this;
        }
        
        public final String getAuthorizedParty() {
            return this.authorizedParty;
        }
        
        public Payload setAuthorizedParty(final String authorizedParty) {
            this.authorizedParty = authorizedParty;
            return this;
        }
        
        public final String getNonce() {
            return this.nonce;
        }
        
        public Payload setNonce(final String nonce) {
            this.nonce = nonce;
            return this;
        }
        
        public final String getAccessTokenHash() {
            return this.accessTokenHash;
        }
        
        public Payload setAccessTokenHash(final String accessTokenHash) {
            this.accessTokenHash = accessTokenHash;
            return this;
        }
        
        public final String getClassReference() {
            return this.classReference;
        }
        
        public Payload setClassReference(final String classReference) {
            this.classReference = classReference;
            return this;
        }
        
        public final List<String> getMethodsReferences() {
            return this.methodsReferences;
        }
        
        public Payload setMethodsReferences(final List<String> methodsReferences) {
            this.methodsReferences = methodsReferences;
            return this;
        }
        
        @Override
        public Payload setExpirationTimeSeconds(final Long expirationTimeSeconds) {
            return (Payload)super.setExpirationTimeSeconds(expirationTimeSeconds);
        }
        
        @Override
        public Payload setNotBeforeTimeSeconds(final Long notBeforeTimeSeconds) {
            return (Payload)super.setNotBeforeTimeSeconds(notBeforeTimeSeconds);
        }
        
        @Override
        public Payload setIssuedAtTimeSeconds(final Long issuedAtTimeSeconds) {
            return (Payload)super.setIssuedAtTimeSeconds(issuedAtTimeSeconds);
        }
        
        @Override
        public Payload setIssuer(final String issuer) {
            return (Payload)super.setIssuer(issuer);
        }
        
        @Override
        public Payload setAudience(final Object audience) {
            return (Payload)super.setAudience(audience);
        }
        
        @Override
        public Payload setJwtId(final String jwtId) {
            return (Payload)super.setJwtId(jwtId);
        }
        
        @Override
        public Payload setType(final String type) {
            return (Payload)super.setType(type);
        }
        
        @Override
        public Payload setSubject(final String subject) {
            return (Payload)super.setSubject(subject);
        }
        
        @Override
        public Payload set(final String fieldName, final Object value) {
            return (Payload)super.set(fieldName, value);
        }
        
        @Override
        public Payload clone() {
            return (Payload)super.clone();
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload clone() {
            return this.clone();
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload set(final String fieldName, final Object value) {
            return this.set(fieldName, value);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setSubject(final String subject) {
            return this.setSubject(subject);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setType(final String type) {
            return this.setType(type);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setJwtId(final String jwtId) {
            return this.setJwtId(jwtId);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setAudience(final Object audience) {
            return this.setAudience(audience);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setIssuer(final String issuer) {
            return this.setIssuer(issuer);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setIssuedAtTimeSeconds(final Long issuedAtTimeSeconds) {
            return this.setIssuedAtTimeSeconds(issuedAtTimeSeconds);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setNotBeforeTimeSeconds(final Long notBeforeTimeSeconds) {
            return this.setNotBeforeTimeSeconds(notBeforeTimeSeconds);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Payload setExpirationTimeSeconds(final Long expirationTimeSeconds) {
            return this.setExpirationTimeSeconds(expirationTimeSeconds);
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
