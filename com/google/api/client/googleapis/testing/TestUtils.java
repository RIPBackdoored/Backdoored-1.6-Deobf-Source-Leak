package com.google.api.client.googleapis.testing;

import com.google.common.base.*;
import com.google.common.collect.*;
import java.io.*;
import java.net.*;
import java.util.*;

public final class TestUtils
{
    private static final String UTF_8 = "UTF-8";
    
    public static Map<String, String> parseQuery(final String query) throws IOException {
        final Map<String, String> map = new HashMap<String, String>();
        final Iterable<String> entries = (Iterable<String>)Splitter.on('&').split((CharSequence)query);
        for (final String entry : entries) {
            final List<String> sides = (List<String>)Lists.newArrayList((Iterable<?>)Splitter.on('=').split((CharSequence)entry));
            if (sides.size() != 2) {
                throw new IOException("Invalid Query String");
            }
            final String key = URLDecoder.decode(sides.get(0), "UTF-8");
            final String value = URLDecoder.decode(sides.get(1), "UTF-8");
            map.put(key, value);
        }
        return map;
    }
    
    private TestUtils() {
        super();
    }
}
