package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class UpdateHmacKeyOption extends Option
{
    private UpdateHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static UpdateHmacKeyOption userProject(final String userProject) {
        return new UpdateHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
}
