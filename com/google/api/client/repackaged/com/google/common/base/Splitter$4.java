package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$4 implements Strategy {
    final /* synthetic */ int val$length;
    
    Splitter$4(final int val$length) {
        this.val$length = val$length;
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
                final int nextChunkStart = start + this.this$0.val$length;
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
}