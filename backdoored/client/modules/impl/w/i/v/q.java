package f.b.o.g.w.i.v;

import net.minecraft.client.*;
import f.b.o.c.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.util.*;

public class q
{
    private static Minecraft mc;
    private float up;
    private float um;
    private float us;
    private float ue;
    private float ux;
    private boolean uz;
    private boolean uo;
    private float ut;
    private float uw;
    private o uu;
    private ArrayList<f.b.o.g.w.i.z.q> uh;
    
    public q(final o v, final ArrayList<f.b.o.g.w.i.z.q> v, final float v, final float v, final float v, final float v) {
        super();
        this.up = v;
        this.um = v;
        this.us = v;
        this.ue = v;
        this.ux = v;
        this.uu = v;
        this.uh = v;
    }
    
    public float rr() {
        return this.up;
    }
    
    public void n(final float v) {
        this.up = v;
    }
    
    public float rc() {
        return this.um;
    }
    
    public void l(final float v) {
        this.um = v;
    }
    
    public float rn() {
        return this.us;
    }
    
    public void i(final float v) {
        this.us = v;
    }
    
    public float rl() {
        return this.ue;
    }
    
    public void f(final float v) {
        this.ue = v;
    }
    
    public float rq() {
        return this.ux;
    }
    
    public void q(final float v) {
        this.ux = v;
    }
    
    public boolean rk() {
        return this.uz;
    }
    
    public void r(final boolean v) {
        this.uz = v;
    }
    
    public boolean rp() {
        return this.uo;
    }
    
    public void c(final boolean v) {
        this.uo = v;
    }
    
    public o rm() {
        return this.uu;
    }
    
    public void b(final o v) {
        this.uu = v;
    }
    
    public ArrayList<f.b.o.g.w.i.z.q> rs() {
        return this.uh;
    }
    
    public void a(final ArrayList<f.b.o.g.w.i.z.q> v) {
        this.uh = v;
    }
    
    public void l(final int v, final int v) {
        Gui.drawRect((int)this.rr(), (int)this.rc(), (int)this.rr() + (int)this.rn(), (int)this.rc() + (int)this.rl(), 1426063360);
        q.mc.fontRenderer.drawStringWithShadow(this.uu.ne.co, this.rr() + this.rn() / 2.0f - q.mc.fontRenderer.getStringWidth(this.uu.ne.co) / 2, this.rc() + 3.0f, -1);
        if (this.rp()) {
            Gui.drawRect((int)this.rr() + 2, (int)this.rc() + (int)this.rq() - 1, (int)this.rr() + (int)this.rn() - 2, (int)this.rc() + (int)this.rq(), 1426063360);
        }
        if (this.rk()) {
            this.up = v + this.ut;
            this.um = v + this.uw;
            if (!Mouse.isButtonDown(0)) {
                this.r(false);
            }
        }
        if (this.rp()) {
            float v2 = this.rq();
            for (final f.b.o.g.w.i.z.q v3 : this.uh) {
                v3.c(v, v);
                v3.n(this.rr() + 2.0f);
                v3.l(this.rc() + v2 + 2.0f);
                v2 += 13.0f;
            }
            this.f(v2 + 3.0f);
        }
        else {
            this.f(this.rq());
        }
    }
    
    public void r(final int v) {
        if (this.rp()) {
            for (final f.b.o.g.w.i.z.q v2 : this.uh) {
                v2.r(v);
            }
        }
    }
    
    public void a(final int v, final int v, final int v) {
        if (this.i(v, v)) {
            if (v == 0) {
                this.ut = this.rr() - v;
                this.uw = this.rc() - v;
                this.uz = true;
            }
            else if (v == 1) {
                this.uo = !this.uo;
            }
        }
        if (this.rp()) {
            for (final f.b.o.g.w.i.z.q v2 : this.uh) {
                v2.a(v, v, v);
            }
        }
    }
    
    public boolean i(final int v, final int v) {
        return v > this.rr() && v > this.rc() && v < this.rr() + this.rn() && v < this.rc() + this.rq();
    }
    
    public void ri() {
        this.uz = false;
    }
    
    static {
        q.mc = Minecraft.getMinecraft();
    }
}
