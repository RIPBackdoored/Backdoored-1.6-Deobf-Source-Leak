package org.spongepowered.asm.mixin.injection;

enum ShiftByViolationBehaviour
{
    IGNORE, 
    WARN, 
    ERROR;
    
    private static final /* synthetic */ ShiftByViolationBehaviour[] $VALUES;
    
    public static ShiftByViolationBehaviour[] values() {
        return ShiftByViolationBehaviour.$VALUES.clone();
    }
    
    public static ShiftByViolationBehaviour valueOf(final String name) {
        return Enum.valueOf(ShiftByViolationBehaviour.class, name);
    }
    
    static {
        $VALUES = new ShiftByViolationBehaviour[] { ShiftByViolationBehaviour.IGNORE, ShiftByViolationBehaviour.WARN, ShiftByViolationBehaviour.ERROR };
    }
}
