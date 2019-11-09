package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.json.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;
import java.math.*;

public abstract class GeneratorBase extends JsonGenerator
{
    public static final int SURR1_FIRST = 55296;
    public static final int SURR1_LAST = 56319;
    public static final int SURR2_FIRST = 56320;
    public static final int SURR2_LAST = 57343;
    protected static final int DERIVED_FEATURES_MASK;
    protected static final String WRITE_BINARY = "write a binary value";
    protected static final String WRITE_BOOLEAN = "write a boolean value";
    protected static final String WRITE_NULL = "write a null";
    protected static final String WRITE_NUMBER = "write a number";
    protected static final String WRITE_RAW = "write a raw (unencoded) value";
    protected static final String WRITE_STRING = "write a string";
    protected static final int MAX_BIG_DECIMAL_SCALE = 9999;
    protected ObjectCodec _objectCodec;
    protected int _features;
    protected boolean _cfgNumbersAsStrings;
    protected JsonWriteContext _writeContext;
    protected boolean _closed;
    
    protected GeneratorBase(final int features, final ObjectCodec codec) {
        super();
        this._features = features;
        this._objectCodec = codec;
        final DupDetector dups = Feature.STRICT_DUPLICATE_DETECTION.enabledIn(features) ? DupDetector.rootDetector(this) : null;
        this._writeContext = JsonWriteContext.createRootContext(dups);
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
    }
    
    protected GeneratorBase(final int features, final ObjectCodec codec, final JsonWriteContext ctxt) {
        super();
        this._features = features;
        this._objectCodec = codec;
        this._writeContext = ctxt;
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(features);
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._writeContext.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object v) {
        this._writeContext.setCurrentValue(v);
    }
    
    @Override
    public final boolean isEnabled(final Feature f) {
        return (this._features & f.getMask()) != 0x0;
    }
    
    @Override
    public int getFeatureMask() {
        return this._features;
    }
    
    @Override
    public JsonGenerator enable(final Feature f) {
        final int mask = f.getMask();
        this._features |= mask;
        if ((mask & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (f == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = true;
            }
            else if (f == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(127);
            }
            else if (f == Feature.STRICT_DUPLICATE_DETECTION && this._writeContext.getDupDetector() == null) {
                this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
            }
        }
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature f) {
        final int mask = f.getMask();
        this._features &= ~mask;
        if ((mask & GeneratorBase.DERIVED_FEATURES_MASK) != 0x0) {
            if (f == Feature.WRITE_NUMBERS_AS_STRINGS) {
                this._cfgNumbersAsStrings = false;
            }
            else if (f == Feature.ESCAPE_NON_ASCII) {
                this.setHighestNonEscapedChar(0);
            }
            else if (f == Feature.STRICT_DUPLICATE_DETECTION) {
                this._writeContext = this._writeContext.withDupDetector(null);
            }
        }
        return this;
    }
    
    @Deprecated
    @Override
    public JsonGenerator setFeatureMask(final int newMask) {
        final int changed = newMask ^ this._features;
        this._features = newMask;
        if (changed != 0) {
            this._checkStdFeatureChanges(newMask, changed);
        }
        return this;
    }
    
    @Override
    public JsonGenerator overrideStdFeatures(final int values, final int mask) {
        final int oldState = this._features;
        final int newState = (oldState & ~mask) | (values & mask);
        final int changed = oldState ^ newState;
        if (changed != 0) {
            this._checkStdFeatureChanges(this._features = newState, changed);
        }
        return this;
    }
    
    protected void _checkStdFeatureChanges(final int newFeatureFlags, final int changedFeatures) {
        if ((changedFeatures & GeneratorBase.DERIVED_FEATURES_MASK) == 0x0) {
            return;
        }
        this._cfgNumbersAsStrings = Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(newFeatureFlags);
        if (Feature.ESCAPE_NON_ASCII.enabledIn(changedFeatures)) {
            if (Feature.ESCAPE_NON_ASCII.enabledIn(newFeatureFlags)) {
                this.setHighestNonEscapedChar(127);
            }
            else {
                this.setHighestNonEscapedChar(0);
            }
        }
        if (Feature.STRICT_DUPLICATE_DETECTION.enabledIn(changedFeatures)) {
            if (Feature.STRICT_DUPLICATE_DETECTION.enabledIn(newFeatureFlags)) {
                if (this._writeContext.getDupDetector() == null) {
                    this._writeContext = this._writeContext.withDupDetector(DupDetector.rootDetector(this));
                }
            }
            else {
                this._writeContext = this._writeContext.withDupDetector(null);
            }
        }
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        if (this.getPrettyPrinter() != null) {
            return this;
        }
        return this.setPrettyPrinter(this._constructDefaultPrettyPrinter());
    }
    
    @Override
    public JsonGenerator setCodec(final ObjectCodec oc) {
        this._objectCodec = oc;
        return this;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public JsonStreamContext getOutputContext() {
        return this._writeContext;
    }
    
    @Override
    public void writeStartObject(final Object forValue) throws IOException {
        this.writeStartObject();
        if (this._writeContext != null && forValue != null) {
            this._writeContext.setCurrentValue(forValue);
        }
        this.setCurrentValue(forValue);
    }
    
    @Override
    public void writeFieldName(final SerializableString name) throws IOException {
        this.writeFieldName(name.getValue());
    }
    
    @Override
    public void writeString(final SerializableString text) throws IOException {
        this.writeString(text.getValue());
    }
    
    @Override
    public void writeRawValue(final String text) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text);
    }
    
