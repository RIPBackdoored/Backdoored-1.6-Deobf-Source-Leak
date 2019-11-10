package com.google.api.client.http;

import com.google.api.client.repackaged.com.google.common.base.*;
import com.google.api.client.util.escape.*;
import java.util.*;
import com.google.api.client.util.*;

public class UriTemplate
{
    static final Map<Character, CompositeOutput> COMPOSITE_PREFIXES;
    private static final String COMPOSITE_NON_EXPLODE_JOINER = ",";
    
    public UriTemplate() {
        super();
    }
    
    static CompositeOutput getCompositeOutput(final String propertyName) {
        final CompositeOutput compositeOutput = UriTemplate.COMPOSITE_PREFIXES.get(propertyName.charAt(0));
        return (compositeOutput == null) ? CompositeOutput.SIMPLE : compositeOutput;
    }
    
    private static Map<String, Object> getMap(final Object obj) {
        final Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (final Map.Entry<String, Object> entry : Data.mapOf(obj).entrySet()) {
            final Object value = entry.getValue();
            if (value != null && !Data.isNull(value)) {
                map.put(entry.getKey(), value);
            }
        }
        return map;
    }
    
    public static String expand(final String baseUrl, final String uriTemplate, final Object parameters, final boolean addUnusedParamsAsQueryParams) {
        String pathUri;
        if (uriTemplate.startsWith("/")) {
            final GenericUrl url = new GenericUrl(baseUrl);
            url.setRawPath(null);
            pathUri = url.build() + uriTemplate;
        }
        else if (uriTemplate.startsWith("http://") || uriTemplate.startsWith("https://")) {
            pathUri = uriTemplate;
        }
        else {
            pathUri = baseUrl + uriTemplate;
        }
        return expand(pathUri, parameters, addUnusedParamsAsQueryParams);
    }
    
    public static String expand(final String pathUri, final Object parameters, final boolean addUnusedParamsAsQueryParams) {
        final Map<String, Object> variableMap = getMap(parameters);
        final StringBuilder pathBuf = new StringBuilder();
        int cur = 0;
        final int length = pathUri.length();
        while (cur < length) {
            final int next = pathUri.indexOf(123, cur);
            if (next == -1) {
                if (cur == 0 && !addUnusedParamsAsQueryParams) {
                    return pathUri;
                }
                pathBuf.append(pathUri.substring(cur));
                break;
            }
            else {
                pathBuf.append(pathUri.substring(cur, next));
                final int close = pathUri.indexOf(125, next + 2);
                cur = close + 1;
                final String templates = pathUri.substring(next + 1, close);
                final CompositeOutput compositeOutput = getCompositeOutput(templates);
                final ListIterator<String> templateIterator = Splitter.on(',').splitToList(templates).listIterator();
                boolean isFirstParameter = true;
                while (templateIterator.hasNext()) {
                    final String template = templateIterator.next();
                    final boolean containsExplodeModifier = template.endsWith("*");
                    final int varNameStartIndex = (templateIterator.nextIndex() == 1) ? compositeOutput.getVarNameStartIndex() : 0;
                    int varNameEndIndex = template.length();
                    if (containsExplodeModifier) {
                        --varNameEndIndex;
                    }
                    final String varName = template.substring(varNameStartIndex, varNameEndIndex);
                    Object value = variableMap.remove(varName);
                    if (value == null) {
                        continue;
                    }
                    if (!isFirstParameter) {
                        pathBuf.append(compositeOutput.getExplodeJoiner());
                    }
                    else {
                        pathBuf.append(compositeOutput.getOutputPrefix());
                        isFirstParameter = false;
                    }
                    if (value instanceof Iterator) {
                        final Iterator<?> iterator = (Iterator<?>)value;
                        value = getListPropertyValue(varName, iterator, containsExplodeModifier, compositeOutput);
                    }
                    else if (value instanceof Iterable || value.getClass().isArray()) {
                        final Iterator<?> iterator = Types.iterableOf(value).iterator();
                        value = getListPropertyValue(varName, iterator, containsExplodeModifier, compositeOutput);
                    }
                    else if (value.getClass().isEnum()) {
                        final String name = FieldInfo.of((Enum<?>)value).getName();
                        if (name != null) {
                            if (compositeOutput.requiresVarAssignment()) {
                                value = String.format("%s=%s", varName, value);
                            }
                            value = CharEscapers.escapeUriPath(value.toString());
                        }
                    }
                    else if (!Data.isValueOfPrimitiveType(value)) {
                        final Map<String, Object> map = getMap(value);
                        value = getMapPropertyValue(varName, map, containsExplodeModifier, compositeOutput);
                    }
                    else {
                        if (compositeOutput.requiresVarAssignment()) {
                            value = String.format("%s=%s", varName, value);
                        }
                        if (compositeOutput.getReservedExpansion()) {
                            value = CharEscapers.escapeUriPathWithoutReserved(value.toString());
                        }
                        else {
                            value = CharEscapers.escapeUriPath(value.toString());
                        }
                    }
                    pathBuf.append(value);
                }
            }
        }
        if (addUnusedParamsAsQueryParams) {
            GenericUrl.addQueryParams(variableMap.entrySet(), pathBuf);
        }
        return pathBuf.toString();
    }
    
