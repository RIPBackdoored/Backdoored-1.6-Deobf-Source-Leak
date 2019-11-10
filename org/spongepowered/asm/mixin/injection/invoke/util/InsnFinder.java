package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.lib.tree.analysis.*;

public class InsnFinder
{
    private static final Logger logger;
    
    public InsnFinder() {
        super();
    }
    
    public AbstractInsnNode findPopInsn(final Target target, final AbstractInsnNode node) {
        try {
            new PopAnalyzer(node).analyze(target.classNode.name, target.method);
        }
        catch (AnalyzerException ex) {
            if (ex.getCause() instanceof AnalysisResultException) {
                return ((AnalysisResultException)ex.getCause()).getResult();
            }
            InsnFinder.logger.catching((Throwable)ex);
        }
        return null;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    static class AnalysisResultException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private AbstractInsnNode result;
        
        public AnalysisResultException(final AbstractInsnNode popNode) {
            super();
            this.result = popNode;
        }
        
        public AbstractInsnNode getResult() {
            return this.result;
        }
    }
    
    enum AnalyzerState
    {
        SEARCH, 
        ANALYSE, 
        COMPLETE;
        
        private static final /* synthetic */ AnalyzerState[] $VALUES;
        
        public static AnalyzerState[] values() {
            return AnalyzerState.$VALUES.clone();
        }
        
        public static AnalyzerState valueOf(final String name) {
            return Enum.valueOf(AnalyzerState.class, name);
        }
        
        static {
            $VALUES = new AnalyzerState[] { AnalyzerState.SEARCH, AnalyzerState.ANALYSE, AnalyzerState.COMPLETE };
        }
    }
    
    static class PopAnalyzer extends Analyzer<BasicValue>
    {
        protected final AbstractInsnNode node;
        
        public PopAnalyzer(final AbstractInsnNode node) {
            super(new BasicInterpreter());
            this.node = node;
        }
        
        @Override
        protected Frame<BasicValue> newFrame(final int locals, final int stack) {
            return new PopFrame(locals, stack);
        }
        
        class PopFrame extends Frame<BasicValue>
        {
            private AbstractInsnNode current;
            private AnalyzerState state;
            private int depth;
            final /* synthetic */ PopAnalyzer this$0;
            
            public PopFrame(final PopAnalyzer this$0, final int locals, final int stack) {
                this.this$0 = this$0;
                super(locals, stack);
                this.state = AnalyzerState.SEARCH;
                this.depth = 0;
            }
            
            @Override
            public void execute(final AbstractInsnNode insn, final Interpreter<BasicValue> interpreter) throws AnalyzerException {
                super.execute(this.current = insn, interpreter);
            }
            
            @Override
            public void push(final BasicValue value) throws IndexOutOfBoundsException {
                if (this.current == this.this$0.node && this.state == AnalyzerState.SEARCH) {
                    this.state = AnalyzerState.ANALYSE;
                    ++this.depth;
                }
                else if (this.state == AnalyzerState.ANALYSE) {
                    ++this.depth;
                }
                super.push(value);
            }
            
            @Override
            public BasicValue pop() throws IndexOutOfBoundsException {
                if (this.state == AnalyzerState.ANALYSE && --this.depth == 0) {
                    this.state = AnalyzerState.COMPLETE;
                    throw new AnalysisResultException(this.current);
                }
                return super.pop();
            }
            
            @Override
            public /* bridge */ void push(final Value value) throws IndexOutOfBoundsException {
                this.push((BasicValue)value);
            }
            
            @Override
            public /* bridge */ Value pop() throws IndexOutOfBoundsException {
                return this.pop();
            }
        }
    }
}
