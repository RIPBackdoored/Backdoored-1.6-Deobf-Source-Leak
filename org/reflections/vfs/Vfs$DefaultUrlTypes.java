package org.reflections.vfs;

import java.util.jar.*;
import java.net.*;
import java.io.*;
import org.reflections.util.*;

public enum DefaultUrlTypes implements UrlType
{
    jarFile {
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
    }, 
    jarUrl {
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
    }, 
    directory {
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
    }, 
    jboss_vfs {
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
    }, 
    jboss_vfsfile {
        Vfs$DefaultUrlTypes$5(final String x0, final int x2) {
        }
        
        @Override
        public boolean matches(final URL url) throws Exception {
            return "vfszip".equals(url.getProtocol()) || "vfsfile".equals(url.getProtocol());
        }
        
        @Override
        public Dir createDir(final URL url) throws Exception {
            return new UrlTypeVFS().createDir(url);
        }
    }, 
    bundle {
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
    }, 
    jarInputStream {
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
    };
    
    private static final /* synthetic */ DefaultUrlTypes[] $VALUES;
    
    public static DefaultUrlTypes[] values() {
        return DefaultUrlTypes.$VALUES.clone();
    }
    
    public static DefaultUrlTypes valueOf(final String name) {
        return Enum.valueOf(DefaultUrlTypes.class, name);
    }
    
    DefaultUrlTypes(final String x0, final int x1, final Vfs$1 x2) {
        this();
    }
    
    static {
        $VALUES = new DefaultUrlTypes[] { DefaultUrlTypes.jarFile, DefaultUrlTypes.jarUrl, DefaultUrlTypes.directory, DefaultUrlTypes.jboss_vfs, DefaultUrlTypes.jboss_vfsfile, DefaultUrlTypes.bundle, DefaultUrlTypes.jarInputStream };
    }
}
