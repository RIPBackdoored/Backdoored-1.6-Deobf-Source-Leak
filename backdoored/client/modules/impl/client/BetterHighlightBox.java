package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import f.b.j.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "BetterHighlightBox", description = "Better Highlight Box", category = f.b.o.c.c.CLIENT)
public class BetterHighlightBox extends c
{
    private f.b.f.c im;
    private f.b.f.c is;
    private f.b.f.c ie;
    private f.b.f.c ix;
    private f.b.f.c iz;
    
    public BetterHighlightBox() {
        super();
        this.im = new f.b.f.c("Width", this, 5.0, 0.0, 50.0);
        this.is = new f.b.f.c("Red", this, 0, 0, 255);
        this.ie = new f.b.f.c("Green", this, 0, 0, 255);
        this.ix = new f.b.f.c("Blue", this, 0, 0, 255);
        this.iz = new f.b.f.c("Alpha", this, 0.4, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void a(final DrawBlockHighlightEvent v) {
        if (this.bu()) {
            final float v2 = v.getPartialTicks();
            final EntityPlayer v3 = v.getPlayer();
            final RayTraceResult v4 = v.getTarget();
            if (v4.typeOfHit == RayTraceResult.Type.BLOCK) {
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.glLineWidth((float)this.im.ck());
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                final BlockPos v5 = v4.getBlockPos();
                final IBlockState v6 = BetterHighlightBox.mc.world.getBlockState(v5);
                if (v6.getMaterial() != Material.AIR && BetterHighlightBox.mc.world.getWorldBorder().contains(v5)) {
                    final double v7 = v3.lastTickPosX + (v3.posX - v3.lastTickPosX) * v2;
                    final double v8 = v3.lastTickPosY + (v3.posY - v3.lastTickPosY) * v2;
                    final double v9 = v3.lastTickPosZ + (v3.posZ - v3.lastTickPosZ) * v2;
                    b.drawSelectionBoundingBox(v6.getSelectedBoundingBox((World)BetterHighlightBox.mc.world, v5).grow(0.0020000000949949026).offset(-v7, -v8, -v9), (float)Math.min(Math.abs(this.is.cp() - 255), 244), (float)Math.min(Math.abs(this.ie.cp() - 255), 244), (float)Math.min(Math.abs(this.ix.cp() - 255), 244), (float)this.iz.ck());
                }
                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
            }
            v.setCanceled(true);
        }
    }
}
