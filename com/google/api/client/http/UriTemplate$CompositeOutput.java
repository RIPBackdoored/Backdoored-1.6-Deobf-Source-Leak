package com.google.api.client.http;

import com.google.api.client.util.*;
import com.google.api.client.util.escape.*;

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
