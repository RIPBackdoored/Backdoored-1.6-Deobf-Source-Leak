package com.fasterxml.jackson.core.sym;

import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.util.*;

public final class CharsToNameCanonicalizer
{
    public static final int HASH_MULT = 33;
    private static final int DEFAULT_T_SIZE = 64;
    private static final int MAX_T_SIZE = 65536;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    static final int MAX_COLL_CHAIN_LENGTH = 100;
    private final CharsToNameCanonicalizer _parent;
    private final AtomicReference<TableInfo> _tableInfo;
    private final int _seed;
    private final int _flags;
    private boolean _canonicalize;
    private String[] _symbols;
    private Bucket[] _buckets;
    private int _size;
    private int _sizeThreshold;
    private int _indexMask;
    private int _longestCollisionList;
    private boolean _hashShared;
    private BitSet _overflows;
    
    private CharsToNameCanonicalizer(final int seed) {
        super();
        this._parent = null;
        this._seed = seed;
        this._canonicalize = true;
        this._flags = -1;
        this._hashShared = false;
        this._longestCollisionList = 0;
        this._tableInfo = new AtomicReference<TableInfo>(TableInfo.createInitial(64));
    }
    
    private CharsToNameCanonicalizer(final CharsToNameCanonicalizer parent, final int flags, final int seed, final TableInfo parentState) {
        super();
        this._parent = parent;
        this._seed = seed;
        this._tableInfo = null;
        this._flags = flags;
        this._canonicalize = JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(flags);
        this._symbols = parentState.symbols;
        this._buckets = parentState.buckets;
        this._size = parentState.size;
        this._longestCollisionList = parentState.longestCollisionList;
        final int arrayLen = this._symbols.length;
        this._sizeThreshold = _thresholdSize(arrayLen);
        this._indexMask = arrayLen - 1;
        this._hashShared = true;
    }
    
    private static int _thresholdSize(final int hashAreaSize) {
        return hashAreaSize - (hashAreaSize >> 2);
    }
    
    public static CharsToNameCanonicalizer createRoot() {
        final long now = System.currentTimeMillis();
        final int seed = (int)now + (int)(now >>> 32) | 0x1;
        return createRoot(seed);
    }
    
    protected static CharsToNameCanonicalizer createRoot(final int seed) {
        return new CharsToNameCanonicalizer(seed);
    }
    
    public CharsToNameCanonicalizer makeChild(final int flags) {
        return new CharsToNameCanonicalizer(this, flags, this._seed, this._tableInfo.get());
    }
    
    public void release() {
        if (!this.maybeDirty()) {
            return;
        }
        if (this._parent != null && this._canonicalize) {
            this._parent.mergeChild(new TableInfo(this));
            this._hashShared = true;
        }
    }
    
    private void mergeChild(TableInfo childState) {
        final int childCount = childState.size;
        final TableInfo currState = this._tableInfo.get();
        if (childCount == currState.size) {
            return;
        }
        if (childCount > 12000) {
            childState = TableInfo.createInitial(64);
        }
        this._tableInfo.compareAndSet(currState, childState);
    }
    
    public int size() {
        if (this._tableInfo != null) {
            return this._tableInfo.get().size;
        }
        return this._size;
    }
    
    public int bucketCount() {
        return this._symbols.length;
    }
    
    public boolean maybeDirty() {
        return !this._hashShared;
    }
    
    public int hashSeed() {
        return this._seed;
    }
    
    public int collisionCount() {
        int count = 0;
        for (final Bucket bucket : this._buckets) {
            if (bucket != null) {
                count += bucket.length;
            }
        }
        return count;
    }
    
    public int maxCollisionLength() {
        return this._longestCollisionList;
    }
    
    public String findSymbol(final char[] buffer, final int start, final int len, final int h) {
        if (len < 1) {
            return "";
        }
        if (!this._canonicalize) {
            return new String(buffer, start, len);
        }
        final int index = this._hashToIndex(h);
        String sym = this._symbols[index];
        if (sym != null) {
            if (sym.length() == len) {
                int i = 0;
                while (sym.charAt(i) == buffer[start + i]) {
                    if (++i == len) {
                        return sym;
                    }
                }
            }
            final Bucket b = this._buckets[index >> 1];
            if (b != null) {
                sym = b.has(buffer, start, len);
                if (sym != null) {
                    return sym;
                }
                sym = this._findSymbol2(buffer, start, len, b.next);
                if (sym != null) {
                    return sym;
                }
            }
        }
        return this._addSymbol(buffer, start, len, h, index);
    }
    
    private String _findSymbol2(final char[] buffer, final int start, final int len, Bucket b) {
        while (b != null) {
            final String sym = b.has(buffer, start, len);
            if (sym != null) {
                return sym;
            }
            b = b.next;
        }
        return null;
    }
    
    private String _addSymbol(final char[] buffer, final int start, final int len, final int h, int index) {
        if (this._hashShared) {
            this.copyArrays();
            this._hashShared = false;
        }
        else if (this._size >= this._sizeThreshold) {
            this.rehash();
            index = this._hashToIndex(this.calcHash(buffer, start, len));
        }
        String newSymbol = new String(buffer, start, len);
        if (JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
            newSymbol = InternCache.instance.intern(newSymbol);
        }
        ++this._size;
        if (this._symbols[index] == null) {
            this._symbols[index] = newSymbol;
        }
        else {
            final int bix = index >> 1;
            final Bucket newB = new Bucket(newSymbol, this._buckets[bix]);
            final int collLen = newB.length;
            if (collLen > 100) {
                this._handleSpillOverflow(bix, newB);
            }
            else {
                this._buckets[bix] = newB;
                this._longestCollisionList = Math.max(collLen, this._longestCollisionList);
            }
        }
        return newSymbol;
    }
    
