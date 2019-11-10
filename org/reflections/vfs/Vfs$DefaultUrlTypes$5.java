package org.reflections.vfs;

import java.net.*;

enum Vfs$DefaultUrlTypes$5
{
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
}