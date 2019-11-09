package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.*;

public class JsonParseException extends JsonProcessingException
{
    private static final long serialVersionUID = 2L;
    protected transient JsonParser _processor;
    protected RequestPayload _requestPayload;
    
    @Deprecated
    public JsonParseException(final String msg, final JsonLocation loc) {
        super(msg, loc);
    }
    
    @Deprecated
    public JsonParseException(final String msg, final JsonLocation loc, final Throwable root) {
        super(msg, loc, root);
    }
    
    public JsonParseException(final JsonParser p, final String msg) {
        super(msg, (p == null) ? null : p.getCurrentLocation());
        this._processor = p;
    }
    
    public JsonParseException(final JsonParser p, final String msg, final Throwable root) {
        super(msg, (p == null) ? null : p.getCurrentLocation(), root);
        this._processor = p;
    }
    
    public JsonParseException(final JsonParser p, final String msg, final JsonLocation loc) {
        super(msg, loc);
        this._processor = p;
    }
    
    public JsonParseException(final JsonParser p, final String msg, final JsonLocation loc, final Throwable root) {
        super(msg, loc, root);
        this._processor = p;
    }
    
    public JsonParseException withParser(final JsonParser p) {
        this._processor = p;
        return this;
    }
    
    public JsonParseException withRequestPayload(final RequestPayload p) {
        this._requestPayload = p;
        return this;
    }
    
    @Override
    public JsonParser getProcessor() {
        return this._processor;
    }
    
    public RequestPayload getRequestPayload() {
        return this._requestPayload;
    }
    
    public String getRequestPayloadAsString() {
        return (this._requestPayload != null) ? this._requestPayload.toString() : null;
    }
    
    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if (this._requestPayload != null) {
            msg = msg + "\nRequest payload : " + this._requestPayload.toString();
        }
        return msg;
    }
    
    @Override
    public /* bridge */ Object getProcessor() {
        return this.getProcessor();
    }
}
