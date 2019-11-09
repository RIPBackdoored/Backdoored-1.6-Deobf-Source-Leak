package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;

public class IOContext
{
    protected final Object _sourceRef;
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected final BufferRecycler _bufferRecycler;
    protected byte[] _readIOBuffer;
    protected byte[] _writeEncodingBuffer;
    protected byte[] _base64Buffer;
    protected char[] _tokenCBuffer;
    protected char[] _concatCBuffer;
    protected char[] _nameCopyBuffer;
    
    public IOContext(final BufferRecycler br, final Object sourceRef, final boolean managedResource) {
        super();
        this._bufferRecycler = br;
        this._sourceRef = sourceRef;
        this._managedResource = managedResource;
    }
    
    public void setEncoding(final JsonEncoding enc) {
        this._encoding = enc;
    }
    
    public IOContext withEncoding(final JsonEncoding enc) {
        this._encoding = enc;
        return this;
    }
    
    public Object getSourceReference() {
        return this._sourceRef;
    }
    
    public JsonEncoding getEncoding() {
        return this._encoding;
    }
    
    public boolean isResourceManaged() {
        return this._managedResource;
    }
    
    public TextBuffer constructTextBuffer() {
        return new TextBuffer(this._bufferRecycler);
    }
    
    public byte[] allocReadIOBuffer() {
        this._verifyAlloc(this._readIOBuffer);
        return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0);
    }
    
    public byte[] allocReadIOBuffer(final int minSize) {
        this._verifyAlloc(this._readIOBuffer);
        return this._readIOBuffer = this._bufferRecycler.allocByteBuffer(0, minSize);
    }
    
    public byte[] allocWriteEncodingBuffer() {
        this._verifyAlloc(this._writeEncodingBuffer);
        return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1);
    }
    
    public byte[] allocWriteEncodingBuffer(final int minSize) {
        this._verifyAlloc(this._writeEncodingBuffer);
        return this._writeEncodingBuffer = this._bufferRecycler.allocByteBuffer(1, minSize);
    }
    
    public byte[] allocBase64Buffer() {
        this._verifyAlloc(this._base64Buffer);
        return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3);
    }
    
    public byte[] allocBase64Buffer(final int minSize) {
        this._verifyAlloc(this._base64Buffer);
        return this._base64Buffer = this._bufferRecycler.allocByteBuffer(3, minSize);
    }
    
    public char[] allocTokenBuffer() {
        this._verifyAlloc(this._tokenCBuffer);
        return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0);
    }
    
    public char[] allocTokenBuffer(final int minSize) {
        this._verifyAlloc(this._tokenCBuffer);
        return this._tokenCBuffer = this._bufferRecycler.allocCharBuffer(0, minSize);
    }
    
    public char[] allocConcatBuffer() {
        this._verifyAlloc(this._concatCBuffer);
        return this._concatCBuffer = this._bufferRecycler.allocCharBuffer(1);
    }
    
    public char[] allocNameCopyBuffer(final int minSize) {
        this._verifyAlloc(this._nameCopyBuffer);
        return this._nameCopyBuffer = this._bufferRecycler.allocCharBuffer(3, minSize);
    }
    
    public void releaseReadIOBuffer(final byte[] buf) {
        if (buf != null) {
            this._verifyRelease(buf, this._readIOBuffer);
            this._readIOBuffer = null;
            this._bufferRecycler.releaseByteBuffer(0, buf);
        }
    }
    
    public void releaseWriteEncodingBuffer(final byte[] buf) {
        if (buf != null) {
            this._verifyRelease(buf, this._writeEncodingBuffer);
            this._writeEncodingBuffer = null;
            this._bufferRecycler.releaseByteBuffer(1, buf);
        }
    }
    
    public void releaseBase64Buffer(final byte[] buf) {
        if (buf != null) {
            this._verifyRelease(buf, this._base64Buffer);
            this._base64Buffer = null;
            this._bufferRecycler.releaseByteBuffer(3, buf);
        }
    }
    
    public void releaseTokenBuffer(final char[] buf) {
        if (buf != null) {
            this._verifyRelease(buf, this._tokenCBuffer);
            this._tokenCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(0, buf);
        }
    }
    
    public void releaseConcatBuffer(final char[] buf) {
        if (buf != null) {
            this._verifyRelease(buf, this._concatCBuffer);
            this._concatCBuffer = null;
            this._bufferRecycler.releaseCharBuffer(1, buf);
        }
    }
    
    public void releaseNameCopyBuffer(final char[] buf) {
        if (buf != null) {
            this._verifyRelease(buf, this._nameCopyBuffer);
            this._nameCopyBuffer = null;
            this._bufferRecycler.releaseCharBuffer(3, buf);
        }
    }
    
    protected final void _verifyAlloc(final Object buffer) {
        if (buffer != null) {
            throw new IllegalStateException("Trying to call same allocXxx() method second time");
        }
    }
    
    protected final void _verifyRelease(final byte[] toRelease, final byte[] src) {
        if (toRelease != src && toRelease.length < src.length) {
            throw this.wrongBuf();
        }
    }
    
    protected final void _verifyRelease(final char[] toRelease, final char[] src) {
        if (toRelease != src && toRelease.length < src.length) {
            throw this.wrongBuf();
        }
    }
    
    private IllegalArgumentException wrongBuf() {
        return new IllegalArgumentException("Trying to release buffer smaller than original");
    }
}
