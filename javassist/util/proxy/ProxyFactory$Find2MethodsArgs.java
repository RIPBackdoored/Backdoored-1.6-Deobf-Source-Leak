package javassist.util.proxy;

static class Find2MethodsArgs
{
    String methodName;
    String delegatorName;
    String descriptor;
    int origIndex;
    
    Find2MethodsArgs(final String mname, final String dname, final String desc, final int index) {
        super();
        this.methodName = mname;
        this.delegatorName = dname;
        this.descriptor = desc;
        this.origIndex = index;
    }
}
