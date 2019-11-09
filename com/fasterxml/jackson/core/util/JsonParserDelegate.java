package com.fasterxml.jackson.core.util;

import java.math.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class JsonParserDelegate extends JsonParser
{
    protected JsonParser delegate;
    
    public JsonParserDelegate(final JsonParser d) {
        super();
        this.delegate = d;
    }
    
    @Override
    public Object getCurrentValue() {
        return this.delegate.getCurrentValue();
    }
    
    @Override
    public void setCurrentValue(final Object v) {
        this.delegate.setCurrentValue(v);
    }
    
    @Override
    public void setCodec(final ObjectCodec c) {
        this.delegate.setCodec(c);
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }
    
    @Override
    public JsonParser enable(final Feature f) {
        this.delegate.enable(f);
        return this;
    }
    
    @Override
    public JsonParser disable(final Feature f) {
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
    public JsonParser setFeatureMask(final int mask) {
        this.delegate.setFeatureMask(mask);
        return this;
    }
    
    @Override
    public JsonParser overrideStdFeatures(final int values, final int mask) {
        this.delegate.overrideStdFeatures(values, mask);
        return this;
    }
    
    @Override
    public JsonParser overrideFormatFeatures(final int values, final int mask) {
        this.delegate.overrideFormatFeatures(values, mask);
        return this;
    }
    
    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }
    
    @Override
    public void setSchema(final FormatSchema schema) {
        this.delegate.setSchema(schema);
    }
    
    @Override
    public boolean canUseSchema(final FormatSchema schema) {
        return this.delegate.canUseSchema(schema);
    }
    
    @Override
    public Version version() {
        return this.delegate.version();
    }
    
    @Override
    public Object getInputSource() {
        return this.delegate.getInputSource();
    }
    
    @Override
    public boolean requiresCustomCodec() {
        return this.delegate.requiresCustomCodec();
    }
    
    @Override
    public void close() throws IOException {
        this.delegate.close();
    }
    
    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }
    
    @Override
    public JsonToken currentToken() {
        return this.delegate.currentToken();
    }
    
    @Override
    public int currentTokenId() {
        return this.delegate.currentTokenId();
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this.delegate.getCurrentToken();
    }
    
    @Override
    public int getCurrentTokenId() {
        return this.delegate.getCurrentTokenId();
    }
    
    @Override
    public boolean hasCurrentToken() {
        return this.delegate.hasCurrentToken();
    }
    
    @Override
    public boolean hasTokenId(final int id) {
        return this.delegate.hasTokenId(id);
    }
    
    @Override
    public boolean hasToken(final JsonToken t) {
        return this.delegate.hasToken(t);
    }
    
    @Override
    public String getCurrentName() throws IOException {
        return this.delegate.getCurrentName();
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }
    
    @Override
    public JsonStreamContext getParsingContext() {
        return this.delegate.getParsingContext();
    }
    
    @Override
    public boolean isExpectedStartArrayToken() {
        return this.delegate.isExpectedStartArrayToken();
    }
    
    @Override
    public boolean isExpectedStartObjectToken() {
        return this.delegate.isExpectedStartObjectToken();
    }
    
    @Override
    public boolean isNaN() throws IOException {
        return this.delegate.isNaN();
    }
    
    @Override
    public void clearCurrentToken() {
        this.delegate.clearCurrentToken();
    }
    
    @Override
    public JsonToken getLastClearedToken() {
        return this.delegate.getLastClearedToken();
    }
    
    @Override
    public void overrideCurrentName(final String name) {
        this.delegate.overrideCurrentName(name);
    }
    
    @Override
    public String getText() throws IOException {
        return this.delegate.getText();
    }
    
    @Override
    public boolean hasTextCharacters() {
        return this.delegate.hasTextCharacters();
    }
    
    @Override
    public char[] getTextCharacters() throws IOException {
        return this.delegate.getTextCharacters();
    }
    
    @Override
    public int getTextLength() throws IOException {
        return this.delegate.getTextLength();
    }
    
    @Override
    public int getTextOffset() throws IOException {
        return this.delegate.getTextOffset();
    }
    
    @Override
    public int getText(final Writer writer) throws IOException, UnsupportedOperationException {
        return this.delegate.getText(writer);
    }
    
    @Override
    public BigInteger getBigIntegerValue() throws IOException {
        return this.delegate.getBigIntegerValue();
    }
    
    @Override
    public boolean getBooleanValue() throws IOException {
        return this.delegate.getBooleanValue();
    }
    
    @Override
    public byte getByteValue() throws IOException {
        return this.delegate.getByteValue();
    }
    
    @Override
    public short getShortValue() throws IOException {
        return this.delegate.getShortValue();
    }
    
    @Override
    public BigDecimal getDecimalValue() throws IOException {
        return this.delegate.getDecimalValue();
    }
    
    @Override
    public double getDoubleValue() throws IOException {
        return this.delegate.getDoubleValue();
    }
    
    @Override
    public float getFloatValue() throws IOException {
        return this.delegate.getFloatValue();
    }
    
    @Override
    public int getIntValue() throws IOException {
        return this.delegate.getIntValue();
    }
    
    @Override
    public long getLongValue() throws IOException {
        return this.delegate.getLongValue();
    }
    
    @Override
    public NumberType getNumberType() throws IOException {
        return this.delegate.getNumberType();
    }
    
    @Override
    public Number getNumberValue() throws IOException {
        return this.delegate.getNumberValue();
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        return this.delegate.getValueAsInt();
    }
    
    @Override
    public int getValueAsInt(final int defaultValue) throws IOException {
        return this.delegate.getValueAsInt(defaultValue);
    }
    
    @Override
    public long getValueAsLong() throws IOException {
        return this.delegate.getValueAsLong();
    }
    
    @Override
    public long getValueAsLong(final long defaultValue) throws IOException {
        return this.delegate.getValueAsLong(defaultValue);
    }
    
    @Override
    public double getValueAsDouble() throws IOException {
        return this.delegate.getValueAsDouble();
    }
    
    @Override
    public double getValueAsDouble(final double defaultValue) throws IOException {
        return this.delegate.getValueAsDouble(defaultValue);
    }
    
    @Override
    public boolean getValueAsBoolean() throws IOException {
        return this.delegate.getValueAsBoolean();
    }
    
    @Override
    public boolean getValueAsBoolean(final boolean defaultValue) throws IOException {
        return this.delegate.getValueAsBoolean(defaultValue);
    }
    
    @Override
    public String getValueAsString() throws IOException {
        return this.delegate.getValueAsString();
    }
    
    @Override
    public String getValueAsString(final String defaultValue) throws IOException {
        return this.delegate.getValueAsString(defaultValue);
    }
    
    @Override
    public Object getEmbeddedObject() throws IOException {
        return this.delegate.getEmbeddedObject();
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant b64variant) throws IOException {
        return this.delegate.getBinaryValue(b64variant);
    }
    
    @Override
    public int readBinaryValue(final Base64Variant b64variant, final OutputStream out) throws IOException {
        return this.delegate.readBinaryValue(b64variant, out);
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        return this.delegate.nextToken();
    }
    
    @Override
    public JsonToken nextValue() throws IOException {
        return this.delegate.nextValue();
    }
    
    @Override
    public void finishToken() throws IOException {
        this.delegate.finishToken();
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        this.delegate.skipChildren();
        return this;
    }
    
    @Override
    public boolean canReadObjectId() {
        return this.delegate.canReadObjectId();
    }
    
    @Override
    public boolean canReadTypeId() {
        return this.delegate.canReadTypeId();
    }
    
    @Override
    public Object getObjectId() throws IOException {
        return this.delegate.getObjectId();
    }
    
    @Override
    public Object getTypeId() throws IOException {
        return this.delegate.getTypeId();
    }
}
