package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;

public abstract class JsonGeneratorImpl extends GeneratorBase
{
    protected static final int[] sOutputEscapes;
    protected final IOContext _ioContext;
    protected int[] _outputEscapes;
    protected int _maximumNonEscapedChar;
    protected CharacterEscapes _characterEscapes;
    protected SerializableString _rootValueSeparator;
    protected boolean _cfgUnqNames;
    
    public JsonGeneratorImpl(final IOContext ctxt, final int features, final ObjectCodec codec) {
        super(features, codec);
        this._outputEscapes = JsonGeneratorImpl.sOutputEscapes;
        this._rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        this._ioContext = ctxt;
        if (Feature.ESCAPE_NON_ASCII.enabledIn(features)) {
            this._maximumNonEscapedChar = 127;
        }
        this._cfgUnqNames = !Feature.QUOTE_FIELD_NAMES.enabledIn(features);
    }
    
    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }
    
    @Override
    public JsonGenerator enable(final Feature f) {
        super.enable(f);
        if (f == Feature.QUOTE_FIELD_NAMES) {
            this._cfgUnqNames = false;
        }
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature f) {
        super.disable(f);
        if (f == Feature.QUOTE_FIELD_NAMES) {
            this._cfgUnqNames = true;
        }
        return this;
    }
    
    @Override
    protected void _checkStdFeatureChanges(final int newFeatureFlags, final int changedFeatures) {
        super._checkStdFeatureChanges(newFeatureFlags, changedFeatures);
        this._cfgUnqNames = !Feature.QUOTE_FIELD_NAMES.enabledIn(newFeatureFlags);
    }
    
    @Override
    public JsonGenerator setHighestNonEscapedChar(final int charCode) {
        this._maximumNonEscapedChar = ((charCode < 0) ? 0 : charCode);
        return this;
    }
    
    @Override
    public int getHighestEscapedChar() {
        return this._maximumNonEscapedChar;
    }
    
    @Override
    public JsonGenerator setCharacterEscapes(final CharacterEscapes esc) {
        this._characterEscapes = esc;
        if (esc == null) {
            this._outputEscapes = JsonGeneratorImpl.sOutputEscapes;
        }
        else {
            this._outputEscapes = esc.getEscapeCodesForAscii();
        }
        return this;
    }
    
    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }
    
    @Override
    public JsonGenerator setRootValueSeparator(final SerializableString sep) {
        this._rootValueSeparator = sep;
        return this;
    }
    
    @Override
    public final void writeStringField(final String fieldName, final String value) throws IOException {
        this.writeFieldName(fieldName);
        this.writeString(value);
    }
    
    protected void _verifyPrettyValueWrite(final String typeMsg, final int status) throws IOException {
        switch (status) {
            case 1: {
                this._cfgPrettyPrinter.writeArrayValueSeparator(this);
                break;
            }
            case 2: {
                this._cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
                break;
            }
            case 3: {
                this._cfgPrettyPrinter.writeRootValueSeparator(this);
                break;
            }
            case 0: {
                if (this._writeContext.inArray()) {
                    this._cfgPrettyPrinter.beforeArrayValues(this);
                    break;
                }
                if (this._writeContext.inObject()) {
                    this._cfgPrettyPrinter.beforeObjectEntries(this);
                    break;
                }
                break;
            }
            case 5: {
                this._reportCantWriteValueExpectName(typeMsg);
                break;
            }
            default: {
                this._throwInternal();
                break;
            }
        }
    }
    
    protected void _reportCantWriteValueExpectName(final String typeMsg) throws IOException {
        this._reportError(String.format("Can not %s, expecting field name (context: %s)", typeMsg, this._writeContext.typeDesc()));
    }
    
    static {
        sOutputEscapes = CharTypes.get7BitOutputEscapes();
    }
}
