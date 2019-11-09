package f.b.s;

import f.b.*;
import net.minecraft.client.gui.*;
import java.io.*;
import java.awt.*;
import f.b.g.*;

public class x extends GuiMainMenu
{
    public x() {
        super();
    }
    
    public void func_73866_w_() {
        super.initGui();
        this.buttonList.add(new GuiButton(62, 2, 2, 98, 20, "Login"));
    }
    
    protected void func_146284_a(final GuiButton v) throws IOException {
        System.out.println("Button pressed: " + v.displayString);
        if (v.displayString.equals("Login")) {
            p.mc.displayGuiScreen((GuiScreen)new g((GuiScreen)this));
        }
        else {
            super.actionPerformed(v);
        }
    }
    
    public void func_73863_a(final int v, final int v, final float v) {
        super.drawScreen(v, v, v);
        String v2 = "[ONLINE]";
        Color v3 = Color.GREEN;
        if (!a.f()) {
            v2 = "[OFFLINE]";
            v3 = Color.RED;
        }
        String v4 = "[ONLINE]";
        Color v5 = Color.GREEN;
        if (!a.q()) {
            v4 = "[OFFLINE]";
            v5 = Color.RED;
        }
        this.mc.fontRenderer.drawString("Auth Server:     " + v2, 2.0f, 25.0f, v3.getRGB(), true);
        this.mc.fontRenderer.drawString("Session Server: " + v4, 2.0f, (float)(25 + this.mc.fontRenderer.FONT_HEIGHT + 2), v5.getRGB(), true);
    }
}
