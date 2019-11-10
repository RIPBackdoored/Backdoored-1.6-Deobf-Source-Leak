package com.google.api.client.testing.json;

import com.google.api.client.util.*;
import com.google.api.client.json.*;
import java.io.*;
import java.math.*;

@Beta
public class MockJsonGenerator extends JsonGenerator
{
    private final JsonFactory factory;
    
    MockJsonGenerator(final JsonFactory factory) {
        super();
        this.factory = factory;
    }
    
    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }
    
    @Override
    public void flush() throws IOException {
    }
    
    @Override
    public void close() throws IOException {
    }
    
    @Override
    public void writeStartArray() throws IOException {
    }
    
    @Override
    public void writeEndArray() throws IOException {
    }
    
    @Override
    public void writeStartObject() throws IOException {
    }
    
    @Override
    public void writeEndObject() throws IOException {
    }
    
    @Override
    public void writeFieldName(final String name) throws IOException {
    }
    
    @Override
    public void writeNull() throws IOException {
    }
    
    @Override
    public void writeString(final String value) throws IOException {
    }
    
    @Override
    public void writeBoolean(final boolean state) throws IOException {
    }
    
    @Override
    public void writeNumber(final int v) throws IOException {
    }
    
    @Override
    public void writeNumber(final long v) throws IOException {
    }
    
    @Override
    public void writeNumber(final BigInteger v) throws IOException {
    }
    
    @Override
    public void writeNumber(final float v) throws IOException {
    }
    
    @Override
    public void writeNumber(final double v) throws IOException {
    }
    
    @Override
    public void writeNumber(final BigDecimal v) throws IOException {
    }
    
    @Override
    public void writeNumber(final String encodedValue) throws IOException {
    }
}
