package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import java.nio.file.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.retrying.*;
import com.google.common.base.*;
import com.google.cloud.*;
import java.util.concurrent.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.security.*;
import com.google.common.io.*;

public class Blob extends BlobInfo
{
    private static final long serialVersionUID = -6806832496717441434L;
    private final StorageOptions options;
    private transient Storage storage;
    static final Function<Tuple<Storage, StorageObject>, Blob> BLOB_FROM_PB_FUNCTION;
    private static final int DEFAULT_CHUNK_SIZE = 2097152;
    
    public void downloadTo(final Path path, final BlobSourceOption... options) {
        try (final OutputStream outputStream = Files.newOutputStream(path, new OpenOption[0])) {
            this.downloadTo(outputStream, options);
        }
        catch (IOException e) {
            throw new StorageException(e);
        }
    }
    
    public void downloadTo(final OutputStream outputStream, final BlobSourceOption... options) {
        final CountingOutputStream countingOutputStream = new CountingOutputStream(outputStream);
        final StorageRpc storageRpc = this.options.getStorageRpcV1();
        final Map<StorageRpc.Option, ?> requestOptions = StorageImpl.optionMap(this.getBlobId(), (Option[])options);
        RetryHelper.runWithRetries((Callable)Executors.callable(new Runnable() {
            final /* synthetic */ StorageRpc val$storageRpc;
            final /* synthetic */ Map val$requestOptions;
            final /* synthetic */ CountingOutputStream val$countingOutputStream;
            final /* synthetic */ Blob this$0;
            
            Blob$2() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public void run() {
                storageRpc.read(this.this$0.getBlobId().toPb(), requestOptions, countingOutputStream.getCount(), (OutputStream)countingOutputStream);
            }
        }), this.options.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, this.options.getClock());
    }
    
    public void downloadTo(final Path path) {
        this.downloadTo(path, new BlobSourceOption[0]);
    }
    
    Blob(final Storage storage, final BuilderImpl infoBuilder) {
        super(infoBuilder);
        this.storage = Preconditions.checkNotNull(storage);
        this.options = (StorageOptions)storage.getOptions();
    }
    
    public boolean exists(final BlobSourceOption... options) {
        final int length = options.length;
        final Storage.BlobGetOption[] getOptions = Arrays.copyOf(BlobSourceOption.toGetOptions(this, options), length + 1);
        getOptions[length] = Storage.BlobGetOption.fields(new Storage.BlobField[0]);
        return this.storage.get(this.getBlobId(), getOptions) != null;
    }
    
    public byte[] getContent(final BlobSourceOption... options) {
        return this.storage.readAllBytes(this.getBlobId(), BlobSourceOption.toSourceOptions(this, options));
    }
    
    public Blob reload(final BlobSourceOption... options) {
        return this.storage.get(this.getBlobId(), BlobSourceOption.toGetOptions(this, options));
    }
    
    public Blob update(final Storage.BlobTargetOption... options) {
        return this.storage.update(this, options);
    }
    
    public boolean delete(final BlobSourceOption... options) {
        return this.storage.delete(this.getBlobId(), BlobSourceOption.toSourceOptions(this, options));
    }
    
    public CopyWriter copyTo(final BlobId targetBlob, final BlobSourceOption... options) {
        final Storage.CopyRequest copyRequest = Storage.CopyRequest.newBuilder().setSource(this.getBucket(), this.getName()).setSourceOptions(BlobSourceOption.toSourceOptions(this, options)).setTarget(targetBlob).build();
        return this.storage.copy(copyRequest);
    }
    
    public CopyWriter copyTo(final String targetBucket, final BlobSourceOption... options) {
        return this.copyTo(targetBucket, this.getName(), options);
    }
    
    public CopyWriter copyTo(final String targetBucket, final String targetBlob, final BlobSourceOption... options) {
        return this.copyTo(BlobId.of(targetBucket, targetBlob), options);
    }
    
    public ReadChannel reader(final BlobSourceOption... options) {
        return this.storage.reader(this.getBlobId(), BlobSourceOption.toSourceOptions(this, options));
    }
    
    public WriteChannel writer(final Storage.BlobWriteOption... options) {
        return this.storage.writer(this, options);
    }
    
    public URL signUrl(final long duration, final TimeUnit unit, final Storage.SignUrlOption... options) {
        return this.storage.signUrl(this, duration, unit, options);
    }
    
