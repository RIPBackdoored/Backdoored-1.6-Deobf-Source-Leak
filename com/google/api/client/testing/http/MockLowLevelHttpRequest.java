package com.google.api.client.testing.http;

import java.util.*;
import java.util.zip.*;
import java.io.*;
import com.google.api.client.http.*;
import com.google.api.client.util.*;
import java.nio.charset.*;

@Beta
public class MockLowLevelHttpRequest extends LowLevelHttpRequest
{
    private String url;
    private final Map<String, List<String>> headersMap;
    private MockLowLevelHttpResponse response;
    
    public MockLowLevelHttpRequest() {
        super();
        this.headersMap = new HashMap<String, List<String>>();
        this.response = new MockLowLevelHttpResponse();
    }
    
    public MockLowLevelHttpRequest(final String url) {
        super();
        this.headersMap = new HashMap<String, List<String>>();
        this.response = new MockLowLevelHttpResponse();
        this.url = url;
    }
    
    @Override
    public void addHeader(String name, final String value) throws IOException {
        name = name.toLowerCase(Locale.US);
        List<String> values = this.headersMap.get(name);
        if (values == null) {
            values = new ArrayList<String>();
            this.headersMap.put(name, values);
        }
        values.add(value);
    }
    
    @Override
    public LowLevelHttpResponse execute() throws IOException {
        return this.response;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends List<String>>)this.headersMap);
    }
    
    public String getFirstHeaderValue(final String name) {
        final List<String> values = this.headersMap.get(name.toLowerCase(Locale.US));
        return (values == null) ? null : values.get(0);
    }
    
    public List<String> getHeaderValues(final String name) {
        final List<String> values = this.headersMap.get(name.toLowerCase(Locale.US));
        return (values == null) ? Collections.emptyList() : Collections.unmodifiableList((List<? extends String>)values);
    }
    
    public MockLowLevelHttpRequest setUrl(final String url) {
        this.url = url;
        return this;
    }
    
    public String getContentAsString() throws IOException {
        if (this.getStreamingContent() == null) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.getStreamingContent().writeTo(out);
        final String contentEncoding = this.getContentEncoding();
        if (contentEncoding != null && contentEncoding.contains("gzip")) {
            final InputStream contentInputStream = new GZIPInputStream(new ByteArrayInputStream(out.toByteArray()));
            out = new ByteArrayOutputStream();
            IOUtils.copy(contentInputStream, out);
        }
        final String contentType = this.getContentType();
        final HttpMediaType mediaType = (contentType != null) ? new HttpMediaType(contentType) : null;
        final Charset charset = (mediaType == null || mediaType.getCharsetParameter() == null) ? Charsets.ISO_8859_1 : mediaType.getCharsetParameter();
        return out.toString(charset.name());
    }
    
    public MockLowLevelHttpResponse getResponse() {
        return this.response;
    }
    
    public MockLowLevelHttpRequest setResponse(final MockLowLevelHttpResponse response) {
        this.response = response;
        return this;
    }
}
