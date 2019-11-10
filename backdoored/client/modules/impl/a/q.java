package f.b.o.g.a;

import java.text.*;
import net.minecraft.client.shader.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.util.text.event.*;
import f.b.q.c.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import java.util.*;

class q implements Runnable
{
    private static final SimpleDateFormat qb;
    private int qg;
    private int qr;
    private int[] qc;
    private Framebuffer framebuffer;
    private File ql;
    
    q(final int v, final int v, final int[] v, final Framebuffer v, final File v) {
        super();
        this.qg = v;
        this.qr = v;
        this.qc = v;
        this.framebuffer = v;
        this.ql = v;
    }
    
    @Override
    public void run() {
        a(this.qc, this.qg, this.qr);
        BufferedImage v = null;
        try {
            if (OpenGlHelper.isFramebufferEnabled()) {
                v = new BufferedImage(this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight, 1);
                int v3;
                for (int v2 = v3 = this.framebuffer.framebufferTextureHeight - this.framebuffer.framebufferHeight; v2 < this.framebuffer.framebufferTextureHeight; ++v2) {
                    for (int v4 = 0; v4 < this.framebuffer.framebufferWidth; ++v4) {
                        v.setRGB(v4, v2 - v3, this.qc[v2 * this.framebuffer.framebufferTextureWidth + v4]);
                    }
                }
            }
            else {
                v = new BufferedImage(this.qg, this.qr, 1);
                v.setRGB(0, 0, this.qg, this.qr, this.qc, 0, this.qg);
            }
            final File v5 = g(this.ql);
            ImageIO.write(v, "png", v5);
            final ITextComponent v6 = (ITextComponent)new TextComponentString(v5.getName());
            v6.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, v5.getAbsolutePath()));
            v6.getStyle().setUnderlined(Boolean.valueOf(true));
            o.a((ITextComponent)new TextComponentTranslation("screenshot.success", new Object[] { v6 }));
        }
        catch (Exception v7) {
            FMLLog.log.info("Failed to save screenshot");
            v7.printStackTrace();
            o.a((ITextComponent)new TextComponentTranslation("screenshot.failure", new Object[] { v7.getMessage() }));
        }
    }
    
    private static void a(final int[] v, final int v, final int v) {
        final int[] v2 = new int[v];
        for (int v3 = v / 2, v4 = 0; v4 < v3; ++v4) {
            System.arraycopy(v, v4 * v, v2, 0, v);
            System.arraycopy(v, (v - 1 - v4) * v, v, v4 * v, v);
            System.arraycopy(v2, 0, v, (v - 1 - v4) * v, v);
        }
    }
    
    private static File g(final File v) {
        final String v2 = q.qb.format(new Date());
        int v3 = 1;
        File v4;
        while (true) {
            v4 = new File(v, v2 + ((v3 == 1) ? "" : ("_" + v3)) + ".png");
            if (!v4.exists()) {
                break;
            }
            ++v3;
        }
        return v4;
    }
    
    static {
        qb = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
}
