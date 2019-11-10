package com.google.cloud.storage;

import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.retrying.*;
import com.google.common.io.*;
import com.google.common.hash.*;
import com.google.common.primitives.*;
import com.google.api.gax.paging.*;
import java.util.concurrent.*;
import com.google.auth.*;
import com.google.common.base.*;
import com.google.common.net.*;
import java.nio.charset.*;
import java.net.*;
import java.io.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.cloud.*;

final class StorageImpl extends BaseService<StorageOptions> implements Storage
{
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final String EMPTY_BYTE_ARRAY_MD5 = "1B2M2Y8AsgTpgAmY7PhCfg==";
    private static final String EMPTY_BYTE_ARRAY_CRC32C = "AAAAAA==";
    private static final String PATH_DELIMITER = "/";
    private static final String STORAGE_XML_HOST_NAME = "https://storage.googleapis.com";
    private static final Function<Tuple<Storage, Boolean>, Boolean> DELETE_FUNCTION;
    private final StorageRpc storageRpc;
    
    StorageImpl(final StorageOptions options) {
        super((ServiceOptions)options);
        this.storageRpc = options.getStorageRpcV1();
    }
    
    public Bucket create(final BucketInfo bucketInfo, final BucketTargetOption... options) {
        final com.google.api.services.storage.model.Bucket bucketPb = bucketInfo.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(bucketInfo, (Option[])options);
        try {
            return Bucket.fromPb(this, (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$2() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.create(bucketPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Blob create(final BlobInfo blobInfo, final BlobTargetOption... options) {
        final BlobInfo updatedInfo = blobInfo.toBuilder().setMd5("1B2M2Y8AsgTpgAmY7PhCfg==").setCrc32c("AAAAAA==").build();
        return this.internalCreate(updatedInfo, StorageImpl.EMPTY_BYTE_ARRAY, options);
    }
    
    public Blob create(final BlobInfo blobInfo, byte[] content, final BlobTargetOption... options) {
        content = MoreObjects.firstNonNull(content, StorageImpl.EMPTY_BYTE_ARRAY);
        final BlobInfo updatedInfo = blobInfo.toBuilder().setMd5(BaseEncoding.base64().encode(Hashing.md5().hashBytes(content).asBytes())).setCrc32c(BaseEncoding.base64().encode(Ints.toByteArray(Hashing.crc32c().hashBytes(content).asInt()))).build();
        return this.internalCreate(updatedInfo, content, options);
    }
    
    public Blob create(final BlobInfo blobInfo, byte[] content, final int offset, final int length, final BlobTargetOption... options) {
        content = MoreObjects.firstNonNull(content, StorageImpl.EMPTY_BYTE_ARRAY);
        final byte[] subContent = Arrays.copyOfRange(content, offset, offset + length);
        final BlobInfo updatedInfo = blobInfo.toBuilder().setMd5(BaseEncoding.base64().encode(Hashing.md5().hashBytes(subContent).asBytes())).setCrc32c(BaseEncoding.base64().encode(Ints.toByteArray(Hashing.crc32c().hashBytes(subContent).asInt()))).build();
        return this.internalCreate(updatedInfo, subContent, options);
    }
    
    @Deprecated
    public Blob create(final BlobInfo blobInfo, final InputStream content, final BlobWriteOption... options) {
        final Tuple<BlobInfo, BlobTargetOption[]> targetOptions = BlobTargetOption.convert(blobInfo, options);
        final StorageObject blobPb = ((BlobInfo)targetOptions.x()).toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap((BlobInfo)targetOptions.x(), (Option[])targetOptions.y());
        final InputStream inputStreamParam = MoreObjects.firstNonNull(content, new ByteArrayInputStream(StorageImpl.EMPTY_BYTE_ARRAY));
        return Blob.fromPb(this, this.storageRpc.create(blobPb, inputStreamParam, optionsMap));
    }
    
    private Blob internalCreate(final BlobInfo info, final byte[] content, final BlobTargetOption... options) {
        Preconditions.checkNotNull(content);
        final StorageObject blobPb = info.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(info, (Option[])options);
        try {
            return Blob.fromPb(this, (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ StorageObject val$blobPb;
                final /* synthetic */ byte[] val$content;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$3() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.create(blobPb, new ByteArrayInputStream(content), optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Bucket get(final String bucket, final BucketGetOption... options) {
        final com.google.api.services.storage.model.Bucket bucketPb = BucketInfo.of(bucket).toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
        try {
            final com.google.api.services.storage.model.Bucket answer = (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$4() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.get(bucketPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (answer == null) ? null : Bucket.fromPb(this, answer);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Blob get(final String bucket, final String blob, final BlobGetOption... options) {
        return this.get(BlobId.of(bucket, blob), options);
    }
    
    public Blob get(final BlobId blob, final BlobGetOption... options) {
        final StorageObject storedObject = blob.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(blob, (Option[])options);
        try {
            final StorageObject storageObject = (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ StorageObject val$storedObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$5() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.get(storedObject, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (storageObject == null) ? null : Blob.fromPb(this, storageObject);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Blob get(final BlobId blob) {
        return this.get(blob, new BlobGetOption[0]);
    }
    
    public Page<Bucket> list(final BucketListOption... options) {
        return listBuckets((StorageOptions)this.getOptions(), optionMap((Option[])options));
    }
    
    public Page<Blob> list(final String bucket, final BlobListOption... options) {
        return listBlobs(bucket, (StorageOptions)this.getOptions(), optionMap((Option[])options));
    }
    
    private static Page<Bucket> listBuckets(final StorageOptions serviceOptions, final Map<StorageRpc.Option, ?> optionsMap) {
        try {
            final Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>> result = (Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>>>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                final /* synthetic */ Map val$optionsMap;
                
                StorageImpl$6() {
                    super();
                }
                
                @Override
                public Tuple<String, Iterable<com.google.api.services.storage.model.Bucket>> call() {
                    return serviceOptions.getStorageRpcV1().list(optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, serviceOptions.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, serviceOptions.getClock());
            final String cursor = (String)result.x();
            final Iterable<Bucket> buckets = (Iterable<Bucket>)((result.y() == null) ? ImmutableList.of() : Iterables.transform((Iterable<Object>)result.y(), (Function<? super Object, ?>)new Function<com.google.api.services.storage.model.Bucket, Bucket>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                
                StorageImpl$7() {
                    super();
                }
                
                @Override
                public Bucket apply(final com.google.api.services.storage.model.Bucket bucketPb) {
                    return Bucket.fromPb((Storage)serviceOptions.getService(), bucketPb);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((com.google.api.services.storage.model.Bucket)o);
                }
            }));
            return (Page<Bucket>)new PageImpl((PageImpl.NextPageFetcher)new BucketPageFetcher(serviceOptions, cursor, optionsMap), cursor, (Iterable)buckets);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    private static Page<Blob> listBlobs(final String bucket, final StorageOptions serviceOptions, final Map<StorageRpc.Option, ?> optionsMap) {
        try {
            final Tuple<String, Iterable<StorageObject>> result = (Tuple<String, Iterable<StorageObject>>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, Iterable<StorageObject>>>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Map val$optionsMap;
                
                StorageImpl$8() {
                    super();
                }
                
                @Override
                public Tuple<String, Iterable<StorageObject>> call() {
                    return serviceOptions.getStorageRpcV1().list(bucket, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, serviceOptions.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, serviceOptions.getClock());
            final String cursor = (String)result.x();
            final Iterable<Blob> blobs = (Iterable<Blob>)((result.y() == null) ? ImmutableList.of() : Iterables.transform((Iterable<Object>)result.y(), (Function<? super Object, ?>)new Function<StorageObject, Blob>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                
                StorageImpl$9() {
                    super();
                }
                
                @Override
                public Blob apply(final StorageObject storageObject) {
                    return Blob.fromPb((Storage)serviceOptions.getService(), storageObject);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((StorageObject)o);
                }
            }));
            return (Page<Blob>)new PageImpl((PageImpl.NextPageFetcher)new BlobPageFetcher(bucket, serviceOptions, cursor, optionsMap), cursor, (Iterable)blobs);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Bucket update(final BucketInfo bucketInfo, final BucketTargetOption... options) {
        final com.google.api.services.storage.model.Bucket bucketPb = bucketInfo.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(bucketInfo, (Option[])options);
        try {
            return Bucket.fromPb(this, (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$10() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.patch(bucketPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Blob update(final BlobInfo blobInfo, final BlobTargetOption... options) {
        final StorageObject storageObject = blobInfo.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(blobInfo, (Option[])options);
        try {
            return Blob.fromPb(this, (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ StorageObject val$storageObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$11() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.patch(storageObject, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Blob update(final BlobInfo blobInfo) {
        return this.update(blobInfo, new BlobTargetOption[0]);
    }
    
    public boolean delete(final String bucket, final BucketSourceOption... options) {
        final com.google.api.services.storage.model.Bucket bucketPb = BucketInfo.of(bucket).toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$12() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.delete(bucketPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public boolean delete(final String bucket, final String blob, final BlobSourceOption... options) {
        return this.delete(BlobId.of(bucket, blob), options);
    }
    
    public boolean delete(final BlobId blob, final BlobSourceOption... options) {
        final StorageObject storageObject = blob.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(blob, (Option[])options);
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ StorageObject val$storageObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$13() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.delete(storageObject, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public boolean delete(final BlobId blob) {
        return this.delete(blob, new BlobSourceOption[0]);
    }
    
    public Blob compose(final ComposeRequest composeRequest) {
        final List<StorageObject> sources = (List<StorageObject>)Lists.newArrayListWithCapacity(composeRequest.getSourceBlobs().size());
        for (final ComposeRequest.SourceBlob sourceBlob : composeRequest.getSourceBlobs()) {
            sources.add(BlobInfo.newBuilder(BlobId.of(composeRequest.getTarget().getBucket(), sourceBlob.getName(), sourceBlob.getGeneration())).build().toPb());
        }
        final StorageObject target = composeRequest.getTarget().toPb();
        final Map<StorageRpc.Option, ?> targetOptions = optionMap(composeRequest.getTarget().getGeneration(), composeRequest.getTarget().getMetageneration(), composeRequest.getTargetOptions());
        try {
            return Blob.fromPb(this, (StorageObject)RetryHelper.runWithRetries((Callable)new Callable<StorageObject>() {
                final /* synthetic */ List val$sources;
                final /* synthetic */ StorageObject val$target;
                final /* synthetic */ Map val$targetOptions;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$14() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public StorageObject call() {
                    return this.this$0.storageRpc.compose(sources, target, targetOptions);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public CopyWriter copy(final CopyRequest copyRequest) {
        final StorageObject source = copyRequest.getSource().toPb();
        final Map<StorageRpc.Option, ?> sourceOptions = optionMap(copyRequest.getSource().getGeneration(), null, copyRequest.getSourceOptions(), true);
        final StorageObject targetObject = copyRequest.getTarget().toPb();
        final Map<StorageRpc.Option, ?> targetOptions = optionMap(copyRequest.getTarget().getGeneration(), copyRequest.getTarget().getMetageneration(), copyRequest.getTargetOptions());
        try {
            final StorageRpc.RewriteResponse rewriteResponse = (StorageRpc.RewriteResponse)RetryHelper.runWithRetries((Callable)new Callable<StorageRpc.RewriteResponse>() {
                final /* synthetic */ StorageObject val$source;
                final /* synthetic */ Map val$sourceOptions;
                final /* synthetic */ CopyRequest val$copyRequest;
                final /* synthetic */ StorageObject val$targetObject;
                final /* synthetic */ Map val$targetOptions;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$15() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public StorageRpc.RewriteResponse call() {
                    return this.this$0.storageRpc.openRewrite(new StorageRpc.RewriteRequest(source, sourceOptions, copyRequest.overrideInfo(), targetObject, targetOptions, copyRequest.getMegabytesCopiedPerChunk()));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return new CopyWriter((StorageOptions)this.getOptions(), rewriteResponse);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public byte[] readAllBytes(final String bucket, final String blob, final BlobSourceOption... options) {
        return this.readAllBytes(BlobId.of(bucket, blob), options);
    }
    
    public byte[] readAllBytes(final BlobId blob, final BlobSourceOption... options) {
        final StorageObject storageObject = blob.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(blob, (Option[])options);
        try {
            return (byte[])RetryHelper.runWithRetries((Callable)new Callable<byte[]>() {
                final /* synthetic */ StorageObject val$storageObject;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$16() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public byte[] call() {
                    return this.this$0.storageRpc.load(storageObject, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public StorageBatch batch() {
        return new StorageBatch((StorageOptions)this.getOptions());
    }
    
    public ReadChannel reader(final String bucket, final String blob, final BlobSourceOption... options) {
        final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
        return (ReadChannel)new BlobReadChannel((StorageOptions)this.getOptions(), BlobId.of(bucket, blob), optionsMap);
    }
    
    public ReadChannel reader(final BlobId blob, final BlobSourceOption... options) {
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(blob, (Option[])options);
        return (ReadChannel)new BlobReadChannel((StorageOptions)this.getOptions(), blob, optionsMap);
    }
    
    public BlobWriteChannel writer(final BlobInfo blobInfo, final BlobWriteOption... options) {
        final Tuple<BlobInfo, BlobTargetOption[]> targetOptions = BlobTargetOption.convert(blobInfo, options);
        return this.writer((BlobInfo)targetOptions.x(), (BlobTargetOption[])targetOptions.y());
    }
    
    public BlobWriteChannel writer(final URL signedURL) {
        return new BlobWriteChannel((StorageOptions)this.getOptions(), signedURL);
    }
    
    private BlobWriteChannel writer(final BlobInfo blobInfo, final BlobTargetOption... options) {
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(blobInfo, (Option[])options);
        return new BlobWriteChannel((StorageOptions)this.getOptions(), blobInfo, optionsMap);
    }
    
    public URL signUrl(final BlobInfo blobInfo, final long duration, final TimeUnit unit, final SignUrlOption... options) {
        final EnumMap<SignUrlOption.Option, Object> optionMap = (EnumMap<SignUrlOption.Option, Object>)Maps.newEnumMap((Class)SignUrlOption.Option.class);
        for (final SignUrlOption option : options) {
            optionMap.put(option.getOption(), option.getValue());
        }
        final boolean isV4 = SignUrlOption.SignatureVersion.V4.equals(optionMap.get(SignUrlOption.Option.SIGNATURE_VERSION));
        ServiceAccountSigner credentials = optionMap.get(SignUrlOption.Option.SERVICE_ACCOUNT_CRED);
        if (credentials == null) {
            Preconditions.checkState(((StorageOptions)this.getOptions()).getCredentials() instanceof ServiceAccountSigner, "Signing key was not provided and could not be derived");
            credentials = (ServiceAccountSigner)((StorageOptions)this.getOptions()).getCredentials();
        }
        final long expiration = isV4 ? TimeUnit.SECONDS.convert(unit.toMillis(duration), TimeUnit.MILLISECONDS) : TimeUnit.SECONDS.convert(((StorageOptions)this.getOptions()).getClock().millisTime() + unit.toMillis(duration), TimeUnit.MILLISECONDS);
        final String storageXmlHostName = (optionMap.get(SignUrlOption.Option.HOST_NAME) != null) ? optionMap.get(SignUrlOption.Option.HOST_NAME) : "https://storage.googleapis.com";
        final String bucketName = CharMatcher.anyOf((CharSequence)"/").trimFrom((CharSequence)blobInfo.getBucket());
        String escapedBlobName = "";
        if (!Strings.isNullOrEmpty(blobInfo.getName())) {
            escapedBlobName = UrlEscapers.urlFragmentEscaper().escape(blobInfo.getName()).replace("?", "%3F").replace(";", "%3B");
        }
        final String stPath = this.constructResourceUriPath(bucketName, escapedBlobName);
        final URI path = URI.create(stPath);
        try {
            final SignatureInfo signatureInfo = this.buildSignatureInfo(optionMap, blobInfo, expiration, path, credentials.getAccount());
            final String unsignedPayload = signatureInfo.constructUnsignedPayload();
            final byte[] signatureBytes = credentials.sign(unsignedPayload.getBytes(StandardCharsets.UTF_8));
            final StringBuilder stBuilder = new StringBuilder();
            stBuilder.append(storageXmlHostName).append(path);
            if (isV4) {
                final BaseEncoding encoding = BaseEncoding.base16().lowerCase();
                final String signature = URLEncoder.encode(encoding.encode(signatureBytes), StandardCharsets.UTF_8.name());
                stBuilder.append("?");
                stBuilder.append(signatureInfo.constructV4QueryString());
                stBuilder.append("&X-Goog-Signature=").append(signature);
            }
            else {
                final BaseEncoding encoding = BaseEncoding.base64();
                final String signature = URLEncoder.encode(encoding.encode(signatureBytes), StandardCharsets.UTF_8.name());
                stBuilder.append("?");
                stBuilder.append("GoogleAccessId=").append(credentials.getAccount());
                stBuilder.append("&Expires=").append(expiration);
                stBuilder.append("&Signature=").append(signature);
            }
            return new URL(stBuilder.toString());
        }
        catch (MalformedURLException | UnsupportedEncodingException ex3) {
            final IOException ex2;
            final IOException ex = ex2;
            throw new IllegalStateException(ex);
        }
    }
    
    private String constructResourceUriPath(final String slashlessBucketName, final String escapedBlobName) {
        final StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/").append(slashlessBucketName);
        if (Strings.isNullOrEmpty(escapedBlobName)) {
            return pathBuilder.toString();
        }
        if (!escapedBlobName.startsWith("/")) {
            pathBuilder.append("/");
        }
        pathBuilder.append(escapedBlobName);
        return pathBuilder.toString();
    }
    
    private SignatureInfo buildSignatureInfo(final Map<SignUrlOption.Option, Object> optionMap, final BlobInfo blobInfo, final long expiration, final URI path, final String accountEmail) {
        final HttpMethod httpVerb = optionMap.containsKey(SignUrlOption.Option.HTTP_METHOD) ? optionMap.get(SignUrlOption.Option.HTTP_METHOD) : HttpMethod.GET;
        final SignatureInfo.Builder signatureInfoBuilder = new SignatureInfo.Builder(httpVerb, expiration, path);
        if (MoreObjects.firstNonNull(optionMap.get(SignUrlOption.Option.MD5), false)) {
            Preconditions.checkArgument(blobInfo.getMd5() != null, (Object)"Blob is missing a value for md5");
            signatureInfoBuilder.setContentMd5(blobInfo.getMd5());
        }
        if (MoreObjects.firstNonNull(optionMap.get(SignUrlOption.Option.CONTENT_TYPE), false)) {
            Preconditions.checkArgument(blobInfo.getContentType() != null, (Object)"Blob is missing a value for content-type");
            signatureInfoBuilder.setContentType(blobInfo.getContentType());
        }
        signatureInfoBuilder.setSignatureVersion(optionMap.get(SignUrlOption.Option.SIGNATURE_VERSION));
        signatureInfoBuilder.setAccountEmail(accountEmail);
        signatureInfoBuilder.setTimestamp(((StorageOptions)this.getOptions()).getClock().millisTime());
        final Map<String, String> extHeaders = optionMap.containsKey(SignUrlOption.Option.EXT_HEADERS) ? optionMap.get(SignUrlOption.Option.EXT_HEADERS) : Collections.emptyMap();
        return signatureInfoBuilder.setCanonicalizedExtensionHeaders(extHeaders).build();
    }
    
    public List<Blob> get(final BlobId... blobIds) {
        return this.get(Arrays.asList(blobIds));
    }
    
    public List<Blob> get(final Iterable<BlobId> blobIds) {
        final StorageBatch batch = this.batch();
        final List<Blob> results = (List<Blob>)Lists.newArrayList();
        for (final BlobId blob : blobIds) {
            batch.get(blob, new BlobGetOption[0]).notify((BatchResult.Callback)new BatchResult.Callback<Blob, StorageException>() {
                final /* synthetic */ List val$results;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$17() {
                    this.this$0 = this$0;
                    super();
                }
                
                public void success(final Blob result) {
                    results.add(result);
                }
                
                public void error(final StorageException exception) {
                    results.add(null);
                }
                
                public /* bridge */ void error(final Object o) {
                    this.error((StorageException)o);
                }
                
                public /* bridge */ void success(final Object o) {
                    this.success((Blob)o);
                }
            });
        }
        batch.submit();
        return Collections.unmodifiableList((List<? extends Blob>)results);
    }
    
    public List<Blob> update(final BlobInfo... blobInfos) {
        return this.update(Arrays.asList(blobInfos));
    }
    
    public List<Blob> update(final Iterable<BlobInfo> blobInfos) {
        final StorageBatch batch = this.batch();
        final List<Blob> results = (List<Blob>)Lists.newArrayList();
        for (final BlobInfo blobInfo : blobInfos) {
            batch.update(blobInfo, new BlobTargetOption[0]).notify((BatchResult.Callback)new BatchResult.Callback<Blob, StorageException>() {
                final /* synthetic */ List val$results;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$18() {
                    this.this$0 = this$0;
                    super();
                }
                
                public void success(final Blob result) {
                    results.add(result);
                }
                
                public void error(final StorageException exception) {
                    results.add(null);
                }
                
                public /* bridge */ void error(final Object o) {
                    this.error((StorageException)o);
                }
                
                public /* bridge */ void success(final Object o) {
                    this.success((Blob)o);
                }
            });
        }
        batch.submit();
        return Collections.unmodifiableList((List<? extends Blob>)results);
    }
    
    public List<Boolean> delete(final BlobId... blobIds) {
        return this.delete(Arrays.asList(blobIds));
    }
    
    public List<Boolean> delete(final Iterable<BlobId> blobIds) {
        final StorageBatch batch = this.batch();
        final List<Boolean> results = (List<Boolean>)Lists.newArrayList();
        for (final BlobId blob : blobIds) {
            batch.delete(blob, new BlobSourceOption[0]).notify((BatchResult.Callback)new BatchResult.Callback<Boolean, StorageException>() {
                final /* synthetic */ List val$results;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$19() {
                    this.this$0 = this$0;
                    super();
                }
                
                public void success(final Boolean result) {
                    results.add(result);
                }
                
                public void error(final StorageException exception) {
                    results.add(Boolean.FALSE);
                }
                
                public /* bridge */ void error(final Object o) {
                    this.error((StorageException)o);
                }
                
                public /* bridge */ void success(final Object o) {
                    this.success((Boolean)o);
                }
            });
        }
        batch.submit();
        return Collections.unmodifiableList((List<? extends Boolean>)results);
    }
    
    public Acl getAcl(final String bucket, final Acl.Entity entity, final BucketSourceOption... options) {
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            final BucketAccessControl answer = (BucketAccessControl)RetryHelper.runWithRetries((Callable)new Callable<BucketAccessControl>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$20() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public BucketAccessControl call() {
                    return this.this$0.storageRpc.getAcl(bucket, entity.toPb(), optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (answer == null) ? null : Acl.fromPb(answer);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl getAcl(final String bucket, final Acl.Entity entity) {
        return this.getAcl(bucket, entity, new BucketSourceOption[0]);
    }
    
    public boolean deleteAcl(final String bucket, final Acl.Entity entity, final BucketSourceOption... options) {
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$21() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.deleteAcl(bucket, entity.toPb(), optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public boolean deleteAcl(final String bucket, final Acl.Entity entity) {
        return this.deleteAcl(bucket, entity, new BucketSourceOption[0]);
    }
    
    public Acl createAcl(final String bucket, final Acl acl, final BucketSourceOption... options) {
        final BucketAccessControl aclPb = acl.toBucketPb().setBucket(bucket);
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            return Acl.fromPb((BucketAccessControl)RetryHelper.runWithRetries((Callable)new Callable<BucketAccessControl>() {
                final /* synthetic */ BucketAccessControl val$aclPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$22() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public BucketAccessControl call() {
                    return this.this$0.storageRpc.createAcl(aclPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl createAcl(final String bucket, final Acl acl) {
        return this.createAcl(bucket, acl, new BucketSourceOption[0]);
    }
    
    public Acl updateAcl(final String bucket, final Acl acl, final BucketSourceOption... options) {
        final BucketAccessControl aclPb = acl.toBucketPb().setBucket(bucket);
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            return Acl.fromPb((BucketAccessControl)RetryHelper.runWithRetries((Callable)new Callable<BucketAccessControl>() {
                final /* synthetic */ BucketAccessControl val$aclPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$23() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public BucketAccessControl call() {
                    return this.this$0.storageRpc.patchAcl(aclPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl updateAcl(final String bucket, final Acl acl) {
        return this.updateAcl(bucket, acl, new BucketSourceOption[0]);
    }
    
    public List<Acl> listAcls(final String bucket, final BucketSourceOption... options) {
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            final List<BucketAccessControl> answer = (List<BucketAccessControl>)RetryHelper.runWithRetries((Callable)new Callable<List<BucketAccessControl>>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$24() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public List<BucketAccessControl> call() {
                    return this.this$0.storageRpc.listAcls(bucket, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return Lists.transform(answer, (Function<? super BucketAccessControl, ? extends Acl>)Acl.FROM_BUCKET_PB_FUNCTION);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public List<Acl> listAcls(final String bucket) {
        return this.listAcls(bucket, new BucketSourceOption[0]);
    }
    
    public Acl getDefaultAcl(final String bucket, final Acl.Entity entity) {
        try {
            final ObjectAccessControl answer = (ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$25() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.getDefaultAcl(bucket, entity.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (answer == null) ? null : Acl.fromPb(answer);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public boolean deleteDefaultAcl(final String bucket, final Acl.Entity entity) {
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$26() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.deleteDefaultAcl(bucket, entity.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl createDefaultAcl(final String bucket, final Acl acl) {
        final ObjectAccessControl aclPb = acl.toObjectPb().setBucket(bucket);
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$27() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.createDefaultAcl(aclPb);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl updateDefaultAcl(final String bucket, final Acl acl) {
        final ObjectAccessControl aclPb = acl.toObjectPb().setBucket(bucket);
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$28() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.patchDefaultAcl(aclPb);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public List<Acl> listDefaultAcls(final String bucket) {
        try {
            final List<ObjectAccessControl> answer = (List<ObjectAccessControl>)RetryHelper.runWithRetries((Callable)new Callable<List<ObjectAccessControl>>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$29() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public List<ObjectAccessControl> call() {
                    return this.this$0.storageRpc.listDefaultAcls(bucket);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return Lists.transform(answer, (Function<? super ObjectAccessControl, ? extends Acl>)Acl.FROM_OBJECT_PB_FUNCTION);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl getAcl(final BlobId blob, final Acl.Entity entity) {
        try {
            final ObjectAccessControl answer = (ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ BlobId val$blob;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$30() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.getAcl(blob.getBucket(), blob.getName(), blob.getGeneration(), entity.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (answer == null) ? null : Acl.fromPb(answer);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public boolean deleteAcl(final BlobId blob, final Acl.Entity entity) {
        try {
            return (boolean)RetryHelper.runWithRetries((Callable)new Callable<Boolean>() {
                final /* synthetic */ BlobId val$blob;
                final /* synthetic */ Acl.Entity val$entity;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$31() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Boolean call() {
                    return this.this$0.storageRpc.deleteAcl(blob.getBucket(), blob.getName(), blob.getGeneration(), entity.toPb());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl createAcl(final BlobId blob, final Acl acl) {
        final ObjectAccessControl aclPb = acl.toObjectPb().setBucket(blob.getBucket()).setObject(blob.getName()).setGeneration(blob.getGeneration());
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$32() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.createAcl(aclPb);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Acl updateAcl(final BlobId blob, final Acl acl) {
        final ObjectAccessControl aclPb = acl.toObjectPb().setBucket(blob.getBucket()).setObject(blob.getName()).setGeneration(blob.getGeneration());
        try {
            return Acl.fromPb((ObjectAccessControl)RetryHelper.runWithRetries((Callable)new Callable<ObjectAccessControl>() {
                final /* synthetic */ ObjectAccessControl val$aclPb;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$33() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public ObjectAccessControl call() {
                    return this.this$0.storageRpc.patchAcl(aclPb);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public List<Acl> listAcls(final BlobId blob) {
        try {
            final List<ObjectAccessControl> answer = (List<ObjectAccessControl>)RetryHelper.runWithRetries((Callable)new Callable<List<ObjectAccessControl>>() {
                final /* synthetic */ BlobId val$blob;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$34() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public List<ObjectAccessControl> call() {
                    return this.this$0.storageRpc.listAcls(blob.getBucket(), blob.getName(), blob.getGeneration());
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return Lists.transform(answer, (Function<? super ObjectAccessControl, ? extends Acl>)Acl.FROM_OBJECT_PB_FUNCTION);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public HmacKey createHmacKey(final ServiceAccount serviceAccount, final CreateHmacKeyOption... options) {
        try {
            return HmacKey.fromPb((com.google.api.services.storage.model.HmacKey)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.HmacKey>() {
                final /* synthetic */ ServiceAccount val$serviceAccount;
                final /* synthetic */ CreateHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$35() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.HmacKey call() {
                    return this.this$0.storageRpc.createHmacKey(serviceAccount.getEmail(), optionMap(options));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Page<HmacKey.HmacKeyMetadata> listHmacKeys(final ListHmacKeysOption... options) {
        return listHmacKeys((StorageOptions)this.getOptions(), optionMap((Option[])options));
    }
    
    public HmacKey.HmacKeyMetadata getHmacKey(final String accessId, final GetHmacKeyOption... options) {
        try {
            return HmacKey.HmacKeyMetadata.fromPb((HmacKeyMetadata)RetryHelper.runWithRetries((Callable)new Callable<HmacKeyMetadata>() {
                final /* synthetic */ String val$accessId;
                final /* synthetic */ GetHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$36() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public HmacKeyMetadata call() {
                    return this.this$0.storageRpc.getHmacKey(accessId, optionMap(options));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    private HmacKey.HmacKeyMetadata updateHmacKey(final HmacKey.HmacKeyMetadata hmacKeyMetadata, final UpdateHmacKeyOption... options) {
        try {
            return HmacKey.HmacKeyMetadata.fromPb((HmacKeyMetadata)RetryHelper.runWithRetries((Callable)new Callable<HmacKeyMetadata>() {
                final /* synthetic */ HmacKey.HmacKeyMetadata val$hmacKeyMetadata;
                final /* synthetic */ UpdateHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$37() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public HmacKeyMetadata call() {
                    return this.this$0.storageRpc.updateHmacKey(hmacKeyMetadata.toPb(), optionMap(options));
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public HmacKey.HmacKeyMetadata updateHmacKeyState(final HmacKey.HmacKeyMetadata hmacKeyMetadata, final HmacKey.HmacKeyState state, final UpdateHmacKeyOption... options) {
        final HmacKey.HmacKeyMetadata updatedMetadata = HmacKey.HmacKeyMetadata.newBuilder(hmacKeyMetadata.getServiceAccount()).setProjectId(hmacKeyMetadata.getProjectId()).setAccessId(hmacKeyMetadata.getAccessId()).setState(state).build();
        return this.updateHmacKey(updatedMetadata, options);
    }
    
    public void deleteHmacKey(final HmacKey.HmacKeyMetadata metadata, final DeleteHmacKeyOption... options) {
        try {
            RetryHelper.runWithRetries((Callable)new Callable<Void>() {
                final /* synthetic */ HmacKey.HmacKeyMetadata val$metadata;
                final /* synthetic */ DeleteHmacKeyOption[] val$options;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$38() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Void call() {
                    this.this$0.storageRpc.deleteHmacKey(metadata.toPb(), optionMap(options));
                    return null;
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    private static Page<HmacKey.HmacKeyMetadata> listHmacKeys(final StorageOptions serviceOptions, final Map<StorageRpc.Option, ?> options) {
        try {
            final Tuple<String, Iterable<HmacKeyMetadata>> result = (Tuple<String, Iterable<HmacKeyMetadata>>)RetryHelper.runWithRetries((Callable)new Callable<Tuple<String, Iterable<HmacKeyMetadata>>>() {
                final /* synthetic */ StorageOptions val$serviceOptions;
                final /* synthetic */ Map val$options;
                
                StorageImpl$39() {
                    super();
                }
                
                @Override
                public Tuple<String, Iterable<HmacKeyMetadata>> call() {
                    return serviceOptions.getStorageRpcV1().listHmacKeys(options);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, serviceOptions.getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, serviceOptions.getClock());
            final String cursor = (String)result.x();
            final Iterable<HmacKey.HmacKeyMetadata> metadata = (Iterable<HmacKey.HmacKeyMetadata>)((result.y() == null) ? ImmutableList.of() : Iterables.transform((Iterable<Object>)result.y(), (Function<? super Object, ?>)new Function<HmacKeyMetadata, HmacKey.HmacKeyMetadata>() {
                StorageImpl$40() {
                    super();
                }
                
                @Override
                public HmacKey.HmacKeyMetadata apply(final HmacKeyMetadata metadataPb) {
                    return HmacKey.HmacKeyMetadata.fromPb(metadataPb);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((HmacKeyMetadata)o);
                }
            }));
            return (Page<HmacKey.HmacKeyMetadata>)new PageImpl((PageImpl.NextPageFetcher)new HmacKeyMetadataPageFetcher(serviceOptions, options), cursor, (Iterable)metadata);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Policy getIamPolicy(final String bucket, final BucketSourceOption... options) {
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            return PolicyHelper.convertFromApiPolicy((com.google.api.services.storage.model.Policy)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Policy>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$41() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Policy call() {
                    return this.this$0.storageRpc.getIamPolicy(bucket, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Policy setIamPolicy(final String bucket, final Policy policy, final BucketSourceOption... options) {
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            return PolicyHelper.convertFromApiPolicy((com.google.api.services.storage.model.Policy)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Policy>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ Policy val$policy;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$42() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Policy call() {
                    return this.this$0.storageRpc.setIamPolicy(bucket, PolicyHelper.convertToApiPolicy(policy), optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public List<Boolean> testIamPermissions(final String bucket, final List<String> permissions, final BucketSourceOption... options) {
        try {
            final Map<StorageRpc.Option, ?> optionsMap = optionMap((Option[])options);
            final TestIamPermissionsResponse response = (TestIamPermissionsResponse)RetryHelper.runWithRetries((Callable)new Callable<TestIamPermissionsResponse>() {
                final /* synthetic */ String val$bucket;
                final /* synthetic */ List val$permissions;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$43() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public TestIamPermissionsResponse call() {
                    return this.this$0.storageRpc.testIamPermissions(bucket, permissions, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            final Set<String> heldPermissions = (Set<String>)((response.getPermissions() != null) ? ImmutableSet.copyOf((Collection<?>)response.getPermissions()) : ImmutableSet.of());
            return Lists.transform(permissions, (Function<? super String, ? extends Boolean>)new Function<String, Boolean>() {
                final /* synthetic */ Set val$heldPermissions;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$44() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public Boolean apply(final String permission) {
                    return heldPermissions.contains(permission);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            });
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public Bucket lockRetentionPolicy(final BucketInfo bucketInfo, final BucketTargetOption... options) {
        final com.google.api.services.storage.model.Bucket bucketPb = bucketInfo.toPb();
        final Map<StorageRpc.Option, ?> optionsMap = optionMap(bucketInfo, (Option[])options);
        try {
            return Bucket.fromPb(this, (com.google.api.services.storage.model.Bucket)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.Bucket>() {
                final /* synthetic */ com.google.api.services.storage.model.Bucket val$bucketPb;
                final /* synthetic */ Map val$optionsMap;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$45() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.Bucket call() {
                    return this.this$0.storageRpc.lockRetentionPolicy(bucketPb, optionsMap);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock()));
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    public ServiceAccount getServiceAccount(final String projectId) {
        try {
            final com.google.api.services.storage.model.ServiceAccount answer = (com.google.api.services.storage.model.ServiceAccount)RetryHelper.runWithRetries((Callable)new Callable<com.google.api.services.storage.model.ServiceAccount>() {
                final /* synthetic */ String val$projectId;
                final /* synthetic */ StorageImpl this$0;
                
                StorageImpl$46() {
                    this.this$0 = this$0;
                    super();
                }
                
                @Override
                public com.google.api.services.storage.model.ServiceAccount call() {
                    return this.this$0.storageRpc.getServiceAccount(projectId);
                }
                
                @Override
                public /* bridge */ Object call() throws Exception {
                    return this.call();
                }
            }, ((StorageOptions)this.getOptions()).getRetrySettings(), (ResultRetryAlgorithm)StorageImpl.EXCEPTION_HANDLER, ((StorageOptions)this.getOptions()).getClock());
            return (answer == null) ? null : ServiceAccount.fromPb(answer);
        }
        catch (RetryHelper.RetryHelperException e) {
            throw StorageException.translateAndThrow(e);
        }
    }
    
    private static <T> void addToOptionMap(final StorageRpc.Option option, final T defaultValue, final Map<StorageRpc.Option, Object> map) {
        addToOptionMap(option, option, defaultValue, map);
    }
    
    private static <T> void addToOptionMap(final StorageRpc.Option getOption, final StorageRpc.Option putOption, final T defaultValue, final Map<StorageRpc.Option, Object> map) {
        if (map.containsKey(getOption)) {
            T value = (T)map.remove(getOption);
            Preconditions.checkArgument(value != null || defaultValue != null, (Object)("Option " + getOption.value() + " is missing a value"));
            value = MoreObjects.firstNonNull(value, defaultValue);
            map.put(putOption, value);
        }
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Long generation, final Long metaGeneration, final Iterable<? extends Option> options) {
        return optionMap(generation, metaGeneration, options, false);
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Long generation, final Long metaGeneration, final Iterable<? extends Option> options, final boolean useAsSource) {
        final Map<StorageRpc.Option, Object> temp = (Map<StorageRpc.Option, Object>)Maps.newEnumMap((Class)StorageRpc.Option.class);
        for (final Option option : options) {
            final Object prev = temp.put(option.getRpcOption(), option.getValue());
            Preconditions.checkArgument(prev == null, "Duplicate option %s", (Object)option);
        }
        final Boolean value = temp.remove(StorageRpc.Option.DELIMITER);
        if (Boolean.TRUE.equals(value)) {
            temp.put(StorageRpc.Option.DELIMITER, "/");
        }
        if (useAsSource) {
            addToOptionMap(StorageRpc.Option.IF_GENERATION_MATCH, StorageRpc.Option.IF_SOURCE_GENERATION_MATCH, generation, temp);
            addToOptionMap(StorageRpc.Option.IF_GENERATION_NOT_MATCH, StorageRpc.Option.IF_SOURCE_GENERATION_NOT_MATCH, generation, temp);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_MATCH, StorageRpc.Option.IF_SOURCE_METAGENERATION_MATCH, metaGeneration, temp);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, StorageRpc.Option.IF_SOURCE_METAGENERATION_NOT_MATCH, metaGeneration, temp);
        }
        else {
            addToOptionMap(StorageRpc.Option.IF_GENERATION_MATCH, generation, temp);
            addToOptionMap(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation, temp);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_MATCH, metaGeneration, temp);
            addToOptionMap(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metaGeneration, temp);
        }
        return (Map<StorageRpc.Option, ?>)ImmutableMap.copyOf((Map)temp);
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Option... options) {
        return optionMap(null, null, Arrays.asList(options));
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final Long generation, final Long metaGeneration, final Option... options) {
        return optionMap(generation, metaGeneration, Arrays.asList(options));
    }
    
    private static Map<StorageRpc.Option, ?> optionMap(final BucketInfo bucketInfo, final Option... options) {
        return optionMap(null, bucketInfo.getMetageneration(), options);
    }
    
    static Map<StorageRpc.Option, ?> optionMap(final BlobInfo blobInfo, final Option... options) {
        return optionMap(blobInfo.getGeneration(), blobInfo.getMetageneration(), options);
    }
    
    static Map<StorageRpc.Option, ?> optionMap(final BlobId blobId, final Option... options) {
        return optionMap(blobId.getGeneration(), null, options);
    }
    
    public /* bridge */ WriteChannel writer(final URL signedURL) {
        return (WriteChannel)this.writer(signedURL);
    }
    
    public /* bridge */ WriteChannel writer(final BlobInfo blobInfo, final BlobWriteOption[] options) {
        return (WriteChannel)this.writer(blobInfo, options);
    }
    
    static /* synthetic */ StorageRpc access$000(final StorageImpl x0) {
        return x0.storageRpc;
    }
    
    static /* synthetic */ Page access$100(final StorageOptions x0, final Map x1) {
        return listBuckets(x0, x1);
    }
    
    static /* synthetic */ Page access$200(final String x0, final StorageOptions x1, final Map x2) {
        return listBlobs(x0, x1, x2);
    }
    
    static /* synthetic */ Page access$300(final StorageOptions x0, final Map x1) {
        return listHmacKeys(x0, x1);
    }
    
    static /* synthetic */ Map access$400(final Option[] x0) {
        return optionMap(x0);
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        DELETE_FUNCTION = new Function<Tuple<Storage, Boolean>, Boolean>() {
            StorageImpl$1() {
                super();
            }
            
            @Override
            public Boolean apply(final Tuple<Storage, Boolean> tuple) {
                return (Boolean)tuple.y();
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((Tuple<Storage, Boolean>)o);
            }
        };
    }
    
    private static class BucketPageFetcher implements PageImpl.NextPageFetcher<Bucket>
    {
        private static final long serialVersionUID = 5850406828803613729L;
        private final Map<StorageRpc.Option, ?> requestOptions;
        private final StorageOptions serviceOptions;
        
        BucketPageFetcher(final StorageOptions serviceOptions, final String cursor, final Map<StorageRpc.Option, ?> optionMap) {
            super();
            this.requestOptions = (Map<StorageRpc.Option, ?>)PageImpl.nextRequestOptions((Object)StorageRpc.Option.PAGE_TOKEN, cursor, (Map)optionMap);
            this.serviceOptions = serviceOptions;
        }
        
        public Page<Bucket> getNextPage() {
            return listBuckets(this.serviceOptions, this.requestOptions);
        }
    }
    
    private static class BlobPageFetcher implements PageImpl.NextPageFetcher<Blob>
    {
        private static final long serialVersionUID = 81807334445874098L;
        private final Map<StorageRpc.Option, ?> requestOptions;
        private final StorageOptions serviceOptions;
        private final String bucket;
        
        BlobPageFetcher(final String bucket, final StorageOptions serviceOptions, final String cursor, final Map<StorageRpc.Option, ?> optionMap) {
            super();
            this.requestOptions = (Map<StorageRpc.Option, ?>)PageImpl.nextRequestOptions((Object)StorageRpc.Option.PAGE_TOKEN, cursor, (Map)optionMap);
            this.serviceOptions = serviceOptions;
            this.bucket = bucket;
        }
        
        public Page<Blob> getNextPage() {
            return listBlobs(this.bucket, this.serviceOptions, this.requestOptions);
        }
    }
    
    private static class HmacKeyMetadataPageFetcher implements PageImpl.NextPageFetcher<HmacKey.HmacKeyMetadata>
    {
        private static final long serialVersionUID = 308012320541700881L;
        private final StorageOptions serviceOptions;
        private final Map<StorageRpc.Option, ?> options;
        
        HmacKeyMetadataPageFetcher(final StorageOptions serviceOptions, final Map<StorageRpc.Option, ?> options) {
            super();
            this.serviceOptions = serviceOptions;
            this.options = options;
        }
        
        public Page<HmacKey.HmacKeyMetadata> getNextPage() {
            return listHmacKeys(this.serviceOptions, this.options);
        }
    }
}
