package f.b.o.g.j;

import java.util.*;
import net.minecraft.client.gui.inventory.*;

public class m extends u
{
    private static final String ic = "Backdoored/announcer.js";
    
    public m() throws Exception {
        super();
        this.if = new f().u(u.w("Backdoored/announcer.js")).a(m.iq);
    }
    
    String o(final String v) {
        try {
            return Objects.requireNonNull(this.if.b("onSendMessage", v));
        }
        catch (Exception v2) {
            v2.printStackTrace();
            return v;
        }
    }
    
    String a(final int v) {
        return this.a("onMove", v);
    }
    
    String t(final String v) {
        return this.a("onAttack", v);
    }
    
    String b(final int v, final String v) {
        return this.a("onBlocksBreak", v, v);
    }
    
    String g(final int v, final String v) {
        return this.a("onBlocksPlace", v, v);
    }
    
    String a(final GuiInventory v) {
        return this.a("onOpenInventory", new Object[0]);
    }
    
    String bj() {
        return this.a("onScreenshot", new Object[0]);
    }
    
    String ga() {
        return this.a("onModuleEnabled", new Object[0]);
    }
    
    String gb() {
        return this.a("onModuleDisabled", new Object[0]);
    }
    
    String gg() {
        return this.a("onPlayerJoin", new Object[0]);
    }
    
    String gr() {
        return this.a("onPlayerLeave", new Object[0]);
    }
    
    private String a(final String v, final Object... v) {
        try {
            return (String)this.if.b(v, v);
        }
        catch (Exception v2) {
            v2.printStackTrace();
            return null;
        }
    }
}
