package com.google.api.client.util;

import java.io.*;

public final class ByteStreams
{
    private static final int BUF_SIZE = 4096;
    
    public static long copy(final InputStream from, final OutputStream to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        final byte[] buf = new byte[4096];
        long total = 0L;
        while (true) {
            final int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
            total += r;
        }
        return total;
    }
    
    public static InputStream limit(final InputStream in, final long limit) {
        return new LimitedInputStream(in, limit);
    }
    
    public static int read(final InputStream in, final byte[] b, final int off, final int len) throws IOException {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(b);
        if (len < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int total;
        int result;
        for (total = 0; total < len; total += result) {
            result = in.read(b, off + total, len - total);
            if (result == -1) {
                break;
            }
        }
        return total;
    }
    
    private ByteStreams() {
        super();
    }
    
    private static final class LimitedInputStream extends FilterInputStream
    {
        private long left;
        private long mark;
        
        LimitedInputStream(final InputStream in, final long limit) {
            super(in);
            this.mark = -1L;
            Preconditions.checkNotNull(in);
            Preconditions.checkArgument(limit >= 0L, (Object)"limit must be non-negative");
            this.left = limit;
        }
        
        @Override
        public int available() throws IOException {
            return (int)Math.min(this.in.available(), this.left);
        }
        
        @Override
        public synchronized void mark(final int readLimit) {
            this.in.mark(readLimit);
            this.mark = this.left;
        }
        
        @Override
        public int read() throws IOException {
            if (this.left == 0L) {
                return -1;
            }
            final int result = this.in.read();
            if (result != -1) {
                --this.left;
            }
            return result;
        }
        
        @Override
        public int read(final byte[] b, final int off, int len) throws IOException {
            if (this.left == 0L) {
                return -1;
            }
            len = (int)Math.min(len, this.left);
            final int result = this.in.read(b, off, len);
            if (result != -1) {
                this.left -= result;
            }
            return result;
        }
        
        @Override
        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1L) {
                throw new IOException("Mark not set");
            }
            this.in.reset();
            this.left = this.mark;
        }
        
        @Override
        public long skip(long n) throws IOException {
            n = Math.min(n, this.left);
            final long skipped = this.in.skip(n);
            this.left -= skipped;
            return skipped;
        }
    }
}
