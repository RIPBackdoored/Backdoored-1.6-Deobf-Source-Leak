package org.spongepowered.asm.mixin.throwables;

public class MixinException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public MixinException() {
        super();
    }
    
    public MixinException(final String message) {
        super(message);
    }
    
    public MixinException(final Throwable cause) {
        super(cause);
    }
    
    public MixinException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
