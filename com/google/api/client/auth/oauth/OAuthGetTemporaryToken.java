package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;

@Beta
public class OAuthGetTemporaryToken extends AbstractOAuthGetToken
{
    public String callback;
    
    public OAuthGetTemporaryToken(final String authorizationServerUrl) {
        super(authorizationServerUrl);
    }
    
    @Override
    public OAuthParameters createParameters() {
        final OAuthParameters result = super.createParameters();
        result.callback = this.callback;
        return result;
    }
}
