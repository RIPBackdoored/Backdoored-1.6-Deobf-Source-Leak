package f.b.o.g.client;

import f.b.o.g.*;
import java.nio.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.io.*;
import net.minecraft.client.shader.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import f.b.o.g.a.*;
import net.minecraft.util.text.*;

@c$b(name = "Better Screenshot", description = "Asyncronous Screenshots", category = f.b.o.c.c.CLIENT)
public class BetterScreenshot extends c
{
    private IntBuffer io;
    private int[] it;
    
    public BetterScreenshot() {
        super();
    }
    
    @SubscribeEvent
    public void a(final bh v) {
        if (this.bu()) {
            v.setCanceled(true);
            this.a(v.rj, v.cb, v.cg, v.framebuffer);
            v.iTextComponent = (ITextComponent)new TextComponentString("Creating screenshot...");
        }
    }
    
    public void a(final File v, int v, int v, final Framebuffer v) {
        final File v2 = new File(v, "screenshots");
        v2.mkdir();
        if (OpenGlHelper.isFramebufferEnabled()) {
            v = v.framebufferTextureWidth;
            v = v.framebufferTextureHeight;
        }
        final int v3 = v * v;
        if (this.io == null || this.io.capacity() < v3) {
            this.io = BufferUtils.createIntBuffer(v3);
            this.it = new int[v3];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        this.io.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(v.framebufferTexture);
            GL11.glGetTexImage(3553, 0, 32993, 33639, this.io);
        }
        else {
            GL11.glReadPixels(0, 0, v, v, 32993, 33639, this.io);
        }
        this.io.get(this.it);
        new Thread(new q(v, v, this.it, BetterScreenshot.mc.getFramebuffer(), v2), "Screenshot creation thread").start();
    }
}
