package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

class ControlFlow$1 extends BasicBlock.Maker {
    final /* synthetic */ ControlFlow this$0;
    
    ControlFlow$1(final ControlFlow this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    protected BasicBlock makeBlock(final int pos) {
        return new Block(pos, ControlFlow.access$000(this.this$0));
    }
    
    @Override
    protected BasicBlock[] makeArray(final int size) {
        return new Block[size];
    }
}