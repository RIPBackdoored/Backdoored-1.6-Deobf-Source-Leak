package com.google.api.client.http.javanet;

import java.net.*;
import java.security.*;
import java.io.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;

public static final class Builder
{
    private SSLSocketFactory sslSocketFactory;
    private HostnameVerifier hostnameVerifier;
    private Proxy proxy;
    private ConnectionFactory connectionFactory;
    
    public Builder() {
        super();
    }
    
    public Builder setProxy(final Proxy proxy) {
        this.proxy = proxy;
        return this;
    }
    
    public Builder setConnectionFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        return this;
    }
    
    public Builder trustCertificatesFromJavaKeyStore(final InputStream keyStoreStream, final String storePass) throws GeneralSecurityException, IOException {
        final KeyStore trustStore = SecurityUtils.getJavaKeyStore();
        SecurityUtils.loadKeyStore(trustStore, keyStoreStream, storePass);
        return this.trustCertificates(trustStore);
    }
    
    public Builder trustCertificatesFromStream(final InputStream certificateStream) throws GeneralSecurityException, IOException {
        final KeyStore trustStore = SecurityUtils.getJavaKeyStore();
        trustStore.load(null, null);
        SecurityUtils.loadKeyStoreFromCertificates(trustStore, SecurityUtils.getX509CertificateFactory(), certificateStream);
        return this.trustCertificates(trustStore);
    }
    
    public Builder trustCertificates(final KeyStore trustStore) throws GeneralSecurityException {
        final SSLContext sslContext = SslUtils.getTlsSslContext();
        SslUtils.initSslContext(sslContext, trustStore, SslUtils.getPkixTrustManagerFactory());
        return this.setSslSocketFactory(sslContext.getSocketFactory());
    }
    
    @Beta
    public Builder doNotValidateCertificate() throws GeneralSecurityException {
        this.hostnameVerifier = SslUtils.trustAllHostnameVerifier();
        this.sslSocketFactory = SslUtils.trustAllSSLContext().getSocketFactory();
        return this;
    }
    
    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }
    
    public Builder setSslSocketFactory(final SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }
    
    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }
    
    public Builder setHostnameVerifier(final HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }
    
    public NetHttpTransport build() {
        if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            this.setProxy(NetHttpTransport.access$000());
        }
        return (this.proxy == null) ? new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier) : new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
    }
}
