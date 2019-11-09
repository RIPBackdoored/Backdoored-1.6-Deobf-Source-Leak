package com.fasterxml.jackson.core.io;

import java.io.*;

public class UTF32Reader extends Reader
{
    protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
    protected static final char NC = '\0';
    protected final IOContext _context;
    protected InputStream _in;
    protected byte[] _buffer;
    protected int _ptr;
    protected int _length;
    protected final boolean _bigEndian;
    protected char _surrogate;
    protected int _charCount;
    protected int _byteCount;
    protected final boolean _managedBuffers;
    protected char[] _tmpBuf;
    
    public UTF32Reader(final IOContext ctxt, final InputStream in, final byte[] buf, final int ptr, final int len, final boolean isBigEndian) {
        super();
        this._surrogate = '\0';
        this._context = ctxt;
        this._in = in;
        this._buffer = buf;
        this._ptr = ptr;
        this._length = len;
        this._bigEndian = isBigEndian;
        this._managedBuffers = (in != null);
    }
    
    @Override
    public void close() throws IOException {
        final InputStream in = this._in;
        if (in != null) {
            this._in = null;
            this.freeBuffers();
            in.close();
        }
    }
    
    @Override
    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (this.read(this._tmpBuf, 0, 1) < 1) {
            return -1;
        }
        return this._tmpBuf[0];
    }
    
    @Override
    public int read(final char[] cbuf, final int start, final int len) throws IOException {
        if (this._buffer == null) {
            return -1;
        }
        if (len < 1) {
            return len;
        }
        if (start < 0 || start + len > cbuf.length) {
            this.reportBounds(cbuf, start, len);
        }
        int outPtr = start;
        final int outEnd = len + start;
        if (this._surrogate != '\0') {
            cbuf[outPtr++] = this._surrogate;
            this._surrogate = '\0';
        }
        else {
            final int left = this._length - this._ptr;
            if (left < 4 && !this.loadMore(left)) {
                if (left == 0) {
                    return -1;
                }
                this.reportUnexpectedEOF(this._length - this._ptr, 4);
            }
        }
        final int lastValidInputStart = this._length - 4;
        while (outPtr < outEnd) {
            final int ptr = this._ptr;
            int hi;
            int lo;
            if (this._bigEndian) {
                hi = (this._buffer[ptr] << 8 | (this._buffer[ptr + 1] & 0xFF));
                lo = ((this._buffer[ptr + 2] & 0xFF) << 8 | (this._buffer[ptr + 3] & 0xFF));
            }
            else {
                lo = ((this._buffer[ptr] & 0xFF) | (this._buffer[ptr + 1] & 0xFF) << 8);
                hi = ((this._buffer[ptr + 2] & 0xFF) | this._buffer[ptr + 3] << 8);
            }
            this._ptr += 4;
            if (hi != 0) {
                hi &= 0xFFFF;
                final int ch = hi - 1 << 16 | lo;
                if (hi > 16) {
                    this.reportInvalid(ch, outPtr - start, String.format(" (above 0x%08x)", 1114111));
                }
                cbuf[outPtr++] = (char)(55296 + (ch >> 10));
                lo = (0xDC00 | (ch & 0x3FF));
                if (outPtr >= outEnd) {
                    this._surrogate = (char)ch;
                    break;
                }
            }
            cbuf[outPtr++] = (char)lo;
            if (this._ptr > lastValidInputStart) {
                break;
            }
        }
        final int actualLen = outPtr - start;
        this._charCount += actualLen;
        return actualLen;
    }
    
    private void reportUnexpectedEOF(final int gotBytes, final int needed) throws IOException {
        final int bytePos = this._byteCount + gotBytes;
        final int charPos = this._charCount;
        throw new CharConversionException("Unexpected EOF in the middle of a 4-byte UTF-32 char: got " + gotBytes + ", needed " + needed + ", at char #" + charPos + ", byte #" + bytePos + ")");
    }
    
    private void reportInvalid(final int value, final int offset, final String msg) throws IOException {
        final int bytePos = this._byteCount + this._ptr - 1;
        final int charPos = this._charCount + offset;
        throw new CharConversionException("Invalid UTF-32 character 0x" + Integer.toHexString(value) + msg + " at char #" + charPos + ", byte #" + bytePos + ")");
    }
    
    private boolean loadMore(final int available) throws IOException {
        this._byteCount += this._length - available;
        if (available > 0) {
            if (this._ptr > 0) {
                System.arraycopy(this._buffer, this._ptr, this._buffer, 0, available);
                this._ptr = 0;
            }
            this._length = available;
        }
        else {
            this._ptr = 0;
            final int count = (this._in == null) ? -1 : this._in.read(this._buffer);
            if (count < 1) {
                this._length = 0;
                if (count < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    return false;
                }
                this.reportStrangeStream();
            }
            this._length = count;
        }
        while (this._length < 4) {
            final int count = (this._in == null) ? -1 : this._in.read(this._buffer, this._length, this._buffer.length - this._length);
            if (count < 1) {
                if (count < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    this.reportUnexpectedEOF(this._length, 4);
                }
                this.reportStrangeStream();
            }
            this._length += count;
        }
        return true;
    }
    
    private void freeBuffers() {
        final byte[] buf = this._buffer;
        if (buf != null) {
            this._buffer = null;
            this._context.releaseReadIOBuffer(buf);
        }
    }
    
    private void reportBounds(final char[] cbuf, final int start, final int len) throws IOException {
        throw new ArrayIndexOutOfBoundsException("read(buf," + start + "," + len + "), cbuf[" + cbuf.length + "]");
    }
    
    private void reportStrangeStream() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }
}
