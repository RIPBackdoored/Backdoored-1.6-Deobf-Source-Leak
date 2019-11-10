package javassist.bytecode;

public static class BootstrapMethod
{
    public int methodRef;
    public int[] arguments;
    
    public BootstrapMethod(final int method, final int[] args) {
        super();
        this.methodRef = method;
        this.arguments = args;
    }
}
