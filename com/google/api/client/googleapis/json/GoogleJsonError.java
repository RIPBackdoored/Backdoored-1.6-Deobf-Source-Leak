package com.google.api.client.googleapis.json;

import com.google.api.client.http.*;
import com.google.api.client.json.*;
import java.util.*;
import java.io.*;
import com.google.api.client.util.*;

public class GoogleJsonError extends GenericJson
{
    @Key
    private List<ErrorInfo> errors;
    @Key
    private int code;
    @Key
    private String message;
    
    public GoogleJsonError() {
        super();
    }
    
    public static GoogleJsonError parse(final JsonFactory jsonFactory, final HttpResponse response) throws IOException {
        final JsonObjectParser jsonObjectParser = new JsonObjectParser.Builder(jsonFactory).setWrapperKeys(Collections.singleton("error")).build();
        return jsonObjectParser.parseAndClose(response.getContent(), response.getContentCharset(), GoogleJsonError.class);
    }
    
    public final List<ErrorInfo> getErrors() {
        return this.errors;
    }
    
    public final void setErrors(final List<ErrorInfo> errors) {
        this.errors = errors;
    }
    
    public final int getCode() {
        return this.code;
    }
    
    public final void setCode(final int code) {
        this.code = code;
    }
    
    public final String getMessage() {
        return this.message;
    }
    
    public final void setMessage(final String message) {
        this.message = message;
    }
    
    @Override
    public GoogleJsonError set(final String fieldName, final Object value) {
        return (GoogleJsonError)super.set(fieldName, value);
    }
    
    @Override
    public GoogleJsonError clone() {
        return (GoogleJsonError)super.clone();
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
    
    static {
        Data.nullOf(ErrorInfo.class);
    }
    
    public static class ErrorInfo extends GenericJson
    {
        @Key
        private String domain;
        @Key
        private String reason;
        @Key
        private String message;
        @Key
        private String location;
        @Key
        private String locationType;
        
        public ErrorInfo() {
            super();
        }
        
        public final String getDomain() {
            return this.domain;
        }
        
        public final void setDomain(final String domain) {
            this.domain = domain;
        }
        
        public final String getReason() {
            return this.reason;
        }
        
        public final void setReason(final String reason) {
            this.reason = reason;
        }
        
        public final String getMessage() {
            return this.message;
        }
        
        public final void setMessage(final String message) {
            this.message = message;
        }
        
        public final String getLocation() {
            return this.location;
        }
        
        public final void setLocation(final String location) {
            this.location = location;
        }
        
        public final String getLocationType() {
            return this.locationType;
        }
        
        public final void setLocationType(final String locationType) {
            this.locationType = locationType;
        }
        
        @Override
        public ErrorInfo set(final String fieldName, final Object value) {
            return (ErrorInfo)super.set(fieldName, value);
        }
        
        @Override
        public ErrorInfo clone() {
            return (ErrorInfo)super.clone();
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
}
