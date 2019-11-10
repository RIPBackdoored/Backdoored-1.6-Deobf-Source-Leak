package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;
import javassist.bytecode.*;
import java.util.*;

public static class Block extends BasicBlock
{
    public Object clientData;
    int index;
    MethodInfo method;
    Block[] entrances;
    
    Block(final int pos, final MethodInfo minfo) {
        super(pos);
        this.clientData = null;
        this.method = minfo;
    }
    
    @Override
    protected void toString2(final StringBuffer sbuf) {
        super.toString2(sbuf);
        sbuf.append(", incoming{");
        for (int i = 0; i < this.entrances.length; ++i) {
            sbuf.append(this.entrances[i].position).append(", ");
        }
        sbuf.append("}");
    }
    
    BasicBlock[] getExit() {
        return this.exit;
    }
    
    public int index() {
        return this.index;
    }
    
    public int position() {
        return this.position;
    }
    
    public int length() {
        return this.length;
    }
    
    public int incomings() {
        return this.incoming;
    }
    
    public Block incoming(final int n) {
        return this.entrances[n];
    }
    
    public int exits() {
        return (this.exit == null) ? 0 : this.exit.length;
    }
    
    public Block exit(final int n) {
        return (Block)this.exit[n];
    }
    
    public Catcher[] catchers() {
        final ArrayList catchers = new ArrayList();
        for (Catch c = this.toCatch; c != null; c = c.next) {
            catchers.add(new Catcher(c));
        }
        return catchers.toArray(new Catcher[catchers.size()]);
    }
}
