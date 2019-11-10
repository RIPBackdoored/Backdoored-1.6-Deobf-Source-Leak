package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

class ControlFlow$2 extends Access {
    final /* synthetic */ ControlFlow this$0;
    
    ControlFlow$2(final ControlFlow this$0, final Node[] nodes) {
        this.this$0 = this$0;
        super(nodes);
    }
    
    @Override
    BasicBlock[] exits(final Node n) {
        return n.block.getExit();
    }
    
    @Override
    BasicBlock[] entrances(final Node n) {
        return n.block.entrances;
    }
}