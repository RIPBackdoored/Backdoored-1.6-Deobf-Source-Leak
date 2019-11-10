package com.google.api.client.util;

public class StringUtils
{
    public static final String LINE_SEPARATOR;
    
    public static byte[] getBytesUtf8(final String string) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils.getBytesUtf8(string);
    }
    
    public static String newStringUtf8(final byte[] bytes) {
        return com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
    }
    
    private StringUtils() {
        super();
    }
    
    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
    }
}
