package com.sun.jna;

private static class PointerArray extends Memory implements PostCallRead
{
    private final Pointer[] original;
    
    public PointerArray(final Pointer[] arg) {
        super(Pointer.SIZE * (arg.length + 1));
        this.original = arg;
        for (int i = 0; i < arg.length; ++i) {
            this.setPointer(i * Pointer.SIZE, arg[i]);
        }
        this.setPointer(Pointer.SIZE * arg.length, null);
    }
    
    @Override
    public void read() {
        this.read(0L, this.original, 0, this.original.length);
    }
}
