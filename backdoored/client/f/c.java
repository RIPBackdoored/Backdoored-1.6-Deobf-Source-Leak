package f.b.f;

import f.b.i.c.*;

public class c
{
    private String db;
    private String dg;
    public Class dr;
    private String[] dc;
    private double dn;
    private double dl;
    public f.b.o.g.c di;
    private String df;
    private Object dq;
    public boolean dk;
    public o dp;
    
    public c(final String v, final f.b.o.g.c v) {
        super();
        this.db = v;
        this.dr = String.class;
        this.di = v;
        this.dg = "bind";
        this.df = v.lq + "-bind";
        this.dk = false;
        this.dq = "NONE";
        this.dp = new a("Bind: " + this.dq);
        b.dm.add(this);
    }
    
    public c(final String v, final f.b.o.g.c v, final String v, final String[] v) {
        super();
        this.db = v;
        this.dr = String.class;
        this.di = v;
        this.dc = v;
        this.dg = "mode";
        this.df = v.lq + "-" + v;
        this.dq = v;
        this.dp = new a(v + ": " + this.dq);
        b.dm.add(this);
    }
    
    public c(final String v, final f.b.o.g.c v, final boolean v) {
        super();
        this.db = v;
        this.dr = Boolean.class;
        this.di = v;
        this.dg = "toggle";
        this.df = v.lq + "-" + v;
        this.dq = v;
        this.dp = new a(v + ": " + this.dq);
        b.dm.add(this);
    }
    
    public c(final String v, final f.b.o.g.c v, final double v, final double v, final double v) {
        super();
        this.db = v;
        this.dr = Double.class;
        this.di = v;
        this.dq = v;
        this.dn = v;
        this.dl = v;
        this.dg = "slider";
        this.df = v.lq + "-" + v;
        this.dk = false;
        this.dq = v;
        this.dp = new a(v + ": " + this.dq);
        b.dm.add(this);
    }
    
    public c(final String v, final f.b.o.g.c v, final int v, final int v, final int v) {
        super();
        this.db = v;
        this.dr = Integer.class;
        this.di = v;
        this.dq = v;
        this.dn = v;
        this.dl = v;
        this.dg = "sliderINT";
        this.df = v.lq + "-" + v;
        this.dk = false;
        this.dq = v;
        this.dp = new a(v + ": " + this.dq);
        b.dm.add(this);
    }
    
    public String rh() {
        return this.db;
    }
    
    public String cn() {
        return this.df;
    }
    
    public void g(final Object v) {
        this.dq = v;
    }
    
    public Object cl() {
        return this.dq;
    }
    
    public String ci() {
        return String.valueOf(this.dq);
    }
    
    public String[] cf() {
        return this.dc;
    }
    
    public boolean cq() {
        return (boolean)this.dq;
    }
    
    public double ck() {
        if (this.dq instanceof Integer) {
            return (double)this.dq;
        }
        return (double)this.dq;
    }
    
    public int cp() {
        if (this.dq instanceof Integer) {
            return (int)this.dq;
        }
        return (int)this.dq;
    }
    
    public double cm() {
        return this.dn;
    }
    
    public double cs() {
        return this.dl;
    }
    
    public boolean ce() {
        return this.dg.equalsIgnoreCase("mode");
    }
    
    public boolean cx() {
        return this.dg.equalsIgnoreCase("toggle");
    }
    
    public boolean cz() {
        return this.dg.equalsIgnoreCase("slider");
    }
    
    public boolean co() {
        return this.dg.equalsIgnoreCase("sliderINT");
    }
    
    public boolean ct() {
        return this.dg.equalsIgnoreCase("SliderINT") || this.dg.equalsIgnoreCase("SliderD");
    }
    
    public boolean cw() {
        return this.dg.equalsIgnoreCase("bind");
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.dr.cast(this.dq));
    }
}
