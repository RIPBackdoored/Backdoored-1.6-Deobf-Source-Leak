package com.google.cloud.storage.testing;

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
