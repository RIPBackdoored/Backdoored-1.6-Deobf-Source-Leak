package f.b.o.g.t;

import java.awt.event.*;

class v$z implements ActionListener
{
    int zg;
    final /* synthetic */ int zr;
    final /* synthetic */ String[] zc;
    final /* synthetic */ v zn;
    
    v$z(final v v, final int zr, final String[] zc) {
        this.zn = v;
        this.zr = zr;
        this.zc = zc;
        super();
        this.zg = 0;
    }
    
    @Override
    public void actionPerformed(final ActionEvent v) {
        if (this.zg < this.zr) {
            v.mc.player.sendChatMessage(this.zc[this.zg]);
            ++this.zg;
            if (this.zg == this.zr) {
                this.zg = 0;
            }
        }
    }
}
