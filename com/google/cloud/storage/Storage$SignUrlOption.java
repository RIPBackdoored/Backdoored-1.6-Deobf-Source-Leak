package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.auth.*;

public static class SignUrlOption implements Serializable
{
    private static final long serialVersionUID = 7850569877451099267L;
    private final Option option;
    private final Object value;
    
    private SignUrlOption(final Option option, final Object value) {
        super();
        this.option = option;
        this.value = value;
    }
    
    Option getOption() {
        return this.option;
    }
    
    Object getValue() {
        return this.value;
    }
    
    public static SignUrlOption httpMethod(final HttpMethod httpMethod) {
        return new SignUrlOption(Option.HTTP_METHOD, httpMethod);
    }
    
    public static SignUrlOption withContentType() {
        return new SignUrlOption(Option.CONTENT_TYPE, true);
    }
    
    public static SignUrlOption withMd5() {
        return new SignUrlOption(Option.MD5, true);
    }
    
    public static SignUrlOption withExtHeaders(final Map<String, String> extHeaders) {
        return new SignUrlOption(Option.EXT_HEADERS, extHeaders);
    }
    
    public static SignUrlOption withV2Signature() {
        return new SignUrlOption(Option.SIGNATURE_VERSION, SignatureVersion.V2);
    }
    
    public static SignUrlOption withV4Signature() {
        return new SignUrlOption(Option.SIGNATURE_VERSION, SignatureVersion.V4);
    }
    
    public static SignUrlOption signWith(final ServiceAccountSigner signer) {
        return new SignUrlOption(Option.SERVICE_ACCOUNT_CRED, signer);
    }
    
    public static SignUrlOption withHostName(final String hostName) {
        return new SignUrlOption(Option.HOST_NAME, hostName);
    }
    
    enum Option
    {
        HTTP_METHOD, 
        CONTENT_TYPE, 
        MD5, 
        EXT_HEADERS, 
        SERVICE_ACCOUNT_CRED, 
        SIGNATURE_VERSION, 
        HOST_NAME;
        
        private static final /* synthetic */ Option[] $VALUES;
        
        public static Option[] values() {
            return Option.$VALUES.clone();
        }
        
        public static Option valueOf(final String name) {
            return Enum.valueOf(Option.class, name);
        }
        
        static {
            $VALUES = new Option[] { Option.HTTP_METHOD, Option.CONTENT_TYPE, Option.MD5, Option.EXT_HEADERS, Option.SERVICE_ACCOUNT_CRED, Option.SIGNATURE_VERSION, Option.HOST_NAME };
        }
    }
    
    enum SignatureVersion
    {
        V2, 
        V4;
        
        private static final /* synthetic */ SignatureVersion[] $VALUES;
        
        public static SignatureVersion[] values() {
            return SignatureVersion.$VALUES.clone();
        }
        
        public static SignatureVersion valueOf(final String name) {
            return Enum.valueOf(SignatureVersion.class, name);
        }
        
        static {
            $VALUES = new SignatureVersion[] { SignatureVersion.V2, SignatureVersion.V4 };
        }
    }
}
