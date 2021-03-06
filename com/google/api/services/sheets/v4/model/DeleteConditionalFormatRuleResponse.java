package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class DeleteConditionalFormatRuleResponse extends GenericJson
{
    @Key
    private ConditionalFormatRule rule;
    
    public DeleteConditionalFormatRuleResponse() {
        super();
    }
    
    public ConditionalFormatRule getRule() {
        return this.rule;
    }
    
    public DeleteConditionalFormatRuleResponse setRule(final ConditionalFormatRule rule) {
        this.rule = rule;
        return this;
    }
    
    @Override
    public DeleteConditionalFormatRuleResponse set(final String fieldName, final Object value) {
        return (DeleteConditionalFormatRuleResponse)super.set(fieldName, value);
    }
    
    @Override
    public DeleteConditionalFormatRuleResponse clone() {
        return (DeleteConditionalFormatRuleResponse)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
