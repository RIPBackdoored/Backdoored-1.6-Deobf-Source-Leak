package com.google.api.client.repackaged.org.apache.commons.codec.binary;

import com.google.api.client.repackaged.org.apache.commons.codec.*;

public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder
{
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    protected final byte PAD = 61;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;
    protected byte[] buffer;
    protected int pos;
    private int readPos;
    protected boolean eof;
    protected int currentLinePos;
    protected int modulus;
    
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength) {
        super();
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        this.lineLength = ((lineLength > 0 && chunkSeparatorLength > 0) ? (lineLength / encodedBlockSize * encodedBlockSize) : 0);
        this.chunkSeparatorLength = chunkSeparatorLength;
    }
    
    boolean hasData() {
        return this.buffer != null;
    }
    
    int available() {
        return (this.buffer != null) ? (this.pos - this.readPos) : 0;
    }
    
    protected int getDefaultBufferSize() {
        return 8192;
    }
    
    private void resizeBuffer() {
        if (this.buffer == null) {
            this.buffer = new byte[this.getDefaultBufferSize()];
            this.pos = 0;
            this.readPos = 0;
        }
        else {
            final byte[] b = new byte[this.buffer.length * 2];
            System.arraycopy(this.buffer, 0, b, 0, this.buffer.length);
            this.buffer = b;
        }
    }
    
    protected void ensureBufferSize(final int size) {
        if (this.buffer == null || this.buffer.length < this.pos + size) {
            this.resizeBuffer();
        }
    }
    
    int readResults(final byte[] b, final int bPos, final int bAvail) {
        if (this.buffer != null) {
            final int len = Math.min(this.available(), bAvail);
            System.arraycopy(this.buffer, this.readPos, b, bPos, len);
            this.readPos += len;
            if (this.readPos >= this.pos) {
                this.buffer = null;
            }
            return len;
        }
        return this.eof ? -1 : 0;
    }
    
    protected static boolean isWhiteSpace(final byte byteToCheck) {
        switch (byteToCheck) {
            case 9:
            case 10:
            case 13:
            case 32: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private void reset() {
        this.buffer = null;
        this.pos = 0;
        this.readPos = 0;
        this.currentLinePos = 0;
        this.modulus = 0;
        this.eof = false;
    }
    
    public Object encode(final Object pObject) throws EncoderException {
        if (!(pObject instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return this.encode((byte[])pObject);
    }
    
    public String encodeToString(final byte[] pArray) {
        return StringUtils.newStringUtf8(this.encode(pArray));
    }
    
    public Object decode(final Object pObject) throws DecoderException {
        if (pObject instanceof byte[]) {
            return this.decode((byte[])pObject);
        }
        if (pObject instanceof String) {
            return this.decode((String)pObject);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }
    
    public byte[] decode(final String pArray) {
        return this.decode(StringUtils.getBytesUtf8(pArray));
    }
    
    public byte[] decode(final byte[] pArray) {
        this.reset();
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        this.decode(pArray, 0, pArray.length);
        this.decode(pArray, 0, -1);
        final byte[] result = new byte[this.pos];
        this.readResults(result, 0, result.length);
        return result;
    }
    
    public byte[] encode(final byte[] pArray) {
        this.reset();
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        this.encode(pArray, 0, pArray.length);
        this.encode(pArray, 0, -1);
        final byte[] buf = new byte[this.pos - this.readPos];
        this.readResults(buf, 0, buf.length);
        return buf;
    }
    
    public String encodeAsString(final byte[] pArray) {
        return StringUtils.newStringUtf8(this.encode(pArray));
    }
    
    abstract void encode(final byte[] p0, final int p1, final int p2);
    
    abstract void decode(final byte[] p0, final int p1, final int p2);
    
    protected abstract boolean isInAlphabet(final byte p0);
    
    public boolean isInAlphabet(final byte[] arrayOctet, final boolean allowWSPad) {
        for (int i = 0; i < arrayOctet.length; ++i) {
            if (!this.isInAlphabet(arrayOctet[i]) && (!allowWSPad || (arrayOctet[i] != 61 && !isWhiteSpace(arrayOctet[i])))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isInAlphabet(final String basen) {
        return this.isInAlphabet(StringUtils.getBytesUtf8(basen), true);
    }
    
    protected boolean containsAlphabetOrPad(final byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        }
        for (final byte element : arrayOctet) {
            if (61 == element || this.isInAlphabet(element)) {
                return true;
            }
        }
        return false;
    }
    
    public long getEncodedLength(final byte[] pArray) {
        long len = (pArray.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            len += (len + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
        }
        return len;
    }
}
