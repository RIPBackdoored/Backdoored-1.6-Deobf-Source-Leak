package f.b.q;

import net.minecraft.util.*;

public class s
{
    public w bap;
    public String bam;
    public String bas;
    private Session session;
    
    public s(final String v, final String v) {
        super();
        this.bap = new w();
        this.bam = v;
        this.bas = v;
    }
    
    public boolean la() {
        if (this.bas == null || this.bas.equals("")) {
            final w bap = this.bap;
            final Session v = w.be(this.bam);
            this.session = v;
            return true;
        }
        final w bap2 = this.bap;
        final Session v = w.q(this.bam, this.bas);
        if (v != null) {
            this.session = v;
            return true;
        }
        return false;
    }
    
    public Session lb() {
        return this.session;
    }
}
