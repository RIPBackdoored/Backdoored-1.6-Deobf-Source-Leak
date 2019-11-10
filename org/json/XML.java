package org.json;

import java.util.*;
import java.io.*;

public class XML
{
    public static final Character AMP;
    public static final Character APOS;
    public static final Character BANG;
    public static final Character EQ;
    public static final Character GT;
    public static final Character LT;
    public static final Character QUEST;
    public static final Character QUOT;
    public static final Character SLASH;
    public static final String NULL_ATTR = "xsi:nil";
    
    public XML() {
        super();
    }
    
    private static Iterable<Integer> codePointIterator(final String string) {
        return new Iterable<Integer>() {
            final /* synthetic */ String val$string;
            
            XML$1() {
                super();
            }
            
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    private int nextIndex = 0;
                    private int length = string.length();
                    final /* synthetic */ XML$1 this$0;
                    
                    XML$1$1() {
                        this.this$0 = this$0;
                        super();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.nextIndex < this.length;
                    }
                    
                    @Override
                    public Integer next() {
                        final int result = string.codePointAt(this.nextIndex);
                        this.nextIndex += Character.charCount(result);
                        return result;
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                    @Override
                    public /* bridge */ Object next() {
                        return this.next();
                    }
                };
            }
        };
    }
    
    public static String escape(final String string) {
        final StringBuilder sb = new StringBuilder(string.length());
        for (final int cp : codePointIterator(string)) {
            switch (cp) {
                case 38: {
                    sb.append("&amp;");
                    continue;
                }
                case 60: {
                    sb.append("&lt;");
                    continue;
                }
                case 62: {
                    sb.append("&gt;");
                    continue;
                }
                case 34: {
                    sb.append("&quot;");
                    continue;
                }
                case 39: {
                    sb.append("&apos;");
                    continue;
                }
                default: {
                    if (mustEscape(cp)) {
                        sb.append("&#x");
                        sb.append(Integer.toHexString(cp));
                        sb.append(';');
                        continue;
                    }
                    sb.appendCodePoint(cp);
                    continue;
                }
            }
        }
        return sb.toString();
    }
    
    private static boolean mustEscape(final int cp) {
        return (Character.isISOControl(cp) && cp != 9 && cp != 10 && cp != 13) || ((cp < 32 || cp > 55295) && (cp < 57344 || cp > 65533) && (cp < 65536 || cp > 1114111));
    }
    
    public static String unescape(final String string) {
        final StringBuilder sb = new StringBuilder(string.length());
        for (int i = 0, length = string.length(); i < length; ++i) {
            final char c = string.charAt(i);
            if (c == '&') {
                final int semic = string.indexOf(59, i);
                if (semic > i) {
                    final String entity = string.substring(i + 1, semic);
                    sb.append(XMLTokener.unescapeEntity(entity));
                    i += entity.length() + 1;
                }
                else {
                    sb.append(c);
                }
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public static void noSpace(final String string) throws JSONException {
        final int length = string.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < length; ++i) {
            if (Character.isWhitespace(string.charAt(i))) {
                throw new JSONException("'" + string + "' contains a space character.");
            }
        }
    }
    
    private static boolean parse(final XMLTokener x, final JSONObject context, final String name, final XMLParserConfiguration config) throws JSONException {
        JSONObject jsonobject = null;
        Object token = x.nextToken();
        if (token == XML.BANG) {
            final char c = x.next();
            if (c == '-') {
                if (x.next() == '-') {
                    x.skipPast("-->");
                    return false;
                }
                x.back();
            }
            else if (c == '[') {
                token = x.nextToken();
                if ("CDATA".equals(token) && x.next() == '[') {
                    final String string = x.nextCDATA();
                    if (string.length() > 0) {
                        context.accumulate(config.cDataTagName, string);
                    }
                    return false;
                }
                throw x.syntaxError("Expected 'CDATA['");
            }
            int i = 1;
            do {
                token = x.nextMeta();
                if (token == null) {
                    throw x.syntaxError("Missing '>' after '<!'.");
                }
                if (token == XML.LT) {
                    ++i;
                }
                else {
                    if (token != XML.GT) {
                        continue;
                    }
                    --i;
                }
            } while (i > 0);
            return false;
        }
        if (token == XML.QUEST) {
            x.skipPast("?>");
            return false;
        }
        if (token == XML.SLASH) {
            token = x.nextToken();
            if (name == null) {
                throw x.syntaxError("Mismatched close tag " + token);
            }
            if (!token.equals(name)) {
                throw x.syntaxError("Mismatched " + name + " and " + token);
            }
            if (x.nextToken() != XML.GT) {
                throw x.syntaxError("Misshaped close tag");
            }
            return true;
        }
        else {
            if (token instanceof Character) {
                throw x.syntaxError("Misshaped tag");
            }
            final String tagName = (String)token;
            token = null;
            jsonobject = new JSONObject();
            boolean nilAttributeFound = false;
            while (true) {
                if (token == null) {
                    token = x.nextToken();
                }
                if (token instanceof String) {
                    final String string = (String)token;
                    token = x.nextToken();
                    if (token == XML.EQ) {
                        token = x.nextToken();
                        if (!(token instanceof String)) {
                            throw x.syntaxError("Missing value");
                        }
                        if (config.convertNilAttributeToNull && "xsi:nil".equals(string) && Boolean.parseBoolean((String)token)) {
                            nilAttributeFound = true;
                        }
                        else if (!nilAttributeFound) {
                            jsonobject.accumulate(string, config.keepStrings ? ((String)token) : stringToValue((String)token));
                        }
                        token = null;
                    }
                    else {
                        jsonobject.accumulate(string, "");
                    }
                }
                else if (token == XML.SLASH) {
                    if (x.nextToken() != XML.GT) {
                        throw x.syntaxError("Misshaped tag");
                    }
                    if (nilAttributeFound) {
                        context.accumulate(tagName, JSONObject.NULL);
                    }
                    else if (jsonobject.length() > 0) {
                        context.accumulate(tagName, jsonobject);
                    }
                    else {
                        context.accumulate(tagName, "");
                    }
                    return false;
                }
                else {
                    if (token != XML.GT) {
                        throw x.syntaxError("Misshaped tag");
                    }
                    while (true) {
                        token = x.nextContent();
                        if (token == null) {
                            if (tagName != null) {
                                throw x.syntaxError("Unclosed tag " + tagName);
                            }
                            return false;
                        }
                        else if (token instanceof String) {
                            final String string = (String)token;
                            if (string.length() <= 0) {
                                continue;
                            }
                            jsonobject.accumulate(config.cDataTagName, config.keepStrings ? string : stringToValue(string));
                        }
                        else {
                            if (token == XML.LT && parse(x, jsonobject, tagName, config)) {
                                if (jsonobject.length() == 0) {
                                    context.accumulate(tagName, "");
                                }
                                else if (jsonobject.length() == 1 && jsonobject.opt(config.cDataTagName) != null) {
                                    context.accumulate(tagName, jsonobject.opt(config.cDataTagName));
                                }
                                else {
                                    context.accumulate(tagName, jsonobject);
                                }
                                return false;
                            }
                            continue;
                        }
                    }
                }
            }
        }
    }
    
    public static Object stringToValue(final String string) {
        if (string.equals("")) {
            return string;
        }
        if (string.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase("null")) {
            return JSONObject.NULL;
        }
        final char initial = string.charAt(0);
        if (initial < '0' || initial > '9') {
            if (initial != '-') {
                return string;
            }
        }
        try {
            if (string.indexOf(46) > -1 || string.indexOf(101) > -1 || string.indexOf(69) > -1 || "-0".equals(string)) {
                final Double d = Double.valueOf(string);
                if (!d.isInfinite() && !d.isNaN()) {
                    return d;
                }
            }
            else {
                final Long myLong = Long.valueOf(string);
                if (string.equals(myLong.toString())) {
                    if (myLong == myLong.intValue()) {
                        return myLong.intValue();
                    }
                    return myLong;
                }
            }
        }
        catch (Exception ex) {}
        return string;
    }
    
    public static JSONObject toJSONObject(final String string) throws JSONException {
        return toJSONObject(string, XMLParserConfiguration.ORIGINAL);
    }
    
    public static JSONObject toJSONObject(final Reader reader) throws JSONException {
        return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }
    
    public static JSONObject toJSONObject(final Reader reader, final boolean keepStrings) throws JSONException {
        if (keepStrings) {
            return toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
        }
        return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
    }
    
    public static JSONObject toJSONObject(final Reader reader, final XMLParserConfiguration config) throws JSONException {
        final JSONObject jo = new JSONObject();
        final XMLTokener x = new XMLTokener(reader);
        while (x.more()) {
            x.skipPast("<");
            if (x.more()) {
                parse(x, jo, null, config);
            }
        }
        return jo;
    }
    
    public static JSONObject toJSONObject(final String string, final boolean keepStrings) throws JSONException {
        return toJSONObject(new StringReader(string), keepStrings);
    }
    
    public static JSONObject toJSONObject(final String string, final XMLParserConfiguration config) throws JSONException {
        return toJSONObject(new StringReader(string), config);
    }
    
    public static String toString(final Object object) throws JSONException {
        return toString(object, null, XMLParserConfiguration.ORIGINAL);
    }
    
    public static String toString(final Object object, final String tagName) {
        return toString(object, tagName, XMLParserConfiguration.ORIGINAL);
    }
    
    public static String toString(final Object object, final String tagName, final XMLParserConfiguration config) throws JSONException {
        final StringBuilder sb = new StringBuilder();
        if (object instanceof JSONObject) {
            if (tagName != null) {
                sb.append('<');
                sb.append(tagName);
                sb.append('>');
            }
            final JSONObject jo = (JSONObject)object;
            for (final String key : jo.keySet()) {
                Object value = jo.opt(key);
                if (value == null) {
                    value = "";
                }
                else if (value.getClass().isArray()) {
                    value = new JSONArray(value);
                }
                if (key.equals(config.cDataTagName)) {
                    if (value instanceof JSONArray) {
                        final JSONArray ja = (JSONArray)value;
                        for (int jaLength = ja.length(), i = 0; i < jaLength; ++i) {
                            if (i > 0) {
                                sb.append('\n');
                            }
                            final Object val = ja.opt(i);
                            sb.append(escape(val.toString()));
                        }
                    }
                    else {
                        sb.append(escape(value.toString()));
                    }
                }
                else if (value instanceof JSONArray) {
                    final JSONArray ja = (JSONArray)value;
                    for (int jaLength = ja.length(), i = 0; i < jaLength; ++i) {
                        final Object val = ja.opt(i);
                        if (val instanceof JSONArray) {
                            sb.append('<');
                            sb.append(key);
                            sb.append('>');
                            sb.append(toString(val, null, config));
                            sb.append("</");
                            sb.append(key);
                            sb.append('>');
                        }
                        else {
                            sb.append(toString(val, key, config));
                        }
                    }
                }
                else if ("".equals(value)) {
                    sb.append('<');
                    sb.append(key);
                    sb.append("/>");
                }
                else {
                    sb.append(toString(value, key, config));
                }
            }
            if (tagName != null) {
                sb.append("</");
                sb.append(tagName);
                sb.append('>');
            }
            return sb.toString();
        }
        if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
            JSONArray ja;
            if (object.getClass().isArray()) {
                ja = new JSONArray(object);
            }
            else {
                ja = (JSONArray)object;
            }
            for (int jaLength2 = ja.length(), j = 0; j < jaLength2; ++j) {
                final Object val2 = ja.opt(j);
                sb.append(toString(val2, (tagName == null) ? "array" : tagName, config));
            }
            return sb.toString();
        }
        final String string = (object == null) ? "null" : escape(object.toString());
        return (tagName == null) ? ("\"" + string + "\"") : ((string.length() == 0) ? ("<" + tagName + "/>") : ("<" + tagName + ">" + string + "</" + tagName + ">"));
    }
    
    static {
        AMP = '&';
        APOS = '\'';
        BANG = '!';
        EQ = '=';
        GT = '>';
        LT = '<';
        QUEST = '?';
        QUOT = '\"';
        SLASH = '/';
    }
}
