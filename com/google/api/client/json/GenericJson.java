package com.google.api.client.json;

import com.google.api.client.util.*;
import java.io.*;

public class GenericJson extends GenericData implements Cloneable
{
    private JsonFactory jsonFactory;
    
    public GenericJson() {
        super();
    }
    
    public final JsonFactory getFactory() {
        return this.jsonFactory;
    }
    
    public final void setFactory(final JsonFactory factory) {
        this.jsonFactory = factory;
    }
    
    @Override
    public String toString() {
        if (this.jsonFactory != null) {
            try {
                return this.jsonFactory.toString(this);
            }
            catch (IOException e) {
                throw Throwables.propagate(e);
            }
        }
        return super.toString();
    }
    
    public String toPrettyString() throws IOException {
        if (this.jsonFactory != null) {
            return this.jsonFactory.toPrettyString(this);
        }
        return super.toString();
    }
    
    @Override
    public GenericJson clone() {
        return (GenericJson)super.clone();
    }
    
    @Override
    public GenericJson set(final String fieldName, final Object value) {
        return (GenericJson)super.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
