package com.google.api.client.json.jackson2;

import java.nio.charset.*;
import com.google.api.client.json.*;
import com.google.api.client.util.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public final class JacksonFactory extends JsonFactory
{
    private final com.fasterxml.jackson.core.JsonFactory factory;
    
    public JacksonFactory() {
        super();
        (this.factory = new com.fasterxml.jackson.core.JsonFactory()).configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
    }
    
    public static JacksonFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }
    
    @Override
    public com.google.api.client.json.JsonGenerator createJsonGenerator(final OutputStream out, final Charset enc) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(out, JsonEncoding.UTF8));
    }
    
    @Override
    public com.google.api.client.json.JsonGenerator createJsonGenerator(final Writer writer) throws IOException {
        return new JacksonGenerator(this, this.factory.createJsonGenerator(writer));
    }
    
    @Override
    public JsonParser createJsonParser(final Reader reader) throws IOException {
        Preconditions.checkNotNull(reader);
        return new JacksonParser(this, this.factory.createJsonParser(reader));
    }
    
    @Override
    public JsonParser createJsonParser(final InputStream in) throws IOException {
        Preconditions.checkNotNull(in);
        return new JacksonParser(this, this.factory.createJsonParser(in));
    }
    
    @Override
    public JsonParser createJsonParser(final InputStream in, final Charset charset) throws IOException {
        Preconditions.checkNotNull(in);
        return new JacksonParser(this, this.factory.createJsonParser(in));
    }
    
    @Override
    public JsonParser createJsonParser(final String value) throws IOException {
        Preconditions.checkNotNull(value);
        return new JacksonParser(this, this.factory.createJsonParser(value));
    }
    
    static com.google.api.client.json.JsonToken convert(final JsonToken token) {
        if (token == null) {
            return null;
        }
        switch (token) {
            case END_ARRAY: {
                return com.google.api.client.json.JsonToken.END_ARRAY;
            }
            case START_ARRAY: {
                return com.google.api.client.json.JsonToken.START_ARRAY;
            }
            case END_OBJECT: {
                return com.google.api.client.json.JsonToken.END_OBJECT;
            }
            case START_OBJECT: {
                return com.google.api.client.json.JsonToken.START_OBJECT;
            }
            case VALUE_FALSE: {
                return com.google.api.client.json.JsonToken.VALUE_FALSE;
            }
            case VALUE_TRUE: {
                return com.google.api.client.json.JsonToken.VALUE_TRUE;
            }
            case VALUE_NULL: {
                return com.google.api.client.json.JsonToken.VALUE_NULL;
            }
            case VALUE_STRING: {
                return com.google.api.client.json.JsonToken.VALUE_STRING;
            }
            case VALUE_NUMBER_FLOAT: {
                return com.google.api.client.json.JsonToken.VALUE_NUMBER_FLOAT;
            }
            case VALUE_NUMBER_INT: {
                return com.google.api.client.json.JsonToken.VALUE_NUMBER_INT;
            }
            case FIELD_NAME: {
                return com.google.api.client.json.JsonToken.FIELD_NAME;
            }
            default: {
                return com.google.api.client.json.JsonToken.NOT_AVAILABLE;
            }
        }
    }
    
    static class InstanceHolder
    {
        static final JacksonFactory INSTANCE;
        
        InstanceHolder() {
            super();
        }
        
        static {
            INSTANCE = new JacksonFactory();
        }
    }
}
