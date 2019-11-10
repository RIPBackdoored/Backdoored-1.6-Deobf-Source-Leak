package com.google.api.client.util;

import java.util.*;

class ClassInfo$1 implements Comparator<String> {
    final /* synthetic */ ClassInfo this$0;
    
    ClassInfo$1(final ClassInfo this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public int compare(final String s0, final String s1) {
        return Objects.equal(s0, s1) ? 0 : ((s0 == null) ? -1 : ((s1 == null) ? 1 : s0.compareTo(s1)));
    }
    
    @Override
    public /* bridge */ int compare(final Object o, final Object o2) {
        return this.compare((String)o, (String)o2);
    }
}