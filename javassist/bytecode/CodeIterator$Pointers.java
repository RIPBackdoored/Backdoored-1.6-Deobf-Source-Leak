package javassist.bytecode;

static class Pointers
{
    int cursor;
    int mark0;
    int mark;
    ExceptionTable etable;
    LineNumberAttribute line;
    LocalVariableAttribute vars;
    LocalVariableAttribute types;
    StackMapTable stack;
    StackMap stack2;
    
    Pointers(final int cur, final int m, final int m0, final ExceptionTable et, final CodeAttribute ca) {
        super();
        this.cursor = cur;
        this.mark = m;
        this.mark0 = m0;
        this.etable = et;
        this.line = (LineNumberAttribute)ca.getAttribute("LineNumberTable");
        this.vars = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
        this.types = (LocalVariableAttribute)ca.getAttribute("LocalVariableTypeTable");
        this.stack = (StackMapTable)ca.getAttribute("StackMapTable");
        this.stack2 = (StackMap)ca.getAttribute("StackMap");
    }
    
    void shiftPc(final int where, final int gapLength, final boolean exclusive) throws BadBytecode {
        if (where < this.cursor || (where == this.cursor && exclusive)) {
            this.cursor += gapLength;
        }
        if (where < this.mark || (where == this.mark && exclusive)) {
            this.mark += gapLength;
        }
        if (where < this.mark0 || (where == this.mark0 && exclusive)) {
            this.mark0 += gapLength;
        }
        this.etable.shiftPc(where, gapLength, exclusive);
        if (this.line != null) {
            this.line.shiftPc(where, gapLength, exclusive);
        }
        if (this.vars != null) {
            this.vars.shiftPc(where, gapLength, exclusive);
        }
        if (this.types != null) {
            this.types.shiftPc(where, gapLength, exclusive);
        }
        if (this.stack != null) {
            this.stack.shiftPc(where, gapLength, exclusive);
        }
        if (this.stack2 != null) {
            this.stack2.shiftPc(where, gapLength, exclusive);
        }
    }
    
    void shiftForSwitch(final int where, final int gapLength) throws BadBytecode {
        if (this.stack != null) {
            this.stack.shiftForSwitch(where, gapLength);
        }
        if (this.stack2 != null) {
            this.stack2.shiftForSwitch(where, gapLength);
        }
    }
}
