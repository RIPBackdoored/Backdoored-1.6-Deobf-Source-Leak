package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.*;
import java.io.*;

public class MinimalPrettyPrinter implements PrettyPrinter, Serializable
{
    private static final long serialVersionUID = 1L;
    protected String _rootValueSeparator;
    protected Separators _separators;
    
    public MinimalPrettyPrinter() {
        this(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR.toString());
    }
    
    public MinimalPrettyPrinter(final String rootValueSeparator) {
        super();
        this._rootValueSeparator = rootValueSeparator;
        this._separators = MinimalPrettyPrinter.DEFAULT_SEPARATORS;
    }
    
    public void setRootValueSeparator(final String sep) {
        this._rootValueSeparator = sep;
    }
    
    public MinimalPrettyPrinter setSeparators(final Separators separators) {
        this._separators = separators;
        return this;
    }
    
    @Override
    public void writeRootValueSeparator(final JsonGenerator g) throws IOException {
        if (this._rootValueSeparator != null) {
            g.writeRaw(this._rootValueSeparator);
        }
    }
    
    @Override
    public void writeStartObject(final JsonGenerator g) throws IOException {
        g.writeRaw('{');
    }
    
    @Override
    public void beforeObjectEntries(final JsonGenerator g) throws IOException {
    }
    
    @Override
    public void writeObjectFieldValueSeparator(final JsonGenerator g) throws IOException {
        g.writeRaw(this._separators.getObjectFieldValueSeparator());
    }
    
    @Override
    public void writeObjectEntrySeparator(final JsonGenerator g) throws IOException {
        g.writeRaw(this._separators.getObjectEntrySeparator());
    }
    
    @Override
    public void writeEndObject(final JsonGenerator g, final int nrOfEntries) throws IOException {
        g.writeRaw('}');
    }
    
    @Override
    public void writeStartArray(final JsonGenerator g) throws IOException {
        g.writeRaw('[');
    }
    
    @Override
    public void beforeArrayValues(final JsonGenerator g) throws IOException {
    }
    
    @Override
    public void writeArrayValueSeparator(final JsonGenerator g) throws IOException {
        g.writeRaw(this._separators.getArrayValueSeparator());
    }
    
    @Override
    public void writeEndArray(final JsonGenerator g, final int nrOfValues) throws IOException {
        g.writeRaw(']');
    }
}
