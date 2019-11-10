package org.reflections.vfs;

import java.io.*;
import java.util.*;
import com.google.common.collect.*;

public class SystemDir implements Vfs.Dir
{
    private final java.io.File file;
    
    public SystemDir(final java.io.File file) {
        super();
        if (file != null && (!file.isDirectory() || !file.canRead())) {
            throw new RuntimeException("cannot use dir " + file);
        }
        this.file = file;
    }
    
    @Override
    public String getPath() {
        if (this.file == null) {
            return "/NO-SUCH-DIRECTORY/";
        }
        return this.file.getPath().replace("\\", "/");
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        if (this.file == null || !this.file.exists()) {
            return (Iterable<Vfs.File>)Collections.emptyList();
        }
        return new Iterable<Vfs.File>() {
            final /* synthetic */ SystemDir this$0;
            
            SystemDir$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>() {
                    final Stack<java.io.File> stack;
                    final /* synthetic */ SystemDir$1 this$1;
                    
                    SystemDir$1$1() {
                        this.this$1 = this$1;
                        super();
                    }
                    
                    {
                        (this.stack = new Stack<java.io.File>()).addAll((Collection<?>)listFiles(this.this$1.this$0.file));
                    }
                    
                    @Override
                    protected Vfs.File computeNext() {
                        while (!this.stack.isEmpty()) {
                            final java.io.File file = this.stack.pop();
                            if (!file.isDirectory()) {
                                return new SystemFile(this.this$1.this$0, file);
                            }
                            this.stack.addAll((Collection<?>)listFiles(file));
                        }
                        return this.endOfData();
                    }
                    
                    @Override
                    protected /* bridge */ Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
        };
    }
    
    private static List<java.io.File> listFiles(final java.io.File file) {
        final java.io.File[] files = file.listFiles();
        if (files != null) {
            return Lists.newArrayList(files);
        }
        return (List<java.io.File>)Lists.newArrayList();
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String toString() {
        return this.getPath();
    }
    
    static /* synthetic */ java.io.File access$000(final SystemDir x0) {
        return x0.file;
    }
    
    static /* synthetic */ List access$100(final java.io.File x0) {
        return listFiles(x0);
    }
}
