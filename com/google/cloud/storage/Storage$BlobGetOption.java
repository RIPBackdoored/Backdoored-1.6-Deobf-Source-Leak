package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.util.*;
import java.security.*;
import com.google.common.io.*;

public static class BlobGetOption extends Option
{
    private static final long serialVersionUID = 803817709703661480L;
    
    private BlobGetOption(final StorageRpc.Option rpcOption, final Long value) {
        super(rpcOption, value);
    }
    
    private BlobGetOption(final StorageRpc.Option rpcOption, final String value) {
        super(rpcOption, value);
    }
    
    public static BlobGetOption generationMatch() {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, (Long)null);
    }
    
    public static BlobGetOption generationMatch(final long generation) {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_MATCH, generation);
    }
    
    public static BlobGetOption generationNotMatch() {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, (Long)null);
    }
    
    public static BlobGetOption generationNotMatch(final long generation) {
        return new BlobGetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation);
    }
    
    public static BlobGetOption metagenerationMatch(final long metageneration) {
        return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }
    
    public static BlobGetOption metagenerationNotMatch(final long metageneration) {
        return new BlobGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
    
    public static BlobGetOption fields(final BlobField... fields) {
        return new BlobGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BlobField.REQUIRED_FIELDS, (FieldSelector[])fields));
    }
    
    public static BlobGetOption userProject(final String userProject) {
        return new BlobGetOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static BlobGetOption decryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobGetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobGetOption decryptionKey(final String key) {
        return new BlobGetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, key);
    }
}
