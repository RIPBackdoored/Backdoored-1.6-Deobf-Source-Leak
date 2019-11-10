package org.spongepowered.tools.obfuscation.mirror;

public enum Visibility
{
    PRIVATE, 
    PROTECTED, 
    PACKAGE, 
    PUBLIC;
    
    private static final /* synthetic */ Visibility[] $VALUES;
    
    public static Visibility[] values() {
        return Visibility.$VALUES.clone();
    }
    
    public static Visibility valueOf(final String name) {
        return Enum.valueOf(Visibility.class, name);
    }
    
    static {
        $VALUES = new Visibility[] { Visibility.PRIVATE, Visibility.PROTECTED, Visibility.PACKAGE, Visibility.PUBLIC };
    }
}
