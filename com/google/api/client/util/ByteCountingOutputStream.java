package com.google.api.client.util;

import java.io.*;

final class ByteCountingOutputStream extends OutputStream
{
    long count;
    
    ByteCountingOutputStream() {
        super();
    }
    
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.count += len;
    }
    
    @Override
    public void write(final int b) throws IOException {
        ++this.count;
    }
}
