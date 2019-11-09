package f.b.o.g.w.f;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import f.b.f.*;
import org.lwjgl.opengl.*;
import f.b.j.*;
import java.awt.*;
import f.b.o.g.*;
import java.util.*;
import f.b.*;

public class e extends Gui
{
    private static Minecraft mc;
    private ResourceLocation resourceLocation;
    private ResourceLocation resourceLocation;
    private static final String ua = "Backdoored 1.6";
    private static int ub;
    
    public e() {
        super();
        this.resourceLocation = new ResourceLocation("backdoored", "textures/dev-donor-client.png");
        this.resourceLocation = new ResourceLocation("backdoored", "textures/backdoored-standard-client.png");
    }
    
    public void a(final RenderGameOverlayEvent v, final c[] v, final z v) {
        if (z.wu.lm.cp) {
            boolean v2 = false;
            try {
                final c v3 = b.b(v, "Rainbow Colour");
                if (v3 == null) {
                    throw new NullPointerException(v3.cl().toString());
                }
                v2 = Objects.requireNonNull(v3.cl());
            }
            catch (Exception ex) {}
            if (v[0].cq()) {
                GL11.glPushAttrib(1048575);
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (f.b.e.p()) {
                    e.mc.renderEngine.bindTexture(this.resourceLocation);
                }
                else {
                    e.mc.renderEngine.bindTexture(this.resourceLocation);
                }
                GL11.glTranslatef(1.0f, 1.0f, 0.0f);
                drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, 208, 208, 30, 30, 208.0f, 208.0f);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
            }
            if (v[1].cq()) {
                int v4 = 2;
                if (v[0].cq()) {
                    v4 = 32;
                }
                this.drawString(g.fontRenderer, "Backdoored 1.6", v4, 2, v2 ? f.b.q.b.nb().getRGB() : Color.RED.getRGB());
            }
            if (v[2].cq()) {
                if (v[6].ci().equals("Length")) {
                    u.b(false);
                }
                else if (v[6].ci().equals("Alphabetical")) {}
                for (final f.b.o.g.c v5 : u.so) {
                    for (final c v6 : Objects.requireNonNull(b.a(v5))) {
                        if (v6.rh().equals("Is Visible") && Boolean.valueOf(v6.ci()) && v5.lm.cp) {
                            int v7 = 0;
                            int v8 = 0;
                            if (v[3].ci().equalsIgnoreCase("top right")) {
                                v7 = v.getResolution().getScaledWidth() - g.fontRenderer.getStringWidth(v5.lq);
                            }
                            if (v[3].ci().equalsIgnoreCase("top left")) {
                                v7 = 2;
                            }
                            if (v[3].ci().equalsIgnoreCase("bot right")) {
                                v7 = v.getResolution().getScaledWidth() - g.fontRenderer.getStringWidth(v5.lq);
                                v8 = v.getResolution().getScaledHeight();
                            }
                            if (v[3].ci().equalsIgnoreCase("bot left")) {
                                v7 = 2;
                                v8 = v.getResolution().getScaledHeight();
                            }
                            if (v[3].ci().equalsIgnoreCase("custom")) {
                                v7 = v[4].cp();
                                v8 = v[5].cp();
                            }
                            if (v[3].ci().equalsIgnoreCase("top left") || v[3].ci().equalsIgnoreCase("top right")) {
                                this.drawString(g.fontRenderer, v5.lq, v7, v8 + e.ub, v2 ? f.b.q.b.nb().getRGB() : Color.RED.getRGB());
                            }
                            if (v[3].ci().equalsIgnoreCase("bot left") || v[3].ci().equalsIgnoreCase("bot right")) {
                                this.drawString(g.fontRenderer, v5.lq, v7, v8 - e.ub - g.fontRenderer.FONT_HEIGHT, v2 ? f.b.q.b.nb().getRGB() : Color.RED.getRGB());
                            }
                            if (v[3].ci().equalsIgnoreCase("custom")) {
                                this.drawString(g.fontRenderer, v5.lq, v7, v8 + e.ub, v2 ? f.b.q.b.nb().getRGB() : Color.RED.getRGB());
                            }
                            e.ub += 10;
                        }
                    }
                }
            }
            e.ub = 2;
        }
    }
    
    static {
        e.mc = p.mc;
        e.ub = 2;
    }
}
