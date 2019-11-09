package com.sun.jna;

private static class AutoAllocated extends Memory
{
    public AutoAllocated(final int size) {
        super(size);
        super.clear();
    }
    
    @Override
    public String toString() {
        return "auto-" + super.toString();
    }
}
