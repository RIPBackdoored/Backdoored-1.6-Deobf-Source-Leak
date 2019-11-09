package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable
{
    private static final long serialVersionUID = 1L;
    public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR;
    protected Indenter _arrayIndenter;
    protected Indenter _objectIndenter;
    protected final SerializableString _rootSeparator;
    protected boolean _spacesInObjectEntries;
    protected transient int _nesting;
    protected Separators _separators;
    protected String _objectFieldValueSeparatorWithSpaces;
    
    public DefaultPrettyPrinter() {
        this(DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    }
    
    public DefaultPrettyPrinter(final String rootSeparator) {
        this((rootSeparator == null) ? null : new SerializedString(rootSeparator));
    }
    
    public DefaultPrettyPrinter(final SerializableString rootSeparator) {
        super();
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._rootSeparator = rootSeparator;
        this.withSeparators(DefaultPrettyPrinter.DEFAULT_SEPARATORS);
    }
    
    public DefaultPrettyPrinter(final DefaultPrettyPrinter base) {
        this(base, base._rootSeparator);
    }
    
    public DefaultPrettyPrinter(final DefaultPrettyPrinter base, final SerializableString rootSeparator) {
        super();
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._arrayIndenter = base._arrayIndenter;
        this._objectIndenter = base._objectIndenter;
        this._spacesInObjectEntries = base._spacesInObjectEntries;
        this._nesting = base._nesting;
        this._separators = base._separators;
        this._objectFieldValueSeparatorWithSpaces = base._objectFieldValueSeparatorWithSpaces;
        this._rootSeparator = rootSeparator;
    }
    
    public DefaultPrettyPrinter withRootSeparator(final SerializableString rootSeparator) {
        if (this._rootSeparator == rootSeparator || (rootSeparator != null && rootSeparator.equals(this._rootSeparator))) {
            return this;
        }
        return new DefaultPrettyPrinter(this, rootSeparator);
    }
    
    public DefaultPrettyPrinter withRootSeparator(final String rootSeparator) {
        return this.withRootSeparator((rootSeparator == null) ? null : new SerializedString(rootSeparator));
    }
    
    public void indentArraysWith(final Indenter i) {
        this._arrayIndenter = ((i == null) ? NopIndenter.instance : i);
    }
    
    public void indentObjectsWith(final Indenter i) {
        this._objectIndenter = ((i == null) ? NopIndenter.instance : i);
    }
    
    public DefaultPrettyPrinter withArrayIndenter(Indenter i) {
        if (i == null) {
            i = NopIndenter.instance;
        }
        if (this._arrayIndenter == i) {
            return this;
        }
        final DefaultPrettyPrinter pp = new DefaultPrettyPrinter(this);
        pp._arrayIndenter = i;
        return pp;
    }
    
    public DefaultPrettyPrinter withObjectIndenter(Indenter i) {
        if (i == null) {
            i = NopIndenter.instance;
        }
        if (this._objectIndenter == i) {
            return this;
        }
        final DefaultPrettyPrinter pp = new DefaultPrettyPrinter(this);
        pp._objectIndenter = i;
        return pp;
    }
    
    public DefaultPrettyPrinter withSpacesInObjectEntries() {
        return this._withSpaces(true);
    }
    
    public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
        return this._withSpaces(false);
    }
    
    protected DefaultPrettyPrinter _withSpaces(final boolean state) {
        if (this._spacesInObjectEntries == state) {
            return this;
        }
        final DefaultPrettyPrinter pp = new DefaultPrettyPrinter(this);
        pp._spacesInObjectEntries = state;
        return pp;
    }
    
    public DefaultPrettyPrinter withSeparators(final Separators separators) {
        this._separators = separators;
        this._objectFieldValueSeparatorWithSpaces = " " + separators.getObjectFieldValueSeparator() + " ";
        return this;
    }
    
    @Override
    public DefaultPrettyPrinter createInstance() {
        return new DefaultPrettyPrinter(this);
    }
    
    @Override
    public void writeRootValueSeparator(final JsonGenerator g) throws IOException {
        if (this._rootSeparator != null) {
            g.writeRaw(this._rootSeparator);
        }
    }
    
    @Override
    public void writeStartObject(final JsonGenerator g) throws IOException {
        g.writeRaw('{');
        if (!this._objectIndenter.isInline()) {
            ++this._nesting;
        }
    }
    
    @Override
    public void beforeObjectEntries(final JsonGenerator g) throws IOException {
        this._objectIndenter.writeIndentation(g, this._nesting);
    }
    
    @Override
    public void writeObjectFieldValueSeparator(final JsonGenerator g) throws IOException {
        if (this._spacesInObjectEntries) {
            g.writeRaw(this._objectFieldValueSeparatorWithSpaces);
        }
        else {
            g.writeRaw(this._separators.getObjectFieldValueSeparator());
        }
    }
    
    @Override
    public void writeObjectEntrySeparator(final JsonGenerator g) throws IOException {
        g.writeRaw(this._separators.getObjectEntrySeparator());
        this._objectIndenter.writeIndentation(g, this._nesting);
    }
    
    @Override
    public void writeEndObject(final JsonGenerator g, final int nrOfEntries) throws IOException {
        if (!this._objectIndenter.isInline()) {
            --this._nesting;
        }
        if (nrOfEntries > 0) {
            this._objectIndenter.writeIndentation(g, this._nesting);
        }
        else {
            g.writeRaw(' ');
        }
        g.writeRaw('}');
    }
    
    @Override
    public void writeStartArray(final JsonGenerator g) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            ++this._nesting;
        }
        g.writeRaw('[');
    }
    
    @Override
    public void beforeArrayValues(final JsonGenerator g) throws IOException {
        this._arrayIndenter.writeIndentation(g, this._nesting);
    }
    
    @Override
    public void writeArrayValueSeparator(final JsonGenerator g) throws IOException {
        g.writeRaw(this._separators.getArrayValueSeparator());
        this._arrayIndenter.writeIndentation(g, this._nesting);
    }
    
    @Override
    public void writeEndArray(final JsonGenerator g, final int nrOfValues) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            --this._nesting;
        }
        if (nrOfValues > 0) {
            this._arrayIndenter.writeIndentation(g, this._nesting);
        }
        else {
            g.writeRaw(' ');
        }
        g.writeRaw(']');
    }
    
    @Override
    public /* bridge */ Object createInstance() {
        return this.createInstance();
    }
    
    static {
        DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
    }
    
    public static class NopIndenter implements Indenter, Serializable
    {
        public static final NopIndenter instance;
        
        public NopIndenter() {
            super();
        }
        
        @Override
        public void writeIndentation(final JsonGenerator g, final int level) throws IOException {
        }
        
        @Override
        public boolean isInline() {
            return true;
        }
        
        static {
            instance = new NopIndenter();
        }
    }
    
    public static class FixedSpaceIndenter extends NopIndenter
    {
        public static final FixedSpaceIndenter instance;
        
        public FixedSpaceIndenter() {
            super();
        }
        
        @Override
        public void writeIndentation(final JsonGenerator g, final int level) throws IOException {
            g.writeRaw(' ');
        }
        
        @Override
        public boolean isInline() {
            return true;
        }
        
        static {
            instance = new FixedSpaceIndenter();
        }
    }
    
    public interface Indenter
    {
        void writeIndentation(final JsonGenerator p0, final int p1) throws IOException;
        
        boolean isInline();
    }
}
