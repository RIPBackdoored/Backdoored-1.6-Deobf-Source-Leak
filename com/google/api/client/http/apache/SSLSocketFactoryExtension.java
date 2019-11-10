package com.google.api.client.http.apache;

import org.apache.http.conn.ssl.*;
import java.security.*;
import java.io.*;
import javax.net.ssl.*;
import java.net.*;

final class SSLSocketFactoryExtension extends SSLSocketFactory
{
    private final javax.net.ssl.SSLSocketFactory socketFactory;
    
    SSLSocketFactoryExtension(final SSLContext sslContext) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        super((KeyStore)null);
        this.socketFactory = sslContext.getSocketFactory();
    }
    
    public Socket createSocket() throws IOException {
        return this.socketFactory.createSocket();
    }
    
    public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose) throws IOException, UnknownHostException {
        final SSLSocket sslSocket = (SSLSocket)this.socketFactory.createSocket(socket, host, port, autoClose);
        this.getHostnameVerifier().verify(host, sslSocket);
        return sslSocket;
    }
}
