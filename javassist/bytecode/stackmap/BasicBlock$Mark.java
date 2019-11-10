package javassist.bytecode.stackmap;

static class Mark implements Comparable
{
    int position;
    BasicBlock block;
    BasicBlock[] jump;
    boolean alwaysJmp;
    int size;
    Catch catcher;
    
    Mark(final int p) {
        super();
        this.position = p;
        this.block = null;
        this.jump = null;
        this.alwaysJmp = false;
        this.size = 0;
        this.catcher = null;
    }
    
    @Override
    public int compareTo(final Object obj) {
        if (obj instanceof Mark) {
            final int pos = ((Mark)obj).position;
            return this.position - pos;
        }
        return -1;
    }
    
    void setJump(final BasicBlock[] bb, final int s, final boolean always) {
        this.jump = bb;
        this.size = s;
        this.alwaysJmp = always;
    }
}
