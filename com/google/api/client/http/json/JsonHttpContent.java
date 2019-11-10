package com.google.api.client.http.json;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import java.io.*;
import com.google.api.client.http.*;

public class JsonHttpContent extends AbstractHttpContent
{
    private final Object data;
    private final JsonFactory jsonFactory;
    private String wrapperKey;
    
    public JsonHttpContent(final JsonFactory jsonFactory, final Object data) {
        super("application/json; charset=UTF-8");
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        this.data = Preconditions.checkNotNull(data);
    }
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        final JsonGenerator generator = this.jsonFactory.createJsonGenerator(out, this.getCharset());
        if (this.wrapperKey != null) {
            generator.writeStartObject();
            generator.writeFieldName(this.wrapperKey);
        }
        generator.serialize(this.data);
        if (this.wrapperKey != null) {
            generator.writeEndObject();
        }
        generator.flush();
    }
    
    @Override
    public JsonHttpContent setMediaType(final HttpMediaType mediaType) {
        super.setMediaType(mediaType);
        return this;
    }
    
    public final Object getData() {
        return this.data;
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public final String getWrapperKey() {
        return this.wrapperKey;
    }
    
    public JsonHttpContent setWrapperKey(final String wrapperKey) {
        this.wrapperKey = wrapperKey;
        return this;
    }
    
    @Override
    public /* bridge */ AbstractHttpContent setMediaType(final HttpMediaType mediaType) {
        return this.setMediaType(mediaType);
    }
}