    @Override
    public void writeRawValue(final String text, final int offset, final int len) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text, offset, len);
    }
    
    @Override
    public void writeRawValue(final char[] text, final int offset, final int len) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text, offset, len);
    }
    
    @Override
    public void writeRawValue(final SerializableString text) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(text);
    }
    
    @Override
    public int writeBinary(final Base64Variant b64variant, final InputStream data, final int dataLength) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }
    
    @Override
    public void writeObject(final Object value) throws IOException {
        if (value == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec != null) {
                this._objectCodec.writeValue(this, value);
                return;
            }
            this._writeSimpleObject(value);
        }
    }
    
    @Override
    public void writeTree(final TreeNode rootNode) throws IOException {
        if (rootNode == null) {
            this.writeNull();
        }
        else {
            if (this._objectCodec == null) {
                throw new IllegalStateException("No ObjectCodec defined");
            }
            this._objectCodec.writeValue(this, rootNode);
        }
    }
    
    @Override
    public abstract void flush() throws IOException;
    
    @Override
    public void close() throws IOException {
        this._closed = true;
    }
    
    @Override
    public boolean isClosed() {
        return this._closed;
    }
    
    protected abstract void _releaseBuffers();
    
    protected abstract void _verifyValueWrite(final String p0) throws IOException;
    
    protected PrettyPrinter _constructDefaultPrettyPrinter() {
        return new DefaultPrettyPrinter();
    }
    
    protected String _asString(final BigDecimal value) throws IOException {
        if (Feature.WRITE_BIGDECIMAL_AS_PLAIN.enabledIn(this._features)) {
            final int scale = value.scale();
            if (scale < -9999 || scale > 9999) {
                this._reportError(String.format("Attempt to write plain `java.math.BigDecimal` (see JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN) with illegal scale (%d): needs to be between [-%d, %d]", scale, 9999, 9999));
            }
            return value.toPlainString();
        }
        return value.toString();
    }
    
    protected final int _decodeSurrogate(final int surr1, final int surr2) throws IOException {
        if (surr2 < 56320 || surr2 > 57343) {
            final String msg = "Incomplete surrogate pair: first char 0x" + Integer.toHexString(surr1) + ", second 0x" + Integer.toHexString(surr2);
            this._reportError(msg);
        }
        final int c = 65536 + (surr1 - 55296 << 10) + (surr2 - 56320);
        return c;
    }
    
    static {
        DERIVED_FEATURES_MASK = (Feature.WRITE_NUMBERS_AS_STRINGS.getMask() | Feature.ESCAPE_NON_ASCII.getMask() | Feature.STRICT_DUPLICATE_DETECTION.getMask());
    }
}
