package com.google.cloud.storage;

import java.util.*;
import com.google.cloud.storage.spi.*;
import com.google.cloud.http.*;
import com.google.auth.*;
import com.google.common.collect.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.spi.*;
import com.google.cloud.*;

public class StorageOptions extends ServiceOptions<Storage, StorageOptions>
{
    private static final long serialVersionUID = -2907268477247502947L;
    private static final String API_SHORT_NAME = "Storage";
    private static final String GCS_SCOPE = "https://www.googleapis.com/auth/devstorage.full_control";
    private static final Set<String> SCOPES;
    
    private StorageOptions(final Builder builder) {
        super((Class)StorageFactory.class, (Class)StorageRpcFactory.class, (ServiceOptions.Builder)builder, (ServiceDefaults)new StorageDefaults());
    }
    
    public static HttpTransportOptions getDefaultHttpTransportOptions() {
        return HttpTransportOptions.newBuilder().build();
    }
    
    protected boolean projectIdRequired() {
        return false;
    }
    
    protected Set<String> getScopes() {
        return StorageOptions.SCOPES;
    }
    
    protected StorageRpc getStorageRpcV1() {
        return (StorageRpc)this.getRpc();
    }
    
    public static StorageOptions getDefaultInstance() {
        return newBuilder().build();
    }
    
    public static StorageOptions getUnauthenticatedInstance() {
        return ((Builder)newBuilder().setCredentials((Credentials)NoCredentials.getInstance())).build();
    }
    
    public Builder toBuilder() {
        return new Builder(this);
    }
    
    public int hashCode() {
        return this.baseHashCode();
    }
    
    public boolean equals(final Object obj) {
        return obj instanceof StorageOptions && this.baseEquals((ServiceOptions)obj);
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public /* bridge */ ServiceOptions.Builder toBuilder() {
        return this.toBuilder();
    }
    
    StorageOptions(final Builder x0, final StorageOptions$1 x1) {
        this(x0);
    }
    
    static {
        SCOPES = ImmutableSet.of("https://www.googleapis.com/auth/devstorage.full_control");
    }
    
    public static class DefaultStorageFactory implements StorageFactory
    {
        private static final StorageFactory INSTANCE;
        
        public DefaultStorageFactory() {
            super();
        }
        
        public Storage create(final StorageOptions options) {
            return new StorageImpl(options);
        }
        
        public /* bridge */ Service create(final ServiceOptions serviceOptions) {
            return (Service)this.create((StorageOptions)serviceOptions);
        }
        
        static /* synthetic */ StorageFactory access$200() {
            return DefaultStorageFactory.INSTANCE;
        }
        
        static {
            INSTANCE = new DefaultStorageFactory();
        }
    }
    
    public static class DefaultStorageRpcFactory implements StorageRpcFactory
    {
        private static final StorageRpcFactory INSTANCE;
        
        public DefaultStorageRpcFactory() {
            super();
        }
        
        public ServiceRpc create(final StorageOptions options) {
            return (ServiceRpc)new HttpStorageRpc(options);
        }
        
        public /* bridge */ ServiceRpc create(final ServiceOptions serviceOptions) {
            return this.create((StorageOptions)serviceOptions);
        }
        
        static /* synthetic */ StorageRpcFactory access$300() {
            return DefaultStorageRpcFactory.INSTANCE;
        }
        
        static {
            INSTANCE = new DefaultStorageRpcFactory();
        }
    }
    
    public static class Builder extends ServiceOptions.Builder<Storage, StorageOptions, Builder>
    {
        private Builder() {
            super();
        }
        
        private Builder(final StorageOptions options) {
            super((ServiceOptions)options);
        }
        
        public Builder setTransportOptions(final TransportOptions transportOptions) {
            if (!(transportOptions instanceof HttpTransportOptions)) {
                throw new IllegalArgumentException("Only http transport is allowed for Storage.");
            }
            return (Builder)super.setTransportOptions(transportOptions);
        }
        
        public StorageOptions build() {
            return new StorageOptions(this, null);
        }
        
        public /* bridge */ ServiceOptions.Builder setTransportOptions(final TransportOptions transportOptions) {
            return this.setTransportOptions(transportOptions);
        }
        
        public /* bridge */ ServiceOptions build() {
            return this.build();
        }
        
        Builder(final StorageOptions x0, final StorageOptions$1 x1) {
            this(x0);
        }
        
        Builder(final StorageOptions$1 x0) {
            this();
        }
    }
    
    private static class StorageDefaults implements ServiceDefaults<Storage, StorageOptions>
    {
        private StorageDefaults() {
            super();
        }
        
        public StorageFactory getDefaultServiceFactory() {
            return DefaultStorageFactory.INSTANCE;
        }
        
        public StorageRpcFactory getDefaultRpcFactory() {
            return DefaultStorageRpcFactory.INSTANCE;
        }
        
        public TransportOptions getDefaultTransportOptions() {
            return (TransportOptions)StorageOptions.getDefaultHttpTransportOptions();
        }
        
        public /* bridge */ ServiceRpcFactory getDefaultRpcFactory() {
            return (ServiceRpcFactory)this.getDefaultRpcFactory();
        }
        
        public /* bridge */ ServiceFactory getDefaultServiceFactory() {
            return (ServiceFactory)this.getDefaultServiceFactory();
        }
        
        StorageDefaults(final StorageOptions$1 x0) {
            this();
        }
    }
}
