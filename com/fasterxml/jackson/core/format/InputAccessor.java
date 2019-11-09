package com.fasterxml.jackson.core.format;

import java.io.*;
import com.fasterxml.jackson.core.*;

public interface InputAccessor
{
    boolean hasMoreBytes() throws IOException;
    
    byte nextByte() throws IOException;
    
    void reset();
    
    public static class Std implements InputAccessor
    {
        protected final InputStream _in;
        protected final byte[] _buffer;
        protected final int _bufferedStart;
        protected int _bufferedEnd;
        protected int _ptr;
        
        public Std(final InputStream in, final byte[] buffer) {
            super();
            this._in = in;
            this._buffer = buffer;
            this._bufferedStart = 0;
            this._ptr = 0;
            this._bufferedEnd = 0;
        }
        
        public Std(final byte[] inputDocument) {
            super();
            this._in = null;
            this._buffer = inputDocument;
            this._bufferedStart = 0;
            this._bufferedEnd = inputDocument.length;
        }
        
        public Std(final byte[] inputDocument, final int start, final int len) {
            super();
            this._in = null;
            this._buffer = inputDocument;
            this._ptr = start;
            this._bufferedStart = start;
            this._bufferedEnd = start + len;
        }
        
        @Override
        public boolean hasMoreBytes() throws IOException {
            if (this._ptr < this._bufferedEnd) {
                return true;
            }
            if (this._in == null) {
                return false;
            }
            final int amount = this._buffer.length - this._ptr;
            if (amount < 1) {
                return false;
            }
            final int count = this._in.read(this._buffer, this._ptr, amount);
            if (count <= 0) {
                return false;
            }
            this._bufferedEnd += count;
            return true;
        }
        
        @Override
        public byte nextByte() throws IOException {
            if (this._ptr >= this._bufferedEnd && !this.hasMoreBytes()) {
                throw new EOFException("Failed auto-detect: could not read more than " + this._ptr + " bytes (max buffer size: " + this._buffer.length + ")");
            }
            return this._buffer[this._ptr++];
        }
        
        @Override
        public void reset() {
            this._ptr = this._bufferedStart;
        }
        
        public DataFormatMatcher createMatcher(final JsonFactory match, final MatchStrength matchStrength) {
            return new DataFormatMatcher(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, match, matchStrength);
        }
    }
}
