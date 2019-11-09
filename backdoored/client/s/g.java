package f.b.s;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import com.google.common.base.*;
import com.google.common.io.*;
import f.b.g.*;
import com.mojang.authlib.exceptions.*;
import java.io.*;

public class g extends GuiScreen
{
    private GuiScreen guiScreen;
    private GuiTextField guiTextField;
    private GuiTextField guiTextField;
    private String dj;
    
    public g(final GuiScreen v) {
        super();
        this.dj = "";
        this.guiScreen = v;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.guiTextField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 4 + 60 + 0, 202, 20);
        this.guiTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, this.height / 4 + 60 + 26, 202, 20);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 60 + 52, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 60 + 76, "Cancel"));
        this.guiTextField.setMaxStringLength(500);
        this.guiTextField.setMaxStringLength(500);
        super.initGui();
        try {
            final String v = Files.asCharSource(new File("Backdoored/accounts.txt"), Charsets.UTF_8).read();
            if (!v.isEmpty()) {
                final String[] v2 = v.split(":");
                try {
                    if (!c.a(v2[0].trim(), v2[1].trim())) {
                        System.out.println("Could not log in");
                        this.dj = "Could not log in";
                        return;
                    }
                }
                catch (AuthenticationException v3) {
                    v3.printStackTrace();
                    System.out.println("Could not log in: " + v3.toString());
                    this.dj = "Could not log in: " + v3.toString();
                    return;
                }
                this.mc.displayGuiScreen(this.guiScreen);
            }
        }
        catch (Exception v4) {
            v4.printStackTrace();
        }
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73876_c() {
        this.guiTextField.updateCursorCounter();
        this.guiTextField.updateCursorCounter();
    }
    
    public void func_73864_a(final int v, final int v, final int v) throws IOException {
        this.guiTextField.mouseClicked(v, v, v);
        this.guiTextField.mouseClicked(v, v, v);
        super.mouseClicked(v, v, v);
    }
    
    public void func_146284_a(final GuiButton v) {
        if (v.id == 1) {
            System.out.println("Attempting subguis, username: " + this.guiTextField.getText().trim());
            try {
                if (!c.a(this.guiTextField.getText().trim(), this.guiTextField.getText().trim())) {
                    System.out.println("Could not log in");
                    this.dj = "Could not log in";
                    return;
                }
            }
            catch (AuthenticationException v2) {
                v2.printStackTrace();
                System.out.println("Could not log in: " + v2.toString());
                this.dj = "Could not log in: " + v2.toString();
                return;
            }
            this.mc.displayGuiScreen(this.guiScreen);
        }
        else if (v.id == 2) {
            this.mc.displayGuiScreen(this.guiScreen);
        }
    }
    
    protected void func_73869_a(final char v, final int v) {
        this.guiTextField.textboxKeyTyped(v, v);
        this.guiTextField.textboxKeyTyped(v, v);
        if (v == '\t') {
            if (this.guiTextField.isFocused()) {
                this.guiTextField.setFocused(false);
                this.guiTextField.setFocused(true);
            }
            else if (this.guiTextField.isFocused()) {
                this.guiTextField.setFocused(false);
                this.guiTextField.setFocused(false);
            }
        }
        if (v == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }
    
    public void func_73863_a(final int v, final int v, final float v) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Backdoored Client: Login to Minecraft", this.width / 2, 2, 16777215);
        this.drawString(this.fontRenderer, "Email", this.width / 2 - 100 - 50, this.height / 4 + 60 + 0 + 6, 16777215);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100 - 50, this.height / 4 + 60 + 26 + 6, 16777215);
        this.drawCenteredString(this.fontRenderer, this.dj, this.width / 2, this.height / 4 + 60 + 100, 16711680);
        try {
            this.guiTextField.drawTextBox();
            this.guiTextField.drawTextBox();
        }
        catch (Exception v2) {
            v2.printStackTrace();
        }
        super.drawScreen(v, v, v);
    }
}