    private void _handleSpillOverflow(final int bindex, final Bucket newBucket) {
        if (this._overflows == null) {
            (this._overflows = new BitSet()).set(bindex);
        }
        else if (this._overflows.get(bindex)) {
            if (JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
                this.reportTooManyCollisions(100);
            }
            this._canonicalize = false;
        }
        else {
            this._overflows.set(bindex);
        }
        this._symbols[bindex + bindex] = newBucket.symbol;
        this._buckets[bindex] = null;
        this._size -= newBucket.length;
        this._longestCollisionList = -1;
    }
    
    public int _hashToIndex(int rawHash) {
        rawHash += rawHash >>> 15;
        rawHash ^= rawHash << 7;
        rawHash += rawHash >>> 3;
        return rawHash & this._indexMask;
    }
    
    public int calcHash(final char[] buffer, final int start, final int len) {
        int hash = this._seed;
        for (int i = start, end = start + len; i < end; ++i) {
            hash = hash * 33 + buffer[i];
        }
        return (hash == 0) ? 1 : hash;
    }
    
    public int calcHash(final String key) {
        final int len = key.length();
        int hash = this._seed;
        for (int i = 0; i < len; ++i) {
            hash = hash * 33 + key.charAt(i);
        }
        return (hash == 0) ? 1 : hash;
    }
    
    private void copyArrays() {
        final String[] oldSyms = this._symbols;
        this._symbols = Arrays.copyOf(oldSyms, oldSyms.length);
        final Bucket[] oldBuckets = this._buckets;
        this._buckets = Arrays.copyOf(oldBuckets, oldBuckets.length);
    }
    
    private void rehash() {
        int size = this._symbols.length;
        final int newSize = size + size;
        if (newSize > 65536) {
            this._size = 0;
            this._canonicalize = false;
            this._symbols = new String[64];
            this._buckets = new Bucket[32];
            this._indexMask = 63;
            this._hashShared = false;
            return;
        }
        final String[] oldSyms = this._symbols;
        final Bucket[] oldBuckets = this._buckets;
        this._symbols = new String[newSize];
        this._buckets = new Bucket[newSize >> 1];
        this._indexMask = newSize - 1;
        this._sizeThreshold = _thresholdSize(newSize);
        int count = 0;
        int maxColl = 0;
        for (final String symbol : oldSyms) {
            if (symbol != null) {
                ++count;
                final int index = this._hashToIndex(this.calcHash(symbol));
                if (this._symbols[index] == null) {
                    this._symbols[index] = symbol;
                }
                else {
                    final int bix = index >> 1;
                    final Bucket newB = new Bucket(symbol, this._buckets[bix]);
                    this._buckets[bix] = newB;
                    maxColl = Math.max(maxColl, newB.length);
                }
            }
        }
        size >>= 1;
        for (Bucket b : oldBuckets) {
            while (b != null) {
                ++count;
                final String symbol2 = b.symbol;
                final int index2 = this._hashToIndex(this.calcHash(symbol2));
                if (this._symbols[index2] == null) {
                    this._symbols[index2] = symbol2;
                }
                else {
                    final int bix2 = index2 >> 1;
                    final Bucket newB2 = new Bucket(symbol2, this._buckets[bix2]);
                    this._buckets[bix2] = newB2;
                    maxColl = Math.max(maxColl, newB2.length);
                }
                b = b.next;
            }
        }
        this._longestCollisionList = maxColl;
        this._overflows = null;
        if (count != this._size) {
            throw new IllegalStateException(String.format("Internal error on SymbolTable.rehash(): had %d entries; now have %d", this._size, count));
        }
    }
    
    protected void reportTooManyCollisions(final int maxLen) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + maxLen + " -- suspect a DoS attack based on hash collisions");
    }
    
    static /* synthetic */ int access$000(final CharsToNameCanonicalizer x0) {
        return x0._size;
    }
    
    static /* synthetic */ int access$100(final CharsToNameCanonicalizer x0) {
        return x0._longestCollisionList;
    }
    
    static /* synthetic */ String[] access$200(final CharsToNameCanonicalizer x0) {
        return x0._symbols;
    }
    
    static /* synthetic */ Bucket[] access$300(final CharsToNameCanonicalizer x0) {
        return x0._buckets;
    }
    
    static final class Bucket
    {
        public final String symbol;
        public final Bucket next;
        public final int length;
        
        public Bucket(final String s, final Bucket n) {
            super();
            this.symbol = s;
            this.next = n;
            this.length = ((n == null) ? 1 : (n.length + 1));
        }
        
        public String has(final char[] buf, final int start, final int len) {
            if (this.symbol.length() != len) {
                return null;
            }
            int i = 0;
            while (this.symbol.charAt(i) == buf[start + i]) {
                if (++i >= len) {
                    return this.symbol;
                }
            }
            return null;
        }
    }
    
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
            this.size = src._size;
            this.longestCollisionList = src._longestCollisionList;
            this.symbols = src._symbols;
            this.buckets = src._buckets;
        }
        
        public static TableInfo createInitial(final int sz) {
            return new TableInfo(0, 0, new String[sz], new Bucket[sz >> 1]);
        }
    }
}
