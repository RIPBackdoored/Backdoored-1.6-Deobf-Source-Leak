package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;

public static class DeleteHmacKeyOption extends Option
{
    private DeleteHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static DeleteHmacKeyOption userProject(final String userProject) {
        return new DeleteHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
}
