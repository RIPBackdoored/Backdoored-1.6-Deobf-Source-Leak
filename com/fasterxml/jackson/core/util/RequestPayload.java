package com.fasterxml.jackson.core.util;

import java.io.*;

public class RequestPayload implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected byte[] _payloadAsBytes;
    protected CharSequence _payloadAsText;
    protected String _charset;
    
    public RequestPayload(final byte[] bytes, final String charset) {
        super();
        if (bytes == null) {
            throw new IllegalArgumentException();
        }
        this._payloadAsBytes = bytes;
        this._charset = ((charset == null || charset.isEmpty()) ? "UTF-8" : charset);
    }
    
    public RequestPayload(final CharSequence str) {
        super();
        if (str == null) {
            throw new IllegalArgumentException();
        }
        this._payloadAsText = str;
    }
    
    public Object getRawPayload() {
        if (this._payloadAsBytes != null) {
            return this._payloadAsBytes;
        }
        return this._payloadAsText;
    }
    
    @Override
    public String toString() {
        if (this._payloadAsBytes != null) {
            try {
                return new String(this._payloadAsBytes, this._charset);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this._payloadAsText.toString();
    }
}
