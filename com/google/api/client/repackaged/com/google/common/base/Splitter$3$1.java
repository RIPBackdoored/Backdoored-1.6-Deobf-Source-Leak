package com.google.api.client.repackaged.com.google.common.base;

class Splitter$3$1 extends SplittingIterator {
    final /* synthetic */ CommonMatcher val$matcher;
    final /* synthetic */ Splitter$3 this$0;
    
    Splitter$3$1(final Splitter$3 this$0, final Splitter x0, final CharSequence x1, final CommonMatcher val$matcher) {
        this.this$0 = this$0;
        this.val$matcher = val$matcher;
        super(x0, x1);
    }
    
    public int separatorStart(final int start) {
        return this.val$matcher.find(start) ? this.val$matcher.start() : -1;
    }
    
    public int separatorEnd(final int separatorPosition) {
        return this.val$matcher.end();
    }
}