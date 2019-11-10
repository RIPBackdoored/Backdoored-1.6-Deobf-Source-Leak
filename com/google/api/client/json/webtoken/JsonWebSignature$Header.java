package com.google.api.client.json.webtoken;

import java.util.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;

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
