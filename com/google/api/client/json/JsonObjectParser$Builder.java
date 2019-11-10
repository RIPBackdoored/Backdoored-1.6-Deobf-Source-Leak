package com.google.api.client.json;

import java.util.*;
import com.google.api.client.util.*;

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
