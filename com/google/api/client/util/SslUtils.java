package com.google.api.client.util;

import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

public final class SslUtils
{
    public static SSLContext getSslContext() throws NoSuchAlgorithmException {
        return SSLContext.getInstance("SSL");
    }
    
    public static SSLContext getTlsSslContext() throws NoSuchAlgorithmException {
        return SSLContext.getInstance("TLS");
    }
    
    public static TrustManagerFactory getDefaultTrustManagerFactory() throws NoSuchAlgorithmException {
        return TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    }
    
    public static TrustManagerFactory getPkixTrustManagerFactory() throws NoSuchAlgorithmException {
        return TrustManagerFactory.getInstance("PKIX");
    }
    
    public static KeyManagerFactory getDefaultKeyManagerFactory() throws NoSuchAlgorithmException {
        return KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    }
    
    public static KeyManagerFactory getPkixKeyManagerFactory() throws NoSuchAlgorithmException {
        return KeyManagerFactory.getInstance("PKIX");
    }
    
    public static SSLContext initSslContext(final SSLContext sslContext, final KeyStore trustStore, final TrustManagerFactory trustManagerFactory) throws GeneralSecurityException {
        trustManagerFactory.init(trustStore);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }
    
    @Beta
    public static SSLContext trustAllSSLContext() throws GeneralSecurityException {
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
                SslUtils$1() {
                    super();
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                }
                
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };
        final SSLContext context = getTlsSslContext();
        context.init(null, trustAllCerts, null);
        return context;
    }
    
    @Beta
    public static HostnameVerifier trustAllHostnameVerifier() {
        return new HostnameVerifier() {
            SslUtils$2() {
                super();
            }
            
            @Override
            public boolean verify(final String arg0, final SSLSession arg1) {
                return true;
            }
        };
    }
    
    private SslUtils() {
        super();
    }
}
