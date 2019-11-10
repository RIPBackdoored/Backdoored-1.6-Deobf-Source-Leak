package org.spongepowered.asm.mixin.transformer;

enum ApplicatorPass
{
    MAIN, 
    PREINJECT, 
    INJECT;
    
    private static final /* synthetic */ ApplicatorPass[] $VALUES;
    
    public static ApplicatorPass[] values() {
        return ApplicatorPass.$VALUES.clone();
    }
    
    public static ApplicatorPass valueOf(final String name) {
        return Enum.valueOf(ApplicatorPass.class, name);
    }
    
    static {
        $VALUES = new ApplicatorPass[] { ApplicatorPass.MAIN, ApplicatorPass.PREINJECT, ApplicatorPass.INJECT };
    }
}
