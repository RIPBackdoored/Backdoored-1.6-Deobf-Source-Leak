package com.google.api.client.http;

import com.google.api.client.util.*;
import java.io.*;

public final class ByteArrayContent extends AbstractInputStreamContent
{
    private final byte[] byteArray;
    private final int offset;
    private final int length;
    
    public ByteArrayContent(final String type, final byte[] array) {
        this(type, array, 0, array.length);
    }
    
    public ByteArrayContent(final String type, final byte[] array, final int offset, final int length) {
        super(type);
        this.byteArray = Preconditions.checkNotNull(array);
        Preconditions.checkArgument(offset >= 0 && length >= 0 && offset + length <= array.length, "offset %s, length %s, array length %s", offset, length, array.length);
        this.offset = offset;
        this.length = length;
    }
    
    public static ByteArrayContent fromString(final String type, final String contentString) {
        return new ByteArrayContent(type, StringUtils.getBytesUtf8(contentString));
    }
    
    @Override
    public long getLength() {
        return this.length;
    }
    
    @Override
    public boolean retrySupported() {
        return true;
    }
    
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.byteArray, this.offset, this.length);
    }
    
    @Override
    public ByteArrayContent setType(final String type) {
        return (ByteArrayContent)super.setType(type);
    }
    
    @Override
    public ByteArrayContent setCloseInputStream(final boolean closeInputStream) {
        return (ByteArrayContent)super.setCloseInputStream(closeInputStream);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setCloseInputStream(final boolean closeInputStream) {
        return this.setCloseInputStream(closeInputStream);
    }
    
    @Override
    public /* bridge */ AbstractInputStreamContent setType(final String type) {
        return this.setType(type);
    }
}
