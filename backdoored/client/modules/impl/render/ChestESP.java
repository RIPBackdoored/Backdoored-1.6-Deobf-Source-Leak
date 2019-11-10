package f.b.o.g.render;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import net.minecraft.tileentity.*;
import f.b.q.i.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Chest ESP", description = "yes", category = f.b.o.c.c.RENDER)
public class ChestESP extends c
{
    private f.b.f.c zd;
    private f.b.f.c zy;
    private f.b.f.c zj;
    private f.b.f.c oa;
    private f.b.f.c ob;
    private f.b.f.c og;
    private f.b.f.c or;
    private f.b.f.c oc;
    private f.b.f.c on;
    private f.b.f.c ol;
    private f.b.f.c oi;
    private f.b.f.c of;
    private f.b.f.c oq;
    private f.b.f.c ok;
    private f.b.f.c op;
    private f.b.f.c om;
    private f.b.f.c os;
    private f.b.f.c oe;
    
    public ChestESP() {
        super();
        this.zd = new f.b.f.c("No Nether", this, false);
        this.zy = new f.b.f.c("Chams", this, true);
        this.zj = new f.b.f.c("Outlines", this, true);
        this.oa = new f.b.f.c("Chests", this, true);
        this.ob = new f.b.f.c("Ender Chests", this, true);
        this.og = new f.b.f.c("Beds", this, true);
        this.or = new f.b.f.c("Chests R", this, 1.0, 0.0, 1.0);
        this.oc = new f.b.f.c("Chests G", this, 1.0, 0.0, 1.0);
        this.on = new f.b.f.c("Chests B", this, 0.0, 0.0, 1.0);
        this.ol = new f.b.f.c("Chests A", this, 1.0, 0.0, 1.0);
        this.oi = new f.b.f.c("Beds R", this, 1.0, 0.0, 1.0);
        this.of = new f.b.f.c("Beds G", this, 1.0, 0.0, 1.0);
        this.oq = new f.b.f.c("Beds B", this, 0.0, 0.0, 1.0);
        this.ok = new f.b.f.c("Beds A", this, 1.0, 0.0, 1.0);
        this.op = new f.b.f.c("E Chests R", this, 0.0, 0.0, 1.0);
        this.om = new f.b.f.c("E Chests G", this, 1.0, 0.0, 1.0);
        this.os = new f.b.f.c("E Chests B", this, 0.0, 0.0, 1.0);
        this.oe = new f.b.f.c("E Chests A", this, 1.0, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void a(final RenderWorldLastEvent v) {
        if (!this.bu()) {
            return;
        }
        if (this.zd.cq() && ChestESP.mc.player.dimension == -1) {
            return;
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        final double v2 = ChestESP.mc.getRenderManager().viewerPosX;
        final double v3 = ChestESP.mc.getRenderManager().viewerPosY;
        final double v4 = ChestESP.mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glTranslated(-v2, -v3, -v4);
        for (final TileEntity v5 : ChestESP.mc.world.loadedTileEntityList) {
            final boolean v6 = this.oa.cq() && v5 instanceof TileEntityChest;
            final boolean v7 = this.og.cq() && v5 instanceof TileEntityBed;
            final boolean v8 = this.ob.cq() && v5 instanceof TileEntityEnderChest;
            if (!v6 && !v7 && !v8) {
                continue;
            }
            float v9 = 0.0f;
            float v10 = 0.0f;
            float v11 = 0.0f;
            float v12 = 0.0f;
            if (v6) {
                v9 = (float)this.or.ck();
                v10 = (float)this.oc.ck();
                v11 = (float)this.on.ck();
                v12 = (float)this.ol.ck();
            }
            if (v7) {
                v9 = (float)this.oi.ck();
                v10 = (float)this.of.ck();
                v11 = (float)this.oq.ck();
                v12 = (float)this.ok.ck();
            }
            if (v8) {
                v9 = (float)this.op.ck();
                v10 = (float)this.om.ck();
                v11 = (float)this.os.ck();
                v12 = (float)this.oe.ck();
            }
            AxisAlignedBB v13 = j.g(v5.getPos());
            if (v5 instanceof TileEntityChest) {
                final TileEntityChest v14 = (TileEntityChest)v5;
                if (v14.adjacentChestXPos != null) {
                    continue;
                }
                if (v14.adjacentChestZPos != null) {
                    continue;
                }
                if (v14.adjacentChestXNeg != null) {
                    v13 = v13.union(j.g(v14.adjacentChestXNeg.getPos()));
                }
                else if (v14.adjacentChestZNeg != null) {
                    v13 = v13.union(j.g(v14.adjacentChestZNeg.getPos()));
                }
            }
            GL11.glColor4f(v9, v10, v11, v12);
            if (this.zy.cq()) {
                j.b(v13);
            }
            if (this.zj.cq()) {
                j.g(v13);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
