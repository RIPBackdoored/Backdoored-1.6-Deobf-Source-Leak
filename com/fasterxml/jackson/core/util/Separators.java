package com.fasterxml.jackson.core.util;

import java.io.*;

public class Separators implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final char objectFieldValueSeparator;
    private final char objectEntrySeparator;
    private final char arrayValueSeparator;
    
    public static Separators createDefaultInstance() {
        return new Separators();
    }
    
    public Separators() {
        this(':', ',', ',');
    }
    
    public Separators(final char objectFieldValueSeparator, final char objectEntrySeparator, final char arrayValueSeparator) {
        super();
        this.objectFieldValueSeparator = objectFieldValueSeparator;
        this.objectEntrySeparator = objectEntrySeparator;
        this.arrayValueSeparator = arrayValueSeparator;
    }
    
    public Separators withObjectFieldValueSeparator(final char sep) {
        return (this.objectFieldValueSeparator == sep) ? this : new Separators(sep, this.objectEntrySeparator, this.arrayValueSeparator);
    }
    
    public Separators withObjectEntrySeparator(final char sep) {
        return (this.objectEntrySeparator == sep) ? this : new Separators(this.objectFieldValueSeparator, sep, this.arrayValueSeparator);
    }
    
    public Separators withArrayValueSeparator(final char sep) {
        return (this.arrayValueSeparator == sep) ? this : new Separators(this.objectFieldValueSeparator, this.objectEntrySeparator, sep);
    }
    
    public char getObjectFieldValueSeparator() {
        return this.objectFieldValueSeparator;
    }
    
    public char getObjectEntrySeparator() {
        return this.objectEntrySeparator;
    }
    
    public char getArrayValueSeparator() {
        return this.arrayValueSeparator;
    }
}
