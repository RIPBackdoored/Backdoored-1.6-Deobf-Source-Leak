package f.b.i.c;

import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import f.b.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class o extends GuiScreen
{
    private static final ResourceLocation resourceLocation;
    public boolean cp;
    public boolean cm;
    public int cs;
    public int ce;
    public int cx;
    public int cz;
    public String co;
    private boolean ct;
    public boolean cw;
    private float[] cu;
    public static ArrayList<o> ch;
    
    public o(final int v, final int v, final int v, final int v, final String v, final boolean v, final boolean v, final float[] v) {
        super();
        this.cp = false;
        this.cm = false;
        this.cs = v;
        this.ce = v;
        this.cx = v;
        this.cz = v;
        this.co = v;
        this.ct = v;
        this.cw = v;
        this.cu = v;
        o.ch.add(this);
        this.mc = p.mc;
    }
    
    public void a(final int v, final int v) {
        final int v2 = 1;
        p.mc.renderEngine.bindTexture(o.resourceLocation);
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(this.cu[0], this.cu[1], this.cu[2], this.cu[3]);
        final List<String> v3 = (List<String>)this.mc.fontRenderer.listFormattedStringToWidth(this.co, this.cx - (v2 + 1));
        boolean v4 = false;
        final int v5 = v3.size() * this.mc.fontRenderer.FONT_HEIGHT + 15;
        if (v5 > this.mc.fontRenderer.FONT_HEIGHT + 15) {
            v4 = true;
            this.cz = v5;
        }
        this.drawTexturedModalRect(this.cs, this.ce, 0, 0, this.cx, this.cz);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        this.drawTexturedModalRect(this.cs + v2, this.ce + v2, 0, 0, this.cx - v2 * 2, this.cz - v2 * 2);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        String v6;
        if (this.ct && this.cp) {
            v6 = "FF0000";
        }
        else {
            v6 = "FFFFFF";
        }
        this.mc.fontRenderer.drawSplitString(this.co, this.cs + (v2 + 1) + (this.cx - (v2 + 1) - this.mc.fontRenderer.getStringWidth((String)v3.get(0))) / 2, this.ce + this.cz / 2 - this.mc.fontRenderer.FONT_HEIGHT * v3.size() / 2, this.cx - (v2 + 1), Integer.parseInt(v6, 16));
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/white.png");
        o.ch = new ArrayList<o>();
    }
}
