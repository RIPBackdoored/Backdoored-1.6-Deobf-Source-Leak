package com.fasterxml.jackson.core.json;

import java.io.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.*;

public class WriterBasedJsonGenerator extends JsonGeneratorImpl
{
    protected static final int SHORT_WRITE = 32;
    protected static final char[] HEX_CHARS;
    protected final Writer _writer;
    protected char _quoteChar;
    protected char[] _outputBuffer;
    protected int _outputHead;
    protected int _outputTail;
    protected int _outputEnd;
    protected char[] _entityBuffer;
    protected SerializableString _currentEscape;
    protected char[] _charBuffer;
    
    public WriterBasedJsonGenerator(final IOContext ctxt, final int features, final ObjectCodec codec, final Writer w) {
        super(ctxt, features, codec);
        this._quoteChar = '\"';
        this._writer = w;
        this._outputBuffer = ctxt.allocConcatBuffer();
        this._outputEnd = this._outputBuffer.length;
    }
    
    @Override
    public Object getOutputTarget() {
        return this._writer;
    }
    
    @Override
    public int getOutputBuffered() {
        final int len = this._outputTail - this._outputHead;
        return Math.max(0, len);
    }
    
    @Override
    public boolean canWriteFormattedNumbers() {
        return true;
    }
    
    @Override
    public void writeFieldName(final String name) throws IOException {
        final int status = this._writeContext.writeFieldName(name);
        if (status == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(name, status == 1);
    }
    
    @Override
    public void writeFieldName(final SerializableString name) throws IOException {
        final int status = this._writeContext.writeFieldName(name.getValue());
        if (status == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        this._writeFieldName(name, status == 1);
    }
    
    protected final void _writeFieldName(final String name, final boolean commaBefore) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(name, commaBefore);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (commaBefore) {
            this._outputBuffer[this._outputTail++] = ',';
        }
        if (this._cfgUnqNames) {
            this._writeString(name);
            return;
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeString(name);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    protected final void _writeFieldName(final SerializableString name, final boolean commaBefore) throws IOException {
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName(name, commaBefore);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (commaBefore) {
            this._outputBuffer[this._outputTail++] = ',';
        }
        final char[] quoted = name.asQuotedChars();
        if (this._cfgUnqNames) {
            this.writeRaw(quoted, 0, quoted.length);
            return;
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final int qlen = quoted.length;
        if (this._outputTail + qlen + 1 >= this._outputEnd) {
            this.writeRaw(quoted, 0, qlen);
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
        else {
            System.arraycopy(quoted, 0, this._outputBuffer, this._outputTail, qlen);
            this._outputTail += qlen;
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    @Override
    public void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = '[';
        }
    }
    
    @Override
    public void writeEndArray() throws IOException {
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
            this._outputBuffer[this._outputTail++] = ']';
        }
        this._writeContext = this._writeContext.clearAndGetParent();
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
            this._outputBuffer[this._outputTail++] = '{';
        }
    }
    
    @Override
    public void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = '{';
        }
    }
    
    @Override
    public void writeEndObject() throws IOException {
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
            this._outputBuffer[this._outputTail++] = '}';
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }
    
    protected final void _writePPFieldName(final String name, final boolean commaBefore) throws IOException {
        if (commaBefore) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeString(name);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
            this._writeString(name);
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
        }
    }
    
    protected final void _writePPFieldName(final SerializableString name, final boolean commaBefore) throws IOException {
        if (commaBefore) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        }
        else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        final char[] quoted = name.asQuotedChars();
        if (this._cfgUnqNames) {
            this.writeRaw(quoted, 0, quoted.length);
        }
        else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = this._quoteChar;
            this.writeRaw(quoted, 0, quoted.length);
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
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        this._writeString(text);
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
        final char[] buf = this._allocateCopyBuffer();
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
            this._writeString(buf, 0, numRead);
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
        this._writeString(text, offset, len);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeString(final SerializableString sstr) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
        final char[] text = sstr.asQuotedChars();
        final int len = text.length;
        if (len < 32) {
            final int room = this._outputEnd - this._outputTail;
            if (len > room) {
                this._flushBuffer();
            }
            System.arraycopy(text, 0, this._outputBuffer, this._outputTail, len);
            this._outputTail += len;
        }
        else {
            this._flushBuffer();
            this._writer.write(text, 0, len);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = this._quoteChar;
    }
    
