package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.*;
import java.io.*;

public class CommonGoogleJsonClientRequestInitializer extends CommonGoogleClientRequestInitializer
{
    public CommonGoogleJsonClientRequestInitializer() {
        super();
    }
    
    public CommonGoogleJsonClientRequestInitializer(final String key) {
        super(key);
    }
    
    public CommonGoogleJsonClientRequestInitializer(final String key, final String userIp) {
        super(key, userIp);
    }
    
    @Override
    public final void initialize(final AbstractGoogleClientRequest<?> request) throws IOException {
        super.initialize(request);
        this.initializeJsonRequest((AbstractGoogleJsonClientRequest)request);
    }
    
    protected void initializeJsonRequest(final AbstractGoogleJsonClientRequest<?> request) throws IOException {
    }
}
