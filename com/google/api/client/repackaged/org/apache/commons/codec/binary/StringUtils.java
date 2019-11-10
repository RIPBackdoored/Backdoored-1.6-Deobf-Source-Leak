package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import java.io.*;

public class StringUtils
{
    public StringUtils() {
        super();
    }
    
    public static byte[] getBytesIso8859_1(final String string) {
        return getBytesUnchecked(string, "ISO-8859-1");
    }
    
    public static byte[] getBytesUsAscii(final String string) {
        return getBytesUnchecked(string, "US-ASCII");
    }
    
    public static byte[] getBytesUtf16(final String string) {
        return getBytesUnchecked(string, "UTF-16");
    }
    
    public static byte[] getBytesUtf16Be(final String string) {
        return getBytesUnchecked(string, "UTF-16BE");
    }
    
    public static byte[] getBytesUtf16Le(final String string) {
        return getBytesUnchecked(string, "UTF-16LE");
    }
    
    public static byte[] getBytesUtf8(final String string) {
        return getBytesUnchecked(string, "UTF-8");
    }
    
    public static byte[] getBytesUnchecked(final String string, final String charsetName) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw newIllegalStateException(charsetName, e);
        }
    }
    
    private static IllegalStateException newIllegalStateException(final String charsetName, final UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }
    
    public static String newString(final byte[] bytes, final String charsetName) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw newIllegalStateException(charsetName, e);
        }
    }
    
    public static String newStringIso8859_1(final byte[] bytes) {
        return newString(bytes, "ISO-8859-1");
    }
    
    public static String newStringUsAscii(final byte[] bytes) {
        return newString(bytes, "US-ASCII");
    }
    
    public static String newStringUtf16(final byte[] bytes) {
        return newString(bytes, "UTF-16");
    }
    
    public static String newStringUtf16Be(final byte[] bytes) {
        return newString(bytes, "UTF-16BE");
    }
    
    public static String newStringUtf16Le(final byte[] bytes) {
        return newString(bytes, "UTF-16LE");
    }
    
    public static String newStringUtf8(final byte[] bytes) {
        return newString(bytes, "UTF-8");
    }
}
