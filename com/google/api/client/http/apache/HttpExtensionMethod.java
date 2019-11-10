package com.google.api.client.http.apache;

import org.apache.http.client.methods.*;
import com.google.api.client.util.*;
import java.net.*;

final class HttpExtensionMethod extends HttpEntityEnclosingRequestBase
{
    private final String methodName;
    
    public HttpExtensionMethod(final String methodName, final String uri) {
        super();
        this.methodName = Preconditions.checkNotNull(methodName);
        this.setURI(URI.create(uri));
    }
    
    public String getMethod() {
        return this.methodName;
    }
}
