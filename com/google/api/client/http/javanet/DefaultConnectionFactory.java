package com.google.api.client.http.javanet;

import java.net.*;
import java.io.*;

public class DefaultConnectionFactory implements ConnectionFactory
{
    private final Proxy proxy;
    
    public DefaultConnectionFactory() {
        this(null);
    }
    
    public DefaultConnectionFactory(final Proxy proxy) {
        super();
        this.proxy = proxy;
    }
    
    @Override
    public HttpURLConnection openConnection(final URL url) throws IOException {
        return (HttpURLConnection)((this.proxy == null) ? url.openConnection() : url.openConnection(this.proxy));
    }
}
