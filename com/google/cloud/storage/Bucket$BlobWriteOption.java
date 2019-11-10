package com.google.cloud.storage;

import java.io.*;
import com.google.cloud.*;
import java.security.*;
import com.google.common.io.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public static class BlobWriteOption implements Serializable
{
    private static final Function<BlobWriteOption, Storage.BlobWriteOption.Option> TO_ENUM;
    private static final long serialVersionUID = 4722190734541993114L;
    private final Storage.BlobWriteOption.Option option;
    private final Object value;
    
    private Tuple<BlobInfo, Storage.BlobWriteOption> toWriteOption(final BlobInfo blobInfo) {
        BlobId blobId = blobInfo.getBlobId();
        switch (this.option) {
            case PREDEFINED_ACL: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobWriteOption.predefinedAcl((Storage.PredefinedAcl)this.value));
            }
            case IF_GENERATION_MATCH: {
                blobId = BlobId.of(blobId.getBucket(), blobId.getName(), (Long)this.value);
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo.toBuilder().setBlobId(blobId).build(), (Object)Storage.BlobWriteOption.generationMatch());
            }
            case IF_GENERATION_NOT_MATCH: {
                blobId = BlobId.of(blobId.getBucket(), blobId.getName(), (Long)this.value);
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo.toBuilder().setBlobId(blobId).build(), (Object)Storage.BlobWriteOption.generationNotMatch());
            }
            case IF_METAGENERATION_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo.toBuilder().setMetageneration((Long)this.value).build(), (Object)Storage.BlobWriteOption.metagenerationMatch());
            }
            case IF_METAGENERATION_NOT_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo.toBuilder().setMetageneration((Long)this.value).build(), (Object)Storage.BlobWriteOption.metagenerationNotMatch());
            }
            case IF_MD5_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo.toBuilder().setMd5((String)this.value).build(), (Object)Storage.BlobWriteOption.md5Match());
            }
            case IF_CRC32C_MATCH: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo.toBuilder().setCrc32c((String)this.value).build(), (Object)Storage.BlobWriteOption.crc32cMatch());
            }
            case CUSTOMER_SUPPLIED_KEY: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobWriteOption.encryptionKey((String)this.value));
            }
            case KMS_KEY_NAME: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobWriteOption.kmsKeyName((String)this.value));
            }
            case USER_PROJECT: {
                return (Tuple<BlobInfo, Storage.BlobWriteOption>)Tuple.of((Object)blobInfo, (Object)Storage.BlobWriteOption.userProject((String)this.value));
            }
            default: {
                throw new AssertionError((Object)"Unexpected enum value");
            }
        }
    }
    
    private BlobWriteOption(final Storage.BlobWriteOption.Option option, final Object value) {
        super();
        this.option = option;
        this.value = value;
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
    
    public static BlobWriteOption predefinedAcl(final Storage.PredefinedAcl acl) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.PREDEFINED_ACL, acl);
    }
    
    public static BlobWriteOption doesNotExist() {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_GENERATION_MATCH, 0L);
    }
    
    public static BlobWriteOption generationMatch(final long generation) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_GENERATION_MATCH, generation);
    }
    
    public static BlobWriteOption generationNotMatch(final long generation) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_GENERATION_NOT_MATCH, generation);
    }
    
    public static BlobWriteOption metagenerationMatch(final long metageneration) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_METAGENERATION_MATCH, metageneration);
    }
    
    public static BlobWriteOption metagenerationNotMatch(final long metageneration) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
    }
    
    public static BlobWriteOption md5Match(final String md5) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_MD5_MATCH, md5);
    }
    
    public static BlobWriteOption crc32cMatch(final String crc32c) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.IF_CRC32C_MATCH, crc32c);
    }
    
    public static BlobWriteOption encryptionKey(final Key key) {
        final String base64Key = BaseEncoding.base64().encode(key.getEncoded());
        return new BlobWriteOption(Storage.BlobWriteOption.Option.CUSTOMER_SUPPLIED_KEY, base64Key);
    }
    
    public static BlobWriteOption encryptionKey(final String key) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.CUSTOMER_SUPPLIED_KEY, key);
    }
    
    public static BlobWriteOption userProject(final String userProject) {
        return new BlobWriteOption(Storage.BlobWriteOption.Option.USER_PROJECT, userProject);
    }
    
    static Tuple<BlobInfo, Storage.BlobWriteOption[]> toWriteOptions(final BlobInfo info, final BlobWriteOption... options) {
        final Set<Storage.BlobWriteOption.Option> optionSet = (Set<Storage.BlobWriteOption.Option>)Sets.immutableEnumSet((Iterable)Lists.transform((List<Object>)Arrays.asList((F[])options), (Function<? super Object, ?>)BlobWriteOption.TO_ENUM));
        Preconditions.checkArgument(!optionSet.contains(Storage.BlobWriteOption.Option.IF_METAGENERATION_NOT_MATCH) || !optionSet.contains(Storage.BlobWriteOption.Option.IF_METAGENERATION_MATCH), (Object)"metagenerationMatch and metagenerationNotMatch options can not be both provided");
        Preconditions.checkArgument(!optionSet.contains(Storage.BlobWriteOption.Option.IF_GENERATION_NOT_MATCH) || !optionSet.contains(Storage.BlobWriteOption.Option.IF_GENERATION_MATCH), (Object)"Only one option of generationMatch, doesNotExist or generationNotMatch can be provided");
        final Storage.BlobWriteOption[] convertedOptions = new Storage.BlobWriteOption[options.length];
        BlobInfo writeInfo = info;
        int index = 0;
        for (final BlobWriteOption option : options) {
            final Tuple<BlobInfo, Storage.BlobWriteOption> write = option.toWriteOption(writeInfo);
            writeInfo = (BlobInfo)write.x();
            convertedOptions[index++] = (Storage.BlobWriteOption)write.y();
        }
        return (Tuple<BlobInfo, Storage.BlobWriteOption[]>)Tuple.of((Object)writeInfo, (Object)convertedOptions);
    }
    
    static /* synthetic */ Storage.BlobWriteOption.Option access$000(final BlobWriteOption x0) {
        return x0.option;
    }
    
    static {
        TO_ENUM = new Function<BlobWriteOption, Storage.BlobWriteOption.Option>() {
            Bucket$BlobWriteOption$1() {
                super();
            }
            
            @Override
            public Storage.BlobWriteOption.Option apply(final BlobWriteOption blobWriteOption) {
                return blobWriteOption.option;
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((BlobWriteOption)o);
            }
        };
    }
}
