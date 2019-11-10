package com.google.api.client.http;

import com.google.api.client.util.*;
import java.util.*;
import java.net.*;
import com.google.api.client.util.escape.*;

public class GenericUrl extends GenericData
{
    private static final Escaper URI_FRAGMENT_ESCAPER;
    private String scheme;
    private String host;
    private String userInfo;
    private int port;
    private List<String> pathParts;
    private String fragment;
    
    public GenericUrl() {
        super();
        this.port = -1;
    }
    
    public GenericUrl(final String encodedUrl) {
        this(parseURL(encodedUrl));
    }
    
    public GenericUrl(final URI uri) {
        this(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getRawPath(), uri.getRawFragment(), uri.getRawQuery(), uri.getRawUserInfo());
    }
    
    public GenericUrl(final URL url) {
        this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef(), url.getQuery(), url.getUserInfo());
    }
    
    private GenericUrl(final String scheme, final String host, final int port, final String path, final String fragment, final String query, final String userInfo) {
        super();
        this.port = -1;
        this.scheme = scheme.toLowerCase(Locale.US);
        this.host = host;
        this.port = port;
        this.pathParts = toPathParts(path);
        this.fragment = ((fragment != null) ? CharEscapers.decodeUri(fragment) : null);
        if (query != null) {
            UrlEncodedParser.parse(query, this);
        }
        this.userInfo = ((userInfo != null) ? CharEscapers.decodeUri(userInfo) : null);
    }
    
    @Override
    public int hashCode() {
        return this.build().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof GenericUrl)) {
            return false;
        }
        final GenericUrl other = (GenericUrl)obj;
        return this.build().equals(other.build());
    }
    
    @Override
    public String toString() {
        return this.build();
    }
    
    @Override
    public GenericUrl clone() {
        final GenericUrl result = (GenericUrl)super.clone();
        if (this.pathParts != null) {
            result.pathParts = new ArrayList<String>(this.pathParts);
        }
        return result;
    }
    
    @Override
    public GenericUrl set(final String fieldName, final Object value) {
        return (GenericUrl)super.set(fieldName, value);
    }
    
    public final String getScheme() {
        return this.scheme;
    }
    
    public final void setScheme(final String scheme) {
        this.scheme = Preconditions.checkNotNull(scheme);
    }
    
    public String getHost() {
        return this.host;
    }
    
    public final void setHost(final String host) {
        this.host = Preconditions.checkNotNull(host);
    }
    
    public final String getUserInfo() {
        return this.userInfo;
    }
    
    public final void setUserInfo(final String userInfo) {
        this.userInfo = userInfo;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public final void setPort(final int port) {
        Preconditions.checkArgument(port >= -1, (Object)"expected port >= -1");
        this.port = port;
    }
    
    public List<String> getPathParts() {
        return this.pathParts;
    }
    
    public void setPathParts(final List<String> pathParts) {
        this.pathParts = pathParts;
    }
    
    public String getFragment() {
        return this.fragment;
    }
    
    public final void setFragment(final String fragment) {
        this.fragment = fragment;
    }
    
    public final String build() {
        return this.buildAuthority() + this.buildRelativeUrl();
    }
    
    public final String buildAuthority() {
        final StringBuilder buf = new StringBuilder();
        buf.append(Preconditions.checkNotNull(this.scheme));
        buf.append("://");
        if (this.userInfo != null) {
            buf.append(CharEscapers.escapeUriUserInfo(this.userInfo)).append('@');
        }
        buf.append(Preconditions.checkNotNull(this.host));
        final int port = this.port;
        if (port != -1) {
            buf.append(':').append(port);
        }
        return buf.toString();
    }
    
    public final String buildRelativeUrl() {
        final StringBuilder buf = new StringBuilder();
        if (this.pathParts != null) {
            this.appendRawPathFromParts(buf);
        }
        addQueryParams(this.entrySet(), buf);
        final String fragment = this.fragment;
        if (fragment != null) {
            buf.append('#').append(GenericUrl.URI_FRAGMENT_ESCAPER.escape(fragment));
        }
        return buf.toString();
    }
    
    public final URI toURI() {
        return toURI(this.build());
    }
    
    public final URL toURL() {
        return parseURL(this.build());
    }
    
    public final URL toURL(final String relativeUrl) {
        try {
            final URL url = this.toURL();
            return new URL(url, relativeUrl);
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    public Object getFirst(final String name) {
        final Object value = this.get(name);
        if (value instanceof Collection) {
            final Collection<Object> collectionValue = (Collection<Object>)value;
            final Iterator<Object> iterator = collectionValue.iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }
        return value;
    }
    
    public Collection<Object> getAll(final String name) {
        final Object value = this.get(name);
        if (value == null) {
            return Collections.emptySet();
        }
        if (value instanceof Collection) {
            final Collection<Object> collectionValue = (Collection<Object>)value;
            return Collections.unmodifiableCollection((Collection<?>)collectionValue);
        }
        return Collections.singleton(value);
    }
    
    public String getRawPath() {
        final List<String> pathParts = this.pathParts;
        if (pathParts == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder();
        this.appendRawPathFromParts(buf);
        return buf.toString();
    }
    
    public void setRawPath(final String encodedPath) {
        this.pathParts = toPathParts(encodedPath);
    }
    
    public void appendRawPath(final String encodedPath) {
        if (encodedPath != null && encodedPath.length() != 0) {
            final List<String> appendedPathParts = toPathParts(encodedPath);
            if (this.pathParts == null || this.pathParts.isEmpty()) {
                this.pathParts = appendedPathParts;
            }
            else {
                final int size = this.pathParts.size();
                this.pathParts.set(size - 1, this.pathParts.get(size - 1) + appendedPathParts.get(0));
                this.pathParts.addAll(appendedPathParts.subList(1, appendedPathParts.size()));
            }
        }
    }
    
    public static List<String> toPathParts(final String encodedPath) {
        if (encodedPath == null || encodedPath.length() == 0) {
            return null;
        }
        final List<String> result = new ArrayList<String>();
        int cur = 0;
        boolean notDone = true;
        while (notDone) {
            final int slash = encodedPath.indexOf(47, cur);
            notDone = (slash != -1);
            String sub;
            if (notDone) {
                sub = encodedPath.substring(cur, slash);
            }
            else {
                sub = encodedPath.substring(cur);
            }
            result.add(CharEscapers.decodeUri(sub));
            cur = slash + 1;
        }
        return result;
    }
    
    private void appendRawPathFromParts(final StringBuilder buf) {
        for (int size = this.pathParts.size(), i = 0; i < size; ++i) {
            final String pathPart = this.pathParts.get(i);
            if (i != 0) {
                buf.append('/');
            }
            if (pathPart.length() != 0) {
                buf.append(CharEscapers.escapeUriPath(pathPart));
            }
        }
    }
    
    static void addQueryParams(final Set<Map.Entry<String, Object>> entrySet, final StringBuilder buf) {
        boolean first = true;
        for (final Map.Entry<String, Object> nameValueEntry : entrySet) {
            final Object value = nameValueEntry.getValue();
            if (value != null) {
                final String name = CharEscapers.escapeUriQuery(nameValueEntry.getKey());
                if (value instanceof Collection) {
                    final Collection<?> collectionValue = (Collection<?>)value;
                    for (final Object repeatedValue : collectionValue) {
                        first = appendParam(first, buf, name, repeatedValue);
                    }
                }
                else {
                    first = appendParam(first, buf, name, value);
                }
            }
        }
    }
    
    private static boolean appendParam(boolean first, final StringBuilder buf, final String name, final Object value) {
        if (first) {
            first = false;
            buf.append('?');
        }
        else {
            buf.append('&');
        }
        buf.append(name);
        final String stringValue = CharEscapers.escapeUriQuery(value.toString());
        if (stringValue.length() != 0) {
            buf.append('=').append(stringValue);
        }
        return first;
    }
    
    private static URI toURI(final String encodedUrl) {
        try {
            return new URI(encodedUrl);
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    private static URL parseURL(final String encodedUrl) {
        try {
            return new URL(encodedUrl);
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
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
        URI_FRAGMENT_ESCAPER = new PercentEscaper("=&-_.!~*'()@:$,;/?:", false);
    }
}
