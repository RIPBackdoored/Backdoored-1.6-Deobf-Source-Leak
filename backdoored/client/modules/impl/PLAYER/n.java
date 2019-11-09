package f.b.o.g.t;

import f.b.o.g.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import f.b.j.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

@c$b(name = "Inventory Preview", description = "Shows you a preview of whats in your inv", category = f.b.o.c.c.PLAYER)
public class n extends c
{
    private static final ResourceLocation resourceLocation;
    private static final int xo = 5;
    private static final int xt = 1;
    private static final int xw = 18;
    private f.b.f.c xu;
    private f.b.f.c xh;
    
    public n() {
        super();
        this.xu = new f.b.f.c("x", this, 2, 0, n.mc.displayWidth + 100);
        this.xh = new f.b.f.c("y", this, 2, 0, n.mc.displayHeight + 100);
    }
    
    public void bm() {
        if (!this.bu()) {
            return;
        }
        final int v = this.xu.cp();
        final int v2 = this.xh.cp();
        final NonNullList<ItemStack> v3 = (NonNullList<ItemStack>)n.mc.player.inventory.mainInventory;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(0.0f, 0.0f, 700.0f);
        n.mc.getTextureManager().bindTexture(n.resourceLocation);
        RenderHelper.disableStandardItemLighting();
        this.b(v, v2, 9, 3, 1973019);
        RenderHelper.enableGUIStandardItemLighting();
        for (int v4 = 9; v4 < v3.size(); ++v4) {
            final ItemStack v5 = (ItemStack)v3.get(v4);
            final int v6 = v + 6 + v4 % 9 * 18;
            final int v7 = v2 + 6 + v4 / 9 * 18 - 18;
            if (!v5.isEmpty()) {
                n.mc.getRenderItem().renderItemAndEffectIntoGUI(v5, v6, v7);
                n.mc.getRenderItem().renderItemOverlays(g.fontRenderer, v5, v6, v7);
            }
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void b(final int v, final int v, final int v, final int v, final int v) {
        n.mc.getTextureManager().bindTexture(n.resourceLocation);
        GlStateManager.color(((v & 0xFF0000) >> 16) / 255.0f, ((v & 0xFF00) >> 8) / 255.0f, (v & 0xFF) / 255.0f);
        RenderHelper.disableStandardItemLighting();
        Gui.drawModalRectWithCustomSizedTexture(v, v, 0.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(v + 5 + 18 * v, v + 5 + 18 * v, 25.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(v + 5 + 18 * v, v, 25.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(v, v + 5 + 18 * v, 0.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        for (int v2 = 0; v2 < v; ++v2) {
            Gui.drawModalRectWithCustomSizedTexture(v, v + 5 + 18 * v2, 0.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            Gui.drawModalRectWithCustomSizedTexture(v + 5 + 18 * v, v + 5 + 18 * v2, 25.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            for (int v3 = 0; v3 < v; ++v3) {
                if (v2 == 0) {
                    Gui.drawModalRectWithCustomSizedTexture(v + 5 + 18 * v3, v, 6.0f, 0.0f, 18, 5, 256.0f, 256.0f);
                    Gui.drawModalRectWithCustomSizedTexture(v + 5 + 18 * v3, v + 5 + 18 * v, 6.0f, 25.0f, 18, 5, 256.0f, 256.0f);
                }
                Gui.drawModalRectWithCustomSizedTexture(v + 5 + 18 * v3, v + 5 + 18 * v2, 6.0f, 6.0f, 18, 18, 256.0f, 256.0f);
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/inv_slot.png");
    }
}
