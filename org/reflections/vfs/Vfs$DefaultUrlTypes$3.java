package org.reflections.vfs;

import java.net.*;
import java.io.*;

enum Vfs$DefaultUrlTypes$3
{
    Vfs$DefaultUrlTypes$3(final String x0, final int x2) {
    }
    
    @Override
    public boolean matches(final URL url) {
        if (url.getProtocol().equals("file") && !Vfs.access$100(url)) {
            final java.io.File file = Vfs.getFile(url);
            return file != null && file.isDirectory();
        }
        return false;
    }
    
    @Override
    public Dir createDir(final URL url) throws Exception {
        return new SystemDir(Vfs.getFile(url));
    }
}