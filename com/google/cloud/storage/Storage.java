package com.google.cloud.storage;

import com.google.api.gax.paging.*;
import java.net.*;
import java.util.concurrent.*;
import com.google.cloud.storage.spi.v1.*;
import java.security.*;
import com.google.common.io.*;
import com.google.cloud.*;
import java.io.*;
import com.google.auth.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public interface Storage extends Service<StorageOptions>
{
    Bucket create(final BucketInfo p0, final BucketTargetOption... p1);
    
    Blob create(final BlobInfo p0, final BlobTargetOption... p1);
    
    Blob create(final BlobInfo p0, final byte[] p1, final BlobTargetOption... p2);
    
    Blob create(final BlobInfo p0, final byte[] p1, final int p2, final int p3, final BlobTargetOption... p4);
    
    @Deprecated
    Blob create(final BlobInfo p0, final InputStream p1, final BlobWriteOption... p2);
    
    Bucket get(final String p0, final BucketGetOption... p1);
    
    Bucket lockRetentionPolicy(final BucketInfo p0, final BucketTargetOption... p1);
    
    Blob get(final String p0, final String p1, final BlobGetOption... p2);
    
    Blob get(final BlobId p0, final BlobGetOption... p1);
    
    Blob get(final BlobId p0);
    
    Page<Bucket> list(final BucketListOption... p0);
    
    Page<Blob> list(final String p0, final BlobListOption... p1);
    
    Bucket update(final BucketInfo p0, final BucketTargetOption... p1);
    
    Blob update(final BlobInfo p0, final BlobTargetOption... p1);
    
    Blob update(final BlobInfo p0);
    
    boolean delete(final String p0, final BucketSourceOption... p1);
    
    boolean delete(final String p0, final String p1, final BlobSourceOption... p2);
    
    boolean delete(final BlobId p0, final BlobSourceOption... p1);
    
    boolean delete(final BlobId p0);
    
    Blob compose(final ComposeRequest p0);
    
    CopyWriter copy(final CopyRequest p0);
    
    byte[] readAllBytes(final String p0, final String p1, final BlobSourceOption... p2);
    
    byte[] readAllBytes(final BlobId p0, final BlobSourceOption... p1);
    
    StorageBatch batch();
    
    ReadChannel reader(final String p0, final String p1, final BlobSourceOption... p2);
    
    ReadChannel reader(final BlobId p0, final BlobSourceOption... p1);
    
    WriteChannel writer(final BlobInfo p0, final BlobWriteOption... p1);
    
    WriteChannel writer(final URL p0);
    
    URL signUrl(final BlobInfo p0, final long p1, final TimeUnit p2, final SignUrlOption... p3);
    
    List<Blob> get(final BlobId... p0);
    
    List<Blob> get(final Iterable<BlobId> p0);
    
    List<Blob> update(final BlobInfo... p0);
    
    List<Blob> update(final Iterable<BlobInfo> p0);
    
    List<Boolean> delete(final BlobId... p0);
    
    List<Boolean> delete(final Iterable<BlobId> p0);
    
    Acl getAcl(final String p0, final Acl.Entity p1, final BucketSourceOption... p2);
    
    Acl getAcl(final String p0, final Acl.Entity p1);
    
    boolean deleteAcl(final String p0, final Acl.Entity p1, final BucketSourceOption... p2);
    
    boolean deleteAcl(final String p0, final Acl.Entity p1);
    
    Acl createAcl(final String p0, final Acl p1, final BucketSourceOption... p2);
    
    Acl createAcl(final String p0, final Acl p1);
    
    Acl updateAcl(final String p0, final Acl p1, final BucketSourceOption... p2);
    
    Acl updateAcl(final String p0, final Acl p1);
    
    List<Acl> listAcls(final String p0, final BucketSourceOption... p1);
    
    List<Acl> listAcls(final String p0);
    
    Acl getDefaultAcl(final String p0, final Acl.Entity p1);
    
    boolean deleteDefaultAcl(final String p0, final Acl.Entity p1);
    
    Acl createDefaultAcl(final String p0, final Acl p1);
    
    Acl updateDefaultAcl(final String p0, final Acl p1);
    
    List<Acl> listDefaultAcls(final String p0);
    
    Acl getAcl(final BlobId p0, final Acl.Entity p1);
    
    boolean deleteAcl(final BlobId p0, final Acl.Entity p1);
    
    Acl createAcl(final BlobId p0, final Acl p1);
    
    Acl updateAcl(final BlobId p0, final Acl p1);
    
    List<Acl> listAcls(final BlobId p0);
    
    HmacKey createHmacKey(final ServiceAccount p0, final CreateHmacKeyOption... p1);
    
    Page<HmacKey.HmacKeyMetadata> listHmacKeys(final ListHmacKeysOption... p0);
    
    HmacKey.HmacKeyMetadata getHmacKey(final String p0, final GetHmacKeyOption... p1);
    
    void deleteHmacKey(final HmacKey.HmacKeyMetadata p0, final DeleteHmacKeyOption... p1);
    
    HmacKey.HmacKeyMetadata updateHmacKeyState(final HmacKey.HmacKeyMetadata p0, final HmacKey.HmacKeyState p1, final UpdateHmacKeyOption... p2);
    
    Policy getIamPolicy(final String p0, final BucketSourceOption... p1);
    
    Policy setIamPolicy(final String p0, final Policy p1, final BucketSourceOption... p2);
    
    List<Boolean> testIamPermissions(final String p0, final List<String> p1, final BucketSourceOption... p2);
    
    ServiceAccount getServiceAccount(final String p0);
    
    public enum PredefinedAcl
    {
        AUTHENTICATED_READ("authenticatedRead"), 
        ALL_AUTHENTICATED_USERS("allAuthenticatedUsers"), 
        PRIVATE("private"), 
        PROJECT_PRIVATE("projectPrivate"), 
        PUBLIC_READ("publicRead"), 
        PUBLIC_READ_WRITE("publicReadWrite"), 
        BUCKET_OWNER_READ("bucketOwnerRead"), 
        BUCKET_OWNER_FULL_CONTROL("bucketOwnerFullControl");
        
        private final String entry;
        private static final /* synthetic */ PredefinedAcl[] $VALUES;
        
        public static PredefinedAcl[] values() {
            return PredefinedAcl.$VALUES.clone();
        }
        
        public static PredefinedAcl valueOf(final String name) {
            return Enum.valueOf(PredefinedAcl.class, name);
        }
        
        private PredefinedAcl(final String entry) {
            this.entry = entry;
        }
        
        String getEntry() {
            return this.entry;
        }
        
        static {
            $VALUES = new PredefinedAcl[] { PredefinedAcl.AUTHENTICATED_READ, PredefinedAcl.ALL_AUTHENTICATED_USERS, PredefinedAcl.PRIVATE, PredefinedAcl.PROJECT_PRIVATE, PredefinedAcl.PUBLIC_READ, PredefinedAcl.PUBLIC_READ_WRITE, PredefinedAcl.BUCKET_OWNER_READ, PredefinedAcl.BUCKET_OWNER_FULL_CONTROL };
        }
    }
    
    public enum BucketField implements FieldSelector
    {
        ID("id"), 
        SELF_LINK("selfLink"), 
        NAME("name"), 
        TIME_CREATED("timeCreated"), 
        METAGENERATION("metageneration"), 
        ACL("acl"), 
        DEFAULT_OBJECT_ACL("defaultObjectAcl"), 
        OWNER("owner"), 
        LABELS("labels"), 
        LOCATION("location"), 
        LOCATION_TYPE("locationType"), 
        WEBSITE("website"), 
        VERSIONING("versioning"), 
        CORS("cors"), 
        LIFECYCLE("lifecycle"), 
        STORAGE_CLASS("storageClass"), 
        ETAG("etag"), 
        ENCRYPTION("encryption"), 
        BILLING("billing"), 
        DEFAULT_EVENT_BASED_HOLD("defaultEventBasedHold"), 
        RETENTION_POLICY("retentionPolicy"), 
        IAMCONFIGURATION("iamConfiguration");
        
        static final List<? extends FieldSelector> REQUIRED_FIELDS;
        private final String selector;
        private static final /* synthetic */ BucketField[] $VALUES;
        
        public static BucketField[] values() {
            return BucketField.$VALUES.clone();
        }
        
        public static BucketField valueOf(final String name) {
            return Enum.valueOf(BucketField.class, name);
        }
        
        private BucketField(final String selector) {
            this.selector = selector;
        }
        
        public String getSelector() {
            return this.selector;
        }
        
        static {
            $VALUES = new BucketField[] { BucketField.ID, BucketField.SELF_LINK, BucketField.NAME, BucketField.TIME_CREATED, BucketField.METAGENERATION, BucketField.ACL, BucketField.DEFAULT_OBJECT_ACL, BucketField.OWNER, BucketField.LABELS, BucketField.LOCATION, BucketField.LOCATION_TYPE, BucketField.WEBSITE, BucketField.VERSIONING, BucketField.CORS, BucketField.LIFECYCLE, BucketField.STORAGE_CLASS, BucketField.ETAG, BucketField.ENCRYPTION, BucketField.BILLING, BucketField.DEFAULT_EVENT_BASED_HOLD, BucketField.RETENTION_POLICY, BucketField.IAMCONFIGURATION };
            REQUIRED_FIELDS = ImmutableList.of(BucketField.NAME);
        }
    }
    
    public enum BlobField implements FieldSelector
    {
        ACL("acl"), 
        BUCKET("bucket"), 
        CACHE_CONTROL("cacheControl"), 
        COMPONENT_COUNT("componentCount"), 
        CONTENT_DISPOSITION("contentDisposition"), 
        CONTENT_ENCODING("contentEncoding"), 
        CONTENT_LANGUAGE("contentLanguage"), 
        CONTENT_TYPE("contentType"), 
        CRC32C("crc32c"), 
        ETAG("etag"), 
        GENERATION("generation"), 
        ID("id"), 
        KIND("kind"), 
        MD5HASH("md5Hash"), 
        MEDIA_LINK("mediaLink"), 
        METADATA("metadata"), 
        METAGENERATION("metageneration"), 
        NAME("name"), 
        OWNER("owner"), 
        SELF_LINK("selfLink"), 
        SIZE("size"), 
        STORAGE_CLASS("storageClass"), 
        TIME_DELETED("timeDeleted"), 
        TIME_CREATED("timeCreated"), 
        KMS_KEY_NAME("kmsKeyName"), 
        EVENT_BASED_HOLD("eventBasedHold"), 
        TEMPORARY_HOLD("temporaryHold"), 
        RETENTION_EXPIRATION_TIME("retentionExpirationTime"), 
        UPDATED("updated");
        
        static final List<? extends FieldSelector> REQUIRED_FIELDS;
        private final String selector;
        private static final /* synthetic */ BlobField[] $VALUES;
        
        public static BlobField[] values() {
            return BlobField.$VALUES.clone();
        }
        
        public static BlobField valueOf(final String name) {
            return Enum.valueOf(BlobField.class, name);
        }
        
        private BlobField(final String selector) {
            this.selector = selector;
        }
        
        public String getSelector() {
            return this.selector;
        }
        
        static {
            $VALUES = new BlobField[] { BlobField.ACL, BlobField.BUCKET, BlobField.CACHE_CONTROL, BlobField.COMPONENT_COUNT, BlobField.CONTENT_DISPOSITION, BlobField.CONTENT_ENCODING, BlobField.CONTENT_LANGUAGE, BlobField.CONTENT_TYPE, BlobField.CRC32C, BlobField.ETAG, BlobField.GENERATION, BlobField.ID, BlobField.KIND, BlobField.MD5HASH, BlobField.MEDIA_LINK, BlobField.METADATA, BlobField.METAGENERATION, BlobField.NAME, BlobField.OWNER, BlobField.SELF_LINK, BlobField.SIZE, BlobField.STORAGE_CLASS, BlobField.TIME_DELETED, BlobField.TIME_CREATED, BlobField.KMS_KEY_NAME, BlobField.EVENT_BASED_HOLD, BlobField.TEMPORARY_HOLD, BlobField.RETENTION_EXPIRATION_TIME, BlobField.UPDATED };
            REQUIRED_FIELDS = ImmutableList.of(BlobField.BUCKET, BlobField.NAME);
        }
    }
    
    public static class BucketTargetOption extends Option
    {
        private static final long serialVersionUID = -5880204616982900975L;
        
        private BucketTargetOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        private BucketTargetOption(final StorageRpc.Option rpcOption) {
            this(rpcOption, null);
        }
        
        public static BucketTargetOption predefinedAcl(final PredefinedAcl acl) {
            return new BucketTargetOption(StorageRpc.Option.PREDEFINED_ACL, acl.getEntry());
        }
        
        public static BucketTargetOption predefinedDefaultObjectAcl(final PredefinedAcl acl) {
            return new BucketTargetOption(StorageRpc.Option.PREDEFINED_DEFAULT_OBJECT_ACL, acl.getEntry());
        }
        
        public static BucketTargetOption metagenerationMatch() {
            return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_MATCH);
        }
        
        public static BucketTargetOption metagenerationNotMatch() {
            return new BucketTargetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH);
        }
        
        public static BucketTargetOption userProject(final String userProject) {
            return new BucketTargetOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static BucketTargetOption projection(final String projection) {
            return new BucketTargetOption(StorageRpc.Option.PROJECTION, projection);
        }
    }
    
    public static class BucketSourceOption extends Option
    {
        private static final long serialVersionUID = 5185657617120212117L;
        
        private BucketSourceOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static BucketSourceOption metagenerationMatch(final long metageneration) {
            return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
        }
        
        public static BucketSourceOption metagenerationNotMatch(final long metageneration) {
            return new BucketSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
        }
        
        public static BucketSourceOption userProject(final String userProject) {
            return new BucketSourceOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
    }
    
    public static class ListHmacKeysOption extends Option
    {
        private ListHmacKeysOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static ListHmacKeysOption serviceAccount(final ServiceAccount serviceAccount) {
            return new ListHmacKeysOption(StorageRpc.Option.SERVICE_ACCOUNT_EMAIL, serviceAccount.getEmail());
        }
        
        public static ListHmacKeysOption maxResults(final long pageSize) {
            return new ListHmacKeysOption(StorageRpc.Option.MAX_RESULTS, pageSize);
        }
        
        public static ListHmacKeysOption pageToken(final String pageToken) {
            return new ListHmacKeysOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
        }
        
        public static ListHmacKeysOption showDeletedKeys(final boolean showDeletedKeys) {
            return new ListHmacKeysOption(StorageRpc.Option.SHOW_DELETED_KEYS, showDeletedKeys);
        }
        
        public static ListHmacKeysOption userProject(final String userProject) {
            return new ListHmacKeysOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static ListHmacKeysOption projectId(final String projectId) {
            return new ListHmacKeysOption(StorageRpc.Option.PROJECT_ID, projectId);
        }
    }
    
    public static class CreateHmacKeyOption extends Option
    {
        private CreateHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static CreateHmacKeyOption userProject(final String userProject) {
            return new CreateHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static CreateHmacKeyOption projectId(final String projectId) {
            return new CreateHmacKeyOption(StorageRpc.Option.PROJECT_ID, projectId);
        }
    }
    
    public static class GetHmacKeyOption extends Option
    {
        private GetHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static GetHmacKeyOption userProject(final String userProject) {
            return new GetHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static GetHmacKeyOption projectId(final String projectId) {
            return new GetHmacKeyOption(StorageRpc.Option.PROJECT_ID, projectId);
        }
    }
    
    public static class DeleteHmacKeyOption extends Option
    {
        private DeleteHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static DeleteHmacKeyOption userProject(final String userProject) {
            return new DeleteHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
    }
    
    public static class UpdateHmacKeyOption extends Option
    {
        private UpdateHmacKeyOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static UpdateHmacKeyOption userProject(final String userProject) {
            return new UpdateHmacKeyOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
    }
    
    public static class BucketGetOption extends Option
    {
        private static final long serialVersionUID = 1901844869484087395L;
        
        private BucketGetOption(final StorageRpc.Option rpcOption, final long metageneration) {
            super(rpcOption, metageneration);
        }
        
        private BucketGetOption(final StorageRpc.Option rpcOption, final String value) {
            super(rpcOption, value);
        }
        
        public static BucketGetOption metagenerationMatch(final long metageneration) {
            return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
        }
        
        public static BucketGetOption metagenerationNotMatch(final long metageneration) {
            return new BucketGetOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
        }
        
        public static BucketGetOption userProject(final String userProject) {
            return new BucketGetOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static BucketGetOption fields(final BucketField... fields) {
            return new BucketGetOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.selector((List)BucketField.REQUIRED_FIELDS, (FieldSelector[])fields));
        }
    }
    
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
    
    public static class BlobSourceOption extends Option
    {
        private static final long serialVersionUID = -3712768261070182991L;
        
        private BlobSourceOption(final StorageRpc.Option rpcOption, final Object value) {
            super(rpcOption, value);
        }
        
        public static BlobSourceOption generationMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, null);
        }
        
        public static BlobSourceOption generationMatch(final long generation) {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_MATCH, generation);
        }
        
        public static BlobSourceOption generationNotMatch() {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, null);
        }
        
        public static BlobSourceOption generationNotMatch(final long generation) {
            return new BlobSourceOption(StorageRpc.Option.IF_GENERATION_NOT_MATCH, generation);
        }
        
        public static BlobSourceOption metagenerationMatch(final long metageneration) {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_MATCH, metageneration);
        }
        
        public static BlobSourceOption metagenerationNotMatch(final long metageneration) {
            return new BlobSourceOption(StorageRpc.Option.IF_METAGENERATION_NOT_MATCH, metageneration);
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
    }
    
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
    
    public static class BucketListOption extends Option
    {
        private static final long serialVersionUID = 8754017079673290353L;
        
        private BucketListOption(final StorageRpc.Option option, final Object value) {
            super(option, value);
        }
        
        public static BucketListOption pageSize(final long pageSize) {
            return new BucketListOption(StorageRpc.Option.MAX_RESULTS, pageSize);
        }
        
        public static BucketListOption pageToken(final String pageToken) {
            return new BucketListOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
        }
        
        public static BucketListOption prefix(final String prefix) {
            return new BucketListOption(StorageRpc.Option.PREFIX, prefix);
        }
        
        public static BucketListOption userProject(final String userProject) {
            return new BucketListOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static BucketListOption fields(final BucketField... fields) {
            return new BucketListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector("items", (List)BucketField.REQUIRED_FIELDS, (FieldSelector[])fields));
        }
    }
    
    public static class BlobListOption extends Option
    {
        private static final String[] TOP_LEVEL_FIELDS;
        private static final long serialVersionUID = 9083383524788661294L;
        
        private BlobListOption(final StorageRpc.Option option, final Object value) {
            super(option, value);
        }
        
        public static BlobListOption pageSize(final long pageSize) {
            return new BlobListOption(StorageRpc.Option.MAX_RESULTS, pageSize);
        }
        
        public static BlobListOption pageToken(final String pageToken) {
            return new BlobListOption(StorageRpc.Option.PAGE_TOKEN, pageToken);
        }
        
        public static BlobListOption prefix(final String prefix) {
            return new BlobListOption(StorageRpc.Option.PREFIX, prefix);
        }
        
        public static BlobListOption currentDirectory() {
            return new BlobListOption(StorageRpc.Option.DELIMITER, true);
        }
        
        public static BlobListOption userProject(final String userProject) {
            return new BlobListOption(StorageRpc.Option.USER_PROJECT, userProject);
        }
        
        public static BlobListOption versions(final boolean versions) {
            return new BlobListOption(StorageRpc.Option.VERSIONS, versions);
        }
        
        public static BlobListOption fields(final BlobField... fields) {
            return new BlobListOption(StorageRpc.Option.FIELDS, FieldSelector.Helper.listSelector(BlobListOption.TOP_LEVEL_FIELDS, "items", (List)BlobField.REQUIRED_FIELDS, (FieldSelector[])fields, new String[0]));
        }
        
        static {
            TOP_LEVEL_FIELDS = new String[] { "prefixes" };
        }
    }
    
    public static class SignUrlOption implements Serializable
    {
        private static final long serialVersionUID = 7850569877451099267L;
        private final Option option;
        private final Object value;
        
        private SignUrlOption(final Option option, final Object value) {
            super();
            this.option = option;
            this.value = value;
        }
        
        Option getOption() {
            return this.option;
        }
        
        Object getValue() {
            return this.value;
        }
        
        public static SignUrlOption httpMethod(final HttpMethod httpMethod) {
            return new SignUrlOption(Option.HTTP_METHOD, httpMethod);
        }
        
        public static SignUrlOption withContentType() {
            return new SignUrlOption(Option.CONTENT_TYPE, true);
        }
        
        public static SignUrlOption withMd5() {
            return new SignUrlOption(Option.MD5, true);
        }
        
        public static SignUrlOption withExtHeaders(final Map<String, String> extHeaders) {
            return new SignUrlOption(Option.EXT_HEADERS, extHeaders);
        }
        
        public static SignUrlOption withV2Signature() {
            return new SignUrlOption(Option.SIGNATURE_VERSION, SignatureVersion.V2);
        }
        
        public static SignUrlOption withV4Signature() {
            return new SignUrlOption(Option.SIGNATURE_VERSION, SignatureVersion.V4);
        }
        
        public static SignUrlOption signWith(final ServiceAccountSigner signer) {
            return new SignUrlOption(Option.SERVICE_ACCOUNT_CRED, signer);
        }
        
        public static SignUrlOption withHostName(final String hostName) {
            return new SignUrlOption(Option.HOST_NAME, hostName);
        }
        
        enum Option
        {
            HTTP_METHOD, 
            CONTENT_TYPE, 
            MD5, 
            EXT_HEADERS, 
            SERVICE_ACCOUNT_CRED, 
            SIGNATURE_VERSION, 
            HOST_NAME;
            
            private static final /* synthetic */ Option[] $VALUES;
            
            public static Option[] values() {
                return Option.$VALUES.clone();
            }
            
            public static Option valueOf(final String name) {
                return Enum.valueOf(Option.class, name);
            }
            
            static {
                $VALUES = new Option[] { Option.HTTP_METHOD, Option.CONTENT_TYPE, Option.MD5, Option.EXT_HEADERS, Option.SERVICE_ACCOUNT_CRED, Option.SIGNATURE_VERSION, Option.HOST_NAME };
            }
        }
        
        enum SignatureVersion
        {
            V2, 
            V4;
            
            private static final /* synthetic */ SignatureVersion[] $VALUES;
            
            public static SignatureVersion[] values() {
                return SignatureVersion.$VALUES.clone();
            }
            
            public static SignatureVersion valueOf(final String name) {
                return Enum.valueOf(SignatureVersion.class, name);
            }
            
            static {
                $VALUES = new SignatureVersion[] { SignatureVersion.V2, SignatureVersion.V4 };
            }
        }
    }
    
    public static class ComposeRequest implements Serializable
    {
        private static final long serialVersionUID = -7385681353748590911L;
        private final List<SourceBlob> sourceBlobs;
        private final BlobInfo target;
        private final List<BlobTargetOption> targetOptions;
        
        private ComposeRequest(final Builder builder) {
            super();
            this.sourceBlobs = (List<SourceBlob>)ImmutableList.copyOf((Collection<?>)builder.sourceBlobs);
            this.target = builder.target;
            this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)builder.targetOptions);
        }
        
        public List<SourceBlob> getSourceBlobs() {
            return this.sourceBlobs;
        }
        
        public BlobInfo getTarget() {
            return this.target;
        }
        
        public List<BlobTargetOption> getTargetOptions() {
            return this.targetOptions;
        }
        
        public static ComposeRequest of(final Iterable<String> sources, final BlobInfo target) {
            return newBuilder().setTarget(target).addSource(sources).build();
        }
        
        public static ComposeRequest of(final String bucket, final Iterable<String> sources, final String target) {
            return of(sources, BlobInfo.newBuilder(BlobId.of(bucket, target)).build());
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        ComposeRequest(final Builder x0, final Storage$1 x1) {
            this(x0);
        }
        
        public static class SourceBlob implements Serializable
        {
            private static final long serialVersionUID = 4094962795951990439L;
            final String name;
            final Long generation;
            
            SourceBlob(final String name) {
                this(name, null);
            }
            
            SourceBlob(final String name, final Long generation) {
                super();
                this.name = name;
                this.generation = generation;
            }
            
            public String getName() {
                return this.name;
            }
            
            public Long getGeneration() {
                return this.generation;
            }
        }
        
        public static class Builder
        {
            private final List<SourceBlob> sourceBlobs;
            private final Set<BlobTargetOption> targetOptions;
            private BlobInfo target;
            
            public Builder() {
                super();
                this.sourceBlobs = new LinkedList<SourceBlob>();
                this.targetOptions = new LinkedHashSet<BlobTargetOption>();
            }
            
            public Builder addSource(final Iterable<String> blobs) {
                for (final String blob : blobs) {
                    this.sourceBlobs.add(new SourceBlob(blob));
                }
                return this;
            }
            
            public Builder addSource(final String... blobs) {
                return this.addSource(Arrays.asList(blobs));
            }
            
            public Builder addSource(final String blob, final long generation) {
                this.sourceBlobs.add(new SourceBlob(blob, generation));
                return this;
            }
            
            public Builder setTarget(final BlobInfo target) {
                this.target = target;
                return this;
            }
            
            public Builder setTargetOptions(final BlobTargetOption... options) {
                Collections.addAll(this.targetOptions, options);
                return this;
            }
            
            public Builder setTargetOptions(final Iterable<BlobTargetOption> options) {
                Iterables.addAll(this.targetOptions, options);
                return this;
            }
            
            public ComposeRequest build() {
                Preconditions.checkArgument(!this.sourceBlobs.isEmpty());
                Preconditions.checkNotNull(this.target);
                return new ComposeRequest(this);
            }
            
            static /* synthetic */ List access$300(final Builder x0) {
                return x0.sourceBlobs;
            }
            
            static /* synthetic */ BlobInfo access$400(final Builder x0) {
                return x0.target;
            }
            
            static /* synthetic */ Set access$500(final Builder x0) {
                return x0.targetOptions;
            }
        }
    }
    
    public static class CopyRequest implements Serializable
    {
        private static final long serialVersionUID = -4498650529476219937L;
        private final BlobId source;
        private final List<BlobSourceOption> sourceOptions;
        private final boolean overrideInfo;
        private final BlobInfo target;
        private final List<BlobTargetOption> targetOptions;
        private final Long megabytesCopiedPerChunk;
        
        private CopyRequest(final Builder builder) {
            super();
            this.source = Preconditions.checkNotNull(builder.source);
            this.sourceOptions = (List<BlobSourceOption>)ImmutableList.copyOf((Collection<?>)builder.sourceOptions);
            this.overrideInfo = builder.overrideInfo;
            this.target = Preconditions.checkNotNull(builder.target);
            this.targetOptions = (List<BlobTargetOption>)ImmutableList.copyOf((Collection<?>)builder.targetOptions);
            this.megabytesCopiedPerChunk = builder.megabytesCopiedPerChunk;
        }
        
        public BlobId getSource() {
            return this.source;
        }
        
        public List<BlobSourceOption> getSourceOptions() {
            return this.sourceOptions;
        }
        
        public BlobInfo getTarget() {
            return this.target;
        }
        
        public boolean overrideInfo() {
            return this.overrideInfo;
        }
        
        public List<BlobTargetOption> getTargetOptions() {
            return this.targetOptions;
        }
        
        public Long getMegabytesCopiedPerChunk() {
            return this.megabytesCopiedPerChunk;
        }
        
        public static CopyRequest of(final String sourceBucket, final String sourceBlob, final BlobInfo target) {
            return newBuilder().setSource(sourceBucket, sourceBlob).setTarget(target, new BlobTargetOption[0]).build();
        }
        
        public static CopyRequest of(final BlobId sourceBlobId, final BlobInfo target) {
            return newBuilder().setSource(sourceBlobId).setTarget(target, new BlobTargetOption[0]).build();
        }
        
        public static CopyRequest of(final String sourceBucket, final String sourceBlob, final String targetBlob) {
            return newBuilder().setSource(sourceBucket, sourceBlob).setTarget(BlobId.of(sourceBucket, targetBlob)).build();
        }
        
        public static CopyRequest of(final String sourceBucket, final String sourceBlob, final BlobId target) {
            return newBuilder().setSource(sourceBucket, sourceBlob).setTarget(target).build();
        }
        
        public static CopyRequest of(final BlobId sourceBlobId, final String targetBlob) {
            return newBuilder().setSource(sourceBlobId).setTarget(BlobId.of(sourceBlobId.getBucket(), targetBlob)).build();
        }
        
        public static CopyRequest of(final BlobId sourceBlobId, final BlobId targetBlobId) {
            return newBuilder().setSource(sourceBlobId).setTarget(targetBlobId).build();
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        CopyRequest(final Builder x0, final Storage$1 x1) {
            this(x0);
        }
        
        public static class Builder
        {
            private final Set<BlobSourceOption> sourceOptions;
            private final Set<BlobTargetOption> targetOptions;
            private BlobId source;
            private boolean overrideInfo;
            private BlobInfo target;
            private Long megabytesCopiedPerChunk;
            
            public Builder() {
                super();
                this.sourceOptions = new LinkedHashSet<BlobSourceOption>();
                this.targetOptions = new LinkedHashSet<BlobTargetOption>();
            }
            
            public Builder setSource(final String bucket, final String blob) {
                this.source = BlobId.of(bucket, blob);
                return this;
            }
            
            public Builder setSource(final BlobId source) {
                this.source = source;
                return this;
            }
            
            public Builder setSourceOptions(final BlobSourceOption... options) {
                Collections.addAll(this.sourceOptions, options);
                return this;
            }
            
            public Builder setSourceOptions(final Iterable<BlobSourceOption> options) {
                Iterables.addAll(this.sourceOptions, options);
                return this;
            }
            
            public Builder setTarget(final BlobId targetId) {
                this.overrideInfo = false;
                this.target = BlobInfo.newBuilder(targetId).build();
                return this;
            }
            
            public Builder setTarget(final BlobId targetId, final BlobTargetOption... options) {
                this.overrideInfo = false;
                this.target = BlobInfo.newBuilder(targetId).build();
                Collections.addAll(this.targetOptions, options);
                return this;
            }
            
            public Builder setTarget(final BlobInfo target, final BlobTargetOption... options) {
                this.overrideInfo = true;
                this.target = Preconditions.checkNotNull(target);
                Collections.addAll(this.targetOptions, options);
                return this;
            }
            
            public Builder setTarget(final BlobInfo target, final Iterable<BlobTargetOption> options) {
                this.overrideInfo = true;
                this.target = Preconditions.checkNotNull(target);
                Iterables.addAll(this.targetOptions, options);
                return this;
            }
            
            public Builder setTarget(final BlobId targetId, final Iterable<BlobTargetOption> options) {
                this.overrideInfo = false;
                this.target = BlobInfo.newBuilder(targetId).build();
                Iterables.addAll(this.targetOptions, options);
                return this;
            }
            
            public Builder setMegabytesCopiedPerChunk(final Long megabytesCopiedPerChunk) {
                this.megabytesCopiedPerChunk = megabytesCopiedPerChunk;
                return this;
            }
            
            public CopyRequest build() {
                return new CopyRequest(this);
            }
            
            static /* synthetic */ BlobId access$700(final Builder x0) {
                return x0.source;
            }
            
            static /* synthetic */ Set access$800(final Builder x0) {
                return x0.sourceOptions;
            }
            
            static /* synthetic */ boolean access$900(final Builder x0) {
                return x0.overrideInfo;
            }
            
            static /* synthetic */ BlobInfo access$1000(final Builder x0) {
                return x0.target;
            }
            
            static /* synthetic */ Set access$1100(final Builder x0) {
                return x0.targetOptions;
            }
            
            static /* synthetic */ Long access$1200(final Builder x0) {
                return x0.megabytesCopiedPerChunk;
            }
        }
    }
}
