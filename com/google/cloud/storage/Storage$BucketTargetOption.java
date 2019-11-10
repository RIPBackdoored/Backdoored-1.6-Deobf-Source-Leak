package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class BucketTargetOption extends Option
{
    private static final long serialVersionUID = -5880204616982900975L;
    
    private BucketTargetOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    private BucketTargetOption(final StorageRpc.Option rpcOption) {
        this(rpcOption, null);
    }
    
    public static BucketTargetOption predefinedAcl(final PredefinedAcl acl) {
        return new BucketTargetOption(StorageRpc.Option.PREDEFINED_ACL, acl.getEntry());
    }
    
    public static BucketTargetOption predefinedDefaultObjectAcl(final PredefinedAcl acl) {
        return new BucketTargetOption(StorageRpc.Option.PREDEFINED_DEFAULT_OBJECT_ACL, acl.getEntry());
    }
    
    public static BucketTargetOption metagenerationMatch() {
        return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BucketTargetOption metagenerationNotMatch() {
        return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BucketTargetOption userProject(final String userProject) {
        return new BucketTargetOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static BucketTargetOption projection(final String projection) {
        return new BucketTargetOption(StorageRpc.Option.PROJECTION, projection);
    }
}
