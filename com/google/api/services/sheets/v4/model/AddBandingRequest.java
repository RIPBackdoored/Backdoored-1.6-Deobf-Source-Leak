package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class AddBandingRequest extends GenericJson
{
    @Key
    private BandedRange bandedRange;
    
    public AddBandingRequest() {
        super();
    }
    
    public BandedRange getBandedRange() {
        return this.bandedRange;
    }
    
    public AddBandingRequest setBandedRange(final BandedRange bandedRange) {
        this.bandedRange = bandedRange;
        return this;
    }
    
    @Override
    public AddBandingRequest set(final String fieldName, final Object value) {
        return (AddBandingRequest)super.set(fieldName, value);
    }
    
    @Override
    public AddBandingRequest clone() {
        return (AddBandingRequest)super.clone();
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
