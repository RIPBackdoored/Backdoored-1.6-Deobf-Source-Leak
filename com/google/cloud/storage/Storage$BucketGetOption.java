package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;

public static class BucketGetOption extends Option
{
    private static final long serialVersionUID = 1901844869484087395L;
    
    private BucketGetOption(final StorageRpc.Option rpcOption, final long metageneration) {
        super(rpcOption, metageneration);
    }
    
    private BucketGetOption(final StorageRpc.Option rpcOption, final String value) {
        super(rpcOption, value);
    }
    
    public static BucketGetOption metagenerationMatch(final long metageneration) {
        return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }
    
    public static BucketGetOption metagenerationNotMatch(final long metageneration) {
        return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
    
    public static BucketGetOption userProject(final String userProject) {
        return new BucketGetOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static BucketGetOption fields(final BucketField... fields) {
        return new BucketGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BucketField.REQUIRED_FIELDS, (FieldSelector[])fields));
    }
}
