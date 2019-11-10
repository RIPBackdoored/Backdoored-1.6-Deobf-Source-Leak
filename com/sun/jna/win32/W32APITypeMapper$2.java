package com.sun.jna.win32;

import com.sun.jna.*;

class W32APITypeMapper$2 implements TypeConverter {
    final /* synthetic */ W32APITypeMapper this$0;
    
    W32APITypeMapper$2(final W32APITypeMapper this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object toNative(final Object value, final ToNativeContext context) {
        return Boolean.TRUE.equals(value) ? 1 : 0;
    }
    
    @Override
    public Object fromNative(final Object value, final FromNativeContext context) {
        return ((int)value != 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Class<?> nativeType() {
        return Integer.class;
    }
}