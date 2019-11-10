package javassist;

import java.util.*;

static final class SerialVersionUID$1 implements Comparator {
    SerialVersionUID$1() {
        super();
    }
    
    @Override
    public int compare(final Object o1, final Object o2) {
        final CtField field1 = (CtField)o1;
        final CtField field2 = (CtField)o2;
        return field1.getName().compareTo(field2.getName());
    }
}