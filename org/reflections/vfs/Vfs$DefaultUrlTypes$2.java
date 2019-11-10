package org.reflections.vfs;

import java.util.jar.*;
import java.net.*;
import java.io.*;

enum Vfs$DefaultUrlTypes$2
{
    Vfs$DefaultUrlTypes$2(final String x0, final int x2) {
    }
    
    @Override
    public boolean matches(final URL url) {
        return "jar".equals(url.getProtocol()) || "zip".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol());
    }
    
    @Override
    public Dir createDir(final URL url) throws Exception {
        try {
            final URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof JarURLConnection) {
                return new ZipDir(((JarURLConnection)urlConnection).getJarFile());
            }
        }
        catch (Throwable t) {}
        final java.io.File file = Vfs.getFile(url);
        if (file != null) {
            return new ZipDir(new JarFile(file));
        }
        return null;
    }
}