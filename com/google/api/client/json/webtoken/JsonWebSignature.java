package com.google.api.client.json.webtoken;

import java.security.cert.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import java.io.*;

public class JsonWebSignature extends JsonWebToken
{
    private final byte[] signatureBytes;
    private final byte[] signedContentBytes;
    
    public JsonWebSignature(final Header header, final Payload payload, final byte[] signatureBytes, final byte[] signedContentBytes) {
        super(header, payload);
        this.signatureBytes = Preconditions.checkNotNull(signatureBytes);
        this.signedContentBytes = Preconditions.checkNotNull(signedContentBytes);
    }
    
    @Override
    public Header getHeader() {
        return (Header)super.getHeader();
    }
    
    public final boolean verifySignature(final PublicKey publicKey) throws GeneralSecurityException {
        Signature signatureAlg = null;
        final String algorithm = this.getHeader().getAlgorithm();
        if ("RS256".equals(algorithm)) {
            signatureAlg = SecurityUtils.getSha256WithRsaSignatureAlgorithm();
            return SecurityUtils.verify(signatureAlg, publicKey, this.signatureBytes, this.signedContentBytes);
        }
        return false;
    }
    
    @Beta
    public final X509Certificate verifySignature(final X509TrustManager trustManager) throws GeneralSecurityException {
        final List<String> x509Certificates = this.getHeader().getX509Certificates();
        if (x509Certificates == null || x509Certificates.isEmpty()) {
            return null;
        }
        final String algorithm = this.getHeader().getAlgorithm();
        Signature signatureAlg = null;
        if ("RS256".equals(algorithm)) {
            signatureAlg = SecurityUtils.getSha256WithRsaSignatureAlgorithm();
            return SecurityUtils.verify(signatureAlg, trustManager, x509Certificates, this.signatureBytes, this.signedContentBytes);
        }
        return null;
    }
    
    @Beta
    public final X509Certificate verifySignature() throws GeneralSecurityException {
        final X509TrustManager trustManager = getDefaultX509TrustManager();
        if (trustManager == null) {
            return null;
        }
        return this.verifySignature(trustManager);
    }
    
    private static X509TrustManager getDefaultX509TrustManager() {
        try {
            final TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init((KeyStore)null);
            for (final TrustManager manager : factory.getTrustManagers()) {
                if (manager instanceof X509TrustManager) {
                    return (X509TrustManager)manager;
                }
            }
            return null;
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
        catch (KeyStoreException e2) {
            return null;
        }
    }
    
    public final byte[] getSignatureBytes() {
        return this.signatureBytes;
    }
    
    public final byte[] getSignedContentBytes() {
        return this.signedContentBytes;
    }
    
    public static JsonWebSignature parse(final JsonFactory jsonFactory, final String tokenString) throws IOException {
        return parser(jsonFactory).parse(tokenString);
    }
    
    public static Parser parser(final JsonFactory jsonFactory) {
        return new Parser(jsonFactory);
    }
    
    public static String signUsingRsaSha256(final PrivateKey privateKey, final JsonFactory jsonFactory, final Header header, final Payload payload) throws GeneralSecurityException, IOException {
        final String content = Base64.encodeBase64URLSafeString(jsonFactory.toByteArray(header)) + "." + Base64.encodeBase64URLSafeString(jsonFactory.toByteArray(payload));
        final byte[] contentBytes = StringUtils.getBytesUtf8(content);
        final byte[] signature = SecurityUtils.sign(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), privateKey, contentBytes);
        return content + "." + Base64.encodeBase64URLSafeString(signature);
    }
    
    @Override
    public /* bridge */ JsonWebToken.Header getHeader() {
        return this.getHeader();
    }
    
    public static class Header extends JsonWebToken.Header
    {
        @Key("alg")
        private String algorithm;
        @Key("jku")
        private String jwkUrl;
        @Key("jwk")
        private String jwk;
        @Key("kid")
        private String keyId;
        @Key("x5u")
        private String x509Url;
        @Key("x5t")
        private String x509Thumbprint;
        @Key("x5c")
        private List<String> x509Certificates;
        @Key("crit")
        private List<String> critical;
        
        public Header() {
            super();
        }
        
        @Override
        public Header setType(final String type) {
            super.setType(type);
            return this;
        }
        
        public final String getAlgorithm() {
            return this.algorithm;
        }
        
