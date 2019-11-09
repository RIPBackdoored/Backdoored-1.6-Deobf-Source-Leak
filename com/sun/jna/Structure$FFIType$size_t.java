package com.sun.jna;

public static class size_t extends IntegerType
{
    private static final long serialVersionUID = 1L;
    
    public size_t() {
        this(0L);
    }
    
    public size_t(final long value) {
        super(Native.SIZE_T_SIZE, value);
    }
}
