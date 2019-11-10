package com.google.api.client.auth.oauth;

import javax.crypto.spec.*;
import com.google.api.client.util.*;
import javax.crypto.*;
import java.security.*;

@Beta
public final class OAuthHmacSigner implements OAuthSigner
{
    public String clientSharedSecret;
    public String tokenSharedSecret;
    
    public OAuthHmacSigner() {
        super();
    }
    
    @Override
    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }
    
    @Override
    public String computeSignature(final String signatureBaseString) throws GeneralSecurityException {
        final StringBuilder keyBuf = new StringBuilder();
        final String clientSharedSecret = this.clientSharedSecret;
        if (clientSharedSecret != null) {
            keyBuf.append(OAuthParameters.escape(clientSharedSecret));
        }
        keyBuf.append('&');
        final String tokenSharedSecret = this.tokenSharedSecret;
        if (tokenSharedSecret != null) {
            keyBuf.append(OAuthParameters.escape(tokenSharedSecret));
        }
        final String key = keyBuf.toString();
        final SecretKey secretKey = new SecretKeySpec(StringUtils.getBytesUtf8(key), "HmacSHA1");
        final Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        return Base64.encodeBase64String(mac.doFinal(StringUtils.getBytesUtf8(signatureBaseString)));
    }
}
