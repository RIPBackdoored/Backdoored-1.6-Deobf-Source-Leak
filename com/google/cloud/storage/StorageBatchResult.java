package com.google.cloud.storage;

import com.google.cloud.*;

public class StorageBatchResult<T> extends BatchResult<T, StorageException>
{
    StorageBatchResult() {
        super();
    }
    
    protected void error(final StorageException error) {
        super.error((BaseServiceException)error);
    }
    
    protected void success(final T result) {
        super.success((Object)result);
    }
    
    protected /* bridge */ void error(final BaseServiceException ex) {
        this.error((StorageException)ex);
    }
}
