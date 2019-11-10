package com.google.cloud.storage;

import com.google.cloud.*;
import java.util.*;
import com.google.cloud.storage.spi.v1.*;
import com.google.api.gax.paging.*;

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
        return (Page<Bucket>)StorageImpl.access$100(this.serviceOptions, this.requestOptions);
    }
}
