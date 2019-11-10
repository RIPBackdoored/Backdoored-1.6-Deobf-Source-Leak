package com.google.api.client.repackaged.com.google.common.base;

import javax.annotation.*;

class Joiner$1 extends Joiner {
    final /* synthetic */ String val$nullText;
    final /* synthetic */ Joiner this$0;
    
    Joiner$1(final Joiner this$0, final Joiner x0, final String val$nullText) {
        this.this$0 = this$0;
        this.val$nullText = val$nullText;
        super(x0, null);
    }
    
    @Override
    CharSequence toString(@Nullable final Object part) {
        return (part == null) ? this.val$nullText : this.this$0.toString(part);
    }
    
    @Override
    public Joiner useForNull(final String nullText) {
        throw new UnsupportedOperationException("already specified useForNull");
    }
    
    @Override
    public Joiner skipNulls() {
        throw new UnsupportedOperationException("already specified useForNull");
    }
}