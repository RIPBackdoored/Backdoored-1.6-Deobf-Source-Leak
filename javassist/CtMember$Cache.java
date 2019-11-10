package javassist;

static class Cache extends CtMember
{
    private CtMember methodTail;
    private CtMember consTail;
    private CtMember fieldTail;
    
    @Override
    protected void extendToString(final StringBuffer buffer) {
    }
    
    @Override
    public boolean hasAnnotation(final String clz) {
        return false;
    }
    
    @Override
    public Object getAnnotation(final Class clz) throws ClassNotFoundException {
        return null;
    }
    
    @Override
    public Object[] getAnnotations() throws ClassNotFoundException {
        return null;
    }
    
    @Override
    public byte[] getAttribute(final String name) {
        return null;
    }
    
    @Override
    public Object[] getAvailableAnnotations() {
        return null;
    }
    
    @Override
    public int getModifiers() {
        return 0;
    }
    
    @Override
    public String getName() {
        return null;
    }
    
    @Override
    public String getSignature() {
        return null;
    }
    
    @Override
    public void setAttribute(final String name, final byte[] data) {
    }
    
    @Override
    public void setModifiers(final int mod) {
    }
    
    @Override
    public String getGenericSignature() {
        return null;
    }
    
    @Override
    public void setGenericSignature(final String sig) {
    }
    
    Cache(final CtClassType decl) {
        super(decl);
        this.methodTail = this;
        this.consTail = this;
        this.fieldTail = this;
        this.fieldTail.next = this;
    }
    
    CtMember methodHead() {
        return this;
    }
    
    CtMember lastMethod() {
        return this.methodTail;
    }
    
    CtMember consHead() {
        return this.methodTail;
    }
    
    CtMember lastCons() {
        return this.consTail;
    }
    
    CtMember fieldHead() {
        return this.consTail;
    }
    
    CtMember lastField() {
        return this.fieldTail;
    }
    
    void addMethod(final CtMember method) {
        method.next = this.methodTail.next;
        this.methodTail.next = method;
        if (this.methodTail == this.consTail) {
            this.consTail = method;
            if (this.methodTail == this.fieldTail) {
                this.fieldTail = method;
            }
        }
        this.methodTail = method;
    }
    
    void addConstructor(final CtMember cons) {
        cons.next = this.consTail.next;
        this.consTail.next = cons;
        if (this.consTail == this.fieldTail) {
            this.fieldTail = cons;
        }
        this.consTail = cons;
    }
    
    void addField(final CtMember field) {
        field.next = this;
        this.fieldTail.next = field;
        this.fieldTail = field;
    }
    
    static int count(CtMember head, final CtMember tail) {
        int n = 0;
        while (head != tail) {
            ++n;
            head = head.next;
        }
        return n;
    }
    
    void remove(final CtMember mem) {
        CtMember m = this;
        CtMember node;
        while ((node = m.next) != this) {
            if (node == mem) {
                m.next = node.next;
                if (node == this.methodTail) {
                    this.methodTail = m;
                }
                if (node == this.consTail) {
                    this.consTail = m;
                }
                if (node == this.fieldTail) {
                    this.fieldTail = m;
                    break;
                }
                break;
            }
            else {
                m = m.next;
            }
        }
    }
}
