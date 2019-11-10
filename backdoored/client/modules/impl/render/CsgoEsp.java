package f.b.o.g.render;

import f.b.o.g.*;
import net.minecraft.entity.player.*;
import f.b.q.i.*;
import \u0000f.\u0000b.\u0000q.\u0000i.*;
import java.util.function.*;
import java.util.stream.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Csgo esp", description = "swoopae on top", category = f.b.o.c.c.RENDER)
public class CsgoEsp extends c
{
    private f.b.f.c ox;
    private f.b.f.c oz;
    private f.b.f.c oo;
    private f.b.f.c ot;
    private f.b.f.c ow;
    
    public CsgoEsp() {
        super();
        this.ox = new f.b.f.c("r", this, 0.0, 0.0, 1.0);
        this.oz = new f.b.f.c("g", this, 0.0, 0.0, 1.0);
        this.oo = new f.b.f.c("b", this, 0.0, 0.0, 1.0);
        this.ot = new f.b.f.c("a", this, 1.0, 0.0, 1.0);
        this.ow = new f.b.f.c("Line Width", this, 1.0, 1.0, 4.0);
    }
    
    private void c(final float v) {
        for (final EntityPlayer v2 : CsgoEsp.mc.world.playerEntities) {
            if (v2.equals((Object)CsgoEsp.mc.player)) {
                continue;
            }
            final List<o$e> v3 = new LinkedList<o$e>();
            final AxisAlignedBB v4 = v2.getEntityBoundingBox().expand(0.1, 0.1, 0.1);
            v4.offset(-(v2.posX - v2.lastTickPosX) * (1.0f - v), -(v2.posY - v2.lastTickPosY) * (1.0f - v), -(v2.posZ - v2.lastTickPosZ) * (1.0f - v));
            final double v5 = CsgoEsp.mc.getRenderManager().viewerPosX;
            final double v6 = CsgoEsp.mc.getRenderManager().viewerPosY;
            final double v7 = CsgoEsp.mc.getRenderManager().viewerPosZ;
            v4.offset(-v5, -v6, -v7);
            for (double v8 = v4.minX; v8 <= v4.maxX; v8 += (v4.maxX - v4.minX) / 2.0) {
                for (double v9 = v4.minY; v9 <= v4.maxY; v9 += (v4.maxY - v4.minY) / 2.0) {
                    for (double v10 = v4.minZ; v10 <= v4.maxZ; v10 += (v4.maxZ - v4.minZ) / 2.0) {
                        final o$e v11 = o.nx().a(v8, v9, v10, o$b.yw, false);
                        if (!v11.a(o$e$z.yy)) {
                            v3.add(v11);
                        }
                    }
                }
            }
            if (v3.isEmpty()) {
                continue;
            }
            final List<Double> v12 = v3.stream().map((Function<? super Object, ?>)\u0000o.\u0000e::nq).collect((Collector<? super Object, ?, List<Double>>)Collectors.toList());
            final double v13 = Collections.min((Collection<? extends Double>)v12);
            final double v14 = Collections.max((Collection<? extends Double>)v12);
            final List<Double> v15 = v3.stream().map((Function<? super Object, ?>)\u0000o.\u0000e::nk).collect((Collector<? super Object, ?, List<Double>>)Collectors.toList());
            final double v16 = Collections.min((Collection<? extends Double>)v15);
            final double v17 = Collections.max((Collection<? extends Double>)v15);
            GL11.glBegin(2);
            GL11.glVertex2d(v14, v17);
            GL11.glVertex2d(v14, v16);
            GL11.glVertex2d(v13, v16);
            GL11.glVertex2d(v13, v17);
            GL11.glEnd();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @Override
    public void b(final RenderGameOverlayEvent.Post v) {
        if (!this.bu()) {
            return;
        }
        final boolean v2 = GL11.glGetBoolean(3042);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth((float)this.ow.ck());
        GL11.glColor4f((float)this.ox.ck(), (float)this.oz.ck(), (float)this.oo.ck(), (float)this.ot.ck());
        this.c(v.getPartialTicks());
        GL11.glDisable(2848);
        if (v2) {
            GlStateManager.enableBlend();
            GL11.glEnable(3042);
        }
        else {
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
        }
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
