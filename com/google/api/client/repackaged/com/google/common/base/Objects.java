package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.errorprone.annotations.*;

@GwtCompatible
public final class Objects extends ExtraObjectsMethodsForWeb
{
    private Objects() {
        super();
    }
    
    public static boolean equal(@Nullable final Object a, @Nullable final Object b) {
        return a == b || (a != null && a.equals(b));
    }
    
    public static int hashCode(@Nullable final Object... objects) {
        return Arrays.hashCode(objects);
    }
    
    @Deprecated
    public static ToStringHelper toStringHelper(final Object self) {
        return new ToStringHelper(self.getClass().getSimpleName());
    }
    
    @Deprecated
    public static ToStringHelper toStringHelper(final Class<?> clazz) {
        return new ToStringHelper(clazz.getSimpleName());
    }
    
    @Deprecated
    public static ToStringHelper toStringHelper(final String className) {
        return new ToStringHelper(className);
    }
    
    @Deprecated
    public static <T> T firstNonNull(@Nullable final T first, @Nullable final T second) {
        return MoreObjects.firstNonNull(first, second);
    }
    
    @Deprecated
    public static final class ToStringHelper
    {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;
        
        private ToStringHelper(final String className) {
            super();
            this.holderHead = new ValueHolder();
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = Preconditions.checkNotNull(className);
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, @Nullable final Object value) {
            return this.addHolder(name, value);
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, final boolean value) {
            return this.addHolder(name, String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, final char value) {
            return this.addHolder(name, String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, final double value) {
            return this.addHolder(name, String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, final float value) {
            return this.addHolder(name, String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, final int value) {
            return this.addHolder(name, String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper add(final String name, final long value) {
            return this.addHolder(name, String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(@Nullable final Object value) {
            return this.addHolder(value);
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final boolean value) {
            return this.addHolder(String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final char value) {
            return this.addHolder(String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final double value) {
            return this.addHolder(String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final float value) {
            return this.addHolder(String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final int value) {
            return this.addHolder(String.valueOf(value));
        }
        
        @CanIgnoreReturnValue
        public ToStringHelper addValue(final long value) {
            return this.addHolder(String.valueOf(value));
        }
        
        @Override
        public String toString() {
            final boolean omitNullValuesSnapshot = this.omitNullValues;
            String nextSeparator = "";
            final StringBuilder builder = new StringBuilder(32).append(this.className).append('{');
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                if (!omitNullValuesSnapshot || valueHolder.value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";
                    if (valueHolder.name != null) {
                        builder.append(valueHolder.name).append('=');
                    }
                    builder.append(valueHolder.value);
                }
            }
            return builder.append('}').toString();
        }
        
        private ValueHolder addHolder() {
            final ValueHolder valueHolder = new ValueHolder();
            final ValueHolder holderTail = this.holderTail;
            final ValueHolder valueHolder2 = valueHolder;
            holderTail.next = valueHolder2;
            this.holderTail = valueHolder2;
            return valueHolder;
        }
        
        private ToStringHelper addHolder(@Nullable final Object value) {
            final ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            return this;
        }
        
        private ToStringHelper addHolder(final String name, @Nullable final Object value) {
            final ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            valueHolder.name = Preconditions.checkNotNull(name);
            return this;
        }
        
        ToStringHelper(final String x0, final Objects$1 x1) {
            this(x0);
        }
        
        private static final class ValueHolder
        {
            String name;
            Object value;
            ValueHolder next;
            
            private ValueHolder() {
                super();
            }
            
            ValueHolder(final Objects$1 x0) {
                this();
            }
        }
    }
}
