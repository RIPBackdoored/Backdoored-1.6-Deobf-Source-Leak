package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import javax.annotation.*;

private static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable
{
    private final Function<? super A, ? extends B> forwardFunction;
    private final Function<? super B, ? extends A> backwardFunction;
    
    private FunctionBasedConverter(final Function<? super A, ? extends B> forwardFunction, final Function<? super B, ? extends A> backwardFunction) {
        super();
        this.forwardFunction = Preconditions.checkNotNull(forwardFunction);
        this.backwardFunction = Preconditions.checkNotNull(backwardFunction);
    }
    
    @Override
    protected B doForward(final A a) {
        return (B)this.forwardFunction.apply((Object)a);
    }
    
    @Override
    protected A doBackward(final B b) {
        return (A)this.backwardFunction.apply((Object)b);
    }
    
    @Override
    public boolean equals(@Nullable final Object object) {
        if (object instanceof FunctionBasedConverter) {
            final FunctionBasedConverter<?, ?> that = (FunctionBasedConverter<?, ?>)object;
            return this.forwardFunction.equals(that.forwardFunction) && this.backwardFunction.equals(that.backwardFunction);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
    }
    
    @Override
    public String toString() {
        return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
    }
    
    FunctionBasedConverter(final Function x0, final Function x1, final Converter$1 x2) {
        this(x0, x1);
    }
}
