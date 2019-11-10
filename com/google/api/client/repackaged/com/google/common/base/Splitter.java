package com.google.api.client.repackaged.com.google.common.base;

import java.util.regex.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class Splitter
{
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;
    
    private Splitter(final Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }
    
    private Splitter(final Strategy strategy, final boolean omitEmptyStrings, final CharMatcher trimmer, final int limit) {
        super();
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }
    
    public static Splitter on(final char separator) {
        return on(CharMatcher.is(separator));
    }
    
    public static Splitter on(final CharMatcher separatorMatcher) {
        Preconditions.checkNotNull(separatorMatcher);
        return new Splitter(new Strategy() {
            final /* synthetic */ CharMatcher val$separatorMatcher;
            
            Splitter$1() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence toSplit) {
                return new SplittingIterator(splitter, toSplit) {
                    final /* synthetic */ Splitter$1 this$0;
                    
                    Splitter$1$1(final Splitter x0, final CharSequence x1) {
                        this.this$0 = this$0;
                        super(x0, x1);
                    }
                    
                    @Override
                    int separatorStart(final int start) {
                        return separatorMatcher.indexIn(this.toSplit, start);
                    }
                    
                    @Override
                    int separatorEnd(final int separatorPosition) {
                        return separatorPosition + 1;
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter x0, final CharSequence x1) {
                return this.iterator(x0, x1);
            }
        });
    }
    
    public static Splitter on(final String separator) {
        Preconditions.checkArgument(separator.length() != 0, (Object)"The separator may not be the empty string.");
        if (separator.length() == 1) {
            return on(separator.charAt(0));
        }
        return new Splitter(new Strategy() {
            final /* synthetic */ String val$separator;
            
            Splitter$2() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence toSplit) {
                return new SplittingIterator(splitter, toSplit) {
                    final /* synthetic */ Splitter$2 this$0;
                    
                    Splitter$2$1(final Splitter x0, final CharSequence x1) {
                        this.this$0 = this$0;
                        super(x0, x1);
                    }
                    
                    public int separatorStart(final int start) {
                        final int separatorLength = separator.length();
                        int p = start;
                        final int last = this.toSplit.length() - separatorLength;
                    Label_0026:
                        while (p <= last) {
                            for (int i = 0; i < separatorLength; ++i) {
                                if (this.toSplit.charAt(i + p) != separator.charAt(i)) {
                                    ++p;
                                    continue Label_0026;
                                }
                            }
                            return p;
                        }
                        return -1;
                    }
                    
                    public int separatorEnd(final int separatorPosition) {
                        return separatorPosition + separator.length();
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter x0, final CharSequence x1) {
                return this.iterator(x0, x1);
            }
        });
    }
    
    @GwtIncompatible
    public static Splitter on(final Pattern separatorPattern) {
        return on(new JdkPattern(separatorPattern));
    }
    
    private static Splitter on(final CommonPattern separatorPattern) {
        Preconditions.checkArgument(!separatorPattern.matcher("").matches(), "The pattern may not match the empty string: %s", separatorPattern);
        return new Splitter(new Strategy() {
            final /* synthetic */ CommonPattern val$separatorPattern;
            
            Splitter$3() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence toSplit) {
                final CommonMatcher matcher = separatorPattern.matcher(toSplit);
                return new SplittingIterator(splitter, toSplit) {
                    final /* synthetic */ CommonMatcher val$matcher;
                    final /* synthetic */ Splitter$3 this$0;
                    
                    Splitter$3$1(final Splitter x0, final CharSequence x1) {
                        this.this$0 = this$0;
                        super(x0, x1);
                    }
                    
                    public int separatorStart(final int start) {
                        return matcher.find(start) ? matcher.start() : -1;
                    }
                    
                    public int separatorEnd(final int separatorPosition) {
                        return matcher.end();
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter x0, final CharSequence x1) {
                return this.iterator(x0, x1);
            }
        });
    }
    
    @GwtIncompatible
    public static Splitter onPattern(final String separatorPattern) {
        return on(Platform.compilePattern(separatorPattern));
    }
    
    public static Splitter fixedLength(final int length) {
        Preconditions.checkArgument(length > 0, (Object)"The length may not be less than 1");
        return new Splitter(new Strategy() {
            final /* synthetic */ int val$length;
            
            Splitter$4() {
                super();
            }
            
            @Override
            public SplittingIterator iterator(final Splitter splitter, final CharSequence toSplit) {
                return new SplittingIterator(splitter, toSplit) {
                    final /* synthetic */ Splitter$4 this$0;
                    
                    Splitter$4$1(final Splitter x0, final CharSequence x1) {
                        this.this$0 = this$0;
                        super(x0, x1);
                    }
                    
                    public int separatorStart(final int start) {
                        final int nextChunkStart = start + length;
                        return (nextChunkStart < this.toSplit.length()) ? nextChunkStart : -1;
                    }
                    
                    public int separatorEnd(final int separatorPosition) {
                        return separatorPosition;
                    }
                };
            }
            
            @Override
            public /* bridge */ Iterator iterator(final Splitter x0, final CharSequence x1) {
                return this.iterator(x0, x1);
            }
        });
    }
    
    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }
    
    public Splitter limit(final int limit) {
        Preconditions.checkArgument(limit > 0, "must be greater than zero: %s", limit);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, limit);
    }
    
    public Splitter trimResults() {
        return this.trimResults(CharMatcher.whitespace());
    }
    
    public Splitter trimResults(final CharMatcher trimmer) {
        Preconditions.checkNotNull(trimmer);
        return new Splitter(this.strategy, this.omitEmptyStrings, trimmer, this.limit);
    }
    
    public Iterable<String> split(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return new Iterable<String>() {
            final /* synthetic */ CharSequence val$sequence;
            final /* synthetic */ Splitter this$0;
            
            Splitter$5() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Iterator<String> iterator() {
                return Splitter.this.splittingIterator(sequence);
            }
            
            @Override
            public String toString() {
                return Joiner.on(", ").appendTo(new StringBuilder().append('['), (Iterable<?>)this).append(']').toString();
            }
        };
    }
    
    private Iterator<String> splittingIterator(final CharSequence sequence) {
        return this.strategy.iterator(this, sequence);
    }
    
    @Beta
    public List<String> splitToList(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        final Iterator<String> iterator = this.splittingIterator(sequence);
        final List<String> result = new ArrayList<String>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return Collections.unmodifiableList((List<? extends String>)result);
    }
    
    @Beta
    public MapSplitter withKeyValueSeparator(final String separator) {
        return this.withKeyValueSeparator(on(separator));
    }
    
    @Beta
    public MapSplitter withKeyValueSeparator(final char separator) {
        return this.withKeyValueSeparator(on(separator));
    }
    
    @Beta
    public MapSplitter withKeyValueSeparator(final Splitter keyValueSplitter) {
        return new MapSplitter(this, keyValueSplitter);
    }
    
    static /* synthetic */ Iterator access$000(final Splitter x0, final CharSequence x1) {
        return x0.splittingIterator(x1);
    }
    
    static /* synthetic */ CharMatcher access$200(final Splitter x0) {
        return x0.trimmer;
    }
    
    static /* synthetic */ boolean access$300(final Splitter x0) {
        return x0.omitEmptyStrings;
    }
    
    static /* synthetic */ int access$400(final Splitter x0) {
        return x0.limit;
    }
    
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
                final Iterator<String> entryFields = this.entrySplitter.splittingIterator(entry);
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
    
    private abstract static class SplittingIterator extends AbstractIterator<String>
    {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;
        int offset;
        int limit;
        
        abstract int separatorStart(final int p0);
        
        abstract int separatorEnd(final int p0);
        
        protected SplittingIterator(final Splitter splitter, final CharSequence toSplit) {
            super();
            this.offset = 0;
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }
        
        @Override
        protected String computeNext() {
            int nextStart = this.offset;
            while (this.offset != -1) {
                int start = nextStart;
                final int separatorPosition = this.separatorStart(this.offset);
                int end;
                if (separatorPosition == -1) {
                    end = this.toSplit.length();
                    this.offset = -1;
                }
                else {
                    end = separatorPosition;
                    this.offset = this.separatorEnd(separatorPosition);
                }
                if (this.offset == nextStart) {
                    ++this.offset;
                    if (this.offset < this.toSplit.length()) {
                        continue;
                    }
                    this.offset = -1;
                }
                else {
                    while (start < end && this.trimmer.matches(this.toSplit.charAt(start))) {
                        ++start;
                    }
                    while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                        --end;
                    }
                    if (!this.omitEmptyStrings || start != end) {
                        if (this.limit == 1) {
                            end = this.toSplit.length();
                            this.offset = -1;
                            while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                                --end;
                            }
                        }
                        else {
                            --this.limit;
                        }
                        return this.toSplit.subSequence(start, end).toString();
                    }
                    nextStart = this.offset;
                }
            }
            return this.endOfData();
        }
        
        @Override
        protected /* bridge */ Object computeNext() {
            return this.computeNext();
        }
    }
    
    private interface Strategy
    {
        Iterator<String> iterator(final Splitter p0, final CharSequence p1);
    }
}
