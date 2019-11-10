package com.google.cloud.storage;

import java.util.*;
import com.google.api.client.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

static final class BuilderImpl extends Builder
{
    private String generatedId;
    private String name;
    private Acl.Entity owner;
    private String selfLink;
    private Boolean requesterPays;
    private Boolean versioningEnabled;
    private String indexPage;
    private String notFoundPage;
    private List<DeleteRule> deleteRules;
    private List<LifecycleRule> lifecycleRules;
    private StorageClass storageClass;
    private String location;
    private String etag;
    private Long createTime;
    private Long metageneration;
    private List<Cors> cors;
    private List<Acl> acl;
    private List<Acl> defaultAcl;
    private Map<String, String> labels;
    private String defaultKmsKeyName;
    private Boolean defaultEventBasedHold;
    private Long retentionEffectiveTime;
    private Boolean retentionPolicyIsLocked;
    private Long retentionPeriod;
    private IamConfiguration iamConfiguration;
    private String locationType;
    
    BuilderImpl(final String name) {
        super();
        this.name = name;
    }
    
    BuilderImpl(final BucketInfo bucketInfo) {
        super();
        this.generatedId = BucketInfo.access$1200(bucketInfo);
        this.name = BucketInfo.access$1300(bucketInfo);
        this.etag = BucketInfo.access$1400(bucketInfo);
        this.createTime = BucketInfo.access$1500(bucketInfo);
        this.metageneration = BucketInfo.access$1600(bucketInfo);
        this.location = BucketInfo.access$1700(bucketInfo);
        this.storageClass = BucketInfo.access$1800(bucketInfo);
        this.cors = (List<Cors>)BucketInfo.access$1900(bucketInfo);
        this.acl = (List<Acl>)BucketInfo.access$2000(bucketInfo);
        this.defaultAcl = (List<Acl>)BucketInfo.access$2100(bucketInfo);
        this.owner = BucketInfo.access$2200(bucketInfo);
        this.selfLink = BucketInfo.access$2300(bucketInfo);
        this.versioningEnabled = BucketInfo.access$2400(bucketInfo);
        this.indexPage = BucketInfo.access$2500(bucketInfo);
        this.notFoundPage = BucketInfo.access$2600(bucketInfo);
        this.deleteRules = (List<DeleteRule>)BucketInfo.access$2700(bucketInfo);
        this.lifecycleRules = (List<LifecycleRule>)BucketInfo.access$2800(bucketInfo);
        this.labels = (Map<String, String>)BucketInfo.access$2900(bucketInfo);
        this.requesterPays = BucketInfo.access$3000(bucketInfo);
        this.defaultKmsKeyName = BucketInfo.access$3100(bucketInfo);
        this.defaultEventBasedHold = BucketInfo.access$3200(bucketInfo);
        this.retentionEffectiveTime = BucketInfo.access$3300(bucketInfo);
        this.retentionPolicyIsLocked = BucketInfo.access$3400(bucketInfo);
        this.retentionPeriod = BucketInfo.access$3500(bucketInfo);
        this.iamConfiguration = BucketInfo.access$3600(bucketInfo);
        this.locationType = BucketInfo.access$3700(bucketInfo);
    }
    
    @Override
    public Builder setName(final String name) {
        this.name = Preconditions.checkNotNull(name);
        return this;
    }
    
    @Override
    Builder setGeneratedId(final String generatedId) {
        this.generatedId = generatedId;
        return this;
    }
    
    @Override
    Builder setOwner(final Acl.Entity owner) {
        this.owner = owner;
        return this;
    }
    
    @Override
    Builder setSelfLink(final String selfLink) {
        this.selfLink = selfLink;
        return this;
    }
    
