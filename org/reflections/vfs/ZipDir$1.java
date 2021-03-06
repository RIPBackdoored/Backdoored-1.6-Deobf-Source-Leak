package org.reflections.vfs;

import com.google.common.collect.*;
import java.util.*;
import java.util.zip.*;

class ZipDir$1 implements Iterable<Vfs.File> {
    final /* synthetic */ ZipDir this$0;
    
    ZipDir$1(final ZipDir this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Iterator<Vfs.File> iterator() {
        return new AbstractIterator<Vfs.File>() {
            final Enumeration<? extends ZipEntry> entries = this.this$1.this$0.jarFile.entries();
            final /* synthetic */ ZipDir$1 this$1;
            
            ZipDir$1$1() {
                this.this$1 = this$1;
                super();
            }
            
            @Override
            protected Vfs.File computeNext() {
                while (this.entries.hasMoreElements()) {
                    final ZipEntry entry = (ZipEntry)this.entries.nextElement();
                    if (!entry.isDirectory()) {
                        return new ZipFile(this.this$1.this$0, entry);
                    }
                }
                return this.endOfData();
            }
            
            @Override
            protected /* bridge */ Object computeNext() {
                return this.computeNext();
            }
        };
    }
}