package javassist.util.proxy;

import java.lang.ref.*;

static class ProxyDetails
{
    byte[] signature;
    WeakReference proxyClass;
    boolean isUseWriteReplace;
    
    ProxyDetails(final byte[] signature, final Class proxyClass, final boolean isUseWriteReplace) {
        super();
        this.signature = signature;
        this.proxyClass = new WeakReference((T)proxyClass);
        this.isUseWriteReplace = isUseWriteReplace;
    }
}
