package com.google.api.client.util;

import javax.net.ssl.*;

static final class SslUtils$2 implements HostnameVerifier {
    SslUtils$2() {
        super();
    }
    
    @Override
    public boolean verify(final String arg0, final SSLSession arg1) {
        return true;
    }
}