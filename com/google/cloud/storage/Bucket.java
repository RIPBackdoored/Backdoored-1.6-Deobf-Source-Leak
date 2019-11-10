package com.google.cloud.storage;

import com.google.api.gax.paging.*;
import com.google.cloud.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.common.base.*;
import java.security.*;
import com.google.common.io.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.*;

public class Bucket extends BucketInfo
{
    private static final long serialVersionUID = 8574601739542252586L;
    private final StorageOptions options;
    private transient Storage storage;
    
    Bucket(final Storage storage, final BuilderImpl infoBuilder) {
        super(infoBuilder);
        this.storage = Preconditions.checkNotNull(storage);
        this.options = (StorageOptions)storage.getOptions();
    }
    
    public boolean exists(final BucketSourceOption... options) {
        final int length = options.length;
        final Storage.BucketGetOption[] getOptions = Arrays.copyOf(BucketSourceOption.toGetOptions(this, options), length + 1);
        getOptions[length] = Storage.BucketGetOption.fields(new Storage.BucketField[0]);
        return this.storage.get(this.getName(), getOptions) != null;
    }
    
    public Bucket reload(final BucketSourceOption... options) {
        return this.storage.get(this.getName(), BucketSourceOption.toGetOptions(this, options));
    }
    
    public Bucket update(final Storage.BucketTargetOption... options) {
        return this.storage.update(this, options);
    }
    
    public boolean delete(final BucketSourceOption... options) {
        return this.storage.delete(this.getName(), BucketSourceOption.toSourceOptions(this, options));
    }
    
    public Page<Blob> list(final Storage.BlobListOption... options) {
        return this.storage.list(this.getName(), options);
    }
    
    public Blob get(final String blob, final Storage.BlobGetOption... options) {
        return this.storage.get(BlobId.of(this.getName(), blob), options);
    }
    
    public List<Blob> get(final String blobName1, final String blobName2, final String... blobNames) {
        final List<BlobId> blobIds = (List<BlobId>)Lists.newArrayListWithCapacity(blobNames.length + 2);
        blobIds.add(BlobId.of(this.getName(), blobName1));
        blobIds.add(BlobId.of(this.getName(), blobName2));
        for (final String blobName3 : blobNames) {
            blobIds.add(BlobId.of(this.getName(), blobName3));
        }
        return this.storage.get(blobIds);
    }
    
    public List<Blob> get(final Iterable<String> blobNames) {
        final ImmutableList.Builder<BlobId> builder = (ImmutableList.Builder<BlobId>)ImmutableList.builder();
        for (final String blobName : blobNames) {
            builder.add(BlobId.of(this.getName(), blobName));
        }
        return this.storage.get(builder.build());
    }
    
