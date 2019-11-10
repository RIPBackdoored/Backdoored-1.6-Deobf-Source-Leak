package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import com.google.api.client.testing.http.*;
import java.io.*;
import com.google.api.client.json.*;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.json.jackson2.*;

@Beta
public class MockMetadataServerTransport extends MockHttpTransport
{
    private static final String METADATA_SERVER_URL;
    private static final String METADATA_TOKEN_SERVER_URL;
    static final JsonFactory JSON_FACTORY;
    String accessToken;
    Integer tokenRequestStatusCode;
    
    public MockMetadataServerTransport(final String accessToken) {
        super();
        this.accessToken = accessToken;
    }
    
    public void setTokenRequestStatusCode(final Integer tokenRequestStatusCode) {
        this.tokenRequestStatusCode = tokenRequestStatusCode;
    }
    
    @Override
    public LowLevelHttpRequest buildRequest(final String method, final String url) throws IOException {
        if (url.equals(MockMetadataServerTransport.METADATA_TOKEN_SERVER_URL)) {
            final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest(url) {
                final /* synthetic */ MockMetadataServerTransport this$0;
                
                MockMetadataServerTransport$1(final String x0) {
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
            };
            return request;
        }
        if (url.equals(MockMetadataServerTransport.METADATA_SERVER_URL)) {
            final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest(url) {
                final /* synthetic */ MockMetadataServerTransport this$0;
                
                MockMetadataServerTransport$2(final String x0) {
                    this.this$0 = this$0;
                    super(x0);
                }
                
                @Override
                public LowLevelHttpResponse execute() {
                    final MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                    response.addHeader("Metadata-Flavor", "Google");
                    return response;
                }
            };
            return request;
        }
        return super.buildRequest(method, url);
    }
    
    static {
        METADATA_SERVER_URL = OAuth2Utils.getMetadataServerUrl();
        METADATA_TOKEN_SERVER_URL = String.valueOf(MockMetadataServerTransport.METADATA_SERVER_URL).concat("/computeMetadata/v1/instance/service-accounts/default/token");
        JSON_FACTORY = new JacksonFactory();
    }
}
