package com.google.api.client.util;

import java.security.*;
import javax.net.ssl.*;
import java.io.*;
import java.util.*;
import java.security.cert.*;

public final class SecurityUtils
{
    public static KeyStore getDefaultKeyStore() throws KeyStoreException {
        return KeyStore.getInstance(KeyStore.getDefaultType());
    }
    
    public static KeyStore getJavaKeyStore() throws KeyStoreException {
        return KeyStore.getInstance("JKS");
    }
    
    public static KeyStore getPkcs12KeyStore() throws KeyStoreException {
        return KeyStore.getInstance("PKCS12");
    }
    
    public static void loadKeyStore(final KeyStore keyStore, final InputStream keyStream, final String storePass) throws IOException, GeneralSecurityException {
        try {
            keyStore.load(keyStream, storePass.toCharArray());
        }
        finally {
            keyStream.close();
        }
    }
    
    public static PrivateKey getPrivateKey(final KeyStore keyStore, final String alias, final String keyPass) throws GeneralSecurityException {
        return (PrivateKey)keyStore.getKey(alias, keyPass.toCharArray());
    }
    
    public static PrivateKey loadPrivateKeyFromKeyStore(final KeyStore keyStore, final InputStream keyStream, final String storePass, final String alias, final String keyPass) throws IOException, GeneralSecurityException {
        loadKeyStore(keyStore, keyStream, storePass);
        return getPrivateKey(keyStore, alias, keyPass);
    }
    
    public static KeyFactory getRsaKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }
    
    public static Signature getSha1WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA1withRSA");
    }
    
    public static Signature getSha256WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }
    
    public static byte[] sign(final Signature signatureAlgorithm, final PrivateKey privateKey, final byte[] contentBytes) throws InvalidKeyException, SignatureException {
        signatureAlgorithm.initSign(privateKey);
        signatureAlgorithm.update(contentBytes);
        return signatureAlgorithm.sign();
    }
    
    public static boolean verify(final Signature signatureAlgorithm, final PublicKey publicKey, final byte[] signatureBytes, final byte[] contentBytes) throws InvalidKeyException, SignatureException {
        signatureAlgorithm.initVerify(publicKey);
        signatureAlgorithm.update(contentBytes);
        try {
            return signatureAlgorithm.verify(signatureBytes);
        }
        catch (SignatureException e) {
            return false;
        }
    }
    
    public static X509Certificate verify(final Signature signatureAlgorithm, final X509TrustManager trustManager, final List<String> certChainBase64, final byte[] signatureBytes, final byte[] contentBytes) throws InvalidKeyException, SignatureException {
        CertificateFactory certificateFactory;
        try {
            certificateFactory = getX509CertificateFactory();
        }
        catch (CertificateException e) {
            return null;
        }
        final X509Certificate[] certificates = new X509Certificate[certChainBase64.size()];
        int currentCert = 0;
        for (final String certBase64 : certChainBase64) {
            final byte[] certDer = Base64.decodeBase64(certBase64);
            final ByteArrayInputStream bis = new ByteArrayInputStream(certDer);
            try {
                final Certificate cert = certificateFactory.generateCertificate(bis);
                if (!(cert instanceof X509Certificate)) {
                    return null;
                }
                certificates[currentCert++] = (X509Certificate)cert;
            }
            catch (CertificateException e2) {
                return null;
            }
        }
        try {
            trustManager.checkServerTrusted(certificates, "RSA");
        }
        catch (CertificateException e3) {
            return null;
        }
        final PublicKey pubKey = certificates[0].getPublicKey();
        if (verify(signatureAlgorithm, pubKey, signatureBytes, contentBytes)) {
            return certificates[0];
        }
        return null;
    }
    
    public static CertificateFactory getX509CertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }
    
    public static void loadKeyStoreFromCertificates(final KeyStore keyStore, final CertificateFactory certificateFactory, final InputStream certificateStream) throws GeneralSecurityException {
        int i = 0;
        for (final Certificate cert : certificateFactory.generateCertificates(certificateStream)) {
            keyStore.setCertificateEntry(String.valueOf(i), cert);
            ++i;
        }
    }
    
    private SecurityUtils() {
        super();
    }
}
