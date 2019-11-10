package com.google.api.client.json.webtoken;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public static class Header extends GenericJson
{
    @Key("typ")
    private String type;
    @Key("cty")
    private String contentType;
    
    public Header() {
        super();
    }
    
    public final String getType() {
        return this.type;
    }
    
    public Header setType(final String type) {
        this.type = type;
        return this;
    }
    
    public final String getContentType() {
        return this.contentType;
    }
    
    public Header setContentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }
    
    @Override
    public Header set(final String fieldName, final Object value) {
        return (Header)super.set(fieldName, value);
    }
    
    @Override
    public Header clone() {
        return (Header)super.clone();
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
