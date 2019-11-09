package f.b.i;

import java.util.function.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class a extends JFrame implements ActionListener
{
    private JTextField nf;
    private JFrame nq;
    private JButton nk;
    private JLabel np;
    private Consumer<String> nm;
    private String ns;
    
    public a(final String v, final String v, final String v, final Consumer<String> v) {
        super();
        this.nm = v;
        this.ns = v;
        this.nq = new JFrame(v);
        this.np = new JLabel(v);
        (this.nk = new JButton(v)).addActionListener(this);
        this.nf = new JTextField(16);
        final JPanel v2 = new JPanel();
        v2.add(this.nf);
        v2.add(this.nk);
        v2.add(this.np);
        this.nq.add(v2);
        this.nq.setSize(300, 300);
        this.nq.setVisible(true);
        this.nq.setAlwaysOnTop(true);
    }
    
    @Override
    public void actionPerformed(final ActionEvent v) {
        if (v.getActionCommand().equals(this.ns)) {
            this.nm.accept(this.nf.getText());
            this.nq.dispatchEvent(new WindowEvent(this.nq, 201));
        }
    }
}
