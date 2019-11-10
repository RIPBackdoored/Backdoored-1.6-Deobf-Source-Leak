package f.b.o.g.b;

import net.minecraftforge.client.event.*;

class b$z
{
    static final /* synthetic */ int[] zw;
    
    static {
        zw = new int[RenderBlockOverlayEvent.OverlayType.values().length];
        try {
            b$z.zw[RenderBlockOverlayEvent.OverlayType.FIRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            b$z.zw[RenderBlockOverlayEvent.OverlayType.BLOCK.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            b$z.zw[RenderBlockOverlayEvent.OverlayType.WATER.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
