package javassist;

class FieldInitLink
{
    FieldInitLink next;
    CtField field;
    CtField.Initializer init;
    
    FieldInitLink(final CtField f, final CtField.Initializer i) {
        super();
        this.next = null;
        this.field = f;
        this.init = i;
    }
}
