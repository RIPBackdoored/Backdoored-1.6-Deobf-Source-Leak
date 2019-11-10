package com.sun.jna.win32;

import com.sun.jna.*;

class W32APITypeMapper$1 implements TypeConverter {
    final /* synthetic */ W32APITypeMapper this$0;
    
    W32APITypeMapper$1(final W32APITypeMapper this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object toNative(final Object value, final ToNativeContext context) {
        if (value == null) {
            return null;
        }
        if (value instanceof String[]) {
            return new StringArray((String[])value, true);
        }
        return new WString(value.toString());
    }
    
    @Override
    public Object fromNative(final Object value, final FromNativeContext context) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
    
    @Override
    public Class<?> nativeType() {
        return WString.class;
    }
}