package com.sun.jna;

import java.io.*;

static final class NativeLibrary$2 implements FilenameFilter {
    final /* synthetic */ String val$libName;
    
    NativeLibrary$2(final String val$libName) {
        this.val$libName = val$libName;
        super();
    }
    
    @Override
    public boolean accept(final File dir, final String filename) {
        return (filename.startsWith("lib" + this.val$libName + ".so") || (filename.startsWith(this.val$libName + ".so") && this.val$libName.startsWith("lib"))) && NativeLibrary.access$000(filename);
    }
}