package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AddSheetResponse extends GenericJson
{
    @Key
    private SheetProperties properties;
    
    public AddSheetResponse() {
        super();
    }
    
    public SheetProperties getProperties() {
        return this.properties;
    }
    
    public AddSheetResponse setProperties(final SheetProperties properties) {
        this.properties = properties;
        return this;
    }
    
    @Override
    public AddSheetResponse set(final String fieldName, final Object value) {
        return (AddSheetResponse)super.set(fieldName, value);
    }
    
    @Override
    public AddSheetResponse clone() {
        return (AddSheetResponse)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
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
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
