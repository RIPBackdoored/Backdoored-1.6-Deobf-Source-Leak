package com.google.cloud.storage;

import java.io.*;
import com.google.api.client.util.*;
import java.util.*;

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
