package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class SearchDeveloperMetadataRequest extends GenericJson
{
    @Key
    private List<DataFilter> dataFilters;
    
    public SearchDeveloperMetadataRequest() {
        super();
    }
    
    public List<DataFilter> getDataFilters() {
        return this.dataFilters;
    }
    
    public SearchDeveloperMetadataRequest setDataFilters(final List<DataFilter> dataFilters) {
        this.dataFilters = dataFilters;
        return this;
    }
    
    @Override
    public SearchDeveloperMetadataRequest set(final String fieldName, final Object value) {
        return (SearchDeveloperMetadataRequest)super.set(fieldName, value);
    }
    
    @Override
    public SearchDeveloperMetadataRequest clone() {
        return (SearchDeveloperMetadataRequest)super.clone();
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
