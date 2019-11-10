package com.google.api.client.util;

public final class Throwables
{
    public static RuntimeException propagate(final Throwable throwable) {
        return com.google.api.client.repackaged.com.google.common.base.Throwables.propagate(throwable);
    }
    
    public static void propagateIfPossible(final Throwable throwable) {
        if (throwable != null) {
            com.google.api.client.repackaged.com.google.common.base.Throwables.throwIfUnchecked(throwable);
        }
    }
    
    public static <X extends Throwable> void propagateIfPossible(final Throwable throwable, final Class<X> declaredType) throws X, Throwable {
        com.google.api.client.repackaged.com.google.common.base.Throwables.propagateIfPossible(throwable, declaredType);
    }
    
    private Throwables() {
        super();
    }
}
