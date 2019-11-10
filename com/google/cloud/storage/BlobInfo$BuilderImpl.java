package com.google.cloud.storage;

import com.google.api.client.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.math.*;
import com.google.common.io.*;
import java.util.*;

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
        this.blobId = BlobInfo.access$000(blobInfo);
        this.generatedId = BlobInfo.access$100(blobInfo);
        this.cacheControl = BlobInfo.access$200(blobInfo);
        this.contentEncoding = BlobInfo.access$300(blobInfo);
        this.contentType = BlobInfo.access$400(blobInfo);
        this.contentDisposition = BlobInfo.access$500(blobInfo);
        this.contentLanguage = BlobInfo.access$600(blobInfo);
        this.componentCount = BlobInfo.access$700(blobInfo);
        this.customerEncryption = BlobInfo.access$800(blobInfo);
        this.acl = (List<Acl>)BlobInfo.access$900(blobInfo);
        this.owner = BlobInfo.access$1000(blobInfo);
        this.size = BlobInfo.access$1100(blobInfo);
        this.etag = BlobInfo.access$1200(blobInfo);
        this.selfLink = BlobInfo.access$1300(blobInfo);
        this.md5 = BlobInfo.access$1400(blobInfo);
        this.crc32c = BlobInfo.access$1500(blobInfo);
        this.mediaLink = BlobInfo.access$1600(blobInfo);
        this.metadata = (Map<String, String>)BlobInfo.access$1700(blobInfo);
        this.metageneration = BlobInfo.access$1800(blobInfo);
        this.deleteTime = BlobInfo.access$1900(blobInfo);
        this.updateTime = BlobInfo.access$2000(blobInfo);
        this.createTime = BlobInfo.access$2100(blobInfo);
        this.isDirectory = BlobInfo.access$2200(blobInfo);
        this.storageClass = BlobInfo.access$2300(blobInfo);
        this.kmsKeyName = BlobInfo.access$2400(blobInfo);
        this.eventBasedHold = BlobInfo.access$2500(blobInfo);
        this.temporaryHold = BlobInfo.access$2600(blobInfo);
        this.retentionExpirationTime = BlobInfo.access$2700(blobInfo);
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
