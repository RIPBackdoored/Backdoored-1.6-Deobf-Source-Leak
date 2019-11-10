package com.google.api.client.http;

import com.google.api.client.util.*;
import java.util.regex.*;
import java.util.*;
import java.nio.charset.*;

public final class HttpMediaType
{
    private static final Pattern TYPE_REGEX;
    private static final Pattern TOKEN_REGEX;
    private static final Pattern FULL_MEDIA_TYPE_REGEX;
    private static final Pattern PARAMETER_REGEX;
    private String type;
    private String subType;
    private final SortedMap<String, String> parameters;
    private String cachedBuildResult;
    
    public HttpMediaType(final String type, final String subType) {
        super();
        this.type = "application";
        this.subType = "octet-stream";
        this.parameters = new TreeMap<String, String>();
        this.setType(type);
        this.setSubType(subType);
    }
    
    public HttpMediaType(final String mediaType) {
        super();
        this.type = "application";
        this.subType = "octet-stream";
        this.parameters = new TreeMap<String, String>();
        this.fromString(mediaType);
    }
    
    public HttpMediaType setType(final String type) {
        Preconditions.checkArgument(HttpMediaType.TYPE_REGEX.matcher(type).matches(), (Object)"Type contains reserved characters");
        this.type = type;
        this.cachedBuildResult = null;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public HttpMediaType setSubType(final String subType) {
        Preconditions.checkArgument(HttpMediaType.TYPE_REGEX.matcher(subType).matches(), (Object)"Subtype contains reserved characters");
        this.subType = subType;
        this.cachedBuildResult = null;
        return this;
    }
    
    public String getSubType() {
        return this.subType;
    }
    
    private HttpMediaType fromString(final String combinedType) {
        Matcher matcher = HttpMediaType.FULL_MEDIA_TYPE_REGEX.matcher(combinedType);
        Preconditions.checkArgument(matcher.matches(), (Object)"Type must be in the 'maintype/subtype; parameter=value' format");
        this.setType(matcher.group(1));
        this.setSubType(matcher.group(2));
        final String params = matcher.group(3);
        if (params != null) {
            matcher = HttpMediaType.PARAMETER_REGEX.matcher(params);
            while (matcher.find()) {
                final String key = matcher.group(1);
                String value = matcher.group(3);
                if (value == null) {
                    value = matcher.group(2);
                }
                this.setParameter(key, value);
            }
        }
        return this;
    }
    
    public HttpMediaType setParameter(final String name, final String value) {
        if (value == null) {
            this.removeParameter(name);
            return this;
        }
        Preconditions.checkArgument(HttpMediaType.TOKEN_REGEX.matcher(name).matches(), (Object)"Name contains reserved characters");
        this.cachedBuildResult = null;
        this.parameters.put(name.toLowerCase(Locale.US), value);
        return this;
    }
    
    public String getParameter(final String name) {
        return this.parameters.get(name.toLowerCase(Locale.US));
    }
    
    public HttpMediaType removeParameter(final String name) {
        this.cachedBuildResult = null;
        this.parameters.remove(name.toLowerCase(Locale.US));
        return this;
    }
    
    public void clearParameters() {
        this.cachedBuildResult = null;
        this.parameters.clear();
    }
    
    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.parameters);
    }
    
    static boolean matchesToken(final String value) {
        return HttpMediaType.TOKEN_REGEX.matcher(value).matches();
    }
    
    private static String quoteString(final String unquotedString) {
        String escapedString = unquotedString.replace("\\", "\\\\");
        escapedString = escapedString.replace("\"", "\\\"");
        return "\"" + escapedString + "\"";
    }
    
    public String build() {
        if (this.cachedBuildResult != null) {
            return this.cachedBuildResult;
        }
        final StringBuilder str = new StringBuilder();
        str.append(this.type);
        str.append('/');
        str.append(this.subType);
        if (this.parameters != null) {
            for (final Map.Entry<String, String> entry : this.parameters.entrySet()) {
                final String value = entry.getValue();
                str.append("; ");
                str.append(entry.getKey());
                str.append("=");
                str.append(matchesToken(value) ? value : quoteString(value));
            }
        }
        return this.cachedBuildResult = str.toString();
    }
    
    @Override
    public String toString() {
        return this.build();
    }
    
    public boolean equalsIgnoreParameters(final HttpMediaType mediaType) {
        return mediaType != null && this.getType().equalsIgnoreCase(mediaType.getType()) && this.getSubType().equalsIgnoreCase(mediaType.getSubType());
    }
    
    public static boolean equalsIgnoreParameters(final String mediaTypeA, final String mediaTypeB) {
        return (mediaTypeA == null && mediaTypeB == null) || (mediaTypeA != null && mediaTypeB != null && new HttpMediaType(mediaTypeA).equalsIgnoreParameters(new HttpMediaType(mediaTypeB)));
    }
    
    public HttpMediaType setCharsetParameter(final Charset charset) {
        this.setParameter("charset", (charset == null) ? null : charset.name());
        return this;
    }
    
    public Charset getCharsetParameter() {
        final String value = this.getParameter("charset");
        return (value == null) ? null : Charset.forName(value);
    }
    
    @Override
    public int hashCode() {
        return this.build().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof HttpMediaType)) {
            return false;
        }
        final HttpMediaType otherType = (HttpMediaType)obj;
        return this.equalsIgnoreParameters(otherType) && this.parameters.equals(otherType.parameters);
    }
    
    static {
        TYPE_REGEX = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
        TOKEN_REGEX = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
        final String typeOrKey = "[^\\s/=;\"]+";
        final String wholeParameterSection = ";.*";
        FULL_MEDIA_TYPE_REGEX = Pattern.compile("\\s*(" + typeOrKey + ")/(" + typeOrKey + ")\\s*(" + wholeParameterSection + ")?", 32);
        final String quotedParameterValue = "\"([^\"]*)\"";
        final String unquotedParameterValue = "[^\\s;\"]*";
        final String parameterValue = quotedParameterValue + "|" + unquotedParameterValue;
        PARAMETER_REGEX = Pattern.compile("\\s*;\\s*(" + typeOrKey + ")=(" + parameterValue + ")");
    }
}
