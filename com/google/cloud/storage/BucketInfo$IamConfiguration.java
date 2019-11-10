package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

public static class IamConfiguration implements Serializable
{
    private static final long serialVersionUID = -8671736104909424616L;
    private Boolean isBucketPolicyOnlyEnabled;
    private Long bucketPolicyOnlyLockedTime;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final IamConfiguration other = (IamConfiguration)o;
        return Objects.equals(this.toPb(), other.toPb());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.isBucketPolicyOnlyEnabled, this.bucketPolicyOnlyLockedTime);
    }
    
    private IamConfiguration(final Builder builder) {
        super();
        this.isBucketPolicyOnlyEnabled = builder.isBucketPolicyOnlyEnabled;
        this.bucketPolicyOnlyLockedTime = builder.bucketPolicyOnlyLockedTime;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public Builder toBuilder() {
        final Builder builder = new Builder();
        builder.isBucketPolicyOnlyEnabled = this.isBucketPolicyOnlyEnabled;
        builder.bucketPolicyOnlyLockedTime = this.bucketPolicyOnlyLockedTime;
        return builder;
    }
    
    public Boolean isBucketPolicyOnlyEnabled() {
        return this.isBucketPolicyOnlyEnabled;
    }
    
    public Long getBucketPolicyOnlyLockedTime() {
        return this.bucketPolicyOnlyLockedTime;
    }
    
    Bucket.IamConfiguration toPb() {
        final Bucket.IamConfiguration iamConfiguration = new Bucket.IamConfiguration();
        final Bucket.IamConfiguration.BucketPolicyOnly bucketPolicyOnly = new Bucket.IamConfiguration.BucketPolicyOnly();
        bucketPolicyOnly.setEnabled(this.isBucketPolicyOnlyEnabled);
        bucketPolicyOnly.setLockedTime((this.bucketPolicyOnlyLockedTime == null) ? null : new DateTime(this.bucketPolicyOnlyLockedTime));
        iamConfiguration.setBucketPolicyOnly(bucketPolicyOnly);
        return iamConfiguration;
    }
    
    static IamConfiguration fromPb(final Bucket.IamConfiguration iamConfiguration) {
        final Bucket.IamConfiguration.BucketPolicyOnly bucketPolicyOnly = iamConfiguration.getBucketPolicyOnly();
        final DateTime lockedTime = bucketPolicyOnly.getLockedTime();
        return newBuilder().setIsBucketPolicyOnlyEnabled(bucketPolicyOnly.getEnabled()).setBucketPolicyOnlyLockedTime((lockedTime == null) ? null : Long.valueOf(lockedTime.getValue())).build();
    }
    
    IamConfiguration(final Builder x0, final BucketInfo$1 x1) {
        this(x0);
    }
    
    public static class Builder
    {
        private Boolean isBucketPolicyOnlyEnabled;
        private Long bucketPolicyOnlyLockedTime;
        
        public Builder() {
            super();
        }
        
        public Builder setIsBucketPolicyOnlyEnabled(final Boolean isBucketPolicyOnlyEnabled) {
            this.isBucketPolicyOnlyEnabled = isBucketPolicyOnlyEnabled;
            return this;
        }
        
        Builder setBucketPolicyOnlyLockedTime(final Long bucketPolicyOnlyLockedTime) {
            this.bucketPolicyOnlyLockedTime = bucketPolicyOnlyLockedTime;
            return this;
        }
        
        public IamConfiguration build() {
            return new IamConfiguration(this);
        }
        
        static /* synthetic */ Boolean access$000(final Builder x0) {
            return x0.isBucketPolicyOnlyEnabled;
        }
        
        static /* synthetic */ Long access$100(final Builder x0) {
            return x0.bucketPolicyOnlyLockedTime;
        }
        
        static /* synthetic */ Boolean access$002(final Builder x0, final Boolean x1) {
            return x0.isBucketPolicyOnlyEnabled = x1;
        }
        
        static /* synthetic */ Long access$102(final Builder x0, final Long x1) {
            return x0.bucketPolicyOnlyLockedTime = x1;
        }
    }
}
