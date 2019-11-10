package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
final class Absent<T> extends Optional<T>
{
    static final Absent<Object> INSTANCE;
    private static final long serialVersionUID = 0L;
    
    static <T> Optional<T> withType() {
        return (Optional<T>)Absent.INSTANCE;
    }
    
    private Absent() {
        super();
    }
    
    @Override
    public boolean isPresent() {
        return false;
    }
    
    @Override
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }
    
    @Override
    public T or(final T defaultValue) {
        return Preconditions.checkNotNull(defaultValue, (Object)"use Optional.orNull() instead of Optional.or(null)");
    }
    
    @Override
    public Optional<T> or(final Optional<? extends T> secondChoice) {
        return Preconditions.checkNotNull((Optional<T>)secondChoice);
    }
    
    @Override
    public T or(final Supplier<? extends T> supplier) {
        return Preconditions.checkNotNull((T)supplier.get(), (Object)"use Optional.orNull() instead of a Supplier that returns null");
    }
    
    @Nullable
    @Override
    public T orNull() {
        return null;
    }
    
    @Override
    public Set<T> asSet() {
        return Collections.emptySet();
    }
    
    @Override
    public <V> Optional<V> transform(final Function<? super T, V> function) {
        Preconditions.checkNotNull(function);
        return Optional.absent();
    }
    
    @Override
    public boolean equals(@Nullable final Object object) {
        return object == this;
    }
    
    @Override
    public int hashCode() {
        return 2040732332;
    }
    
    @Override
    public String toString() {
        return "Optional.absent()";
    }
    
    private Object readResolve() {
        return Absent.INSTANCE;
    }
    
    static {
        INSTANCE = new Absent<Object>();
    }
}
