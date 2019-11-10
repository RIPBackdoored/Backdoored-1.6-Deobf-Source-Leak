package com.google.api.client.http;

import java.util.logging.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.api.client.util.*;
import java.util.*;

public class HttpHeaders extends GenericData
{
    @Key("Accept")
    private List<String> accept;
    @Key("Accept-Encoding")
    private List<String> acceptEncoding;
    @Key("Authorization")
    private List<String> authorization;
    @Key("Cache-Control")
    private List<String> cacheControl;
    @Key("Content-Encoding")
    private List<String> contentEncoding;
    @Key("Content-Length")
    private List<Long> contentLength;
    @Key("Content-MD5")
    private List<String> contentMD5;
    @Key("Content-Range")
    private List<String> contentRange;
    @Key("Content-Type")
    private List<String> contentType;
    @Key("Cookie")
    private List<String> cookie;
    @Key("Date")
    private List<String> date;
    @Key("ETag")
    private List<String> etag;
    @Key("Expires")
    private List<String> expires;
    @Key("If-Modified-Since")
    private List<String> ifModifiedSince;
    @Key("If-Match")
    private List<String> ifMatch;
    @Key("If-None-Match")
    private List<String> ifNoneMatch;
    @Key("If-Unmodified-Since")
    private List<String> ifUnmodifiedSince;
    @Key("If-Range")
    private List<String> ifRange;
    @Key("Last-Modified")
    private List<String> lastModified;
    @Key("Location")
    private List<String> location;
    @Key("MIME-Version")
    private List<String> mimeVersion;
    @Key("Range")
    private List<String> range;
    @Key("Retry-After")
    private List<String> retryAfter;
    @Key("User-Agent")
    private List<String> userAgent;
    @Key("WWW-Authenticate")
    private List<String> authenticate;
    @Key("Age")
    private List<Long> age;
    
    public HttpHeaders() {
        super(EnumSet.of(Flags.IGNORE_CASE));
        this.acceptEncoding = new ArrayList<String>(Collections.singleton("gzip"));
    }
    
    @Override
    public HttpHeaders clone() {
        return (HttpHeaders)super.clone();
    }
    
    @Override
    public HttpHeaders set(final String fieldName, final Object value) {
        return (HttpHeaders)super.set(fieldName, value);
    }
    
    public final String getAccept() {
        return this.getFirstHeaderValue(this.accept);
    }
    
    public HttpHeaders setAccept(final String accept) {
        this.accept = this.getAsList(accept);
        return this;
    }
    
    public final String getAcceptEncoding() {
        return this.getFirstHeaderValue(this.acceptEncoding);
    }
    
    public HttpHeaders setAcceptEncoding(final String acceptEncoding) {
        this.acceptEncoding = this.getAsList(acceptEncoding);
        return this;
    }
    
    public final String getAuthorization() {
        return this.getFirstHeaderValue(this.authorization);
    }
    
    public final List<String> getAuthorizationAsList() {
        return this.authorization;
    }
    
    public HttpHeaders setAuthorization(final String authorization) {
        return this.setAuthorization(this.getAsList(authorization));
    }
    
    public HttpHeaders setAuthorization(final List<String> authorization) {
        this.authorization = authorization;
        return this;
    }
    
    public final String getCacheControl() {
        return this.getFirstHeaderValue(this.cacheControl);
    }
    
    public HttpHeaders setCacheControl(final String cacheControl) {
        this.cacheControl = this.getAsList(cacheControl);
        return this;
    }
    
    public final String getContentEncoding() {
        return this.getFirstHeaderValue(this.contentEncoding);
    }
    
    public HttpHeaders setContentEncoding(final String contentEncoding) {
        this.contentEncoding = this.getAsList(contentEncoding);
        return this;
    }
    
    public final Long getContentLength() {
        return this.getFirstHeaderValue(this.contentLength);
    }
    
    public HttpHeaders setContentLength(final Long contentLength) {
        this.contentLength = this.getAsList(contentLength);
        return this;
    }
    
    public final String getContentMD5() {
        return this.getFirstHeaderValue(this.contentMD5);
    }
    
    public HttpHeaders setContentMD5(final String contentMD5) {
        this.contentMD5 = this.getAsList(contentMD5);
        return this;
    }
    
    public final String getContentRange() {
        return this.getFirstHeaderValue(this.contentRange);
    }
    
    public HttpHeaders setContentRange(final String contentRange) {
        this.contentRange = this.getAsList(contentRange);
        return this;
    }
    
