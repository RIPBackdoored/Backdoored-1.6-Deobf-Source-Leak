package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import java.io.*;

class Joiner$2 extends Joiner {
    final /* synthetic */ Joiner this$0;
    
    Joiner$2(final Joiner this$0, final Joiner x0) {
        this.this$0 = this$0;
        super(x0, null);
    }
    
    @Override
    public <A extends Appendable> A appendTo(final A appendable, final Iterator<?> parts) throws IOException {
        Preconditions.checkNotNull(appendable, (Object)"appendable");
        Preconditions.checkNotNull(parts, (Object)"parts");
        while (parts.hasNext()) {
            final Object part = parts.next();
            if (part != null) {
                appendable.append(this.this$0.toString(part));
                break;
            }
        }
        while (parts.hasNext()) {
            final Object part = parts.next();
            if (part != null) {
                appendable.append(Joiner.access$100(this.this$0));
                appendable.append(this.this$0.toString(part));
            }
        }
        return appendable;
    }
    
    @Override
    public Joiner useForNull(final String nullText) {
        throw new UnsupportedOperationException("already specified skipNulls");
    }
    
    @Override
    public MapJoiner withKeyValueSeparator(final String kvs) {
        throw new UnsupportedOperationException("can't use .skipNulls() with maps");
    }
}