package org.reflections.vfs;

import java.net.*;
import java.util.jar.*;
import java.util.*;
import com.google.common.collect.*;
import org.reflections.*;
import java.util.zip.*;
import org.reflections.util.*;
import java.io.*;

public class JarInputDir implements Vfs.Dir
{
    private final URL url;
    JarInputStream jarInputStream;
    long cursor;
    long nextCursor;
    
    public JarInputDir(final URL url) {
        super();
        this.cursor = 0L;
        this.nextCursor = 0L;
        this.url = url;
    }
    
    @Override
    public String getPath() {
        return this.url.getPath();
    }
    
    @Override
    public Iterable<Vfs.File> getFiles() {
        return new Iterable<Vfs.File>() {
            final /* synthetic */ JarInputDir this$0;
            
            JarInputDir$1() {
                this.this$0 = this$0;
                super();
            }
            
            @Override
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>() {
                    final /* synthetic */ JarInputDir$1 this$1;
                    
                    JarInputDir$1$1() throws Error {
                        this.this$1 = this$1;
                        super();
                    }
                    
                    {
                        try {
                            this.this$1.this$0.jarInputStream = new JarInputStream(this.this$1.this$0.url.openConnection().getInputStream());
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
                };
            }
        };
    }
    
    @Override
    public void close() {
        Utils.close(this.jarInputStream);
    }
    
    static /* synthetic */ URL access$000(final JarInputDir x0) {
        return x0.url;
    }
}
