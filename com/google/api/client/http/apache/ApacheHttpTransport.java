package com.google.api.client.http.apache;

import org.apache.http.conn.ssl.*;
import java.net.*;
import org.apache.http.params.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.impl.conn.tsccm.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.*;
import org.apache.http.client.methods.*;
import com.google.api.client.http.*;
import org.apache.http.*;
import org.apache.http.conn.params.*;
import java.io.*;
import java.security.*;
import javax.net.ssl.*;
import com.google.api.client.util.*;

public final class ApacheHttpTransport extends HttpTransport
{
    private final HttpClient httpClient;
    
    public ApacheHttpTransport() {
        this((HttpClient)newDefaultHttpClient());
    }
    
    public ApacheHttpTransport(final HttpClient httpClient) {
        super();
        this.httpClient = httpClient;
        HttpParams params = httpClient.getParams();
        if (params == null) {
            params = newDefaultHttpClient().getParams();
        }
        HttpProtocolParams.setVersion(params, (ProtocolVersion)HttpVersion.HTTP_1_1);
        params.setBooleanParameter("http.protocol.handle-redirects", false);
    }
    
    public static DefaultHttpClient newDefaultHttpClient() {
        return newDefaultHttpClient(SSLSocketFactory.getSocketFactory(), newDefaultHttpParams(), ProxySelector.getDefault());
    }
    
    static HttpParams newDefaultHttpParams() {
        final HttpParams params = (HttpParams)new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        ConnManagerParams.setMaxTotalConnections(params, 200);
        ConnManagerParams.setMaxConnectionsPerRoute(params, (ConnPerRoute)new ConnPerRouteBean(20));
        return params;
    }
    
    static DefaultHttpClient newDefaultHttpClient(final SSLSocketFactory socketFactory, final HttpParams params, final ProxySelector proxySelector) {
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", (SocketFactory)socketFactory, 443));
        final ClientConnectionManager connectionManager = (ClientConnectionManager)new ThreadSafeClientConnManager(params, registry);
        final DefaultHttpClient defaultHttpClient = new DefaultHttpClient(connectionManager, params);
        defaultHttpClient.setHttpRequestRetryHandler((HttpRequestRetryHandler)new DefaultHttpRequestRetryHandler(0, false));
        if (proxySelector != null) {
            defaultHttpClient.setRoutePlanner((HttpRoutePlanner)new ProxySelectorRoutePlanner(registry, proxySelector));
        }
        return defaultHttpClient;
    }
    
    @Override
    public boolean supportsMethod(final String method) {
        return true;
    }
    
    @Override
    protected ApacheHttpRequest buildRequest(final String method, final String url) {
        HttpRequestBase requestBase;
        if (method.equals("DELETE")) {
            requestBase = (HttpRequestBase)new HttpDelete(url);
        }
        else if (method.equals("GET")) {
            requestBase = (HttpRequestBase)new HttpGet(url);
        }
        else if (method.equals("HEAD")) {
            requestBase = (HttpRequestBase)new HttpHead(url);
        }
        else if (method.equals("POST")) {
            requestBase = (HttpRequestBase)new HttpPost(url);
        }
        else if (method.equals("PUT")) {
            requestBase = (HttpRequestBase)new HttpPut(url);
        }
        else if (method.equals("TRACE")) {
            requestBase = (HttpRequestBase)new HttpTrace(url);
        }
        else if (method.equals("OPTIONS")) {
            requestBase = (HttpRequestBase)new HttpOptions(url);
        }
        else {
            requestBase = (HttpRequestBase)new HttpExtensionMethod(method, url);
        }
        return new ApacheHttpRequest(this.httpClient, requestBase);
    }
    
    @Override
    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }
    
    public HttpClient getHttpClient() {
        return this.httpClient;
    }
    
    @Override
    protected /* bridge */ LowLevelHttpRequest buildRequest(final String method, final String url) throws IOException {
        return this.buildRequest(method, url);
    }
    
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
}
