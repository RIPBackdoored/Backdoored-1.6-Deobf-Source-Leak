package org.reflections.vfs;

import java.net.*;
import java.util.jar.*;

enum Vfs$DefaultUrlTypes$1
{
    Vfs$DefaultUrlTypes$1(final String x0, final int x2) {
    }
    
    @Override
    public boolean matches(final URL url) {
        return url.getProtocol().equals("file") && Vfs.access$100(url);
    }
    
    @Override
    public Dir createDir(final URL url) throws Exception {
        return new ZipDir(new JarFile(Vfs.getFile(url)));
    }
}