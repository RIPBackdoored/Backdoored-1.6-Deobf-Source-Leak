package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.cloud.*;
import java.security.*;
import com.google.common.io.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public static class BlobTargetOption extends Option
{
    private static final Function<BlobTargetOption, StorageRpc.Option> TO_ENUM;
    private static final long serialVersionUID = 8345296337342509425L;
    
    private BlobTargetOption(final StorageRpc.Option rpcOption, final Object value) {
        super(rpcOption, value);
    }
    
    private Tuple<BlobInfo, Storage.BlobTargetOption> toTargetOption(final BlobInfo blobInfo) {
        BlobId blobId = blobInfo.getBlobId();
        switch (this.getRpcOption()) {
            case PREDEFINED_ACL: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobTargetOption.predefinedAcl((Storage.PredefinedAcl)this.getValue()));
            }
            case IF_GENERATION_MATCH: {
                blobId = BlobId.of(blobId.getBucket(), blobId.getName(), (Long)this.getValue());
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo.toBuilder().setBlobId(blobId).build(), (Object)Storage.BlobTargetOption.generationMatch());
            }
            case IF_GENERATION_NOT_MATCH: {
                blobId = BlobId.of(blobId.getBucket(), blobId.getName(), (Long)this.getValue());
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo.toBuilder().setBlobId(blobId).build(), (Object)Storage.BlobTargetOption.generationNotMatch());
            }
            case IF_METAGENERATION_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo.toBuilder().setMetageneration((Long)this.getValue()).build(), (Object)Storage.BlobTargetOption.metagenerationMatch());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo.toBuilder().setMetageneration((Long)this.getValue()).build(), (Object)Storage.BlobTargetOption.metagenerationNotMatch());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobTargetOption.encryptionKey((String)this.getValue()));
            }
            case KMS_KEY_NAME: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobTargetOption.kmsKeyName((String)this.getValue()));
            }
            case USER_PROJECT: {
                return (Tuple<BlobInfo, Storage.BlobTargetOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobTargetOption.userProject((String)this.getValue()));
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    public static BlobTargetOption predefinedAcl(final Storage.PredefinedAcl acl) {
        return new BlobTargetOption(StorageRpc.Option.PREDEFINED_ACL, acl);
    }
    
    public static BlobTargetOption doesNotExist() {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobTargetOption generationMatch(final long generation) {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_MATCH, generation);
    }
    
    public static BlobTargetOption generationNotMatch(final long generation) {
        return new BlobTargetOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation);
    }
    
    public static BlobTargetOption metagenerationMatch(final long metageneration) {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
    }
    
    public static BlobTargetOption metagenerationNotMatch(final long metageneration) {
        return new BlobTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
    
    public static BlobTargetOption encryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobTargetOption encryptionKey(final String key) {
        return new BlobTargetOption(StorageRpc.Option.CUSTOMER_SUPPLIED_KEY, key);
    }
    
    public static BlobTargetOption kmsKeyName(final String kmsKeyName) {
        return new BlobTargetOption(StorageRpc.Option.KMS_KEY_NAME, kmsKeyName);
    }
    
    public static BlobTargetOption userProject(final String userProject) {
        return new BlobTargetOption(StorageRpc.Option.USER_PROJECT, userProject);
    }
    
    static Tuple<BlobInfo, Storage.BlobTargetOption[]> toTargetOptions(final BlobInfo info, final BlobTargetOption... options) {
        final Set<StorageRpc.Option> optionSet = (Set<StorageRpc.Option>)Sets.immutableEnumSet((Iterable)Lists.transform((List<Object>)Arrays.asList((F[])options), (Function<? super Object, ?>)BlobTargetOption.TO_ENUM));
        Preconditions.checkArgument(!optionSet.contains(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH) || !optionSet.contains(StorageRpc.Option.IF_METAGENERATION_MATCH), (Object)"metagenerationMatch and metagenerationNotMatch options can not be both provided");
        Preconditions.checkArgument(!optionSet.contains(StorageRpc.Option.IF_GENERATION_NOT_MATCH) || !optionSet.contains(StorageRpc.Option.IF_GENERATION_MATCH), (Object)"Only one option of generationMatch, doesNotExist or generationNotMatch can be provided");
        final Storage.BlobTargetOption[] convertedOptions = new Storage.BlobTargetOption[options.length];
        BlobInfo targetInfo = info;
        int index = 0;
        for (final BlobTargetOption option : options) {
            final Tuple<BlobInfo, Storage.BlobTargetOption> target = option.toTargetOption(targetInfo);
            targetInfo = (BlobInfo)target.x();
            convertedOptions[index++] = (Storage.BlobTargetOption)target.y();
        }
        return (Tuple<BlobInfo, Storage.BlobTargetOption[]>)Tuple.of((Object)targetInfo, (Object)convertedOptions);
    }
    
    static {
        TO_ENUM = new Function<BlobTargetOption, StorageRpc.Option>() {
            Bucket$BlobTargetOption$1() {
                super();
            }
            
            @Override
            public StorageRpc.Option apply(final BlobTargetOption blobTargetOption) {
                return blobTargetOption.getRpcOption();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BlobTargetOption)o);
            }
        };
    }
}
