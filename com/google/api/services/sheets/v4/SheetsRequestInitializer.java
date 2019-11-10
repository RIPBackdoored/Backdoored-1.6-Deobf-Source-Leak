package com.google.api.services.sheets.v4;

import com.google.api.client.googleapis.services.json.*;
import java.io.*;

public class SheetsRequestInitializer extends CommonGoogleJsonClientRequestInitializer
{
    public SheetsRequestInitializer() {
        super();
    }
    
    public SheetsRequestInitializer(final String key) {
        super(key);
    }
    
    public SheetsRequestInitializer(final String key, final String userIp) {
        super(key, userIp);
    }
    
    public final void initializeJsonRequest(final AbstractGoogleJsonClientRequest<?> request) throws IOException {
        super.initializeJsonRequest(request);
        this.initializeSheetsRequest((SheetsRequest<?>)request);
    }
    
    protected void initializeSheetsRequest(final SheetsRequest<?> sheetsRequest) throws IOException {
    }
}
