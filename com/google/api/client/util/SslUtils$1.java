package com.google.api.client.util;

import javax.net.ssl.*;
import java.security.cert.*;

static final class SslUtils$1 implements X509TrustManager {
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
}