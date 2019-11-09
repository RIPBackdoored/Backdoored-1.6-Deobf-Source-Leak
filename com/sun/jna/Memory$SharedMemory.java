package com.sun.jna;

private class SharedMemory extends Memory
{
    final /* synthetic */ Memory this$0;
    
    public SharedMemory(final Memory this$0, final long offset, final long size) {
        this.this$0 = this$0;
        super();
        this.size = size;
        this.peer = this$0.peer + offset;
    }
    
    @Override
    protected void dispose() {
        this.peer = 0L;
    }
    
    @Override
    protected void boundsCheck(final long off, final long sz) {
        this.this$0.boundsCheck(this.peer - this.this$0.peer + off, sz);
    }
    
    @Override
    public String toString() {
        return super.toString() + " (shared from " + this.this$0.toString() + ")";
    }
}
