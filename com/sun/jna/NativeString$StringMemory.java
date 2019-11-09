package com.sun.jna;

private class StringMemory extends Memory
{
    final /* synthetic */ NativeString this$0;
    
    public StringMemory(final NativeString this$0, final long size) {
        this.this$0 = this$0;
        super(size);
    }
    
    @Override
    public String toString() {
        return this.this$0.toString();
    }
}
