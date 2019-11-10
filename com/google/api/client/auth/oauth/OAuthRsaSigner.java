package com.google.api.client.auth.oauth;

import com.google.api.client.util.*;
import java.security.*;

@Beta
public final class OAuthRsaSigner implements OAuthSigner
{
    public PrivateKey privateKey;
    
    public OAuthRsaSigner() {
        super();
    }
    
    @Override
    public String getSignatureMethod() {
        return "RSA-SHA1";
    }
    
    @Override
    public String computeSignature(final String signatureBaseString) throws GeneralSecurityException {
        final Signature signer = SecurityUtils.getSha1WithRsaSignatureAlgorithm();
        final byte[] data = StringUtils.getBytesUtf8(signatureBaseString);
        return Base64.encodeBase64String(SecurityUtils.sign(signer, this.privateKey, data));
    }
}
