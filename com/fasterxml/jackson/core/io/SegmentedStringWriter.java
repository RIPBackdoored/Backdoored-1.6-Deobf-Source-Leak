package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.*;
import java.io.*;

public final class SegmentedStringWriter extends Writer
{
    private final TextBuffer _buffer;
    
    public SegmentedStringWriter(final BufferRecycler br) {
        super();
        this._buffer = new TextBuffer(br);
    }
    
    @Override
    public Writer append(final char c) {
        this.write(c);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence csq) {
        final String str = csq.toString();
        this._buffer.append(str, 0, str.length());
        return this;
    }
    
    @Override
    public Writer append(final CharSequence csq, final int start, final int end) {
        final String str = csq.subSequence(start, end).toString();
        this._buffer.append(str, 0, str.length());
        return this;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void write(final char[] cbuf) {
        this._buffer.append(cbuf, 0, cbuf.length);
    }
    
    @Override
    public void write(final char[] cbuf, final int off, final int len) {
        this._buffer.append(cbuf, off, len);
    }
    
    @Override
    public void write(final int c) {
        this._buffer.append((char)c);
    }
    
    @Override
    public void write(final String str) {
        this._buffer.append(str, 0, str.length());
    }
    
    @Override
    public void write(final String str, final int off, final int len) {
        this._buffer.append(str, off, len);
    }
    
    public String getAndClear() {
        final String result = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return result;
    }
    
    @Override
    public /* bridge */ Appendable append(final char x0) throws IOException {
        return this.append(x0);
    }
    
    @Override
    public /* bridge */ Appendable append(final CharSequence x0, final int x1, final int x2) throws IOException {
        return this.append(x0, x1, x2);
    }
    
    @Override
    public /* bridge */ Appendable append(final CharSequence x0) throws IOException {
        return this.append(x0);
    }
}
