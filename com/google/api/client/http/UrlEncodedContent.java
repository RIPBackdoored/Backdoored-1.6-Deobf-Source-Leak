package com.google.api.client.http;

import com.google.api.client.util.escape.*;
import java.io.*;
import java.util.*;
import com.google.api.client.util.*;

public class UrlEncodedContent extends AbstractHttpContent
{
    private Object data;
    
    public UrlEncodedContent(final Object data) {
        super(UrlEncodedParser.MEDIA_TYPE);
        this.setData(data);
    }
    
    @Override
    public void writeTo(final OutputStream out) throws IOException {
        final Writer writer = new BufferedWriter(new OutputStreamWriter(out, this.getCharset()));
        boolean first = true;
        for (final Map.Entry<String, Object> nameValueEntry : Data.mapOf(this.data).entrySet()) {
            final Object value = nameValueEntry.getValue();
            if (value != null) {
                final String name = CharEscapers.escapeUri(nameValueEntry.getKey());
                final Class<?> valueClass = value.getClass();
                if (value instanceof Iterable || valueClass.isArray()) {
                    for (final Object repeatedValue : Types.iterableOf(value)) {
                        first = appendParam(first, writer, name, repeatedValue);
                    }
                }
                else {
                    first = appendParam(first, writer, name, value);
                }
            }
        }
        writer.flush();
    }
    
    @Override
    public UrlEncodedContent setMediaType(final HttpMediaType mediaType) {
        super.setMediaType(mediaType);
        return this;
    }
    
    public final Object getData() {
        return this.data;
    }
    
    public UrlEncodedContent setData(final Object data) {
        this.data = Preconditions.checkNotNull(data);
        return this;
    }
    
    public static UrlEncodedContent getContent(final HttpRequest request) {
        final HttpContent content = request.getContent();
        if (content != null) {
            return (UrlEncodedContent)content;
        }
        final UrlEncodedContent result = new UrlEncodedContent(new HashMap());
        request.setContent(result);
        return result;
    }
    
    private static boolean appendParam(boolean first, final Writer writer, final String name, final Object value) throws IOException {
        if (value == null || Data.isNull(value)) {
            return first;
        }
        if (first) {
            first = false;
        }
        else {
            writer.write("&");
        }
        writer.write(name);
        final String stringValue = CharEscapers.escapeUri((value instanceof Enum) ? FieldInfo.of((Enum<?>)value).getName() : value.toString());
        if (stringValue.length() != 0) {
            writer.write("=");
            writer.write(stringValue);
        }
        return first;
    }
    
    @Override
    public /* bridge */ AbstractHttpContent setMediaType(final HttpMediaType mediaType) {
        return this.setMediaType(mediaType);
    }
}
