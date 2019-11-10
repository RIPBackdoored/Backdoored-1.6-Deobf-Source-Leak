package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.*;
import java.io.*;
import com.google.api.client.json.*;

class MockMetadataServerTransport$1 extends MockLowLevelHttpRequest {
    final /* synthetic */ MockMetadataServerTransport this$0;
    
    MockMetadataServerTransport$1(final MockMetadataServerTransport this$0, final String x0) {
        this.this$0 = this$0;
        super(x0);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        if (this.this$0.tokenRequestStatusCode != null) {
            final MockLowLevelHttpResponse response = new MockLowLevelHttpResponse().setStatusCode(this.this$0.tokenRequestStatusCode).setContent("Token Fetch Error");
            return response;
        }
        final String metadataRequestHeader = this.getFirstHeaderValue("Metadata-Flavor");
        if (!"Google".equals(metadataRequestHeader)) {
            throw new IOException("Metadata request header not found.");
        }
        final GenericJson refreshContents = new GenericJson();
        refreshContents.setFactory(MockMetadataServerTransport.JSON_FACTORY);
        refreshContents.put("access_token", this.this$0.accessToken);
        refreshContents.put("expires_in", 3600000);
        refreshContents.put("token_type", "Bearer");
        final String refreshText = refreshContents.toPrettyString();
        final MockLowLevelHttpResponse response2 = new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent(refreshText);
        return response2;
    }
}