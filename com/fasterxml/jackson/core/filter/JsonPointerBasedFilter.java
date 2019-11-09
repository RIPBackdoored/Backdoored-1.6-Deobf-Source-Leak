package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.*;

public class JsonPointerBasedFilter extends TokenFilter
{
    protected final JsonPointer _pathToMatch;
    
    public JsonPointerBasedFilter(final String ptrExpr) {
        this(JsonPointer.compile(ptrExpr));
    }
    
    public JsonPointerBasedFilter(final JsonPointer match) {
        super();
        this._pathToMatch = match;
    }
    
    @Override
    public TokenFilter includeElement(final int index) {
        final JsonPointer next = this._pathToMatch.matchElement(index);
        if (next == null) {
            return null;
        }
        if (next.matches()) {
            return TokenFilter.INCLUDE_ALL;
        }
        return new JsonPointerBasedFilter(next);
    }
    
    @Override
    public TokenFilter includeProperty(final String name) {
        final JsonPointer next = this._pathToMatch.matchProperty(name);
        if (next == null) {
            return null;
        }
        if (next.matches()) {
            return TokenFilter.INCLUDE_ALL;
        }
        return new JsonPointerBasedFilter(next);
    }
    
    @Override
    public TokenFilter filterStartArray() {
        return this;
    }
    
    @Override
    public TokenFilter filterStartObject() {
        return this;
    }
    
    @Override
    protected boolean _includeScalar() {
        return this._pathToMatch.matches();
    }
    
    @Override
    public String toString() {
        return "[JsonPointerFilter at: " + this._pathToMatch + "]";
    }
}
