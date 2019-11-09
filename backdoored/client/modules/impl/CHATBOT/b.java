package f.b.o.g.j;

import f.b.o.g.*;
import java.time.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import f.b.q.c.*;
import f.b.q.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.client.event.*;
import f.b.a.*;

@c$b(name = "Announcer", description = "Don't use this unless your a penis", category = f.b.o.c.c.CHATBOT)
public class b extends c
{
    private f.b.f.c le;
    private Instant lx;
    private Vec3d vec3d;
    private f.b.f.c lo;
    private Instant lt;
    private Block block;
    private int lu;
    private f.b.f.c lh;
    private Instant lv;
    private Block block;
    private int ly;
    private f.b.f.c lj;
    private Instant ia;
    private f.b.f.c ib;
    private f.b.f.c ig;
    private m ir;
    
    public b() {
        super();
        this.le = new f.b.f.c("Movement", this, true);
        this.lx = Instant.now();
        this.vec3d = null;
        this.lo = new f.b.f.c("Block Place", this, true);
        this.lt = Instant.now();
        this.block = null;
        this.lu = 0;
        this.lh = new f.b.f.c("Block Break", this, true);
        this.lv = Instant.now();
        this.block = null;
        this.ly = 0;
        this.lj = new f.b.f.c("Attack Entities", this, true);
        this.ia = Instant.now();
        this.ib = new f.b.f.c("Gui", this, true);
        this.ig = new f.b.f.c("Screenshot", this, true);
    }
    
    public void bv() {
        try {
            this.ir = new m();
        }
        catch (Exception v) {
            this.a(false);
            o.i("Failed to initialise Announcer script: " + v.getMessage(), "red");
            v.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void a(final bg$c v) {
        if (this.bu() && j.a(this.lx, Instant.now(), 60L) && this.le.cq()) {
            if (this.vec3d == null) {
                this.vec3d = b.mc.player.getPositionVector();
                return;
            }
            final int v2 = (int)Math.round(this.vec3d.distanceTo(b.mc.player.getPositionVector()));
            if (v2 > 0) {
                this.z(this.ir.a(v2));
                this.lx = Instant.now();
            }
        }
    }
    
    @SubscribeEvent
    public void a(final q v) {
        if (this.bu() && v.packet instanceof CPacketPlayerTryUseItemOnBlock && this.lo.cq()) {
            final CPacketPlayerTryUseItemOnBlock v2 = (CPacketPlayerTryUseItemOnBlock)v.packet;
            final ItemStack v3 = b.mc.player.getHeldItem(v2.getHand());
            if (v3.getItem() instanceof ItemBlock) {
                final Block v4 = ((ItemBlock)v3.getItem()).getBlock();
                if (this.block == null) {
                    this.block = v4;
                }
                if (this.block.equals(v4)) {
                    ++this.lu;
                }
            }
            if (j.a(this.lt, Instant.now(), 60L) && this.lu > 0) {
                this.z(this.ir.g(this.lu, v3.getDisplayName()));
                this.lt = Instant.now();
                this.block = null;
            }
        }
    }
    
    @SubscribeEvent
    public void a(final BlockEvent.BreakEvent v) {
        if (this.bu() && v.getPlayer().equals((Object)b.mc.player)) {
            final Block v2 = v.getState().getBlock();
            if (this.block == null) {
                this.block = v2;
            }
            if (this.block.equals(v2)) {
                ++this.ly;
            }
            if (j.a(this.lv, Instant.now(), 60L) && this.ly > 0) {
                this.z(this.ir.b(this.ly, v2.getLocalizedName()));
                this.lv = Instant.now();
                this.block = null;
            }
        }
    }
    
    @SubscribeEvent
    public void a(final AttackEntityEvent v) {
        if (this.bu() && v.getTarget() instanceof EntityLivingBase && j.a(this.ia, Instant.now(), 60L)) {
            this.z(this.ir.t(v.getTarget().getDisplayName().getUnformattedText()));
            this.ia = Instant.now();
        }
    }
    
    @SubscribeEvent
    public void b(final GuiOpenEvent v) {
        if (this.bu() && this.ib.cq() && v.getGui() != null && v.getGui() instanceof GuiInventory) {
            this.z(this.ir.a((GuiInventory)v.getGui()));
        }
    }
    
    @SubscribeEvent
    public void a(final ScreenshotEvent v) {
        if (this.bu() && this.ig.cq()) {
            this.z(this.ir.bj());
        }
    }
    
    @SubscribeEvent
    public void a(final r v) {
        if (this.bu()) {
            this.z(this.ir.ga());
        }
    }
    
    @SubscribeEvent
    public void a(final u v) {
        if (this.bu()) {
            this.z(this.ir.gb());
        }
    }
    
    @SubscribeEvent
    public void a(final bc v) {
        if (this.bu()) {
            this.z(this.ir.gg());
        }
    }
    
    @SubscribeEvent
    public void a(final bb v) {
        if (this.bu()) {
            this.z(this.ir.gr());
        }
    }
    
    private void z(String v) {
        if (v == null) {
            return;
        }
        v = this.ir.o(v);
        if (v == null) {
            return;
        }
        if (this.bu()) {
            b.mc.player.sendChatMessage(v + " thanks to Backdoored Client");
        }
    }
}
