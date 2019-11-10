package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.util.*;
import java.util.*;
import com.google.api.client.http.*;
import com.google.api.client.googleapis.testing.*;
import java.io.*;
import com.google.api.client.json.webtoken.*;
import com.google.api.client.json.*;
import com.google.api.client.testing.http.*;
import com.google.api.client.json.jackson2.*;

@Beta
public class MockTokenServerTransport extends MockHttpTransport
{
    static final String EXPECTED_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    static final JsonFactory JSON_FACTORY;
    final String tokenServerUrl;
    Map<String, String> serviceAccounts;
    Map<String, String> clients;
    Map<String, String> refreshTokens;
    
    public MockTokenServerTransport() {
        this("https://accounts.google.com/o/oauth2/token");
    }
    
    public MockTokenServerTransport(final String tokenServerUrl) {
        super();
        this.serviceAccounts = new HashMap<String, String>();
        this.clients = new HashMap<String, String>();
        this.refreshTokens = new HashMap<String, String>();
        this.tokenServerUrl = tokenServerUrl;
    }
    
    public void addServiceAccount(final String email, final String accessToken) {
        this.serviceAccounts.put(email, accessToken);
    }
    
    public void addClient(final String clientId, final String clientSecret) {
        this.clients.put(clientId, clientSecret);
    }
    
    public void addRefreshToken(final String refreshToken, final String accessTokenToReturn) {
        this.refreshTokens.put(refreshToken, accessTokenToReturn);
    }
    
    @Override
    public LowLevelHttpRequest buildRequest(final String method, final String url) throws IOException {
        if (url.equals(this.tokenServerUrl)) {
            final MockLowLevelHttpRequest request = new MockLowLevelHttpRequest(url) {
                final /* synthetic */ MockTokenServerTransport this$0;
                
                MockTokenServerTransport$1(final String x0) {
                    this.this$0 = this$0;
                    super(x0);
                }
                
                @Override
                public LowLevelHttpResponse execute() throws IOException {
                    final String content = this.getContentAsString();
                    final Map<String, String> query = TestUtils.parseQuery(content);
                    String accessToken = null;
                    final String foundId = query.get("client_id");
                    if (foundId != null) {
                        if (!this.this$0.clients.containsKey(foundId)) {
                            throw new IOException("Client ID not found.");
                        }
                        final String foundSecret = query.get("client_secret");
                        final String expectedSecret = this.this$0.clients.get(foundId);
                        if (foundSecret == null || !foundSecret.equals(expectedSecret)) {
                            throw new IOException("Client secret not found.");
                        }
                        final String foundRefresh = query.get("refresh_token");
                        if (!this.this$0.refreshTokens.containsKey(foundRefresh)) {
                            throw new IOException("Refresh Token not found.");
                        }
                        accessToken = this.this$0.refreshTokens.get(foundRefresh);
                    }
                    else {
                        if (!query.containsKey("grant_type")) {
                            throw new IOException("Unknown token type.");
                        }
                        final String grantType = query.get("grant_type");
                        if (!"urn:ietf:params:oauth:grant-type:jwt-bearer".equals(grantType)) {
                            throw new IOException("Unexpected Grant Type.");
                        }
                        final String assertion = query.get("assertion");
                        final JsonWebSignature signature = JsonWebSignature.parse(MockTokenServerTransport.JSON_FACTORY, assertion);
                        final String foundEmail = signature.getPayload().getIssuer();
                        if (!this.this$0.serviceAccounts.containsKey(foundEmail)) {
                            throw new IOException("Service Account Email not found as issuer.");
                        }
                        accessToken = this.this$0.serviceAccounts.get(foundEmail);
                        final String foundScopes = (String)signature.getPayload().get("scope");
                        if (foundScopes == null || foundScopes.length() == 0) {
                            throw new IOException("Scopes not found.");
                        }
                    }
                    final GenericJson refreshContents = new GenericJson();
                    refreshContents.setFactory(MockTokenServerTransport.JSON_FACTORY);
                    refreshContents.put("access_token", accessToken);
                    refreshContents.put("expires_in", 3600000);
                    refreshContents.put("token_type", "Bearer");
                    final String refreshText = refreshContents.toPrettyString();
                    final MockLowLevelHttpResponse response = new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent(refreshText);
                    return response;
                }
            };
            return request;
        }
        return super.buildRequest(method, url);
    }
    
    static {
        JSON_FACTORY = new JacksonFactory();
    }
}
