package com.sun.jna;

static final class Native$7 extends ThreadLocal<Memory> {
    Native$7() {
        super();
    }
    
    @Override
    protected Memory initialValue() {
        final Memory m = new Memory(4L);
        m.clear();
        return m;
    }
    
    @Override
    protected /* bridge */ Object initialValue() {
        return this.initialValue();
    }
}