package com.google.cloud.storage;

import java.net.*;
import com.google.common.collect.*;
import java.text.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import com.google.common.net.*;
import com.google.common.base.*;
import java.util.*;

public class SignatureInfo
{
    public static final char COMPONENT_SEPARATOR = '\n';
    public static final String GOOG4_RSA_SHA256 = "GOOG4-RSA-SHA256";
    public static final String SCOPE = "/auto/storage/goog4_request";
    private final HttpMethod httpVerb;
    private final String contentMd5;
    private final String contentType;
    private final long expiration;
    private final Map<String, String> canonicalizedExtensionHeaders;
    private final URI canonicalizedResource;
    private final Storage.SignUrlOption.SignatureVersion signatureVersion;
    private final String accountEmail;
    private final long timestamp;
    private final String yearMonthDay;
    private final String exactDate;
    
    private SignatureInfo(final Builder builder) {
        super();
        this.httpVerb = builder.httpVerb;
        this.contentMd5 = builder.contentMd5;
        this.contentType = builder.contentType;
        this.expiration = builder.expiration;
        this.canonicalizedResource = builder.canonicalizedResource;
        this.signatureVersion = builder.signatureVersion;
        this.accountEmail = builder.accountEmail;
        this.timestamp = builder.timestamp;
        if (Storage.SignUrlOption.SignatureVersion.V4.equals(this.signatureVersion) && !builder.canonicalizedExtensionHeaders.containsKey("host")) {
            this.canonicalizedExtensionHeaders = (Map<String, String>)new ImmutableMap.Builder().putAll(builder.canonicalizedExtensionHeaders).put((Object)"host", (Object)"storage.googleapis.com").build();
        }
        else {
            this.canonicalizedExtensionHeaders = builder.canonicalizedExtensionHeaders;
        }
        final Date date = new Date(this.timestamp);
        final SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat("yyyyMMdd");
        final SimpleDateFormat exactDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        yearMonthDayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        exactDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.yearMonthDay = yearMonthDayFormat.format(date);
        this.exactDate = exactDateFormat.format(date);
    }
    
    public String constructUnsignedPayload() {
        if (Storage.SignUrlOption.SignatureVersion.V4.equals(this.signatureVersion)) {
            return this.constructV4UnsignedPayload();
        }
        return this.constructV2UnsignedPayload();
    }
    
    private String constructV2UnsignedPayload() {
        final StringBuilder payload = new StringBuilder();
        payload.append(this.httpVerb.name()).append('\n');
        if (this.contentMd5 != null) {
            payload.append(this.contentMd5);
        }
        payload.append('\n');
        if (this.contentType != null) {
            payload.append(this.contentType);
        }
        payload.append('\n');
        payload.append(this.expiration).append('\n');
        if (this.canonicalizedExtensionHeaders != null) {
            payload.append((CharSequence)new CanonicalExtensionHeadersSerializer(Storage.SignUrlOption.SignatureVersion.V2).serialize(this.canonicalizedExtensionHeaders));
        }
        payload.append(this.canonicalizedResource);
        return payload.toString();
    }
    
    private String constructV4UnsignedPayload() {
        final StringBuilder payload = new StringBuilder();
        payload.append("GOOG4-RSA-SHA256").append('\n');
        payload.append(this.exactDate).append('\n');
        payload.append(this.yearMonthDay).append("/auto/storage/goog4_request").append('\n');
        payload.append(this.constructV4CanonicalRequestHash());
        return payload.toString();
    }
    
    private String constructV4CanonicalRequestHash() {
        final StringBuilder canonicalRequest = new StringBuilder();
        final CanonicalExtensionHeadersSerializer serializer = new CanonicalExtensionHeadersSerializer(Storage.SignUrlOption.SignatureVersion.V4);
        canonicalRequest.append(this.httpVerb.name()).append('\n');
        canonicalRequest.append(this.canonicalizedResource).append('\n');
        canonicalRequest.append(this.constructV4QueryString()).append('\n');
        canonicalRequest.append((CharSequence)serializer.serialize(this.canonicalizedExtensionHeaders)).append('\n');
        canonicalRequest.append((CharSequence)serializer.serializeHeaderNames(this.canonicalizedExtensionHeaders)).append('\n');
        canonicalRequest.append("UNSIGNED-PAYLOAD");
        return Hashing.sha256().hashString((CharSequence)canonicalRequest.toString(), StandardCharsets.UTF_8).toString();
    }
    
    public String constructV4QueryString() {
        final StringBuilder signedHeaders = new CanonicalExtensionHeadersSerializer(Storage.SignUrlOption.SignatureVersion.V4).serializeHeaderNames(this.canonicalizedExtensionHeaders);
        final StringBuilder queryString = new StringBuilder();
        queryString.append("X-Goog-Algorithm=").append("GOOG4-RSA-SHA256").append("&");
        queryString.append("X-Goog-Credential=" + UrlEscapers.urlFormParameterEscaper().escape(this.accountEmail + "/" + this.yearMonthDay + "/auto/storage/goog4_request") + "&");
        queryString.append("X-Goog-Date=" + this.exactDate + "&");
        queryString.append("X-Goog-Expires=" + this.expiration + "&");
        queryString.append("X-Goog-SignedHeaders=" + UrlEscapers.urlFormParameterEscaper().escape(signedHeaders.toString()));
        return queryString.toString();
    }
    
    public HttpMethod getHttpVerb() {
        return this.httpVerb;
    }
    
    public String getContentMd5() {
        return this.contentMd5;
    }
    
    public String getContentType() {
        return this.contentType;
    }
    
    public long getExpiration() {
        return this.expiration;
    }
    
