package com.google.cloud.storage;

import java.io.*;
import java.util.*;
import java.security.*;
import com.google.common.io.*;
import com.google.cloud.storage.spi.v1.*;

public static class BlobWriteOption implements Serializable
{
    private static final long serialVersionUID = -3880421670966224580L;
    private final Option option;
    private final Object value;
    
    BlobTargetOption toTargetOption() {
        return new BlobTargetOption(this.option.toRpcOption(), this.value);
    }
    
    private BlobWriteOption(final Option option, final Object value) {
        super();
        this.option = option;
        this.value = value;
    }
    
    private BlobWriteOption(final Option option) {
        this(option, null);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.option, this.value);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BlobWriteOption)) {
            return false;
        }
        final BlobWriteOption other = (BlobWriteOption)obj;
        return this.option == other.option && Objects.equals(this.value, other.value);
    }
    
    public static BlobWriteOption predefinedAcl(final PredefinedAcl acl) {
        return new BlobWriteOption(Option.PREDEFINED_ACL, acl.getEntry());
    }
    
    public static BlobWriteOption doesNotExist() {
        return new BlobWriteOption(Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobWriteOption generationMatch() {
        return new BlobWriteOption(Option.IF_GENERATION_MATCH);
    }
    
    public static BlobWriteOption generationNotMatch() {
        return new BlobWriteOption(Option.IF_GENERATION_NOT_MATCH);
    }
    
    public static BlobWriteOption metagenerationMatch() {
        return new BlobWriteOption(Option.IF_METAGENERATION_MATCH);
    }
    
    public static BlobWriteOption metagenerationNotMatch() {
        return new BlobWriteOption(Option.IF_METAGENERATION_NOT_MATCH);
    }
    
    public static BlobWriteOption md5Match() {
        return new BlobWriteOption(Option.IF_MD5_MATCH, true);
    }
    
    public static BlobWriteOption crc32cMatch() {
        return new BlobWriteOption(Option.IF_CRC32C_MATCH, true);
    }
    
    public static BlobWriteOption encryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobWriteOption(Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobWriteOption encryptionKey(final String key) {
        return new BlobWriteOption(Option.CUSTOMER_SUPPLIED_KEY, key);
    }
    
    public static BlobWriteOption kmsKeyName(final String kmsKeyName) {
        return new BlobWriteOption(Option.KMS_KEY_NAME, kmsKeyName);
    }
    
    public static BlobWriteOption userProject(final String userProject) {
        return new BlobWriteOption(Option.USER_PROJECT, userProject);
    }
    
    static /* synthetic */ Option access$000(final BlobWriteOption x0) {
        return x0.option;
    }
    
    enum Option
    {
        PREDEFINED_ACL, 
        IF_GENERATION_MATCH, 
        IF_GENERATION_NOT_MATCH, 
        IF_METAGENERATION_MATCH, 
        IF_METAGENERATION_NOT_MATCH, 
        IF_MD5_MATCH, 
        IF_CRC32C_MATCH, 
        CUSTOMER_SUPPLIED_KEY, 
        KMS_KEY_NAME, 
        USER_PROJECT;
        
        private static final /* synthetic */ Option[] $VALUES;
        
        public static Option[] values() {
            return Option.$VALUES.clone();
        }
        
        public static Option valueOf(final String name) {
            return Enum.valueOf(Option.class, name);
        }
        
        StorageRpc.Option toRpcOption() {
            return StorageRpc.Option.valueOf(this.name());
        }
        
        static {
            $VALUES = new Option[] { Option.PREDEFINED_ACL, Option.IF_GENERATION_MATCH, Option.IF_GENERATION_NOT_MATCH, Option.IF_METAGENERATION_MATCH, Option.IF_METAGENERATION_NOT_MATCH, Option.IF_MD5_MATCH, Option.IF_CRC32C_MATCH, Option.CUSTOMER_SUPPLIED_KEY, Option.KMS_KEY_NAME, Option.USER_PROJECT };
        }
    }
}
