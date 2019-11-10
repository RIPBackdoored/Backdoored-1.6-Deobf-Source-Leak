package f.b.o.g.movement;

import f.b.o.g.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import f.b.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Gui Move", description = "Walk while in guis", category = f.b.o.c.c.MOVEMENT)
public class GuiMove extends c
{
    public GuiMove() {
        super();
    }
    
    public void bh() {
        if (this.bu() && GuiMove.mc.currentScreen != null && !(GuiMove.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = GuiMove.mc.player;
                player.rotationPitch -= 2.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = GuiMove.mc.player;
                player2.rotationPitch += 2.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player3 = GuiMove.mc.player;
                player3.rotationYaw -= 2.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player4 = GuiMove.mc.player;
                player4.rotationYaw += 2.0f;
            }
        }
    }
    
    @SubscribeEvent
    public void a(final m v) {
        if (this.bu() && !(GuiMove.mc.currentScreen instanceof GuiChat)) {
            v.gw = v.gu;
        }
    }
}