    public final String getContentType() {
        return this.getFirstHeaderValue(this.contentType);
    }
    
    public HttpHeaders setContentType(final String contentType) {
        this.contentType = this.getAsList(contentType);
        return this;
    }
    
    public final String getCookie() {
        return this.getFirstHeaderValue(this.cookie);
    }
    
    public HttpHeaders setCookie(final String cookie) {
        this.cookie = this.getAsList(cookie);
        return this;
    }
    
    public final String getDate() {
        return this.getFirstHeaderValue(this.date);
    }
    
    public HttpHeaders setDate(final String date) {
        this.date = this.getAsList(date);
        return this;
    }
    
    public final String getETag() {
        return this.getFirstHeaderValue(this.etag);
    }
    
    public HttpHeaders setETag(final String etag) {
        this.etag = this.getAsList(etag);
        return this;
    }
    
    public final String getExpires() {
        return this.getFirstHeaderValue(this.expires);
    }
    
    public HttpHeaders setExpires(final String expires) {
        this.expires = this.getAsList(expires);
        return this;
    }
    
    public final String getIfModifiedSince() {
        return this.getFirstHeaderValue(this.ifModifiedSince);
    }
    
    public HttpHeaders setIfModifiedSince(final String ifModifiedSince) {
        this.ifModifiedSince = this.getAsList(ifModifiedSince);
        return this;
    }
    
    public final String getIfMatch() {
        return this.getFirstHeaderValue(this.ifMatch);
    }
    
    public HttpHeaders setIfMatch(final String ifMatch) {
        this.ifMatch = this.getAsList(ifMatch);
        return this;
    }
    
    public final String getIfNoneMatch() {
        return this.getFirstHeaderValue(this.ifNoneMatch);
    }
    
    public HttpHeaders setIfNoneMatch(final String ifNoneMatch) {
        this.ifNoneMatch = this.getAsList(ifNoneMatch);
        return this;
    }
    
    public final String getIfUnmodifiedSince() {
        return this.getFirstHeaderValue(this.ifUnmodifiedSince);
    }
    
    public HttpHeaders setIfUnmodifiedSince(final String ifUnmodifiedSince) {
        this.ifUnmodifiedSince = this.getAsList(ifUnmodifiedSince);
        return this;
    }
    
    public final String getIfRange() {
        return this.getFirstHeaderValue(this.ifRange);
    }
    
    public HttpHeaders setIfRange(final String ifRange) {
        this.ifRange = this.getAsList(ifRange);
        return this;
    }
    
    public final String getLastModified() {
        return this.getFirstHeaderValue(this.lastModified);
    }
    
    public HttpHeaders setLastModified(final String lastModified) {
        this.lastModified = this.getAsList(lastModified);
        return this;
    }
    
    public final String getLocation() {
        return this.getFirstHeaderValue(this.location);
    }
    
    public HttpHeaders setLocation(final String location) {
        this.location = this.getAsList(location);
        return this;
    }
    
    public final String getMimeVersion() {
        return this.getFirstHeaderValue(this.mimeVersion);
    }
    
    public HttpHeaders setMimeVersion(final String mimeVersion) {
        this.mimeVersion = this.getAsList(mimeVersion);
        return this;
    }
    
    public final String getRange() {
        return this.getFirstHeaderValue(this.range);
    }
    
    public HttpHeaders setRange(final String range) {
        this.range = this.getAsList(range);
        return this;
    }
    
    public final String getRetryAfter() {
        return this.getFirstHeaderValue(this.retryAfter);
    }
    
    public HttpHeaders setRetryAfter(final String retryAfter) {
        this.retryAfter = this.getAsList(retryAfter);
        return this;
    }
    
    public final String getUserAgent() {
        return this.getFirstHeaderValue(this.userAgent);
    }
    
    public HttpHeaders setUserAgent(final String userAgent) {
        this.userAgent = this.getAsList(userAgent);
        return this;
    }
    
    public final String getAuthenticate() {
        return this.getFirstHeaderValue(this.authenticate);
    }
    
    public final List<String> getAuthenticateAsList() {
        return this.authenticate;
    }
    
    public HttpHeaders setAuthenticate(final String authenticate) {
        this.authenticate = this.getAsList(authenticate);
        return this;
    }
    
    public final Long getAge() {
        return this.getFirstHeaderValue(this.age);
    }
    
    public HttpHeaders setAge(final Long age) {
        this.age = this.getAsList(age);
        return this;
    }
    