    public Map<String, String> getCanonicalizedExtensionHeaders() {
        return this.canonicalizedExtensionHeaders;
    }
    
    public URI getCanonicalizedResource() {
        return this.canonicalizedResource;
    }
    
    public Storage.SignUrlOption.SignatureVersion getSignatureVersion() {
        return this.signatureVersion;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public String getAccountEmail() {
        return this.accountEmail;
    }
    
    static /* synthetic */ HttpMethod access$900(final SignatureInfo x0) {
        return x0.httpVerb;
    }
    
    static /* synthetic */ String access$1000(final SignatureInfo x0) {
        return x0.contentMd5;
    }
    
    static /* synthetic */ String access$1100(final SignatureInfo x0) {
        return x0.contentType;
    }
    
    static /* synthetic */ long access$1200(final SignatureInfo x0) {
        return x0.expiration;
    }
    
    static /* synthetic */ Map access$1300(final SignatureInfo x0) {
        return x0.canonicalizedExtensionHeaders;
    }
    
    static /* synthetic */ URI access$1400(final SignatureInfo x0) {
        return x0.canonicalizedResource;
    }
    
    static /* synthetic */ Storage.SignUrlOption.SignatureVersion access$1500(final SignatureInfo x0) {
        return x0.signatureVersion;
    }
    
    static /* synthetic */ String access$1600(final SignatureInfo x0) {
        return x0.accountEmail;
    }
    
    static /* synthetic */ long access$1700(final SignatureInfo x0) {
        return x0.timestamp;
    }
    
    SignatureInfo(final Builder x0, final SignatureInfo$1 x1) {
        this(x0);
    }
    
    public static final class Builder
    {
        private final HttpMethod httpVerb;
        private String contentMd5;
        private String contentType;
        private final long expiration;
        private Map<String, String> canonicalizedExtensionHeaders;
        private final URI canonicalizedResource;
        private Storage.SignUrlOption.SignatureVersion signatureVersion;
        private String accountEmail;
        private long timestamp;
        
        public Builder(final HttpMethod httpVerb, final long expiration, final URI canonicalizedResource) {
            super();
            this.httpVerb = httpVerb;
            this.expiration = expiration;
            this.canonicalizedResource = canonicalizedResource;
        }
        
        public Builder(final SignatureInfo signatureInfo) {
            super();
            this.httpVerb = signatureInfo.httpVerb;
            this.contentMd5 = signatureInfo.contentMd5;
            this.contentType = signatureInfo.contentType;
            this.expiration = signatureInfo.expiration;
            this.canonicalizedExtensionHeaders = signatureInfo.canonicalizedExtensionHeaders;
            this.canonicalizedResource = signatureInfo.canonicalizedResource;
            this.signatureVersion = signatureInfo.signatureVersion;
            this.accountEmail = signatureInfo.accountEmail;
            this.timestamp = signatureInfo.timestamp;
        }
        
        public Builder setContentMd5(final String contentMd5) {
            this.contentMd5 = contentMd5;
            return this;
        }
        
        public Builder setContentType(final String contentType) {
            this.contentType = contentType;
            return this;
        }
        
        public Builder setCanonicalizedExtensionHeaders(final Map<String, String> canonicalizedExtensionHeaders) {
            this.canonicalizedExtensionHeaders = canonicalizedExtensionHeaders;
            return this;
        }
        
        public Builder setSignatureVersion(final Storage.SignUrlOption.SignatureVersion signatureVersion) {
            this.signatureVersion = signatureVersion;
            return this;
        }
        
        public Builder setAccountEmail(final String accountEmail) {
            this.accountEmail = accountEmail;
            return this;
        }
        
        public Builder setTimestamp(final long timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public SignatureInfo build() {
            Preconditions.checkArgument(this.httpVerb != null, (Object)"Required HTTP method");
            Preconditions.checkArgument(this.canonicalizedResource != null, (Object)"Required canonicalized resource");
            Preconditions.checkArgument(this.expiration >= 0L, (Object)"Expiration must be greater than or equal to zero");
            if (Storage.SignUrlOption.SignatureVersion.V4.equals(this.signatureVersion)) {
                Preconditions.checkArgument(this.accountEmail != null, (Object)"Account email required to use V4 signing");
                Preconditions.checkArgument(this.timestamp > 0L, (Object)"Timestamp required to use V4 signing");
                Preconditions.checkArgument(this.expiration <= 604800L, (Object)"Expiration can't be longer than 7 days to use V4 signing");
            }
            if (this.canonicalizedExtensionHeaders == null) {
                this.canonicalizedExtensionHeaders = new HashMap<String, String>();
            }
            return new SignatureInfo(this, null);
        }
        
        static /* synthetic */ HttpMethod access$000(final Builder x0) {
            return x0.httpVerb;
        }
        
        static /* synthetic */ String access$100(final Builder x0) {
            return x0.contentMd5;
        }
        
        static /* synthetic */ String access$200(final Builder x0) {
            return x0.contentType;
        }
        
        static /* synthetic */ long access$300(final Builder x0) {
            return x0.expiration;
        }
        
        static /* synthetic */ URI access$400(final Builder x0) {
            return x0.canonicalizedResource;
        }
        
        static /* synthetic */ Storage.SignUrlOption.SignatureVersion access$500(final Builder x0) {
            return x0.signatureVersion;
        }
        
        static /* synthetic */ String access$600(final Builder x0) {
            return x0.accountEmail;
        }
        
        static /* synthetic */ long access$700(final Builder x0) {
            return x0.timestamp;
        }
        
        static /* synthetic */ Map access$800(final Builder x0) {
            return x0.canonicalizedExtensionHeaders;
        }
    }
}
