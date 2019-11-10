package f.b.o.g.player;

import f.b.o.g.*;
import net.minecraft.util.text.*;
import net.minecraft.init.*;
import f.b.q.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;

@c$b(name = "AutoSign", description = "Automatically place signs with text", category = f.b.o.c.c.PLAYER)
public class AutoSign extends c
{
    private ITextComponent[] iTextComponent;
    
    public AutoSign() {
        super();
        this.iTextComponent = new ITextComponent[] { (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test"), (ITextComponent)new TextComponentString("test") };
    }
    
    public void bv() {
        final BlockPos v = AutoSign.mc.objectMouseOver.getBlockPos().offset(AutoSign.mc.objectMouseOver.sideHit);
        final int v2 = AutoSign.mc.player.inventory.currentItem;
        AutoSign.mc.player.inventory.currentItem = n.b(Items.SIGN);
        if (AutoSign.mc.world.getBlockState(v).getMaterial().isReplaceable()) {
            n.c(v);
        }
        AutoSign.mc.player.closeScreen();
        AutoSign.mc.player.connection.sendPacket((Packet)new CPacketUpdateSign(v, this.iTextComponent));
        AutoSign.mc.player.inventory.currentItem = v2;
        this.a(false);
    }
}
