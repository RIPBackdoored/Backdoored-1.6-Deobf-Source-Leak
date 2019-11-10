package com.google.api.client.testing.json.webtoken;

import java.security.cert.*;
import java.io.*;
import com.google.api.client.util.*;
import javax.net.ssl.*;
import java.security.*;

@Beta
public static class CertData
{
    private String pem;
    
    public CertData(final String pem) {
        super();
        this.pem = pem;
    }
    
    public Certificate getCertfificate() throws IOException, CertificateException {
        final byte[] bytes = this.getDer();
        final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return SecurityUtils.getX509CertificateFactory().generateCertificate(bis);
    }
    
    public byte[] getDer() throws IOException {
        return PemReader.readFirstSectionAndClose(new StringReader(this.pem), "CERTIFICATE").getBase64DecodedBytes();
    }
    
    public String getBase64Der() throws IOException {
        return Base64.encodeBase64String(this.getDer());
    }
    
    public X509TrustManager getTrustManager() throws IOException, GeneralSecurityException {
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", this.getCertfificate());
        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        return (X509TrustManager)trustManagerFactory.getTrustManagers()[0];
    }
}
