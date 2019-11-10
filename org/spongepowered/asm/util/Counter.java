package org.spongepowered.asm.util;

public final class Counter
{
    public int value;
    
    public Counter() {
        super();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj.getClass() == Counter.class && ((Counter)obj).value == this.value;
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
}
