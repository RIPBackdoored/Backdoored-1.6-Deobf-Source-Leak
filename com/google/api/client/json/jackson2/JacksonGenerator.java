package com.google.api.client.json.jackson2;

import java.io.*;
import java.math.*;
import com.google.api.client.json.*;

final class JacksonGenerator extends JsonGenerator
{
    private final com.fasterxml.jackson.core.JsonGenerator generator;
    private final JacksonFactory factory;
    
    @Override
    public JacksonFactory getFactory() {
        return this.factory;
    }
    
    JacksonGenerator(final JacksonFactory factory, final com.fasterxml.jackson.core.JsonGenerator generator) {
        super();
        this.factory = factory;
        this.generator = generator;
    }
    
    @Override
    public void flush() throws IOException {
        this.generator.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.generator.close();
    }
    
    @Override
    public void writeBoolean(final boolean state) throws IOException {
        this.generator.writeBoolean(state);
    }
    
    @Override
    public void writeEndArray() throws IOException {
        this.generator.writeEndArray();
    }
    
    @Override
    public void writeEndObject() throws IOException {
        this.generator.writeEndObject();
    }
    
    @Override
    public void writeFieldName(final String name) throws IOException {
        this.generator.writeFieldName(name);
    }
    
    @Override
    public void writeNull() throws IOException {
        this.generator.writeNull();
    }
    
    @Override
    public void writeNumber(final int v) throws IOException {
        this.generator.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final long v) throws IOException {
        this.generator.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final BigInteger v) throws IOException {
        this.generator.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final double v) throws IOException {
        this.generator.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final float v) throws IOException {
        this.generator.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final BigDecimal v) throws IOException {
        this.generator.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final String encodedValue) throws IOException {
        this.generator.writeNumber(encodedValue);
    }
    
    @Override
    public void writeStartArray() throws IOException {
        this.generator.writeStartArray();
    }
    
    @Override
    public void writeStartObject() throws IOException {
        this.generator.writeStartObject();
    }
    
    @Override
    public void writeString(final String value) throws IOException {
        this.generator.writeString(value);
    }
    
    @Override
    public void enablePrettyPrint() throws IOException {
        this.generator.useDefaultPrettyPrinter();
    }
    
    @Override
    public /* bridge */ JsonFactory getFactory() {
        return this.getFactory();
    }
}
