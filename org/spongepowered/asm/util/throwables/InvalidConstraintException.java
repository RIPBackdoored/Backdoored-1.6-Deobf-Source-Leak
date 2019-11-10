package org.spongepowered.asm.util.throwables;

public class InvalidConstraintException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidConstraintException() {
        super();
    }
    
    public InvalidConstraintException(final String s) {
        super(s);
    }
    
    public InvalidConstraintException(final Throwable cause) {
        super(cause);
    }
    
    public InvalidConstraintException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
