package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class CreateHmacKeyOption extends Option
{
    private CreateHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static CreateHmacKeyOption userProject(final String userProject) {
        return new CreateHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static CreateHmacKeyOption projectId(final String projectId) {
        return new CreateHmacKeyOption(StorageRpc.Option.PROJECT_ID, projectId);
    }
}
