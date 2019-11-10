package com.google.api.client.googleapis.auth.oauth2;

import java.util.concurrent.locks.*;
import java.security.*;
import com.google.api.client.util.*;
import java.io.*;
import java.util.*;
import java.security.cert.*;
import com.google.api.client.json.*;
import com.google.api.client.http.*;
import java.util.regex.*;

@Beta
public class GooglePublicKeysManager
{
    private static final long REFRESH_SKEW_MILLIS = 300000L;
    private static final Pattern MAX_AGE_PATTERN;
    private final JsonFactory jsonFactory;
    private List<PublicKey> publicKeys;
    private long expirationTimeMilliseconds;
    private final HttpTransport transport;
    private final Lock lock;
    private final Clock clock;
    private final String publicCertsEncodedUrl;
    
    public GooglePublicKeysManager(final HttpTransport transport, final JsonFactory jsonFactory) {
        this(new Builder(transport, jsonFactory));
    }
    
    protected GooglePublicKeysManager(final Builder builder) {
        super();
        this.lock = new ReentrantLock();
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.clock = builder.clock;
        this.publicCertsEncodedUrl = builder.publicCertsEncodedUrl;
    }
    
    public final HttpTransport getTransport() {
        return this.transport;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getPublicCertsEncodedUrl() {
        return this.publicCertsEncodedUrl;
    }
    
    public final Clock getClock() {
        return this.clock;
    }
    
    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        this.lock.lock();
        try {
            if (this.publicKeys == null || this.clock.currentTimeMillis() + 300000L > this.expirationTimeMilliseconds) {
                this.refresh();
            }
            return this.publicKeys;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    public final long getExpirationTimeMilliseconds() {
        return this.expirationTimeMilliseconds;
    }
    
    public GooglePublicKeysManager refresh() throws GeneralSecurityException, IOException {
        this.lock.lock();
        try {
            this.publicKeys = new ArrayList<PublicKey>();
            final CertificateFactory factory = SecurityUtils.getX509CertificateFactory();
            final HttpResponse certsResponse = this.transport.createRequestFactory().buildGetRequest(new GenericUrl(this.publicCertsEncodedUrl)).execute();
            this.expirationTimeMilliseconds = this.clock.currentTimeMillis() + this.getCacheTimeInSec(certsResponse.getHeaders()) * 1000L;
            final JsonParser parser = this.jsonFactory.createJsonParser(certsResponse.getContent());
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == null) {
                currentToken = parser.nextToken();
            }
            Preconditions.checkArgument(currentToken == JsonToken.START_OBJECT);
            try {
                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    parser.nextToken();
                    final String certValue = parser.getText();
                    final X509Certificate x509Cert = (X509Certificate)factory.generateCertificate(new ByteArrayInputStream(StringUtils.getBytesUtf8(certValue)));
                    this.publicKeys.add(x509Cert.getPublicKey());
                }
                this.publicKeys = Collections.unmodifiableList((List<? extends PublicKey>)this.publicKeys);
            }
            finally {
                parser.close();
            }
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }
    
    long getCacheTimeInSec(final HttpHeaders httpHeaders) {
        long cacheTimeInSec = 0L;
        if (httpHeaders.getCacheControl() != null) {
            for (final String arg : httpHeaders.getCacheControl().split(",")) {
                final Matcher m = GooglePublicKeysManager.MAX_AGE_PATTERN.matcher(arg);
                if (m.matches()) {
                    cacheTimeInSec = Long.valueOf(m.group(1));
                    break;
                }
            }
        }
        if (httpHeaders.getAge() != null) {
            cacheTimeInSec -= httpHeaders.getAge();
        }
        return Math.max(0L, cacheTimeInSec);
    }
    
    static {
        MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
    }
    
    @Beta
    public static class Builder
    {
        Clock clock;
        final HttpTransport transport;
        final JsonFactory jsonFactory;
        String publicCertsEncodedUrl;
        
        public Builder(final HttpTransport transport, final JsonFactory jsonFactory) {
            super();
            this.clock = Clock.SYSTEM;
            this.publicCertsEncodedUrl = "https://www.googleapis.com/oauth2/v1/certs";
            this.transport = Preconditions.checkNotNull(transport);
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }
        
        public GooglePublicKeysManager build() {
            return new GooglePublicKeysManager(this);
        }
        
        public final HttpTransport getTransport() {
            return this.transport;
        }
        
        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public final String getPublicCertsEncodedUrl() {
            return this.publicCertsEncodedUrl;
        }
        
        public Builder setPublicCertsEncodedUrl(final String publicCertsEncodedUrl) {
            this.publicCertsEncodedUrl = Preconditions.checkNotNull(publicCertsEncodedUrl);
            return this;
        }
        
        public final Clock getClock() {
            return this.clock;
        }
        
        public Builder setClock(final Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }
    }
}
