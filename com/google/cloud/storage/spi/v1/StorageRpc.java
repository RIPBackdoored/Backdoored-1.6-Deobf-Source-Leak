package com.google.cloud.storage.spi.v1;

import com.google.api.core.*;
import com.google.cloud.*;
import java.io.*;
import com.google.api.services.storage.model.*;
import java.util.*;

@InternalApi
public interface StorageRpc extends ServiceRpc
{
    Bucket create(final Bucket p0, final Map<Option, ?> p1);
    
    StorageObject create(final StorageObject p0, final InputStream p1, final Map<Option, ?> p2);
    
    Tuple<String, Iterable<Bucket>> list(final Map<Option, ?> p0);
    
    Tuple<String, Iterable<StorageObject>> list(final String p0, final Map<Option, ?> p1);
    
    Bucket get(final Bucket p0, final Map<Option, ?> p1);
    
    StorageObject get(final StorageObject p0, final Map<Option, ?> p1);
    
    Bucket patch(final Bucket p0, final Map<Option, ?> p1);
    
    StorageObject patch(final StorageObject p0, final Map<Option, ?> p1);
    
    boolean delete(final Bucket p0, final Map<Option, ?> p1);
    
    boolean delete(final StorageObject p0, final Map<Option, ?> p1);
    
    RpcBatch createBatch();
    
    StorageObject compose(final Iterable<StorageObject> p0, final StorageObject p1, final Map<Option, ?> p2);
    
    byte[] load(final StorageObject p0, final Map<Option, ?> p1);
    
    Tuple<String, byte[]> read(final StorageObject p0, final Map<Option, ?> p1, final long p2, final int p3);
    
    long read(final StorageObject p0, final Map<Option, ?> p1, final long p2, final OutputStream p3);
    
    String open(final StorageObject p0, final Map<Option, ?> p1);
    
    String open(final String p0);
    
    void write(final String p0, final byte[] p1, final int p2, final long p3, final int p4, final boolean p5);
    
    RewriteResponse openRewrite(final RewriteRequest p0);
    
    RewriteResponse continueRewrite(final RewriteResponse p0);
    
    BucketAccessControl getAcl(final String p0, final String p1, final Map<Option, ?> p2);
    
    boolean deleteAcl(final String p0, final String p1, final Map<Option, ?> p2);
    
    BucketAccessControl createAcl(final BucketAccessControl p0, final Map<Option, ?> p1);
    
    BucketAccessControl patchAcl(final BucketAccessControl p0, final Map<Option, ?> p1);
    
    List<BucketAccessControl> listAcls(final String p0, final Map<Option, ?> p1);
    
    ObjectAccessControl getDefaultAcl(final String p0, final String p1);
    
    boolean deleteDefaultAcl(final String p0, final String p1);
    
    ObjectAccessControl createDefaultAcl(final ObjectAccessControl p0);
    
    ObjectAccessControl patchDefaultAcl(final ObjectAccessControl p0);
    
    List<ObjectAccessControl> listDefaultAcls(final String p0);
    
    ObjectAccessControl getAcl(final String p0, final String p1, final Long p2, final String p3);
    
    boolean deleteAcl(final String p0, final String p1, final Long p2, final String p3);
    
    ObjectAccessControl createAcl(final ObjectAccessControl p0);
    
    ObjectAccessControl patchAcl(final ObjectAccessControl p0);
    
    List<ObjectAccessControl> listAcls(final String p0, final String p1, final Long p2);
    
    HmacKey createHmacKey(final String p0, final Map<Option, ?> p1);
    
    Tuple<String, Iterable<HmacKeyMetadata>> listHmacKeys(final Map<Option, ?> p0);
    
    HmacKeyMetadata updateHmacKey(final HmacKeyMetadata p0, final Map<Option, ?> p1);
    
    HmacKeyMetadata getHmacKey(final String p0, final Map<Option, ?> p1);
    
    void deleteHmacKey(final HmacKeyMetadata p0, final Map<Option, ?> p1);
    
    Policy getIamPolicy(final String p0, final Map<Option, ?> p1);
    
    Policy setIamPolicy(final String p0, final Policy p1, final Map<Option, ?> p2);
    
    TestIamPermissionsResponse testIamPermissions(final String p0, final List<String> p1, final Map<Option, ?> p2);
    
    boolean deleteNotification(final String p0, final String p1);
    
    List<Notification> listNotifications(final String p0);
    
    Notification createNotification(final String p0, final Notification p1);
    
    Bucket lockRetentionPolicy(final Bucket p0, final Map<Option, ?> p1);
    
    ServiceAccount getServiceAccount(final String p0);
    
