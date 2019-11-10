package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class ListHmacKeysOption extends Option
{
    private ListHmacKeysOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static ListHmacKeysOption serviceAccount(final ServiceAccount serviceAccount) {
        return new ListHmacKeysOption(StorageRpc.Option.SERVICE_ACCOUNT_EMAIL, serviceAccount.getEmail());
    }
    
    public static ListHmacKeysOption maxResults(final long pageSize) {
        return new ListHmacKeysOption(StorageRpc.Option.MAX_RESULTS, pageSize);
    }
    
    public static ListHmacKeysOption pageToken(final String pageToken) {
        return new ListHmacKeysOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
    }
    
    public static ListHmacKeysOption showDeletedKeys(final boolean showDeletedKeys) {
        return new ListHmacKeysOption(StorageRpc.Option.SHOW_DELETED_KEYS, showDeletedKeys);
    }
    
    public static ListHmacKeysOption userProject(final String userProject) {
        return new ListHmacKeysOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static ListHmacKeysOption projectId(final String projectId) {
        return new ListHmacKeysOption(StorageRpc.Option.PROJECT_ID, projectId);
    }
}
