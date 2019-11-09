package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.*;

public class JsonEOFException extends JsonParseException
{
    private static final long serialVersionUID = 1L;
    protected final JsonToken _token;
    
    public JsonEOFException(final JsonParser p, final JsonToken token, final String msg) {
        super(p, msg);
        this._token = token;
    }
    
    public JsonToken getTokenBeingDecoded() {
        return this._token;
    }
}
