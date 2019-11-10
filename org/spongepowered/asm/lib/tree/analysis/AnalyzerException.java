package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.*;

public class AnalyzerException extends Exception
{
    public final AbstractInsnNode node;
    
    public AnalyzerException(final AbstractInsnNode node, final String msg) {
        super(msg);
        this.node = node;
    }
    
    public AnalyzerException(final AbstractInsnNode node, final String msg, final Throwable exception) {
        super(msg, exception);
        this.node = node;
    }
    
    public AnalyzerException(final AbstractInsnNode node, final String msg, final Object expected, final Value encountered) {
        super(((msg == null) ? "Expected " : (msg + ": expected ")) + expected + ", but found " + encountered);
        this.node = node;
    }
}
