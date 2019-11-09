package f.b.q.i;

public class o$m
{
    public o$u yu;
    public o$u yh;
    
    public o$m(final double v, final double v, final double v, final double v, final double v, final double v) {
        super();
        this.yu = new o$u(0.0, 0.0, 0.0);
        this.yh = new o$u(0.0, 0.0, 0.0);
        this.yu.jr = v;
        this.yu.jc = v;
        this.yu.jn = v;
        this.yh.jr = v;
        this.yh.jc = v;
        this.yh.jn = v;
    }
    
    public o$u a(final o$m v) {
        final double v2 = this.yu.jr;
        final double v3 = this.yh.jr;
        final double v4 = v.yu.jr;
        final double v5 = v.yh.jr;
        final double v6 = this.yu.jc;
        final double v7 = this.yh.jc;
        final double v8 = v.yu.jc;
        final double v9 = v.yh.jc;
        final double v10 = -(v2 * v9 - v4 * v9 - v5 * (v6 - v8));
        final double v11 = v3 * v9 - v5 * v7;
        if (v11 == 0.0) {
            return this.b(v);
        }
        final double v12 = v10 / v11;
        final o$u v13 = new o$u(0.0, 0.0, 0.0);
        v13.jr = this.yu.jr + this.yh.jr * v12;
        v13.jc = this.yu.jc + this.yh.jc * v12;
        v13.jn = this.yu.jn + this.yh.jn * v12;
        return v13;
    }
    
    private o$u b(final o$m v) {
        final double v2 = this.yu.jr;
        final double v3 = this.yh.jr;
        final double v4 = v.yu.jr;
        final double v5 = v.yh.jr;
        final double v6 = this.yu.jn;
        final double v7 = this.yh.jn;
        final double v8 = v.yu.jn;
        final double v9 = v.yh.jn;
        final double v10 = -(v2 * v9 - v4 * v9 - v5 * (v6 - v8));
        final double v11 = v3 * v9 - v5 * v7;
        if (v11 == 0.0) {
            return this.g(v);
        }
        final double v12 = v10 / v11;
        final o$u v13 = new o$u(0.0, 0.0, 0.0);
        v13.jr = this.yu.jr + this.yh.jr * v12;
        v13.jc = this.yu.jc + this.yh.jc * v12;
        v13.jn = this.yu.jn + this.yh.jn * v12;
        return v13;
    }
    
    private o$u g(final o$m v) {
        final double v2 = this.yu.jc;
        final double v3 = this.yh.jc;
        final double v4 = v.yu.jc;
        final double v5 = v.yh.jc;
        final double v6 = this.yu.jn;
        final double v7 = this.yh.jn;
        final double v8 = v.yu.jn;
        final double v9 = v.yh.jn;
        final double v10 = -(v2 * v9 - v4 * v9 - v5 * (v6 - v8));
        final double v11 = v3 * v9 - v5 * v7;
        if (v11 == 0.0) {
            return null;
        }
        final double v12 = v10 / v11;
        final o$u v13 = new o$u(0.0, 0.0, 0.0);
        v13.jr = this.yu.jr + this.yh.jr * v12;
        v13.jc = this.yu.jc + this.yh.jc * v12;
        v13.jn = this.yu.jn + this.yh.jn * v12;
        return v13;
    }
    
    public o$u a(final o$u v, final o$u v) {
        final o$u v2 = new o$u(this.yu.jr, this.yu.jc, this.yu.jn);
        final double v3 = v.b(this.yu).g(v) / this.yh.g(v);
        v2.c(this.yh.g(v3));
        if (this.yh.g(v) == 0.0) {
            return null;
        }
        return v2;
    }
}
