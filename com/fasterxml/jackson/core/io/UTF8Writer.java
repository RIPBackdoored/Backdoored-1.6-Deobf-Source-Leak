package com.fasterxml.jackson.core.io;

import java.io.*;

public final class UTF8Writer extends Writer
{
    static final int SURR1_FIRST = 55296;
    static final int SURR1_LAST = 56319;
    static final int SURR2_FIRST = 56320;
    static final int SURR2_LAST = 57343;
    private final IOContext _context;
    private OutputStream _out;
    private byte[] _outBuffer;
    private final int _outBufferEnd;
    private int _outPtr;
    private int _surrogate;
    
    public UTF8Writer(final IOContext ctxt, final OutputStream out) {
        super();
        this._context = ctxt;
        this._out = out;
        this._outBuffer = ctxt.allocWriteEncodingBuffer();
        this._outBufferEnd = this._outBuffer.length - 4;
        this._outPtr = 0;
    }
    
    @Override
    public Writer append(final char c) throws IOException {
        this.write(c);
        return this;
    }
    
    @Override
    public void close() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            final OutputStream out = this._out;
            this._out = null;
            final byte[] buf = this._outBuffer;
            if (buf != null) {
                this._outBuffer = null;
                this._context.releaseWriteEncodingBuffer(buf);
            }
            out.close();
            final int code = this._surrogate;
            this._surrogate = 0;
            if (code > 0) {
                illegalSurrogate(code);
            }
        }
    }
    
    @Override
    public void flush() throws IOException {
        if (this._out != null) {
            if (this._outPtr > 0) {
                this._out.write(this._outBuffer, 0, this._outPtr);
                this._outPtr = 0;
            }
            this._out.flush();
        }
    }
    
    @Override
    public void write(final char[] cbuf) throws IOException {
        this.write(cbuf, 0, cbuf.length);
    }
    
    @Override
    public void write(final char[] cbuf, int off, int len) throws IOException {
        if (len < 2) {
            if (len == 1) {
                this.write(cbuf[off]);
            }
            return;
        }
        if (this._surrogate > 0) {
            final char second = cbuf[off++];
            --len;
            this.write(this.convertSurrogate(second));
        }
        int outPtr = this._outPtr;
        final byte[] outBuf = this._outBuffer;
        final int outBufLast = this._outBufferEnd;
        len += off;
        while (off < len) {
            if (outPtr >= outBufLast) {
                this._out.write(outBuf, 0, outPtr);
                outPtr = 0;
            }
            int c = cbuf[off++];
            Label_0193: {
                if (c < 128) {
                    outBuf[outPtr++] = (byte)c;
                    int maxInCount = len - off;
                    final int maxOutCount = outBufLast - outPtr;
                    if (maxInCount > maxOutCount) {
                        maxInCount = maxOutCount;
                    }
                    maxInCount += off;
                    while (off < maxInCount) {
                        c = cbuf[off++];
                        if (c >= 128) {
                            break Label_0193;
                        }
                        outBuf[outPtr++] = (byte)c;
                    }
                    continue;
                }
            }
            if (c < 2048) {
                outBuf[outPtr++] = (byte)(0xC0 | c >> 6);
                outBuf[outPtr++] = (byte)(0x80 | (c & 0x3F));
            }
            else if (c < 55296 || c > 57343) {
                outBuf[outPtr++] = (byte)(0xE0 | c >> 12);
                outBuf[outPtr++] = (byte)(0x80 | (c >> 6 & 0x3F));
                outBuf[outPtr++] = (byte)(0x80 | (c & 0x3F));
            }
            else {
                if (c > 56319) {
                    this._outPtr = outPtr;
                    illegalSurrogate(c);
                }
                this._surrogate = c;
                if (off >= len) {
                    break;
                }
                c = this.convertSurrogate(cbuf[off++]);
                if (c > 1114111) {
                    this._outPtr = outPtr;
                    illegalSurrogate(c);
                }
                outBuf[outPtr++] = (byte)(0xF0 | c >> 18);
                outBuf[outPtr++] = (byte)(0x80 | (c >> 12 & 0x3F));
                outBuf[outPtr++] = (byte)(0x80 | (c >> 6 & 0x3F));
                outBuf[outPtr++] = (byte)(0x80 | (c & 0x3F));
            }
        }
        this._outPtr = outPtr;
    }
    
    @Override
    public void write(int c) throws IOException {
        if (this._surrogate > 0) {
            c = this.convertSurrogate(c);
        }
        else if (c >= 55296 && c <= 57343) {
            if (c > 56319) {
                illegalSurrogate(c);
            }
            this._surrogate = c;
            return;
        }
        if (this._outPtr >= this._outBufferEnd) {
            this._out.write(this._outBuffer, 0, this._outPtr);
            this._outPtr = 0;
        }
        if (c < 128) {
            this._outBuffer[this._outPtr++] = (byte)c;
        }
        else {
            int ptr = this._outPtr;
            if (c < 2048) {
                this._outBuffer[ptr++] = (byte)(0xC0 | c >> 6);
                this._outBuffer[ptr++] = (byte)(0x80 | (c & 0x3F));
            }
            else if (c <= 65535) {
                this._outBuffer[ptr++] = (byte)(0xE0 | c >> 12);
                this._outBuffer[ptr++] = (byte)(0x80 | (c >> 6 & 0x3F));
                this._outBuffer[ptr++] = (byte)(0x80 | (c & 0x3F));
            }
            else {
                if (c > 1114111) {
                    illegalSurrogate(c);
                }
                this._outBuffer[ptr++] = (byte)(0xF0 | c >> 18);
                this._outBuffer[ptr++] = (byte)(0x80 | (c >> 12 & 0x3F));
                this._outBuffer[ptr++] = (byte)(0x80 | (c >> 6 & 0x3F));
                this._outBuffer[ptr++] = (byte)(0x80 | (c & 0x3F));
            }
            this._outPtr = ptr;
        }
    }
    
    @Override
    public void write(final String str) throws IOException {
        this.write(str, 0, str.length());
    }
    
    @Override
    public void write(final String str, int off, int len) throws IOException {
        if (len < 2) {
            if (len == 1) {
                this.write(str.charAt(off));
            }
            return;
        }
        if (this._surrogate > 0) {
            final char second = str.charAt(off++);
            --len;
            this.write(this.convertSurrogate(second));
        }
        int outPtr = this._outPtr;
        final byte[] outBuf = this._outBuffer;
        final int outBufLast = this._outBufferEnd;
        len += off;
        while (off < len) {
            if (outPtr >= outBufLast) {
                this._out.write(outBuf, 0, outPtr);
                outPtr = 0;
            }
            int c = str.charAt(off++);
            Label_0201: {
                if (c < 128) {
                    outBuf[outPtr++] = (byte)c;
                    int maxInCount = len - off;
                    final int maxOutCount = outBufLast - outPtr;
                    if (maxInCount > maxOutCount) {
                        maxInCount = maxOutCount;
                    }
                    maxInCount += off;
                    while (off < maxInCount) {
                        c = str.charAt(off++);
                        if (c >= 128) {
                            break Label_0201;
                        }
                        outBuf[outPtr++] = (byte)c;
                    }
                    continue;
                }
            }
            if (c < 2048) {
                outBuf[outPtr++] = (byte)(0xC0 | c >> 6);
                outBuf[outPtr++] = (byte)(0x80 | (c & 0x3F));
            }
            else if (c < 55296 || c > 57343) {
                outBuf[outPtr++] = (byte)(0xE0 | c >> 12);
                outBuf[outPtr++] = (byte)(0x80 | (c >> 6 & 0x3F));
                outBuf[outPtr++] = (byte)(0x80 | (c & 0x3F));
            }
            else {
                if (c > 56319) {
                    this._outPtr = outPtr;
                    illegalSurrogate(c);
                }
                this._surrogate = c;
                if (off >= len) {
                    break;
                }
                c = this.convertSurrogate(str.charAt(off++));
                if (c > 1114111) {
                    this._outPtr = outPtr;
                    illegalSurrogate(c);
                }
                outBuf[outPtr++] = (byte)(0xF0 | c >> 18);
                outBuf[outPtr++] = (byte)(0x80 | (c >> 12 & 0x3F));
                outBuf[outPtr++] = (byte)(0x80 | (c >> 6 & 0x3F));
                outBuf[outPtr++] = (byte)(0x80 | (c & 0x3F));
            }
        }
        this._outPtr = outPtr;
    }
    
    protected int convertSurrogate(final int secondPart) throws IOException {
        final int firstPart = this._surrogate;
        this._surrogate = 0;
        if (secondPart < 56320 || secondPart > 57343) {
            throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(firstPart) + ", second 0x" + Integer.toHexString(secondPart) + "; illegal combination");
        }
        return 65536 + (firstPart - 55296 << 10) + (secondPart - 56320);
    }
    
    protected static void illegalSurrogate(final int code) throws IOException {
        throw new IOException(illegalSurrogateDesc(code));
    }
    
    protected static String illegalSurrogateDesc(final int code) {
        if (code > 1114111) {
            return "Illegal character point (0x" + Integer.toHexString(code) + ") to output; max is 0x10FFFF as per RFC 4627";
        }
        if (code < 55296) {
            return "Illegal character point (0x" + Integer.toHexString(code) + ") to output";
        }
        if (code <= 56319) {
            return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(code) + ")";
        }
        return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(code) + ")";
    }
    
    @Override
    public /* bridge */ Appendable append(final char x0) throws IOException {
        return this.append(x0);
    }
}
