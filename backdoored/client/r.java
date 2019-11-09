package f.b;

import net.minecraftforge.fml.common.*;
import club.minnced.discord.rpc.*;
import \u0000f.\u0000b.*;
import net.minecraft.client.multiplayer.*;

public class r
{
    private static final String bs = "608333956575920159";
    private static final DiscordRPC be;
    private static DiscordRichPresence bx;
    private static boolean bz;
    
    public r() {
        super();
    }
    
    public static boolean bc() {
        FMLLog.log.info("Starting Discord RPC");
        if (r.bz) {
            return false;
        }
        r.bz = true;
        final DiscordEventHandlers v = new DiscordEventHandlers();
        v.disconnected = \u0000r::a;
        r.be.Discord_Initialize("608333956575920159", v, true, "");
        r.bx.startTimestamp = System.currentTimeMillis() / 1000L;
        r.bx.details = "Main Menu";
        r.bx.state = "discord.gg/ncQkFKU";
        r.bx.largeImageKey = "backdoored_logo";
        r.be.Discord_UpdatePresence(r.bx);
        new Thread(\u0000r::bn, "Discord-RPC-Callback-Handler").start();
        FMLLog.log.info("Discord RPC initialised succesfully");
        return true;
    }
    
    private static /* synthetic */ void bn() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                r.be.Discord_RunCallbacks();
                String v = "";
                String v2 = "";
                int v3 = 0;
                int v4 = 0;
                if (p.mc.isIntegratedServerRunning()) {
                    v = "Singleplayer";
                }
                else if (p.mc.getCurrentServerData() != null) {
                    final ServerData v5 = p.mc.getCurrentServerData();
                    if (!v5.serverIP.equals("")) {
                        v = "Multiplayer";
                        v2 = v5.serverIP;
                        if (v5.populationInfo != null) {
                            final String[] v6 = v5.populationInfo.split("/");
                            if (v6.length > 2) {
                                v3 = Integer.valueOf(v6[0]);
                                v4 = Integer.valueOf(v6[1]);
                            }
                        }
                        if (v2.contains("2b2t.org")) {
                            try {
                                if (e.k.startsWith("Position in queue: ")) {
                                    v2 = v2 + " " + Integer.parseInt(e.k.substring(19)) + " in queue";
                                }
                            }
                            catch (Throwable v7) {
                                v7.printStackTrace();
                            }
                        }
                    }
                }
                else {
                    v = "Main Menu";
                    v2 = "discord.gg/ncQkFKU";
                }
                if (!v.equals(r.bx.details) || !v2.equals(r.bx.state)) {
                    r.bx.startTimestamp = System.currentTimeMillis() / 1000L;
                }
                r.bx.details = v;
                r.bx.state = v2;
                r.be.Discord_UpdatePresence(r.bx);
            }
            catch (Exception v8) {
                v8.printStackTrace();
            }
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException v9) {
                v9.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ void a(final int v, final String v) {
        System.out.println("Discord RPC disconnected, var1: " + String.valueOf(v) + ", var2: " + v);
    }
    
    static {
        be = DiscordRPC.INSTANCE;
        r.bx = new DiscordRichPresence();
        r.bz = false;
    }
}
