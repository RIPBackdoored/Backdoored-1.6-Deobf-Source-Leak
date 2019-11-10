package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class BucketSourceOption extends Option
{
    private static final long serialVersionUID = 5185657617120212117L;
    
    private BucketSourceOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static BucketSourceOption metagenerationMatch(final long metageneration) {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }
    
    public static BucketSourceOption metagenerationNotMatch(final long metageneration) {
        return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
    
    public static BucketSourceOption userProject(final String userProject) {
        return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
}
