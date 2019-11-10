package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$1 implements Strategy {
    final /* synthetic */ CharMatcher val$separatorMatcher;
    
    Splitter$1(final CharMatcher val$separatorMatcher) {
        this.val$separatorMatcher = val$separatorMatcher;
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
                return this.this$0.val$separatorMatcher.indexIn(this.toSplit, start);
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
}