package com.google.api.client.auth.oauth2;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public class TokenErrorResponse extends GenericJson
{
    @Key
    private String error;
    @Key("error_description")
    private String errorDescription;
    @Key("error_uri")
    private String errorUri;
    
    public TokenErrorResponse() {
        super();
    }
    
    public final String getError() {
        return this.error;
    }
    
    public TokenErrorResponse setError(final String error) {
        this.error = Preconditions.checkNotNull(error);
        return this;
    }
    
    public final String getErrorDescription() {
        return this.errorDescription;
    }
    
    public TokenErrorResponse setErrorDescription(final String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }
    
    public final String getErrorUri() {
        return this.errorUri;
    }
    
    public TokenErrorResponse setErrorUri(final String errorUri) {
        this.errorUri = errorUri;
        return this;
    }
    
    @Override
    public TokenErrorResponse set(final String fieldName, final Object value) {
        return (TokenErrorResponse)super.set(fieldName, value);
    }
    
    @Override
    public TokenErrorResponse clone() {
        return (TokenErrorResponse)super.clone();
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