    @Override
    public void writeRawUTF8String(final byte[] text, final int offset, final int length) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeUTF8String(final byte[] text, final int offset, final int length) throws IOException {
        this._reportUnsupportedOperation();
    }
    
    @Override
    public void writeRaw(final String text) throws IOException {
        final int len = text.length();
        int room = this._outputEnd - this._outputTail;
        if (room == 0) {
            this._flushBuffer();
            room = this._outputEnd - this._outputTail;
        }
        if (room >= len) {
            text.getChars(0, len, this._outputBuffer, this._outputTail);
            this._outputTail += len;
        }
        else {
            this.writeRawLong(text);
        }
    }
    
    @Override
    public void writeRaw(final String text, final int start, final int len) throws IOException {
        int room = this._outputEnd - this._outputTail;
        if (room < len) {
            this._flushBuffer();
            room = this._outputEnd - this._outputTail;
        }
        if (room >= len) {
            text.getChars(start, start + len, this._outputBuffer, this._outputTail);
            this._outputTail += len;
        }
        else {
            this.writeRawLong(text.substring(start, start + len));
        }
    }
    
    @Override
    public void writeRaw(final SerializableString text) throws IOException {
        this.writeRaw(text.getValue());
    }
    
    @Override
    public void writeRaw(final char[] text, final int offset, final int len) throws IOException {
        if (len < 32) {
            final int room = this._outputEnd - this._outputTail;
            if (len > room) {
                this._flushBuffer();
            }
            System.arraycopy(text, offset, this._outputBuffer, this._outputTail, len);
            this._outputTail += len;
            return;
        }
        this._flushBuffer();
        this._writer.write(text, offset, len);
    }
    
