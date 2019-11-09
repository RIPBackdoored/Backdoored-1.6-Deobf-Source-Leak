package f.b.o.x;

public enum o
{
    vp, 
    vm, 
    vs, 
    ve, 
    vx, 
    vz;
    
    private static final /* synthetic */ o[] $VALUES;
    
    public static o[] values() {
        return o.$VALUES.clone();
    }
    
    public static o valueOf(final String v) {
        return Enum.valueOf(o.class, v);
    }
    
    static {
        $VALUES = new o[] { o.vp, o.vm, o.vs, o.ve, o.vx, o.vz };
    }
}
