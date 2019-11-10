package com.google.cloud.storage;

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
