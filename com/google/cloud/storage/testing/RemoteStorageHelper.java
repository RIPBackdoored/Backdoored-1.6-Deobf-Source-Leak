package com.google.cloud.storage.testing;

import com.google.api.gax.paging.*;
import java.util.concurrent.*;
import com.google.auth.oauth2.*;
import com.google.auth.*;
import com.google.cloud.*;
import java.util.logging.*;
import java.io.*;
import com.google.cloud.http.*;
import com.google.api.gax.retrying.*;
import org.threeten.bp.*;
import com.google.common.base.*;
import com.google.cloud.storage.*;
import java.util.*;

public class RemoteStorageHelper
{
    private static final Logger log;
    private static final String BUCKET_NAME_PREFIX = "gcloud-test-bucket-temp-";
    private final StorageOptions options;
    
    private RemoteStorageHelper(final StorageOptions options) {
        super();
        this.options = options;
    }
    
    public StorageOptions getOptions() {
        return this.options;
    }
    
    public static void cleanBuckets(final Storage storage, final long olderThan, final long timeoutMs) {
        final Runnable task = new Runnable() {
            final /* synthetic */ Storage val$storage;
            final /* synthetic */ long val$olderThan;
            
            RemoteStorageHelper$1() {
                super();
            }
            
            @Override
            public void run() {
                final Page<Bucket> buckets = storage.list(Storage.BucketListOption.prefix("gcloud-test-bucket-temp-"));
                for (final Bucket bucket : buckets.iterateAll()) {
                    if (bucket.getCreateTime() < olderThan) {
                        try {
                            for (final Blob blob : bucket.list(Storage.BlobListOption.fields(Storage.BlobField.EVENT_BASED_HOLD, Storage.BlobField.TEMPORARY_HOLD)).iterateAll()) {
                                if (blob.getEventBasedHold() || blob.getTemporaryHold()) {
                                    storage.update(blob.toBuilder().setTemporaryHold(false).setEventBasedHold(false).build());
                                }
                            }
                            RemoteStorageHelper.forceDelete(storage, bucket.getName());
                        }
                        catch (Exception ex) {}
                    }
                }
            }
        };
        final Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join(timeoutMs);
        }
        catch (InterruptedException e) {
            RemoteStorageHelper.log.info("cleanBuckets interrupted");
        }
    }
    
    public static Boolean forceDelete(final Storage storage, final String bucket, final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException {
        return forceDelete(storage, bucket, timeout, unit, "");
    }
    
    public static Boolean forceDelete(final Storage storage, final String bucket, final long timeout, final TimeUnit unit, final String userProject) throws InterruptedException, ExecutionException {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Boolean> future = executor.submit((Callable<Boolean>)new DeleteBucketTask(storage, bucket, userProject));
        try {
            return future.get(timeout, unit);
        }
        catch (TimeoutException ex) {
            return false;
        }
        finally {
            executor.shutdown();
        }
    }
    
    public static void forceDelete(final Storage storage, final String bucket) {
        new DeleteBucketTask(storage, bucket).call();
    }
    
    public static String generateBucketName() {
        return "gcloud-test-bucket-temp-" + UUID.randomUUID().toString();
    }
    
    public static RemoteStorageHelper create(final String projectId, final InputStream keyStream) throws StorageHelperException {
        try {
            HttpTransportOptions transportOptions = StorageOptions.getDefaultHttpTransportOptions();
            transportOptions = transportOptions.toBuilder().setConnectTimeout(60000).setReadTimeout(60000).build();
            final StorageOptions storageOptions = ((StorageOptions.Builder)((StorageOptions.Builder)((StorageOptions.Builder)StorageOptions.newBuilder().setCredentials((Credentials)GoogleCredentials.fromStream(keyStream))).setProjectId(projectId)).setRetrySettings(retrySettings())).setTransportOptions((TransportOptions)transportOptions).build();
            return new RemoteStorageHelper(storageOptions);
        }
        catch (IOException ex) {
            if (RemoteStorageHelper.log.isLoggable(Level.WARNING)) {
                RemoteStorageHelper.log.log(Level.WARNING, ex.getMessage());
            }
            throw StorageHelperException.translate(ex);
        }
    }
    
    public static RemoteStorageHelper create() throws StorageHelperException {
        HttpTransportOptions transportOptions = StorageOptions.getDefaultHttpTransportOptions();
        transportOptions = transportOptions.toBuilder().setConnectTimeout(60000).setReadTimeout(60000).build();
        final StorageOptions storageOptions = ((StorageOptions.Builder)StorageOptions.newBuilder().setRetrySettings(retrySettings())).setTransportOptions((TransportOptions)transportOptions).build();
        return new RemoteStorageHelper(storageOptions);
    }
    
    private static RetrySettings retrySettings() {
        return RetrySettings.newBuilder().setMaxAttempts(10).setMaxRetryDelay(Duration.ofMillis(30000L)).setTotalTimeout(Duration.ofMillis(120000L)).setInitialRetryDelay(Duration.ofMillis(250L)).setRetryDelayMultiplier(1.0).setInitialRpcTimeout(Duration.ofMillis(120000L)).setRpcTimeoutMultiplier(1.0).setMaxRpcTimeout(Duration.ofMillis(120000L)).build();
    }
    
    static {
        log = Logger.getLogger(RemoteStorageHelper.class.getName());
    }
    
    private static class DeleteBucketTask implements Callable<Boolean>
    {
        private final Storage storage;
        private final String bucket;
        private final String userProject;
        
        public DeleteBucketTask(final Storage storage, final String bucket) {
            super();
            this.storage = storage;
            this.bucket = bucket;
            this.userProject = "";
        }
        
        public DeleteBucketTask(final Storage storage, final String bucket, final String userProject) {
            super();
            this.storage = storage;
            this.bucket = bucket;
            this.userProject = userProject;
        }
        
        @Override
        public Boolean call() {
            while (true) {
                final ArrayList<BlobId> ids = new ArrayList<BlobId>();
                Page<Blob> listedBlobs;
                if (Strings.isNullOrEmpty(this.userProject)) {
                    listedBlobs = this.storage.list(this.bucket, Storage.BlobListOption.versions(true));
                }
                else {
                    listedBlobs = this.storage.list(this.bucket, Storage.BlobListOption.versions(true), Storage.BlobListOption.userProject(this.userProject));
                }
                for (final BlobInfo info : listedBlobs.getValues()) {
                    ids.add(info.getBlobId());
                }
                if (!ids.isEmpty()) {
                    final List<Boolean> results = this.storage.delete(ids);
                    if (!Strings.isNullOrEmpty(this.userProject)) {
                        for (int i = 0; i < results.size(); ++i) {
                            if (!results.get(i)) {
                                this.storage.delete(this.bucket, ids.get(i).getName(), Storage.BlobSourceOption.userProject(this.userProject));
                            }
                        }
                    }
                }
                try {
                    if (Strings.isNullOrEmpty(this.userProject)) {
                        this.storage.delete(this.bucket, new Storage.BucketSourceOption[0]);
                    }
                    else {
                        this.storage.delete(this.bucket, Storage.BucketSourceOption.userProject(this.userProject));
                    }
                    return true;
                }
                catch (StorageException e) {
                    if (e.getCode() == 409) {
                        try {
                            Thread.sleep(500L);
                            continue;
                        }
                        catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                            throw e;
                        }
                        throw e;
                        continue;
                    }
                    throw e;
                }
            }
        }
        
        @Override
        public /* bridge */ Object call() throws Exception {
            return this.call();
        }
    }
    
    public static class StorageHelperException extends RuntimeException
    {
        private static final long serialVersionUID = -7756074894502258736L;
        
        public StorageHelperException(final String message) {
            super(message);
        }
        
        public StorageHelperException(final String message, final Throwable cause) {
            super(message, cause);
        }
        
        public static StorageHelperException translate(final Exception ex) {
            return new StorageHelperException(ex.getMessage(), ex);
        }
    }
}
