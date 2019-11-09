package com.fasterxml.jackson.core.json;

import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.*;

public class UTF8JsonGenerator extends JsonGeneratorImpl
{
    private static final byte BYTE_u = 117;
    private static final byte BYTE_0 = 48;
    private static final byte BYTE_LBRACKET = 91;
    private static final byte BYTE_RBRACKET = 93;
    private static final byte BYTE_LCURLY = 123;
    private static final byte BYTE_RCURLY = 125;
    private static final byte BYTE_BACKSLASH = 92;
    private static final byte BYTE_COMMA = 44;
    private static final byte BYTE_COLON = 58;
    private static final int MAX_BYTES_TO_BUFFER = 512;
    private static final byte[] HEX_CHARS;
    private static final byte[] NULL_BYTES;
    private static final byte[] TRUE_BYTES;
    private static final byte[] FALSE_BYTES;
    protected final OutputStream _outputStream;
    protected byte _quoteChar;
    protected byte[] _outputBuffer;
    protected int _outputTail;
    protected final int _outputEnd;
    protected final int _outputMaxContiguous;
    protected char[] _charBuffer;
    protected final int _charBufferLength;
    protected byte[] _entityBuffer;
    protected boolean _bufferRecyclable;
    
    public UTF8JsonGenerator(final IOContext ctxt, final int features, final ObjectCodec codec, final OutputStream out) {
        super(ctxt, features, codec);
        this._quoteChar = 34;
        this._outputStream = out;
        this._bufferRecyclable = true;
        this._outputBuffer = ctxt.allocWriteEncodingBuffer();
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = ctxt.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
        if (this.isEnabled(Feature.ESCAPE_NON_ASCII)) {
            this.setHighestNonEscapedChar(127);
        }
    }
    
    public UTF8JsonGenerator(final IOContext ctxt, final int features, final ObjectCodec codec, final OutputStream out, final byte[] outputBuffer, final int outputOffset, final boolean bufferRecyclable) {
        super(ctxt, features, codec);
        this._quoteChar = 34;
        this._outputStream = out;
        this._bufferRecyclable = bufferRecyclable;
        this._outputTail = outputOffset;
        this._outputBuffer = outputBuffer;
        this._outputEnd = this._outputBuffer.length;
        this._outputMaxContiguous = this._outputEnd >> 3;
        this._charBuffer = ctxt.allocConcatBuffer();
        this._charBufferLength = this._charBuffer.length;
    }
    
    @Override
    public Object getOutputTarget() {
        return this._outputStream;
    }
    
    @Override
    public int getOutputBuffered() {
        return this._outputTail;
    }
    