    @Override
    public Builder setVersioningEnabled(final Boolean enable) {
        this.versioningEnabled = MoreObjects.firstNonNull(enable, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    public Builder setRequesterPays(final Boolean enable) {
        this.requesterPays = MoreObjects.firstNonNull(enable, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    public Builder setIndexPage(final String indexPage) {
        this.indexPage = indexPage;
        return this;
    }
    
    @Override
    public Builder setNotFoundPage(final String notFoundPage) {
        this.notFoundPage = notFoundPage;
        return this;
    }
    
    @Deprecated
    @Override
    public Builder setDeleteRules(final Iterable<? extends DeleteRule> rules) {
        this.deleteRules = (List<DeleteRule>)((rules != null) ? ImmutableList.copyOf((Iterable<?>)rules) : null);
        return this;
    }
    
    @Override
    public Builder setLifecycleRules(final Iterable<? extends LifecycleRule> rules) {
        this.lifecycleRules = (List<LifecycleRule>)((rules != null) ? ImmutableList.copyOf((Iterable<?>)rules) : null);
        return this;
    }
    
    @Override
    public Builder setStorageClass(final StorageClass storageClass) {
        this.storageClass = storageClass;
        return this;
    }
    
    @Override
    public Builder setLocation(final String location) {
        this.location = location;
        return this;
    }
    
    @Override
    Builder setEtag(final String etag) {
        this.etag = etag;
        return this;
    }
    
    @Override
    Builder setCreateTime(final Long createTime) {
        this.createTime = createTime;
        return this;
    }
    
    @Override
    Builder setMetageneration(final Long metageneration) {
        this.metageneration = metageneration;
        return this;
    }
    
    @Override
    public Builder setCors(final Iterable<Cors> cors) {
        this.cors = (List<Cors>)((cors != null) ? ImmutableList.copyOf((Iterable<?>)cors) : null);
        return this;
    }
    
    @Override
    public Builder setAcl(final Iterable<Acl> acl) {
        this.acl = (List<Acl>)((acl != null) ? ImmutableList.copyOf((Iterable<?>)acl) : null);
        return this;
    }
    
    @Override
    public Builder setDefaultAcl(final Iterable<Acl> acl) {
        this.defaultAcl = (List<Acl>)((acl != null) ? ImmutableList.copyOf((Iterable<?>)acl) : null);
        return this;
    }
    
    @Override
    public Builder setLabels(final Map<String, String> labels) {
        this.labels = (Map<String, String>)((labels != null) ? ImmutableMap.copyOf((Map)labels) : null);
        return this;
    }
    
    @Override
    public Builder setDefaultKmsKeyName(final String defaultKmsKeyName) {
        this.defaultKmsKeyName = ((defaultKmsKeyName != null) ? defaultKmsKeyName : Data.nullOf(String.class));
        return this;
    }
    
    @Override
    public Builder setDefaultEventBasedHold(final Boolean defaultEventBasedHold) {
        this.defaultEventBasedHold = MoreObjects.firstNonNull(defaultEventBasedHold, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    Builder setRetentionEffectiveTime(final Long retentionEffectiveTime) {
        this.retentionEffectiveTime = MoreObjects.firstNonNull(retentionEffectiveTime, Data.nullOf(Long.class));
        return this;
    }
    
    @Override
    Builder setRetentionPolicyIsLocked(final Boolean retentionPolicyIsLocked) {
        this.retentionPolicyIsLocked = MoreObjects.firstNonNull(retentionPolicyIsLocked, Data.nullOf(Boolean.class));
        return this;
    }
    
    @Override
    public Builder setRetentionPeriod(final Long retentionPeriod) {
        this.retentionPeriod = MoreObjects.firstNonNull(retentionPeriod, Data.nullOf(Long.class));
        return this;
    }
    
    @Override
    public Builder setIamConfiguration(final IamConfiguration iamConfiguration) {
        this.iamConfiguration = iamConfiguration;
        return this;
    }
    
    @Override
    Builder setLocationType(final String locationType) {
        this.locationType = locationType;
        return this;
    }
    
    @Override
    public BucketInfo build() {
        Preconditions.checkNotNull(this.name);
        return new BucketInfo(this);
    }
    
    static /* synthetic */ String access$3800(final BuilderImpl x0) {
        return x0.generatedId;
    }
    
    static /* synthetic */ String access$3900(final BuilderImpl x0) {
        return x0.name;
    }
    
    static /* synthetic */ String access$4000(final BuilderImpl x0) {
        return x0.etag;
    }
    
    static /* synthetic */ Long access$4100(final BuilderImpl x0) {
        return x0.createTime;
    }
    
    static /* synthetic */ Long access$4200(final BuilderImpl x0) {
        return x0.metageneration;
    }
    
    static /* synthetic */ String access$4300(final BuilderImpl x0) {
        return x0.location;
    }
    
    static /* synthetic */ StorageClass access$4400(final BuilderImpl x0) {
        return x0.storageClass;
    }
    
    static /* synthetic */ List access$4500(final BuilderImpl x0) {
        return x0.cors;
    }
    
    static /* synthetic */ List access$4600(final BuilderImpl x0) {
        return x0.acl;
    }
    
    static /* synthetic */ List access$4700(final BuilderImpl x0) {
        return x0.defaultAcl;
    }
    
    static /* synthetic */ Acl.Entity access$4800(final BuilderImpl x0) {
        return x0.owner;
    }
    
    static /* synthetic */ String access$4900(final BuilderImpl x0) {
        return x0.selfLink;
    }
    
    static /* synthetic */ Boolean access$5000(final BuilderImpl x0) {
        return x0.versioningEnabled;
    }
    
    static /* synthetic */ String access$5100(final BuilderImpl x0) {
        return x0.indexPage;
    }
    
    static /* synthetic */ String access$5200(final BuilderImpl x0) {
        return x0.notFoundPage;
    }
    
    static /* synthetic */ List access$5300(final BuilderImpl x0) {
        return x0.deleteRules;
    }
    
    static /* synthetic */ List access$5400(final BuilderImpl x0) {
        return x0.lifecycleRules;
    }
    
    static /* synthetic */ Map access$5500(final BuilderImpl x0) {
        return x0.labels;
    }
    
    static /* synthetic */ Boolean access$5600(final BuilderImpl x0) {
        return x0.requesterPays;
    }
    
    static /* synthetic */ String access$5700(final BuilderImpl x0) {
        return x0.defaultKmsKeyName;
    }
    
    static /* synthetic */ Boolean access$5800(final BuilderImpl x0) {
        return x0.defaultEventBasedHold;
    }
    
    static /* synthetic */ Long access$5900(final BuilderImpl x0) {
        return x0.retentionEffectiveTime;
    }
    
    static /* synthetic */ Boolean access$6000(final BuilderImpl x0) {
        return x0.retentionPolicyIsLocked;
    }
    
    static /* synthetic */ Long access$6100(final BuilderImpl x0) {
        return x0.retentionPeriod;
    }
    
    static /* synthetic */ IamConfiguration access$6200(final BuilderImpl x0) {
        return x0.iamConfiguration;
    }
    
    static /* synthetic */ String access$6300(final BuilderImpl x0) {
        return x0.locationType;
    }
}
