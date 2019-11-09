package f.b.o.g.t;

import f.b.o.g.*;
import net.minecraft.util.text.*;
import net.minecraft.init.*;
import f.b.q.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;

@c$b(name = "AutoSign", description = "Automatically place signs with text", category = f.b.o.c.c.PLAYER)
public class m extends c
{
    private ITextComponent[] iTextComponent;
    
    public m() {
        super();
        this.iTextComponent = new ITextComponent[] { (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test") };
    }
    
    public void bv() {
        final BlockPos v = m.mc.objectMouseOver.getBlockPos().offset(m.mc.objectMouseOver.sideHit);
        final int v2 = m.mc.player.inventory.currentItem;
        m.mc.player.inventory.currentItem = n.b(Items.SIGN);
        if (m.mc.world.getBlockState(v).getMaterial().isReplaceable()) {
            n.c(v);
        }
        m.mc.player.closeScreen();
        m.mc.player.connection.sendPacket((Packet)new CPacketUpdateSign(v, this.iTextComponent));
        m.mc.player.inventory.currentItem = v2;
        this.a(false);
    }
}
