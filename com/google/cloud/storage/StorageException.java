package com.google.cloud.storage;

import com.google.cloud.http.*;
import com.google.api.core.*;
import java.util.*;
import java.io.*;
import com.google.api.client.googleapis.json.*;
import com.google.cloud.*;
import com.google.common.collect.*;

@InternalApi
public final class StorageException extends BaseHttpServiceException
{
    private static final Set<BaseServiceException.Error> RETRYABLE_ERRORS;
    private static final long serialVersionUID = -4168430271327813063L;
    
    public StorageException(final int code, final String message) {
        this(code, message, null);
    }
    
    public StorageException(final int code, final String message, final Throwable cause) {
        super(code, message, (String)null, true, (Set)StorageException.RETRYABLE_ERRORS, cause);
    }
    
    public StorageException(final IOException exception) {
        super(exception, true, (Set)StorageException.RETRYABLE_ERRORS);
    }
    
    public StorageException(final GoogleJsonError error) {
        super(error, true, (Set)StorageException.RETRYABLE_ERRORS);
    }
    
    public static StorageException translateAndThrow(final RetryHelper.RetryHelperException ex) {
        BaseServiceException.translate(ex);
        throw new StorageException(0, ex.getMessage(), ex.getCause());
    }
    
    static {
        RETRYABLE_ERRORS = ImmutableSet.of((Object)new BaseServiceException.Error(Integer.valueOf(504), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(503), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(502), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(500), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(429), (String)null), (Object)new BaseServiceException.Error(Integer.valueOf(408), (String)null), (Object[])new BaseServiceException.Error[] { new BaseServiceException.Error((Integer)null, "internalError") });
    }
}
