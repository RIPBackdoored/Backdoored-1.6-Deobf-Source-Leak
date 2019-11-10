package com.google.cloud.storage;

import java.io.*;
import com.google.common.base.*;
import java.util.*;

public final class ServiceAccount implements Serializable
{
    static final Function<com.google.api.services.storage.model.ServiceAccount, ServiceAccount> FROM_PB_FUNCTION;
    static final Function<ServiceAccount, com.google.api.services.storage.model.ServiceAccount> TO_PB_FUNCTION;
    private static final long serialVersionUID = 4199610694227857331L;
    private final String email;
    
    private ServiceAccount(final String email) {
        super();
        this.email = email;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("email", this.email).toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.email);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj == this || (obj instanceof ServiceAccount && Objects.equals(this.toPb(), ((ServiceAccount)obj).toPb()));
    }
    
    com.google.api.services.storage.model.ServiceAccount toPb() {
        final com.google.api.services.storage.model.ServiceAccount serviceAccountPb = new com.google.api.services.storage.model.ServiceAccount();
        serviceAccountPb.setEmailAddress(this.email);
        return serviceAccountPb;
    }
    
    public static ServiceAccount of(final String email) {
        return new ServiceAccount(email);
    }
    
    static ServiceAccount fromPb(final com.google.api.services.storage.model.ServiceAccount accountPb) {
        return new ServiceAccount(accountPb.getEmailAddress());
    }
    
    static {
        FROM_PB_FUNCTION = new Function<com.google.api.services.storage.model.ServiceAccount, ServiceAccount>() {
            ServiceAccount$1() {
                super();
            }
            
            @Override
            public ServiceAccount apply(final com.google.api.services.storage.model.ServiceAccount pb) {
                return ServiceAccount.fromPb(pb);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((com.google.api.services.storage.model.ServiceAccount)o);
            }
        };
        TO_PB_FUNCTION = new Function<ServiceAccount, com.google.api.services.storage.model.ServiceAccount>() {
            ServiceAccount$2() {
                super();
            }
            
            @Override
            public com.google.api.services.storage.model.ServiceAccount apply(final ServiceAccount metadata) {
                return metadata.toPb();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((ServiceAccount)o);
            }
        };
    }
}
