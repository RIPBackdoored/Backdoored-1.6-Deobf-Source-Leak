package com.sun.jna;

import java.io.*;

static final class Native$5 implements FilenameFilter {
    Native$5() {
        super();
    }
    
    @Override
    public boolean accept(final File dir, final String name) {
        return name.endsWith(".x") && name.startsWith("jna");
    }
}