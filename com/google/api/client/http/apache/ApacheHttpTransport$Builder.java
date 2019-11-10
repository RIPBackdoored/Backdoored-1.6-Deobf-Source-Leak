package com.google.api.client.http.apache;

import org.apache.http.conn.ssl.*;
import org.apache.http.params.*;
import java.net.*;
import org.apache.http.*;
import org.apache.http.conn.params.*;
import java.security.*;
import java.io.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;
import org.apache.http.client.*;

public static final class Builder
{
    private SSLSocketFactory socketFactory;
    private HttpParams params;
    private ProxySelector proxySelector;
    
    public Builder() {
        super();
        this.socketFactory = SSLSocketFactory.getSocketFactory();
        this.params = ApacheHttpTransport.newDefaultHttpParams();
        this.proxySelector = ProxySelector.getDefault();
    }
    
    public Builder setProxy(final HttpHost proxy) {
        ConnRouteParams.setDefaultProxy(this.params, proxy);
        if (proxy != null) {
            this.proxySelector = null;
        }
        return this;
    }
    
    public Builder setProxySelector(final ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        if (proxySelector != null) {
            ConnRouteParams.setDefaultProxy(this.params, (HttpHost)null);
        }
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
        return this.setSocketFactory(new SSLSocketFactoryExtension(sslContext));
    }
    
    @Beta
    public Builder doNotValidateCertificate() throws GeneralSecurityException {
        (this.socketFactory = new SSLSocketFactoryExtension(SslUtils.trustAllSSLContext())).setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return this;
    }
    
    public Builder setSocketFactory(final SSLSocketFactory socketFactory) {
        this.socketFactory = Preconditions.checkNotNull(socketFactory);
        return this;
    }
    
    public SSLSocketFactory getSSLSocketFactory() {
        return this.socketFactory;
    }
    
    public HttpParams getHttpParams() {
        return this.params;
    }
    
    public ApacheHttpTransport build() {
        return new ApacheHttpTransport((HttpClient)ApacheHttpTransport.newDefaultHttpClient(this.socketFactory, this.params, this.proxySelector));
    }
}
