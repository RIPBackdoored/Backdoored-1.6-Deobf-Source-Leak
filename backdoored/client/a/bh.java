package f.b.a;

import net.minecraftforge.fml.common.eventhandler.*;
import java.io.*;
import net.minecraft.client.shader.*;
import net.minecraft.util.text.*;

@Cancelable
public class bh extends b
{
    public File rj;
    public String ca;
    public int cb;
    public int cg;
    public Framebuffer framebuffer;
    public ITextComponent iTextComponent;
    
    public bh(final File v, final String v, final int v, final int v, final Framebuffer v) {
        super();
        this.rj = v;
        this.ca = v;
        this.cb = v;
        this.cg = v;
        this.framebuffer = v;
        this.iTextComponent = (ITextComponent)new TextComponentString("Screenshot meant to be here?");
    }
}
