package com.google.api.client.http.javanet;

import java.util.*;
import java.net.*;
import com.google.api.client.http.*;
import java.io.*;
import java.security.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;

public final class NetHttpTransport extends HttpTransport
{
    private static final String[] SUPPORTED_METHODS;
    private static final String SHOULD_USE_PROXY_FLAG = "com.google.api.client.should_use_proxy";
    private final ConnectionFactory connectionFactory;
    private final SSLSocketFactory sslSocketFactory;
    private final HostnameVerifier hostnameVerifier;
    
    private static Proxy defaultProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("https.proxyHost"), Integer.parseInt(System.getProperty("https.proxyPort"))));
    }
    
    public NetHttpTransport() {
        this((ConnectionFactory)null, null, null);
    }
    
    NetHttpTransport(final Proxy proxy, final SSLSocketFactory sslSocketFactory, final HostnameVerifier hostnameVerifier) {
        this(new DefaultConnectionFactory(proxy), sslSocketFactory, hostnameVerifier);
    }
    
    NetHttpTransport(final ConnectionFactory connectionFactory, final SSLSocketFactory sslSocketFactory, final HostnameVerifier hostnameVerifier) {
        super();
        this.connectionFactory = this.getConnectionFactory(connectionFactory);
        this.sslSocketFactory = sslSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
    }
    
    private ConnectionFactory getConnectionFactory(final ConnectionFactory connectionFactory) {
        if (connectionFactory != null) {
            return connectionFactory;
        }
        if (System.getProperty("com.google.api.client.should_use_proxy") != null) {
            return new DefaultConnectionFactory(defaultProxy());
        }
        return new DefaultConnectionFactory();
    }
    
    @Override
    public boolean supportsMethod(final String method) {
        return Arrays.binarySearch(NetHttpTransport.SUPPORTED_METHODS, method) >= 0;
    }
    
    @Override
    protected NetHttpRequest buildRequest(final String method, final String url) throws IOException {
        Preconditions.checkArgument(this.supportsMethod(method), "HTTP method %s not supported", method);
        final URL connUrl = new URL(url);
        final HttpURLConnection connection = this.connectionFactory.openConnection(connUrl);
        connection.setRequestMethod(method);
        if (connection instanceof HttpsURLConnection) {
            final HttpsURLConnection secureConnection = (HttpsURLConnection)connection;
            if (this.hostnameVerifier != null) {
                secureConnection.setHostnameVerifier(this.hostnameVerifier);
            }
            if (this.sslSocketFactory != null) {
                secureConnection.setSSLSocketFactory(this.sslSocketFactory);
            }
        }
        return new NetHttpRequest(connection);
    }
    
    @Override
    protected /* bridge */ LowLevelHttpRequest buildRequest(final String method, final String url) throws IOException {
        return this.buildRequest(method, url);
    }
    
    static /* synthetic */ Proxy access$000() {
        return defaultProxy();
    }
    
    static {
        Arrays.sort(SUPPORTED_METHODS = new String[] { "DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE" });
    }
    
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
                this.setProxy(defaultProxy());
            }
            return (this.proxy == null) ? new NetHttpTransport(this.connectionFactory, this.sslSocketFactory, this.hostnameVerifier) : new NetHttpTransport(this.proxy, this.sslSocketFactory, this.hostnameVerifier);
        }
    }
}
