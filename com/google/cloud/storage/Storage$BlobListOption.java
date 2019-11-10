package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;

public static class BlobListOption extends Option
{
    private static final String[] TOP_LEVEL_FIELDS;
    private static final long serialVersionUID = 9083383524788661294L;
    
    private BlobListOption(final StorageRpc.Option option, final Object value) {
        super(option, value);
    }
    
    public static BlobListOption pageSize(final long pageSize) {
        return new BlobListOption(StorageRpc.Option.MAX_RESULTS, pageSize);
    }
    
    public static BlobListOption pageToken(final String pageToken) {
        return new BlobListOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
    }
    
    public static BlobListOption prefix(final String prefix) {
        return new BlobListOption(StorageRpc.Option.PREFIX, prefix);
    }
    
    public static BlobListOption currentDirectory() {
        return new BlobListOption(StorageRpc.Option.DELIMITER, true);
    }
    
    public static BlobListOption userProject(final String userProject) {
        return new BlobListOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static BlobListOption versions(final boolean versions) {
        return new BlobListOption(StorageRpc.Option.VERSIONS, versions);
    }
    
    public static BlobListOption fields(final BlobField... fields) {
        return new BlobListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector(BlobListOption.TOP_LEVEL_FIELDS, "items", (List)BlobField.REQUIRED_FIELDS, (FieldSelector[])fields, new String[0]));
    }
    
    static {
        TOP_LEVEL_FIELDS = new String[] { "prefixes" };
    }
}
