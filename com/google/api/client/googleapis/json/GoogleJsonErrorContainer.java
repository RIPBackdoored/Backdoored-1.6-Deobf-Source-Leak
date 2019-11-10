package com.google.api.client.googleapis.json;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class GoogleJsonErrorContainer extends GenericJson
{
    @Key
    private GoogleJsonError error;
    
    public GoogleJsonErrorContainer() {
        super();
    }
    
    public final GoogleJsonError getError() {
        return this.error;
    }
    
    public final void setError(final GoogleJsonError error) {
        this.error = error;
    }
    
    @Override
    public GoogleJsonErrorContainer set(final String fieldName, final Object value) {
        return (GoogleJsonErrorContainer)super.set(fieldName, value);
    }
    
    @Override
    public GoogleJsonErrorContainer clone() {
        return (GoogleJsonErrorContainer)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String fieldName, final Object value) {
        return this.set(fieldName, value);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
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
