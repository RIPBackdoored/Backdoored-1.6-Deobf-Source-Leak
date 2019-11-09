package f.b.i.x;

import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class j
{
    j() {
        super();
    }
    
    public void bm() {
    }
    
    @SubscribeEvent
    public void a(final RenderGameOverlayEvent.Post v) {
        if (v.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        this.bm();
    }
}
