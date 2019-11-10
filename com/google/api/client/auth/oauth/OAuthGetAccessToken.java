package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;

@Beta
public class OAuthGetAccessToken extends AbstractOAuthGetToken
{
    public String temporaryToken;
    public String verifier;
    
    public OAuthGetAccessToken(final String authorizationServerUrl) {
        super(authorizationServerUrl);
    }
    
    @Override
    public OAuthParameters createParameters() {
        final OAuthParameters result = super.createParameters();
        result.token = this.temporaryToken;
        result.verifier = this.verifier;
        return result;
    }
}
