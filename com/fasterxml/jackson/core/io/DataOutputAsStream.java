package com.fasterxml.jackson.core.io;

import java.io.*;

public class DataOutputAsStream extends OutputStream
{
    protected final DataOutput _output;
    
    public DataOutputAsStream(final DataOutput out) {
        super();
        this._output = out;
    }
    
    @Override
    public void write(final int b) throws IOException {
        this._output.write(b);
    }
    
    @Override
    public void write(final byte[] b) throws IOException {
        this._output.write(b, 0, b.length);
    }
    
    @Override
    public void write(final byte[] b, final int offset, final int length) throws IOException {
        this._output.write(b, offset, length);
    }
}
