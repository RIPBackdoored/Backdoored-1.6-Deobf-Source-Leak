package com.fasterxml.jackson.core.sym;

private static final class TableInfo
{
    final int size;
    final int longestCollisionList;
    final String[] symbols;
    final Bucket[] buckets;
    
    public TableInfo(final int size, final int longestCollisionList, final String[] symbols, final Bucket[] buckets) {
        super();
        this.size = size;
        this.longestCollisionList = longestCollisionList;
        this.symbols = symbols;
        this.buckets = buckets;
    }
    
    public TableInfo(final CharsToNameCanonicalizer src) {
        super();
        this.size = CharsToNameCanonicalizer.access$000(src);
        this.longestCollisionList = CharsToNameCanonicalizer.access$100(src);
        this.symbols = CharsToNameCanonicalizer.access$200(src);
        this.buckets = CharsToNameCanonicalizer.access$300(src);
    }
    
    public static TableInfo createInitial(final int sz) {
        return new TableInfo(0, 0, new String[sz], new Bucket[sz >> 1]);
    }
}
