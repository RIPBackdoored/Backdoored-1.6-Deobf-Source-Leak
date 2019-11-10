package javassist;

class CtClass$1 extends ClassMap {
    final /* synthetic */ CtClass this$0;
    
    CtClass$1(final CtClass this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void put(final String oldname, final String newname) {
        this.put0(oldname, newname);
    }
    
    @Override
    public Object get(final Object jvmClassName) {
        final String n = ClassMap.toJavaName((String)jvmClassName);
        this.put0(n, n);
        return null;
    }
    
    @Override
    public void fix(final String name) {
    }
}