    public Blob create(final String blob, final byte[] content, final String contentType, final BlobTargetOption... options) {
        final BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(this.getName(), blob)).setContentType(contentType).build();
        final Tuple<BlobInfo, Storage.BlobTargetOption[]> target = BlobTargetOption.toTargetOptions(blobInfo, options);
        return this.storage.create((BlobInfo)target.x(), content, (Storage.BlobTargetOption[])target.y());
    }
    
    public Blob create(final String blob, final InputStream content, final String contentType, final BlobWriteOption... options) {
        final BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(this.getName(), blob)).setContentType(contentType).build();
        final Tuple<BlobInfo, Storage.BlobWriteOption[]> write = BlobWriteOption.toWriteOptions(blobInfo, options);
        return this.storage.create((BlobInfo)write.x(), content, (Storage.BlobWriteOption[])write.y());
    }
    
    public Blob create(final String blob, final byte[] content, final BlobTargetOption... options) {
        final BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(this.getName(), blob)).build();
        final Tuple<BlobInfo, Storage.BlobTargetOption[]> target = BlobTargetOption.toTargetOptions(blobInfo, options);
        return this.storage.create((BlobInfo)target.x(), content, (Storage.BlobTargetOption[])target.y());
    }
    
    public Blob create(final String blob, final InputStream content, final BlobWriteOption... options) {
        final BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(this.getName(), blob)).build();
        final Tuple<BlobInfo, Storage.BlobWriteOption[]> write = BlobWriteOption.toWriteOptions(blobInfo, options);
        return this.storage.create((BlobInfo)write.x(), content, (Storage.BlobWriteOption[])write.y());
    }
    
    public Acl getAcl(final Acl.Entity entity) {
        return this.storage.getAcl(this.getName(), entity);
    }
    
    public boolean deleteAcl(final Acl.Entity entity) {
        return this.storage.deleteAcl(this.getName(), entity);
    }
    
    public Acl createAcl(final Acl acl) {
        return this.storage.createAcl(this.getName(), acl);
    }
    
    public Acl updateAcl(final Acl acl) {
        return this.storage.updateAcl(this.getName(), acl);
    }
    
    public List<Acl> listAcls() {
        return this.storage.listAcls(this.getName());
    }
    
    public Acl getDefaultAcl(final Acl.Entity entity) {
        return this.storage.getDefaultAcl(this.getName(), entity);
    }
    
    public boolean deleteDefaultAcl(final Acl.Entity entity) {
        return this.storage.deleteDefaultAcl(this.getName(), entity);
    }
    
    public Acl createDefaultAcl(final Acl acl) {
        return this.storage.createDefaultAcl(this.getName(), acl);
    }
    
    public Acl updateDefaultAcl(final Acl acl) {
        return this.storage.updateDefaultAcl(this.getName(), acl);
    }
    
    public List<Acl> listDefaultAcls() {
        return this.storage.listDefaultAcls(this.getName());
    }
    
    public Bucket lockRetentionPolicy(final Storage.BucketTargetOption... options) {
        return this.storage.lockRetentionPolicy(this, options);
    }
    
    public Storage getStorage() {
        return this.storage;
    }
    
    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }
    
    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(Bucket.class)) {
            return false;
        }
        final Bucket other = (Bucket)obj;
        return Objects.equals(this.toPb(), other.toPb()) && Objects.equals(this.options, other.options);
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), this.options);
    }
    
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.storage = (Storage)this.options.getService();
    }
    
    static Bucket fromPb(final Storage storage, final com.google.api.services.storage.model.Bucket bucketPb) {
        return new Bucket(storage, new BuilderImpl(BucketInfo.fromPb(bucketPb)));
    }
    
    @Override
    public /* bridge */ BucketInfo.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static /* synthetic */ Storage access$100(final Bucket x0) {
        return x0.storage;
    }
    
    public static class BucketSourceOption extends Option
    {
        private static final long serialVersionUID = 6928872234155522371L;
        
        private BucketSourceOption(final StorageRpc.Option rpcOption) {
            super(rpcOption, null);
        }
        
        private BucketSourceOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        private Storage.BucketSourceOption toSourceOption(final BucketInfo bucketInfo) {
            switch (this.getRpcOption()) {
                case IF_METAGENERATION_MATCH: {
                    return Storage.BucketSourceOption.metagenerationMatch(bucketInfo.getMetageneration());
                }
                case IF_METAGENERATION_NOT_MATCH: {
                    return Storage.BucketSourceOption.metagenerationNotMatch(bucketInfo.getMetageneration());
                }
                default: {
                    throw new AssertionError((Object)"Unexpected enum value");
                }
            }
        }
        
        private Storage.BucketGetOption toGetOption(final BucketInfo bucketInfo) {
            switch (this.getRpcOption()) {
                case IF_METAGENERATION_MATCH: {
                    return Storage.BucketGetOption.metagenerationMatch(bucketInfo.getMetageneration());
                }
                case IF_METAGENERATION_NOT_MATCH: {
                    return Storage.BucketGetOption.metagenerationNotMatch(bucketInfo.getMetageneration());
                }
                default: {
                    throw new AssertionError((Object)"Unexpected enum value");
                }
            }
        }
        
        public static BucketSourceOption metagenerationMatch() {
            return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
        }
        
        public static BucketSourceOption metagenerationNotMatch() {
            return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
        }
        
        public static BucketSourceOption userProject(final String userProject) {
            return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        static Storage.BucketSourceOption[] toSourceOptions(final BucketInfo bucketInfo, final BucketSourceOption... options) {
            final Storage.BucketSourceOption[] convertedOptions = new Storage.BucketSourceOption[options.length];
            int index = 0;
            for (final BucketSourceOption option : options) {
                convertedOptions[index++] = option.toSourceOption(bucketInfo);
            }
            return convertedOptions;
        }
        
        static Storage.BucketGetOption[] toGetOptions(final BucketInfo bucketInfo, final BucketSourceOption... options) {
            final Storage.BucketGetOption[] convertedOptions = new Storage.BucketGetOption[options.length];
            int index = 0;
            for (final BucketSourceOption option : options) {
                convertedOptions[index++] = option.toGetOption(bucketInfo);
            }
            return convertedOptions;
        }
    }
    
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
    
    public static class Builder extends BucketInfo.Builder
    {
        private final Storage storage;
        private final BuilderImpl infoBuilder;
        
        Builder(final Bucket bucket) {
            super();
            this.storage = bucket.storage;
            this.infoBuilder = new BuilderImpl(bucket);
        }
        
        @Override
        public Builder setName(final String name) {
            this.infoBuilder.setName(name);
            return this;
        }
        
        @Override
        Builder setGeneratedId(final String generatedId) {
            this.infoBuilder.setGeneratedId(generatedId);
            return this;
        }
        
        @Override
        Builder setOwner(final Acl.Entity owner) {
            this.infoBuilder.setOwner(owner);
            return this;
        }
        
        @Override
        Builder setSelfLink(final String selfLink) {
            this.infoBuilder.setSelfLink(selfLink);
            return this;
        }
        
        @Override
        public Builder setVersioningEnabled(final Boolean enable) {
            this.infoBuilder.setVersioningEnabled(enable);
            return this;
        }
        
        @Override
        public Builder setRequesterPays(final Boolean requesterPays) {
            this.infoBuilder.setRequesterPays(requesterPays);
            return this;
        }
        
        @Override
        public Builder setIndexPage(final String indexPage) {
            this.infoBuilder.setIndexPage(indexPage);
            return this;
        }
        
        @Override
        public Builder setNotFoundPage(final String notFoundPage) {
            this.infoBuilder.setNotFoundPage(notFoundPage);
            return this;
        }
        
        @Deprecated
        @Override
        public Builder setDeleteRules(final Iterable<? extends DeleteRule> rules) {
            this.infoBuilder.setDeleteRules(rules);
            return this;
        }
        
        @Override
        public Builder setLifecycleRules(final Iterable<? extends LifecycleRule> rules) {
            this.infoBuilder.setLifecycleRules(rules);
            return this;
        }
        
        @Override
        public Builder setStorageClass(final StorageClass storageClass) {
            this.infoBuilder.setStorageClass(storageClass);
            return this;
        }
        
        @Override
        public Builder setLocation(final String location) {
            this.infoBuilder.setLocation(location);
            return this;
        }
        
        @Override
        Builder setEtag(final String etag) {
            this.infoBuilder.setEtag(etag);
            return this;
        }
        
        @Override
        Builder setCreateTime(final Long createTime) {
            this.infoBuilder.setCreateTime(createTime);
            return this;
        }
        
        @Override
        Builder setMetageneration(final Long metageneration) {
            this.infoBuilder.setMetageneration(metageneration);
            return this;
        }
        
        @Override
        public Builder setCors(final Iterable<Cors> cors) {
            this.infoBuilder.setCors(cors);
            return this;
        }
        
        @Override
        public Builder setAcl(final Iterable<Acl> acl) {
            this.infoBuilder.setAcl(acl);
            return this;
        }
        
        @Override
        public Builder setDefaultAcl(final Iterable<Acl> acl) {
            this.infoBuilder.setDefaultAcl(acl);
            return this;
        }
        
        @Override
        public Builder setLabels(final Map<String, String> labels) {
            this.infoBuilder.setLabels(labels);
            return this;
        }
        
        @Override
        public Builder setDefaultKmsKeyName(final String defaultKmsKeyName) {
            this.infoBuilder.setDefaultKmsKeyName(defaultKmsKeyName);
            return this;
        }
        
        @Override
        public Builder setDefaultEventBasedHold(final Boolean defaultEventBasedHold) {
            this.infoBuilder.setDefaultEventBasedHold(defaultEventBasedHold);
            return this;
        }
        
        @Override
        Builder setRetentionEffectiveTime(final Long retentionEffectiveTime) {
            this.infoBuilder.setRetentionEffectiveTime(retentionEffectiveTime);
            return this;
        }
        
        @Override
        Builder setRetentionPolicyIsLocked(final Boolean retentionIsLocked) {
            this.infoBuilder.setRetentionPolicyIsLocked(retentionIsLocked);
            return this;
        }
        
        @Override
        public Builder setRetentionPeriod(final Long retentionPeriod) {
            this.infoBuilder.setRetentionPeriod(retentionPeriod);
            return this;
        }
        
        @Override
        public Builder setIamConfiguration(final IamConfiguration iamConfiguration) {
            this.infoBuilder.setIamConfiguration(iamConfiguration);
            return this;
        }
        
        @Override
        Builder setLocationType(final String locationType) {
            this.infoBuilder.setLocationType(locationType);
            return this;
        }
        
        @Override
        public Bucket build() {
            return new Bucket(this.storage, this.infoBuilder);
        }
        
        @Override
        public /* bridge */ BucketInfo build() {
            return this.build();
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setIamConfiguration(final IamConfiguration iamConfiguration) {
            return this.setIamConfiguration(iamConfiguration);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setRetentionPeriod(final Long retentionPeriod) {
            return this.setRetentionPeriod(retentionPeriod);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setRetentionPolicyIsLocked(final Boolean retentionPolicyIsLocked) {
            return this.setRetentionPolicyIsLocked(retentionPolicyIsLocked);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setRetentionEffectiveTime(final Long retentionEffectiveTime) {
            return this.setRetentionEffectiveTime(retentionEffectiveTime);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setDefaultEventBasedHold(final Boolean defaultEventBasedHold) {
            return this.setDefaultEventBasedHold(defaultEventBasedHold);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setDefaultKmsKeyName(final String defaultKmsKeyName) {
            return this.setDefaultKmsKeyName(defaultKmsKeyName);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setLabels(final Map labels) {
            return this.setLabels(labels);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setDefaultAcl(final Iterable defaultAcl) {
            return this.setDefaultAcl(defaultAcl);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setAcl(final Iterable acl) {
            return this.setAcl(acl);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setCors(final Iterable cors) {
            return this.setCors(cors);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setLocationType(final String locationType) {
            return this.setLocationType(locationType);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setMetageneration(final Long metageneration) {
            return this.setMetageneration(metageneration);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setCreateTime(final Long createTime) {
            return this.setCreateTime(createTime);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setEtag(final String etag) {
            return this.setEtag(etag);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setLocation(final String location) {
            return this.setLocation(location);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setStorageClass(final StorageClass storageClass) {
            return this.setStorageClass(storageClass);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setLifecycleRules(final Iterable lifecycleRules) {
            return this.setLifecycleRules(lifecycleRules);
        }
        
        @Deprecated
        @Override
        public /* bridge */ BucketInfo.Builder setDeleteRules(final Iterable deleteRules) {
            return this.setDeleteRules(deleteRules);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setNotFoundPage(final String notFoundPage) {
            return this.setNotFoundPage(notFoundPage);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setIndexPage(final String indexPage) {
            return this.setIndexPage(indexPage);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setVersioningEnabled(final Boolean versioningEnabled) {
            return this.setVersioningEnabled(versioningEnabled);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setRequesterPays(final Boolean requesterPays) {
            return this.setRequesterPays(requesterPays);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setSelfLink(final String selfLink) {
            return this.setSelfLink(selfLink);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setOwner(final Acl.Entity owner) {
            return this.setOwner(owner);
        }
        
        @Override
        /* bridge */ BucketInfo.Builder setGeneratedId(final String generatedId) {
            return this.setGeneratedId(generatedId);
        }
        
        @Override
        public /* bridge */ BucketInfo.Builder setName(final String name) {
            return this.setName(name);
        }
    }
}
