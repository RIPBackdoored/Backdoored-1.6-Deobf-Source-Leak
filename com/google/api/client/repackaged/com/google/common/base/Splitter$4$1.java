package com.google.api.client.repackaged.com.google.common.base;

class Splitter$4$1 extends SplittingIterator {
    final /* synthetic */ Splitter$4 this$0;
    
    Splitter$4$1(final Splitter$4 this$0, final Splitter x0, final CharSequence x1) {
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
}