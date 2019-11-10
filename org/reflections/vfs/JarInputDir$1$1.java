package org.reflections.vfs;

import com.google.common.collect.*;
import java.util.jar.*;
import org.reflections.*;
import java.io.*;
import java.util.zip.*;

class JarInputDir$1$1 extends AbstractIterator<Vfs.File> {
    final /* synthetic */ JarInputDir$1 this$1;
    
    JarInputDir$1$1(final JarInputDir$1 this$1) throws Error {
        this.this$1 = this$1;
        super();
    }
    
    {
        try {
            this.this$1.this$0.jarInputStream = new JarInputStream(JarInputDir.access$000(this.this$1.this$0).openConnection().getInputStream());
        }
        catch (Exception e) {
            throw new ReflectionsException("Could not open url connection", e);
        }
    }
    
    @Override
    protected Vfs.File computeNext() {
        try {
            while (true) {
                final ZipEntry entry = this.this$1.this$0.jarInputStream.getNextJarEntry();
                if (entry == null) {
                    return this.endOfData();
                }
                long size = entry.getSize();
                if (size < 0L) {
                    size += 4294967295L;
                }
                final JarInputDir this$0 = this.this$1.this$0;
                this$0.nextCursor += size;
                if (!entry.isDirectory()) {
                    return new JarInputFile(entry, this.this$1.this$0, this.this$1.this$0.cursor, this.this$1.this$0.nextCursor);
                }
            }
        }
        catch (IOException e) {
            throw new ReflectionsException("could not get next zip entry", e);
        }
    }
    
    @Override
    protected /* bridge */ Object computeNext() {
        return this.computeNext();
    }
}