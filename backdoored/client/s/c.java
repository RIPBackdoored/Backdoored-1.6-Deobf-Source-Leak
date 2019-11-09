package f.b.s;

import net.minecraft.util.text.*;
import javax.annotation.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class c extends GuiGameOver
{
    public c(@Nullable final ITextComponent v) {
        super(v);
    }
    
    public void func_73866_w_() {
        super.initGui();
        final GuiButton v = new GuiButton(420, this.width / 2 - 100, this.height / 4 + 120, "Hide Death Screen");
        this.buttonList.add(v);
        v.enabled = true;
    }
    
    protected void func_146284_a(final GuiButton v) throws IOException {
        if (v.id == 420) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        super.actionPerformed(v);
    }
    
    public void func_73876_c() {
        if (this.mc.player != null && !this.mc.player.isDead && this.mc.player.getHealth() > 0.0f) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
        else {
            super.updateScreen();
        }
    }
}
