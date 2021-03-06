package com.google.cloud.storage;

public static class Builder
{
    private String accessId;
    private String etag;
    private String id;
    private String projectId;
    private ServiceAccount serviceAccount;
    private HmacKeyState state;
    private Long createTime;
    private Long updateTime;
    
    private Builder(final ServiceAccount serviceAccount) {
        super();
        this.serviceAccount = serviceAccount;
    }
    
    private Builder(final HmacKeyMetadata metadata) {
        super();
        this.accessId = HmacKeyMetadata.access$1400(metadata);
        this.etag = HmacKeyMetadata.access$1500(metadata);
        this.id = HmacKeyMetadata.access$1600(metadata);
        this.projectId = HmacKeyMetadata.access$1700(metadata);
        this.serviceAccount = HmacKeyMetadata.access$1800(metadata);
        this.state = HmacKeyMetadata.access$1900(metadata);
        this.createTime = HmacKeyMetadata.access$2000(metadata);
        this.updateTime = HmacKeyMetadata.access$2100(metadata);
    }
    
    public Builder setAccessId(final String accessId) {
        this.accessId = accessId;
        return this;
    }
    
    public Builder setEtag(final String etag) {
        this.etag = etag;
        return this;
    }
    
    public Builder setId(final String id) {
        this.id = id;
        return this;
    }
    
    public Builder setServiceAccount(final ServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
        return this;
    }
    
    public Builder setState(final HmacKeyState state) {
        this.state = state;
        return this;
    }
    
    public Builder setCreateTime(final long createTime) {
        this.createTime = createTime;
        return this;
    }
    
    public Builder setProjectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }
    
    public HmacKeyMetadata build() {
        return new HmacKeyMetadata(this);
    }
    
    public Builder setUpdateTime(final long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    static /* synthetic */ String access$400(final Builder x0) {
        return x0.accessId;
    }
    
    static /* synthetic */ String access$500(final Builder x0) {
        return x0.etag;
    }
    
    static /* synthetic */ String access$600(final Builder x0) {
        return x0.id;
    }
    
    static /* synthetic */ String access$700(final Builder x0) {
        return x0.projectId;
    }
    
    static /* synthetic */ ServiceAccount access$800(final Builder x0) {
        return x0.serviceAccount;
    }
    
    static /* synthetic */ HmacKeyState access$900(final Builder x0) {
        return x0.state;
    }
    
    static /* synthetic */ Long access$1000(final Builder x0) {
        return x0.createTime;
    }
    
    static /* synthetic */ Long access$1100(final Builder x0) {
        return x0.updateTime;
    }
    
    Builder(final ServiceAccount x0, final HmacKey$1 x1) {
        this(x0);
    }
    
    Builder(final HmacKeyMetadata x0, final HmacKey$1 x1) {
        this(x0);
    }
}
