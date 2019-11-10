package com.google.cloud.storage;

import com.google.cloud.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.paging.*;

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
        return (Page<Blob>)StorageImpl.access$200(this.bucket, this.serviceOptions, this.requestOptions);
    }
}
