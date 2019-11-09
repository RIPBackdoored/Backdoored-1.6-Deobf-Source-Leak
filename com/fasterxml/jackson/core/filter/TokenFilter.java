package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.*;
import java.io.*;
import java.math.*;

public class TokenFilter
{
    public static final TokenFilter INCLUDE_ALL;
    
    protected TokenFilter() {
        super();
    }
    
    public TokenFilter filterStartObject() {
        return this;
    }
    
    public TokenFilter filterStartArray() {
        return this;
    }
    
    public void filterFinishObject() {
    }
    
    public void filterFinishArray() {
    }
    
    public TokenFilter includeProperty(final String name) {
        return this;
    }
    
    public TokenFilter includeElement(final int index) {
        return this;
    }
    
    public TokenFilter includeRootValue(final int index) {
        return this;
    }
    
    public boolean includeValue(final JsonParser p) throws IOException {
        return this._includeScalar();
    }
    
    public boolean includeBoolean(final boolean value) {
        return this._includeScalar();
    }
    
    public boolean includeNull() {
        return this._includeScalar();
    }
    
    public boolean includeString(final String value) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final int v) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final long v) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final float v) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final double v) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final BigDecimal v) {
        return this._includeScalar();
    }
    
    public boolean includeNumber(final BigInteger v) {
        return this._includeScalar();
    }
    
    public boolean includeBinary() {
        return this._includeScalar();
    }
    
    public boolean includeRawValue() {
        return this._includeScalar();
    }
    
    public boolean includeEmbeddedValue(final Object ob) {
        return this._includeScalar();
    }
    
    @Override
    public String toString() {
        if (this == TokenFilter.INCLUDE_ALL) {
            return "TokenFilter.INCLUDE_ALL";
        }
        return super.toString();
    }
    
    protected boolean _includeScalar() {
        return true;
    }
    
    static {
        INCLUDE_ALL = new TokenFilter();
    }
}
