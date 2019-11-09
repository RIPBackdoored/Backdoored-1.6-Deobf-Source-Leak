package com.sun.jna;

private static class NativeMappedArray extends Memory implements PostCallRead
{
    private final NativeMapped[] original;
    
    public NativeMappedArray(final NativeMapped[] arg) {
        super(Native.getNativeSize(arg.getClass(), arg));
        this.setValue(0L, this.original = arg, this.original.getClass());
    }
    
    @Override
    public void read() {
        this.getValue(0L, this.original.getClass(), this.original);
    }
}
