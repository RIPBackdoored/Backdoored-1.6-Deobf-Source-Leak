package org.spongepowered.tools.obfuscation.interfaces;

public enum ValidationPass
{
    EARLY, 
    LATE, 
    FINAL;
    
    private static final /* synthetic */ ValidationPass[] $VALUES;
    
    public static ValidationPass[] values() {
        return ValidationPass.$VALUES.clone();
    }
    
    public static ValidationPass valueOf(final String name) {
        return Enum.valueOf(ValidationPass.class, name);
    }
    
    static {
        $VALUES = new ValidationPass[] { ValidationPass.EARLY, ValidationPass.LATE, ValidationPass.FINAL };
    }
}
