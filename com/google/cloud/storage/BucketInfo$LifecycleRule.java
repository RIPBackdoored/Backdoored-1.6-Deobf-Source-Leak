package com.google.cloud.storage;

import java.io.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public static class LifecycleRule implements Serializable
{
    private static final long serialVersionUID = -5739807320148748613L;
    private final LifecycleAction lifecycleAction;
    private final LifecycleCondition lifecycleCondition;
    
    public LifecycleRule(final LifecycleAction action, final LifecycleCondition condition) {
        super();
        if (condition.getIsLive() == null && condition.getAge() == null && condition.getCreatedBefore() == null && condition.getMatchesStorageClass() == null && condition.getNumberOfNewerVersions() == null) {
            throw new IllegalArgumentException("You must specify at least one condition to use object lifecycle management. Please see https://cloud.google.com/storage/docs/lifecycle for details.");
        }
        this.lifecycleAction = action;
        this.lifecycleCondition = condition;
    }
    
    public LifecycleAction getAction() {
        return this.lifecycleAction;
    }
    
    public LifecycleCondition getCondition() {
        return this.lifecycleCondition;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.lifecycleAction, this.lifecycleCondition);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final LifecycleRule other = (LifecycleRule)obj;
        return Objects.equals(this.toPb(), other.toPb());
    }
    
    Bucket.Lifecycle.Rule toPb() {
        final Bucket.Lifecycle.Rule rule = new Bucket.Lifecycle.Rule();
        final Bucket.Lifecycle.Rule.Action action = new Bucket.Lifecycle.Rule.Action().setType(this.lifecycleAction.getActionType());
        if (this.lifecycleAction.getActionType().equals("SetStorageClass")) {
            action.setStorageClass(((SetStorageClassLifecycleAction)this.lifecycleAction).getStorageClass().toString());
        }
        rule.setAction(action);
        final Bucket.Lifecycle.Rule.Condition condition = new Bucket.Lifecycle.Rule.Condition().setAge(this.lifecycleCondition.getAge()).setCreatedBefore((this.lifecycleCondition.getCreatedBefore() == null) ? null : new DateTime(true, this.lifecycleCondition.getCreatedBefore().getValue(), 0)).setIsLive(this.lifecycleCondition.getIsLive()).setNumNewerVersions(this.lifecycleCondition.getNumberOfNewerVersions()).setMatchesStorageClass((List)((this.lifecycleCondition.getMatchesStorageClass() == null) ? null : Lists.transform(this.lifecycleCondition.getMatchesStorageClass(), (Function<? super StorageClass, ?>)Functions.toStringFunction())));
        rule.setCondition(condition);
        return rule;
    }
    
    static LifecycleRule fromPb(final Bucket.Lifecycle.Rule rule) {
        final Bucket.Lifecycle.Rule.Action action = rule.getAction();
        final String type = action.getType();
        LifecycleAction lifecycleAction = null;
        switch (type) {
            case "Delete": {
                lifecycleAction = LifecycleAction.newDeleteAction();
                break;
            }
            case "SetStorageClass": {
                lifecycleAction = LifecycleAction.newSetStorageClassAction(StorageClass.valueOf(action.getStorageClass()));
                break;
            }
            default: {
                throw new UnsupportedOperationException("The specified lifecycle action " + action.getType() + " is not currently supported");
            }
        }
        final Bucket.Lifecycle.Rule.Condition condition = rule.getCondition();
        final LifecycleCondition.Builder conditionBuilder = LifecycleCondition.newBuilder().setAge(condition.getAge()).setCreatedBefore(condition.getCreatedBefore()).setIsLive(condition.getIsLive()).setNumberOfNewerVersions(condition.getNumNewerVersions()).setMatchesStorageClass((condition.getMatchesStorageClass() == null) ? null : Lists.transform((List<Object>)condition.getMatchesStorageClass(), (Function<? super Object, ? extends StorageClass>)new Function<String, StorageClass>() {
            BucketInfo$LifecycleRule$1() {
                super();
            }
            
            @Override
            public StorageClass apply(final String storageClass) {
                return StorageClass.valueOf(storageClass);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        }));
        return new LifecycleRule(lifecycleAction, conditionBuilder.build());
    }
    
    public static class LifecycleCondition implements Serializable
    {
        private static final long serialVersionUID = -6482314338394768785L;
        private final Integer age;
        private final DateTime createdBefore;
        private final Integer numberOfNewerVersions;
        private final Boolean isLive;
        private final List<StorageClass> matchesStorageClass;
        
        private LifecycleCondition(final Builder builder) {
            super();
            this.age = builder.age;
            this.createdBefore = builder.createdBefore;
            this.numberOfNewerVersions = builder.numberOfNewerVersions;
            this.isLive = builder.isLive;
            this.matchesStorageClass = builder.matchesStorageClass;
        }
        
        public Builder toBuilder() {
            return newBuilder().setAge(this.age).setCreatedBefore(this.createdBefore).setNumberOfNewerVersions(this.numberOfNewerVersions).setIsLive(this.isLive).setMatchesStorageClass(this.matchesStorageClass);
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public Integer getAge() {
            return this.age;
        }
        
        public DateTime getCreatedBefore() {
            return this.createdBefore;
        }
        
        public Integer getNumberOfNewerVersions() {
            return this.numberOfNewerVersions;
        }
        
        public Boolean getIsLive() {
            return this.isLive;
        }
        
        public List<StorageClass> getMatchesStorageClass() {
            return this.matchesStorageClass;
        }
        
        LifecycleCondition(final Builder x0, final BucketInfo$1 x1) {
            this(x0);
        }
        
        public static class Builder
        {
            private Integer age;
            private DateTime createdBefore;
            private Integer numberOfNewerVersions;
            private Boolean isLive;
            private List<StorageClass> matchesStorageClass;
            
            private Builder() {
                super();
            }
            
            public Builder setAge(final Integer age) {
                this.age = age;
                return this;
            }
            
            public Builder setCreatedBefore(final DateTime createdBefore) {
                this.createdBefore = createdBefore;
                return this;
            }
            
            public Builder setNumberOfNewerVersions(final Integer numberOfNewerVersions) {
                this.numberOfNewerVersions = numberOfNewerVersions;
                return this;
            }
            
            public Builder setIsLive(final Boolean live) {
                this.isLive = live;
                return this;
            }
            
            public Builder setMatchesStorageClass(final List<StorageClass> matchesStorageClass) {
                this.matchesStorageClass = matchesStorageClass;
                return this;
            }
            
            public LifecycleCondition build() {
                return new LifecycleCondition(this);
            }
            
            static /* synthetic */ Integer access$300(final Builder x0) {
                return x0.age;
            }
            
            static /* synthetic */ DateTime access$400(final Builder x0) {
                return x0.createdBefore;
            }
            
            static /* synthetic */ Integer access$500(final Builder x0) {
                return x0.numberOfNewerVersions;
            }
            
            static /* synthetic */ Boolean access$600(final Builder x0) {
                return x0.isLive;
            }
            
            static /* synthetic */ List access$700(final Builder x0) {
                return x0.matchesStorageClass;
            }
            
            Builder(final BucketInfo$1 x0) {
                this();
            }
        }
    }
    
    public abstract static class LifecycleAction implements Serializable
    {
        private static final long serialVersionUID = 5801228724709173284L;
        
        public LifecycleAction() {
            super();
        }
        
        public abstract String getActionType();
        
        public static DeleteLifecycleAction newDeleteAction() {
            return new DeleteLifecycleAction();
        }
        
        public static SetStorageClassLifecycleAction newSetStorageClassAction(final StorageClass storageClass) {
            return new SetStorageClassLifecycleAction(storageClass);
        }
    }
    
    public static class DeleteLifecycleAction extends LifecycleAction
    {
        public static final String TYPE = "Delete";
        private static final long serialVersionUID = -2050986302222644873L;
        
        private DeleteLifecycleAction() {
            super();
        }
        
        @Override
        public String getActionType() {
            return "Delete";
        }
        
        DeleteLifecycleAction(final BucketInfo$1 x0) {
            this();
        }
    }
    
    public static class SetStorageClassLifecycleAction extends LifecycleAction
    {
        public static final String TYPE = "SetStorageClass";
        private static final long serialVersionUID = -62615467186000899L;
        private final StorageClass storageClass;
        
        private SetStorageClassLifecycleAction(final StorageClass storageClass) {
            super();
            this.storageClass = storageClass;
        }
        
        @Override
        public String getActionType() {
            return "SetStorageClass";
        }
        
        StorageClass getStorageClass() {
            return this.storageClass;
        }
        
        SetStorageClassLifecycleAction(final StorageClass x0, final BucketInfo$1 x1) {
            this(x0);
        }
    }
}
