package com.google.api.client.testing.util;

import com.google.api.client.util.*;
import java.io.*;

@Beta
public class TestableByteArrayInputStream extends ByteArrayInputStream
{
    private boolean closed;
    
    public TestableByteArrayInputStream(final byte[] buf) {
        super(buf);
    }
    
    public TestableByteArrayInputStream(final byte[] buf, final int offset, final int length) {
        super(buf);
    }
    
    @Override
    public void close() throws IOException {
        this.closed = true;
    }
    
    public final byte[] getBuffer() {
        return this.buf;
    }
    
    public final boolean isClosed() {
        return this.closed;
    }
}
