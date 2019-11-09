package f.b.q;

import java.awt.image.*;
import java.awt.*;

public class o
{
    private static SystemTray bai;
    private static TrayIcon baf;
    
    public o() {
        super();
        try {
            o.bai = SystemTray.getSystemTray();
            (o.baf = new TrayIcon(new BufferedImage(20, 20, 1), "Tray Demo")).setImageAutoSize(true);
            o.baf.setToolTip("Backdoored");
            o.bai.add(o.baf);
        }
        catch (Exception v) {
            v.printStackTrace();
            f.b.q.c.o.bn("Could not send notification due to error: " + v.toString());
        }
    }
    
    public static void bm(final String v) {
        l("Backdoored", v);
    }
    
    public static void l(final String v, final String v) {
        if (o.baf == null) {
            new o();
        }
        o.baf.displayMessage(v, v, TrayIcon.MessageType.INFO);
    }
}
