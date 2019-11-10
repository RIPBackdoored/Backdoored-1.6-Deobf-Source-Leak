package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class PivotGroupSortValueBucket extends GenericJson
{
    @Key
    private List<ExtendedValue> buckets;
    @Key
    private Integer valuesIndex;
    
    public PivotGroupSortValueBucket() {
        super();
    }
    
    public List<ExtendedValue> getBuckets() {
        return this.buckets;
    }
    
    public PivotGroupSortValueBucket setBuckets(final List<ExtendedValue> buckets) {
        this.buckets = buckets;
        return this;
    }
    
    public Integer getValuesIndex() {
        return this.valuesIndex;
    }
    
    public PivotGroupSortValueBucket setValuesIndex(final Integer valuesIndex) {
        this.valuesIndex = valuesIndex;
        return this;
    }
    
    @Override
    public PivotGroupSortValueBucket set(final String fieldName, final Object value) {
        return (PivotGroupSortValueBucket)super.set(fieldName, value);
    }
    
    @Override
    public PivotGroupSortValueBucket clone() {
        return (PivotGroupSortValueBucket)super.clone();
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
