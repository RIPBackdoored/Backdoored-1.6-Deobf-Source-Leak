package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@Beta
public static final class MapSplitter
{
    private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
    private final Splitter outerSplitter;
    private final Splitter entrySplitter;
    
    private MapSplitter(final Splitter outerSplitter, final Splitter entrySplitter) {
        super();
        this.outerSplitter = outerSplitter;
        this.entrySplitter = Preconditions.checkNotNull(entrySplitter);
    }
    
    public Map<String, String> split(final CharSequence sequence) {
        final Map<String, String> map = new LinkedHashMap<String, String>();
        for (final String entry : this.outerSplitter.split(sequence)) {
            final Iterator<String> entryFields = (Iterator<String>)Splitter.access$000(this.entrySplitter, entry);
            Preconditions.checkArgument(entryFields.hasNext(), "Chunk [%s] is not a valid entry", entry);
            final String key = entryFields.next();
            Preconditions.checkArgument(!map.containsKey(key), "Duplicate key [%s] found.", key);
            Preconditions.checkArgument(entryFields.hasNext(), "Chunk [%s] is not a valid entry", entry);
            final String value = entryFields.next();
            map.put(key, value);
            Preconditions.checkArgument(!entryFields.hasNext(), "Chunk [%s] is not a valid entry", entry);
        }
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)map);
    }
    
    MapSplitter(final Splitter x0, final Splitter x1, final Splitter$1 x2) {
        this(x0, x1);
    }
}
