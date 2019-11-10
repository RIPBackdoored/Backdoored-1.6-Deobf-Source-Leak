package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.io.*;

class CheckMethodAdapter$1 extends MethodNode {
    final /* synthetic */ MethodVisitor val$cmv;
    
    CheckMethodAdapter$1(final int api, final int access, final String name, final String desc, final String signature, final String[] exceptions, final MethodVisitor val$cmv) {
        this.val$cmv = val$cmv;
        super(api, access, name, desc, signature, exceptions);
    }
    
    @Override
    public void visitEnd() {
        final Analyzer<BasicValue> a = new Analyzer<BasicValue>(new BasicVerifier());
        try {
            a.analyze("dummy", this);
        }
        catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
                throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
            }
            e.printStackTrace();
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            CheckClassAdapter.printAnalyzerResult(this, a, pw);
            pw.close();
            throw new RuntimeException(e.getMessage() + ' ' + sw.toString());
        }
        this.accept(this.val$cmv);
    }
}