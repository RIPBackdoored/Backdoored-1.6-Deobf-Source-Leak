package f.b.o.g.w.d;

import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import f.b.a.*;
import f.b.o.g.*;
import java.util.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import f.b.c.*;
import f.b.*;

public class e
{
    private JFrame hl;
    private JTextArea hi;
    private JTextField hf;
    private JTextArea hq;
    private JPanel hk;
    private JSplitPane hp;
    private JPanel hm;
    private JPanel hs;
    private JScrollPane he;
    
    public e() {
        super();
        this.rx();
        this.hl = new JFrame("SwingImpl");
        final Dimension v = Toolkit.getDefaultToolkit().getScreenSize();
        this.hl.setSize(v.width / 2, v.height / 2);
        this.hl.setContentPane(this.hk);
        this.hl.pack();
        this.hl.setVisible(true);
        this.hf.addActionListener(this::a);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void r(final ClientChatReceivedEvent v) {
        if (z.hr.cq()) {
            this.hi.append(v.getMessage().getUnformattedText() + "\n");
        }
        final JScrollBar v2 = this.he.getVerticalScrollBar();
        v2.setValue(v2.getMaximum());
    }
    
    @SubscribeEvent
    public void a(final c v) {
        this.hi.append(v.iTextComponent.getUnformattedText() + "\n");
        final JScrollBar v2 = this.he.getVerticalScrollBar();
        v2.setValue(v2.getMaximum());
    }
    
    public void bh() {
        final StringBuilder v = new StringBuilder();
        for (final f.b.o.g.c v2 : u.so) {
            if (v2.bu()) {
                v.append(v2.lq).append("\n");
            }
        }
        this.hq.setText(v.toString());
    }
    
    public void re() {
        this.hl.setVisible(false);
        this.hl.dispose();
    }
    
    private void rx() {
        (this.hk = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.hk.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        this.hp = new JSplitPane();
        this.hk.add(this.hp, "Center");
        (this.hm = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.hp.setLeftComponent(this.hm);
        this.hf = new JTextField();
        this.hm.add(this.hf, "South");
        (this.he = new JScrollPane()).setHorizontalScrollBarPolicy(31);
        this.he.setVerticalScrollBarPolicy(20);
        this.hm.add(this.he, "Center");
        (this.hi = new JTextArea()).setLineWrap(true);
        this.hi.setText("");
        this.he.setViewportView(this.hi);
        (this.hs = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.hp.setRightComponent(this.hs);
        this.hq = new JTextArea();
        this.hs.add(this.hq, "Center");
    }
    
    public JComponent rz() {
        return this.hk;
    }
    
    private /* synthetic */ void a(final ActionEvent v) {
        if (this.hf.getText().startsWith(g.v)) {
            g.l(this.hf.getText());
        }
        else {
            p.mc.player.sendChatMessage(this.hf.getText());
        }
        this.hf.setText("");
    }
}
