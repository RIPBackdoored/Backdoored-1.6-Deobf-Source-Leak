package com.fasterxml.jackson.core;

import java.io.*;
import java.nio.charset.*;

public class JsonLocation implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final int MAX_CONTENT_SNIPPET = 500;
    public static final JsonLocation NA;
    protected final long _totalBytes;
    protected final long _totalChars;
    protected final int _lineNr;
    protected final int _columnNr;
    final transient Object _sourceRef;
    
    public JsonLocation(final Object srcRef, final long totalChars, final int lineNr, final int colNr) {
        this(srcRef, -1L, totalChars, lineNr, colNr);
    }
    
    public JsonLocation(final Object sourceRef, final long totalBytes, final long totalChars, final int lineNr, final int columnNr) {
        super();
        this._sourceRef = sourceRef;
        this._totalBytes = totalBytes;
        this._totalChars = totalChars;
        this._lineNr = lineNr;
        this._columnNr = columnNr;
    }
    
    public Object getSourceRef() {
        return this._sourceRef;
    }
    
    public int getLineNr() {
        return this._lineNr;
    }
    
    public int getColumnNr() {
        return this._columnNr;
    }
    
    public long getCharOffset() {
        return this._totalChars;
    }
    
    public long getByteOffset() {
        return this._totalBytes;
    }
    
    public String sourceDescription() {
        return this._appendSourceDesc(new StringBuilder(100)).toString();
    }
    
    @Override
    public int hashCode() {
        int hash = (this._sourceRef == null) ? 1 : this._sourceRef.hashCode();
        hash ^= this._lineNr;
        hash += this._columnNr;
        hash ^= (int)this._totalChars;
        hash += (int)this._totalBytes;
        return hash;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof JsonLocation)) {
            return false;
        }
        final JsonLocation otherLoc = (JsonLocation)other;
        if (this._sourceRef == null) {
            if (otherLoc._sourceRef != null) {
                return false;
            }
        }
        else if (!this._sourceRef.equals(otherLoc._sourceRef)) {
            return false;
        }
        return this._lineNr == otherLoc._lineNr && this._columnNr == otherLoc._columnNr && this._totalChars == otherLoc._totalChars && this.getByteOffset() == otherLoc.getByteOffset();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append("[Source: ");
        this._appendSourceDesc(sb);
        sb.append("; line: ");
        sb.append(this._lineNr);
        sb.append(", column: ");
        sb.append(this._columnNr);
        sb.append(']');
        return sb.toString();
    }
    
    protected StringBuilder _appendSourceDesc(final StringBuilder sb) {
        final Object srcRef = this._sourceRef;
        if (srcRef == null) {
            sb.append("UNKNOWN");
            return sb;
        }
        final Class<?> srcType = (Class<?>)((srcRef instanceof Class) ? ((Class)srcRef) : srcRef.getClass());
        String tn = srcType.getName();
        if (tn.startsWith("java.")) {
            tn = srcType.getSimpleName();
        }
        else if (srcRef instanceof byte[]) {
            tn = "byte[]";
        }
        else if (srcRef instanceof char[]) {
            tn = "char[]";
        }
        sb.append('(').append(tn).append(')');
        String charStr = " chars";
        int len;
        if (srcRef instanceof CharSequence) {
            final CharSequence cs = (CharSequence)srcRef;
            len = cs.length();
            len -= this._append(sb, cs.subSequence(0, Math.min(len, 500)).toString());
        }
        else if (srcRef instanceof char[]) {
            final char[] ch = (char[])srcRef;
            len = ch.length;
            len -= this._append(sb, new String(ch, 0, Math.min(len, 500)));
        }
        else if (srcRef instanceof byte[]) {
            final byte[] b = (byte[])srcRef;
            final int maxLen = Math.min(b.length, 500);
            this._append(sb, new String(b, 0, maxLen, Charset.forName("UTF-8")));
            len = b.length - maxLen;
            charStr = " bytes";
        }
        else {
            len = 0;
        }
        if (len > 0) {
            sb.append("[truncated ").append(len).append(charStr).append(']');
        }
        return sb;
    }
    
    private int _append(final StringBuilder sb, final String content) {
        sb.append('\"').append(content).append('\"');
        return content.length();
    }
    
    static {
        NA = new JsonLocation(null, -1L, -1L, -1, -1);
    }
}
