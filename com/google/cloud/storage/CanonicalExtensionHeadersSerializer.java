package com.google.cloud.storage;

import java.util.*;

public class CanonicalExtensionHeadersSerializer
{
    private static final char HEADER_SEPARATOR = ':';
    private static final char HEADER_NAME_SEPARATOR = ';';
    private final Storage.SignUrlOption.SignatureVersion signatureVersion;
    
    public CanonicalExtensionHeadersSerializer(final Storage.SignUrlOption.SignatureVersion signatureVersion) {
        super();
        this.signatureVersion = signatureVersion;
    }
    
    public CanonicalExtensionHeadersSerializer() {
        super();
        this.signatureVersion = Storage.SignUrlOption.SignatureVersion.V2;
    }
    
    public StringBuilder serialize(final Map<String, String> canonicalizedExtensionHeaders) {
        final StringBuilder serializedHeaders = new StringBuilder();
        if (canonicalizedExtensionHeaders == null || canonicalizedExtensionHeaders.isEmpty()) {
            return serializedHeaders;
        }
        final Map<String, String> lowercaseHeaders = this.getLowercaseHeaders(canonicalizedExtensionHeaders);
        final List<String> sortedHeaderNames = new ArrayList<String>(lowercaseHeaders.keySet());
        Collections.sort(sortedHeaderNames);
        for (final String headerName : sortedHeaderNames) {
            serializedHeaders.append(headerName).append(':').append(lowercaseHeaders.get(headerName).trim().replaceAll("[\\s]{2,}", " ").replaceAll("(\\t|\\r?\\n)+", " ")).append('\n');
        }
        return serializedHeaders;
    }
    
    public StringBuilder serializeHeaderNames(final Map<String, String> canonicalizedExtensionHeaders) {
        final StringBuilder serializedHeaders = new StringBuilder();
        if (canonicalizedExtensionHeaders == null || canonicalizedExtensionHeaders.isEmpty()) {
            return serializedHeaders;
        }
        final Map<String, String> lowercaseHeaders = this.getLowercaseHeaders(canonicalizedExtensionHeaders);
        final List<String> sortedHeaderNames = new ArrayList<String>(lowercaseHeaders.keySet());
        Collections.sort(sortedHeaderNames);
        for (final String headerName : sortedHeaderNames) {
            serializedHeaders.append(headerName).append(';');
        }
        serializedHeaders.setLength(serializedHeaders.length() - 1);
        return serializedHeaders;
    }
    
    private Map<String, String> getLowercaseHeaders(final Map<String, String> canonicalizedExtensionHeaders) {
        final Map<String, String> lowercaseHeaders = new HashMap<String, String>();
        for (final String headerName : new ArrayList<String>(canonicalizedExtensionHeaders.keySet())) {
            final String lowercaseHeaderName = headerName.toLowerCase();
            if (Storage.SignUrlOption.SignatureVersion.V2.equals(this.signatureVersion)) {
                if ("x-goog-encryption-key".equals(lowercaseHeaderName)) {
                    continue;
                }
                if ("x-goog-encryption-key-sha256".equals(lowercaseHeaderName)) {
                    continue;
                }
            }
            lowercaseHeaders.put(lowercaseHeaderName, canonicalizedExtensionHeaders.get(headerName));
        }
        return lowercaseHeaders;
    }
}