    public Acl getAcl(final Acl.Entity entity) {
        return this.storage.getAcl(this.getBlobId(), entity);
    }
    
    public boolean deleteAcl(final Acl.Entity entity) {
        return this.storage.deleteAcl(this.getBlobId(), entity);
    }
    
    public Acl createAcl(final Acl acl) {
        return this.storage.createAcl(this.getBlobId(), acl);
    }
    
    public Acl updateAcl(final Acl acl) {
        return this.storage.updateAcl(this.getBlobId(), acl);
    }
    
    public List<Acl> listAcls() {
        return this.storage.listAcls(this.getBlobId());
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
        if (obj == null || !obj.getClass().equals(Blob.class)) {
            return false;
        }
        final Blob other = (Blob)obj;
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
    
    static Blob fromPb(final Storage storage, final StorageObject storageObject) {
        final BlobInfo info = BlobInfo.fromPb(storageObject);
        return new Blob(storage, new BuilderImpl(info));
    }
    
    @Override
    public /* bridge */ BlobInfo.Builder toBuilder() {
        return this.toBuilder();
    }
    
    static {
        BLOB_FROM_PB_FUNCTION = new Function<Tuple<Storage, StorageObject>, Blob>() {
            Blob$1() {
                super();
            }
            
            @Override
            public Blob apply(final Tuple<Storage, StorageObject> pb) {
                return Blob.fromPb((Storage)pb.x(), (StorageObject)pb.y());
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Tuple<Storage, StorageObject>)o);
            }
        };
    }
    
    public static class BlobSourceOption extends Option
    {
        private static final long serialVersionUID = 214616862061934846L;
        
        private BlobSourceOption(final StorageRpc.Option rpcOption) {
            super(rpcOption, null);
        }
        
        private BlobSourceOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        private Storage.BlobSourceOption toSourceOptions(final BlobInfo blobInfo) {
            switch (this.getRpcOption()) {
                case IF_GENERATION_MATCH: {
                    return Storage.BlobSourceOption.generationMatch(blobInfo.getGeneration());
                }
                case IF_GENERATION_NOT_MATCH: {
                    return Storage.BlobSourceOption.generationNotMatch(blobInfo.getGeneration());
                }
                case IF_METAGENERATION_MATCH: {
                    return Storage.BlobSourceOption.metagenerationMatch(blobInfo.getMetageneration());
                }
                case IF_METAGENERATION_NOT_MATCH: {
                    return Storage.BlobSourceOption.metagenerationNotMatch(blobInfo.getMetageneration());
                }
                case CUSTOMER_SUPPLIED_KEY: {
                    return Storage.BlobSourceOption.decryptionKey((String)this.getValue());
                }
                case USER_PROJECT: {
                    return Storage.BlobSourceOption.userProject((String)this.getValue());
                }
                default: {
                    throw new AssertionError((Object)"Unexpected enum value");
                }
            }
        }
        
        private Storage.BlobGetOption toGetOption(final BlobInfo blobInfo) {
            switch (this.getRpcOption()) {
                case IF_GENERATION_MATCH: {
                    return Storage.BlobGetOption.generationMatch(blobInfo.getGeneration());
                }
                case IF_GENERATION_NOT_MATCH: {
                    return Storage.BlobGetOption.generationNotMatch(blobInfo.getGeneration());
                }
                case IF_METAGENERATION_MATCH: {
                    return Storage.BlobGetOption.metagenerationMatch(blobInfo.getMetageneration());
                }
                case IF_METAGENERATION_NOT_MATCH: {
                    return Storage.BlobGetOption.metagenerationNotMatch(blobInfo.getMetageneration());
                }
                case USER_PROJECT: {
                    return Storage.BlobGetOption.userProject((String)this.getValue());
                }
                case CUSTOMER_SUPPLIED_KEY: {
                    return Storage.BlobGetOption.decryptionKey((String)this.getValue());
                }
                default: {
                    throw new AssertionError((Object)"Unexpected enum value");
                }
            }
        }
        
        public static BlobSourceOption generationMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH);
        }
        
        public static BlobSourceOption generationNotMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH);
        }
        
        public static BlobSourceOption metagenerationMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
        }
        
        public static BlobSourceOption metagenerationNotMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
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
        
        static Storage.BlobSourceOption[] toSourceOptions(final BlobInfo blobInfo, final BlobSourceOption... options) {
            final Storage.BlobSourceOption[] convertedOptions = new Storage.BlobSourceOption[options.length];
            int index = 0;
            for (final BlobSourceOption option : options) {
                convertedOptions[index++] = option.toSourceOptions(blobInfo);
            }
            return convertedOptions;
        }
        
        static Storage.BlobGetOption[] toGetOptions(final BlobInfo blobInfo, final BlobSourceOption... options) {
            final Storage.BlobGetOption[] convertedOptions = new Storage.BlobGetOption[options.length];
            int index = 0;
            for (final BlobSourceOption option : options) {
                convertedOptions[index++] = option.toGetOption(blobInfo);
            }
            return convertedOptions;
        }
    }
    
    public static class Builder extends BlobInfo.Builder
    {
        private final Storage storage;
        private final BuilderImpl infoBuilder;
        
        Builder(final Blob blob) {
            super();
            this.storage = blob.getStorage();
            this.infoBuilder = new BuilderImpl(blob);
        }
        
        @Override
        public Builder setBlobId(final BlobId blobId) {
            this.infoBuilder.setBlobId(blobId);
            return this;
        }
        
        @Override
        Builder setGeneratedId(final String generatedId) {
            this.infoBuilder.setGeneratedId(generatedId);
            return this;
        }
        
        @Override
        public Builder setContentType(final String contentType) {
            this.infoBuilder.setContentType(contentType);
            return this;
        }
        
        @Override
        public Builder setContentDisposition(final String contentDisposition) {
            this.infoBuilder.setContentDisposition(contentDisposition);
            return this;
        }
        
        @Override
        public Builder setContentLanguage(final String contentLanguage) {
            this.infoBuilder.setContentLanguage(contentLanguage);
            return this;
        }
        
        @Override
        public Builder setContentEncoding(final String contentEncoding) {
            this.infoBuilder.setContentEncoding(contentEncoding);
            return this;
        }
        
        @Override
        Builder setComponentCount(final Integer componentCount) {
            this.infoBuilder.setComponentCount(componentCount);
            return this;
        }
        
        @Override
        public Builder setCacheControl(final String cacheControl) {
            this.infoBuilder.setCacheControl(cacheControl);
            return this;
        }
        
        @Override
        public Builder setAcl(final List<Acl> acl) {
            this.infoBuilder.setAcl(acl);
            return this;
        }
        
        @Override
        Builder setOwner(final Acl.Entity owner) {
            this.infoBuilder.setOwner(owner);
            return this;
        }
        
        @Override
        Builder setSize(final Long size) {
            this.infoBuilder.setSize(size);
            return this;
        }
        
        @Override
        Builder setEtag(final String etag) {
            this.infoBuilder.setEtag(etag);
            return this;
        }
        
        @Override
        Builder setSelfLink(final String selfLink) {
            this.infoBuilder.setSelfLink(selfLink);
            return this;
        }
        
        @Override
        public Builder setMd5(final String md5) {
            this.infoBuilder.setMd5(md5);
            return this;
        }
        
        @Override
        public Builder setMd5FromHexString(final String md5HexString) {
            this.infoBuilder.setMd5FromHexString(md5HexString);
            return this;
        }
        
        @Override
        public Builder setCrc32c(final String crc32c) {
            this.infoBuilder.setCrc32c(crc32c);
            return this;
        }
        
        @Override
        public Builder setCrc32cFromHexString(final String crc32cHexString) {
            this.infoBuilder.setCrc32cFromHexString(crc32cHexString);
            return this;
        }
        
        @Override
        Builder setMediaLink(final String mediaLink) {
            this.infoBuilder.setMediaLink(mediaLink);
            return this;
        }
        
        @Override
        public Builder setMetadata(final Map<String, String> metadata) {
            this.infoBuilder.setMetadata(metadata);
            return this;
        }
        
        @Override
        public Builder setStorageClass(final StorageClass storageClass) {
            this.infoBuilder.setStorageClass(storageClass);
            return this;
        }
        
        @Override
        Builder setMetageneration(final Long metageneration) {
            this.infoBuilder.setMetageneration(metageneration);
            return this;
        }
        
        @Override
        Builder setDeleteTime(final Long deleteTime) {
            this.infoBuilder.setDeleteTime(deleteTime);
            return this;
        }
        
        @Override
        Builder setUpdateTime(final Long updateTime) {
            this.infoBuilder.setUpdateTime(updateTime);
            return this;
        }
        
        @Override
        Builder setCreateTime(final Long createTime) {
            this.infoBuilder.setCreateTime(createTime);
            return this;
        }
        
        @Override
        Builder setIsDirectory(final boolean isDirectory) {
            this.infoBuilder.setIsDirectory(isDirectory);
            return this;
        }
        
        @Override
        Builder setCustomerEncryption(final CustomerEncryption customerEncryption) {
            this.infoBuilder.setCustomerEncryption(customerEncryption);
            return this;
        }
        
        @Override
        Builder setKmsKeyName(final String kmsKeyName) {
            this.infoBuilder.setKmsKeyName(kmsKeyName);
            return this;
        }
        
        @Override
        public Builder setEventBasedHold(final Boolean eventBasedHold) {
            this.infoBuilder.setEventBasedHold(eventBasedHold);
            return this;
        }
        
        @Override
        public Builder setTemporaryHold(final Boolean temporaryHold) {
            this.infoBuilder.setTemporaryHold(temporaryHold);
            return this;
        }
        
        @Override
        Builder setRetentionExpirationTime(final Long retentionExpirationTime) {
            this.infoBuilder.setRetentionExpirationTime(retentionExpirationTime);
            return this;
        }
        
        @Override
        public Blob build() {
            return new Blob(this.storage, this.infoBuilder);
        }
        
        @Override
        public /* bridge */ BlobInfo build() {
            return this.build();
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setRetentionExpirationTime(final Long retentionExpirationTime) {
            return this.setRetentionExpirationTime(retentionExpirationTime);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setTemporaryHold(final Boolean temporaryHold) {
            return this.setTemporaryHold(temporaryHold);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setEventBasedHold(final Boolean eventBasedHold) {
            return this.setEventBasedHold(eventBasedHold);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setKmsKeyName(final String kmsKeyName) {
            return this.setKmsKeyName(kmsKeyName);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setCustomerEncryption(final CustomerEncryption customerEncryption) {
            return this.setCustomerEncryption(customerEncryption);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setIsDirectory(final boolean isDirectory) {
            return this.setIsDirectory(isDirectory);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setCreateTime(final Long createTime) {
            return this.setCreateTime(createTime);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setUpdateTime(final Long updateTime) {
            return this.setUpdateTime(updateTime);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setDeleteTime(final Long deleteTime) {
            return this.setDeleteTime(deleteTime);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setMetageneration(final Long metageneration) {
            return this.setMetageneration(metageneration);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setMetadata(final Map metadata) {
            return this.setMetadata(metadata);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setStorageClass(final StorageClass storageClass) {
            return this.setStorageClass(storageClass);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setMediaLink(final String mediaLink) {
            return this.setMediaLink(mediaLink);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setCrc32cFromHexString(final String crc32cFromHexString) {
            return this.setCrc32cFromHexString(crc32cFromHexString);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setCrc32c(final String crc32c) {
            return this.setCrc32c(crc32c);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setMd5FromHexString(final String md5FromHexString) {
            return this.setMd5FromHexString(md5FromHexString);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setMd5(final String md5) {
            return this.setMd5(md5);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setSelfLink(final String selfLink) {
            return this.setSelfLink(selfLink);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setEtag(final String etag) {
            return this.setEtag(etag);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setSize(final Long size) {
            return this.setSize(size);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setOwner(final Acl.Entity owner) {
            return this.setOwner(owner);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setAcl(final List acl) {
            return this.setAcl(acl);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setCacheControl(final String cacheControl) {
            return this.setCacheControl(cacheControl);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setComponentCount(final Integer componentCount) {
            return this.setComponentCount(componentCount);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setContentEncoding(final String contentEncoding) {
            return this.setContentEncoding(contentEncoding);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setContentLanguage(final String contentLanguage) {
            return this.setContentLanguage(contentLanguage);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setContentDisposition(final String contentDisposition) {
            return this.setContentDisposition(contentDisposition);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setContentType(final String contentType) {
            return this.setContentType(contentType);
        }
        
        @Override
        /* bridge */ BlobInfo.Builder setGeneratedId(final String generatedId) {
            return this.setGeneratedId(generatedId);
        }
        
        @Override
        public /* bridge */ BlobInfo.Builder setBlobId(final BlobId blobId) {
            return this.setBlobId(blobId);
        }
    }
}
