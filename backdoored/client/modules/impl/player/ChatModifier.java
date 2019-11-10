package f.b.o.g.player;

import f.b.o.g.*;
import f.b.q.c.c.*;
import f.b.a.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.*;
import f.b.q.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

@c$b(name = "Chat Modifier", description = "Modify your chat messages", category = f.b.o.c.c.PLAYER)
public class ChatModifier extends c
{
    private p[] ed;
    private f.b.f.c ey;
    private f.b.f.c ej;
    private f.b.f.c xa;
    private f.b.f.c xb;
    private f.b.f.c xg;
    private f.b.f.c xr;
    private f.b.f.c xc;
    
    public ChatModifier() {
        super();
        this.ed = new p[] { new e(), new d(), new b(), new f(), new i(), new m(), new u() };
        this.ey = new f.b.f.c("Emphasize", this, false);
        this.ej = new f.b.f.c("Reverse", this, false);
        this.xa = new f.b.f.c("Chav", this, false);
        this.xb = new f.b.f.c("JustLearntEngrish", this, false);
        this.xg = new f.b.f.c("L33t", this, false);
        this.xr = new f.b.f.c("Disabled", this, false);
        this.xc = new f.b.f.c("Fancy", this, false);
    }
    
    @SubscribeEvent
    public void a(final q v) {
        if (v.packet instanceof CPacketChatMessage) {
            System.out.println("Was packet");
            if (this.bu()) {
                final CPacketChatMessage v2 = (CPacketChatMessage)v.packet;
                String v3 = v2.getMessage();
                final boolean v4 = v3.startsWith("/") || v3.startsWith("!");
                if (!v4) {
                    for (final p v5 : this.ed) {
                        try {
                            if (this.x(v5.rh()).cq()) {
                                v3 = v5.bl(v3);
                            }
                        }
                        catch (Exception v6) {
                            v6.printStackTrace();
                        }
                    }
                }
                try {
                    ObfuscationReflectionHelper.setPrivateValue((Class)CPacketChatMessage.class, (Object)v2, (Object)v3, new String[] { "message", "field_149440_a" });
                }
                catch (Exception v7) {
                    o.bn("Disabled chat modifier due to error: " + v7.getMessage());
                    this.a(false);
                    v7.printStackTrace();
                }
            }
        }
    }
}
