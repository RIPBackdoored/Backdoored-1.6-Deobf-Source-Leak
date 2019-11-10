package f.b.o.g.client;

import f.b.o.g.*;
import net.minecraftforge.event.entity.player.*;
import f.b.q.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.items.*;
import f.b.j.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import f.b.o.g.a.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraftforge.registries.*;
import \u0000f.\u0000b.\u0000o.\u0000g.\u0000a.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

@c$b(name = "Shulker Preview", description = "Preview Shulkers via tooltip", category = f.b.o.c.c.CLIENT)
public class ShulkerPreview extends c
{
    static String[] qi;
    private static final ResourceLocation resourceLocation;
    private static List<ResourceLocation> qq;
    private static final int[][] qk;
    private static final int qp = 5;
    private static final int qm = 1;
    private static final int qs = 18;
    static final /* synthetic */ boolean qe;
    
    public ShulkerPreview() {
        super();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void a(final ItemTooltipEvent v) {
        if (a(v.getItemStack(), ShulkerPreview.qq) && v.getItemStack().hasTagCompound()) {
            NBTTagCompound v2 = a.g(v.getItemStack(), "BlockEntityTag", true);
            if (v2 != null) {
                if (!v2.hasKey("id", 8)) {
                    v2 = v2.copy();
                    v2.setString("id", "minecraft:shulker_box");
                }
                final TileEntity v3 = TileEntity.create((World)ShulkerPreview.mc.world, v2);
                if (v3 != null && v3.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
                    final List<String> v4 = (List<String>)v.getToolTip();
                    final List<String> v5 = new ArrayList<String>(v4);
                    for (int v6 = 1; v6 < v5.size(); ++v6) {
                        final String v7 = v5.get(v6);
                        if (!v7.startsWith("§") || v7.startsWith("§o")) {
                            v4.remove(v7);
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void a(final RenderTooltipEvent.PostText v) {
        if (!this.bu()) {
            return;
        }
        if (a(v.getStack(), ShulkerPreview.qq) && v.getStack().hasTagCompound()) {
            NBTTagCompound v2 = a.g(v.getStack(), "BlockEntityTag", true);
            if (v2 != null) {
                if (!v2.hasKey("id", 8)) {
                    v2 = v2.copy();
                    v2.setString("id", "minecraft:shulker_box");
                }
                final TileEntity v3 = TileEntity.create((World)ShulkerPreview.mc.world, v2);
                if (v3 != null && v3.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
                    final ItemStack v4 = v.getStack();
                    int v5 = v.getX() - 5;
                    int v6 = v.getY() - 70;
                    final IItemHandler v7 = (IItemHandler)v3.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
                    if (!ShulkerPreview.qe && v7 == null) {
                        throw new AssertionError();
                    }
                    final int v8 = v7.getSlots();
                    int[] v9 = { Math.min(v8, 9), Math.max(v8 / 9, 1) };
                    for (final int[] v10 : ShulkerPreview.qk) {
                        if (v10[0] * v10[1] == v8) {
                            v9 = v10;
                            break;
                        }
                    }
                    final int v11 = 10 + 18 * v9[0];
                    if (v6 < 0) {
                        v6 = v.getY() + v.getLines().size() * 10 + 5;
                    }
                    final ScaledResolution v12 = new ScaledResolution(ShulkerPreview.mc);
                    final int v13 = v5 + v11;
                    if (v13 > v12.getScaledWidth()) {
                        v5 -= v13 - v12.getScaledWidth();
                    }
                    GlStateManager.pushMatrix();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.color(1.0f, 1.0f, 1.0f);
                    GlStateManager.translate(0.0f, 0.0f, 700.0f);
                    ShulkerPreview.mc.getTextureManager().bindTexture(ShulkerPreview.resourceLocation);
                    RenderHelper.disableStandardItemLighting();
                    int v14 = -1;
                    if (((ItemBlock)v4.getItem()).getBlock() instanceof BlockShulkerBox) {
                        final EnumDyeColor v15 = ((BlockShulkerBox)((ItemBlock)v4.getItem()).getBlock()).getColor();
                        v14 = ItemDye.DYE_COLORS[v15.getDyeDamage()];
                    }
                    a(v5, v6, v9[0], v9[1], v14);
                    final RenderItem v16 = ShulkerPreview.mc.getRenderItem();
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.enableDepth();
                    for (int v17 = 0; v17 < v8; ++v17) {
                        final ItemStack v18 = v7.getStackInSlot(v17);
                        final int v19 = v5 + 6 + v17 % 9 * 18;
                        final int v20 = v6 + 6 + v17 / 9 * 18;
                        if (!v18.isEmpty()) {
                            v16.renderItemAndEffectIntoGUI(v18, v19, v20);
                            v16.renderItemOverlays(g.fontRenderer, v18, v19, v20);
                        }
                    }
                    GlStateManager.disableDepth();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    private static void a(final int v, final int v, final int v, final int v, final int v) {
        ShulkerPreview.mc.getTextureManager().bindTexture(ShulkerPreview.resourceLocation);
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
    
    private static boolean a(final ItemStack v, final List<ResourceLocation> v) {
        return !v.isEmpty() && a(v.getItem().getRegistryName(), v);
    }
    
    private static boolean a(final ResourceLocation v, final List<ResourceLocation> v) {
        return v != null && v.contains(v);
    }
    
    private static /* synthetic */ String[] b(final int v) {
        return new String[v];
    }
    
    static {
        qe = !v.class.desiredAssertionStatus();
        ShulkerPreview.qi = (String[])ImmutableSet.of((Object)Blocks.WHITE_SHULKER_BOX, (Object)Blocks.ORANGE_SHULKER_BOX, (Object)Blocks.MAGENTA_SHULKER_BOX, (Object)Blocks.LIGHT_BLUE_SHULKER_BOX, (Object)Blocks.YELLOW_SHULKER_BOX, (Object)Blocks.LIME_SHULKER_BOX, (Object[])new Block[] { Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX }).stream().map(IForgeRegistryEntry.Impl::getRegistryName).map(Objects::toString).toArray(\u0000v::b);
        resourceLocation = new ResourceLocation("backdoored", "textures/inv_slot.png");
        ShulkerPreview.qq = Arrays.stream(ShulkerPreview.qi).map((Function<? super String, ?>)ResourceLocation::new).collect((Collector<? super Object, ?, List<ResourceLocation>>)Collectors.toList());
        qk = new int[][] { { 1, 1 }, { 9, 3 }, { 9, 5 }, { 9, 6 }, { 9, 8 }, { 9, 9 }, { 12, 9 } };
    }
}
