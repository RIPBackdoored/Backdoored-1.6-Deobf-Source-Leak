package f.b.s;

import org.lwjgl.input.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.client.gui.*;
import \u0000f.\u0000b.\u0000s.*;
import java.io.*;
import java.util.function.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

public class b extends GuiScreen
{
    public final TileEntitySign tileEntitySign;
    private static int dw;
    private List<GuiTextField> du;
    private String[] dh;
    
    public b(final TileEntitySign v) {
        super();
        this.tileEntitySign = v;
    }
    
    public void func_73866_w_() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.du = new LinkedList<GuiTextField>();
        this.dh = new String[4];
        for (int v = 0; v < 4; ++v) {
            final GuiTextField v2 = new GuiTextField(v, this.fontRenderer, this.width / 2 + 4, 75 + v * 24, 120, 20);
            v2.setValidator((Predicate)this::bc);
            v2.setMaxStringLength(100);
            final String v3 = this.tileEntitySign.signText[v].getUnformattedText();
            v2.setText(this.dh[v] = v3);
            this.du.add(v2);
        }
        this.du.get(b.dw).setFocused(true);
        this.addButton(new GuiButton(4, this.width / 2 + 5, this.height / 4 + 120, 120, 20, "Done"));
        this.addButton(new GuiButton(5, this.width / 2 - 125, this.height / 4 + 120, 120, 20, "Cancel"));
        this.addButton(new GuiButton(6, this.width / 2 - 41, 147, 40, 20, "Shift"));
        this.addButton(new GuiButton(7, this.width / 2 - 41, 123, 40, 20, "Clear"));
        this.tileEntitySign.setEditable(false);
    }
    
    protected void func_73864_a(final int v, final int v, final int v) throws IOException {
        super.mouseClicked(v, v, v);
        final int v2 = b.dw;
        this.du.forEach(\u0000b::a);
        this.cj();
        if (b.dw == v2 && !this.du.get(b.dw).isFocused()) {
            this.du.get(b.dw).setFocused(true);
        }
    }
    
    protected void func_73869_a(final char v, final int v) {
        switch (v) {
            case 1: {
                this.na();
                return;
            }
            case 15: {
                final int v2 = isShiftKeyDown() ? -1 : 1;
                this.l(v2);
                return;
            }
            case 200: {
                this.l(-1);
                return;
            }
            case 28:
            case 156:
            case 208: {
                this.l(1);
                break;
            }
        }
        this.du.forEach(\u0000b::a);
        this.tileEntitySign.signText[b.dw] = (ITextComponent)new TextComponentString(this.du.get(b.dw).getText());
    }
    
    protected void func_146284_a(final GuiButton v) throws IOException {
        super.actionPerformed(v);
        switch (v.id) {
            case 5: {
                for (int v2 = 0; v2 < 4; ++v2) {
                    this.tileEntitySign.signText[v2] = (ITextComponent)new TextComponentString(this.dh[v2]);
                }
            }
            case 4: {
                this.na();
                break;
            }
            case 6: {
                final String[] v3 = new String[4];
                for (int v4 = 0; v4 < 4; ++v4) {
                    final int v5 = isShiftKeyDown() ? 1 : -1;
                    final int v6 = this.i(v4 + v5);
                    v3[v4] = this.tileEntitySign.signText[v6].getUnformattedText();
                }
                this.du.forEach(this::a);
                break;
            }
            case 7: {
                this.du.forEach(this::b);
                break;
            }
        }
    }
    
    public void func_73863_a(final int v, final int v, final float v) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2 - 63.0f, 0.0f, 50.0f);
        final float v2 = 93.75f;
        GlStateManager.scale(-93.75f, -93.75f, -93.75f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        final Block v3 = this.tileEntitySign.getBlockType();
        if (v3 == Blocks.STANDING_SIGN) {
            final float v4 = this.tileEntitySign.getBlockMetadata() * 360 / 16.0f;
            GlStateManager.rotate(v4, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int v5 = this.tileEntitySign.getBlockMetadata();
            float v6 = 0.0f;
            if (v5 == 2) {
                v6 = 180.0f;
            }
            if (v5 == 4) {
                v6 = 90.0f;
            }
            if (v5 == 5) {
                v6 = -90.0f;
            }
            GlStateManager.rotate(v6, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, -0.7625f, 0.0f);
        }
        this.tileEntitySign.lineBeingEdited = -1;
        TileEntityRendererDispatcher.instance.render((TileEntity)this.tileEntitySign, -0.5, -0.75, -0.5, 0.0f);
        GlStateManager.popMatrix();
        this.du.forEach(GuiTextField::func_146194_f);
        super.drawScreen(v, v, v);
    }
    
    void cj() {
        this.du.forEach(\u0000b::a);
    }
    
    void na() {
        this.tileEntitySign.markDirty();
        this.mc.displayGuiScreen((GuiScreen)null);
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient v = this.mc.getConnection();
        if (v != null) {
            v.sendPacket((Packet)new CPacketUpdateSign(this.tileEntitySign.getPos(), this.tileEntitySign.signText));
        }
        this.tileEntitySign.setEditable(true);
    }
    
    void l(final int v) {
        this.du.get(b.dw).setFocused(false);
        b.dw = this.i(b.dw + v);
        this.du.get(b.dw).setFocused(true);
    }
    
    int i(final int v) {
        if (v > 3) {
            return 0;
        }
        if (v < 0) {
            return 3;
        }
        return v;
    }
    
    boolean bc(final String v) {
        if (this.fontRenderer.getStringWidth(v) > 90) {
            return false;
        }
        final char[] charArray;
        final char[] v2 = charArray = v.toCharArray();
        for (final char v3 : charArray) {
            if (!ChatAllowedCharacters.isAllowedCharacter(v3)) {
                return false;
            }
        }
        return true;
    }
    
    private static /* synthetic */ void a(final GuiTextField v) {
        if (v.isFocused()) {
            b.dw = v.getId();
        }
    }
    
    private /* synthetic */ void b(final GuiTextField v) {
        final int v2 = v.getId();
        v.setText("");
        this.tileEntitySign.signText[v2] = (ITextComponent)new TextComponentString("");
    }
    
    private /* synthetic */ void a(final String[] v, final GuiTextField v) {
        final int v2 = v.getId();
        v.setText(v[v2]);
        this.tileEntitySign.signText[v2] = (ITextComponent)new TextComponentString(v[v2]);
    }
    
    private static /* synthetic */ void a(final char v, final int v, final GuiTextField v) {
        v.textboxKeyTyped(v, v);
    }
    
    private static /* synthetic */ void a(final int v, final int v, final int v, final GuiTextField v) {
        v.mouseClicked(v, v, v);
    }
    
    static {
        b.dw = 0;
    }
}
