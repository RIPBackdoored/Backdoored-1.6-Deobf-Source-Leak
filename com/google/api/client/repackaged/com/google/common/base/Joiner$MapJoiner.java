package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import com.google.errorprone.annotations.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

public static final class MapJoiner
{
    private final Joiner joiner;
    private final String keyValueSeparator;
    
    private MapJoiner(final Joiner joiner, final String keyValueSeparator) {
        super();
        this.joiner = joiner;
        this.keyValueSeparator = Preconditions.checkNotNull(keyValueSeparator);
    }
    
    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(final A appendable, final Map<?, ?> map) throws IOException {
        return this.appendTo(appendable, map.entrySet());
    }
    
    @CanIgnoreReturnValue
    public StringBuilder appendTo(final StringBuilder builder, final Map<?, ?> map) {
        return this.appendTo(builder, (Iterable<? extends Map.Entry<?, ?>>)map.entrySet());
    }
    
    public String join(final Map<?, ?> map) {
        return this.join(map.entrySet());
    }
    
    @Beta
    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(final A appendable, final Iterable<? extends Map.Entry<?, ?>> entries) throws IOException {
        return this.appendTo(appendable, entries.iterator());
    }
    
    @Beta
    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(final A appendable, final Iterator<? extends Map.Entry<?, ?>> parts) throws IOException {
        Preconditions.checkNotNull(appendable);
        if (parts.hasNext()) {
            final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)parts.next();
            appendable.append(this.joiner.toString(entry.getKey()));
            appendable.append(this.keyValueSeparator);
            appendable.append(this.joiner.toString(entry.getValue()));
            while (parts.hasNext()) {
                appendable.append(Joiner.access$100(this.joiner));
                final Map.Entry<?, ?> e = (Map.Entry<?, ?>)parts.next();
                appendable.append(this.joiner.toString(e.getKey()));
                appendable.append(this.keyValueSeparator);
                appendable.append(this.joiner.toString(e.getValue()));
            }
        }
        return appendable;
    }
    
    @Beta
    @CanIgnoreReturnValue
    public StringBuilder appendTo(final StringBuilder builder, final Iterable<? extends Map.Entry<?, ?>> entries) {
        return this.appendTo(builder, entries.iterator());
    }
    
    @Beta
    @CanIgnoreReturnValue
    public StringBuilder appendTo(final StringBuilder builder, final Iterator<? extends Map.Entry<?, ?>> entries) {
        try {
            this.appendTo(builder, entries);
        }
        catch (IOException impossible) {
            throw new AssertionError((Object)impossible);
        }
        return builder;
    }
    
    @Beta
    public String join(final Iterable<? extends Map.Entry<?, ?>> entries) {
        return this.join(entries.iterator());
    }
    
    @Beta
    public String join(final Iterator<? extends Map.Entry<?, ?>> entries) {
        return this.appendTo(new StringBuilder(), entries).toString();
    }
    
    public MapJoiner useForNull(final String nullText) {
        return new MapJoiner(this.joiner.useForNull(nullText), this.keyValueSeparator);
    }
    
    MapJoiner(final Joiner x0, final String x1, final Joiner$1 x2) {
        this(x0, x1);
    }
}
