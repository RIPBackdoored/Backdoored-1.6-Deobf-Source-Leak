package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeleteDimensionGroupRequest extends GenericJson
{
    @Key
    private DimensionRange range;
    
    public DeleteDimensionGroupRequest() {
        super();
    }
    
    public DimensionRange getRange() {
        return this.range;
    }
    
    public DeleteDimensionGroupRequest setRange(final DimensionRange range) {
        this.range = range;
        return this;
    }
    
    @Override
    public DeleteDimensionGroupRequest set(final String fieldName, final Object value) {
        return (DeleteDimensionGroupRequest)super.set(fieldName, value);
    }
    
    @Override
    public DeleteDimensionGroupRequest clone() {
        return (DeleteDimensionGroupRequest)super.clone();
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
