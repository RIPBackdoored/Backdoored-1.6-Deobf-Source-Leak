package com.google.api.client.json;

import java.nio.charset.*;
import java.io.*;
import com.google.api.client.util.*;

public abstract class JsonFactory
{
    public JsonFactory() {
        super();
    }
    
    public abstract JsonParser createJsonParser(final InputStream p0) throws IOException;
    
    public abstract JsonParser createJsonParser(final InputStream p0, final Charset p1) throws IOException;
    
    public abstract JsonParser createJsonParser(final String p0) throws IOException;
    
    public abstract JsonParser createJsonParser(final Reader p0) throws IOException;
    
    public abstract JsonGenerator createJsonGenerator(final OutputStream p0, final Charset p1) throws IOException;
    
    public abstract JsonGenerator createJsonGenerator(final Writer p0) throws IOException;
    
    public final JsonObjectParser createJsonObjectParser() {
        return new JsonObjectParser(this);
    }
    
    public final String toString(final Object item) throws IOException {
        return this.toString(item, false);
    }
    
    public final String toPrettyString(final Object item) throws IOException {
        return this.toString(item, true);
    }
    
    public final byte[] toByteArray(final Object item) throws IOException {
        return this.toByteStream(item, false).toByteArray();
    }
    
    private String toString(final Object item, final boolean pretty) throws IOException {
        return this.toByteStream(item, pretty).toString("UTF-8");
    }
    
    private ByteArrayOutputStream toByteStream(final Object item, final boolean pretty) throws IOException {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final JsonGenerator generator = this.createJsonGenerator(byteStream, Charsets.UTF_8);
        if (pretty) {
            generator.enablePrettyPrint();
        }
        generator.serialize(item);
        generator.flush();
        return byteStream;
    }
    
    public final <T> T fromString(final String value, final Class<T> destinationClass) throws IOException {
        return this.createJsonParser(value).parse(destinationClass);
    }
    
    public final <T> T fromInputStream(final InputStream inputStream, final Class<T> destinationClass) throws IOException {
        return this.createJsonParser(inputStream).parseAndClose(destinationClass);
    }
    
    public final <T> T fromInputStream(final InputStream inputStream, final Charset charset, final Class<T> destinationClass) throws IOException {
        return this.createJsonParser(inputStream, charset).parseAndClose(destinationClass);
    }
    
    public final <T> T fromReader(final Reader reader, final Class<T> destinationClass) throws IOException {
        return this.createJsonParser(reader).parseAndClose(destinationClass);
    }
}
