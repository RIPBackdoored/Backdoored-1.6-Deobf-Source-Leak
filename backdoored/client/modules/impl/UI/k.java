package f.b.o.g.w;

import f.b.o.g.*;
import java.time.*;
import org.lwjgl.opengl.*;

@c$b(name = "Time", description = "Display time", category = f.b.o.c.c.UI)
public class k extends c
{
    private final f.b.f.c vl;
    private final f.b.f.c vi;
    private final f.b.f.c vf;
    private final f.b.f.c vq;
    private final f.b.f.c vk;
    
    public k() {
        super();
        this.vl = new f.b.f.c("Ingame Time", this, false);
        this.vi = new f.b.f.c("Radius", this, 1, 0, k.mc.displayWidth);
        this.vf = new f.b.f.c("x", this, 1, 0, (int)Math.round(k.mc.displayWidth * 1.2));
        this.vq = new f.b.f.c("y", this, 1, 0, (int)Math.round(k.mc.displayHeight * 1.2));
        this.vk = new f.b.f.c("Circle Accuracy", this, 1.0, 0.5, 2.0);
    }
    
    private static LocalTime n(final boolean v) {
        if (v) {
            final long v2 = k.mc.world.getWorldTime() % 24000L;
            final int v3 = (int)((v2 / 1000L + 6L) % 24L);
            final int v4 = (int)(60L * (v2 % 1000L) / 1000L);
            return LocalTime.of(v3, v4);
        }
        return LocalTime.now();
    }
    
    public void bm() {
        if (!this.bu()) {
            return;
        }
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glDisable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glTranslated((double)this.vf.cp(), (double)this.vq.cp(), 0.0);
        GL11.glLineWidth(1.0f);
        final LocalTime v = n(this.vl.cq());
        final double v2 = v.getSecond() + System.currentTimeMillis() % 1000L / 1000.0;
        final double v3 = v.getMinute() + v2 / 60.0;
        final double v4 = v.getHour() + v3 / 60.0;
        if (!this.vl.cq()) {
            a(1.0f, v2 / 60.0 * 100.0, this.vi.ck());
        }
        a(4.0f, v3 / 60.0 * 100.0, this.vi.ck() * 0.75);
        a(6.0f, v4 % 12.0 / 12.0 * 100.0, this.vi.ck() * 0.5);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        a(this.vi.ck(), false, this.vk.ck());
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glEnable(3553);
    }
    
    private static void a(final float v, final double v, final double v) {
        final double v2 = a(v);
        GL11.glLineWidth(v);
        GL11.glBegin(1);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d(-v * Math.sin(v2), v * Math.cos(v2));
        GL11.glEnd();
    }
    
    private static double a(final double v) {
        return v / 100.0 * 3.141592653589793 * 2.0 + 3.141592653589793;
    }
    
    private static void a(final double v, final boolean v, final double v) {
        final double v2 = 6.283185307179586;
        int v3 = (int)Math.max(15.0, v * v2 / 15.0);
        v3 *= (int)v;
        GL11.glBegin(v ? 4 : 2);
        for (int v4 = 0; v4 <= v3; ++v4) {
            if (v) {
                GL11.glVertex2d(0.0, 0.0);
            }
            GL11.glVertex2d(v * Math.cos(v4 * v2 / v3), v * Math.sin(v4 * v2 / v3));
        }
        GL11.glEnd();
    }
    
    private static void b(final double v) {
        final double v2 = 6.283185307179586;
        int v3 = (int)Math.max(15.0, v * v2 / 15.0);
        v3 *= 2;
        GL11.glBegin(4);
        for (int v4 = 0; v4 <= v3; ++v4) {
            GL11.glVertex2d(0.0, 0.0);
            GL11.glVertex2d(v * Math.cos(v4 * v2 / v3), v * Math.sin(v4 * v2 / v3));
        }
        GL11.glEnd();
    }
}
