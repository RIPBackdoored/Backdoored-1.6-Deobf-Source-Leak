package com.fasterxml.jackson.core.util;

import java.util.concurrent.*;

public final class InternCache extends ConcurrentHashMap<String, String>
{
    private static final long serialVersionUID = 1L;
    private static final int MAX_ENTRIES = 180;
    public static final InternCache instance;
    private final Object lock;
    
    private InternCache() {
        super(180, 0.8f, 4);
        this.lock = new Object();
    }
    
    public String intern(final String input) {
        String result = ((ConcurrentHashMap<K, String>)this).get(input);
        if (result != null) {
            return result;
        }
        if (this.size() >= 180) {
            synchronized (this.lock) {
                if (this.size() >= 180) {
                    this.clear();
                }
            }
        }
        result = input.intern();
        this.put(result, result);
        return result;
    }
    
    static {
        instance = new InternCache();
    }
}
