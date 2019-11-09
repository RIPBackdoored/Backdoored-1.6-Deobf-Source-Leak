package com.fasterxml.jackson.core.util;

import java.lang.ref.*;
import com.fasterxml.jackson.core.io.*;

public class BufferRecyclers
{
    public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
    private static final ThreadLocalBufferManager _bufferRecyclerTracker;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _encoderRef;
    
    public BufferRecyclers() {
        super();
    }
    
    public static BufferRecycler getBufferRecycler() {
        SoftReference<BufferRecycler> ref = BufferRecyclers._recyclerRef.get();
        BufferRecycler br = (ref == null) ? null : ref.get();
        if (br == null) {
            br = new BufferRecycler();
            if (BufferRecyclers._bufferRecyclerTracker != null) {
                ref = BufferRecyclers._bufferRecyclerTracker.wrapAndTrack(br);
            }
            else {
                ref = new SoftReference<BufferRecycler>(br);
            }
            BufferRecyclers._recyclerRef.set(ref);
        }
        return br;
    }
    
    public static int releaseBuffers() {
        if (BufferRecyclers._bufferRecyclerTracker != null) {
            return BufferRecyclers._bufferRecyclerTracker.releaseBuffers();
        }
        return -1;
    }
    
    public static JsonStringEncoder getJsonStringEncoder() {
        final SoftReference<JsonStringEncoder> ref = BufferRecyclers._encoderRef.get();
        JsonStringEncoder enc = (ref == null) ? null : ref.get();
        if (enc == null) {
            enc = new JsonStringEncoder();
            BufferRecyclers._encoderRef.set(new SoftReference<JsonStringEncoder>(enc));
        }
        return enc;
    }
    
    public static byte[] encodeAsUTF8(final String text) {
        return getJsonStringEncoder().encodeAsUTF8(text);
    }
    
    public static char[] quoteAsJsonText(final String rawText) {
        return getJsonStringEncoder().quoteAsString(rawText);
    }
    
    public static void quoteAsJsonText(final CharSequence input, final StringBuilder output) {
        getJsonStringEncoder().quoteAsString(input, output);
    }
    
    public static byte[] quoteAsJsonUTF8(final String rawText) {
        return getJsonStringEncoder().quoteAsUTF8(rawText);
    }
    
    static {
        _bufferRecyclerTracker = ("true".equals(System.getProperty("com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers")) ? ThreadLocalBufferManager.instance() : null);
        _recyclerRef = new ThreadLocal<SoftReference<BufferRecycler>>();
        _encoderRef = new ThreadLocal<SoftReference<JsonStringEncoder>>();
    }
}
