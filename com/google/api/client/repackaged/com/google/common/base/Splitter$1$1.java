package com.google.api.client.repackaged.com.google.common.base;

class Splitter$1$1 extends SplittingIterator {
    final /* synthetic */ Splitter$1 this$0;
    
    Splitter$1$1(final Splitter$1 this$0, final Splitter x0, final CharSequence x1) {
        this.this$0 = this$0;
        super(x0, x1);
    }
    
    @Override
    int separatorStart(final int start) {
        return this.this$0.val$separatorMatcher.indexIn(this.toSplit, start);
    }
    
    @Override
    int separatorEnd(final int separatorPosition) {
        return separatorPosition + 1;
    }
}