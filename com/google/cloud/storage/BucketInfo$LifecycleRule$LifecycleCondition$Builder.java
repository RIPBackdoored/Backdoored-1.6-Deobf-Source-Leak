package com.google.cloud.storage;

import com.google.api.client.util.*;
import java.util.*;

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
