package com.sun.jna;

static final class Structure$3 extends Pointer {
    Structure$3(final long peer) {
        super(peer);
    }
    
    @Override
    public Pointer share(final long offset, final long sz) {
        return this;
    }
}