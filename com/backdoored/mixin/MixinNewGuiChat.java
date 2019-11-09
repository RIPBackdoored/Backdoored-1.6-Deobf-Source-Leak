package com.backdoored.mixin;

import net.minecraft.client.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import \u0000f.\u0000b.\u0000a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ GuiNewChat.class })
public abstract class MixinNewGuiChat
{
    @Shadow
    @Final
    private Minecraft field_146247_f;
    @Shadow
    private int field_146250_j;
    @Shadow
    private boolean field_146251_k;
    @Shadow
    @Final
    private List<ChatLine> field_146253_i;
    @Shadow
    @Final
    private List<ChatLine> field_146252_h;
    
    public MixinNewGuiChat() {
        super();
    }
    
    @Shadow
    public abstract void func_146242_c(final int p0);
    
    @Shadow
    public abstract int func_146228_f();
    
    @Shadow
    public abstract float func_146244_h();
    
    @Shadow
    public abstract boolean func_146241_e();
    
    @Shadow
    public abstract void func_146229_b(final int p0);
    
    @Overwrite
    private void func_146237_a(final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
        if (chatLineId != 0) {
            this.func_146242_c(chatLineId);
        }
        for (final ITextComponent itextcomponent : GuiUtilRenderComponents.func_178908_a(chatComponent, MathHelper.func_76141_d(this.func_146228_f() / this.func_146244_h()), this.field_146247_f.field_71466_p, false, false)) {
            if (this.func_146241_e() && this.field_146250_j > 0) {
                this.field_146251_k = true;
                this.func_146229_b(1);
            }
            this.field_146253_i.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId));
        }
        final \u0000d event = new \u0000d();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.getResult() == Event.Result.ALLOW && !displayOnly) {
            this.field_146252_h.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
        }
        else {
            while (this.field_146253_i.size() > 100) {
                this.field_146253_i.remove(this.field_146253_i.size() - 1);
            }
            if (!displayOnly) {
                this.field_146252_h.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
                while (this.field_146252_h.size() > 100) {
                    this.field_146252_h.remove(this.field_146252_h.size() - 1);
                }
            }
        }
    }
}
