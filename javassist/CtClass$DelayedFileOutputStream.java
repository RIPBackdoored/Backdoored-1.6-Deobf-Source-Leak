package javassist;

import java.io.*;

static class DelayedFileOutputStream extends OutputStream
{
    private FileOutputStream file;
    private String filename;
    
    DelayedFileOutputStream(final String name) {
        super();
        this.file = null;
        this.filename = name;
    }
    
    private void init() throws IOException {
        if (this.file == null) {
            this.file = new FileOutputStream(this.filename);
        }
    }
    
    @Override
    public void write(final int b) throws IOException {
        this.init();
        this.file.write(b);
    }
    
    @Override
    public void write(final byte[] b) throws IOException {
        this.init();
        this.file.write(b);
    }
    
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        this.init();
        this.file.write(b, off, len);
    }
    
    @Override
    public void flush() throws IOException {
        this.init();
        this.file.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.init();
        this.file.close();
    }
}
