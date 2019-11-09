package f.b.o.g.a;

import f.b.o.g.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.*;

@c$b(name = "Map Tooltip", description = "Tooltips to preview maps", category = f.b.o.c.c.CLIENT)
public class g extends c
{
    private static final ResourceLocation resourceLocation;
    
    public g() {
        super();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void a(final ItemTooltipEvent v) {
    }
    
    @SubscribeEvent
    public void a(final RenderTooltipEvent.PostText v) {
        if (!this.bu()) {
            return;
        }
        if (!v.getStack().isEmpty() && v.getStack().getItem() instanceof ItemMap) {
            final MapData v2 = Items.FILLED_MAP.getMapData(v.getStack(), (World)g.mc.world);
            if (v2 != null) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderHelper.disableStandardItemLighting();
                g.mc.getTextureManager().bindTexture(g.resourceLocation);
                final Tessellator v3 = Tessellator.getInstance();
                final BufferBuilder v4 = v3.getBuffer();
                final int v5 = 7;
                final float v6 = 135.0f;
                final float v7 = 0.5f;
                GlStateManager.translate((float)v.getX(), v.getY() - v6 * v7 - 5.0f, 0.0f);
                GlStateManager.scale(v7, v7, v7);
                v4.begin(7, DefaultVertexFormats.POSITION_TEX);
                v4.pos((double)(-v5), (double)v6, 0.0).tex(0.0, 1.0).endVertex();
                v4.pos((double)v6, (double)v6, 0.0).tex(1.0, 1.0).endVertex();
                v4.pos((double)v6, (double)(-v5), 0.0).tex(1.0, 0.0).endVertex();
                v4.pos((double)(-v5), (double)(-v5), 0.0).tex(0.0, 0.0).endVertex();
                v3.draw();
                g.mc.entityRenderer.getMapItemRenderer().renderMap(v2, false);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
    
    static {
        resourceLocation = new ResourceLocation("textures/map/map_background.png");
    }
}
