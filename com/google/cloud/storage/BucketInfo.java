package com.google.cloud.storage;

import com.google.api.core.*;
import com.google.api.client.util.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import java.io.*;
import com.google.api.client.json.jackson2.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class BucketInfo implements Serializable
{
    static final Function<Bucket, BucketInfo> FROM_PB_FUNCTION;
    static final Function<BucketInfo, Bucket> TO_PB_FUNCTION;
    private static final long serialVersionUID = -4712013629621638459L;
    private final String generatedId;
    private final String name;
    private final Acl.Entity owner;
    private final String selfLink;
    private final Boolean requesterPays;
    private final Boolean versioningEnabled;
    private final String indexPage;
    private final String notFoundPage;
    private final List<DeleteRule> deleteRules;
    private final List<LifecycleRule> lifecycleRules;
    private final String etag;
    private final Long createTime;
    private final Long metageneration;
    private final List<Cors> cors;
    private final List<Acl> acl;
    private final List<Acl> defaultAcl;
    private final String location;
    private final StorageClass storageClass;
    private final Map<String, String> labels;
    private final String defaultKmsKeyName;
    private final Boolean defaultEventBasedHold;
    private final Long retentionEffectiveTime;
    private final Boolean retentionPolicyIsLocked;
    private final Long retentionPeriod;
    private final IamConfiguration iamConfiguration;
    private final String locationType;
    
    BucketInfo(final BuilderImpl builder) {
        super();
        this.generatedId = builder.generatedId;
        this.name = builder.name;
        this.etag = builder.etag;
        this.createTime = builder.createTime;
        this.metageneration = builder.metageneration;
        this.location = builder.location;
        this.storageClass = builder.storageClass;
        this.cors = builder.cors;
        this.acl = builder.acl;
        this.defaultAcl = builder.defaultAcl;
        this.owner = builder.owner;
        this.selfLink = builder.selfLink;
        this.versioningEnabled = builder.versioningEnabled;
        this.indexPage = builder.indexPage;
        this.notFoundPage = builder.notFoundPage;
        this.deleteRules = builder.deleteRules;
        this.lifecycleRules = builder.lifecycleRules;
        this.labels = builder.labels;
        this.requesterPays = builder.requesterPays;
        this.defaultKmsKeyName = builder.defaultKmsKeyName;
        this.defaultEventBasedHold = builder.defaultEventBasedHold;
        this.retentionEffectiveTime = builder.retentionEffectiveTime;
        this.retentionPolicyIsLocked = builder.retentionPolicyIsLocked;
        this.retentionPeriod = builder.retentionPeriod;
        this.iamConfiguration = builder.iamConfiguration;
        this.locationType = builder.locationType;
    }
    
    public String getGeneratedId() {
        return this.generatedId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Acl.Entity getOwner() {
        return this.owner;
    }
    
    public String getSelfLink() {
        return this.selfLink;
    }
    
    public Boolean versioningEnabled() {
        return Data.isNull(this.versioningEnabled) ? null : this.versioningEnabled;
    }
    
    public Boolean requesterPays() {
        return Data.isNull(this.requesterPays) ? null : this.requesterPays;
    }
    
    public String getIndexPage() {
        return this.indexPage;
    }
    
    public String getNotFoundPage() {
        return this.notFoundPage;
    }
    
    @Deprecated
    public List<? extends DeleteRule> getDeleteRules() {
        return this.deleteRules;
    }
    
    public List<? extends LifecycleRule> getLifecycleRules() {
        return this.lifecycleRules;
    }
    
    public String getEtag() {
        return this.etag;
    }
    
    public Long getCreateTime() {
        return this.createTime;
    }
    
    public Long getMetageneration() {
        return this.metageneration;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String getLocationType() {
        return this.locationType;
    }
    
    public StorageClass getStorageClass() {
        return this.storageClass;
    }
    
    public List<Cors> getCors() {
        return this.cors;
    }
    
    public List<Acl> getAcl() {
        return this.acl;
    }
    
    public List<Acl> getDefaultAcl() {
        return this.defaultAcl;
    }
    
    public Map<String, String> getLabels() {
        return this.labels;
    }
    
    public String getDefaultKmsKeyName() {
        return this.defaultKmsKeyName;
    }
    
    @BetaApi
    public Boolean getDefaultEventBasedHold() {
        return Data.isNull(this.defaultEventBasedHold) ? null : this.defaultEventBasedHold;
    }
    
    @BetaApi
    public Long getRetentionEffectiveTime() {
        return this.retentionEffectiveTime;
    }
    
    @BetaApi
    public Boolean retentionPolicyIsLocked() {
        return Data.isNull(this.retentionPolicyIsLocked) ? null : this.retentionPolicyIsLocked;
    }
    
    @BetaApi
    public Long getRetentionPeriod() {
        return this.retentionPeriod;
    }
    
    @BetaApi
    public IamConfiguration getIamConfiguration() {
        return this.iamConfiguration;
    }
    
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj == this || (obj != null && obj.getClass().equals(BucketInfo.class) && Objects.equals(this.toPb(), ((BucketInfo)obj).toPb()));
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.name).toString();
    }
    
    Bucket toPb() {
        final Bucket bucketPb = new Bucket();
        bucketPb.setId(this.generatedId);
        bucketPb.setName(this.name);
        bucketPb.setEtag(this.etag);
        if (this.createTime != null) {
            bucketPb.setTimeCreated(new DateTime(this.createTime));
        }
        if (this.metageneration != null) {
            bucketPb.setMetageneration(this.metageneration);
        }
        if (this.location != null) {
            bucketPb.setLocation(this.location);
        }
        if (this.locationType != null) {
            bucketPb.setLocationType(this.locationType);
        }
        if (this.storageClass != null) {
            bucketPb.setStorageClass(this.storageClass.toString());
        }
        if (this.cors != null) {
            bucketPb.setCors((List)Lists.transform(this.cors, (Function<? super Cors, ?>)Cors.TO_PB_FUNCTION));
        }
        if (this.acl != null) {
            bucketPb.setAcl((List)Lists.transform(this.acl, (Function<? super Acl, ?>)new Function<Acl, BucketAccessControl>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$3() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public BucketAccessControl apply(final Acl acl) {
                    return acl.toBucketPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Acl)o);
                }
            }));
        }
        if (this.defaultAcl != null) {
            bucketPb.setDefaultObjectAcl((List)Lists.transform(this.defaultAcl, (Function<? super Acl, ?>)new Function<Acl, ObjectAccessControl>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$4() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl apply(final Acl acl) {
                    return acl.toObjectPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Acl)o);
                }
            }));
        }
        if (this.owner != null) {
            bucketPb.setOwner(new Bucket.Owner().setEntity(this.owner.toPb()));
        }
        bucketPb.setSelfLink(this.selfLink);
        if (this.versioningEnabled != null) {
            bucketPb.setVersioning(new Bucket.Versioning().setEnabled(this.versioningEnabled));
        }
        if (this.requesterPays != null) {
            final Bucket.Billing billing = new Bucket.Billing();
            billing.setRequesterPays(this.requesterPays);
            bucketPb.setBilling(billing);
        }
        if (this.indexPage != null || this.notFoundPage != null) {
            final Bucket.Website website = new Bucket.Website();
            website.setMainPageSuffix(this.indexPage);
            website.setNotFoundPage(this.notFoundPage);
            bucketPb.setWebsite(website);
        }
        final Set<Bucket.Lifecycle.Rule> rules = new HashSet<Bucket.Lifecycle.Rule>();
        if (this.deleteRules != null) {
            rules.addAll((Collection<? extends Bucket.Lifecycle.Rule>)Lists.transform(this.deleteRules, (Function<? super DeleteRule, ?>)new Function<DeleteRule, Bucket.Lifecycle.Rule>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$5() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Bucket.Lifecycle.Rule apply(final DeleteRule deleteRule) {
                    return deleteRule.toPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((DeleteRule)o);
                }
            }));
        }
        if (this.lifecycleRules != null) {
            rules.addAll((Collection<? extends Bucket.Lifecycle.Rule>)Lists.transform(this.lifecycleRules, (Function<? super LifecycleRule, ?>)new Function<LifecycleRule, Bucket.Lifecycle.Rule>() {
                final /* synthetic */ BucketInfo this$0;
                
                BucketInfo$6() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Bucket.Lifecycle.Rule apply(final LifecycleRule lifecycleRule) {
                    return lifecycleRule.toPb();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((LifecycleRule)o);
                }
            }));
        }
        if (!rules.isEmpty()) {
            final Bucket.Lifecycle lifecycle = new Bucket.Lifecycle();
            lifecycle.setRule((List)ImmutableList.copyOf((Collection<?>)rules));
            bucketPb.setLifecycle(lifecycle);
        }
        if (this.labels != null) {
            bucketPb.setLabels((Map)this.labels);
        }
        if (this.defaultKmsKeyName != null) {
            bucketPb.setEncryption(new Bucket.Encryption().setDefaultKmsKeyName(this.defaultKmsKeyName));
        }
        if (this.defaultEventBasedHold != null) {
            bucketPb.setDefaultEventBasedHold(this.defaultEventBasedHold);
        }
        if (this.retentionPeriod != null) {
            if (Data.isNull(this.retentionPeriod)) {
                bucketPb.setRetentionPolicy((Bucket.RetentionPolicy)Data.nullOf(Bucket.RetentionPolicy.class));
            }
            else {
                final Bucket.RetentionPolicy retentionPolicy = new Bucket.RetentionPolicy();
                retentionPolicy.setRetentionPeriod(this.retentionPeriod);
                if (this.retentionEffectiveTime != null) {
                    retentionPolicy.setEffectiveTime(new DateTime(this.retentionEffectiveTime));
                }
                if (this.retentionPolicyIsLocked != null) {
                    retentionPolicy.setIsLocked(this.retentionPolicyIsLocked);
                }
                bucketPb.setRetentionPolicy(retentionPolicy);
            }
        }
        if (this.iamConfiguration != null) {
            bucketPb.setIamConfiguration(this.iamConfiguration.toPb());
        }
        return bucketPb;
    }
    
    public static BucketInfo of(final String name) {
        return newBuilder(name).build();
    }
    
    public static Builder newBuilder(final String name) {
        return new BuilderImpl(name);
    }
    
    static BucketInfo fromPb(final Bucket bucketPb) {
        final Builder builder = new BuilderImpl(bucketPb.getName());
        if (bucketPb.getId() != null) {
            builder.setGeneratedId(bucketPb.getId());
        }
        if (bucketPb.getEtag() != null) {
            builder.setEtag(bucketPb.getEtag());
        }
        if (bucketPb.getMetageneration() != null) {
            builder.setMetageneration(bucketPb.getMetageneration());
        }
        if (bucketPb.getSelfLink() != null) {
            builder.setSelfLink(bucketPb.getSelfLink());
        }
        if (bucketPb.getTimeCreated() != null) {
            builder.setCreateTime(bucketPb.getTimeCreated().getValue());
        }
        if (bucketPb.getLocation() != null) {
            builder.setLocation(bucketPb.getLocation());
        }
        if (bucketPb.getStorageClass() != null) {
            builder.setStorageClass(StorageClass.valueOf(bucketPb.getStorageClass()));
        }
        if (bucketPb.getCors() != null) {
            builder.setCors((Iterable<Cors>)Lists.transform((List<Object>)bucketPb.getCors(), (Function<? super Object, ?>)Cors.FROM_PB_FUNCTION));
        }
        if (bucketPb.getAcl() != null) {
            builder.setAcl((Iterable<Acl>)Lists.transform((List<Object>)bucketPb.getAcl(), (Function<? super Object, ?>)new Function<BucketAccessControl, Acl>() {
                BucketInfo$7() {
                    super();
                }
                
                @Override
                public Acl apply(final BucketAccessControl bucketAccessControl) {
                    return Acl.fromPb(bucketAccessControl);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((BucketAccessControl)o);
                }
            }));
        }
        if (bucketPb.getDefaultObjectAcl() != null) {
            builder.setDefaultAcl((Iterable<Acl>)Lists.transform((List<Object>)bucketPb.getDefaultObjectAcl(), (Function<? super Object, ?>)new Function<ObjectAccessControl, Acl>() {
                BucketInfo$8() {
                    super();
                }
                
                @Override
                public Acl apply(final ObjectAccessControl objectAccessControl) {
                    return Acl.fromPb(objectAccessControl);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((ObjectAccessControl)o);
                }
            }));
        }
        if (bucketPb.getOwner() != null) {
            builder.setOwner(Acl.Entity.fromPb(bucketPb.getOwner().getEntity()));
        }
        if (bucketPb.getVersioning() != null) {
            builder.setVersioningEnabled(bucketPb.getVersioning().getEnabled());
        }
        final Bucket.Website website = bucketPb.getWebsite();
        if (website != null) {
            builder.setIndexPage(website.getMainPageSuffix());
            builder.setNotFoundPage(website.getNotFoundPage());
        }
        if (bucketPb.getLifecycle() != null && bucketPb.getLifecycle().getRule() != null) {
            builder.setLifecycleRules((Iterable<? extends LifecycleRule>)Lists.transform((List<Object>)bucketPb.getLifecycle().getRule(), (Function<? super Object, ?>)new Function<Bucket.Lifecycle.Rule, LifecycleRule>() {
                BucketInfo$9() {
                    super();
                }
                
                @Override
                public LifecycleRule apply(final Bucket.Lifecycle.Rule rule) {
                    return LifecycleRule.fromPb(rule);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Bucket.Lifecycle.Rule)o);
                }
            }));
            builder.setDeleteRules((Iterable<? extends DeleteRule>)Lists.transform((List<Object>)bucketPb.getLifecycle().getRule(), (Function<? super Object, ?>)new Function<Bucket.Lifecycle.Rule, DeleteRule>() {
                BucketInfo$10() {
                    super();
                }
                
                @Override
                public DeleteRule apply(final Bucket.Lifecycle.Rule rule) {
                    return DeleteRule.fromPb(rule);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Bucket.Lifecycle.Rule)o);
                }
            }));
        }
        if (bucketPb.getLabels() != null) {
            builder.setLabels(bucketPb.getLabels());
        }
        final Bucket.Billing billing = bucketPb.getBilling();
        if (billing != null) {
            builder.setRequesterPays(billing.getRequesterPays());
        }
        final Bucket.Encryption encryption = bucketPb.getEncryption();
        if (encryption != null && encryption.getDefaultKmsKeyName() != null && !encryption.getDefaultKmsKeyName().isEmpty()) {
            builder.setDefaultKmsKeyName(encryption.getDefaultKmsKeyName());
        }
        if (bucketPb.getDefaultEventBasedHold() != null) {
            builder.setDefaultEventBasedHold(bucketPb.getDefaultEventBasedHold());
        }
        final Bucket.RetentionPolicy retentionPolicy = bucketPb.getRetentionPolicy();
        if (retentionPolicy != null) {
            if (retentionPolicy.getEffectiveTime() != null) {
                builder.setRetentionEffectiveTime(retentionPolicy.getEffectiveTime().getValue());
            }
            if (retentionPolicy.getIsLocked() != null) {
                builder.setRetentionPolicyIsLocked(retentionPolicy.getIsLocked());
            }
            if (retentionPolicy.getRetentionPeriod() != null) {
                builder.setRetentionPeriod(retentionPolicy.getRetentionPeriod());
            }
        }
        final Bucket.IamConfiguration iamConfiguration = bucketPb.getIamConfiguration();
        if (bucketPb.getLocationType() != null) {
            builder.setLocationType(bucketPb.getLocationType());
        }
        if (iamConfiguration != null) {
            builder.setIamConfiguration(IamConfiguration.fromPb(iamConfiguration));
        }
        return builder.build();
    }
    
    static /* synthetic */ String access$1200(final BucketInfo x0) {
        return x0.generatedId;
    }
    
    static /* synthetic */ String access$1300(final BucketInfo x0) {
        return x0.name;
    }
    
    static /* synthetic */ String access$1400(final BucketInfo x0) {
        return x0.etag;
    }
    
    static /* synthetic */ Long access$1500(final BucketInfo x0) {
        return x0.createTime;
    }
    
    static /* synthetic */ Long access$1600(final BucketInfo x0) {
        return x0.metageneration;
    }
    
    static /* synthetic */ String access$1700(final BucketInfo x0) {
        return x0.location;
    }
    
    static /* synthetic */ StorageClass access$1800(final BucketInfo x0) {
        return x0.storageClass;
    }
    
    static /* synthetic */ List access$1900(final BucketInfo x0) {
        return x0.cors;
    }
    
    static /* synthetic */ List access$2000(final BucketInfo x0) {
        return x0.acl;
    }
    
    static /* synthetic */ List access$2100(final BucketInfo x0) {
        return x0.defaultAcl;
    }
    
    static /* synthetic */ Acl.Entity access$2200(final BucketInfo x0) {
        return x0.owner;
    }
    
    static /* synthetic */ String access$2300(final BucketInfo x0) {
        return x0.selfLink;
    }
    
    static /* synthetic */ Boolean access$2400(final BucketInfo x0) {
        return x0.versioningEnabled;
    }
    
    static /* synthetic */ String access$2500(final BucketInfo x0) {
        return x0.indexPage;
    }
    
    static /* synthetic */ String access$2600(final BucketInfo x0) {
        return x0.notFoundPage;
    }
    
    static /* synthetic */ List access$2700(final BucketInfo x0) {
        return x0.deleteRules;
    }
    
    static /* synthetic */ List access$2800(final BucketInfo x0) {
        return x0.lifecycleRules;
    }
    
    static /* synthetic */ Map access$2900(final BucketInfo x0) {
        return x0.labels;
    }
    
    static /* synthetic */ Boolean access$3000(final BucketInfo x0) {
        return x0.requesterPays;
    }
    
    static /* synthetic */ String access$3100(final BucketInfo x0) {
        return x0.defaultKmsKeyName;
    }
    
    static /* synthetic */ Boolean access$3200(final BucketInfo x0) {
        return x0.defaultEventBasedHold;
    }
    
    static /* synthetic */ Long access$3300(final BucketInfo x0) {
        return x0.retentionEffectiveTime;
    }
    
    static /* synthetic */ Boolean access$3400(final BucketInfo x0) {
        return x0.retentionPolicyIsLocked;
    }
    
    static /* synthetic */ Long access$3500(final BucketInfo x0) {
        return x0.retentionPeriod;
    }
    
    static /* synthetic */ IamConfiguration access$3600(final BucketInfo x0) {
        return x0.iamConfiguration;
    }
    
    static /* synthetic */ String access$3700(final BucketInfo x0) {
        return x0.locationType;
    }
    
    static {
        FROM_PB_FUNCTION = new Function<Bucket, BucketInfo>() {
            BucketInfo$1() {
                super();
            }
            
            @Override
            public BucketInfo apply(final Bucket pb) {
                return BucketInfo.fromPb(pb);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Bucket)o);
            }
        };
        TO_PB_FUNCTION = new Function<BucketInfo, Bucket>() {
            BucketInfo$2() {
                super();
            }
            
            @Override
            public Bucket apply(final BucketInfo bucketInfo) {
                return bucketInfo.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BucketInfo)o);
            }
        };
    }
    
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
    
    @Deprecated
    public static class AgeDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = 5697166940712116380L;
        private final int daysToLive;
        
        public AgeDeleteRule(final int daysToLive) {
            super(Type.AGE);
            this.daysToLive = daysToLive;
        }
        
        public int getDaysToLive() {
            return this.daysToLive;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition condition) {
            condition.setAge(Integer.valueOf(this.daysToLive));
        }
    }
    
    static class RawDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = -7166938278642301933L;
        private transient Bucket.Lifecycle.Rule rule;
        
        RawDeleteRule(final Bucket.Lifecycle.Rule rule) {
            super(Type.UNKNOWN);
            this.rule = rule;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition condition) {
            throw new UnsupportedOperationException();
        }
        
        private void writeObject(final ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            out.writeUTF(this.rule.toString());
        }
        
        private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            this.rule = new JacksonFactory().fromString(in.readUTF(), Bucket.Lifecycle.Rule.class);
        }
        
        @Override
        Bucket.Lifecycle.Rule toPb() {
            return this.rule;
        }
    }
    
    @Deprecated
    public static class CreatedBeforeDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = 881692650279195867L;
        private final long timeMillis;
        
        public CreatedBeforeDeleteRule(final long timeMillis) {
            super(Type.CREATE_BEFORE);
            this.timeMillis = timeMillis;
        }
        
        public long getTimeMillis() {
            return this.timeMillis;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition condition) {
            condition.setCreatedBefore(new DateTime(true, this.timeMillis, 0));
        }
    }
    
    @Deprecated
    public static class NumNewerVersionsDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = -1955554976528303894L;
        private final int numNewerVersions;
        
        public NumNewerVersionsDeleteRule(final int numNewerVersions) {
            super(Type.NUM_NEWER_VERSIONS);
            this.numNewerVersions = numNewerVersions;
        }
        
        public int getNumNewerVersions() {
            return this.numNewerVersions;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition condition) {
            condition.setNumNewerVersions(Integer.valueOf(this.numNewerVersions));
        }
    }
    
    @Deprecated
    public static class IsLiveDeleteRule extends DeleteRule
    {
        private static final long serialVersionUID = -3502994563121313364L;
        private final boolean isLive;
        
        public IsLiveDeleteRule(final boolean isLive) {
            super(Type.IS_LIVE);
            this.isLive = isLive;
        }
        
        public boolean isLive() {
            return this.isLive;
        }
        
        @Override
        void populateCondition(final Bucket.Lifecycle.Rule.Condition condition) {
            condition.setIsLive(Boolean.valueOf(this.isLive));
        }
    }
    
    public abstract static class Builder
    {
        Builder() {
            super();
        }
        
        public abstract Builder setName(final String p0);
        
        abstract Builder setGeneratedId(final String p0);
        
        abstract Builder setOwner(final Acl.Entity p0);
        
        abstract Builder setSelfLink(final String p0);
        
        public abstract Builder setRequesterPays(final Boolean p0);
        
        public abstract Builder setVersioningEnabled(final Boolean p0);
        
        public abstract Builder setIndexPage(final String p0);
        
        public abstract Builder setNotFoundPage(final String p0);
        
        @Deprecated
        public abstract Builder setDeleteRules(final Iterable<? extends DeleteRule> p0);
        
        public abstract Builder setLifecycleRules(final Iterable<? extends LifecycleRule> p0);
        
        public abstract Builder setStorageClass(final StorageClass p0);
        
        public abstract Builder setLocation(final String p0);
        
        abstract Builder setEtag(final String p0);
        
        abstract Builder setCreateTime(final Long p0);
        
        abstract Builder setMetageneration(final Long p0);
        
        abstract Builder setLocationType(final String p0);
        
        public abstract Builder setCors(final Iterable<Cors> p0);
        
        public abstract Builder setAcl(final Iterable<Acl> p0);
        
        public abstract Builder setDefaultAcl(final Iterable<Acl> p0);
        
        public abstract Builder setLabels(final Map<String, String> p0);
        
        public abstract Builder setDefaultKmsKeyName(final String p0);
        
        @BetaApi
        public abstract Builder setDefaultEventBasedHold(final Boolean p0);
        
        @BetaApi
        abstract Builder setRetentionEffectiveTime(final Long p0);
        
        @BetaApi
        abstract Builder setRetentionPolicyIsLocked(final Boolean p0);
        
        @BetaApi
        public abstract Builder setRetentionPeriod(final Long p0);
        
        @BetaApi
        public abstract Builder setIamConfiguration(final IamConfiguration p0);
        
        public abstract BucketInfo build();
    }
    
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
            this.generatedId = bucketInfo.generatedId;
            this.name = bucketInfo.name;
            this.etag = bucketInfo.etag;
            this.createTime = bucketInfo.createTime;
            this.metageneration = bucketInfo.metageneration;
            this.location = bucketInfo.location;
            this.storageClass = bucketInfo.storageClass;
            this.cors = bucketInfo.cors;
            this.acl = bucketInfo.acl;
            this.defaultAcl = bucketInfo.defaultAcl;
            this.owner = bucketInfo.owner;
            this.selfLink = bucketInfo.selfLink;
            this.versioningEnabled = bucketInfo.versioningEnabled;
            this.indexPage = bucketInfo.indexPage;
            this.notFoundPage = bucketInfo.notFoundPage;
            this.deleteRules = bucketInfo.deleteRules;
            this.lifecycleRules = bucketInfo.lifecycleRules;
            this.labels = bucketInfo.labels;
            this.requesterPays = bucketInfo.requesterPays;
            this.defaultKmsKeyName = bucketInfo.defaultKmsKeyName;
            this.defaultEventBasedHold = bucketInfo.defaultEventBasedHold;
            this.retentionEffectiveTime = bucketInfo.retentionEffectiveTime;
            this.retentionPolicyIsLocked = bucketInfo.retentionPolicyIsLocked;
            this.retentionPeriod = bucketInfo.retentionPeriod;
            this.iamConfiguration = bucketInfo.iamConfiguration;
            this.locationType = bucketInfo.locationType;
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
}
