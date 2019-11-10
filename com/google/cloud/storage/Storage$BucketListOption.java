package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;

public static class BucketListOption extends Option
{
    private static final long serialVersionUID = 8754017079673290353L;
    
    private BucketListOption(final StorageRpc.Option option, final Object value) {
        super(option, value);
    }
    
    public static BucketListOption pageSize(final long pageSize) {
        return new BucketListOption(StorageRpc.Option.MAX_RESULTS, pageSize);
    }
    
    public static BucketListOption pageToken(final String pageToken) {
        return new BucketListOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
    }
    
    public static BucketListOption prefix(final String prefix) {
        return new BucketListOption(StorageRpc.Option.PREFIX, prefix);
    }
    
    public static BucketListOption userProject(final String userProject) {
        return new BucketListOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static BucketListOption fields(final BucketField... fields) {
        return new BucketListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector("items", (List)BucketField.REQUIRED_FIELDS, (FieldSelector[])fields));
    }
}
