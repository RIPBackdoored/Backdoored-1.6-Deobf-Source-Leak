package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import f.b.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;

@c$b(name = "Debug Crosshair", description = "Show f3 crosshair", category = f.b.o.c.c.CLIENT)
public class DebugCrosshair extends c
{
    public DebugCrosshair() {
        super();
    }
    
    @SubscribeEvent
    public void a(final RenderGameOverlayEvent v) {
        if (this.bu() && v.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            v.setCanceled(true);
            final int v2 = new ScaledResolution(DebugCrosshair.mc).getScaledWidth();
            final int v3 = new ScaledResolution(DebugCrosshair.mc).getScaledHeight();
            final float v4 = (float)ObfuscationReflectionHelper.getPrivateValue((Class)Gui.class, (Object)p.mc.ingameGUI, new String[] { "zLevel", "field_73735_i" });
            a(v.getPartialTicks(), v2, v3, v4);
        }
    }
    
    private static void a(final float v, final int v, final int v, final float v) {
        final GameSettings v2 = DebugCrosshair.mc.gameSettings;
        if (v2.thirdPersonView == 0) {
            if (DebugCrosshair.mc.playerController.isSpectator() && DebugCrosshair.mc.pointedEntity == null) {
                final RayTraceResult v3 = DebugCrosshair.mc.objectMouseOver;
                if (v3 == null || v3.typeOfHit != RayTraceResult.Type.BLOCK) {
                    return;
                }
                final BlockPos v4 = v3.getBlockPos();
                final IBlockState v5 = DebugCrosshair.mc.world.getBlockState(v4);
                if (!v5.getBlock().hasTileEntity(v5) || !(DebugCrosshair.mc.world.getTileEntity(v4) instanceof IInventory)) {
                    return;
                }
            }
            if (!v2.hideGUI) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(v / 2), (float)(v / 2), v);
                final Entity v6 = DebugCrosshair.mc.getRenderViewEntity();
                if (v6 != null) {
                    GlStateManager.rotate(v6.prevRotationPitch + (v6.rotationPitch - v6.prevRotationPitch) * v, -1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(v6.prevRotationYaw + (v6.rotationYaw - v6.prevRotationYaw) * v, 0.0f, 1.0f, 0.0f);
                    GlStateManager.scale(-1.0f, -1.0f, -1.0f);
                    OpenGlHelper.renderDirections(10);
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}
