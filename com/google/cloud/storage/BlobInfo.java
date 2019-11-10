package com.google.cloud.storage;

import java.io.*;
import com.google.common.io.*;
import com.google.api.core.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;
import java.math.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class BlobInfo implements Serializable
{
    static final Function<BlobInfo, StorageObject> INFO_TO_PB_FUNCTION;
    private static final long serialVersionUID = -5625857076205028976L;
    private final BlobId blobId;
    private final String generatedId;
    private final String selfLink;
    private final String cacheControl;
    private final List<Acl> acl;
    private final Acl.Entity owner;
    private final Long size;
    private final String etag;
    private final String md5;
    private final String crc32c;
    private final String mediaLink;
    private final Map<String, String> metadata;
    private final Long metageneration;
    private final Long deleteTime;
    private final Long updateTime;
    private final Long createTime;
    private final String contentType;
    private final String contentEncoding;
    private final String contentDisposition;
    private final String contentLanguage;
    private final StorageClass storageClass;
    private final Integer componentCount;
    private final boolean isDirectory;
    private final CustomerEncryption customerEncryption;
    private final String kmsKeyName;
    private final Boolean eventBasedHold;
    private final Boolean temporaryHold;
    private final Long retentionExpirationTime;
    
    BlobInfo(final BuilderImpl builder) {
        super();
        this.blobId = builder.blobId;
        this.generatedId = builder.generatedId;
        this.cacheControl = builder.cacheControl;
        this.contentEncoding = builder.contentEncoding;
        this.contentType = builder.contentType;
        this.contentDisposition = builder.contentDisposition;
        this.contentLanguage = builder.contentLanguage;
        this.componentCount = builder.componentCount;
        this.customerEncryption = builder.customerEncryption;
        this.acl = builder.acl;
        this.owner = builder.owner;
        this.size = builder.size;
        this.etag = builder.etag;
        this.selfLink = builder.selfLink;
        this.md5 = builder.md5;
        this.crc32c = builder.crc32c;
        this.mediaLink = builder.mediaLink;
        this.metadata = builder.metadata;
        this.metageneration = builder.metageneration;
        this.deleteTime = builder.deleteTime;
        this.updateTime = builder.updateTime;
        this.createTime = builder.createTime;
        this.isDirectory = MoreObjects.firstNonNull(builder.isDirectory, Boolean.FALSE);
        this.storageClass = builder.storageClass;
        this.kmsKeyName = builder.kmsKeyName;
        this.eventBasedHold = builder.eventBasedHold;
        this.temporaryHold = builder.temporaryHold;
        this.retentionExpirationTime = builder.retentionExpirationTime;
    }
    
    public BlobId getBlobId() {
        return this.blobId;
    }
    
    public String getBucket() {
        return this.getBlobId().getBucket();
    }
    
    public String getGeneratedId() {
        return this.generatedId;
    }
    
    public String getName() {
        return this.getBlobId().getName();
    }
    
    public String getCacheControl() {
        return Data.isNull(this.cacheControl) ? null : this.cacheControl;
    }
    
    public List<Acl> getAcl() {
        return this.acl;
    }
    
    public Acl.Entity getOwner() {
        return this.owner;
    }
    
    public Long getSize() {
        return this.size;
    }
    
    public String getContentType() {
        return Data.isNull(this.contentType) ? null : this.contentType;
    }
    
    public String getContentEncoding() {
        return Data.isNull(this.contentEncoding) ? null : this.contentEncoding;
    }
    
    public String getContentDisposition() {
        return Data.isNull(this.contentDisposition) ? null : this.contentDisposition;
    }
    
    public String getContentLanguage() {
        return Data.isNull(this.contentLanguage) ? null : this.contentLanguage;
    }
    
    public Integer getComponentCount() {
        return this.componentCount;
    }
    
    public String getEtag() {
        return this.etag;
    }
    
    public String getSelfLink() {
        return this.selfLink;
    }
    
    public String getMd5() {
        return Data.isNull(this.md5) ? null : this.md5;
    }
    
    public String getMd5ToHexString() {
        if (this.md5 == null) {
            return null;
        }
        final byte[] decodedMd5 = BaseEncoding.base64().decode((CharSequence)this.md5);
        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte b : decodedMd5) {
            stringBuilder.append(String.format("%02x", b & 0xFF));
        }
        return stringBuilder.toString();
    }
    
    public String getCrc32c() {
        return Data.isNull(this.crc32c) ? null : this.crc32c;
    }
    
    public String getCrc32cToHexString() {
        if (this.crc32c == null) {
            return null;
        }
        final byte[] decodeCrc32c = BaseEncoding.base64().decode((CharSequence)this.crc32c);
        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte b : decodeCrc32c) {
            stringBuilder.append(String.format("%02x", b & 0xFF));
        }
        return stringBuilder.toString();
    }
    
    public String getMediaLink() {
        return this.mediaLink;
    }
    
    public Map<String, String> getMetadata() {
        return (this.metadata == null || Data.isNull(this.metadata)) ? null : Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.metadata);
    }
    
    public Long getGeneration() {
        return this.getBlobId().getGeneration();
    }
    
    public Long getMetageneration() {
        return this.metageneration;
    }
    
    public Long getDeleteTime() {
        return this.deleteTime;
    }
    
    public Long getUpdateTime() {
        return this.updateTime;
    }
    
    public Long getCreateTime() {
        return this.createTime;
    }
    
    public boolean isDirectory() {
        return this.isDirectory;
    }
    
    public CustomerEncryption getCustomerEncryption() {
        return this.customerEncryption;
    }
    
    public StorageClass getStorageClass() {
        return this.storageClass;
    }
    
    public String getKmsKeyName() {
        return this.kmsKeyName;
    }
    
    @BetaApi
    public Boolean getEventBasedHold() {
        return Data.isNull(this.eventBasedHold) ? null : this.eventBasedHold;
    }
    
    @BetaApi
    public Boolean getTemporaryHold() {
        return Data.isNull(this.temporaryHold) ? null : this.temporaryHold;
    }
    
    @BetaApi
    public Long getRetentionExpirationTime() {
        return Data.isNull(this.retentionExpirationTime) ? null : this.retentionExpirationTime;
    }
    
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("bucket", this.getBucket()).add("name", this.getName()).add("generation", this.getGeneration()).add("size", this.getSize()).add("content-type", this.getContentType()).add("metadata", this.getMetadata()).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.blobId);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj == this || (obj != null && obj.getClass().equals(BlobInfo.class) && Objects.equals(this.toPb(), ((BlobInfo)obj).toPb()));
    }
    
    StorageObject toPb() {
        final StorageObject storageObject = this.blobId.toPb();
        if (this.acl != null) {
            storageObject.setAcl((List)Lists.transform(this.acl, (Function<? super Acl, ?>)new Function<Acl, ObjectAccessControl>() {
                final /* synthetic */ BlobInfo this$0;
                
                BlobInfo$2() {
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
        if (this.deleteTime != null) {
            storageObject.setTimeDeleted(new DateTime(this.deleteTime));
        }
        if (this.updateTime != null) {
            storageObject.setUpdated(new DateTime(this.updateTime));
        }
        if (this.createTime != null) {
            storageObject.setTimeCreated(new DateTime(this.createTime));
        }
        if (this.size != null) {
            storageObject.setSize(BigInteger.valueOf(this.size));
        }
        if (this.owner != null) {
            storageObject.setOwner(new StorageObject.Owner().setEntity(this.owner.toPb()));
        }
        if (this.storageClass != null) {
            storageObject.setStorageClass(this.storageClass.toString());
        }
        Map<String, String> pbMetadata = this.metadata;
        if (this.metadata != null && !Data.isNull(this.metadata)) {
            pbMetadata = (Map<String, String>)Maps.newHashMapWithExpectedSize(this.metadata.size());
            for (final Map.Entry<String, String> entry : this.metadata.entrySet()) {
                pbMetadata.put(entry.getKey(), MoreObjects.firstNonNull(entry.getValue(), Data.nullOf(String.class)));
            }
        }
        if (this.customerEncryption != null) {
            storageObject.setCustomerEncryption(this.customerEncryption.toPb());
        }
        if (this.retentionExpirationTime != null) {
            storageObject.setRetentionExpirationTime(new DateTime(this.retentionExpirationTime));
        }
        storageObject.setKmsKeyName(this.kmsKeyName);
        storageObject.setEventBasedHold(this.eventBasedHold);
        storageObject.setTemporaryHold(this.temporaryHold);
        storageObject.setMetadata((Map)pbMetadata);
        storageObject.setCacheControl(this.cacheControl);
        storageObject.setContentEncoding(this.contentEncoding);
        storageObject.setCrc32c(this.crc32c);
        storageObject.setContentType(this.contentType);
        storageObject.setMd5Hash(this.md5);
        storageObject.setMediaLink(this.mediaLink);
        storageObject.setMetageneration(this.metageneration);
        storageObject.setContentDisposition(this.contentDisposition);
        storageObject.setComponentCount(this.componentCount);
        storageObject.setContentLanguage(this.contentLanguage);
        storageObject.setEtag(this.etag);
        storageObject.setId(this.generatedId);
        storageObject.setSelfLink(this.selfLink);
        return storageObject;
    }
    
    public static Builder newBuilder(final BucketInfo bucketInfo, final String name) {
        return newBuilder(bucketInfo.getName(), name);
    }
    
    public static Builder newBuilder(final String bucket, final String name) {
        return newBuilder(BlobId.of(bucket, name));
    }
    
    public static Builder newBuilder(final BucketInfo bucketInfo, final String name, final Long generation) {
        return newBuilder(bucketInfo.getName(), name, generation);
    }
    
    public static Builder newBuilder(final String bucket, final String name, final Long generation) {
        return newBuilder(BlobId.of(bucket, name, generation));
    }
    
    public static Builder newBuilder(final BlobId blobId) {
        return new BuilderImpl(blobId);
    }
    
    static BlobInfo fromPb(final StorageObject storageObject) {
        final Builder builder = newBuilder(BlobId.fromPb(storageObject));
        if (storageObject.getCacheControl() != null) {
            builder.setCacheControl(storageObject.getCacheControl());
        }
        if (storageObject.getContentEncoding() != null) {
            builder.setContentEncoding(storageObject.getContentEncoding());
        }
        if (storageObject.getCrc32c() != null) {
            builder.setCrc32c(storageObject.getCrc32c());
        }
        if (storageObject.getContentType() != null) {
            builder.setContentType(storageObject.getContentType());
        }
        if (storageObject.getMd5Hash() != null) {
            builder.setMd5(storageObject.getMd5Hash());
        }
        if (storageObject.getMediaLink() != null) {
            builder.setMediaLink(storageObject.getMediaLink());
        }
        if (storageObject.getMetageneration() != null) {
            builder.setMetageneration(storageObject.getMetageneration());
        }
        if (storageObject.getContentDisposition() != null) {
            builder.setContentDisposition(storageObject.getContentDisposition());
        }
        if (storageObject.getComponentCount() != null) {
            builder.setComponentCount(storageObject.getComponentCount());
        }
        if (storageObject.getContentLanguage() != null) {
            builder.setContentLanguage(storageObject.getContentLanguage());
        }
        if (storageObject.getEtag() != null) {
            builder.setEtag(storageObject.getEtag());
        }
        if (storageObject.getId() != null) {
            builder.setGeneratedId(storageObject.getId());
        }
        if (storageObject.getSelfLink() != null) {
            builder.setSelfLink(storageObject.getSelfLink());
        }
        if (storageObject.getMetadata() != null) {
            builder.setMetadata(storageObject.getMetadata());
        }
        if (storageObject.getTimeDeleted() != null) {
            builder.setDeleteTime(storageObject.getTimeDeleted().getValue());
        }
        if (storageObject.getUpdated() != null) {
            builder.setUpdateTime(storageObject.getUpdated().getValue());
        }
        if (storageObject.getTimeCreated() != null) {
            builder.setCreateTime(storageObject.getTimeCreated().getValue());
        }
        if (storageObject.getSize() != null) {
            builder.setSize(storageObject.getSize().longValue());
        }
        if (storageObject.getOwner() != null) {
            builder.setOwner(Acl.Entity.fromPb(storageObject.getOwner().getEntity()));
        }
        if (storageObject.getAcl() != null) {
            builder.setAcl(Lists.transform((List<Object>)storageObject.getAcl(), (Function<? super Object, ? extends Acl>)new Function<ObjectAccessControl, Acl>() {
                BlobInfo$3() {
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
        if (storageObject.containsKey((Object)"isDirectory")) {
            builder.setIsDirectory(Boolean.TRUE);
        }
        if (storageObject.getCustomerEncryption() != null) {
            builder.setCustomerEncryption(CustomerEncryption.fromPb(storageObject.getCustomerEncryption()));
        }
        if (storageObject.getStorageClass() != null) {
            builder.setStorageClass(StorageClass.valueOf(storageObject.getStorageClass()));
        }
        if (storageObject.getKmsKeyName() != null) {
            builder.setKmsKeyName(storageObject.getKmsKeyName());
        }
        if (storageObject.getEventBasedHold() != null) {
            builder.setEventBasedHold(storageObject.getEventBasedHold());
        }
        if (storageObject.getTemporaryHold() != null) {
            builder.setTemporaryHold(storageObject.getTemporaryHold());
        }
        if (storageObject.getRetentionExpirationTime() != null) {
            builder.setRetentionExpirationTime(storageObject.getRetentionExpirationTime().getValue());
        }
        return builder.build();
    }
    
    static /* synthetic */ BlobId access$000(final BlobInfo x0) {
        return x0.blobId;
    }
    
    static /* synthetic */ String access$100(final BlobInfo x0) {
        return x0.generatedId;
    }
    
    static /* synthetic */ String access$200(final BlobInfo x0) {
        return x0.cacheControl;
    }
    
    static /* synthetic */ String access$300(final BlobInfo x0) {
        return x0.contentEncoding;
    }
    
    static /* synthetic */ String access$400(final BlobInfo x0) {
        return x0.contentType;
    }
    
    static /* synthetic */ String access$500(final BlobInfo x0) {
        return x0.contentDisposition;
    }
    
    static /* synthetic */ String access$600(final BlobInfo x0) {
        return x0.contentLanguage;
    }
    
    static /* synthetic */ Integer access$700(final BlobInfo x0) {
        return x0.componentCount;
    }
    
    static /* synthetic */ CustomerEncryption access$800(final BlobInfo x0) {
        return x0.customerEncryption;
    }
    
    static /* synthetic */ List access$900(final BlobInfo x0) {
        return x0.acl;
    }
    
    static /* synthetic */ Acl.Entity access$1000(final BlobInfo x0) {
        return x0.owner;
    }
    
    static /* synthetic */ Long access$1100(final BlobInfo x0) {
        return x0.size;
    }
    
    static /* synthetic */ String access$1200(final BlobInfo x0) {
        return x0.etag;
    }
    
    static /* synthetic */ String access$1300(final BlobInfo x0) {
        return x0.selfLink;
    }
    
    static /* synthetic */ String access$1400(final BlobInfo x0) {
        return x0.md5;
    }
    
    static /* synthetic */ String access$1500(final BlobInfo x0) {
        return x0.crc32c;
    }
    
    static /* synthetic */ String access$1600(final BlobInfo x0) {
        return x0.mediaLink;
    }
    
    static /* synthetic */ Map access$1700(final BlobInfo x0) {
        return x0.metadata;
    }
    
    static /* synthetic */ Long access$1800(final BlobInfo x0) {
        return x0.metageneration;
    }
    
    static /* synthetic */ Long access$1900(final BlobInfo x0) {
        return x0.deleteTime;
    }
    
    static /* synthetic */ Long access$2000(final BlobInfo x0) {
        return x0.updateTime;
    }
    
    static /* synthetic */ Long access$2100(final BlobInfo x0) {
        return x0.createTime;
    }
    
    static /* synthetic */ boolean access$2200(final BlobInfo x0) {
        return x0.isDirectory;
    }
    
    static /* synthetic */ StorageClass access$2300(final BlobInfo x0) {
        return x0.storageClass;
    }
    
    static /* synthetic */ String access$2400(final BlobInfo x0) {
        return x0.kmsKeyName;
    }
    
    static /* synthetic */ Boolean access$2500(final BlobInfo x0) {
        return x0.eventBasedHold;
    }
    
    static /* synthetic */ Boolean access$2600(final BlobInfo x0) {
        return x0.temporaryHold;
    }
    
    static /* synthetic */ Long access$2700(final BlobInfo x0) {
        return x0.retentionExpirationTime;
    }
    
    static {
        INFO_TO_PB_FUNCTION = new Function<BlobInfo, StorageObject>() {
            BlobInfo$1() {
                super();
            }
            
            @Override
            public StorageObject apply(final BlobInfo blobInfo) {
                return blobInfo.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BlobInfo)o);
            }
        };
    }
    
    public static final class ImmutableEmptyMap<K, V> extends AbstractMap<K, V>
    {
        public ImmutableEmptyMap() {
            super();
        }
        
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return (Set<Map.Entry<K, V>>)ImmutableSet.of();
        }
    }
    
    public static class CustomerEncryption implements Serializable
    {
        private static final long serialVersionUID = -2133042982786959351L;
        private final String encryptionAlgorithm;
        private final String keySha256;
        
        CustomerEncryption(final String encryptionAlgorithm, final String keySha256) {
            super();
            this.encryptionAlgorithm = encryptionAlgorithm;
            this.keySha256 = keySha256;
        }
        
        public String getEncryptionAlgorithm() {
            return this.encryptionAlgorithm;
        }
        
        public String getKeySha256() {
            return this.keySha256;
        }
        
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("encryptionAlgorithm", this.getEncryptionAlgorithm()).add("keySha256", this.getKeySha256()).toString();
        }
        
        @Override
        public final int hashCode() {
            return Objects.hash(this.encryptionAlgorithm, this.keySha256);
        }
        
        @Override
        public final boolean equals(final Object obj) {
            return obj == this || (obj != null && obj.getClass().equals(CustomerEncryption.class) && Objects.equals(this.toPb(), ((CustomerEncryption)obj).toPb()));
        }
        
        StorageObject.CustomerEncryption toPb() {
            return new StorageObject.CustomerEncryption().setEncryptionAlgorithm(this.encryptionAlgorithm).setKeySha256(this.keySha256);
        }
        
        static CustomerEncryption fromPb(final StorageObject.CustomerEncryption customerEncryptionPb) {
            return new CustomerEncryption(customerEncryptionPb.getEncryptionAlgorithm(), customerEncryptionPb.getKeySha256());
        }
    }
    
    public abstract static class Builder
    {
        public Builder() {
            super();
        }
        
        public abstract Builder setBlobId(final BlobId p0);
        
        abstract Builder setGeneratedId(final String p0);
        
        public abstract Builder setContentType(final String p0);
        
        public abstract Builder setContentDisposition(final String p0);
        
        public abstract Builder setContentLanguage(final String p0);
        
        public abstract Builder setContentEncoding(final String p0);
        
        abstract Builder setComponentCount(final Integer p0);
        
        public abstract Builder setCacheControl(final String p0);
        
        public abstract Builder setAcl(final List<Acl> p0);
        
        abstract Builder setOwner(final Acl.Entity p0);
        
        abstract Builder setSize(final Long p0);
        
        abstract Builder setEtag(final String p0);
        
        abstract Builder setSelfLink(final String p0);
        
        public abstract Builder setMd5(final String p0);
        
        public abstract Builder setMd5FromHexString(final String p0);
        
        public abstract Builder setCrc32c(final String p0);
        
        public abstract Builder setCrc32cFromHexString(final String p0);
        
        abstract Builder setMediaLink(final String p0);
        
        public abstract Builder setStorageClass(final StorageClass p0);
        
        public abstract Builder setMetadata(final Map<String, String> p0);
        
        abstract Builder setMetageneration(final Long p0);
        
        abstract Builder setDeleteTime(final Long p0);
        
        abstract Builder setUpdateTime(final Long p0);
        
        abstract Builder setCreateTime(final Long p0);
        
        abstract Builder setIsDirectory(final boolean p0);
        
        abstract Builder setCustomerEncryption(final CustomerEncryption p0);
        
        abstract Builder setKmsKeyName(final String p0);
        
        @BetaApi
        public abstract Builder setEventBasedHold(final Boolean p0);
        
        @BetaApi
        public abstract Builder setTemporaryHold(final Boolean p0);
        
        @BetaApi
        abstract Builder setRetentionExpirationTime(final Long p0);
        
        public abstract BlobInfo build();
    }
    
    static final class BuilderImpl extends Builder
    {
        private BlobId blobId;
        private String generatedId;
        private String contentType;
        private String contentEncoding;
        private String contentDisposition;
        private String contentLanguage;
        private Integer componentCount;
        private String cacheControl;
        private List<Acl> acl;
        private Acl.Entity owner;
        private Long size;
        private String etag;
        private String selfLink;
        private String md5;
        private String crc32c;
        private String mediaLink;
        private Map<String, String> metadata;
        private Long metageneration;
        private Long deleteTime;
        private Long updateTime;
        private Long createTime;
        private Boolean isDirectory;
        private CustomerEncryption customerEncryption;
        private StorageClass storageClass;
        private String kmsKeyName;
        private Boolean eventBasedHold;
        private Boolean temporaryHold;
        private Long retentionExpirationTime;
        
        BuilderImpl(final BlobId blobId) {
            super();
            this.blobId = blobId;
        }
        
        BuilderImpl(final BlobInfo blobInfo) {
            super();
            this.blobId = blobInfo.blobId;
            this.generatedId = blobInfo.generatedId;
            this.cacheControl = blobInfo.cacheControl;
            this.contentEncoding = blobInfo.contentEncoding;
            this.contentType = blobInfo.contentType;
            this.contentDisposition = blobInfo.contentDisposition;
            this.contentLanguage = blobInfo.contentLanguage;
            this.componentCount = blobInfo.componentCount;
            this.customerEncryption = blobInfo.customerEncryption;
            this.acl = blobInfo.acl;
            this.owner = blobInfo.owner;
            this.size = blobInfo.size;
            this.etag = blobInfo.etag;
            this.selfLink = blobInfo.selfLink;
            this.md5 = blobInfo.md5;
            this.crc32c = blobInfo.crc32c;
            this.mediaLink = blobInfo.mediaLink;
            this.metadata = blobInfo.metadata;
            this.metageneration = blobInfo.metageneration;
            this.deleteTime = blobInfo.deleteTime;
            this.updateTime = blobInfo.updateTime;
            this.createTime = blobInfo.createTime;
            this.isDirectory = blobInfo.isDirectory;
            this.storageClass = blobInfo.storageClass;
            this.kmsKeyName = blobInfo.kmsKeyName;
            this.eventBasedHold = blobInfo.eventBasedHold;
            this.temporaryHold = blobInfo.temporaryHold;
            this.retentionExpirationTime = blobInfo.retentionExpirationTime;
        }
        
        @Override
        public Builder setBlobId(final BlobId blobId) {
            this.blobId = Preconditions.checkNotNull(blobId);
            return this;
        }
        
        @Override
        Builder setGeneratedId(final String generatedId) {
            this.generatedId = generatedId;
            return this;
        }
        
        @Override
        public Builder setContentType(final String contentType) {
            this.contentType = MoreObjects.firstNonNull(contentType, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setContentDisposition(final String contentDisposition) {
            this.contentDisposition = MoreObjects.firstNonNull(contentDisposition, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setContentLanguage(final String contentLanguage) {
            this.contentLanguage = MoreObjects.firstNonNull(contentLanguage, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setContentEncoding(final String contentEncoding) {
            this.contentEncoding = MoreObjects.firstNonNull(contentEncoding, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        Builder setComponentCount(final Integer componentCount) {
            this.componentCount = componentCount;
            return this;
        }
        
        @Override
        public Builder setCacheControl(final String cacheControl) {
            this.cacheControl = MoreObjects.firstNonNull(cacheControl, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setAcl(final List<Acl> acl) {
            this.acl = (List<Acl>)((acl != null) ? ImmutableList.copyOf((Collection<?>)acl) : null);
            return this;
        }
        
        @Override
        Builder setOwner(final Acl.Entity owner) {
            this.owner = owner;
            return this;
        }
        
        @Override
        Builder setSize(final Long size) {
            this.size = size;
            return this;
        }
        
        @Override
        Builder setEtag(final String etag) {
            this.etag = etag;
            return this;
        }
        
        @Override
        Builder setSelfLink(final String selfLink) {
            this.selfLink = selfLink;
            return this;
        }
        
        @Override
        public Builder setMd5(final String md5) {
            this.md5 = MoreObjects.firstNonNull(md5, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setMd5FromHexString(final String md5HexString) {
            if (md5HexString == null) {
                return this;
            }
            byte[] bytes = new BigInteger(md5HexString, 16).toByteArray();
            final int leadingEmptyBytes = bytes.length - md5HexString.length() / 2;
            if (leadingEmptyBytes > 0) {
                bytes = Arrays.copyOfRange(bytes, leadingEmptyBytes, bytes.length);
            }
            this.md5 = BaseEncoding.base64().encode(bytes);
            return this;
        }
        
        @Override
        public Builder setCrc32c(final String crc32c) {
            this.crc32c = MoreObjects.firstNonNull(crc32c, Data.nullOf(String.class));
            return this;
        }
        
        @Override
        public Builder setCrc32cFromHexString(final String crc32cHexString) {
            if (crc32cHexString == null) {
                return this;
            }
            byte[] bytes = new BigInteger(crc32cHexString, 16).toByteArray();
            final int leadingEmptyBytes = bytes.length - crc32cHexString.length() / 2;
            if (leadingEmptyBytes > 0) {
                bytes = Arrays.copyOfRange(bytes, leadingEmptyBytes, bytes.length);
            }
            this.crc32c = BaseEncoding.base64().encode(bytes);
            return this;
        }
        
        @Override
        Builder setMediaLink(final String mediaLink) {
            this.mediaLink = mediaLink;
            return this;
        }
        
        @Override
        public Builder setMetadata(final Map<String, String> metadata) {
            if (metadata != null) {
                this.metadata = new HashMap<String, String>(metadata);
            }
            else {
                this.metadata = Data.nullOf(ImmutableEmptyMap.class);
            }
            return this;
        }
        
        @Override
        public Builder setStorageClass(final StorageClass storageClass) {
            this.storageClass = storageClass;
            return this;
        }
        
        @Override
        Builder setMetageneration(final Long metageneration) {
            this.metageneration = metageneration;
            return this;
        }
        
        @Override
        Builder setDeleteTime(final Long deleteTime) {
            this.deleteTime = deleteTime;
            return this;
        }
        
        @Override
        Builder setUpdateTime(final Long updateTime) {
            this.updateTime = updateTime;
            return this;
        }
        
        @Override
        Builder setCreateTime(final Long createTime) {
            this.createTime = createTime;
            return this;
        }
        
        @Override
        Builder setIsDirectory(final boolean isDirectory) {
            this.isDirectory = isDirectory;
            return this;
        }
        
        @Override
        Builder setCustomerEncryption(final CustomerEncryption customerEncryption) {
            this.customerEncryption = customerEncryption;
            return this;
        }
        
        @Override
        Builder setKmsKeyName(final String kmsKeyName) {
            this.kmsKeyName = kmsKeyName;
            return this;
        }
        
        @Override
        public Builder setEventBasedHold(final Boolean eventBasedHold) {
            this.eventBasedHold = eventBasedHold;
            return this;
        }
        
        @Override
        public Builder setTemporaryHold(final Boolean temporaryHold) {
            this.temporaryHold = temporaryHold;
            return this;
        }
        
        @Override
        Builder setRetentionExpirationTime(final Long retentionExpirationTime) {
            this.retentionExpirationTime = retentionExpirationTime;
            return this;
        }
        
        @Override
        public BlobInfo build() {
            Preconditions.checkNotNull(this.blobId);
            return new BlobInfo(this);
        }
        
        static /* synthetic */ BlobId access$2800(final BuilderImpl x0) {
            return x0.blobId;
        }
        
        static /* synthetic */ String access$2900(final BuilderImpl x0) {
            return x0.generatedId;
        }
        
        static /* synthetic */ String access$3000(final BuilderImpl x0) {
            return x0.cacheControl;
        }
        
        static /* synthetic */ String access$3100(final BuilderImpl x0) {
            return x0.contentEncoding;
        }
        
        static /* synthetic */ String access$3200(final BuilderImpl x0) {
            return x0.contentType;
        }
        
        static /* synthetic */ String access$3300(final BuilderImpl x0) {
            return x0.contentDisposition;
        }
        
        static /* synthetic */ String access$3400(final BuilderImpl x0) {
            return x0.contentLanguage;
        }
        
        static /* synthetic */ Integer access$3500(final BuilderImpl x0) {
            return x0.componentCount;
        }
        
        static /* synthetic */ CustomerEncryption access$3600(final BuilderImpl x0) {
            return x0.customerEncryption;
        }
        
        static /* synthetic */ List access$3700(final BuilderImpl x0) {
            return x0.acl;
        }
        
        static /* synthetic */ Acl.Entity access$3800(final BuilderImpl x0) {
            return x0.owner;
        }
        
        static /* synthetic */ Long access$3900(final BuilderImpl x0) {
            return x0.size;
        }
        
        static /* synthetic */ String access$4000(final BuilderImpl x0) {
            return x0.etag;
        }
        
        static /* synthetic */ String access$4100(final BuilderImpl x0) {
            return x0.selfLink;
        }
        
        static /* synthetic */ String access$4200(final BuilderImpl x0) {
            return x0.md5;
        }
        
        static /* synthetic */ String access$4300(final BuilderImpl x0) {
            return x0.crc32c;
        }
        
        static /* synthetic */ String access$4400(final BuilderImpl x0) {
            return x0.mediaLink;
        }
        
        static /* synthetic */ Map access$4500(final BuilderImpl x0) {
            return x0.metadata;
        }
        
        static /* synthetic */ Long access$4600(final BuilderImpl x0) {
            return x0.metageneration;
        }
        
        static /* synthetic */ Long access$4700(final BuilderImpl x0) {
            return x0.deleteTime;
        }
        
        static /* synthetic */ Long access$4800(final BuilderImpl x0) {
            return x0.updateTime;
        }
        
        static /* synthetic */ Long access$4900(final BuilderImpl x0) {
            return x0.createTime;
        }
        
        static /* synthetic */ Boolean access$5000(final BuilderImpl x0) {
            return x0.isDirectory;
        }
        
        static /* synthetic */ StorageClass access$5100(final BuilderImpl x0) {
            return x0.storageClass;
        }
        
        static /* synthetic */ String access$5200(final BuilderImpl x0) {
            return x0.kmsKeyName;
        }
        
        static /* synthetic */ Boolean access$5300(final BuilderImpl x0) {
            return x0.eventBasedHold;
        }
        
        static /* synthetic */ Boolean access$5400(final BuilderImpl x0) {
            return x0.temporaryHold;
        }
        
        static /* synthetic */ Long access$5500(final BuilderImpl x0) {
            return x0.retentionExpirationTime;
        }
    }
}