        public Header setAlgorithm(final String algorithm) {
            this.algorithm = algorithm;
            return this;
        }
        
        public final String getJwkUrl() {
            return this.jwkUrl;
        }
        
        public Header setJwkUrl(final String jwkUrl) {
            this.jwkUrl = jwkUrl;
            return this;
        }
        
        public final String getJwk() {
            return this.jwk;
        }
        
        public Header setJwk(final String jwk) {
            this.jwk = jwk;
            return this;
        }
        
        public final String getKeyId() {
            return this.keyId;
        }
        
        public Header setKeyId(final String keyId) {
            this.keyId = keyId;
            return this;
        }
        
        public final String getX509Url() {
            return this.x509Url;
        }
        
        public Header setX509Url(final String x509Url) {
            this.x509Url = x509Url;
            return this;
        }
        
        public final String getX509Thumbprint() {
            return this.x509Thumbprint;
        }
        
        public Header setX509Thumbprint(final String x509Thumbprint) {
            this.x509Thumbprint = x509Thumbprint;
            return this;
        }
        
        @Deprecated
        public final String getX509Certificate() {
            if (this.x509Certificates == null || this.x509Certificates.isEmpty()) {
                return null;
            }
            return this.x509Certificates.get(0);
        }
        
        public final List<String> getX509Certificates() {
            return this.x509Certificates;
        }
        
        @Deprecated
        public Header setX509Certificate(final String x509Certificate) {
            final ArrayList<String> x509Certificates = new ArrayList<String>();
            x509Certificates.add(x509Certificate);
            this.x509Certificates = x509Certificates;
            return this;
        }
        
        public Header setX509Certificates(final List<String> x509Certificates) {
            this.x509Certificates = x509Certificates;
            return this;
        }
        
        public final List<String> getCritical() {
            return this.critical;
        }
        
        public Header setCritical(final List<String> critical) {
            this.critical = critical;
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
        public /* bridge */ JsonWebToken.Header clone() {
            return this.clone();
        }
        
        @Override
        public /* bridge */ JsonWebToken.Header set(final String fieldName, final Object value) {
            return this.set(fieldName, value);
        }
        
        @Override
        public /* bridge */ JsonWebToken.Header setType(final String type) {
            return this.setType(type);
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
    
    public static final class Parser
    {
        private final JsonFactory jsonFactory;
        private Class<? extends Header> headerClass;
        private Class<? extends Payload> payloadClass;
        
        public Parser(final JsonFactory jsonFactory) {
            super();
            this.headerClass = Header.class;
            this.payloadClass = Payload.class;
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }
        
        public Class<? extends Header> getHeaderClass() {
            return this.headerClass;
        }
        
        public Parser setHeaderClass(final Class<? extends Header> headerClass) {
            this.headerClass = headerClass;
            return this;
        }
        
        public Class<? extends Payload> getPayloadClass() {
            return this.payloadClass;
        }
        
        public Parser setPayloadClass(final Class<? extends Payload> payloadClass) {
            this.payloadClass = payloadClass;
            return this;
        }
        
        public JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public JsonWebSignature parse(final String tokenString) throws IOException {
            final int firstDot = tokenString.indexOf(46);
            Preconditions.checkArgument(firstDot != -1);
            final byte[] headerBytes = Base64.decodeBase64(tokenString.substring(0, firstDot));
            final int secondDot = tokenString.indexOf(46, firstDot + 1);
            Preconditions.checkArgument(secondDot != -1);
            Preconditions.checkArgument(tokenString.indexOf(46, secondDot + 1) == -1);
            final byte[] payloadBytes = Base64.decodeBase64(tokenString.substring(firstDot + 1, secondDot));
            final byte[] signatureBytes = Base64.decodeBase64(tokenString.substring(secondDot + 1));
            final byte[] signedContentBytes = StringUtils.getBytesUtf8(tokenString.substring(0, secondDot));
            final Header header = this.jsonFactory.fromInputStream(new ByteArrayInputStream(headerBytes), this.headerClass);
            Preconditions.checkArgument(header.getAlgorithm() != null);
            final Payload payload = this.jsonFactory.fromInputStream(new ByteArrayInputStream(payloadBytes), this.payloadClass);
            return new JsonWebSignature(header, payload, signatureBytes, signedContentBytes);
        }
    }
}
