package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$3 implements Strategy {
    final /* synthetic */ CommonPattern val$separatorPattern;
    
    Splitter$3(final CommonPattern val$separatorPattern) {
        this.val$separatorPattern = val$separatorPattern;
        super();
    }
    
    @Override
    public SplittingIterator iterator(final Splitter splitter, final CharSequence toSplit) {
        final CommonMatcher matcher = this.val$separatorPattern.matcher(toSplit);
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
}