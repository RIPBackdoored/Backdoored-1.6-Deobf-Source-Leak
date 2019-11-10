package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;
import com.google.cloud.*;
import com.google.common.collect.*;
import java.util.*;

public static class BlobTargetOption extends Option
{
    private static final long serialVersionUID = 214616862061934846L;
    
    private BlobTargetOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    private BlobTargetOption(final StorageRpc.Option rpcOption) {
        this(rpcOption, null);
    }
    
    public static BlobTargetOption predefinedAcl(final PredefinedAcl acl) {
        return new BlobTargetOption(StorageRpc.Option.PREDEFINED_ACL, acl.getEntry());
    }
    
    public static BlobTargetOption doesNotExist() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobTargetOption generationMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH);
    }
    
    public static BlobTargetOption generationNotMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
    }
    
    public static BlobTargetOption metagenerationMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
    }
    
    public static BlobTargetOption metagenerationNotMatch() {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BlobTargetOption disableGzipContent() {
        return new BlobTargetOption(StorageRpc.Option.IF_DISABLE_GZIP_CONTENT, true);
    }
    
    public static BlobTargetOption encryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobTargetOption userProject(final String userProject) {
        return new BlobTargetOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    public static BlobTargetOption encryptionKey(final String key) {
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, key);
    }
    
    public static BlobTargetOption kmsKeyName(final String kmsKeyName) {
        return new BlobTargetOption(StorageRpc.Option.KMS_KEY_NAME, kmsKeyName);
    }
    
    static Tuple<BlobInfo, BlobTargetOption[]> convert(final BlobInfo info, final BlobWriteOption... options) {
        final BlobInfo.Builder infoBuilder = info.toBuilder().setCrc32c(null).setMd5(null);
        final List<BlobTargetOption> targetOptions = (List<BlobTargetOption>)Lists.newArrayListWithCapacity(options.length);
        for (final BlobWriteOption option : options) {
            switch (option.option) {
                case IF_CRC32C_MATCH: {
                    infoBuilder.setCrc32c(info.getCrc32c());
                    break;
                }
                case IF_MD5_MATCH: {
                    infoBuilder.setMd5(info.getMd5());
                    break;
                }
                default: {
                    targetOptions.add(option.toTargetOption());
                    break;
                }
            }
        }
        return (Tuple<BlobInfo, BlobTargetOption[]>)Tuple.of((Object)infoBuilder.build(), (Object)targetOptions.toArray(new BlobTargetOption[targetOptions.size()]));
    }
    
    BlobTargetOption(final StorageRpc.Option x0, final Object x1, final Storage$1 x2) {
        this(x0, x1);
    }
}
