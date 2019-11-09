package f.b.q.i;

public class o$u
{
    public double jr;
    public double jc;
    public double jn;
    
    public o$u(final double v, final double v, final double v) {
        super();
        this.jr = v;
        this.jc = v;
        this.jn = v;
    }
    
    public o$u a(final o$u v) {
        return new o$u(this.jr + v.jr, this.jc + v.jc, this.jn + v.jn);
    }
    
    public o$u a(final double v, final double v, final double v) {
        return new o$u(this.jr + v, this.jc + v, this.jn + v);
    }
    
    public o$u b(final o$u v) {
        return new o$u(this.jr - v.jr, this.jc - v.jc, this.jn - v.jn);
    }
    
    public o$u b(final double v, final double v, final double v) {
        return new o$u(this.jr - v, this.jc - v, this.jn - v);
    }
    
    public o$u nm() {
        final double v = Math.sqrt(this.jr * this.jr + this.jc * this.jc + this.jn * this.jn);
        return new o$u(this.jr / v, this.jc / v, this.jn / v);
    }
    
    public double g(final o$u v) {
        return this.jr * v.jr + this.jc * v.jc + this.jn * v.jn;
    }
    
    public o$u r(final o$u v) {
        return new o$u(this.jc * v.jn - this.jn * v.jc, this.jn * v.jr - this.jr * v.jn, this.jr * v.jc - this.jc * v.jr);
    }
    
    public o$u g(final double v) {
        return new o$u(this.jr * v, this.jc * v, this.jn * v);
    }
    
    public o$u r(final double v) {
        return new o$u(this.jr / v, this.jc / v, this.jn / v);
    }
    
    public double ns() {
        return Math.sqrt(this.jr * this.jr + this.jc * this.jc + this.jn * this.jn);
    }
    
    public o$u c(final o$u v) {
        this.jr += v.jr;
        this.jc += v.jc;
        this.jn += v.jn;
        return this;
    }
    
    public o$u g(final double v, final double v, final double v) {
        this.jr += v;
        this.jc += v;
        this.jn += v;
        return this;
    }
    
    public o$u n(final o$u v) {
        this.jr -= v.jr;
        this.jc -= v.jc;
        this.jn -= v.jn;
        return this;
    }
    
    public o$u r(final double v, final double v, final double v) {
        this.jr -= v;
        this.jc -= v;
        this.jn -= v;
        return this;
    }
    
    public o$u ne() {
        final double v = Math.sqrt(this.jr * this.jr + this.jc * this.jc + this.jn * this.jn);
        this.jr /= v;
        this.jc /= v;
        this.jn /= v;
        return this;
    }
    
    public o$u l(final o$u v) {
        this.jr = this.jc * v.jn - this.jn * v.jc;
        this.jc = this.jn * v.jr - this.jr * v.jn;
        this.jn = this.jr * v.jc - this.jc * v.jr;
        return this;
    }
    
    public o$u c(final double v) {
        this.jr *= v;
        this.jc *= v;
        this.jn *= v;
        return this;
    }
    
    public o$u n(final double v) {
        this.jr /= v;
        this.jc /= v;
        this.jn /= v;
        return this;
    }
    
    @Override
    public String toString() {
        return "(X: " + this.jr + " Y: " + this.jc + " Z: " + this.jn + ")";
    }
}