    public enum Option
    {
        PREDEFINED_ACL("predefinedAcl"), 
        PREDEFINED_DEFAULT_OBJECT_ACL("predefinedDefaultObjectAcl"), 
        IF_METAGENERATION_MATCH("ifMetagenerationMatch"), 
        IF_METAGENERATION_NOT_MATCH("ifMetagenerationNotMatch"), 
        IF_GENERATION_MATCH("ifGenerationMatch"), 
        IF_GENERATION_NOT_MATCH("ifGenerationNotMatch"), 
        IF_SOURCE_METAGENERATION_MATCH("ifSourceMetagenerationMatch"), 
        IF_SOURCE_METAGENERATION_NOT_MATCH("ifSourceMetagenerationNotMatch"), 
        IF_SOURCE_GENERATION_MATCH("ifSourceGenerationMatch"), 
        IF_SOURCE_GENERATION_NOT_MATCH("ifSourceGenerationNotMatch"), 
        IF_DISABLE_GZIP_CONTENT("disableGzipContent"), 
        PREFIX("prefix"), 
        PROJECT_ID("projectId"), 
        PROJECTION("projection"), 
        MAX_RESULTS("maxResults"), 
        PAGE_TOKEN("pageToken"), 
        DELIMITER("delimiter"), 
        VERSIONS("versions"), 
        FIELDS("fields"), 
        CUSTOMER_SUPPLIED_KEY("customerSuppliedKey"), 
        USER_PROJECT("userProject"), 
        KMS_KEY_NAME("kmsKeyName"), 
        SERVICE_ACCOUNT_EMAIL("serviceAccount"), 
        SHOW_DELETED_KEYS("showDeletedKeys");
        
        private final String value;
        private static final /* synthetic */ Option[] $VALUES;
        
        public static Option[] values() {
            return Option.$VALUES.clone();
        }
        
        public static Option valueOf(final String name) {
            return Enum.valueOf(Option.class, name);
        }
        
        private Option(final String value) {
            this.value = value;
        }
        
        public String value() {
            return this.value;
        }
        
         <T> T get(final Map<Option, ?> options) {
            return (T)options.get(this);
        }
        
        String getString(final Map<Option, ?> options) {
            return this.get(options);
        }
        
        Long getLong(final Map<Option, ?> options) {
            return this.get(options);
        }
        
        Boolean getBoolean(final Map<Option, ?> options) {
            return this.get(options);
        }
        
        static {
            $VALUES = new Option[] { Option.PREDEFINED_ACL, Option.PREDEFINED_DEFAULT_OBJECT_ACL, Option.IF_METAGENERATION_MATCH, Option.IF_METAGENERATION_NOT_MATCH, Option.IF_GENERATION_MATCH, Option.IF_GENERATION_NOT_MATCH, Option.IF_SOURCE_METAGENERATION_MATCH, Option.IF_SOURCE_METAGENERATION_NOT_MATCH, Option.IF_SOURCE_GENERATION_MATCH, Option.IF_SOURCE_GENERATION_NOT_MATCH, Option.IF_DISABLE_GZIP_CONTENT, Option.PREFIX, Option.PROJECT_ID, Option.PROJECTION, Option.MAX_RESULTS, Option.PAGE_TOKEN, Option.DELIMITER, Option.VERSIONS, Option.FIELDS, Option.CUSTOMER_SUPPLIED_KEY, Option.USER_PROJECT, Option.KMS_KEY_NAME, Option.SERVICE_ACCOUNT_EMAIL, Option.SHOW_DELETED_KEYS };
        }
    }
    
    public static class RewriteRequest
    {
        public final StorageObject source;
        public final Map<Option, ?> sourceOptions;
        public final boolean overrideInfo;
        public final StorageObject target;
        public final Map<Option, ?> targetOptions;
        public final Long megabytesRewrittenPerCall;
        
        public RewriteRequest(final StorageObject source, final Map<Option, ?> sourceOptions, final boolean overrideInfo, final StorageObject target, final Map<Option, ?> targetOptions, final Long megabytesRewrittenPerCall) {
            super();
            this.source = source;
            this.sourceOptions = sourceOptions;
            this.overrideInfo = overrideInfo;
            this.target = target;
            this.targetOptions = targetOptions;
            this.megabytesRewrittenPerCall = megabytesRewrittenPerCall;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof RewriteRequest)) {
                return false;
            }
            final RewriteRequest other = (RewriteRequest)obj;
            return Objects.equals(this.source, other.source) && Objects.equals(this.sourceOptions, other.sourceOptions) && Objects.equals(this.overrideInfo, other.overrideInfo) && Objects.equals(this.target, other.target) && Objects.equals(this.targetOptions, other.targetOptions) && Objects.equals(this.megabytesRewrittenPerCall, other.megabytesRewrittenPerCall);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.source, this.sourceOptions, this.overrideInfo, this.target, this.targetOptions, this.megabytesRewrittenPerCall);
        }
    }
    
    public static class RewriteResponse
    {
        public final RewriteRequest rewriteRequest;
        public final StorageObject result;
        public final long blobSize;
        public final boolean isDone;
        public final String rewriteToken;
        public final long totalBytesRewritten;
        
        public RewriteResponse(final RewriteRequest rewriteRequest, final StorageObject result, final long blobSize, final boolean isDone, final String rewriteToken, final long totalBytesRewritten) {
            super();
            this.rewriteRequest = rewriteRequest;
            this.result = result;
            this.blobSize = blobSize;
            this.isDone = isDone;
            this.rewriteToken = rewriteToken;
            this.totalBytesRewritten = totalBytesRewritten;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof RewriteResponse)) {
                return false;
            }
            final RewriteResponse other = (RewriteResponse)obj;
            return Objects.equals(this.rewriteRequest, other.rewriteRequest) && Objects.equals(this.result, other.result) && Objects.equals(this.rewriteToken, other.rewriteToken) && this.blobSize == other.blobSize && Objects.equals(this.isDone, other.isDone) && this.totalBytesRewritten == other.totalBytesRewritten;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.rewriteRequest, this.result, this.blobSize, this.isDone, this.rewriteToken, this.totalBytesRewritten);
        }
    }
}
