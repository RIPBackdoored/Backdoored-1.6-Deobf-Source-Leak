package com.google.cloud.storage;

public static class Builder
{
    private String secretKey;
    private HmacKeyMetadata metadata;
    
    private Builder(final String secretKey) {
        super();
        this.secretKey = secretKey;
    }
    
    public Builder setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
        return this;
    }
    
    public Builder setMetadata(final HmacKeyMetadata metadata) {
        this.metadata = metadata;
        return this;
    }
    
    public HmacKey build() {
        return new HmacKey(this, null);
    }
    
    static /* synthetic */ String access$000(final Builder x0) {
        return x0.secretKey;
    }
    
    static /* synthetic */ HmacKeyMetadata access$100(final Builder x0) {
        return x0.metadata;
    }
    
    Builder(final String x0, final HmacKey$1 x1) {
        this(x0);
    }
}
