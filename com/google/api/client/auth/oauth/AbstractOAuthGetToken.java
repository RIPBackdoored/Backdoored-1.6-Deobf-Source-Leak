package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;
import com.google.api.client.http.*;
import java.io.*;

@Beta
public abstract class AbstractOAuthGetToken extends GenericUrl
{
    public HttpTransport transport;
    public String consumerKey;
    public OAuthSigner signer;
    protected boolean usePost;
    
    protected AbstractOAuthGetToken(final String authorizationServerUrl) {
        super(authorizationServerUrl);
    }
    
    public final OAuthCredentialsResponse execute() throws IOException {
        final HttpRequestFactory requestFactory = this.transport.createRequestFactory();
        final HttpRequest request = requestFactory.buildRequest(this.usePost ? "POST" : "GET", this, null);
        this.createParameters().intercept(request);
        final HttpResponse response = request.execute();
        response.setContentLoggingLimit(0);
        final OAuthCredentialsResponse oauthResponse = new OAuthCredentialsResponse();
        UrlEncodedParser.parse(response.parseAsString(), oauthResponse);
        return oauthResponse;
    }
    
    public OAuthParameters createParameters() {
        final OAuthParameters result = new OAuthParameters();
        result.consumerKey = this.consumerKey;
        result.signer = this.signer;
        return result;
    }
}
