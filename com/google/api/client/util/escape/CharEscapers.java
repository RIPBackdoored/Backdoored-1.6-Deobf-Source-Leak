package com.google.api.client.util.escape;

import java.net.*;
import java.io.*;

public final class CharEscapers
{
    private static final Escaper URI_ESCAPER;
    private static final Escaper URI_PATH_ESCAPER;
    private static final Escaper URI_RESERVED_ESCAPER;
    private static final Escaper URI_USERINFO_ESCAPER;
    private static final Escaper URI_QUERY_STRING_ESCAPER;
    
    public static String escapeUri(final String value) {
        return CharEscapers.URI_ESCAPER.escape(value);
    }
    
    public static String decodeUri(final String uri) {
        try {
            return URLDecoder.decode(uri, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String escapeUriPath(final String value) {
        return CharEscapers.URI_PATH_ESCAPER.escape(value);
    }
    
    public static String escapeUriPathWithoutReserved(final String value) {
        return CharEscapers.URI_RESERVED_ESCAPER.escape(value);
    }
    
    public static String escapeUriUserInfo(final String value) {
        return CharEscapers.URI_USERINFO_ESCAPER.escape(value);
    }
    
    public static String escapeUriQuery(final String value) {
        return CharEscapers.URI_QUERY_STRING_ESCAPER.escape(value);
    }
    
    private CharEscapers() {
        super();
    }
    
    static {
        URI_ESCAPER = new PercentEscaper("-_.*", true);
        URI_PATH_ESCAPER = new PercentEscaper("-_.!~*'()@:$&,;=", false);
        URI_RESERVED_ESCAPER = new PercentEscaper("-_.!~*'()@:$&,;=+/?", false);
        URI_USERINFO_ESCAPER = new PercentEscaper("-_.!~*'():$&,;=", false);
        URI_QUERY_STRING_ESCAPER = new PercentEscaper("-_.!~*'()@:$,;/?:", false);
    }
}
