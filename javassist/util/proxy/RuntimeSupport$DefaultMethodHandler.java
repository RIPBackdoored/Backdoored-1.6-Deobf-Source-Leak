package javassist.util.proxy;

import java.io.*;
import java.lang.reflect.*;

static class DefaultMethodHandler implements MethodHandler, Serializable
{
    DefaultMethodHandler() {
        super();
    }
    
    @Override
    public Object invoke(final Object self, final Method m, final Method proceed, final Object[] args) throws Exception {
        return proceed.invoke(self, args);
    }
}
