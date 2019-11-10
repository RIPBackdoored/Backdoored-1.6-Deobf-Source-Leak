package javassist;

import java.util.*;

static final class SerialVersionUID$2 implements Comparator {
    SerialVersionUID$2() {
        super();
    }
    
    @Override
    public int compare(final Object o1, final Object o2) {
        final CtConstructor c1 = (CtConstructor)o1;
        final CtConstructor c2 = (CtConstructor)o2;
        return c1.getMethodInfo2().getDescriptor().compareTo(c2.getMethodInfo2().getDescriptor());
    }
}