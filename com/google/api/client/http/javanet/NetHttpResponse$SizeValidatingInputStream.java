package com.google.api.client.http.javanet;

import java.io.*;

private final class SizeValidatingInputStream extends FilterInputStream
{
    private long bytesRead;
    final /* synthetic */ NetHttpResponse this$0;
    
    public SizeValidatingInputStream(final NetHttpResponse this$0, final InputStream in) {
        this.this$0 = this$0;
        super(in);
        this.bytesRead = 0L;
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int n = this.in.read(b, off, len);
        if (n == -1) {
            this.throwIfFalseEOF();
        }
        else {
            this.bytesRead += n;
        }
        return n;
    }
    
    @Override
    public int read() throws IOException {
        final int n = this.in.read();
        if (n == -1) {
            this.throwIfFalseEOF();
        }
        else {
            ++this.bytesRead;
        }
        return n;
    }
    
    private void throwIfFalseEOF() throws IOException {
        final long contentLength = this.this$0.getContentLength();
        if (contentLength == -1L) {
            return;
        }
        if (this.bytesRead != 0L && this.bytesRead < contentLength) {
            throw new IOException("Connection closed prematurely: bytesRead = " + this.bytesRead + ", Content-Length = " + contentLength);
        }
    }
}
