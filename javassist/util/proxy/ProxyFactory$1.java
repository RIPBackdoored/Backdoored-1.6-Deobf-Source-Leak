package javassist.util.proxy;

static final class ProxyFactory$1 implements ClassLoaderProvider {
    ProxyFactory$1() {
        super();
    }
    
    @Override
    public ClassLoader get(final ProxyFactory pf) {
        return pf.getClassLoader0();
    }
}