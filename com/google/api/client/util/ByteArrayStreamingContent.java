package com.google.api.client.util;

import java.io.*;

public class ByteArrayStreamingContent implements StreamingContent
{
    private final byte[] byteArray;
    private final int offset;
    private final int length;
    
    public ByteArrayStreamingContent(final byte[] byteArray) {
        this(byteArray, 0, byteArray.length);
    }
    
    public ByteArrayStreamingContent(final byte[] byteArray, final int offset, final int length) {
        super();
        this.byteArray = Preconditions.checkNotNull(byteArray);
        Preconditions.checkArgument(offset >= 0 && length >= 0 && offset + length <= byteArray.length);
        this.offset = offset;
        this.length = length;
    }
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        out.write(this.byteArray, this.offset, this.length);
        out.flush();
    }
}
