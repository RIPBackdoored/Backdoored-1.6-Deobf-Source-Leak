package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

abstract static class Access
{
    Node[] all;
    
    Access(final Node[] nodes) {
        super();
        this.all = nodes;
    }
    
    Node node(final BasicBlock b) {
        return this.all[((Block)b).index];
    }
    
    abstract BasicBlock[] exits(final Node p0);
    
    abstract BasicBlock[] entrances(final Node p0);
}
