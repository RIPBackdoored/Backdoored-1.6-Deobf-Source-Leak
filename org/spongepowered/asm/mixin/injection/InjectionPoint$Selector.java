package org.spongepowered.asm.mixin.injection;

public enum Selector
{
    FIRST, 
    LAST, 
    ONE;
    
    public static final Selector DEFAULT;
    private static final /* synthetic */ Selector[] $VALUES;
    
    public static Selector[] values() {
        return Selector.$VALUES.clone();
    }
    
    public static Selector valueOf(final String name) {
        return Enum.valueOf(Selector.class, name);
    }
    
    static {
        $VALUES = new Selector[] { Selector.FIRST, Selector.LAST, Selector.ONE };
        DEFAULT = Selector.FIRST;
    }
}
