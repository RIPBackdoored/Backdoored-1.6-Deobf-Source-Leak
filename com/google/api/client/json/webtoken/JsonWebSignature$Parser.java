package com.google.api.client.json.webtoken;

import com.google.api.client.json.*;
import com.google.api.client.util.*;
import java.io.*;

public static final class Parser
{
    private final JsonFactory jsonFactory;
    private Class<? extends Header> headerClass;
    private Class<? extends Payload> payloadClass;
    
    public Parser(final JsonFactory jsonFactory) {
        super();
        this.headerClass = Header.class;
        this.payloadClass = Payload.class;
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
    }
    
    public Class<? extends Header> getHeaderClass() {
        return this.headerClass;
    }
    
    public Parser setHeaderClass(final Class<? extends Header> headerClass) {
        this.headerClass = headerClass;
        return this;
    }
    
    public Class<? extends Payload> getPayloadClass() {
        return this.payloadClass;
    }
    
    public Parser setPayloadClass(final Class<? extends Payload> payloadClass) {
        this.payloadClass = payloadClass;
        return this;
    }
    
    public JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    
    public JsonWebSignature parse(final String tokenString) throws IOException {
        final int firstDot = tokenString.indexOf(46);
        Preconditions.checkArgument(firstDot != -1);
        final byte[] headerBytes = Base64.decodeBase64(tokenString.substring(0, firstDot));
        final int secondDot = tokenString.indexOf(46, firstDot + 1);
        Preconditions.checkArgument(secondDot != -1);
        Preconditions.checkArgument(tokenString.indexOf(46, secondDot + 1) == -1);
        final byte[] payloadBytes = Base64.decodeBase64(tokenString.substring(firstDot + 1, secondDot));
        final byte[] signatureBytes = Base64.decodeBase64(tokenString.substring(secondDot + 1));
        final byte[] signedContentBytes = StringUtils.getBytesUtf8(tokenString.substring(0, secondDot));
        final Header header = this.jsonFactory.fromInputStream(new ByteArrayInputStream(headerBytes), this.headerClass);
        Preconditions.checkArgument(header.getAlgorithm() != null);
        final Payload payload = this.jsonFactory.fromInputStream(new ByteArrayInputStream(payloadBytes), this.payloadClass);
        return new JsonWebSignature(header, payload, signatureBytes, signedContentBytes);
    }
}
