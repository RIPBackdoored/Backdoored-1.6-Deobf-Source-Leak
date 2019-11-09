package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.*;
import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.*;

public class JsonGeneratorDelegate extends JsonGenerator
{
    protected JsonGenerator delegate;
    protected boolean delegateCopyMethods;
    
    public JsonGeneratorDelegate(final JsonGenerator d) {
        this(d, true);
    }
    
    public JsonGeneratorDelegate(final JsonGenerator d, final boolean delegateCopyMethods) {
        super();
        this.delegate = d;
        this.delegateCopyMethods = delegateCopyMethods;
    }
    
    @Override
    public Object getCurrentValue() {
        return this.delegate.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object v) {
        this.delegate.setCurrentValue(v);
    }
    
    public JsonGenerator getDelegate() {
        return this.delegate;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }
    
    @Override
    public JsonGenerator setCodec(final ObjectCodec oc) {
        this.delegate.setCodec(oc);
        return this;
    }
    
    @Override
    public void setSchema(final FormatSchema schema) {
        this.delegate.setSchema(schema);
    }
    
    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }
    
    @Override
    public Version version() {
        return this.delegate.version();
    }
    
    @Override
    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }
    
    @Override
    public int getOutputBuffered() {
        return this.delegate.getOutputBuffered();
    }
    
    @Override
    public boolean canUseSchema(final FormatSchema schema) {
        return this.delegate.canUseSchema(schema);
    }
    
    @Override
    public boolean canWriteTypeId() {
        return this.delegate.canWriteTypeId();
    }
    
    @Override
    public boolean canWriteObjectId() {
        return this.delegate.canWriteObjectId();
    }
    
    @Override
    public boolean canWriteBinaryNatively() {
        return this.delegate.canWriteBinaryNatively();
    }
    
    @Override
    public boolean canOmitFields() {
        return this.delegate.canOmitFields();
    }
    
    @Override
    public JsonGenerator enable(final Feature f) {
        this.delegate.enable(f);
        return this;
    }
    
    @Override
    public JsonGenerator disable(final Feature f) {
        this.delegate.disable(f);
        return this;
    }
    
    @Override
    public boolean isEnabled(final Feature f) {
        return this.delegate.isEnabled(f);
    }
    
    @Override
    public int getFeatureMask() {
        return this.delegate.getFeatureMask();
    }
    
    @Deprecated
    @Override
    public JsonGenerator setFeatureMask(final int mask) {
        this.delegate.setFeatureMask(mask);
        return this;
    }
    
    @Override
    public JsonGenerator overrideStdFeatures(final int values, final int mask) {
        this.delegate.overrideStdFeatures(values, mask);
        return this;
    }
    
    @Override
    public JsonGenerator overrideFormatFeatures(final int values, final int mask) {
        this.delegate.overrideFormatFeatures(values, mask);
        return this;
    }
    
    @Override
    public JsonGenerator setPrettyPrinter(final PrettyPrinter pp) {
        this.delegate.setPrettyPrinter(pp);
        return this;
    }
    
    @Override
    public PrettyPrinter getPrettyPrinter() {
        return this.delegate.getPrettyPrinter();
    }
    
    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }
    
    @Override
    public JsonGenerator setHighestNonEscapedChar(final int charCode) {
        this.delegate.setHighestNonEscapedChar(charCode);
        return this;
    }
    
    @Override
    public int getHighestEscapedChar() {
        return this.delegate.getHighestEscapedChar();
    }
    
    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this.delegate.getCharacterEscapes();
    }
    
    @Override
    public JsonGenerator setCharacterEscapes(final CharacterEscapes esc) {
        this.delegate.setCharacterEscapes(esc);
        return this;
    }
    
    @Override
    public JsonGenerator setRootValueSeparator(final SerializableString sep) {
        this.delegate.setRootValueSeparator(sep);
        return this;
    }
    
    @Override
    public void writeStartArray() throws IOException {
        this.delegate.writeStartArray();
    }
    
    @Override
    public void writeStartArray(final int size) throws IOException {
        this.delegate.writeStartArray(size);
    }
    
    @Override
    public void writeEndArray() throws IOException {
        this.delegate.writeEndArray();
    }
    
    @Override
    public void writeStartObject() throws IOException {
        this.delegate.writeStartObject();
    }
    
    @Override
    public void writeStartObject(final Object forValue) throws IOException {
        this.delegate.writeStartObject(forValue);
    }
    
    @Override
    public void writeEndObject() throws IOException {
        this.delegate.writeEndObject();
    }
    
    @Override
    public void writeFieldName(final String name) throws IOException {
        this.delegate.writeFieldName(name);
    }
    
    @Override
    public void writeFieldName(final SerializableString name) throws IOException {
        this.delegate.writeFieldName(name);
    }
    
    @Override
    public void writeFieldId(final long id) throws IOException {
        this.delegate.writeFieldId(id);
    }
    
    @Override
    public void writeArray(final int[] array, final int offset, final int length) throws IOException {
        this.delegate.writeArray(array, offset, length);
    }
    
    @Override
    public void writeArray(final long[] array, final int offset, final int length) throws IOException {
        this.delegate.writeArray(array, offset, length);
    }
    
    @Override
    public void writeArray(final double[] array, final int offset, final int length) throws IOException {
        this.delegate.writeArray(array, offset, length);
    }
    
    @Override
    public void writeString(final String text) throws IOException {
        this.delegate.writeString(text);
    }
    
    @Override
    public void writeString(final Reader reader, final int len) throws IOException {
        this.delegate.writeString(reader, len);
    }
    
    @Override
    public void writeString(final char[] text, final int offset, final int len) throws IOException {
        this.delegate.writeString(text, offset, len);
    }
    
    @Override
    public void writeString(final SerializableString text) throws IOException {
        this.delegate.writeString(text);
    }
    
    @Override
    public void writeRawUTF8String(final byte[] text, final int offset, final int length) throws IOException {
        this.delegate.writeRawUTF8String(text, offset, length);
    }
    
    @Override
    public void writeUTF8String(final byte[] text, final int offset, final int length) throws IOException {
        this.delegate.writeUTF8String(text, offset, length);
    }
    
    @Override
    public void writeRaw(final String text) throws IOException {
        this.delegate.writeRaw(text);
    }
    
    @Override
    public void writeRaw(final String text, final int offset, final int len) throws IOException {
        this.delegate.writeRaw(text, offset, len);
    }
    
    @Override
    public void writeRaw(final SerializableString raw) throws IOException {
        this.delegate.writeRaw(raw);
    }
    
    @Override
    public void writeRaw(final char[] text, final int offset, final int len) throws IOException {
        this.delegate.writeRaw(text, offset, len);
    }
    
    @Override
    public void writeRaw(final char c) throws IOException {
        this.delegate.writeRaw(c);
    }
    
    @Override
    public void writeRawValue(final String text) throws IOException {
        this.delegate.writeRawValue(text);
    }
    
    @Override
    public void writeRawValue(final String text, final int offset, final int len) throws IOException {
        this.delegate.writeRawValue(text, offset, len);
    }
    
    @Override
    public void writeRawValue(final char[] text, final int offset, final int len) throws IOException {
        this.delegate.writeRawValue(text, offset, len);
    }
    
    @Override
    public void writeBinary(final Base64Variant b64variant, final byte[] data, final int offset, final int len) throws IOException {
        this.delegate.writeBinary(b64variant, data, offset, len);
    }
    
    @Override
    public int writeBinary(final Base64Variant b64variant, final InputStream data, final int dataLength) throws IOException {
        return this.delegate.writeBinary(b64variant, data, dataLength);
    }
    
    @Override
    public void writeNumber(final short v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final int v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final long v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final BigInteger v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final double v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final float v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final BigDecimal v) throws IOException {
        this.delegate.writeNumber(v);
    }
    
    @Override
    public void writeNumber(final String encodedValue) throws IOException, UnsupportedOperationException {
        this.delegate.writeNumber(encodedValue);
    }
    
    @Override
    public void writeBoolean(final boolean state) throws IOException {
        this.delegate.writeBoolean(state);
    }
    
    @Override
    public void writeNull() throws IOException {
        this.delegate.writeNull();
    }
    
    @Override
    public void writeOmittedField(final String fieldName) throws IOException {
        this.delegate.writeOmittedField(fieldName);
    }
    
    @Override
    public void writeObjectId(final Object id) throws IOException {
        this.delegate.writeObjectId(id);
    }
    
    @Override
    public void writeObjectRef(final Object id) throws IOException {
        this.delegate.writeObjectRef(id);
    }
    
    @Override
    public void writeTypeId(final Object id) throws IOException {
        this.delegate.writeTypeId(id);
    }
    
    @Override
    public void writeEmbeddedObject(final Object object) throws IOException {
        this.delegate.writeEmbeddedObject(object);
    }
    
    @Override
    public void writeObject(final Object pojo) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeObject(pojo);
            return;
        }
        if (pojo == null) {
            this.writeNull();
        }
        else {
            final ObjectCodec c = this.getCodec();
            if (c != null) {
                c.writeValue(this, pojo);
                return;
            }
            this._writeSimpleObject(pojo);
        }
    }
    
    @Override
    public void writeTree(final TreeNode tree) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeTree(tree);
            return;
        }
        if (tree == null) {
            this.writeNull();
        }
        else {
            final ObjectCodec c = this.getCodec();
            if (c == null) {
                throw new IllegalStateException("No ObjectCodec defined");
            }
            c.writeTree(this, tree);
        }
    }
    
    @Override
    public void copyCurrentEvent(final JsonParser p) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentEvent(p);
        }
        else {
            super.copyCurrentEvent(p);
        }
    }
    
    @Override
    public void copyCurrentStructure(final JsonParser p) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentStructure(p);
        }
        else {
            super.copyCurrentStructure(p);
        }
    }
    
    @Override
    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }
    
    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.delegate.close();
    }
    
    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }
}
