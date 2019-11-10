package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;

public static class BlobSourceOption extends Option
{
    private static final long serialVersionUID = -3712768261070182991L;
    
    private BlobSourceOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    public static BlobSourceOption generationMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, null);
    }
    
    public static BlobSourceOption generationMatch(final long generation) {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, generation);
    }
    
    public static BlobSourceOption generationNotMatch() {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, null);
    }
    
    public static BlobSourceOption generationNotMatch(final long generation) {
        return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation);
    }
    
    public static BlobSourceOption metagenerationMatch(final long metageneration) {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }
    
    public static BlobSourceOption metagenerationNotMatch(final long metageneration) {
        return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
    
    public static BlobSourceOption decryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobSourceOption decryptionKey(final String key) {
        return new BlobSourceOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, key);
    }
    
    public static BlobSourceOption userProject(final String userProject) {
        return new BlobSourceOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
}
