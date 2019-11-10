package org.spongepowered.asm.mixin.injection.throwables;

public class InjectionError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public InjectionError() {
        super();
    }
    
    public InjectionError(final String message) {
        super(message);
    }
    
    public InjectionError(final Throwable cause) {
        super(cause);
    }
    
    public InjectionError(final String message, final Throwable cause) {
        super(message, cause);
    }
}
