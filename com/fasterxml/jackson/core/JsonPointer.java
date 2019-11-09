package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.*;

public class JsonPointer
{
    public static final char SEPARATOR = '/';
    protected static final JsonPointer EMPTY;
    protected final JsonPointer _nextSegment;
    protected volatile JsonPointer _head;
    protected final String _asString;
    protected final String _matchingPropertyName;
    protected final int _matchingElementIndex;
    
    protected JsonPointer() {
        super();
        this._nextSegment = null;
        this._matchingPropertyName = "";
        this._matchingElementIndex = -1;
        this._asString = "";
    }
    
    protected JsonPointer(final String fullString, final String segment, final JsonPointer next) {
        super();
        this._asString = fullString;
        this._nextSegment = next;
        this._matchingPropertyName = segment;
        this._matchingElementIndex = _parseIndex(segment);
    }
    
    protected JsonPointer(final String fullString, final String segment, final int matchIndex, final JsonPointer next) {
        super();
        this._asString = fullString;
        this._nextSegment = next;
        this._matchingPropertyName = segment;
        this._matchingElementIndex = matchIndex;
    }
    
    public static JsonPointer compile(final String input) throws IllegalArgumentException {
        if (input == null || input.length() == 0) {
            return JsonPointer.EMPTY;
        }
        if (input.charAt(0) != '/') {
            throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + input + "\"");
        }
        return _parseTail(input);
    }
    
    public static JsonPointer valueOf(final String input) {
        return compile(input);
    }
    
    public static JsonPointer forPath(JsonStreamContext context, final boolean includeRoot) {
        if (context == null) {
            return JsonPointer.EMPTY;
        }
        if (!context.hasPathSegment() && (!includeRoot || !context.inRoot() || !context.hasCurrentIndex())) {
            context = context.getParent();
        }
        JsonPointer tail = null;
        while (context != null) {
            if (context.inObject()) {
                String seg = context.getCurrentName();
                if (seg == null) {
                    seg = "";
                }
                tail = new JsonPointer(_fullPath(tail, seg), seg, tail);
            }
            else if (context.inArray() || includeRoot) {
                final int ix = context.getCurrentIndex();
                final String ixStr = String.valueOf(ix);
                tail = new JsonPointer(_fullPath(tail, ixStr), ixStr, ix, tail);
            }
            context = context.getParent();
        }
        if (tail == null) {
            return JsonPointer.EMPTY;
        }
        return tail;
    }
    
    private static String _fullPath(final JsonPointer tail, final String segment) {
        if (tail == null) {
            final StringBuilder sb = new StringBuilder(segment.length() + 1);
            sb.append('/');
            _appendEscaped(sb, segment);
            return sb.toString();
        }
        final String tailDesc = tail._asString;
        final StringBuilder sb2 = new StringBuilder(segment.length() + 1 + tailDesc.length());
        sb2.append('/');
        _appendEscaped(sb2, segment);
        sb2.append(tailDesc);
        return sb2.toString();
    }
    
    private static void _appendEscaped(final StringBuilder sb, final String segment) {
        for (int i = 0, end = segment.length(); i < end; ++i) {
            final char c = segment.charAt(i);
            if (c == '/') {
                sb.append("~1");
            }
            else if (c == '~') {
                sb.append("~0");
            }
            else {
                sb.append(c);
            }
        }
    }
    
    public boolean matches() {
        return this._nextSegment == null;
    }
    
    public String getMatchingProperty() {
        return this._matchingPropertyName;
    }
    
    public int getMatchingIndex() {
        return this._matchingElementIndex;
    }
    
    public boolean mayMatchProperty() {
        return this._matchingPropertyName != null;
    }
    
    public boolean mayMatchElement() {
        return this._matchingElementIndex >= 0;
    }
    
    public JsonPointer last() {
        JsonPointer current = this;
        if (current == JsonPointer.EMPTY) {
            return null;
        }
        JsonPointer next;
        while ((next = current._nextSegment) != JsonPointer.EMPTY) {
            current = next;
        }
        return current;
    }
    
    public JsonPointer append(final JsonPointer tail) {
        if (this == JsonPointer.EMPTY) {
            return tail;
        }
        if (tail == JsonPointer.EMPTY) {
            return this;
        }
        String currentJsonPointer = this._asString;
        if (currentJsonPointer.endsWith("/")) {
            currentJsonPointer = currentJsonPointer.substring(0, currentJsonPointer.length() - 1);
        }
        return compile(currentJsonPointer + tail._asString);
    }
    
    public boolean matchesProperty(final String name) {
        return this._nextSegment != null && this._matchingPropertyName.equals(name);
    }
    
    public JsonPointer matchProperty(final String name) {
        if (this._nextSegment != null && this._matchingPropertyName.equals(name)) {
            return this._nextSegment;
        }
        return null;
    }
    
    public boolean matchesElement(final int index) {
        return index == this._matchingElementIndex && index >= 0;
    }
    
    public JsonPointer matchElement(final int index) {
        if (index != this._matchingElementIndex || index < 0) {
            return null;
        }
        return this._nextSegment;
    }
    
    public JsonPointer tail() {
        return this._nextSegment;
    }
    
    public JsonPointer head() {
        JsonPointer h = this._head;
        if (h == null) {
            if (this != JsonPointer.EMPTY) {
                h = this._constructHead();
            }
            this._head = h;
        }
        return h;
    }
    
    @Override
    public String toString() {
        return this._asString;
    }
    
    @Override
    public int hashCode() {
        return this._asString.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof JsonPointer && this._asString.equals(((JsonPointer)o)._asString));
    }
    
    private static final int _parseIndex(final String str) {
        final int len = str.length();
        if (len == 0 || len > 10) {
            return -1;
        }
        char c = str.charAt(0);
        if (c <= '0') {
            return (len == 1 && c == '0') ? 0 : -1;
        }
        if (c > '9') {
            return -1;
        }
        for (int i = 1; i < len; ++i) {
            c = str.charAt(i);
            if (c > '9' || c < '0') {
                return -1;
            }
        }
        if (len == 10) {
            final long l = NumberInput.parseLong(str);
            if (l > 2147483647L) {
                return -1;
            }
        }
        return NumberInput.parseInt(str);
    }
    
    protected static JsonPointer _parseTail(final String input) {
        final int end = input.length();
        int i = 1;
        while (i < end) {
            final char c = input.charAt(i);
            if (c == '/') {
                return new JsonPointer(input, input.substring(1, i), _parseTail(input.substring(i)));
            }
            ++i;
            if (c == '~' && i < end) {
                return _parseQuotedTail(input, i);
            }
        }
        return new JsonPointer(input, input.substring(1), JsonPointer.EMPTY);
    }
    
    protected static JsonPointer _parseQuotedTail(final String input, int i) {
        final int end = input.length();
        final StringBuilder sb = new StringBuilder(Math.max(16, end));
        if (i > 2) {
            sb.append(input, 1, i - 1);
        }
        _appendEscape(sb, input.charAt(i++));
        while (i < end) {
            final char c = input.charAt(i);
            if (c == '/') {
                return new JsonPointer(input, sb.toString(), _parseTail(input.substring(i)));
            }
            ++i;
            if (c == '~' && i < end) {
                _appendEscape(sb, input.charAt(i++));
            }
            else {
                sb.append(c);
            }
        }
        return new JsonPointer(input, sb.toString(), JsonPointer.EMPTY);
    }
    
    protected JsonPointer _constructHead() {
        final JsonPointer last = this.last();
        if (last == this) {
            return JsonPointer.EMPTY;
        }
        final int suffixLength = last._asString.length();
        final JsonPointer next = this._nextSegment;
        return new JsonPointer(this._asString.substring(0, this._asString.length() - suffixLength), this._matchingPropertyName, this._matchingElementIndex, next._constructHead(suffixLength, last));
    }
    
    protected JsonPointer _constructHead(final int suffixLength, final JsonPointer last) {
        if (this == last) {
            return JsonPointer.EMPTY;
        }
        final JsonPointer next = this._nextSegment;
        final String str = this._asString;
        return new JsonPointer(str.substring(0, str.length() - suffixLength), this._matchingPropertyName, this._matchingElementIndex, next._constructHead(suffixLength, last));
    }
    
    private static void _appendEscape(final StringBuilder sb, char c) {
        if (c == '0') {
            c = '~';
        }
        else if (c == '1') {
            c = '/';
        }
        else {
            sb.append('~');
        }
        sb.append(c);
    }
    
    static {
        EMPTY = new JsonPointer();
    }
}
