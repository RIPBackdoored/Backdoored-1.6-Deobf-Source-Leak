package javassist.compiler;

import javassist.bytecode.*;

static class JsrHook2 extends ReturnHook
{
    int var;
    int target;
    
    JsrHook2(final CodeGen gen, final int[] retTarget) {
        super(gen);
        this.target = retTarget[0];
        this.var = retTarget[1];
    }
    
    @Override
    protected boolean doit(final Bytecode b, final int opcode) {
        switch (opcode) {
            case 177: {
                break;
            }
            case 176: {
                b.addAstore(this.var);
                break;
            }
            case 172: {
                b.addIstore(this.var);
                break;
            }
            case 173: {
                b.addLstore(this.var);
                break;
            }
            case 175: {
                b.addDstore(this.var);
                break;
            }
            case 174: {
                b.addFstore(this.var);
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        b.addOpcode(167);
        b.addIndex(this.target - b.currentPc() + 3);
        return true;
    }
}
