package com.google.cloud.storage.spi.v1;

import com.google.api.services.storage.*;
import com.google.cloud.http.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.json.*;
import com.google.api.client.googleapis.batch.json.*;
import com.google.api.client.googleapis.json.*;
import com.google.cloud.storage.*;
import com.google.common.io.*;
import com.google.common.hash.*;
import io.opencensus.common.*;
import com.google.cloud.*;
import com.google.common.collect.*;
import java.math.*;
import java.io.*;
import com.google.common.base.*;
import com.google.api.client.http.json.*;
import com.google.api.client.http.*;
import com.google.api.services.storage.model.*;
import java.util.*;
import com.google.api.client.googleapis.batch.*;
import io.opencensus.trace.*;

public class HttpStorageRpc implements StorageRpc
{
    public static final String DEFAULT_PROJECTION = "full";
    public static final String NO_ACL_PROJECTION = "noAcl";
    private static final String ENCRYPTION_KEY_PREFIX = "x-goog-encryption-";
    private static final String SOURCE_ENCRYPTION_KEY_PREFIX = "x-goog-copy-source-encryption-";
    private static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private final StorageOptions options;
    private final Storage storage;
    private final Tracer tracer;
    private final CensusHttpModule censusHttpModule;
    private final HttpRequestInitializer batchRequestInitializer;
    private static final long MEGABYTE = 1048576L;
    
    public HttpStorageRpc(final StorageOptions options) {
        super();
        this.tracer = Tracing.getTracer();
        final HttpTransportOptions transportOptions = (HttpTransportOptions)options.getTransportOptions();
        final HttpTransport transport = transportOptions.getHttpTransportFactory().create();
        HttpRequestInitializer initializer = transportOptions.getHttpRequestInitializer((ServiceOptions)options);
        this.options = options;
        this.censusHttpModule = new CensusHttpModule(this.tracer, true);
        initializer = this.censusHttpModule.getHttpRequestInitializer(initializer);
        this.batchRequestInitializer = this.censusHttpModule.getHttpRequestInitializer((HttpRequestInitializer)null);
        HttpStorageRpcSpans.registerAllSpanNamesForCollection();
        this.storage = new Storage.Builder(transport, (JsonFactory)new JacksonFactory(), initializer).setRootUrl(options.getHost()).setApplicationName(options.getApplicationName()).build();
    }
    
    private static <T> JsonBatchCallback<T> toJsonCallback(final RpcBatch.Callback<T> callback) {
        return new JsonBatchCallback<T>() {
            final /* synthetic */ RpcBatch.Callback val$callback;
            
            HttpStorageRpc$1() {
                super();
            }
            
            @Override
            public void onSuccess(final T response, final HttpHeaders httpHeaders) throws IOException {
                callback.onSuccess(response);
            }
            
            @Override
            public void onFailure(final GoogleJsonError googleJsonError, final HttpHeaders httpHeaders) throws IOException {
                callback.onFailure(googleJsonError);
            }
        };
    }
    
    private static StorageException translate(final IOException exception) {
        return new StorageException(exception);
    }
    
    private static StorageException translate(final GoogleJsonError exception) {
        return new StorageException(exception);
    }
    
    private static void setEncryptionHeaders(final HttpHeaders headers, final String headerPrefix, final Map<Option, ?> options) {
        final String key = Option.CUSTOMER_SUPPLIED_KEY.getString(options);
        if (key != null) {
            final BaseEncoding base64 = BaseEncoding.base64();
            final HashFunction hashFunction = Hashing.sha256();
            headers.set(headerPrefix + "algorithm", "AES256");
            headers.set(headerPrefix + "key", key);
            headers.set(headerPrefix + "key-sha256", base64.encode(hashFunction.hashBytes(base64.decode((CharSequence)key)).asBytes()));
        }
    }
    
    private Span startSpan(final String spanName) {
        return this.tracer.spanBuilder(spanName).setRecordEvents(this.censusHttpModule.isRecordEvents()).startSpan();
    }
    
