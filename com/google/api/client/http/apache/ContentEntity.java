package com.google.api.client.http.apache;

import org.apache.http.entity.*;
import com.google.api.client.util.*;
import java.io.*;

final class ContentEntity extends AbstractHttpEntity
{
    private final long contentLength;
    private final StreamingContent streamingContent;
    
    ContentEntity(final long contentLength, final StreamingContent streamingContent) {
        super();
        this.contentLength = contentLength;
        this.streamingContent = Preconditions.checkNotNull(streamingContent);
    }
    
    public InputStream getContent() {
        throw new UnsupportedOperationException();
    }
    
    public long getContentLength() {
        return this.contentLength;
    }
    
    public boolean isRepeatable() {
        return false;
    }
    
    public boolean isStreaming() {
        return true;
    }
    
    public void writeTo(final OutputStream out) throws IOException {
        if (this.contentLength != 0L) {
            this.streamingContent.writeTo(out);
        }
    }
}