    private static String getListPropertyValue(final String varName, final Iterator<?> iterator, final boolean containsExplodeModifier, final CompositeOutput compositeOutput) {
        if (!iterator.hasNext()) {
            return "";
        }
        final StringBuilder retBuf = new StringBuilder();
        String joiner;
        if (containsExplodeModifier) {
            joiner = compositeOutput.getExplodeJoiner();
        }
        else {
            joiner = ",";
            if (compositeOutput.requiresVarAssignment()) {
                retBuf.append(CharEscapers.escapeUriPath(varName));
                retBuf.append("=");
            }
        }
        while (iterator.hasNext()) {
            if (containsExplodeModifier && compositeOutput.requiresVarAssignment()) {
                retBuf.append(CharEscapers.escapeUriPath(varName));
                retBuf.append("=");
            }
            retBuf.append(compositeOutput.getEncodedValue(iterator.next().toString()));
            if (iterator.hasNext()) {
                retBuf.append(joiner);
            }
        }
        return retBuf.toString();
    }
    
    private static String getMapPropertyValue(final String varName, final Map<String, Object> map, final boolean containsExplodeModifier, final CompositeOutput compositeOutput) {
        if (map.isEmpty()) {
            return "";
        }
        final StringBuilder retBuf = new StringBuilder();
        String joiner;
        String mapElementsJoiner;
        if (containsExplodeModifier) {
            joiner = compositeOutput.getExplodeJoiner();
            mapElementsJoiner = "=";
        }
        else {
            joiner = ",";
            mapElementsJoiner = ",";
            if (compositeOutput.requiresVarAssignment()) {
                retBuf.append(CharEscapers.escapeUriPath(varName));
                retBuf.append("=");
            }
        }
        final Iterator<Map.Entry<String, Object>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            final Map.Entry<String, Object> entry = mapIterator.next();
            final String encodedKey = compositeOutput.getEncodedValue(entry.getKey());
            final String encodedValue = compositeOutput.getEncodedValue(entry.getValue().toString());
            retBuf.append(encodedKey);
            retBuf.append(mapElementsJoiner);
            retBuf.append(encodedValue);
            if (mapIterator.hasNext()) {
                retBuf.append(joiner);
            }
        }
        return retBuf.toString();
    }
    
    static {
        COMPOSITE_PREFIXES = new HashMap<Character, CompositeOutput>();
        CompositeOutput.values();
    }
    
    private enum CompositeOutput
    {
        PLUS(Character.valueOf('+'), "", ",", false, true), 
        HASH(Character.valueOf('#'), "#", ",", false, true), 
        DOT(Character.valueOf('.'), ".", ".", false, false), 
        FORWARD_SLASH(Character.valueOf('/'), "/", "/", false, false), 
        SEMI_COLON(Character.valueOf(';'), ";", ";", true, false), 
        QUERY(Character.valueOf('?'), "?", "&", true, false), 
        AMP(Character.valueOf('&'), "&", "&", true, false), 
        SIMPLE((Character)null, "", ",", false, false);
        
        private final Character propertyPrefix;
        private final String outputPrefix;
        private final String explodeJoiner;
        private final boolean requiresVarAssignment;
        private final boolean reservedExpansion;
        private static final /* synthetic */ CompositeOutput[] $VALUES;
        
        public static CompositeOutput[] values() {
            return CompositeOutput.$VALUES.clone();
        }
        
        public static CompositeOutput valueOf(final String name) {
            return Enum.valueOf(CompositeOutput.class, name);
        }
        
        private CompositeOutput(final Character propertyPrefix, final String outputPrefix, final String explodeJoiner, final boolean requiresVarAssignment, final boolean reservedExpansion) {
            this.propertyPrefix = propertyPrefix;
            this.outputPrefix = Preconditions.checkNotNull(outputPrefix);
            this.explodeJoiner = Preconditions.checkNotNull(explodeJoiner);
            this.requiresVarAssignment = requiresVarAssignment;
            this.reservedExpansion = reservedExpansion;
            if (propertyPrefix != null) {
                UriTemplate.COMPOSITE_PREFIXES.put(propertyPrefix, this);
            }
        }
        
        String getOutputPrefix() {
            return this.outputPrefix;
        }
        
        String getExplodeJoiner() {
            return this.explodeJoiner;
        }
        
        boolean requiresVarAssignment() {
            return this.requiresVarAssignment;
        }
        
        int getVarNameStartIndex() {
            return (this.propertyPrefix != null) ? 1 : 0;
        }
        
        String getEncodedValue(final String value) {
            String encodedValue;
            if (this.reservedExpansion) {
                encodedValue = CharEscapers.escapeUriPath(value);
            }
            else {
                encodedValue = CharEscapers.escapeUri(value);
            }
            return encodedValue;
        }
        
        boolean getReservedExpansion() {
            return this.reservedExpansion;
        }
        
        static {
            $VALUES = new CompositeOutput[] { CompositeOutput.PLUS, CompositeOutput.HASH, CompositeOutput.DOT, CompositeOutput.FORWARD_SLASH, CompositeOutput.SEMI_COLON, CompositeOutput.QUERY, CompositeOutput.AMP, CompositeOutput.SIMPLE };
        }
    }
}