    @Override
    public Bucket create(final Bucket bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_BUCKET);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (Bucket)this.storage.buckets().insert(this.options.getProjectId(), bucket).setProjection("full").setPredefinedAcl(Option.PREDEFINED_ACL.getString(options)).setPredefinedDefaultObjectAcl(Option.PREDEFINED_DEFAULT_OBJECT_ACL.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public StorageObject create(final StorageObject storageObject, final InputStream content, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final Storage.Objects.Insert insert = this.storage.objects().insert(storageObject.getBucket(), storageObject, (AbstractInputStreamContent)new InputStreamContent(storageObject.getContentType(), content));
            insert.getMediaHttpUploader().setDirectUploadEnabled(true);
            final Boolean disableGzipContent = Option.IF_DISABLE_GZIP_CONTENT.getBoolean(options);
            if (disableGzipContent != null) {
                insert.setDisableGZipContent((boolean)disableGzipContent);
            }
            setEncryptionHeaders(insert.getRequestHeaders(), "x-goog-encryption-", options);
            return (StorageObject)insert.setProjection("full").setPredefinedAcl(Option.PREDEFINED_ACL.getString(options)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(options)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options)).setKmsKeyName(Option.KMS_KEY_NAME.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Tuple<String, Iterable<Bucket>> list(final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_BUCKETS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final Buckets buckets = (Buckets)this.storage.buckets().list(this.options.getProjectId()).setProjection("full").setPrefix(Option.PREFIX.getString(options)).setMaxResults(Option.MAX_RESULTS.getLong(options)).setPageToken(Option.PAGE_TOKEN.getString(options)).setFields(Option.FIELDS.getString(options)).setUserProject(Option.USER_PROJECT.getString(options)).execute();
            return (Tuple<String, Iterable<Bucket>>)Tuple.of((Object)buckets.getNextPageToken(), (Object)buckets.getItems());
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Tuple<String, Iterable<StorageObject>> list(final String bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECTS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final Objects objects = (Objects)this.storage.objects().list(bucket).setProjection("full").setVersions(Option.VERSIONS.getBoolean(options)).setDelimiter(Option.DELIMITER.getString(options)).setPrefix(Option.PREFIX.getString(options)).setMaxResults(Option.MAX_RESULTS.getLong(options)).setPageToken(Option.PAGE_TOKEN.getString(options)).setFields(Option.FIELDS.getString(options)).setUserProject(Option.USER_PROJECT.getString(options)).execute();
            final Iterable<StorageObject> storageObjects = Iterables.concat((Iterable<? extends StorageObject>)MoreObjects.firstNonNull(objects.getItems(), ImmutableList.of()), (Iterable<? extends StorageObject>)((objects.getPrefixes() != null) ? Lists.transform((List<Object>)objects.getPrefixes(), (Function<? super Object, ?>)objectFromPrefix(bucket)) : ImmutableList.of()));
            return (Tuple<String, Iterable<StorageObject>>)Tuple.of((Object)objects.getNextPageToken(), (Object)storageObjects);
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    private static Function<String, StorageObject> objectFromPrefix(final String bucket) {
        return new Function<String, StorageObject>() {
            final /* synthetic */ String val$bucket;
            
            HttpStorageRpc$2() {
                super();
            }
            
            @Override
            public StorageObject apply(final String prefix) {
                return new StorageObject().set("isDirectory", (Object)true).setBucket(bucket).setName(prefix).setSize(BigInteger.ZERO);
            }
            
            @Override
            public /* bridge */ Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
    }
    
    @Override
    public Bucket get(final Bucket bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (Bucket)this.storage.buckets().get(bucket.getName()).setProjection("full").setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setFields(Option.FIELDS.getString(options)).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return null;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    private Storage.Objects.Get getCall(final StorageObject object, final Map<Option, ?> options) throws IOException {
        final Storage.Objects.Get get = this.storage.objects().get(object.getBucket(), object.getName());
        setEncryptionHeaders(get.getRequestHeaders(), "x-goog-encryption-", options);
        return get.setGeneration(object.getGeneration()).setProjection("full").setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(options)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(options)).setFields(Option.FIELDS.getString(options)).setUserProject(Option.USER_PROJECT.getString(options));
    }
    
    @Override
    public StorageObject get(final StorageObject object, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (StorageObject)this.getCall(object, options).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return null;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Bucket patch(final Bucket bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_BUCKET);
        final Scope scope = this.tracer.withSpan(span);
        try {
            String projection = Option.PROJECTION.getString(options);
            if (bucket.getIamConfiguration() != null && bucket.getIamConfiguration().getBucketPolicyOnly() != null && bucket.getIamConfiguration().getBucketPolicyOnly().getEnabled() != null && bucket.getIamConfiguration().getBucketPolicyOnly().getEnabled()) {
                bucket.setDefaultObjectAcl((List)null);
                bucket.setAcl((List)null);
                if (projection == null) {
                    projection = "noAcl";
                }
            }
            return (Bucket)this.storage.buckets().patch(bucket.getName(), bucket).setProjection((projection == null) ? "full" : projection).setPredefinedAcl(Option.PREDEFINED_ACL.getString(options)).setPredefinedDefaultObjectAcl(Option.PREDEFINED_DEFAULT_OBJECT_ACL.getString(options)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    private Storage.Objects.Patch patchCall(final StorageObject storageObject, final Map<Option, ?> options) throws IOException {
        return this.storage.objects().patch(storageObject.getBucket(), storageObject.getName(), storageObject).setProjection("full").setPredefinedAcl(Option.PREDEFINED_ACL.getString(options)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(options)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options));
    }
    
    @Override
    public StorageObject patch(final StorageObject storageObject, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (StorageObject)this.patchCall(storageObject, options).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public boolean delete(final Bucket bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_BUCKET);
        final Scope scope = this.tracer.withSpan(span);
        try {
            this.storage.buckets().delete(bucket.getName()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options)).execute();
            return true;
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return false;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    private Storage.Objects.Delete deleteCall(final StorageObject blob, final Map<Option, ?> options) throws IOException {
        return this.storage.objects().delete(blob.getBucket(), blob.getName()).setGeneration(blob.getGeneration()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(options)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options));
    }
    
    @Override
    public boolean delete(final StorageObject blob, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT);
        final Scope scope = this.tracer.withSpan(span);
        try {
            this.deleteCall(blob, options).execute();
            return true;
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return false;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public StorageObject compose(final Iterable<StorageObject> sources, final StorageObject target, final Map<Option, ?> targetOptions) {
        final ComposeRequest request = new ComposeRequest();
        request.setDestination(target);
        final List<ComposeRequest.SourceObjects> sourceObjects = new ArrayList<ComposeRequest.SourceObjects>();
        for (final StorageObject source : sources) {
            final ComposeRequest.SourceObjects sourceObject = new ComposeRequest.SourceObjects();
            sourceObject.setName(source.getName());
            final Long generation = source.getGeneration();
            if (generation != null) {
                sourceObject.setGeneration(generation);
                sourceObject.setObjectPreconditions(new ComposeRequest.SourceObjects.ObjectPreconditions().setIfGenerationMatch(generation));
            }
            sourceObjects.add(sourceObject);
        }
        request.setSourceObjects((List)sourceObjects);
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_COMPOSE);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (StorageObject)this.storage.objects().compose(target.getBucket(), target.getName(), request).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(targetOptions)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(targetOptions)).setUserProject(Option.USER_PROJECT.getString(targetOptions)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public byte[] load(final StorageObject from, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LOAD);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final Storage.Objects.Get getRequest = this.storage.objects().get(from.getBucket(), from.getName()).setGeneration(from.getGeneration()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(options)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options));
            setEncryptionHeaders(getRequest.getRequestHeaders(), "x-goog-encryption-", options);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            getRequest.executeMedia().download(out);
            return out.toByteArray();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public RpcBatch createBatch() {
        return new DefaultRpcBatch(this, this.storage);
    }
    
    private Storage.Objects.Get createReadRequest(final StorageObject from, final Map<Option, ?> options) throws IOException {
        final Storage.Objects.Get req = this.storage.objects().get(from.getBucket(), from.getName()).setGeneration(from.getGeneration()).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(options)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(options)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(options)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options));
        setEncryptionHeaders(req.getRequestHeaders(), "x-goog-encryption-", options);
        req.setReturnRawInputStream(true);
        return req;
    }
    
    @Override
    public long read(final StorageObject from, final Map<Option, ?> options, final long position, final OutputStream outputStream) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_READ);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final Storage.Objects.Get req = this.createReadRequest(from, options);
            req.getMediaHttpDownloader().setBytesDownloaded(position);
            req.getMediaHttpDownloader().setDirectDownloadEnabled(true);
            req.executeMediaAndDownloadTo(outputStream);
            return req.getMediaHttpDownloader().getNumBytesDownloaded();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 416) {
                return 0L;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Tuple<String, byte[]> read(final StorageObject from, final Map<Option, ?> options, final long position, final int bytes) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_READ);
        final Scope scope = this.tracer.withSpan(span);
        try {
            Preconditions.checkArgument(position >= 0L, "Position should be non-negative, is %d", position);
            final Storage.Objects.Get req = this.createReadRequest(from, options);
            final StringBuilder range = new StringBuilder();
            range.append("bytes=").append(position).append("-").append(position + bytes - 1L);
            final HttpHeaders requestHeaders = req.getRequestHeaders();
            requestHeaders.setRange(range.toString());
            final ByteArrayOutputStream output = new ByteArrayOutputStream(bytes);
            req.executeMedia().download(output);
            final String etag = req.getLastResponseHeaders().getETag();
            return (Tuple<String, byte[]>)Tuple.of((Object)etag, (Object)output.toByteArray());
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 416) {
                return (Tuple<String, byte[]>)Tuple.of((Object)null, (Object)new byte[0]);
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public void write(final String uploadId, final byte[] toWrite, final int toWriteOffset, final long destOffset, final int length, final boolean last) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_WRITE);
        final Scope scope = this.tracer.withSpan(span);
        try {
            if (length == 0 && !last) {
                return;
            }
            final GenericUrl url = new GenericUrl(uploadId);
            final HttpRequest httpRequest = this.storage.getRequestFactory().buildPutRequest(url, new ByteArrayContent(null, toWrite, toWriteOffset, length));
            final long limit = destOffset + length;
            final StringBuilder range = new StringBuilder("bytes ");
            if (length == 0) {
                range.append('*');
            }
            else {
                range.append(destOffset).append('-').append(limit - 1L);
            }
            range.append('/');
            if (last) {
                range.append(limit);
            }
            else {
                range.append('*');
            }
            httpRequest.getHeaders().setContentRange(range.toString());
            IOException exception = null;
            HttpResponse response = null;
            int code;
            String message;
            try {
                response = httpRequest.execute();
                code = response.getStatusCode();
                message = response.getStatusMessage();
            }
            catch (HttpResponseException ex) {
                exception = ex;
                code = ex.getStatusCode();
                message = ex.getStatusMessage();
            }
            finally {
                if (response != null) {
                    response.disconnect();
                }
            }
            if ((!last && code != 308) || (last && code != 200 && code != 201)) {
                if (exception != null) {
                    throw exception;
                }
                final GoogleJsonError error = new GoogleJsonError();
                error.setCode(code);
                error.setMessage(message);
                throw translate(error);
            }
        }
        catch (IOException ex2) {
            span.setStatus(Status.UNKNOWN.withDescription(ex2.getMessage()));
            throw translate(ex2);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public String open(final StorageObject object, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_OPEN);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final Storage.Objects.Insert req = this.storage.objects().insert(object.getBucket(), object);
            GenericUrl url = req.buildHttpRequest().getUrl();
            final String scheme = url.getScheme();
            final String host = url.getHost();
            int port = url.getPort();
            port = ((port > 0) ? port : url.toURL().getDefaultPort());
            final String path = "/upload" + url.getRawPath();
            url = new GenericUrl(scheme + "://" + host + ":" + port + path);
            url.set("uploadType", "resumable");
            url.set("name", object.getName());
            for (final Option option : options.keySet()) {
                final Object content = option.get(options);
                if (content != null) {
                    url.set(option.value(), content.toString());
                }
            }
            final JsonFactory jsonFactory = this.storage.getJsonFactory();
            final HttpRequestFactory requestFactory = this.storage.getRequestFactory();
            final HttpRequest httpRequest = requestFactory.buildPostRequest(url, new JsonHttpContent(jsonFactory, object));
            final HttpHeaders requestHeaders = httpRequest.getHeaders();
            requestHeaders.set("X-Upload-Content-Type", MoreObjects.firstNonNull(object.getContentType(), "application/octet-stream"));
            final String key = Option.CUSTOMER_SUPPLIED_KEY.getString(options);
            if (key != null) {
                final BaseEncoding base64 = BaseEncoding.base64();
                final HashFunction hashFunction = Hashing.sha256();
                requestHeaders.set("x-goog-encryption-algorithm", "AES256");
                requestHeaders.set("x-goog-encryption-key", key);
                requestHeaders.set("x-goog-encryption-key-sha256", base64.encode(hashFunction.hashBytes(base64.decode((CharSequence)key)).asBytes()));
            }
            final HttpResponse response = httpRequest.execute();
            if (response.getStatusCode() != 200) {
                final GoogleJsonError error = new GoogleJsonError();
                error.setCode(response.getStatusCode());
                error.setMessage(response.getStatusMessage());
                throw translate(error);
            }
            return response.getHeaders().getLocation();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public String open(final String signedURL) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_OPEN);
        final Scope scope = this.tracer.withSpan(span);
        try {
            final GenericUrl url = new GenericUrl(signedURL);
            url.set("uploadType", "resumable");
            final String bytesArrayParameters = "";
            final byte[] bytesArray = new byte[bytesArrayParameters.length()];
            final HttpRequestFactory requestFactory = this.storage.getRequestFactory();
            final HttpRequest httpRequest = requestFactory.buildPostRequest(url, new ByteArrayContent("", bytesArray, 0, bytesArray.length));
            final HttpHeaders requestHeaders = httpRequest.getHeaders();
            requestHeaders.set("X-Upload-Content-Type", "");
            requestHeaders.set("x-goog-resumable", "start");
            final HttpResponse response = httpRequest.execute();
            if (response.getStatusCode() != 201) {
                final GoogleJsonError error = new GoogleJsonError();
                error.setCode(response.getStatusCode());
                error.setMessage(response.getStatusMessage());
                throw translate(error);
            }
            return response.getHeaders().getLocation();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public RewriteResponse openRewrite(final RewriteRequest rewriteRequest) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_OPEN_REWRITE);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return this.rewrite(rewriteRequest, null);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public RewriteResponse continueRewrite(final RewriteResponse previousResponse) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CONTINUE_REWRITE);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return this.rewrite(previousResponse.rewriteRequest, previousResponse.rewriteToken);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    private RewriteResponse rewrite(final RewriteRequest req, final String token) {
        try {
            String userProject = Option.USER_PROJECT.getString(req.sourceOptions);
            if (userProject == null) {
                userProject = Option.USER_PROJECT.getString(req.targetOptions);
            }
            final Long maxBytesRewrittenPerCall = (req.megabytesRewrittenPerCall != null) ? Long.valueOf(req.megabytesRewrittenPerCall * 1048576L) : null;
            final Storage.Objects.Rewrite rewrite = this.storage.objects().rewrite(req.source.getBucket(), req.source.getName(), req.target.getBucket(), req.target.getName(), req.overrideInfo ? req.target : null).setSourceGeneration(req.source.getGeneration()).setRewriteToken(token).setMaxBytesRewrittenPerCall(maxBytesRewrittenPerCall).setProjection("full").setIfSourceMetagenerationMatch(Option.IF_SOURCE_METAGENERATION_MATCH.getLong(req.sourceOptions)).setIfSourceMetagenerationNotMatch(Option.IF_SOURCE_METAGENERATION_NOT_MATCH.getLong(req.sourceOptions)).setIfSourceGenerationMatch(Option.IF_SOURCE_GENERATION_MATCH.getLong(req.sourceOptions)).setIfSourceGenerationNotMatch(Option.IF_SOURCE_GENERATION_NOT_MATCH.getLong(req.sourceOptions)).setIfMetagenerationMatch(Option.IF_METAGENERATION_MATCH.getLong(req.targetOptions)).setIfMetagenerationNotMatch(Option.IF_METAGENERATION_NOT_MATCH.getLong(req.targetOptions)).setIfGenerationMatch(Option.IF_GENERATION_MATCH.getLong(req.targetOptions)).setIfGenerationNotMatch(Option.IF_GENERATION_NOT_MATCH.getLong(req.targetOptions)).setDestinationPredefinedAcl(Option.PREDEFINED_ACL.getString(req.targetOptions)).setUserProject(userProject).setDestinationKmsKeyName(Option.KMS_KEY_NAME.getString(req.targetOptions));
            final HttpHeaders requestHeaders = rewrite.getRequestHeaders();
            setEncryptionHeaders(requestHeaders, "x-goog-copy-source-encryption-", req.sourceOptions);
            setEncryptionHeaders(requestHeaders, "x-goog-encryption-", req.targetOptions);
            final com.google.api.services.storage.model.RewriteResponse rewriteResponse = (com.google.api.services.storage.model.RewriteResponse)rewrite.execute();
            return new RewriteResponse(req, rewriteResponse.getResource(), rewriteResponse.getObjectSize(), rewriteResponse.getDone(), rewriteResponse.getRewriteToken(), rewriteResponse.getTotalBytesRewritten());
        }
        catch (IOException ex) {
            this.tracer.getCurrentSpan().setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
    }
    
    @Override
    public BucketAccessControl getAcl(final String bucket, final String entity, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (BucketAccessControl)this.storage.bucketAccessControls().get(bucket, entity).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return null;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public boolean deleteAcl(final String bucket, final String entity, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_BUCKET_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            this.storage.bucketAccessControls().delete(bucket, entity).setUserProject(Option.USER_PROJECT.getString(options)).execute();
            return true;
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return false;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public BucketAccessControl createAcl(final BucketAccessControl acl, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_BUCKET_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (BucketAccessControl)this.storage.bucketAccessControls().insert(acl.getBucket(), acl).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public BucketAccessControl patchAcl(final BucketAccessControl acl, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_BUCKET_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (BucketAccessControl)this.storage.bucketAccessControls().patch(acl.getBucket(), acl.getEntity(), acl).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public List<BucketAccessControl> listAcls(final String bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_BUCKET_ACLS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (List<BucketAccessControl>)((BucketAccessControls)this.storage.bucketAccessControls().list(bucket).setUserProject(Option.USER_PROJECT.getString(options)).execute()).getItems();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ObjectAccessControl getDefaultAcl(final String bucket, final String entity) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT_DEFAULT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ObjectAccessControl)this.storage.defaultObjectAccessControls().get(bucket, entity).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return null;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public boolean deleteDefaultAcl(final String bucket, final String entity) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT_DEFAULT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            this.storage.defaultObjectAccessControls().delete(bucket, entity).execute();
            return true;
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return false;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ObjectAccessControl createDefaultAcl(final ObjectAccessControl acl) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT_DEFAULT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ObjectAccessControl)this.storage.defaultObjectAccessControls().insert(acl.getBucket(), acl).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ObjectAccessControl patchDefaultAcl(final ObjectAccessControl acl) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT_DEFAULT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ObjectAccessControl)this.storage.defaultObjectAccessControls().patch(acl.getBucket(), acl.getEntity(), acl).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public List<ObjectAccessControl> listDefaultAcls(final String bucket) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECT_DEFAULT_ACLS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (List<ObjectAccessControl>)((ObjectAccessControls)this.storage.defaultObjectAccessControls().list(bucket).execute()).getItems();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ObjectAccessControl getAcl(final String bucket, final String object, final Long generation, final String entity) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ObjectAccessControl)this.storage.objectAccessControls().get(bucket, object, entity).setGeneration(generation).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return null;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public boolean deleteAcl(final String bucket, final String object, final Long generation, final String entity) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            this.storage.objectAccessControls().delete(bucket, object, entity).setGeneration(generation).execute();
            return true;
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return false;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ObjectAccessControl createAcl(final ObjectAccessControl acl) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ObjectAccessControl)this.storage.objectAccessControls().insert(acl.getBucket(), acl.getObject(), acl).setGeneration(acl.getGeneration()).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ObjectAccessControl patchAcl(final ObjectAccessControl acl) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT_ACL);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ObjectAccessControl)this.storage.objectAccessControls().patch(acl.getBucket(), acl.getObject(), acl.getEntity(), acl).setGeneration(acl.getGeneration()).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public List<ObjectAccessControl> listAcls(final String bucket, final String object, final Long generation) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECT_ACLS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (List<ObjectAccessControl>)((ObjectAccessControls)this.storage.objectAccessControls().list(bucket, object).setGeneration(generation).execute()).getItems();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public HmacKey createHmacKey(final String serviceAccountEmail, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_HMAC_KEY);
        final Scope scope = this.tracer.withSpan(span);
        String projectId = Option.PROJECT_ID.getString(options);
        if (projectId == null) {
            projectId = this.options.getProjectId();
        }
        try {
            return (HmacKey)this.storage.projects().hmacKeys().create(projectId, serviceAccountEmail).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Tuple<String, Iterable<HmacKeyMetadata>> listHmacKeys(final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_HMAC_KEYS);
        final Scope scope = this.tracer.withSpan(span);
        String projectId = Option.PROJECT_ID.getString(options);
        if (projectId == null) {
            projectId = this.options.getProjectId();
        }
        try {
            final HmacKeysMetadata hmacKeysMetadata = (HmacKeysMetadata)this.storage.projects().hmacKeys().list(projectId).setServiceAccountEmail(Option.SERVICE_ACCOUNT_EMAIL.getString(options)).setPageToken(Option.PAGE_TOKEN.getString(options)).setMaxResults(Option.MAX_RESULTS.getLong(options)).setShowDeletedKeys(Option.SHOW_DELETED_KEYS.getBoolean(options)).execute();
            return (Tuple<String, Iterable<HmacKeyMetadata>>)Tuple.of((Object)hmacKeysMetadata.getNextPageToken(), (Object)hmacKeysMetadata.getItems());
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public HmacKeyMetadata getHmacKey(final String accessId, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_HMAC_KEY);
        final Scope scope = this.tracer.withSpan(span);
        String projectId = Option.PROJECT_ID.getString(options);
        if (projectId == null) {
            projectId = this.options.getProjectId();
        }
        try {
            return (HmacKeyMetadata)this.storage.projects().hmacKeys().get(projectId, accessId).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public HmacKeyMetadata updateHmacKey(final HmacKeyMetadata hmacKeyMetadata, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_UPDATE_HMAC_KEY);
        final Scope scope = this.tracer.withSpan(span);
        String projectId = hmacKeyMetadata.getProjectId();
        if (projectId == null) {
            projectId = this.options.getProjectId();
        }
        try {
            return (HmacKeyMetadata)this.storage.projects().hmacKeys().update(projectId, hmacKeyMetadata.getAccessId(), hmacKeyMetadata).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public void deleteHmacKey(final HmacKeyMetadata hmacKeyMetadata, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_HMAC_KEY);
        final Scope scope = this.tracer.withSpan(span);
        String projectId = hmacKeyMetadata.getProjectId();
        if (projectId == null) {
            projectId = this.options.getProjectId();
        }
        try {
            this.storage.projects().hmacKeys().delete(projectId, hmacKeyMetadata.getAccessId()).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Policy getIamPolicy(final String bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET_IAM_POLICY);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (Policy)this.storage.buckets().getIamPolicy(bucket).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Policy setIamPolicy(final String bucket, final Policy policy, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_SET_BUCKET_IAM_POLICY);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (Policy)this.storage.buckets().setIamPolicy(bucket, policy).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public TestIamPermissionsResponse testIamPermissions(final String bucket, final List<String> permissions, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_TEST_BUCKET_IAM_PERMISSIONS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (TestIamPermissionsResponse)this.storage.buckets().testIamPermissions(bucket, (List)permissions).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public boolean deleteNotification(final String bucket, final String notification) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_DELETE_NOTIFICATION);
        final Scope scope = this.tracer.withSpan(span);
        try {
            this.storage.notifications().delete(bucket, notification).execute();
            return true;
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            final StorageException serviceException = translate(ex);
            if (serviceException.getCode() == 404) {
                return false;
            }
            throw serviceException;
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public List<Notification> listNotifications(final String bucket) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_LIST_NOTIFICATIONS);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (List<Notification>)((Notifications)this.storage.notifications().list(bucket).execute()).getItems();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Notification createNotification(final String bucket, final Notification notification) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_CREATE_NOTIFICATION);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (Notification)this.storage.notifications().insert(bucket, notification).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public Bucket lockRetentionPolicy(final Bucket bucket, final Map<Option, ?> options) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_LOCK_RETENTION_POLICY);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (Bucket)this.storage.buckets().lockRetentionPolicy(bucket.getName(), Option.IF_METAGENERATION_MATCH.getLong(options)).setUserProject(Option.USER_PROJECT.getString(options)).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    @Override
    public ServiceAccount getServiceAccount(final String projectId) {
        final Span span = this.startSpan(HttpStorageRpcSpans.SPAN_NAME_GET_SERVICE_ACCOUNT);
        final Scope scope = this.tracer.withSpan(span);
        try {
            return (ServiceAccount)this.storage.projects().serviceAccount().get(projectId).execute();
        }
        catch (IOException ex) {
            span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
            throw translate(ex);
        }
        finally {
            scope.close();
            span.end();
        }
    }
    
    static /* synthetic */ HttpRequestInitializer access$000(final HttpStorageRpc x0) {
        return x0.batchRequestInitializer;
    }
    
    static /* synthetic */ JsonBatchCallback access$100(final RpcBatch.Callback x0) {
        return toJsonCallback((RpcBatch.Callback<Object>)x0);
    }
    
    static /* synthetic */ Storage.Objects.Delete access$200(final HttpStorageRpc x0, final StorageObject x1, final Map x2) throws IOException {
        return x0.deleteCall(x1, x2);
    }
    
    static /* synthetic */ StorageException access$300(final IOException x0) {
        return translate(x0);
    }
    
    static /* synthetic */ Storage.Objects.Patch access$400(final HttpStorageRpc x0, final StorageObject x1, final Map x2) throws IOException {
        return x0.patchCall(x1, x2);
    }
    
    static /* synthetic */ Storage.Objects.Get access$500(final HttpStorageRpc x0, final StorageObject x1, final Map x2) throws IOException {
        return x0.getCall(x1, x2);
    }
    
    static /* synthetic */ Span access$600(final HttpStorageRpc x0, final String x1) {
        return x0.startSpan(x1);
    }
    
    static /* synthetic */ Tracer access$700(final HttpStorageRpc x0) {
        return x0.tracer;
    }
    
    static /* synthetic */ StorageOptions access$800(final HttpStorageRpc x0) {
        return x0.options;
    }
    
    private class DefaultRpcBatch implements RpcBatch
    {
        private static final int MAX_BATCH_SIZE = 100;
        private final Storage storage;
        private final LinkedList<BatchRequest> batches;
        private int currentBatchSize;
        final /* synthetic */ HttpStorageRpc this$0;
        
        private DefaultRpcBatch(final HttpStorageRpc httpStorageRpc, final Storage storage) {
            this.this$0 = httpStorageRpc;
            super();
            this.storage = storage;
            (this.batches = new LinkedList<BatchRequest>()).add(storage.batch(httpStorageRpc.batchRequestInitializer));
        }
        
        @Override
        public void addDelete(final StorageObject storageObject, final Callback<Void> callback, final Map<Option, ?> options) {
            try {
                if (this.currentBatchSize == 100) {
                    this.batches.add(this.storage.batch());
                    this.currentBatchSize = 0;
                }
                HttpStorageRpc.this.deleteCall(storageObject, options).queue((BatchRequest)this.batches.getLast(), toJsonCallback((RpcBatch.Callback<Object>)callback));
                ++this.currentBatchSize;
            }
            catch (IOException ex) {
                throw translate(ex);
            }
        }
        
        @Override
        public void addPatch(final StorageObject storageObject, final Callback<StorageObject> callback, final Map<Option, ?> options) {
            try {
                if (this.currentBatchSize == 100) {
                    this.batches.add(this.storage.batch());
                    this.currentBatchSize = 0;
                }
                HttpStorageRpc.this.patchCall(storageObject, options).queue((BatchRequest)this.batches.getLast(), toJsonCallback((RpcBatch.Callback<Object>)callback));
                ++this.currentBatchSize;
            }
            catch (IOException ex) {
                throw translate(ex);
            }
        }
        
        @Override
        public void addGet(final StorageObject storageObject, final Callback<StorageObject> callback, final Map<Option, ?> options) {
            try {
                if (this.currentBatchSize == 100) {
                    this.batches.add(this.storage.batch());
                    this.currentBatchSize = 0;
                }
                HttpStorageRpc.this.getCall(storageObject, options).queue((BatchRequest)this.batches.getLast(), toJsonCallback((RpcBatch.Callback<Object>)callback));
                ++this.currentBatchSize;
            }
            catch (IOException ex) {
                throw translate(ex);
            }
        }
        
        @Override
        public void submit() {
            final Span span = HttpStorageRpc.this.startSpan(HttpStorageRpcSpans.SPAN_NAME_BATCH_SUBMIT);
            final Scope scope = this.this$0.tracer.withSpan(span);
            try {
                span.putAttribute("batch size", AttributeValue.longAttributeValue((long)this.batches.size()));
                for (final BatchRequest batch : this.batches) {
                    span.addAnnotation("Execute batch request");
                    batch.setBatchUrl(new GenericUrl(String.format("%s/batch/storage/v1", this.this$0.options.getHost())));
                    batch.execute();
                }
            }
            catch (IOException ex) {
                span.setStatus(Status.UNKNOWN.withDescription(ex.getMessage()));
                throw translate(ex);
            }
            finally {
                scope.close();
                span.end();
            }
        }
        
        DefaultRpcBatch(final HttpStorageRpc x0, final Storage x1, final HttpStorageRpc$1 x2) {
            this(x0, x1);
        }
    }
}
