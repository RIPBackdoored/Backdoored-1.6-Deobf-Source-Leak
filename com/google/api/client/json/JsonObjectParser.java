package com.google.api.client.json;

import java.nio.charset.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import com.google.api.client.util.*;

public class JsonObjectParser implements ObjectParser
{
    private final JsonFactory jsonFactory;
    private final Set<String> wrapperKeys;
    
    public JsonObjectParser(final JsonFactory jsonFactory) {
        this(new Builder(jsonFactory));
    }
    
    protected JsonObjectParser(final Builder builder) {
        super();
        this.jsonFactory = builder.jsonFactory;
        this.wrapperKeys = new HashSet<String>(builder.wrapperKeys);
    }
    
    @Override
    public <T> T parseAndClose(final InputStream in, final Charset charset, final Class<T> dataClass) throws IOException {
        return (T)this.parseAndClose(in, charset, (Type)dataClass);
    }
    
    @Override
    public Object parseAndClose(final InputStream in, final Charset charset, final Type dataType) throws IOException {
        final JsonParser parser = this.jsonFactory.createJsonParser(in, charset);
        this.initializeParser(parser);
        return parser.parse(dataType, true);
    }
    
    @Override
    public <T> T parseAndClose(final Reader reader, final Class<T> dataClass) throws IOException {
        return (T)this.parseAndClose(reader, (Type)dataClass);
    }
    
    @Override
    public Object parseAndClose(final Reader reader, final Type dataType) throws IOException {
        final JsonParser parser = this.jsonFactory.createJsonParser(reader);
        this.initializeParser(parser);
        return parser.parse(dataType, true);
    }
    
    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public Set<String> getWrapperKeys() {
        return Collections.unmodifiableSet((Set<? extends String>)this.wrapperKeys);
    }
    
    private void initializeParser(final JsonParser parser) throws IOException {
        if (this.wrapperKeys.isEmpty()) {
            return;
        }
        boolean failed = true;
        try {
            final String match = parser.skipToKey(this.wrapperKeys);
            Preconditions.checkArgument(match != null && parser.getCurrentToken() != JsonToken.END_OBJECT, "wrapper key(s) not found: %s", this.wrapperKeys);
            failed = false;
        }
        finally {
            if (failed) {
                parser.close();
            }
        }
    }
    
    public static class Builder
    {
        final JsonFactory jsonFactory;
        Collection<String> wrapperKeys;
        
        public Builder(final JsonFactory jsonFactory) {
            super();
            this.wrapperKeys = (Collection<String>)Sets.newHashSet();
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }
        
        public JsonObjectParser build() {
            return new JsonObjectParser(this);
        }
        
        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }
        
        public final Collection<String> getWrapperKeys() {
            return this.wrapperKeys;
        }
        
        public Builder setWrapperKeys(final Collection<String> wrapperKeys) {
            this.wrapperKeys = wrapperKeys;
            return this;
        }
    }
}
