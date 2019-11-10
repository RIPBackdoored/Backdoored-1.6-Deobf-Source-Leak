package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

static final class Splitter$2 implements Strategy {
    final /* synthetic */ String val$separator;
    
    Splitter$2(final String val$separator) {
        this.val$separator = val$separator;
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
                final int separatorLength = this.this$0.val$separator.length();
                int p = start;
                final int last = this.toSplit.length() - separatorLength;
            Label_0026:
                while (p <= last) {
                    for (int i = 0; i < separatorLength; ++i) {
                        if (this.toSplit.charAt(i + p) != this.this$0.val$separator.charAt(i)) {
                            ++p;
                            continue Label_0026;
                        }
                    }
                    return p;
                }
                return -1;
            }
            
            public int separatorEnd(final int separatorPosition) {
                return separatorPosition + this.this$0.val$separator.length();
            }
        };
    }
    
    @Override
    public /* bridge */ Iterator iterator(final Splitter x0, final CharSequence x1) {
        return this.iterator(x0, x1);
    }
}