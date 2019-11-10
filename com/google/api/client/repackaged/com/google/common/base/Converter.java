package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import com.google.errorprone.annotations.concurrent.*;
import javax.annotation.*;
import com.google.errorprone.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible
public abstract class Converter<A, B> implements Function<A, B>
{
    private final boolean handleNullAutomatically;
    @LazyInit
    private transient Converter<B, A> reverse;
    
    protected Converter() {
        this(true);
    }
    
    Converter(final boolean handleNullAutomatically) {
        super();
        this.handleNullAutomatically = handleNullAutomatically;
    }
    
    protected abstract B doForward(final A p0);
    
    protected abstract A doBackward(final B p0);
    
    @Nullable
    @CanIgnoreReturnValue
    public final B convert(@Nullable final A a) {
        return this.correctedDoForward(a);
    }
    
    @Nullable
    B correctedDoForward(@Nullable final A a) {
        if (this.handleNullAutomatically) {
            return (a == null) ? null : Preconditions.checkNotNull(this.doForward(a));
        }
        return this.doForward(a);
    }
    
    @Nullable
    A correctedDoBackward(@Nullable final B b) {
        if (this.handleNullAutomatically) {
            return (b == null) ? null : Preconditions.checkNotNull(this.doBackward(b));
        }
        return this.doBackward(b);
    }
    
    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> fromIterable) {
        Preconditions.checkNotNull(fromIterable, (Object)"fromIterable");
        return new Iterable<B>() {
            final /* synthetic */ Iterable val$fromIterable;
            final /* synthetic */ Converter this$0;
            
            Converter$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>() {
                    private final Iterator<? extends A> fromIterator = fromIterable.iterator();
                    final /* synthetic */ Converter$1 this$1;
                    
                    Converter$1$1() {
                        this.this$1 = this$1;
                        super();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }
                    
                    @Override
                    public B next() {
                        return this.this$1.this$0.convert(this.fromIterator.next());
                    }
                    
                    @Override
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }
    
    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        final Converter<B, A> result = this.reverse;
        return (result == null) ? (this.reverse = (Converter<B, A>)new ReverseConverter((Converter<Object, Object>)this)) : result;
    }
    
    public final <C> Converter<A, C> andThen(final Converter<B, C> secondConverter) {
        return (Converter<A, C>)this.doAndThen((Converter<B, Object>)secondConverter);
    }
    
     <C> Converter<A, C> doAndThen(final Converter<B, C> secondConverter) {
        return (Converter<A, C>)new ConverterComposition((Converter<Object, Object>)this, (Converter<Object, Object>)Preconditions.checkNotNull(secondConverter));
    }
    
    @Deprecated
    @Nullable
    @CanIgnoreReturnValue
    @Override
    public final B apply(@Nullable final A a) {
        return this.convert(a);
    }
    
    @Override
    public boolean equals(@Nullable final Object object) {
        return super.equals(object);
    }
    
    public static <A, B> Converter<A, B> from(final Function<? super A, ? extends B> forwardFunction, final Function<? super B, ? extends A> backwardFunction) {
        return new FunctionBasedConverter<A, B>((Function)forwardFunction, (Function)backwardFunction);
    }
    
    public static <T> Converter<T, T> identity() {
        return (Converter<T, T>)IdentityConverter.INSTANCE;
    }
    
    private static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable
    {
        final Converter<A, B> original;
        private static final long serialVersionUID = 0L;
        
        ReverseConverter(final Converter<A, B> original) {
            super();
            this.original = original;
        }
        
        @Override
        protected A doForward(final B b) {
            throw new AssertionError();
        }
        
        @Override
        protected B doBackward(final A a) {
            throw new AssertionError();
        }
        
        @Nullable
        @Override
        A correctedDoForward(@Nullable final B b) {
            return this.original.correctedDoBackward(b);
        }
        
        @Nullable
        @Override
        B correctedDoBackward(@Nullable final A a) {
            return this.original.correctedDoForward(a);
        }
        
        @Override
        public Converter<A, B> reverse() {
            return this.original;
        }
        
        @Override
        public boolean equals(@Nullable final Object object) {
            if (object instanceof ReverseConverter) {
                final ReverseConverter<?, ?> that = (ReverseConverter<?, ?>)object;
                return this.original.equals(that.original);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return ~this.original.hashCode();
        }
        
        @Override
        public String toString() {
            return this.original + ".reverse()";
        }
    }
    
    private static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable
    {
        final Converter<A, B> first;
        final Converter<B, C> second;
        private static final long serialVersionUID = 0L;
        
        ConverterComposition(final Converter<A, B> first, final Converter<B, C> second) {
            super();
            this.first = first;
            this.second = second;
        }
        
        @Override
        protected C doForward(final A a) {
            throw new AssertionError();
        }
        
        @Override
        protected A doBackward(final C c) {
            throw new AssertionError();
        }
        
        @Nullable
        @Override
        C correctedDoForward(@Nullable final A a) {
            return this.second.correctedDoForward(this.first.correctedDoForward(a));
        }
        
        @Nullable
        @Override
        A correctedDoBackward(@Nullable final C c) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(c));
        }
        
        @Override
        public boolean equals(@Nullable final Object object) {
            if (object instanceof ConverterComposition) {
                final ConverterComposition<?, ?, ?> that = (ConverterComposition<?, ?, ?>)object;
                return this.first.equals(that.first) && this.second.equals(that.second);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return 31 * this.first.hashCode() + this.second.hashCode();
        }
        
        @Override
        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }
    
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
    
    private static final class IdentityConverter<T> extends Converter<T, T> implements Serializable
    {
        static final IdentityConverter INSTANCE;
        private static final long serialVersionUID = 0L;
        
        private IdentityConverter() {
            super();
        }
        
        @Override
        protected T doForward(final T t) {
            return t;
        }
        
        @Override
        protected T doBackward(final T t) {
            return t;
        }
        
        @Override
        public IdentityConverter<T> reverse() {
            return this;
        }
        
        @Override
         <S> Converter<T, S> doAndThen(final Converter<T, S> otherConverter) {
            return Preconditions.checkNotNull(otherConverter, (Object)"otherConverter");
        }
        
        @Override
        public String toString() {
            return "Converter.identity()";
        }
        
        private Object readResolve() {
            return IdentityConverter.INSTANCE;
        }
        
        @Override
        public /* bridge */ Converter reverse() {
            return this.reverse();
        }
        
        static {
            INSTANCE = new IdentityConverter();
        }
    }
}
