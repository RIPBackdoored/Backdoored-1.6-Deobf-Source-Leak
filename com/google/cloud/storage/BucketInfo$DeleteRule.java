package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

@Deprecated
public abstract static class DeleteRule implements Serializable
{
    private static final long serialVersionUID = 3137971668395933033L;
    private static final String SUPPORTED_ACTION = "Delete";
    private final Type type;
    
    DeleteRule(final Type type) {
        super();
        this.type = type;
    }
    
    public Type getType() {
        return this.type;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.type);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final DeleteRule other = (DeleteRule)obj;
        return Objects.equals(this.toPb(), other.toPb());
    }
    
    Bucket.Lifecycle.Rule toPb() {
        final Bucket.Lifecycle.Rule rule = new Bucket.Lifecycle.Rule();
        rule.setAction(new Bucket.Lifecycle.Rule.Action().setType("Delete"));
        final Bucket.Lifecycle.Rule.Condition condition = new Bucket.Lifecycle.Rule.Condition();
        this.populateCondition(condition);
        rule.setCondition(condition);
        return rule;
    }
    
    abstract void populateCondition(final Bucket.Lifecycle.Rule.Condition p0);
    
    static DeleteRule fromPb(final Bucket.Lifecycle.Rule rule) {
        if (rule.getAction() != null && "Delete".endsWith(rule.getAction().getType())) {
            final Bucket.Lifecycle.Rule.Condition condition = rule.getCondition();
            final Integer age = condition.getAge();
            if (age != null) {
                return new AgeDeleteRule(age);
            }
            final DateTime dateTime = condition.getCreatedBefore();
            if (dateTime != null) {
                return new CreatedBeforeDeleteRule(dateTime.getValue());
            }
            final Integer numNewerVersions = condition.getNumNewerVersions();
            if (numNewerVersions != null) {
                return new NumNewerVersionsDeleteRule(numNewerVersions);
            }
            final Boolean isLive = condition.getIsLive();
            if (isLive != null) {
                return new IsLiveDeleteRule(isLive);
            }
        }
        return new RawDeleteRule(rule);
    }
    
    public enum Type
    {
        AGE, 
        CREATE_BEFORE, 
        NUM_NEWER_VERSIONS, 
        IS_LIVE, 
        UNKNOWN;
        
        private static final /* synthetic */ Type[] $VALUES;
        
        public static Type[] values() {
            return Type.$VALUES.clone();
        }
        
        public static Type valueOf(final String name) {
            return Enum.valueOf(Type.class, name);
        }
        
        static {
            $VALUES = new Type[] { Type.AGE, Type.CREATE_BEFORE, Type.NUM_NEWER_VERSIONS, Type.IS_LIVE, Type.UNKNOWN };
        }
    }
}
