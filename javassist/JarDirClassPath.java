package javassist;

import java.io.*;
import java.net.*;

final class JarDirClassPath implements ClassPath
{
    JarClassPath[] jars;
    
    JarDirClassPath(final String dirName) throws NotFoundException {
        super();
        final File[] files = new File(dirName).listFiles(new FilenameFilter() {
            final /* synthetic */ JarDirClassPath this$0;
            
            JarDirClassPath$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public boolean accept(final File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jar") || name.endsWith(".zip");
            }
        });
        if (files != null) {
            this.jars = new JarClassPath[files.length];
            for (int i = 0; i < files.length; ++i) {
                this.jars[i] = new JarClassPath(files[i].getPath());
            }
        }
    }
    
    @Override
    public InputStream openClassfile(final String classname) throws NotFoundException {
        if (this.jars != null) {
            for (int i = 0; i < this.jars.length; ++i) {
                final InputStream is = this.jars[i].openClassfile(classname);
                if (is != null) {
                    return is;
                }
            }
        }
        return null;
    }
    
    @Override
    public URL find(final String classname) {
        if (this.jars != null) {
            for (int i = 0; i < this.jars.length; ++i) {
                final URL url = this.jars[i].find(classname);
                if (url != null) {
                    return url;
                }
            }
        }
        return null;
    }
    
    @Override
    public void close() {
        if (this.jars != null) {
            for (int i = 0; i < this.jars.length; ++i) {
                this.jars[i].close();
            }
        }
    }
}
