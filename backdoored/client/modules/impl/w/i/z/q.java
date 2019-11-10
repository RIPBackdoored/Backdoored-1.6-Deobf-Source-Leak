package f.b.o.g.w.i.z;

public abstract class q
{
    private float ug;
    private float ur;
    private float uc;
    private float un;
    
    public q(final float v, final float v, final float v, final float v) {
        super();
        this.ug = v;
        this.ur = v;
        this.uc = v;
        this.un = v;
    }
    
    public float rr() {
        return this.ug;
    }
    
    public void n(final float v) {
        this.ug = v;
    }
    
    public float rc() {
        return this.ur;
    }
    
    public void l(final float v) {
        this.ur = v;
    }
    
    public float rn() {
        return this.uc;
    }
    
    public void i(final float v) {
        this.uc = v;
    }
    
    public float rl() {
        return this.un;
    }
    
    public void f(final float v) {
        this.un = v;
    }
    
    public abstract void c(final int p0, final int p1);
    
    public abstract void r(final int p0);
    
    public abstract void a(final int p0, final int p1, final int p2);
    
    public abstract void ri();
    
    public boolean n(final int v, final int v) {
        return v > this.rr() && v > this.rc() && v < this.rr() + this.rn() && v < this.rc() + this.rl();
    }
}
