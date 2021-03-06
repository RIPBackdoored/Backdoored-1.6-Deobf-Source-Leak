package org.reflections.vfs;

import java.util.zip.*;
import java.io.*;

public class JarInputFile implements Vfs.File
{
    private final ZipEntry entry;
    private final JarInputDir jarInputDir;
    private final long fromIndex;
    private final long endIndex;
    
    public JarInputFile(final ZipEntry entry, final JarInputDir jarInputDir, final long cursor, final long nextCursor) {
        super();
        this.entry = entry;
        this.jarInputDir = jarInputDir;
        this.fromIndex = cursor;
        this.endIndex = nextCursor;
    }
    
    @Override
    public String getName() {
        final String name = this.entry.getName();
        return name.substring(name.lastIndexOf("/") + 1);
    }
    
    @Override
    public String getRelativePath() {
        return this.entry.getName();
    }
    
    @Override
    public InputStream openInputStream() throws IOException {
        return new InputStream() {
            final /* synthetic */ JarInputFile this$0;
            
            JarInputFile$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public int read() throws IOException {
                if (this.this$0.jarInputDir.cursor >= this.this$0.fromIndex && this.this$0.jarInputDir.cursor <= this.this$0.endIndex) {
                    final int read = this.this$0.jarInputDir.jarInputStream.read();
                    final JarInputDir access$000 = this.this$0.jarInputDir;
                    ++access$000.cursor;
                    return read;
                }
                return -1;
            }
        };
    }
    
    static /* synthetic */ JarInputDir access$000(final JarInputFile x0) {
        return x0.jarInputDir;
    }
    
    static /* synthetic */ long access$100(final JarInputFile x0) {
        return x0.fromIndex;
    }
    
    static /* synthetic */ long access$200(final JarInputFile x0) {
        return x0.endIndex;
    }
}
