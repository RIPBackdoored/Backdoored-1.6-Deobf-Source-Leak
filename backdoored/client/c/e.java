package f.b.c;

import f.b.q.c.*;
import net.minecraft.util.text.*;

public class e extends x
{
    public e() {
        super(new String[] { "fakemsg", "msg", "impersonate" });
    }
    
    @Override
    public boolean a(final String[] v) {
        if (v.length < 3) {
            return false;
        }
        final String s = v[0];
        switch (s) {
            default: {
                final StringBuilder v2 = new StringBuilder();
                v2.append("<").append(v[1]).append("> ");
                for (int v3 = 2; v3 < v.length; ++v3) {
                    v2.append(v[v3]).append(" ");
                }
                o.b(v2.toString(), false);
                return true;
            }
            case "whisper": {
                final String v4 = v[1];
                final StringBuilder v5 = new StringBuilder();
                for (int v6 = 2; v6 < v.length; ++v6) {
                    v5.append(v[v6]).append(" ");
                }
                this.mc.ingameGUI.addChatMessage(ChatType.CHAT, (ITextComponent)new TextComponentString("§d" + v4 + " whispers: " + v5.toString()));
                return true;
            }
            case "server": {
                final StringBuilder v2 = new StringBuilder("§e[SERVER] ");
                for (int v6 = 1; v6 < v.length; ++v6) {
                    v2.append(v[v6]).append(" ");
                }
                o.b(v2.toString(), false);
                return true;
            }
            case "suicide": {
                final String v7 = v[1];
                final StringBuilder v2 = new StringBuilder("§4");
                for (int v8 = 2; v8 < v.length; ++v8) {
                    v2.append(v[v8]).append(" ");
                }
                String v9 = v2.toString();
                v9 = v9.replace(" player ", " §3" + v7 + " §4");
                o.b(v9, false);
                return true;
            }
            case "kill": {
                final String v7 = v[1];
                final String v10 = v[2];
                final StringBuilder v2 = new StringBuilder("§4");
                for (int v11 = 3; v11 < v.length; ++v11) {
                    v2.append(v[v11]).append(" ");
                }
                String v9 = v2.toString();
                v9 = v9.replace(" player1 ", " §3" + v7 + " §4");
                v9 = v9.replace(" player2 ", " §3" + v10 + " §4");
                o.b(v9, false);
                return true;
            }
            case "killWeapon": {
                final String v7 = v[1];
                final String v10 = v[2];
                final String v12 = v[3];
                final StringBuilder v2 = new StringBuilder("§4");
                for (int v13 = 4; v13 < v.length; ++v13) {
                    v2.append(v[v13]).append(" ");
                }
                String v9 = v2.toString();
                v9 = v9.replace(" player1 ", " §3" + v7 + " §4");
                v9 = v9.replace(" player2 ", " §3" + v10 + " §4");
                v9 = v9.replace(" weapon ", " §6" + v12 + " §4");
                o.b(v9, false);
                return true;
            }
        }
    }
    
    @Override
    public String o() {
        return "-fakemsg chat 4yl im kinda ez ngl\n-fakemsg whisper John200410 Backdoored client on top\n-fakemsg server buy prio pls";
    }
}
