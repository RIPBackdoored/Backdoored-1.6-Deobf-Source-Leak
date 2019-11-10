package com.google.api.client.util;

public class Base64
{
    public static byte[] encodeBase64(final byte[] binaryData) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64(binaryData);
    }
    
    public static String encodeBase64String(final byte[] binaryData) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64String(binaryData);
    }
    
    public static byte[] encodeBase64URLSafe(final byte[] binaryData) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafe(binaryData);
    }
    
    public static String encodeBase64URLSafeString(final byte[] binaryData) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(binaryData);
    }
    
    public static byte[] decodeBase64(final byte[] base64Data) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.decodeBase64(base64Data);
    }
    
    public static byte[] decodeBase64(final String base64String) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.decodeBase64(base64String);
    }
    
    private Base64() {
        super();
    }
}
