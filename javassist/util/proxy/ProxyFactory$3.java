package javassist.util.proxy;

import java.util.*;

static final class ProxyFactory$3 implements Comparator {
    ProxyFactory$3() {
        super();
    }
    
    @Override
    public int compare(final Object o1, final Object o2) {
        final Map.Entry e1 = (Map.Entry)o1;
        final Map.Entry e2 = (Map.Entry)o2;
        final String key1 = e1.getKey();
        final String key2 = e2.getKey();
        return key1.compareTo(key2);
    }
}