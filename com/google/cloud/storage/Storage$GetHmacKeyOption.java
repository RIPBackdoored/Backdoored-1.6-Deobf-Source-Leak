package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class GetHmacKeyOption extends Option
{
    private GetHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static GetHmacKeyOption userProject(final String userProject) {
        return new GetHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static GetHmacKeyOption projectId(final String projectId) {
        return new GetHmacKeyOption(StorageRpc.Option.PROJECT_ID, projectId);
    }
}
