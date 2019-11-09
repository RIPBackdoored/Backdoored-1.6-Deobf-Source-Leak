package com.fasterxml.jackson.core.sym;

private static final class TableInfo
{
    public final int size;
    public final int count;
    public final int tertiaryShift;
    public final int[] mainHash;
    public final String[] names;
    public final int spilloverEnd;
    public final int longNameOffset;
    
    public TableInfo(final int size, final int count, final int tertiaryShift, final int[] mainHash, final String[] names, final int spilloverEnd, final int longNameOffset) {
        super();
        this.size = size;
        this.count = count;
        this.tertiaryShift = tertiaryShift;
        this.mainHash = mainHash;
        this.names = names;
        this.spilloverEnd = spilloverEnd;
        this.longNameOffset = longNameOffset;
    }
    
    public TableInfo(final ByteQuadsCanonicalizer src) {
        super();
        this.size = ByteQuadsCanonicalizer.access$000(src);
        this.count = ByteQuadsCanonicalizer.access$100(src);
        this.tertiaryShift = ByteQuadsCanonicalizer.access$200(src);
        this.mainHash = ByteQuadsCanonicalizer.access$300(src);
        this.names = ByteQuadsCanonicalizer.access$400(src);
        this.spilloverEnd = ByteQuadsCanonicalizer.access$500(src);
        this.longNameOffset = ByteQuadsCanonicalizer.access$600(src);
    }
    
    public static TableInfo createInitial(final int sz) {
        final int hashAreaSize = sz << 3;
        final int tertShift = ByteQuadsCanonicalizer._calcTertiaryShift(sz);
        return new TableInfo(sz, 0, tertShift, new int[hashAreaSize], new String[sz << 1], hashAreaSize - sz, hashAreaSize);
    }
}
