package com.google.api.client.util.store;

import java.io.*;
import java.util.*;

public final class DataStoreUtils
{
    public static String toString(final DataStore<?> dataStore) {
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append('{');
            boolean first = true;
            for (final String key : dataStore.keySet()) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(", ");
                }
                sb.append(key).append('=').append(dataStore.get(key));
            }
            return sb.append('}').toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private DataStoreUtils() {
        super();
    }
}