    @Override
    public void writeFieldName(final String name) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(name);
            return;
        }
        final int status = this._writeContext.writeFieldName(name);
        if (status == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (status == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 44;
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments(name, false);
            return;
        }
        final int len = name.length();
        if (len > this._charBufferLength) {
            this._writeStringSegments(name, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (len <= this._outputMaxContiguous) {
            if (this._outputTail + len > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(name, 0, len);
        }
        else {
            this._writeStringSegments(name, 0, len);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeFieldName(final SerializableString name) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(name);
            return;
        }
        final int status = this._writeContext.writeFieldName(name.getValue());
        if (status == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (status == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 44;
        }
        if (this._cfgUnqNames) {
            this._writeUnq(name);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final int len = name.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (len < 0) {
            this._writeBytes(name.asQuotedUTF8());
        }
        else {
            this._outputTail += len;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    private final void _writeUnq(final SerializableString name) throws IOException {
        final int len = name.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (len < 0) {
            this._writeBytes(name.asQuotedUTF8());
        }
        else {
            this._outputTail += len;
        }
    }
    
    @Override
    public final void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 91;
        }
    }
    
    @Override
    public final void writeEndArray() throws IOException {
        if (!this._writeContext.inArray()) {
            this._reportError("Current context not Array but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 93;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }
    
    @Override
    public final void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 123;
        }
    }
    
    @Override
    public void writeStartObject(final Object forValue) throws IOException {
        this._verifyValueWrite("start an object");
        final JsonWriteContext ctxt = this._writeContext.createChildObjectContext();
        this._writeContext = ctxt;
        if (forValue != null) {
            ctxt.setCurrentValue(forValue);
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 123;
        }
    }
    
    @Override
    public final void writeEndObject() throws IOException {
        if (!this._writeContext.inObject()) {
            this._reportError("Current context not Object but " + this._writeContext.typeDesc());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = 125;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }
    
    protected final void _writePPFieldName(final String name) throws IOException {
        final int status = this._writeContext.writeFieldName(name);
        if (status == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (status == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments(name, false);
            return;
        }
        final int len = name.length();
        if (len > this._charBufferLength) {
            this._writeStringSegments(name, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        name.getChars(0, len, this._charBuffer, 0);
        if (len <= this._outputMaxContiguous) {
            if (this._outputTail + len > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(this._charBuffer, 0, len);
        }
        else {
            this._writeStringSegments(this._charBuffer, 0, len);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    protected final void _writePPFieldName(final SerializableString name) throws IOException {
        final int status = this._writeContext.writeFieldName(name.getValue());
        if (status == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (status == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        final boolean addQuotes = !this._cfgUnqNames;
        if (addQuotes) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
        this._writeBytes(name.asQuotedUTF8());
        if (addQuotes) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    @Override
    public void writeString(final String text) throws IOException {
        this._verifyValueWrite("write a string");
        if (text == null) {
            this._writeNull();
            return;
        }
        final int len = text.length();
        if (len > this._outputMaxContiguous) {
            this._writeStringSegments(text, true);
            return;
        }
        if (this._outputTail + len >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeStringSegment(text, 0, len);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeString(final Reader reader, final int len) throws IOException {
        this._verifyValueWrite("write a string");
        if (reader == null) {
            this._reportError("null reader");
        }
        int toRead = (len >= 0) ? len : Integer.MAX_VALUE;
        final char[] buf = this._charBuffer;
        if (this._outputTail + len >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        while (toRead > 0) {
            final int toReadNow = Math.min(toRead, buf.length);
            final int numRead = reader.read(buf, 0, toReadNow);
            if (numRead <= 0) {
                break;
            }
            if (this._outputTail + len >= this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegments(buf, 0, numRead);
            toRead -= numRead;
        }
        if (this._outputTail + len >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (toRead > 0 && len >= 0) {
            this._reportError("Didn't read enough from reader");
        }
    }
    
    @Override
    public void writeString(final char[] text, final int offset, final int len) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (len <= this._outputMaxContiguous) {
            if (this._outputTail + len > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(text, offset, len);
        }
        else {
            this._writeStringSegments(text, offset, len);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public final void writeString(final SerializableString text) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final int len = text.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (len < 0) {
            this._writeBytes(text.asQuotedUTF8());
        }
        else {
            this._outputTail += len;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeRawUTF8String(final byte[] text, final int offset, final int length) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeBytes(text, offset, length);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeUTF8String(final byte[] text, final int offset, final int len) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        if (len <= this._outputMaxContiguous) {
            this._writeUTF8Segment(text, offset, len);
        }
        else {
            this._writeUTF8Segments(text, offset, len);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeRaw(final String text) throws IOException {
        final int len = text.length();
        final char[] buf = this._charBuffer;
        if (len <= buf.length) {
            text.getChars(0, len, buf, 0);
            this.writeRaw(buf, 0, len);
        }
        else {
            this.writeRaw(text, 0, len);
        }
    }
    
    @Override
    public void writeRaw(final String text, int offset, int len) throws IOException {
        final char[] buf = this._charBuffer;
        final int cbufLen = buf.length;
        if (len <= cbufLen) {
            text.getChars(offset, offset + len, buf, 0);
            this.writeRaw(buf, 0, len);
            return;
        }
        final int maxChunk = Math.min(cbufLen, (this._outputEnd >> 2) + (this._outputEnd >> 4));
        final int maxBytes = maxChunk * 3;
        while (len > 0) {
            int len2 = Math.min(maxChunk, len);
            text.getChars(offset, offset + len2, buf, 0);
            if (this._outputTail + maxBytes > this._outputEnd) {
                this._flushBuffer();
            }
            if (len2 > 1) {
                final char ch = buf[len2 - 1];
                if (ch >= '\ud800' && ch <= '\udbff') {
                    --len2;
                }
            }
            this._writeRawSegment(buf, 0, len2);
            offset += len2;
            len -= len2;
        }
    }
    
    @Override
    public void writeRaw(final SerializableString text) throws IOException {
        final byte[] raw = text.asUnquotedUTF8();
        if (raw.length > 0) {
            this._writeBytes(raw);
        }
    }
    
    @Override
    public void writeRawValue(final SerializableString text) throws IOException {
        this._verifyValueWrite("write a raw (unencoded) value");
        final byte[] raw = text.asUnquotedUTF8();
        if (raw.length > 0) {
            this._writeBytes(raw);
        }
    }
    
    @Override
    public final void writeRaw(final char[] cbuf, int offset, int len) throws IOException {
        final int len2 = len + len + len;
        if (this._outputTail + len2 > this._outputEnd) {
            if (this._outputEnd < len2) {
                this._writeSegmentedRaw(cbuf, offset, len);
                return;
            }
            this._flushBuffer();
        }
        len += offset;
    Label_0183:
        while (offset < len) {
            while (true) {
                final int ch = cbuf[offset];
                if (ch > 127) {
                    final char ch2 = cbuf[offset++];
                    if (ch2 < '\u0800') {
                        this._outputBuffer[this._outputTail++] = (byte)(0xC0 | ch2 >> 6);
                        this._outputBuffer[this._outputTail++] = (byte)(0x80 | (ch2 & '?'));
                    }
                    else {
                        offset = this._outputRawMultiByteChar(ch2, cbuf, offset, len);
                    }
                    break;
                }
                this._outputBuffer[this._outputTail++] = (byte)ch;
                if (++offset >= len) {
                    break Label_0183;
                }
            }
        }
    }
    
    @Override
    public void writeRaw(final char ch) throws IOException {
        if (this._outputTail + 3 >= this._outputEnd) {
            this._flushBuffer();
        }
        final byte[] bbuf = this._outputBuffer;
        if (ch <= '\u007f') {
            bbuf[this._outputTail++] = (byte)ch;
        }
        else if (ch < '\u0800') {
            bbuf[this._outputTail++] = (byte)(0xC0 | ch >> 6);
            bbuf[this._outputTail++] = (byte)(0x80 | (ch & '?'));
        }
        else {
            this._outputRawMultiByteChar(ch, null, 0, 0);
        }
    }
    
    private final void _writeSegmentedRaw(final char[] cbuf, int offset, final int len) throws IOException {
        final int end = this._outputEnd;
        final byte[] bbuf = this._outputBuffer;
        final int inputEnd = offset + len;
    Label_0182:
        while (offset < inputEnd) {
            while (true) {
                final int ch = cbuf[offset];
                if (ch >= 128) {
                    if (this._outputTail + 3 >= this._outputEnd) {
                        this._flushBuffer();
                    }
                    final char ch2 = cbuf[offset++];
                    if (ch2 < '\u0800') {
                        bbuf[this._outputTail++] = (byte)(0xC0 | ch2 >> 6);
                        bbuf[this._outputTail++] = (byte)(0x80 | (ch2 & '?'));
                    }
                    else {
                        offset = this._outputRawMultiByteChar(ch2, cbuf, offset, inputEnd);
                    }
                    break;
                }
                if (this._outputTail >= end) {
                    this._flushBuffer();
                }
                bbuf[this._outputTail++] = (byte)ch;
                if (++offset >= inputEnd) {
                    break Label_0182;
                }
            }
        }
    }
    
    private void _writeRawSegment(final char[] cbuf, int offset, final int end) throws IOException {
    Label_0137:
        while (offset < end) {
            while (true) {
                final int ch = cbuf[offset];
                if (ch > 127) {
                    final char ch2 = cbuf[offset++];
                    if (ch2 < '\u0800') {
                        this._outputBuffer[this._outputTail++] = (byte)(0xC0 | ch2 >> 6);
                        this._outputBuffer[this._outputTail++] = (byte)(0x80 | (ch2 & '?'));
                    }
                    else {
                        offset = this._outputRawMultiByteChar(ch2, cbuf, offset, end);
                    }
                    break;
                }
                this._outputBuffer[this._outputTail++] = (byte)ch;
                if (++offset >= end) {
                    break Label_0137;
                }
            }
        }
    }
    
    @Override
    public void writeBinary(final Base64Variant b64variant, final byte[] data, final int offset, final int len) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeBinary(b64variant, data, offset, offset + len);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public int writeBinary(final Base64Variant b64variant, final InputStream data, final int dataLength) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final byte[] encodingBuffer = this._ioContext.allocBase64Buffer();
        int bytes;
        try {
            if (dataLength < 0) {
                bytes = this._writeBinary(b64variant, data, encodingBuffer);
            }
            else {
                final int missing = this._writeBinary(b64variant, data, encodingBuffer, dataLength);
                if (missing > 0) {
                    this._reportError("Too few bytes available: missing " + missing + " bytes (out of " + dataLength + ")");
                }
                bytes = dataLength;
            }
        }
        finally {
            this._ioContext.releaseBase64Buffer(encodingBuffer);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        return bytes;
    }
    
    @Override
    public void writeNumber(final short s) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(s);
            return;
        }
        this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
    }
    
    private final void _writeQuotedShort(final short s) throws IOException {
        if (this._outputTail + 8 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeNumber(final int i) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(i);
            return;
        }
        this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
    }
    
    private final void _writeQuotedInt(final int i) throws IOException {
        if (this._outputTail + 13 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeNumber(final long l) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedLong(l);
            return;
        }
        if (this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
    }
    
    private final void _writeQuotedLong(final long l) throws IOException {
        if (this._outputTail + 23 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeNumber(final BigInteger value) throws IOException {
        this._verifyValueWrite("write a number");
        if (value == null) {
            this._writeNull();
        }
        else if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(value.toString());
        }
        else {
            this.writeRaw(value.toString());
        }
    }
    
    @Override
    public void writeNumber(final double d) throws IOException {
        if (this._cfgNumbersAsStrings || ((Double.isNaN(d) || Double.isInfinite(d)) && Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            this.writeString(String.valueOf(d));
            return;
        }
        this._verifyValueWrite("write a number");
        this.writeRaw(String.valueOf(d));
    }
    
    @Override
    public void writeNumber(final float f) throws IOException {
        if (this._cfgNumbersAsStrings || ((Float.isNaN(f) || Float.isInfinite(f)) && Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            this.writeString(String.valueOf(f));
            return;
        }
        this._verifyValueWrite("write a number");
        this.writeRaw(String.valueOf(f));
    }
    
    @Override
    public void writeNumber(final BigDecimal value) throws IOException {
        this._verifyValueWrite("write a number");
        if (value == null) {
            this._writeNull();
        }
        else if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(this._asString(value));
        }
        else {
            this.writeRaw(this._asString(value));
        }
    }
    
    @Override
    public void writeNumber(final String encodedValue) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(encodedValue);
        }
        else {
            this.writeRaw(encodedValue);
        }
    }
    
    private final void _writeQuotedRaw(final String value) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this.writeRaw(value);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeBoolean(final boolean state) throws IOException {
        this._verifyValueWrite("write a boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
        }
        final byte[] keyword = state ? UTF8JsonGenerator.TRUE_BYTES : UTF8JsonGenerator.FALSE_BYTES;
        final int len = keyword.length;
        System.arraycopy(keyword, 0, this._outputBuffer, this._outputTail, len);
        this._outputTail += len;
    }
    
    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write a null");
        this._writeNull();
    }
    
    @Override
    protected final void _verifyValueWrite(final String typeMsg) throws IOException {
        final int status = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite(typeMsg, status);
            return;
        }
        byte b = 0;
        switch (status) {
            default: {
                return;
            }
            case 1: {
                b = 44;
                break;
            }
            case 2: {
                b = 58;
                break;
            }
            case 3: {
                if (this._rootValueSeparator != null) {
                    final byte[] raw = this._rootValueSeparator.asUnquotedUTF8();
                    if (raw.length > 0) {
                        this._writeBytes(raw);
                    }
                }
                return;
            }
            case 5: {
                this._reportCantWriteValueExpectName(typeMsg);
                return;
            }
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = b;
    }
    
    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._outputStream != null && this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
            this._outputStream.flush();
        }
    }
    
    @Override
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && this.isEnabled(Feature.AUTO_CLOSE_JSON_CONTENT)) {
            while (true) {
                final JsonStreamContext ctxt = this.getOutputContext();
                if (ctxt.inArray()) {
                    this.writeEndArray();
                }
                else {
                    if (!ctxt.inObject()) {
                        break;
                    }
                    this.writeEndObject();
                }
            }
        }
        this._flushBuffer();
        this._outputTail = 0;
        if (this._outputStream != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_TARGET)) {
                this._outputStream.close();
            }
            else if (this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
                this._outputStream.flush();
            }
        }
        this._releaseBuffers();
    }
    
    @Override
    protected void _releaseBuffers() {
        final byte[] buf = this._outputBuffer;
        if (buf != null && this._bufferRecyclable) {
            this._outputBuffer = null;
            this._ioContext.releaseWriteEncodingBuffer(buf);
        }
        final char[] cbuf = this._charBuffer;
        if (cbuf != null) {
            this._charBuffer = null;
            this._ioContext.releaseConcatBuffer(cbuf);
        }
    }
    
    private final void _writeBytes(final byte[] bytes) throws IOException {
        final int len = bytes.length;
        if (this._outputTail + len > this._outputEnd) {
            this._flushBuffer();
            if (len > 512) {
                this._outputStream.write(bytes, 0, len);
                return;
            }
        }
        System.arraycopy(bytes, 0, this._outputBuffer, this._outputTail, len);
        this._outputTail += len;
    }
    
    private final void _writeBytes(final byte[] bytes, final int offset, final int len) throws IOException {
        if (this._outputTail + len > this._outputEnd) {
            this._flushBuffer();
            if (len > 512) {
                this._outputStream.write(bytes, offset, len);
                return;
            }
        }
        System.arraycopy(bytes, offset, this._outputBuffer, this._outputTail, len);
        this._outputTail += len;
    }
    
    private final void _writeStringSegments(final String text, final boolean addQuotes) throws IOException {
        if (addQuotes) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
        int left = text.length();
        int offset = 0;
        while (left > 0) {
            final int len = Math.min(this._outputMaxContiguous, left);
            if (this._outputTail + len > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(text, offset, len);
            offset += len;
            left -= len;
        }
        if (addQuotes) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    private final void _writeStringSegments(final char[] cbuf, int offset, int totalLen) throws IOException {
        do {
            final int len = Math.min(this._outputMaxContiguous, totalLen);
            if (this._outputTail + len > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(cbuf, offset, len);
            offset += len;
            totalLen -= len;
        } while (totalLen > 0);
    }
    
    private final void _writeStringSegments(final String text, int offset, int totalLen) throws IOException {
        do {
            final int len = Math.min(this._outputMaxContiguous, totalLen);
            if (this._outputTail + len > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(text, offset, len);
            offset += len;
            totalLen -= len;
        } while (totalLen > 0);
    }
    
    private final void _writeStringSegment(final char[] cbuf, int offset, int len) throws IOException {
        len += offset;
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        while (offset < len) {
            final int ch = cbuf[offset];
            if (ch > 127) {
                break;
            }
            if (escCodes[ch] != 0) {
                break;
            }
            outputBuffer[outputPtr++] = (byte)ch;
            ++offset;
        }
        this._outputTail = outputPtr;
        if (offset < len) {
            if (this._characterEscapes != null) {
                this._writeCustomStringSegment2(cbuf, offset, len);
            }
            else if (this._maximumNonEscapedChar == 0) {
                this._writeStringSegment2(cbuf, offset, len);
            }
            else {
                this._writeStringSegmentASCII2(cbuf, offset, len);
            }
        }
    }
    
    private final void _writeStringSegment(final String text, int offset, int len) throws IOException {
        len += offset;
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        while (offset < len) {
            final int ch = text.charAt(offset);
            if (ch > 127) {
                break;
            }
            if (escCodes[ch] != 0) {
                break;
            }
            outputBuffer[outputPtr++] = (byte)ch;
            ++offset;
        }
        this._outputTail = outputPtr;
        if (offset < len) {
            if (this._characterEscapes != null) {
                this._writeCustomStringSegment2(text, offset, len);
            }
            else if (this._maximumNonEscapedChar == 0) {
                this._writeStringSegment2(text, offset, len);
            }
            else {
                this._writeStringSegmentASCII2(text, offset, len);
            }
        }
    }
    
    private final void _writeStringSegment2(final char[] cbuf, int offset, final int end) throws IOException {
        if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
            this._flushBuffer();
        }
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        while (offset < end) {
            final int ch = cbuf[offset++];
            if (ch <= 127) {
                if (escCodes[ch] == 0) {
                    outputBuffer[outputPtr++] = (byte)ch;
                }
                else {
                    final int escape = escCodes[ch];
                    if (escape > 0) {
                        outputBuffer[outputPtr++] = 92;
                        outputBuffer[outputPtr++] = (byte)escape;
                    }
                    else {
                        outputPtr = this._writeGenericEscape(ch, outputPtr);
                    }
                }
            }
            else if (ch <= 2047) {
                outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                outputBuffer[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
            }
            else {
                outputPtr = this._outputMultiByteChar(ch, outputPtr);
            }
        }
        this._outputTail = outputPtr;
    }
    
    private final void _writeStringSegment2(final String text, int offset, final int end) throws IOException {
        if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
            this._flushBuffer();
        }
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        while (offset < end) {
            final int ch = text.charAt(offset++);
            if (ch <= 127) {
                if (escCodes[ch] == 0) {
                    outputBuffer[outputPtr++] = (byte)ch;
                }
                else {
                    final int escape = escCodes[ch];
                    if (escape > 0) {
                        outputBuffer[outputPtr++] = 92;
                        outputBuffer[outputPtr++] = (byte)escape;
                    }
                    else {
                        outputPtr = this._writeGenericEscape(ch, outputPtr);
                    }
                }
            }
            else if (ch <= 2047) {
                outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                outputBuffer[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
            }
            else {
                outputPtr = this._outputMultiByteChar(ch, outputPtr);
            }
        }
        this._outputTail = outputPtr;
    }
    
    private final void _writeStringSegmentASCII2(final char[] cbuf, int offset, final int end) throws IOException {
        if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
            this._flushBuffer();
        }
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        final int maxUnescaped = this._maximumNonEscapedChar;
        while (offset < end) {
            final int ch = cbuf[offset++];
            if (ch <= 127) {
                if (escCodes[ch] == 0) {
                    outputBuffer[outputPtr++] = (byte)ch;
                }
                else {
                    final int escape = escCodes[ch];
                    if (escape > 0) {
                        outputBuffer[outputPtr++] = 92;
                        outputBuffer[outputPtr++] = (byte)escape;
                    }
                    else {
                        outputPtr = this._writeGenericEscape(ch, outputPtr);
                    }
                }
            }
            else if (ch > maxUnescaped) {
                outputPtr = this._writeGenericEscape(ch, outputPtr);
            }
            else if (ch <= 2047) {
                outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                outputBuffer[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
            }
            else {
                outputPtr = this._outputMultiByteChar(ch, outputPtr);
            }
        }
        this._outputTail = outputPtr;
    }
    
    private final void _writeStringSegmentASCII2(final String text, int offset, final int end) throws IOException {
        if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
            this._flushBuffer();
        }
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        final int maxUnescaped = this._maximumNonEscapedChar;
        while (offset < end) {
            final int ch = text.charAt(offset++);
            if (ch <= 127) {
                if (escCodes[ch] == 0) {
                    outputBuffer[outputPtr++] = (byte)ch;
                }
                else {
                    final int escape = escCodes[ch];
                    if (escape > 0) {
                        outputBuffer[outputPtr++] = 92;
                        outputBuffer[outputPtr++] = (byte)escape;
                    }
                    else {
                        outputPtr = this._writeGenericEscape(ch, outputPtr);
                    }
                }
            }
            else if (ch > maxUnescaped) {
                outputPtr = this._writeGenericEscape(ch, outputPtr);
            }
            else if (ch <= 2047) {
                outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                outputBuffer[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
            }
            else {
                outputPtr = this._outputMultiByteChar(ch, outputPtr);
            }
        }
        this._outputTail = outputPtr;
    }
    
    private final void _writeCustomStringSegment2(final char[] cbuf, int offset, final int end) throws IOException {
        if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
            this._flushBuffer();
        }
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        final int maxUnescaped = (this._maximumNonEscapedChar <= 0) ? 65535 : this._maximumNonEscapedChar;
        final CharacterEscapes customEscapes = this._characterEscapes;
        while (offset < end) {
            final int ch = cbuf[offset++];
            if (ch <= 127) {
                if (escCodes[ch] == 0) {
                    outputBuffer[outputPtr++] = (byte)ch;
                }
                else {
                    final int escape = escCodes[ch];
                    if (escape > 0) {
                        outputBuffer[outputPtr++] = 92;
                        outputBuffer[outputPtr++] = (byte)escape;
                    }
                    else if (escape == -2) {
                        final SerializableString esc = customEscapes.getEscapeSequence(ch);
                        if (esc == null) {
                            this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(ch) + ", although was supposed to have one");
                        }
                        outputPtr = this._writeCustomEscape(outputBuffer, outputPtr, esc, end - offset);
                    }
                    else {
                        outputPtr = this._writeGenericEscape(ch, outputPtr);
                    }
                }
            }
            else if (ch > maxUnescaped) {
                outputPtr = this._writeGenericEscape(ch, outputPtr);
            }
            else {
                final SerializableString esc2 = customEscapes.getEscapeSequence(ch);
                if (esc2 != null) {
                    outputPtr = this._writeCustomEscape(outputBuffer, outputPtr, esc2, end - offset);
                }
                else if (ch <= 2047) {
                    outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                    outputBuffer[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
                }
                else {
                    outputPtr = this._outputMultiByteChar(ch, outputPtr);
                }
            }
        }
        this._outputTail = outputPtr;
    }
    
    private final void _writeCustomStringSegment2(final String text, int offset, final int end) throws IOException {
        if (this._outputTail + 6 * (end - offset) > this._outputEnd) {
            this._flushBuffer();
        }
        int outputPtr = this._outputTail;
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        final int maxUnescaped = (this._maximumNonEscapedChar <= 0) ? 65535 : this._maximumNonEscapedChar;
        final CharacterEscapes customEscapes = this._characterEscapes;
        while (offset < end) {
            final int ch = text.charAt(offset++);
            if (ch <= 127) {
                if (escCodes[ch] == 0) {
                    outputBuffer[outputPtr++] = (byte)ch;
                }
                else {
                    final int escape = escCodes[ch];
                    if (escape > 0) {
                        outputBuffer[outputPtr++] = 92;
                        outputBuffer[outputPtr++] = (byte)escape;
                    }
                    else if (escape == -2) {
                        final SerializableString esc = customEscapes.getEscapeSequence(ch);
                        if (esc == null) {
                            this._reportError("Invalid custom escape definitions; custom escape not found for character code 0x" + Integer.toHexString(ch) + ", although was supposed to have one");
                        }
                        outputPtr = this._writeCustomEscape(outputBuffer, outputPtr, esc, end - offset);
                    }
                    else {
                        outputPtr = this._writeGenericEscape(ch, outputPtr);
                    }
                }
            }
            else if (ch > maxUnescaped) {
                outputPtr = this._writeGenericEscape(ch, outputPtr);
            }
            else {
                final SerializableString esc2 = customEscapes.getEscapeSequence(ch);
                if (esc2 != null) {
                    outputPtr = this._writeCustomEscape(outputBuffer, outputPtr, esc2, end - offset);
                }
                else if (ch <= 2047) {
                    outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                    outputBuffer[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
                }
                else {
                    outputPtr = this._outputMultiByteChar(ch, outputPtr);
                }
            }
        }
        this._outputTail = outputPtr;
    }
    
    private final int _writeCustomEscape(final byte[] outputBuffer, final int outputPtr, final SerializableString esc, final int remainingChars) throws IOException, JsonGenerationException {
        final byte[] raw = esc.asUnquotedUTF8();
        final int len = raw.length;
        if (len > 6) {
            return this._handleLongCustomEscape(outputBuffer, outputPtr, this._outputEnd, raw, remainingChars);
        }
        System.arraycopy(raw, 0, outputBuffer, outputPtr, len);
        return outputPtr + len;
    }
    
    private final int _handleLongCustomEscape(final byte[] outputBuffer, int outputPtr, final int outputEnd, final byte[] raw, final int remainingChars) throws IOException, JsonGenerationException {
        final int len = raw.length;
        if (outputPtr + len > outputEnd) {
            this._outputTail = outputPtr;
            this._flushBuffer();
            outputPtr = this._outputTail;
            if (len > outputBuffer.length) {
                this._outputStream.write(raw, 0, len);
                return outputPtr;
            }
            System.arraycopy(raw, 0, outputBuffer, outputPtr, len);
            outputPtr += len;
        }
        if (outputPtr + 6 * remainingChars > outputEnd) {
            this._flushBuffer();
            return this._outputTail;
        }
        return outputPtr;
    }
    
    private final void _writeUTF8Segments(final byte[] utf8, int offset, int totalLen) throws IOException, JsonGenerationException {
        do {
            final int len = Math.min(this._outputMaxContiguous, totalLen);
            this._writeUTF8Segment(utf8, offset, len);
            offset += len;
            totalLen -= len;
        } while (totalLen > 0);
    }
    
    private final void _writeUTF8Segment(final byte[] utf8, final int offset, final int len) throws IOException, JsonGenerationException {
        final int[] escCodes = this._outputEscapes;
        int ptr = offset;
        final int end = offset + len;
        while (ptr < end) {
            final int ch = utf8[ptr++];
            if (ch >= 0 && escCodes[ch] != 0) {
                this._writeUTF8Segment2(utf8, offset, len);
                return;
            }
        }
        if (this._outputTail + len > this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(utf8, offset, this._outputBuffer, this._outputTail, len);
        this._outputTail += len;
    }
    
    private final void _writeUTF8Segment2(final byte[] utf8, int offset, int len) throws IOException, JsonGenerationException {
        int outputPtr = this._outputTail;
        if (outputPtr + len * 6 > this._outputEnd) {
            this._flushBuffer();
            outputPtr = this._outputTail;
        }
        final byte[] outputBuffer = this._outputBuffer;
        final int[] escCodes = this._outputEscapes;
        len += offset;
        while (offset < len) {
            final int ch;
            final byte b = (byte)(ch = utf8[offset++]);
            if (ch < 0 || escCodes[ch] == 0) {
                outputBuffer[outputPtr++] = b;
            }
            else {
                final int escape = escCodes[ch];
                if (escape > 0) {
                    outputBuffer[outputPtr++] = 92;
                    outputBuffer[outputPtr++] = (byte)escape;
                }
                else {
                    outputPtr = this._writeGenericEscape(ch, outputPtr);
                }
            }
        }
        this._outputTail = outputPtr;
    }
    
    protected final void _writeBinary(final Base64Variant b64variant, final byte[] input, int inputPtr, final int inputEnd) throws IOException, JsonGenerationException {
        final int safeInputEnd = inputEnd - 3;
        final int safeOutputEnd = this._outputEnd - 6;
        int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
        while (inputPtr <= safeInputEnd) {
            if (this._outputTail > safeOutputEnd) {
                this._flushBuffer();
            }
            int b24 = input[inputPtr++] << 8;
            b24 |= (input[inputPtr++] & 0xFF);
            b24 = (b24 << 8 | (input[inputPtr++] & 0xFF));
            this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
            if (--chunksBeforeLF <= 0) {
                this._outputBuffer[this._outputTail++] = 92;
                this._outputBuffer[this._outputTail++] = 110;
                chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
            }
        }
        final int inputLeft = inputEnd - inputPtr;
        if (inputLeft > 0) {
            if (this._outputTail > safeOutputEnd) {
                this._flushBuffer();
            }
            int b25 = input[inputPtr++] << 16;
            if (inputLeft == 2) {
                b25 |= (input[inputPtr++] & 0xFF) << 8;
            }
            this._outputTail = b64variant.encodeBase64Partial(b25, inputLeft, this._outputBuffer, this._outputTail);
        }
    }
    
    protected final int _writeBinary(final Base64Variant b64variant, final InputStream data, final byte[] readBuffer, int bytesLeft) throws IOException, JsonGenerationException {
        int inputPtr = 0;
        int inputEnd = 0;
        int lastFullOffset = -3;
        final int safeOutputEnd = this._outputEnd - 6;
        int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
        while (bytesLeft > 2) {
            if (inputPtr > lastFullOffset) {
                inputEnd = this._readMore(data, readBuffer, inputPtr, inputEnd, bytesLeft);
                inputPtr = 0;
                if (inputEnd < 3) {
                    break;
                }
                lastFullOffset = inputEnd - 3;
            }
            if (this._outputTail > safeOutputEnd) {
                this._flushBuffer();
            }
            int b24 = readBuffer[inputPtr++] << 8;
            b24 |= (readBuffer[inputPtr++] & 0xFF);
            b24 = (b24 << 8 | (readBuffer[inputPtr++] & 0xFF));
            bytesLeft -= 3;
            this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
            if (--chunksBeforeLF <= 0) {
                this._outputBuffer[this._outputTail++] = 92;
                this._outputBuffer[this._outputTail++] = 110;
                chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
            }
        }
        if (bytesLeft > 0) {
            inputEnd = this._readMore(data, readBuffer, inputPtr, inputEnd, bytesLeft);
            inputPtr = 0;
            if (inputEnd > 0) {
                if (this._outputTail > safeOutputEnd) {
                    this._flushBuffer();
                }
                int b24 = readBuffer[inputPtr++] << 16;
                int amount;
                if (inputPtr < inputEnd) {
                    b24 |= (readBuffer[inputPtr] & 0xFF) << 8;
                    amount = 2;
                }
                else {
                    amount = 1;
                }
                this._outputTail = b64variant.encodeBase64Partial(b24, amount, this._outputBuffer, this._outputTail);
                bytesLeft -= amount;
            }
        }
        return bytesLeft;
    }
    
    protected final int _writeBinary(final Base64Variant b64variant, final InputStream data, final byte[] readBuffer) throws IOException, JsonGenerationException {
        int inputPtr = 0;
        int inputEnd = 0;
        int lastFullOffset = -3;
        int bytesDone = 0;
        final int safeOutputEnd = this._outputEnd - 6;
        int chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
        while (true) {
            if (inputPtr > lastFullOffset) {
                inputEnd = this._readMore(data, readBuffer, inputPtr, inputEnd, readBuffer.length);
                inputPtr = 0;
                if (inputEnd < 3) {
                    break;
                }
                lastFullOffset = inputEnd - 3;
            }
            if (this._outputTail > safeOutputEnd) {
                this._flushBuffer();
            }
            int b24 = readBuffer[inputPtr++] << 8;
            b24 |= (readBuffer[inputPtr++] & 0xFF);
            b24 = (b24 << 8 | (readBuffer[inputPtr++] & 0xFF));
            bytesDone += 3;
            this._outputTail = b64variant.encodeBase64Chunk(b24, this._outputBuffer, this._outputTail);
            if (--chunksBeforeLF <= 0) {
                this._outputBuffer[this._outputTail++] = 92;
                this._outputBuffer[this._outputTail++] = 110;
                chunksBeforeLF = b64variant.getMaxLineLength() >> 2;
            }
        }
        if (inputPtr < inputEnd) {
            if (this._outputTail > safeOutputEnd) {
                this._flushBuffer();
            }
            int b24 = readBuffer[inputPtr++] << 16;
            int amount = 1;
            if (inputPtr < inputEnd) {
                b24 |= (readBuffer[inputPtr] & 0xFF) << 8;
                amount = 2;
            }
            bytesDone += amount;
            this._outputTail = b64variant.encodeBase64Partial(b24, amount, this._outputBuffer, this._outputTail);
        }
        return bytesDone;
    }
    
    private final int _readMore(final InputStream in, final byte[] readBuffer, int inputPtr, int inputEnd, int maxRead) throws IOException {
        int i;
        for (i = 0; inputPtr < inputEnd; readBuffer[i++] = readBuffer[inputPtr++]) {}
        inputPtr = 0;
        inputEnd = i;
        maxRead = Math.min(maxRead, readBuffer.length);
        do {
            final int length = maxRead - inputEnd;
            if (length == 0) {
                break;
            }
            final int count = in.read(readBuffer, inputEnd, length);
            if (count < 0) {
                return inputEnd;
            }
            inputEnd += count;
        } while (inputEnd < 3);
        return inputEnd;
    }
    
    private final int _outputRawMultiByteChar(final int ch, final char[] cbuf, final int inputOffset, final int inputEnd) throws IOException {
        if (ch >= 55296 && ch <= 57343) {
            if (inputOffset >= inputEnd || cbuf == null) {
                this._reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", ch));
            }
            this._outputSurrogates(ch, cbuf[inputOffset]);
            return inputOffset + 1;
        }
        final byte[] bbuf = this._outputBuffer;
        bbuf[this._outputTail++] = (byte)(0xE0 | ch >> 12);
        bbuf[this._outputTail++] = (byte)(0x80 | (ch >> 6 & 0x3F));
        bbuf[this._outputTail++] = (byte)(0x80 | (ch & 0x3F));
        return inputOffset;
    }
    
    protected final void _outputSurrogates(final int surr1, final int surr2) throws IOException {
        final int c = this._decodeSurrogate(surr1, surr2);
        if (this._outputTail + 4 > this._outputEnd) {
            this._flushBuffer();
        }
        final byte[] bbuf = this._outputBuffer;
        bbuf[this._outputTail++] = (byte)(0xF0 | c >> 18);
        bbuf[this._outputTail++] = (byte)(0x80 | (c >> 12 & 0x3F));
        bbuf[this._outputTail++] = (byte)(0x80 | (c >> 6 & 0x3F));
        bbuf[this._outputTail++] = (byte)(0x80 | (c & 0x3F));
    }
    
    private final int _outputMultiByteChar(final int ch, int outputPtr) throws IOException {
        final byte[] bbuf = this._outputBuffer;
        if (ch >= 55296 && ch <= 57343) {
            bbuf[outputPtr++] = 92;
            bbuf[outputPtr++] = 117;
            bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[ch >> 12 & 0xF];
            bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[ch >> 8 & 0xF];
            bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[ch >> 4 & 0xF];
            bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[ch & 0xF];
        }
        else {
            bbuf[outputPtr++] = (byte)(0xE0 | ch >> 12);
            bbuf[outputPtr++] = (byte)(0x80 | (ch >> 6 & 0x3F));
            bbuf[outputPtr++] = (byte)(0x80 | (ch & 0x3F));
        }
        return outputPtr;
    }
    
    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(UTF8JsonGenerator.NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
        this._outputTail += 4;
    }
    
    private int _writeGenericEscape(int charToEscape, int outputPtr) throws IOException {
        final byte[] bbuf = this._outputBuffer;
        bbuf[outputPtr++] = 92;
        bbuf[outputPtr++] = 117;
        if (charToEscape > 255) {
            final int hi = charToEscape >> 8 & 0xFF;
            bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[hi >> 4];
            bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[hi & 0xF];
            charToEscape &= 0xFF;
        }
        else {
            bbuf[outputPtr++] = 48;
            bbuf[outputPtr++] = 48;
        }
        bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[charToEscape >> 4];
        bbuf[outputPtr++] = UTF8JsonGenerator.HEX_CHARS[charToEscape & 0xF];
        return outputPtr;
    }
    
    protected final void _flushBuffer() throws IOException {
        final int len = this._outputTail;
        if (len > 0) {
            this._outputTail = 0;
            this._outputStream.write(this._outputBuffer, 0, len);
        }
    }
    
    static {
        HEX_CHARS = CharTypes.copyHexBytes();
        NULL_BYTES = new byte[] { 110, 117, 108, 108 };
        TRUE_BYTES = new byte[] { 116, 114, 117, 101 };
        FALSE_BYTES = new byte[] { 102, 97, 108, 115, 101 };
    }
}
