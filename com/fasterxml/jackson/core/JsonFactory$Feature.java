package com.fasterxml.jackson.core;

public enum Feature
{
    INTERN_FIELD_NAMES(true), 
    CANONICALIZE_FIELD_NAMES(true), 
    FAIL_ON_SYMBOL_HASH_OVERFLOW(true), 
    USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING(true);
    
    private final boolean _defaultState;
    private static final /* synthetic */ Feature[] $VALUES;
    
    public static Feature[] values() {
        return Feature.$VALUES.clone();
    }
    
    public static Feature valueOf(final String name) {
        return Enum.valueOf(Feature.class, name);
    }
    
    public static int collectDefaults() {
        int flags = 0;
        for (final Feature f : values()) {
            if (f.enabledByDefault()) {
                flags |= f.getMask();
            }
        }
        return flags;
    }
    
    private Feature(final boolean defaultState) {
        this._defaultState = defaultState;
    }
    
    public boolean enabledByDefault() {
        return this._defaultState;
    }
    
    public boolean enabledIn(final int flags) {
        return (flags & this.getMask()) != 0x0;
    }
    
    public int getMask() {
        return 1 << this.ordinal();
    }
    
    static {
        $VALUES = new Feature[] { Feature.INTERN_FIELD_NAMES, Feature.CANONICALIZE_FIELD_NAMES, Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW, Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING };
    }
}
