package f.b.q.i;

import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.util.glu.*;

public final class o
{
    private static o jl;
    private final FloatBuffer ji;
    private IntBuffer jf;
    private FloatBuffer jq;
    private FloatBuffer jk;
    private o$u jp;
    private o$u[] jm;
    private o$u[] js;
    private o$u je;
    private double jx;
    private double jz;
    private double jo;
    private double jt;
    private double jw;
    private double ju;
    private double jh;
    private double jv;
    private o$m jd;
    private o$m jy;
    private o$m jj;
    private o$m baa;
    private float bab;
    private float bag;
    private o$u bar;
    
    private o() {
        super();
        this.ji = BufferUtils.createFloatBuffer(3);
    }
    
    public static o nx() {
        if (o.jl == null) {
            o.jl = new o();
        }
        return o.jl;
    }
    
    public void a(final IntBuffer v, final FloatBuffer v, final FloatBuffer v, final double v, final double v) {
        this.jf = v;
        this.jq = v;
        this.jk = v;
        this.jo = v;
        this.jt = v;
        final float v2 = (float)Math.toDegrees(Math.atan(1.0 / this.jk.get(5)) * 2.0);
        this.bab = v2;
        this.jx = this.jf.get(2);
        this.jz = this.jf.get(3);
        this.bag = (float)Math.toDegrees(2.0 * Math.atan(this.jx / this.jz * Math.tan(Math.toRadians(this.bab) / 2.0)));
        final o$u v3 = new o$u(this.jq.get(12), this.jq.get(13), this.jq.get(14));
        final o$u v4 = new o$u(this.jq.get(0), this.jq.get(1), this.jq.get(2));
        final o$u v5 = new o$u(this.jq.get(4), this.jq.get(5), this.jq.get(6));
        final o$u v6 = new o$u(this.jq.get(8), this.jq.get(9), this.jq.get(10));
        final o$u v7 = new o$u(0.0, 1.0, 0.0);
        final o$u v8 = new o$u(1.0, 0.0, 0.0);
        final o$u v9 = new o$u(0.0, 0.0, 1.0);
        double v10 = Math.toDegrees(Math.atan2(v8.r(v4).ns(), v8.g(v4))) + 180.0;
        if (v6.jr < 0.0) {
            v10 = 360.0 - v10;
        }
        double v11 = 0.0;
        if ((-v6.jc > 0.0 && v10 >= 90.0 && v10 < 270.0) || (v6.jc > 0.0 && (v10 < 90.0 || v10 >= 270.0))) {
            v11 = Math.toDegrees(Math.atan2(v7.r(v5).ns(), v7.g(v5)));
        }
        else {
            v11 = -Math.toDegrees(Math.atan2(v7.r(v5).ns(), v7.g(v5)));
        }
        this.bar = this.a(v10, v11);
        final Matrix4f v12 = new Matrix4f();
        v12.load(this.jq.asReadOnlyBuffer());
        v12.invert();
        this.jp = new o$u(v12.m30, v12.m31, v12.m32);
        this.jm = this.a(this.jp.jr, this.jp.jc, this.jp.jn, v10, v11, v2, 1.0, this.jx / this.jz);
        this.js = this.a(this.jp.jr, this.jp.jc, this.jp.jn, v10 - 180.0, -v11, v2, 1.0, this.jx / this.jz);
        this.je = this.a(v10, v11).nm();
        this.jw = Math.toDegrees(Math.acos(this.jz * v / Math.sqrt(this.jx * v * this.jx * v + this.jz * v * this.jz * v)));
        this.ju = 360.0 - this.jw;
        this.jh = this.ju - 180.0;
        this.jv = this.jw + 180.0;
        this.baa = new o$m(this.jx * this.jo, 0.0, 0.0, 0.0, 1.0, 0.0);
        this.jd = new o$m(0.0, 0.0, 0.0, 1.0, 0.0, 0.0);
        this.jj = new o$m(0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
        this.jy = new o$m(0.0, this.jz * this.jt, 0.0, 1.0, 0.0, 0.0);
    }
    
    public o$e a(final double v, final double v, final double v, final o$b v, final boolean v) {
        if (this.jf != null && this.jq != null && this.jk != null) {
            final o$u v2 = new o$u(v, v, v);
            final boolean[] v3 = this.a(this.jm, this.jp, v, v, v);
            final boolean v4 = v3[0] || v3[1] || v3[2] || v3[3];
            if (v4) {
                final boolean v5 = v2.b(this.jp).g(this.je) <= 0.0;
                final boolean[] v6 = this.a(this.js, this.jp, v, v, v);
                final boolean v7 = v6[0] || v6[1] || v6[2] || v6[3];
                if ((v && !v7) || (v7 && v != o$b.yw)) {
                    if ((v && !v7) || (v == o$b.yt && v7)) {
                        double v8 = 0.0;
                        double v9 = 0.0;
                        if (GLU.gluProject((float)v, (float)v, (float)v, this.jq, this.jk, this.jf, this.ji)) {
                            if (v5) {
                                v8 = this.jx * this.jo - this.ji.get(0) * this.jo - this.jx * this.jo / 2.0;
                                v9 = this.jz * this.jt - (this.jz - this.ji.get(1)) * this.jt - this.jz * this.jt / 2.0;
                            }
                            else {
                                v8 = this.ji.get(0) * this.jo - this.jx * this.jo / 2.0;
                                v9 = (this.jz - this.ji.get(1)) * this.jt - this.jz * this.jt / 2.0;
                            }
                            final o$u v10 = new o$u(v8, v9, 0.0).ne();
                            v8 = v10.jr;
                            v9 = v10.jc;
                            final o$m v11 = new o$m(this.jx * this.jo / 2.0, this.jz * this.jt / 2.0, 0.0, v8, v9, 0.0);
                            double v12 = Math.toDegrees(Math.acos(v10.jc / Math.sqrt(v10.jr * v10.jr + v10.jc * v10.jc)));
                            if (v8 < 0.0) {
                                v12 = 360.0 - v12;
                            }
                            o$u v13 = new o$u(0.0, 0.0, 0.0);
                            if (v12 >= this.jw && v12 < this.jh) {
                                v13 = this.baa.a(v11);
                            }
                            else if (v12 >= this.jh && v12 < this.jv) {
                                v13 = this.jd.a(v11);
                            }
                            else if (v12 >= this.jv && v12 < this.ju) {
                                v13 = this.jj.a(v11);
                            }
                            else {
                                v13 = this.jy.a(v11);
                            }
                            return new o$e(v13.jr, v13.jc, v7 ? o$e$z.yd : o$e$z.yy);
                        }
                        return new o$e(0.0, 0.0, o$e$z.yj);
                    }
                    else if (v == o$b.yo && v7) {
                        if (GLU.gluProject((float)v, (float)v, (float)v, this.jq, this.jk, this.jf, this.ji)) {
                            double v8 = this.ji.get(0) * this.jo;
                            double v9 = (this.jz - this.ji.get(1)) * this.jt;
                            if (v5) {
                                v8 = this.jx * this.jo - v8;
                                v9 = this.jz * this.jt - v9;
                            }
                            if (v8 < 0.0) {
                                v8 = 0.0;
                            }
                            else if (v8 > this.jx * this.jo) {
                                v8 = this.jx * this.jo;
                            }
                            if (v9 < 0.0) {
                                v9 = 0.0;
                            }
                            else if (v9 > this.jz * this.jt) {
                                v9 = this.jz * this.jt;
                            }
                            return new o$e(v8, v9, v7 ? o$e$z.yd : o$e$z.yy);
                        }
                        return new o$e(0.0, 0.0, o$e$z.yj);
                    }
                }
                else {
                    if (GLU.gluProject((float)v, (float)v, (float)v, this.jq, this.jk, this.jf, this.ji)) {
                        double v8 = this.ji.get(0) * this.jo;
                        double v9 = (this.jz - this.ji.get(1)) * this.jt;
                        if (v5) {
                            v8 = this.jx * this.jo - v8;
                            v9 = this.jz * this.jt - v9;
                        }
                        return new o$e(v8, v9, v7 ? o$e$z.yd : o$e$z.yy);
                    }
                    return new o$e(0.0, 0.0, o$e$z.yj);
                }
            }
            else {
                if (GLU.gluProject((float)v, (float)v, (float)v, this.jq, this.jk, this.jf, this.ji)) {
                    final double v14 = this.ji.get(0) * this.jo;
                    final double v15 = (this.jz - this.ji.get(1)) * this.jt;
                    return new o$e(v14, v15, o$e$z.yv);
                }
                return new o$e(0.0, 0.0, o$e$z.yj);
            }
        }
        return new o$e(0.0, 0.0, o$e$z.yj);
    }
    
    public boolean[] a(final o$u[] v, final o$u v, final double v, final double v, final double v) {
        final o$u v2 = new o$u(v, v, v);
        final boolean v3 = this.a(new o$u[] { v, v[3], v[0] }, v2);
        final boolean v4 = this.a(new o$u[] { v, v[0], v[1] }, v2);
        final boolean v5 = this.a(new o$u[] { v, v[1], v[2] }, v2);
        final boolean v6 = this.a(new o$u[] { v, v[2], v[3] }, v2);
        return new boolean[] { v3, v4, v5, v6 };
    }
    
    public boolean a(final o$u[] v, final o$u v) {
        final o$u v2 = new o$u(0.0, 0.0, 0.0);
        final o$u v3 = v[1].b(v[0]);
        final o$u v4 = v[2].b(v[0]);
        final o$u v5 = v3.r(v4).ne();
        final double v6 = v2.b(v5).g(v[2]);
        final double v7 = v5.g(v) + v6;
        return v7 >= 0.0;
    }
    
    public o$u[] a(final double v, final double v, final double v, final double v, final double v, final double v, final double v, final double v) {
        final o$u v2 = this.a(v, v).ne();
        final double v3 = 2.0 * Math.tan(Math.toRadians(v / 2.0)) * v;
        final double v4 = v3 * v;
        final o$u v5 = this.a(v, v).ne();
        final o$u v6 = this.a(v, v - 90.0).ne();
        final o$u v7 = this.a(v + 90.0, 0.0).ne();
        final o$u v8 = new o$u(v, v, v);
        final o$u v9 = v5.a(v8);
        final o$u v10 = new o$u(v9.jr * v, v9.jc * v, v9.jn * v);
        final o$u v11 = new o$u(v10.jr + v6.jr * v3 / 2.0 - v7.jr * v4 / 2.0, v10.jc + v6.jc * v3 / 2.0 - v7.jc * v4 / 2.0, v10.jn + v6.jn * v3 / 2.0 - v7.jn * v4 / 2.0);
        final o$u v12 = new o$u(v10.jr - v6.jr * v3 / 2.0 - v7.jr * v4 / 2.0, v10.jc - v6.jc * v3 / 2.0 - v7.jc * v4 / 2.0, v10.jn - v6.jn * v3 / 2.0 - v7.jn * v4 / 2.0);
        final o$u v13 = new o$u(v10.jr + v6.jr * v3 / 2.0 + v7.jr * v4 / 2.0, v10.jc + v6.jc * v3 / 2.0 + v7.jc * v4 / 2.0, v10.jn + v6.jn * v3 / 2.0 + v7.jn * v4 / 2.0);
        final o$u v14 = new o$u(v10.jr - v6.jr * v3 / 2.0 + v7.jr * v4 / 2.0, v10.jc - v6.jc * v3 / 2.0 + v7.jc * v4 / 2.0, v10.jn - v6.jn * v3 / 2.0 + v7.jn * v4 / 2.0);
        return new o$u[] { v11, v12, v14, v13 };
    }
    
    public o$u[] nz() {
        return this.jm;
    }
    
    public float no() {
        return this.bag;
    }
    
    public float nt() {
        return this.bab;
    }
    
    public o$u nw() {
        return this.bar;
    }
    
    public o$u a(final double v, final double v) {
        final double v2 = Math.cos(-v * 0.01745329238474369 - 3.141592653589793);
        final double v3 = Math.sin(-v * 0.01745329238474369 - 3.141592653589793);
        final double v4 = -Math.cos(-v * 0.01745329238474369);
        final double v5 = Math.sin(-v * 0.01745329238474369);
        return new o$u(v3 * v4, v5, v2 * v4);
    }
}
