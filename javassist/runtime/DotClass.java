package javassist.runtime;

public class DotClass
{
    public DotClass() {
        super();
    }
    
    public static NoClassDefFoundError fail(final ClassNotFoundException e) {
        return new NoClassDefFoundError(e.getMessage());
    }
}