    public HttpHeaders setBasicAuthentication(final String username, final String password) {
        final String userPass = Preconditions.checkNotNull(username) + ":" + Preconditions.checkNotNull(password);
        final String encoded = Base64.encodeBase64String(StringUtils.getBytesUtf8(userPass));
        return this.setAuthorization("Basic " + encoded);
    }
    
    private static void addHeader(final Logger logger, final StringBuilder logbuf, final StringBuilder curlbuf, final LowLevelHttpRequest lowLevelHttpRequest, final String name, final Object value, final Writer writer) throws IOException {
        if (value == null || Data.isNull(value)) {
            return;
        }
        String loggedStringValue;
        final String stringValue = loggedStringValue = toStringValue(value);
        if (("Authorization".equalsIgnoreCase(name) || "Cookie".equalsIgnoreCase(name)) && (logger == null || !logger.isLoggable(Level.ALL))) {
            loggedStringValue = "<Not Logged>";
        }
        if (logbuf != null) {
            logbuf.append(name).append(": ");
            logbuf.append(loggedStringValue);
            logbuf.append(StringUtils.LINE_SEPARATOR);
        }
        if (curlbuf != null) {
            curlbuf.append(" -H '").append(name).append(": ").append(loggedStringValue).append("'");
        }
        if (lowLevelHttpRequest != null) {
            lowLevelHttpRequest.addHeader(name, stringValue);
        }
        if (writer != null) {
            writer.write(name);
            writer.write(": ");
            writer.write(stringValue);
            writer.write("\r\n");
        }
    }
    
    private static String toStringValue(final Object headerValue) {
        return (headerValue instanceof Enum) ? FieldInfo.of((Enum<?>)headerValue).getName() : headerValue.toString();
    }
    
    static void serializeHeaders(final HttpHeaders headers, final StringBuilder logbuf, final StringBuilder curlbuf, final Logger logger, final LowLevelHttpRequest lowLevelHttpRequest) throws IOException {
        serializeHeaders(headers, logbuf, curlbuf, logger, lowLevelHttpRequest, null);
    }
    
    public static void serializeHeadersForMultipartRequests(final HttpHeaders headers, final StringBuilder logbuf, final Logger logger, final Writer writer) throws IOException {
        serializeHeaders(headers, logbuf, null, logger, null, writer);
    }
    
    static void serializeHeaders(final HttpHeaders headers, final StringBuilder logbuf, final StringBuilder curlbuf, final Logger logger, final LowLevelHttpRequest lowLevelHttpRequest, final Writer writer) throws IOException {
        final HashSet<String> headerNames = new HashSet<String>();
        for (final Map.Entry<String, Object> headerEntry : headers.entrySet()) {
            final String name = headerEntry.getKey();
            Preconditions.checkArgument(headerNames.add(name), "multiple headers of the same name (headers are case insensitive): %s", name);
            final Object value = headerEntry.getValue();
            if (value != null) {
                String displayName = name;
                final FieldInfo fieldInfo = headers.getClassInfo().getFieldInfo(name);
                if (fieldInfo != null) {
                    displayName = fieldInfo.getName();
                }
                final Class<?> valueClass = value.getClass();
                if (value instanceof Iterable || valueClass.isArray()) {
                    for (final Object repeatedValue : Types.iterableOf(value)) {
                        addHeader(logger, logbuf, curlbuf, lowLevelHttpRequest, displayName, repeatedValue, writer);
                    }
                }
                else {
                    addHeader(logger, logbuf, curlbuf, lowLevelHttpRequest, displayName, value, writer);
                }
            }
        }
        if (writer != null) {
            writer.flush();
        }
    }
    
    public final void fromHttpResponse(final LowLevelHttpResponse response, final StringBuilder logger) throws IOException {
        this.clear();
        final ParseHeaderState state = new ParseHeaderState(this, logger);
        for (int headerCount = response.getHeaderCount(), i = 0; i < headerCount; ++i) {
            this.parseHeader(response.getHeaderName(i), response.getHeaderValue(i), state);
        }
        state.finish();
    }
    
    private <T> T getFirstHeaderValue(final List<T> internalValue) {
        return (internalValue == null) ? null : internalValue.get(0);
    }
    
    private <T> List<T> getAsList(final T passedValue) {
        if (passedValue == null) {
            return null;
        }
        final List<T> result = new ArrayList<T>();
        result.add(passedValue);
        return result;
    }
    