    @Override
    public void writeRaw(final char c) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputBuffer[this._outputTail++] = c;
    }
    
    private void writeRawLong(final String text) throws IOException {
        final int room = this._outputEnd - this._outputTail;
        text.getChars(0, room, this._outputBuffer, this._outputTail);
        this._outputTail += room;
        this._flushBuffer();
        int offset = room;
        int len;
        int amount;
        for (len = text.length() - room; len > this._outputEnd; len -= amount) {
            amount = this._outputEnd;
            text.getChars(offset, offset + amount, this._outputBuffer, 0);
            this._outputHead = 0;
            this._outputTail = amount;
            this._flushBuffer();
            offset += amount;
        }
        text.getChars(offset, offset + len, this._outputBuffer, 0);
        this._outputHead = 0;
        this._outputTail = len;
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
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(s);
            return;
        }
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(s, this._outputBuffer, this._outputTail);
    }
    
    private void _writeQuotedShort(final short s) throws IOException {
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
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(i);
            return;
        }
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt(i, this._outputBuffer, this._outputTail);
    }
    
    private void _writeQuotedInt(final int i) throws IOException {
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
    
    private void _writeQuotedLong(final long l) throws IOException {
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
        if (this._cfgNumbersAsStrings || (this.isEnabled(Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Double.isNaN(d) || Double.isInfinite(d)))) {
            this.writeString(String.valueOf(d));
            return;
        }
        this._verifyValueWrite("write a number");
        this.writeRaw(String.valueOf(d));
    }
    
    @Override
    public void writeNumber(final float f) throws IOException {
        if (this._cfgNumbersAsStrings || (this.isEnabled(Feature.QUOTE_NON_NUMERIC_NUMBERS) && (Float.isNaN(f) || Float.isInfinite(f)))) {
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
    
    private void _writeQuotedRaw(final String value) throws IOException {
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
        int ptr = this._outputTail;
        final char[] buf = this._outputBuffer;
        if (state) {
            buf[ptr] = 't';
            buf[++ptr] = 'r';
            buf[++ptr] = 'u';
            buf[++ptr] = 'e';
        }
        else {
            buf[ptr] = 'f';
            buf[++ptr] = 'a';
            buf[++ptr] = 'l';
            buf[++ptr] = 's';
            buf[++ptr] = 'e';
        }
        this._outputTail = ptr + 1;
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
        char c = '\0';
        switch (status) {
            default: {
                return;
            }
            case 1: {
                c = ',';
                break;
            }
            case 2: {
                c = ':';
                break;
            }
            case 3: {
                if (this._rootValueSeparator != null) {
                    this.writeRaw(this._rootValueSeparator.getValue());
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
        this._outputBuffer[this._outputTail++] = c;
    }
    
    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._writer != null && this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
            this._writer.flush();
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
        this._outputHead = 0;
        this._outputTail = 0;
        if (this._writer != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_TARGET)) {
                this._writer.close();
            }
            else if (this.isEnabled(Feature.FLUSH_PASSED_TO_STREAM)) {
                this._writer.flush();
            }
        }
        this._releaseBuffers();
    }
    
    @Override
    protected void _releaseBuffers() {
        char[] buf = this._outputBuffer;
        if (buf != null) {
            this._outputBuffer = null;
            this._ioContext.releaseConcatBuffer(buf);
        }
        buf = this._charBuffer;
        if (buf != null) {
            this._charBuffer = null;
            this._ioContext.releaseNameCopyBuffer(buf);
        }
    }
    
    private void _writeString(final String text) throws IOException {
        final int len = text.length();
        if (len > this._outputEnd) {
            this._writeLongString(text);
            return;
        }
        if (this._outputTail + len > this._outputEnd) {
            this._flushBuffer();
        }
        text.getChars(0, len, this._outputBuffer, this._outputTail);
        if (this._characterEscapes != null) {
            this._writeStringCustom(len);
        }
        else if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(len, this._maximumNonEscapedChar);
        }
        else {
            this._writeString2(len);
        }
    }
    
    private void _writeString2(final int len) throws IOException {
        final int end = this._outputTail + len;
        final int[] escCodes = this._outputEscapes;
        final int escLen = escCodes.length;
    Label_0137:
        while (this._outputTail < end) {
            while (true) {
                final char c = this._outputBuffer[this._outputTail];
                if (c < escLen && escCodes[c] != 0) {
                    final int flushLen = this._outputTail - this._outputHead;
                    if (flushLen > 0) {
                        this._writer.write(this._outputBuffer, this._outputHead, flushLen);
                    }
                    final char c2 = this._outputBuffer[this._outputTail++];
                    this._prependOrWriteCharacterEscape(c2, escCodes[c2]);
                    break;
                }
                if (++this._outputTail >= end) {
                    break Label_0137;
                }
            }
        }
    }
    
    private void _writeLongString(final String text) throws IOException {
        this._flushBuffer();
        final int textLen = text.length();
        int offset = 0;
        do {
            final int max = this._outputEnd;
            final int segmentLen = (offset + max > textLen) ? (textLen - offset) : max;
            text.getChars(offset, offset + segmentLen, this._outputBuffer, 0);
            if (this._characterEscapes != null) {
                this._writeSegmentCustom(segmentLen);
            }
            else if (this._maximumNonEscapedChar != 0) {
                this._writeSegmentASCII(segmentLen, this._maximumNonEscapedChar);
            }
            else {
                this._writeSegment(segmentLen);
            }
            offset += segmentLen;
        } while (offset < textLen);
    }
    
    private void _writeSegment(final int end) throws IOException {
        final int[] escCodes = this._outputEscapes;
        final int escLen = escCodes.length;
        int start;
        char c;
        for (int ptr = start = 0; ptr < end; ++ptr, start = this._prependOrWriteCharacterEscape(this._outputBuffer, ptr, end, c, escCodes[c])) {
            do {
                c = this._outputBuffer[ptr];
                if (c < escLen && escCodes[c] != 0) {
                    break;
                }
            } while (++ptr < end);
            final int flushLen = ptr - start;
            if (flushLen > 0) {
                this._writer.write(this._outputBuffer, start, flushLen);
                if (ptr >= end) {
                    break;
                }
            }
        }
    }
    
    private void _writeString(final char[] text, int offset, int len) throws IOException {
        if (this._characterEscapes != null) {
            this._writeStringCustom(text, offset, len);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(text, offset, len, this._maximumNonEscapedChar);
            return;
        }
        len += offset;
        final int[] escCodes = this._outputEscapes;
        final int escLen = escCodes.length;
        while (offset < len) {
            final int start = offset;
            do {
                final char c = text[offset];
                if (c < escLen && escCodes[c] != 0) {
                    break;
                }
            } while (++offset < len);
            final int newAmount = offset - start;
            if (newAmount < 32) {
                if (this._outputTail + newAmount > this._outputEnd) {
                    this._flushBuffer();
                }
                if (newAmount > 0) {
                    System.arraycopy(text, start, this._outputBuffer, this._outputTail, newAmount);
                    this._outputTail += newAmount;
                }
            }
            else {
                this._flushBuffer();
                this._writer.write(text, start, newAmount);
            }
            if (offset >= len) {
                break;
            }
            final char c2 = text[offset++];
            this._appendCharacterEscape(c2, escCodes[c2]);
        }
    }
    
    private void _writeStringASCII(final int len, final int maxNonEscaped) throws IOException, JsonGenerationException {
        final int end = this._outputTail + len;
        final int[] escCodes = this._outputEscapes;
        final int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
        int escCode = 0;
    Label_0027:
        while (this._outputTail < end) {
            do {
                final char c = this._outputBuffer[this._outputTail];
                if (c < escLimit) {
                    escCode = escCodes[c];
                    if (escCode == 0) {
                        continue;
                    }
                }
                else {
                    if (c <= maxNonEscaped) {
                        continue;
                    }
                    escCode = -1;
                }
                final int flushLen = this._outputTail - this._outputHead;
                if (flushLen > 0) {
                    this._writer.write(this._outputBuffer, this._outputHead, flushLen);
                }
                ++this._outputTail;
                this._prependOrWriteCharacterEscape(c, escCode);
                continue Label_0027;
            } while (++this._outputTail < end);
            break;
        }
    }
    
    private void _writeSegmentASCII(final int end, final int maxNonEscaped) throws IOException, JsonGenerationException {
        final int[] escCodes = this._outputEscapes;
        final int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
        int ptr = 0;
        int escCode = 0;
        int start = ptr;
        while (ptr < end) {
            char c;
            do {
                c = this._outputBuffer[ptr];
                if (c < escLimit) {
                    escCode = escCodes[c];
                    if (escCode != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (c > maxNonEscaped) {
                        escCode = -1;
                        break;
                    }
                    continue;
                }
            } while (++ptr < end);
            final int flushLen = ptr - start;
            if (flushLen > 0) {
                this._writer.write(this._outputBuffer, start, flushLen);
                if (ptr >= end) {
                    break;
                }
            }
            ++ptr;
            start = this._prependOrWriteCharacterEscape(this._outputBuffer, ptr, end, c, escCode);
        }
    }
    
    private void _writeStringASCII(final char[] text, int offset, int len, final int maxNonEscaped) throws IOException, JsonGenerationException {
        len += offset;
        final int[] escCodes = this._outputEscapes;
        final int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
        int escCode = 0;
        while (offset < len) {
            final int start = offset;
            char c;
            do {
                c = text[offset];
                if (c < escLimit) {
                    escCode = escCodes[c];
                    if (escCode != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (c > maxNonEscaped) {
                        escCode = -1;
                        break;
                    }
                    continue;
                }
            } while (++offset < len);
            final int newAmount = offset - start;
            if (newAmount < 32) {
                if (this._outputTail + newAmount > this._outputEnd) {
                    this._flushBuffer();
                }
                if (newAmount > 0) {
                    System.arraycopy(text, start, this._outputBuffer, this._outputTail, newAmount);
                    this._outputTail += newAmount;
                }
            }
            else {
                this._flushBuffer();
                this._writer.write(text, start, newAmount);
            }
            if (offset >= len) {
                break;
            }
            ++offset;
            this._appendCharacterEscape(c, escCode);
        }
    }
    
    private void _writeStringCustom(final int len) throws IOException, JsonGenerationException {
        final int end = this._outputTail + len;
        final int[] escCodes = this._outputEscapes;
        final int maxNonEscaped = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
        final int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
        int escCode = 0;
        final CharacterEscapes customEscapes = this._characterEscapes;
    Label_0052:
        while (this._outputTail < end) {
            do {
                final char c = this._outputBuffer[this._outputTail];
                if (c < escLimit) {
                    escCode = escCodes[c];
                    if (escCode == 0) {
                        continue;
                    }
                }
                else if (c > maxNonEscaped) {
                    escCode = -1;
                }
                else {
                    if ((this._currentEscape = customEscapes.getEscapeSequence(c)) == null) {
                        continue;
                    }
                    escCode = -2;
                }
                final int flushLen = this._outputTail - this._outputHead;
                if (flushLen > 0) {
                    this._writer.write(this._outputBuffer, this._outputHead, flushLen);
                }
                ++this._outputTail;
                this._prependOrWriteCharacterEscape(c, escCode);
                continue Label_0052;
            } while (++this._outputTail < end);
            break;
        }
    }
    
    private void _writeSegmentCustom(final int end) throws IOException, JsonGenerationException {
        final int[] escCodes = this._outputEscapes;
        final int maxNonEscaped = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
        final int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
        final CharacterEscapes customEscapes = this._characterEscapes;
        int ptr = 0;
        int escCode = 0;
        int start = ptr;
        while (ptr < end) {
            char c;
            do {
                c = this._outputBuffer[ptr];
                if (c < escLimit) {
                    escCode = escCodes[c];
                    if (escCode != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (c > maxNonEscaped) {
                        escCode = -1;
                        break;
                    }
                    if ((this._currentEscape = customEscapes.getEscapeSequence(c)) != null) {
                        escCode = -2;
                        break;
                    }
                    continue;
                }
            } while (++ptr < end);
            final int flushLen = ptr - start;
            if (flushLen > 0) {
                this._writer.write(this._outputBuffer, start, flushLen);
                if (ptr >= end) {
                    break;
                }
            }
            ++ptr;
            start = this._prependOrWriteCharacterEscape(this._outputBuffer, ptr, end, c, escCode);
        }
    }
    
    private void _writeStringCustom(final char[] text, int offset, int len) throws IOException, JsonGenerationException {
        len += offset;
        final int[] escCodes = this._outputEscapes;
        final int maxNonEscaped = (this._maximumNonEscapedChar < 1) ? 65535 : this._maximumNonEscapedChar;
        final int escLimit = Math.min(escCodes.length, maxNonEscaped + 1);
        final CharacterEscapes customEscapes = this._characterEscapes;
        int escCode = 0;
        while (offset < len) {
            final int start = offset;
            char c;
            do {
                c = text[offset];
                if (c < escLimit) {
                    escCode = escCodes[c];
                    if (escCode != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    if (c > maxNonEscaped) {
                        escCode = -1;
                        break;
                    }
                    if ((this._currentEscape = customEscapes.getEscapeSequence(c)) != null) {
                        escCode = -2;
                        break;
                    }
                    continue;
                }
            } while (++offset < len);
            final int newAmount = offset - start;
            if (newAmount < 32) {
                if (this._outputTail + newAmount > this._outputEnd) {
                    this._flushBuffer();
                }
                if (newAmount > 0) {
                    System.arraycopy(text, start, this._outputBuffer, this._outputTail, newAmount);
                    this._outputTail += newAmount;
                }
            }
            else {
                this._flushBuffer();
                this._writer.write(text, start, newAmount);
            }
            if (offset >= len) {
                break;
            }
            ++offset;
            this._appendCharacterEscape(c, escCode);
        }
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
                this._outputBuffer[this._outputTail++] = '\\';
                this._outputBuffer[this._outputTail++] = 'n';
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
                this._outputBuffer[this._outputTail++] = '\\';
                this._outputBuffer[this._outputTail++] = 'n';
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
                this._outputBuffer[this._outputTail++] = '\\';
                this._outputBuffer[this._outputTail++] = 'n';
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
    
    private int _readMore(final InputStream in, final byte[] readBuffer, int inputPtr, int inputEnd, int maxRead) throws IOException {
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
    
    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        int ptr = this._outputTail;
        final char[] buf = this._outputBuffer;
        buf[ptr] = 'n';
        buf[++ptr] = 'u';
        buf[++ptr] = 'l';
        buf[++ptr] = 'l';
        this._outputTail = ptr + 1;
    }
    
    private void _prependOrWriteCharacterEscape(char ch, final int escCode) throws IOException, JsonGenerationException {
        if (escCode >= 0) {
            if (this._outputTail >= 2) {
                int ptr = this._outputTail - 2;
                this._outputHead = ptr;
                this._outputBuffer[ptr++] = '\\';
                this._outputBuffer[ptr] = (char)escCode;
                return;
            }
            char[] buf = this._entityBuffer;
            if (buf == null) {
                buf = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            buf[1] = (char)escCode;
            this._writer.write(buf, 0, 2);
        }
        else if (escCode != -2) {
            if (this._outputTail >= 6) {
                final char[] buf = this._outputBuffer;
                int ptr2 = this._outputTail - 6;
                buf[this._outputHead = ptr2] = '\\';
                buf[++ptr2] = 'u';
                if (ch > '\u00ff') {
                    final int hi = ch >> 8 & 0xFF;
                    buf[++ptr2] = WriterBasedJsonGenerator.HEX_CHARS[hi >> 4];
                    buf[++ptr2] = WriterBasedJsonGenerator.HEX_CHARS[hi & 0xF];
                    ch &= '\u00ff';
                }
                else {
                    buf[++ptr2] = '0';
                    buf[++ptr2] = '0';
                }
                buf[++ptr2] = WriterBasedJsonGenerator.HEX_CHARS[ch >> 4];
                buf[++ptr2] = WriterBasedJsonGenerator.HEX_CHARS[ch & '\u000f'];
                return;
            }
            char[] buf = this._entityBuffer;
            if (buf == null) {
                buf = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (ch > '\u00ff') {
                final int hi2 = ch >> 8 & 0xFF;
                final int lo = ch & '\u00ff';
                buf[10] = WriterBasedJsonGenerator.HEX_CHARS[hi2 >> 4];
                buf[11] = WriterBasedJsonGenerator.HEX_CHARS[hi2 & 0xF];
                buf[12] = WriterBasedJsonGenerator.HEX_CHARS[lo >> 4];
                buf[13] = WriterBasedJsonGenerator.HEX_CHARS[lo & 0xF];
                this._writer.write(buf, 8, 6);
            }
            else {
                buf[6] = WriterBasedJsonGenerator.HEX_CHARS[ch >> 4];
                buf[7] = WriterBasedJsonGenerator.HEX_CHARS[ch & '\u000f'];
                this._writer.write(buf, 2, 6);
            }
        }
        else {
            String escape;
            if (this._currentEscape == null) {
                escape = this._characterEscapes.getEscapeSequence(ch).getValue();
            }
            else {
                escape = this._currentEscape.getValue();
                this._currentEscape = null;
            }
            final int len = escape.length();
            if (this._outputTail >= len) {
                final int ptr3 = this._outputTail - len;
                this._outputHead = ptr3;
                escape.getChars(0, len, this._outputBuffer, ptr3);
                return;
            }
            this._outputHead = this._outputTail;
            this._writer.write(escape);
        }
    }
    
    private int _prependOrWriteCharacterEscape(final char[] buffer, int ptr, final int end, char ch, final int escCode) throws IOException, JsonGenerationException {
        if (escCode >= 0) {
            if (ptr > 1 && ptr < end) {
                ptr -= 2;
                buffer[ptr] = '\\';
                buffer[ptr + 1] = (char)escCode;
            }
            else {
                char[] ent = this._entityBuffer;
                if (ent == null) {
                    ent = this._allocateEntityBuffer();
                }
                ent[1] = (char)escCode;
                this._writer.write(ent, 0, 2);
            }
            return ptr;
        }
        if (escCode != -2) {
            if (ptr > 5 && ptr < end) {
                ptr -= 6;
                buffer[ptr++] = '\\';
                buffer[ptr++] = 'u';
                if (ch > '\u00ff') {
                    final int hi = ch >> 8 & 0xFF;
                    buffer[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[hi >> 4];
                    buffer[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[hi & 0xF];
                    ch &= '\u00ff';
                }
                else {
                    buffer[ptr++] = '0';
                    buffer[ptr++] = '0';
                }
                buffer[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[ch >> 4];
                buffer[ptr] = WriterBasedJsonGenerator.HEX_CHARS[ch & '\u000f'];
                ptr -= 5;
            }
            else {
                char[] ent = this._entityBuffer;
                if (ent == null) {
                    ent = this._allocateEntityBuffer();
                }
                this._outputHead = this._outputTail;
                if (ch > '\u00ff') {
                    final int hi2 = ch >> 8 & 0xFF;
                    final int lo = ch & '\u00ff';
                    ent[10] = WriterBasedJsonGenerator.HEX_CHARS[hi2 >> 4];
                    ent[11] = WriterBasedJsonGenerator.HEX_CHARS[hi2 & 0xF];
                    ent[12] = WriterBasedJsonGenerator.HEX_CHARS[lo >> 4];
                    ent[13] = WriterBasedJsonGenerator.HEX_CHARS[lo & 0xF];
                    this._writer.write(ent, 8, 6);
                }
                else {
                    ent[6] = WriterBasedJsonGenerator.HEX_CHARS[ch >> 4];
                    ent[7] = WriterBasedJsonGenerator.HEX_CHARS[ch & '\u000f'];
                    this._writer.write(ent, 2, 6);
                }
            }
            return ptr;
        }
        String escape;
        if (this._currentEscape == null) {
            escape = this._characterEscapes.getEscapeSequence(ch).getValue();
        }
        else {
            escape = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        final int len = escape.length();
        if (ptr >= len && ptr < end) {
            ptr -= len;
            escape.getChars(0, len, buffer, ptr);
        }
        else {
            this._writer.write(escape);
        }
        return ptr;
    }
    
    private void _appendCharacterEscape(char ch, final int escCode) throws IOException, JsonGenerationException {
        if (escCode >= 0) {
            if (this._outputTail + 2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._outputBuffer[this._outputTail++] = '\\';
            this._outputBuffer[this._outputTail++] = (char)escCode;
            return;
        }
        if (escCode != -2) {
            if (this._outputTail + 5 >= this._outputEnd) {
                this._flushBuffer();
            }
            int ptr = this._outputTail;
            final char[] buf = this._outputBuffer;
            buf[ptr++] = '\\';
            buf[ptr++] = 'u';
            if (ch > '\u00ff') {
                final int hi = ch >> 8 & 0xFF;
                buf[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[hi >> 4];
                buf[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[hi & 0xF];
                ch &= '\u00ff';
            }
            else {
                buf[ptr++] = '0';
                buf[ptr++] = '0';
            }
            buf[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[ch >> 4];
            buf[ptr++] = WriterBasedJsonGenerator.HEX_CHARS[ch & '\u000f'];
            this._outputTail = ptr;
            return;
        }
        String escape;
        if (this._currentEscape == null) {
            escape = this._characterEscapes.getEscapeSequence(ch).getValue();
        }
        else {
            escape = this._currentEscape.getValue();
            this._currentEscape = null;
        }
        final int len = escape.length();
        if (this._outputTail + len > this._outputEnd) {
            this._flushBuffer();
            if (len > this._outputEnd) {
                this._writer.write(escape);
                return;
            }
        }
        escape.getChars(0, len, this._outputBuffer, this._outputTail);
        this._outputTail += len;
    }
    
    private char[] _allocateEntityBuffer() {
        final char[] buf = { '\\', '\0', '\\', 'u', '0', '0', '\0', '\0', '\\', 'u', '\0', '\0', '\0', '\0' };
        return this._entityBuffer = buf;
    }
    
    private char[] _allocateCopyBuffer() {
        if (this._charBuffer == null) {
            this._charBuffer = this._ioContext.allocNameCopyBuffer(2000);
        }
        return this._charBuffer;
    }
    
    protected void _flushBuffer() throws IOException {
        final int len = this._outputTail - this._outputHead;
        if (len > 0) {
            final int offset = this._outputHead;
            final int n = 0;
            this._outputHead = n;
            this._outputTail = n;
            this._writer.write(this._outputBuffer, offset, len);
        }
    }
    
    static {
        HEX_CHARS = CharTypes.copyHexChars();
    }
}
