package javassist;

import java.io.*;

class JarDirClassPath$1 implements FilenameFilter {
    final /* synthetic */ JarDirClassPath this$0;
    
    JarDirClassPath$1(final JarDirClassPath this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public boolean accept(final File dir, String name) {
        name = name.toLowerCase();
        return name.endsWith(".jar") || name.endsWith(".zip");
    }
}