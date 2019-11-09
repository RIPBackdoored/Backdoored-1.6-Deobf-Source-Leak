package com.fasterxml.jackson.core;

public class JsonGenerationException extends JsonProcessingException
{
    private static final long serialVersionUID = 123L;
    protected transient JsonGenerator _processor;
    
    @Deprecated
    public JsonGenerationException(final Throwable rootCause) {
        super(rootCause);
    }
    
    @Deprecated
    public JsonGenerationException(final String msg) {
        super(msg, (JsonLocation)null);
    }
    
    @Deprecated
    public JsonGenerationException(final String msg, final Throwable rootCause) {
        super(msg, null, rootCause);
    }
    
    public JsonGenerationException(final Throwable rootCause, final JsonGenerator g) {
        super(rootCause);
        this._processor = g;
    }
    
    public JsonGenerationException(final String msg, final JsonGenerator g) {
        super(msg, (JsonLocation)null);
        this._processor = g;
    }
    
    public JsonGenerationException(final String msg, final Throwable rootCause, final JsonGenerator g) {
        super(msg, null, rootCause);
        this._processor = g;
    }
    
    public JsonGenerationException withGenerator(final JsonGenerator g) {
        this._processor = g;
        return this;
    }
    
    @Override
    public JsonGenerator getProcessor() {
        return this._processor;
    }
    
    @Override
    public /* bridge */ Object getProcessor() {
        return this.getProcessor();
    }
}