    public String getFirstHeaderStringValue(final String name) {
        final Object value = this.get(name.toLowerCase(Locale.US));
        if (value == null) {
            return null;
        }
        final Class<?> valueClass = value.getClass();
        if (value instanceof Iterable || valueClass.isArray()) {
            final Iterator<Object> iterator = Types.iterableOf(value).iterator();
            if (iterator.hasNext()) {
                final Object repeatedValue = iterator.next();
                return toStringValue(repeatedValue);
            }
        }
        return toStringValue(value);
    }
    
    public List<String> getHeaderStringValues(final String name) {
        final Object value = this.get(name.toLowerCase(Locale.US));
        if (value == null) {
            return Collections.emptyList();
        }
        final Class<?> valueClass = value.getClass();
        if (value instanceof Iterable || valueClass.isArray()) {
            final List<String> values = new ArrayList<String>();
            for (final Object repeatedValue : Types.iterableOf(value)) {
                values.add(toStringValue(repeatedValue));
            }
            return Collections.unmodifiableList((List<? extends String>)values);
        }
        return Collections.singletonList(toStringValue(value));
    }
    
    public final void fromHttpHeaders(final HttpHeaders headers) {
        try {
            final ParseHeaderState state = new ParseHeaderState(this, null);
            serializeHeaders(headers, null, null, null, new HeaderParsingFakeLevelHttpRequest(this, state));
            state.finish();
        }
        catch (IOException ex) {
            throw Throwables.propagate(ex);
        }
    }
    
    void parseHeader(final String headerName, final String headerValue, final ParseHeaderState state) {
        final List<Type> context = state.context;
        final ClassInfo classInfo = state.classInfo;
        final ArrayValueMap arrayValueMap = state.arrayValueMap;
        final StringBuilder logger = state.logger;
        if (logger != null) {
            logger.append(headerName + ": " + headerValue).append(StringUtils.LINE_SEPARATOR);
        }
        final FieldInfo fieldInfo = classInfo.getFieldInfo(headerName);
        if (fieldInfo != null) {
            final Type type = Data.resolveWildcardTypeOrTypeVariable(context, fieldInfo.getGenericType());
            if (Types.isArray(type)) {
                final Class<?> rawArrayComponentType = Types.getRawArrayComponentType(context, Types.getArrayComponentType(type));
                arrayValueMap.put(fieldInfo.getField(), rawArrayComponentType, parseValue(rawArrayComponentType, context, headerValue));
            }
            else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(context, type), Iterable.class)) {
                Collection<Object> collection = (Collection<Object>)fieldInfo.getValue(this);
                if (collection == null) {
                    collection = Data.newCollectionInstance(type);
                    fieldInfo.setValue(this, collection);
                }
                final Type subFieldType = (type == Object.class) ? null : Types.getIterableParameter(type);
                collection.add(parseValue(subFieldType, context, headerValue));
            }
            else {
                fieldInfo.setValue(this, parseValue(type, context, headerValue));
            }
        }
        else {
            ArrayList<String> listValue = (ArrayList<String>)this.get(headerName);
            if (listValue == null) {
                listValue = new ArrayList<String>();
                this.set(headerName, listValue);
            }
            listValue.add(headerValue);
        }
    }
    
    private static Object parseValue(final Type valueType, final List<Type> context, final String value) {
        final Type resolved = Data.resolveWildcardTypeOrTypeVariable(context, valueType);
        return Data.parsePrimitiveValue(resolved, value);
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
    
    private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest
    {
        private final HttpHeaders target;
        private final ParseHeaderState state;
        
        HeaderParsingFakeLevelHttpRequest(final HttpHeaders target, final ParseHeaderState state) {
            super();
            this.target = target;
            this.state = state;
        }
        
        @Override
        public void addHeader(final String name, final String value) {
            this.target.parseHeader(name, value, this.state);
        }
        
        @Override
        public LowLevelHttpResponse execute() throws IOException {
            throw new UnsupportedOperationException();
        }
    }
    
    private static final class ParseHeaderState
    {
        final ArrayValueMap arrayValueMap;
        final StringBuilder logger;
        final ClassInfo classInfo;
        final List<Type> context;
        
        public ParseHeaderState(final HttpHeaders headers, final StringBuilder logger) {
            super();
            final Class<? extends HttpHeaders> clazz = headers.getClass();
            this.context = Arrays.asList(clazz);
            this.classInfo = ClassInfo.of(clazz, true);
            this.logger = logger;
            this.arrayValueMap = new ArrayValueMap(headers);
        }
        
        void finish() {
            this.arrayValueMap.setValues();
        }
    }
}
