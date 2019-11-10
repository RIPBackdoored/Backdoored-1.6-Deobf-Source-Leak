package org.reflections.vfs;

import java.net.*;
import org.reflections.util.*;

enum Vfs$DefaultUrlTypes$6
{
    Vfs$DefaultUrlTypes$6(final String x0, final int x2) {
    }
    
    @Override
    public boolean matches(final URL url) throws Exception {
        return url.getProtocol().startsWith("bundle");
    }
    
    @Override
    public Dir createDir(final URL url) throws Exception {
        return Vfs.fromURL((URL)ClasspathHelper.contextClassLoader().loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke(null, url));
    }
}