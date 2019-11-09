package f.b.o.c;

public enum c
{
    no("Movement"), 
    nt("Render"), 
    nw("Player"), 
    nu("Combat"), 
    nh("Misc"), 
    nv("Exploits"), 
    nd("Client"), 
    ny("UIs"), 
    nj("ChatBot");
    
    public o la;
    private static final /* synthetic */ c[] $VALUES;
    
    public static c[] values() {
        return c.$VALUES.clone();
    }
    
    public static c valueOf(final String v) {
        return Enum.valueOf(c.class, v);
    }
    
    private c(final String v) {
        this.la = new o(v);
    }
    
    static {
        $VALUES = new c[] { c.no, c.nt, c.nw, c.nu, c.nh, c.nv, c.nd, c.ny, c.nj };
    }
}
