package f.b.i.c;

public class c extends o
{
    public static final int cv = 100;
    public static final int cd = 20;
    private static final int cy = 25;
    private static int cj;
    public int na;
    public boolean nb;
    public int ng;
    public int nr;
    
    public c(final String v) {
        super(25, c.cj, 100, 20, v, false, true, new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
        this.na = 1;
        this.nb = false;
        this.ng = 0;
        this.nr = 0;
        c.cj += 21;
    }
    
    static {
        c.cj = 25;
    }
}
