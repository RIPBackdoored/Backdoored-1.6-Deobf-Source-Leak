package com.google.api.client.json.webtoken;

import com.google.api.client.json.*;
import com.google.api.client.util.*;
import java.util.*;

public class JsonWebToken
{
    private final Header header;
    private final Payload payload;
    
    public JsonWebToken(final Header header, final Payload payload) {
        super();
        this.header = Preconditions.checkNotNull(header);
        this.payload = Preconditions.checkNotNull(payload);
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("header", this.header).add("payload", this.payload).toString();
    }
    
    public Header getHeader() {
        return this.header;
    }
    
    public Payload getPayload() {
        return this.payload;
    }
    
    public static class Header extends GenericJson
    {
        @Key("typ")
        private String type;
        @Key("cty")
        private String contentType;
        
        public Header() {
            super();
        }
        
        public final String getType() {
            return this.type;
        }
        
        public Header setType(final String type) {
            this.type = type;
            return this;
        }
        
        public final String getContentType() {
            return this.contentType;
        }
        
        public Header setContentType(final String contentType) {
            this.contentType = contentType;
            return this;
        }
        
        @Override
        public Header set(final String fieldName, final Object value) {
            return (Header)super.set(fieldName, value);
        }
        
        @Override
        public Header clone() {
            return (Header)super.clone();
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
    
    public static class Payload extends GenericJson
    {
        @Key("exp")
        private Long expirationTimeSeconds;
        @Key("nbf")
        private Long notBeforeTimeSeconds;
        @Key("iat")
        private Long issuedAtTimeSeconds;
        @Key("iss")
        private String issuer;
        @Key("aud")
        private Object audience;
        @Key("jti")
        private String jwtId;
        @Key("typ")
        private String type;
        @Key("sub")
        private String subject;
        
        public Payload() {
            super();
        }
        
        public final Long getExpirationTimeSeconds() {
            return this.expirationTimeSeconds;
        }
        
        public Payload setExpirationTimeSeconds(final Long expirationTimeSeconds) {
            this.expirationTimeSeconds = expirationTimeSeconds;
            return this;
        }
        
        public final Long getNotBeforeTimeSeconds() {
            return this.notBeforeTimeSeconds;
        }
        
        public Payload setNotBeforeTimeSeconds(final Long notBeforeTimeSeconds) {
            this.notBeforeTimeSeconds = notBeforeTimeSeconds;
            return this;
        }
        
        public final Long getIssuedAtTimeSeconds() {
            return this.issuedAtTimeSeconds;
        }
        
        public Payload setIssuedAtTimeSeconds(final Long issuedAtTimeSeconds) {
            this.issuedAtTimeSeconds = issuedAtTimeSeconds;
            return this;
        }
        
        public final String getIssuer() {
            return this.issuer;
        }
        
        public Payload setIssuer(final String issuer) {
            this.issuer = issuer;
            return this;
        }
        
        public final Object getAudience() {
            return this.audience;
        }
        
        public final List<String> getAudienceAsList() {
            if (this.audience == null) {
                return Collections.emptyList();
            }
            if (this.audience instanceof String) {
                return Collections.singletonList(this.audience);
            }
            return (List<String>)this.audience;
        }
        
        public Payload setAudience(final Object audience) {
            this.audience = audience;
            return this;
        }
        
        public final String getJwtId() {
            return this.jwtId;
        }
        
        public Payload setJwtId(final String jwtId) {
            this.jwtId = jwtId;
            return this;
        }
        
        public final String getType() {
            return this.type;
        }
        
        public Payload setType(final String type) {
            this.type = type;
            return this;
        }
        
        public final String getSubject() {
            return this.subject;
        }
        
        public Payload setSubject(final String subject) {
            this.subject = subject;
            return this;
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
