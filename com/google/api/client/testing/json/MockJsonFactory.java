package com.google.api.client.testing.json;

import com.google.api.client.util.*;
import java.nio.charset.*;
import com.google.api.client.json.*;
import java.io.*;

@Beta
public class MockJsonFactory extends JsonFactory
{
    public MockJsonFactory() {
        super();
    }
    
    @Override
    public JsonParser createJsonParser(final InputStream in) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonParser createJsonParser(final InputStream in, final Charset charset) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonParser createJsonParser(final String value) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonParser createJsonParser(final Reader reader) throws IOException {
        return new MockJsonParser(this);
    }
    
    @Override
    public JsonGenerator createJsonGenerator(final OutputStream out, final Charset enc) throws IOException {
        return new MockJsonGenerator(this);
    }
    
    @Override
    public JsonGenerator createJsonGenerator(final Writer writer) throws IOException {
        return new MockJsonGenerator(this);
    }
}
