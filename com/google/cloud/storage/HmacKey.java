package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import com.google.api.services.storage.model.*;
import com.google.api.client.util.*;

public class HmacKey implements Serializable
{
    private static final long serialVersionUID = -1809610424373783062L;
    private final String secretKey;
    private final HmacKeyMetadata metadata;
    
    private HmacKey(final Builder builder) {
        super();
        this.secretKey = builder.secretKey;
        this.metadata = builder.metadata;
    }
    
    public static Builder newBuilder(final String secretKey) {
        return new Builder(secretKey);
    }
    
    public String getSecretKey() {
        return this.secretKey;
    }
    
    public HmacKeyMetadata getMetadata() {
        return this.metadata;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.secretKey, this.metadata);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final HmacKeyMetadata other = (HmacKeyMetadata)obj;
        return Objects.equals(this.secretKey, this.secretKey) && Objects.equals(this.metadata, this.metadata);
    }
    
    com.google.api.services.storage.model.HmacKey toPb() {
        final com.google.api.services.storage.model.HmacKey hmacKey = new com.google.api.services.storage.model.HmacKey();
        hmacKey.setSecret(this.secretKey);
        if (this.metadata != null) {
            hmacKey.setMetadata(this.metadata.toPb());
        }
        return hmacKey;
    }
    
    static HmacKey fromPb(final com.google.api.services.storage.model.HmacKey hmacKey) {
        return newBuilder(hmacKey.getSecret()).setMetadata(HmacKeyMetadata.fromPb(hmacKey.getMetadata())).build();
    }
    
    HmacKey(final Builder x0, final HmacKey$1 x1) {
        this(x0);
    }
    
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
    
    public enum HmacKeyState
    {
        ACTIVE("ACTIVE"), 
        INACTIVE("INACTIVE"), 
        DELETED("DELETED");
        
        private final String state;
        private static final /* synthetic */ HmacKeyState[] $VALUES;
        
        public static HmacKeyState[] values() {
            return HmacKeyState.$VALUES.clone();
        }
        
        public static HmacKeyState valueOf(final String name) {
            return Enum.valueOf(HmacKeyState.class, name);
        }
        
        private HmacKeyState(final String state) {
            this.state = state;
        }
        
        static {
            $VALUES = new HmacKeyState[] { HmacKeyState.ACTIVE, HmacKeyState.INACTIVE, HmacKeyState.DELETED };
        }
    }
    
    public static class HmacKeyMetadata implements Serializable
    {
        private static final long serialVersionUID = 4571684785352640737L;
        private final String accessId;
        private final String etag;
        private final String id;
        private final String projectId;
        private final ServiceAccount serviceAccount;
        private final HmacKeyState state;
        private final Long createTime;
        private final Long updateTime;
        
        private HmacKeyMetadata(final Builder builder) {
            super();
            this.accessId = builder.accessId;
            this.etag = builder.etag;
            this.id = builder.id;
            this.projectId = builder.projectId;
            this.serviceAccount = builder.serviceAccount;
            this.state = builder.state;
            this.createTime = builder.createTime;
            this.updateTime = builder.updateTime;
        }
        
        public static Builder newBuilder(final ServiceAccount serviceAccount) {
            return new Builder(serviceAccount);
        }
        
        public Builder toBuilder() {
            return new Builder(this);
        }
        
        public static HmacKeyMetadata of(final ServiceAccount serviceAccount, final String accessId, final String projectId) {
            return newBuilder(serviceAccount).setAccessId(accessId).setProjectId(projectId).build();
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.accessId, this.projectId);
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            final HmacKeyMetadata other = (HmacKeyMetadata)obj;
            return Objects.equals(this.accessId, other.accessId) && Objects.equals(this.etag, other.etag) && Objects.equals(this.id, other.id) && Objects.equals(this.projectId, other.projectId) && Objects.equals(this.serviceAccount, other.serviceAccount) && Objects.equals(this.state, other.state) && Objects.equals(this.createTime, other.createTime) && Objects.equals(this.updateTime, other.updateTime);
        }
        
        public com.google.api.services.storage.model.HmacKeyMetadata toPb() {
            final com.google.api.services.storage.model.HmacKeyMetadata metadata = new com.google.api.services.storage.model.HmacKeyMetadata();
            metadata.setAccessId(this.accessId);
            metadata.setEtag(this.etag);
            metadata.setId(this.id);
            metadata.setProjectId(this.projectId);
            metadata.setServiceAccountEmail((this.serviceAccount == null) ? null : this.serviceAccount.getEmail());
            metadata.setState((this.state == null) ? null : this.state.toString());
            metadata.setTimeCreated((this.createTime == null) ? null : new DateTime(this.createTime));
            metadata.setUpdated((this.updateTime == null) ? null : new DateTime(this.updateTime));
            return metadata;
        }
        
        static HmacKeyMetadata fromPb(final com.google.api.services.storage.model.HmacKeyMetadata metadata) {
            return newBuilder(ServiceAccount.of(metadata.getServiceAccountEmail())).setAccessId(metadata.getAccessId()).setCreateTime(metadata.getTimeCreated().getValue()).setEtag(metadata.getEtag()).setId(metadata.getId()).setProjectId(metadata.getProjectId()).setState(HmacKeyState.valueOf(metadata.getState())).setUpdateTime(metadata.getUpdated().getValue()).build();
        }
        
        public String getAccessId() {
            return this.accessId;
        }
        
        public String getEtag() {
            return this.etag;
        }
        
        public String getId() {
            return this.id;
        }
        
        public String getProjectId() {
            return this.projectId;
        }
        
        public ServiceAccount getServiceAccount() {
            return this.serviceAccount;
        }
        
        public HmacKeyState getState() {
            return this.state;
        }
        
        public Long getCreateTime() {
            return this.createTime;
        }
        
        public Long getUpdateTime() {
            return this.updateTime;
        }
        
        static /* synthetic */ String access$1400(final HmacKeyMetadata x0) {
            return x0.accessId;
        }
        
        static /* synthetic */ String access$1500(final HmacKeyMetadata x0) {
            return x0.etag;
        }
        
        static /* synthetic */ String access$1600(final HmacKeyMetadata x0) {
            return x0.id;
        }
        
        static /* synthetic */ String access$1700(final HmacKeyMetadata x0) {
            return x0.projectId;
        }
        
        static /* synthetic */ ServiceAccount access$1800(final HmacKeyMetadata x0) {
            return x0.serviceAccount;
        }
        
        static /* synthetic */ HmacKeyState access$1900(final HmacKeyMetadata x0) {
            return x0.state;
        }
        
        static /* synthetic */ Long access$2000(final HmacKeyMetadata x0) {
            return x0.createTime;
        }
        
        static /* synthetic */ Long access$2100(final HmacKeyMetadata x0) {
            return x0.updateTime;
        }
        
        HmacKeyMetadata(final Builder x0, final HmacKey$1 x1) {
            this(x0);
        }
        
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
                this.accessId = metadata.accessId;
                this.etag = metadata.etag;
                this.id = metadata.id;
                this.projectId = metadata.projectId;
                this.serviceAccount = metadata.serviceAccount;
                this.state = metadata.state;
                this.createTime = metadata.createTime;
                this.updateTime = metadata.updateTime;
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
    }
}
