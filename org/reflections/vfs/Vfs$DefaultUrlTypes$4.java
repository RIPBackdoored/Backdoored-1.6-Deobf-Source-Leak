package org.reflections.vfs;

import java.net.*;
import org.reflections.util.*;
import java.io.*;
import java.util.jar.*;

enum Vfs$DefaultUrlTypes$4
{
    Vfs$DefaultUrlTypes$4(final String x0, final int x2) {
    }
    
    @Override
    public boolean matches(final URL url) {
        return url.getProtocol().equals("vfs");
    }
    
    @Override
    public Dir createDir(final URL url) throws Exception {
        final Object content = url.openConnection().getContent();
        final Class<?> virtualFile = ClasspathHelper.contextClassLoader().loadClass("org.jboss.vfs.VirtualFile");
        final java.io.File physicalFile = (java.io.File)virtualFile.getMethod("getPhysicalFile", (Class<?>[])new Class[0]).invoke(content, new Object[0]);
        final String name = (String)virtualFile.getMethod("getName", (Class<?>[])new Class[0]).invoke(content, new Object[0]);
        java.io.File file = new java.io.File(physicalFile.getParentFile(), name);
        if (!file.exists() || !file.canRead()) {
            file = physicalFile;
        }
        return file.isDirectory() ? new SystemDir(file) : new ZipDir(new JarFile(file));
    }
}