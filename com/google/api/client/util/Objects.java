package com.google.api.client.util;

public final class Objects
{
    public static boolean equal(final Object a, final Object b) {
        return com.google.api.client.repackaged.com.google.common.base.Objects.equal(a, b);
    }
    
    public static ToStringHelper toStringHelper(final Object self) {
        return new ToStringHelper(self.getClass().getSimpleName());
    }
    
    private Objects() {
        super();
    }
    
    public static final class ToStringHelper
    {
        private final String className;
        private ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;
        
        ToStringHelper(final String className) {
            super();
            this.holderHead = new ValueHolder();
            this.holderTail = this.holderHead;
            this.className = className;
        }
        
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }
        
        public ToStringHelper add(final String name, final Object value) {
            return this.addHolder(name, value);
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
        
        private ToStringHelper addHolder(final String name, final Object value) {
            final ValueHolder valueHolder = this.addHolder();
            valueHolder.value = value;
            valueHolder.name = Preconditions.checkNotNull(name);
            return this;
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
