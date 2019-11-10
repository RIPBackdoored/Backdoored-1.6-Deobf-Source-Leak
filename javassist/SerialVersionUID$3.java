package javassist;

import java.util.*;

static final class SerialVersionUID$3 implements Comparator {
    SerialVersionUID$3() {
        super();
    }
    
    @Override
    public int compare(final Object o1, final Object o2) {
        final CtMethod m1 = (CtMethod)o1;
        final CtMethod m2 = (CtMethod)o2;
        int value = m1.getName().compareTo(m2.getName());
        if (value == 0) {
            value = m1.getMethodInfo2().getDescriptor().compareTo(m2.getMethodInfo2().getDescriptor());
        }
        return value;
    }
}