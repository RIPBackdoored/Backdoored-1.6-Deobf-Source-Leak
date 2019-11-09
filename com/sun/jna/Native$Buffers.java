package com.sun.jna;

import java.nio.*;

private static class Buffers
{
    private Buffers() {
        super();
    }
    
    static boolean isBuffer(final Class<?> cls) {
        return Buffer.class.isAssignableFrom(cls);
    }
}
