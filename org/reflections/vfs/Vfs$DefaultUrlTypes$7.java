package org.reflections.vfs;

import java.net.*;

enum Vfs$DefaultUrlTypes$7
{
    Vfs$DefaultUrlTypes$7(final String x0, final int x2) {
    }
    
    @Override
    public boolean matches(final URL url) throws Exception {
        return url.toExternalForm().contains(".jar");
    }
    
    @Override
    public Dir createDir(final URL url) throws Exception {
        return new JarInputDir(url);
    }
}