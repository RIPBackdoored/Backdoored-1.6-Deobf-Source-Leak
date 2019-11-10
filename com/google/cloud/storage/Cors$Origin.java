package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.net.*;

public static final class Origin implements Serializable
{
    private static final long serialVersionUID = -4447958124895577993L;
    private static final String ANY_URI = "*";
    private final String value;
    private static final Origin ANY;
    
    private Origin(final String value) {
        super();
        this.value = Preconditions.checkNotNull(value);
    }
    
    public static Origin any() {
        return Origin.ANY;
    }
    
    public static Origin of(final String scheme, final String host, final int port) {
        try {
            return of(new URI(scheme, null, host, port, null, null, null).toString());
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    public static Origin of(final String value) {
        if ("*".equals(value)) {
            return any();
        }
        return new Origin(value);
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Origin && this.value.equals(((Origin)obj).value);
    }
    
    @Override
    public String toString() {
        return this.getValue();
    }
    
    public String getValue() {
        return this.value;
    }
    
    static {
        ANY = new Origin("*");
    }
}
