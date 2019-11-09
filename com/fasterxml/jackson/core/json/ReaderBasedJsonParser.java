package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.base.*;
import com.fasterxml.jackson.core.sym.*;
import java.io.*;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;

public class ReaderBasedJsonParser extends ParserBase
{
    protected static final int FEAT_MASK_TRAILING_COMMA;
    protected static final int[] _icLatin1;
    protected Reader _reader;
    protected char[] _inputBuffer;
    protected boolean _bufferRecyclable;
    protected ObjectCodec _objectCodec;
    protected final CharsToNameCanonicalizer _symbols;
    protected final int _hashSeed;
    protected boolean _tokenIncomplete;
    protected long _nameStartOffset;
    protected int _nameStartRow;
    protected int _nameStartCol;
    
    public ReaderBasedJsonParser(final IOContext ctxt, final int features, final Reader r, final ObjectCodec codec, final CharsToNameCanonicalizer st, final char[] inputBuffer, final int start, final int end, final boolean bufferRecyclable) {
        super(ctxt, features);
        this._reader = r;
        this._inputBuffer = inputBuffer;
        this._inputPtr = start;
        this._inputEnd = end;
        this._objectCodec = codec;
        this._symbols = st;
        this._hashSeed = st.hashSeed();
        this._bufferRecyclable = bufferRecyclable;
    }
    
    public ReaderBasedJsonParser(final IOContext ctxt, final int features, final Reader r, final ObjectCodec codec, final CharsToNameCanonicalizer st) {
        super(ctxt, features);
        this._reader = r;
        this._inputBuffer = ctxt.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = codec;
        this._symbols = st;
        this._hashSeed = st.hashSeed();
        this._bufferRecyclable = true;
    }
    
    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }
    
    @Override
    public void setCodec(final ObjectCodec c) {
        this._objectCodec = c;
    }
    
    @Override
    public int releaseBuffered(final Writer w) throws IOException {
        final int count = this._inputEnd - this._inputPtr;
        if (count < 1) {
            return 0;
        }
        final int origPtr = this._inputPtr;
        w.write(this._inputBuffer, origPtr, count);
        return count;
    }
    
    @Override
    public Object getInputSource() {
        return this._reader;
    }
    
    @Deprecated
    protected char getNextChar(final String eofMsg) throws IOException {
        return this.getNextChar(eofMsg, null);
    }
    
    protected char getNextChar(final String eofMsg, final JsonToken forToken) throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(eofMsg, forToken);
        }
        return this._inputBuffer[this._inputPtr++];
    }
    
    @Override
    protected void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || this.isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }
    
    @Override
    protected void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable) {
            final char[] buf = this._inputBuffer;
            if (buf != null) {
                this._inputBuffer = null;
                this._ioContext.releaseTokenBuffer(buf);
            }
        }
    }
    
    protected void _loadMoreGuaranteed() throws IOException {
        if (!this._loadMore()) {
            this._reportInvalidEOF();
        }
    }
    
    protected boolean _loadMore() throws IOException {
        final int bufSize = this._inputEnd;
        this._currInputProcessed += bufSize;
        this._currInputRowStart -= bufSize;
        this._nameStartOffset -= bufSize;
        if (this._reader != null) {
            final int count = this._reader.read(this._inputBuffer, 0, this._inputBuffer.length);
            if (count > 0) {
                this._inputPtr = 0;
                this._inputEnd = count;
                return true;
            }
            this._closeInput();
            if (count == 0) {
                throw new IOException("Reader returned 0 characters when trying to read " + this._inputEnd);
            }
        }
        return false;
    }
    
    @Override
    public final String getText() throws IOException {
        final JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        return this._getText2(t);
    }
    
    @Override
    public int getText(final Writer writer) throws IOException {
        final JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsToWriter(writer);
        }
        if (t == JsonToken.FIELD_NAME) {
            final String n = this._parsingContext.getCurrentName();
            writer.write(n);
            return n.length();
        }
        if (t == null) {
            return 0;
        }
        if (t.isNumeric()) {
            return this._textBuffer.contentsToWriter(writer);
        }
        final char[] ch = t.asCharArray();
        writer.write(ch);
        return ch.length;
    }
    
    @Override
    public final String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(null);
    }
    
    @Override
    public final String getValueAsString(final String defValue) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return super.getValueAsString(defValue);
    }
    
    protected final String _getText2(final JsonToken t) {
        if (t == null) {
            return null;
        }
        switch (t.id()) {
            case 5: {
                return this._parsingContext.getCurrentName();
            }
            case 6:
            case 7:
            case 8: {
                return this._textBuffer.contentsAsString();
            }
            default: {
                return t.asString();
            }
        }
    }
    
    @Override
    public final char[] getTextCharacters() throws IOException {
        if (this._currToken == null) {
            return null;
        }
        switch (this._currToken.id()) {
            case 5: {
                if (!this._nameCopied) {
                    final String name = this._parsingContext.getCurrentName();
                    final int nameLen = name.length();
                    if (this._nameCopyBuffer == null) {
                        this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(nameLen);
                    }
                    else if (this._nameCopyBuffer.length < nameLen) {
                        this._nameCopyBuffer = new char[nameLen];
                    }
                    name.getChars(0, nameLen, this._nameCopyBuffer, 0);
                    this._nameCopied = true;
                }
                return this._nameCopyBuffer;
            }
            case 6: {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    this._finishString();
                    return this._textBuffer.getTextBuffer();
                }
                return this._textBuffer.getTextBuffer();
            }
            case 7:
            case 8: {
                return this._textBuffer.getTextBuffer();
            }
            default: {
                return this._currToken.asCharArray();
            }
        }
    }
    
    @Override
    public final int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        switch (this._currToken.id()) {
            case 5: {
                return this._parsingContext.getCurrentName().length();
            }
            case 6: {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    this._finishString();
                    return this._textBuffer.size();
                }
                return this._textBuffer.size();
            }
            case 7:
            case 8: {
                return this._textBuffer.size();
            }
            default: {
                return this._currToken.asCharArray().length;
            }
        }
    }
    
    @Override
    public final int getTextOffset() throws IOException {
        if (this._currToken != null) {
            switch (this._currToken.id()) {
                case 5: {
                    return 0;
                }
                case 6: {
                    if (this._tokenIncomplete) {
                        this._tokenIncomplete = false;
                        this._finishString();
                        return this._textBuffer.getTextOffset();
                    }
                    return this._textBuffer.getTextOffset();
                }
                case 7:
                case 8: {
                    return this._textBuffer.getTextOffset();
                }
            }
        }
        return 0;
    }
    
    @Override
    public byte[] getBinaryValue(final Base64Variant b64variant) throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
            return this._binaryValue;
        }
        if (this._currToken != JsonToken.VALUE_STRING) {
            this._reportError("Current token (" + this._currToken + ") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = this._decodeBase64(b64variant);
            }
            catch (IllegalArgumentException iae) {
                throw this._constructError("Failed to decode VALUE_STRING as base64 (" + b64variant + "): " + iae.getMessage());
            }
            this._tokenIncomplete = false;
        }
        else if (this._binaryValue == null) {
            final ByteArrayBuilder builder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), builder, b64variant);
            this._binaryValue = builder.toByteArray();
        }
        return this._binaryValue;
    }
    
    @Override
    public int readBinaryValue(final Base64Variant b64variant, final OutputStream out) throws IOException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            final byte[] b = this.getBinaryValue(b64variant);
            out.write(b);
            return b.length;
        }
        final byte[] buf = this._ioContext.allocBase64Buffer();
        try {
            return this._readBinary(b64variant, out, buf);
        }
        finally {
            this._ioContext.releaseBase64Buffer(buf);
        }
    }
    
    protected int _readBinary(final Base64Variant b64variant, final OutputStream out, final byte[] buffer) throws IOException {
        int outputPtr = 0;
        final int outputEnd = buffer.length - 3;
        int outputCount = 0;
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            char ch = this._inputBuffer[this._inputPtr++];
            if (ch > ' ') {
                int bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (ch == '\"') {
                        break;
                    }
                    bits = this._decodeBase64Escape(b64variant, ch, 0);
                    if (bits < 0) {
                        continue;
                    }
                }
                if (outputPtr > outputEnd) {
                    outputCount += outputPtr;
                    out.write(buffer, 0, outputPtr);
                    outputPtr = 0;
                }
                int decodedData = bits;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                ch = this._inputBuffer[this._inputPtr++];
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    bits = this._decodeBase64Escape(b64variant, ch, 1);
                }
                decodedData = (decodedData << 6 | bits);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                ch = this._inputBuffer[this._inputPtr++];
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (bits != -2) {
                        if (ch == '\"' && !b64variant.usesPadding()) {
                            decodedData >>= 4;
                            buffer[outputPtr++] = (byte)decodedData;
                            break;
                        }
                        bits = this._decodeBase64Escape(b64variant, ch, 2);
                    }
                    if (bits == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        ch = this._inputBuffer[this._inputPtr++];
                        if (!b64variant.usesPaddingChar(ch)) {
                            throw this.reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                        }
                        decodedData >>= 4;
                        buffer[outputPtr++] = (byte)decodedData;
                        continue;
                    }
                }
                decodedData = (decodedData << 6 | bits);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                ch = this._inputBuffer[this._inputPtr++];
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (bits != -2) {
                        if (ch == '\"' && !b64variant.usesPadding()) {
                            decodedData >>= 2;
                            buffer[outputPtr++] = (byte)(decodedData >> 8);
                            buffer[outputPtr++] = (byte)decodedData;
                            break;
                        }
                        bits = this._decodeBase64Escape(b64variant, ch, 3);
                    }
                    if (bits == -2) {
                        decodedData >>= 2;
                        buffer[outputPtr++] = (byte)(decodedData >> 8);
                        buffer[outputPtr++] = (byte)decodedData;
                        continue;
                    }
                }
                decodedData = (decodedData << 6 | bits);
                buffer[outputPtr++] = (byte)(decodedData >> 16);
                buffer[outputPtr++] = (byte)(decodedData >> 8);
                buffer[outputPtr++] = (byte)decodedData;
            }
        }
        this._tokenIncomplete = false;
        if (outputPtr > 0) {
            outputCount += outputPtr;
            out.write(buffer, 0, outputPtr);
        }
        return outputCount;
    }
    
    @Override
    public final JsonToken nextToken() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this._nextAfterName();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int i = this._skipWSOrEnd();
        if (i < 0) {
            this.close();
            return this._currToken = null;
        }
        this._binaryValue = null;
        if (i == 93 || i == 125) {
            this._closeScope(i);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            i = this._skipComma(i);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (i == 93 || i == 125)) {
                this._closeScope(i);
                return this._currToken;
            }
        }
        final boolean inObject = this._parsingContext.inObject();
        if (inObject) {
            this._updateNameLocation();
            final String name = (i == 34) ? this._parseName() : this._handleOddName(i);
            this._parsingContext.setCurrentName(name);
            this._currToken = JsonToken.FIELD_NAME;
            i = this._skipColon();
        }
        this._updateLocation();
        JsonToken t = null;
        switch (i) {
            case 34: {
                this._tokenIncomplete = true;
                t = JsonToken.VALUE_STRING;
                break;
            }
            case 91: {
                if (!inObject) {
                    this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                }
                t = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                if (!inObject) {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                }
                t = JsonToken.START_OBJECT;
                break;
            }
            case 125: {
                this._reportUnexpectedChar(i, "expected a value");
            }
            case 116: {
                this._matchTrue();
                t = JsonToken.VALUE_TRUE;
                break;
            }
            case 102: {
                this._matchFalse();
                t = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                t = JsonToken.VALUE_NULL;
                break;
            }
            case 45: {
                t = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                t = this._parsePosNumber(i);
                break;
            }
            default: {
                t = this._handleOddValue(i);
                break;
            }
        }
        if (inObject) {
            this._nextToken = t;
            return this._currToken;
        }
        return this._currToken = t;
    }
    
    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        final JsonToken t = this._nextToken;
        this._nextToken = null;
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (t == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return this._currToken = t;
    }
    
    @Override
    public void finishToken() throws IOException {
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            this._finishString();
        }
    }
    
    @Override
    public boolean nextFieldName(final SerializableString sstr) throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return false;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int i = this._skipWSOrEnd();
        if (i < 0) {
            this.close();
            this._currToken = null;
            return false;
        }
        this._binaryValue = null;
        if (i == 93 || i == 125) {
            this._closeScope(i);
            return false;
        }
        if (this._parsingContext.expectComma()) {
            i = this._skipComma(i);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (i == 93 || i == 125)) {
                this._closeScope(i);
                return false;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(i);
            return false;
        }
        this._updateNameLocation();
        if (i == 34) {
            final char[] nameChars = sstr.asQuotedChars();
            final int len = nameChars.length;
            if (this._inputPtr + len + 4 < this._inputEnd) {
                final int end = this._inputPtr + len;
                if (this._inputBuffer[end] == '\"') {
                    int offset = 0;
                    int ptr;
                    for (ptr = this._inputPtr; ptr != end; ++ptr) {
                        if (nameChars[offset] != this._inputBuffer[ptr]) {
                            return this._isNextTokenNameMaybe(i, sstr.getValue());
                        }
                        ++offset;
                    }
                    this._parsingContext.setCurrentName(sstr.getValue());
                    this._isNextTokenNameYes(this._skipColonFast(ptr + 1));
                    return true;
                }
            }
        }
        return this._isNextTokenNameMaybe(i, sstr.getValue());
    }
    
    @Override
    public String nextFieldName() throws IOException {
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nextAfterName();
            return null;
        }
        if (this._tokenIncomplete) {
            this._skipString();
        }
        int i = this._skipWSOrEnd();
        if (i < 0) {
            this.close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (i == 93 || i == 125) {
            this._closeScope(i);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            i = this._skipComma(i);
            if ((this._features & ReaderBasedJsonParser.FEAT_MASK_TRAILING_COMMA) != 0x0 && (i == 93 || i == 125)) {
                this._closeScope(i);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            this._updateLocation();
            this._nextTokenNotInObject(i);
            return null;
        }
        this._updateNameLocation();
        final String name = (i == 34) ? this._parseName() : this._handleOddName(i);
        this._parsingContext.setCurrentName(name);
        this._currToken = JsonToken.FIELD_NAME;
        i = this._skipColon();
        this._updateLocation();
        if (i == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return name;
        }
        JsonToken t = null;
        switch (i) {
            case 45: {
                t = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                t = this._parsePosNumber(i);
                break;
            }
            case 102: {
                this._matchFalse();
                t = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                t = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchTrue();
                t = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                t = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                t = JsonToken.START_OBJECT;
                break;
            }
            default: {
                t = this._handleOddValue(i);
                break;
            }
        }
        this._nextToken = t;
        return name;
    }
    
    private final void _isNextTokenNameYes(final int i) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        this._updateLocation();
        switch (i) {
            case 34: {
                this._tokenIncomplete = true;
                this._nextToken = JsonToken.VALUE_STRING;
            }
            case 91: {
                this._nextToken = JsonToken.START_ARRAY;
            }
            case 123: {
                this._nextToken = JsonToken.START_OBJECT;
            }
            case 116: {
                this._matchToken("true", 1);
                this._nextToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchToken("false", 1);
                this._nextToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchToken("null", 1);
                this._nextToken = JsonToken.VALUE_NULL;
            }
            case 45: {
                this._nextToken = this._parseNegNumber();
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                this._nextToken = this._parsePosNumber(i);
            }
            default: {
                this._nextToken = this._handleOddValue(i);
            }
        }
    }
    
    protected boolean _isNextTokenNameMaybe(int i, final String nameToMatch) throws IOException {
        final String name = (i == 34) ? this._parseName() : this._handleOddName(i);
        this._parsingContext.setCurrentName(name);
        this._currToken = JsonToken.FIELD_NAME;
        i = this._skipColon();
        this._updateLocation();
        if (i == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return nameToMatch.equals(name);
        }
        JsonToken t = null;
        switch (i) {
            case 45: {
                t = this._parseNegNumber();
                break;
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                t = this._parsePosNumber(i);
                break;
            }
            case 102: {
                this._matchFalse();
                t = JsonToken.VALUE_FALSE;
                break;
            }
            case 110: {
                this._matchNull();
                t = JsonToken.VALUE_NULL;
                break;
            }
            case 116: {
                this._matchTrue();
                t = JsonToken.VALUE_TRUE;
                break;
            }
            case 91: {
                t = JsonToken.START_ARRAY;
                break;
            }
            case 123: {
                t = JsonToken.START_OBJECT;
                break;
            }
            default: {
                t = this._handleOddValue(i);
                break;
            }
        }
        this._nextToken = t;
        return nameToMatch.equals(name);
    }
    
    private final JsonToken _nextTokenNotInObject(final int i) throws IOException {
        if (i == 34) {
            this._tokenIncomplete = true;
            return this._currToken = JsonToken.VALUE_STRING;
        }
        switch (i) {
            case 91: {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                return this._currToken = JsonToken.START_ARRAY;
            }
            case 123: {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                return this._currToken = JsonToken.START_OBJECT;
            }
            case 116: {
                this._matchToken("true", 1);
                return this._currToken = JsonToken.VALUE_TRUE;
            }
            case 102: {
                this._matchToken("false", 1);
                return this._currToken = JsonToken.VALUE_FALSE;
            }
            case 110: {
                this._matchToken("null", 1);
                return this._currToken = JsonToken.VALUE_NULL;
            }
            case 45: {
                return this._currToken = this._parseNegNumber();
            }
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                return this._currToken = this._parsePosNumber(i);
            }
            case 44:
            case 93: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return this._currToken = JsonToken.VALUE_NULL;
                }
                break;
            }
        }
        return this._currToken = this._handleOddValue(i);
    }
    
    @Override
    public final String nextTextValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_STRING) ? this.getText() : null;
        }
        this._nameCopied = false;
        final JsonToken t = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = t) == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                this._finishString();
            }
            return this._textBuffer.contentsAsString();
        }
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (t == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }
    
    @Override
    public final int nextIntValue(final int defaultValue) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getIntValue() : defaultValue;
        }
        this._nameCopied = false;
        final JsonToken t = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = t) == JsonToken.VALUE_NUMBER_INT) {
            return this.getIntValue();
        }
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (t == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return defaultValue;
    }
    
    @Override
    public final long nextLongValue(final long defaultValue) throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            return (this.nextToken() == JsonToken.VALUE_NUMBER_INT) ? this.getLongValue() : defaultValue;
        }
        this._nameCopied = false;
        final JsonToken t = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = t) == JsonToken.VALUE_NUMBER_INT) {
            return this.getLongValue();
        }
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (t == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return defaultValue;
    }
    
    @Override
    public final Boolean nextBooleanValue() throws IOException {
        if (this._currToken != JsonToken.FIELD_NAME) {
            final JsonToken t = this.nextToken();
            if (t != null) {
                final int id = t.id();
                if (id == 9) {
                    return Boolean.TRUE;
                }
                if (id == 10) {
                    return Boolean.FALSE;
                }
            }
            return null;
        }
        this._nameCopied = false;
        final JsonToken t = this._nextToken;
        this._nextToken = null;
        if ((this._currToken = t) == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (t == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (t == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        }
        else if (t == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        return null;
    }
    
    protected final JsonToken _parsePosNumber(int ch) throws IOException {
        int ptr = this._inputPtr;
        final int startPtr = ptr - 1;
        final int inputLen = this._inputEnd;
        if (ch == 48) {
            return this._parseNumber2(false, startPtr);
        }
        int intLen = 1;
        while (ptr < inputLen) {
            ch = this._inputBuffer[ptr++];
            if (ch >= 48 && ch <= 57) {
                ++intLen;
            }
            else {
                if (ch == 46 || ch == 101 || ch == 69) {
                    this._inputPtr = ptr;
                    return this._parseFloat(ch, startPtr, ptr, false, intLen);
                }
                --ptr;
                this._inputPtr = ptr;
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(ch);
                }
                final int len = ptr - startPtr;
                this._textBuffer.resetWithShared(this._inputBuffer, startPtr, len);
                return this.resetInt(false, intLen);
            }
        }
        this._inputPtr = startPtr;
        return this._parseNumber2(false, startPtr);
    }
    
    private final JsonToken _parseFloat(int ch, final int startPtr, int ptr, final boolean neg, final int intLen) throws IOException {
        final int inputLen = this._inputEnd;
        int fractLen = 0;
        Label_0073: {
            if (ch == 46) {
                while (ptr < inputLen) {
                    ch = this._inputBuffer[ptr++];
                    if (ch >= 48 && ch <= 57) {
                        ++fractLen;
                    }
                    else {
                        if (fractLen == 0) {
                            this.reportUnexpectedNumberChar(ch, "Decimal point not followed by a digit");
                        }
                        break Label_0073;
                    }
                }
                return this._parseNumber2(neg, startPtr);
            }
        }
        int expLen = 0;
        if (ch == 101 || ch == 69) {
            if (ptr >= inputLen) {
                this._inputPtr = startPtr;
                return this._parseNumber2(neg, startPtr);
            }
            ch = this._inputBuffer[ptr++];
            if (ch == 45 || ch == 43) {
                if (ptr >= inputLen) {
                    this._inputPtr = startPtr;
                    return this._parseNumber2(neg, startPtr);
                }
                ch = this._inputBuffer[ptr++];
            }
            while (ch <= 57 && ch >= 48) {
                ++expLen;
                if (ptr >= inputLen) {
                    this._inputPtr = startPtr;
                    return this._parseNumber2(neg, startPtr);
                }
                ch = this._inputBuffer[ptr++];
            }
            if (expLen == 0) {
                this.reportUnexpectedNumberChar(ch, "Exponent indicator not followed by a digit");
            }
        }
        --ptr;
        this._inputPtr = ptr;
        if (this._parsingContext.inRoot()) {
            this._verifyRootSpace(ch);
        }
        final int len = ptr - startPtr;
        this._textBuffer.resetWithShared(this._inputBuffer, startPtr, len);
        return this.resetFloat(neg, intLen, fractLen, expLen);
    }
    
    protected final JsonToken _parseNegNumber() throws IOException {
        int ptr = this._inputPtr;
        final int startPtr = ptr - 1;
        final int inputLen = this._inputEnd;
        if (ptr >= inputLen) {
            return this._parseNumber2(true, startPtr);
        }
        int ch = this._inputBuffer[ptr++];
        if (ch > 57 || ch < 48) {
            this._inputPtr = ptr;
            return this._handleInvalidNumberStart(ch, true);
        }
        if (ch == 48) {
            return this._parseNumber2(true, startPtr);
        }
        int intLen = 1;
        while (ptr < inputLen) {
            ch = this._inputBuffer[ptr++];
            if (ch >= 48 && ch <= 57) {
                ++intLen;
            }
            else {
                if (ch == 46 || ch == 101 || ch == 69) {
                    this._inputPtr = ptr;
                    return this._parseFloat(ch, startPtr, ptr, true, intLen);
                }
                --ptr;
                this._inputPtr = ptr;
                if (this._parsingContext.inRoot()) {
                    this._verifyRootSpace(ch);
                }
                final int len = ptr - startPtr;
                this._textBuffer.resetWithShared(this._inputBuffer, startPtr, len);
                return this.resetInt(true, intLen);
            }
        }
        return this._parseNumber2(true, startPtr);
    }
    
    private final JsonToken _parseNumber2(final boolean neg, final int startPtr) throws IOException {
        this._inputPtr = (neg ? (startPtr + 1) : startPtr);
        char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
        int outPtr = 0;
        if (neg) {
            outBuf[outPtr++] = '-';
        }
        int intLen = 0;
        char c = (this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
        if (c == '0') {
            c = this._verifyNoLeadingZeroes();
        }
        boolean eof = false;
        while (c >= '0' && c <= '9') {
            ++intLen;
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
            outBuf[outPtr++] = c;
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                c = '\0';
                eof = true;
                break;
            }
            c = this._inputBuffer[this._inputPtr++];
        }
        if (intLen == 0) {
            return this._handleInvalidNumberStart(c, neg);
        }
        int fractLen = 0;
        Label_0348: {
            if (c == '.') {
                if (outPtr >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr = 0;
                }
                outBuf[outPtr++] = c;
                while (true) {
                    while (this._inputPtr < this._inputEnd || this._loadMore()) {
                        c = this._inputBuffer[this._inputPtr++];
                        if (c >= '0') {
                            if (c <= '9') {
                                ++fractLen;
                                if (outPtr >= outBuf.length) {
                                    outBuf = this._textBuffer.finishCurrentSegment();
                                    outPtr = 0;
                                }
                                outBuf[outPtr++] = c;
                                continue;
                            }
                        }
                        if (fractLen == 0) {
                            this.reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
                        }
                        break Label_0348;
                    }
                    eof = true;
                    continue;
                }
            }
        }
        int expLen = 0;
        if (c == 'e' || c == 'E') {
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
            outBuf[outPtr++] = c;
            c = ((this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("expected a digit for number exponent"));
            if (c == '-' || c == '+') {
                if (outPtr >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr = 0;
                }
                outBuf[outPtr++] = c;
                c = ((this._inputPtr < this._inputEnd) ? this._inputBuffer[this._inputPtr++] : this.getNextChar("expected a digit for number exponent"));
            }
            while (c <= '9' && c >= '0') {
                ++expLen;
                if (outPtr >= outBuf.length) {
                    outBuf = this._textBuffer.finishCurrentSegment();
                    outPtr = 0;
                }
                outBuf[outPtr++] = c;
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    eof = true;
                    break;
                }
                c = this._inputBuffer[this._inputPtr++];
            }
            if (expLen == 0) {
                this.reportUnexpectedNumberChar(c, "Exponent indicator not followed by a digit");
            }
        }
        if (!eof) {
            --this._inputPtr;
            if (this._parsingContext.inRoot()) {
                this._verifyRootSpace(c);
            }
        }
        this._textBuffer.setCurrentLength(outPtr);
        return this.reset(neg, intLen, fractLen, expLen);
    }
    
    private final char _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr < this._inputEnd) {
            final char ch = this._inputBuffer[this._inputPtr];
            if (ch < '0' || ch > '9') {
                return '0';
            }
        }
        return this._verifyNLZ2();
    }
    
    private char _verifyNLZ2() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return '0';
        }
        char ch = this._inputBuffer[this._inputPtr];
        if (ch < '0' || ch > '9') {
            return '0';
        }
        if (!this.isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            this.reportInvalidNumber("Leading zeroes not allowed");
        }
        ++this._inputPtr;
        if (ch == '0') {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                ch = this._inputBuffer[this._inputPtr];
                if (ch < '0' || ch > '9') {
                    return '0';
                }
                ++this._inputPtr;
                if (ch != '0') {
                    break;
                }
            }
        }
        return ch;
    }
    
    protected JsonToken _handleInvalidNumberStart(int ch, final boolean negative) throws IOException {
        if (ch == 73) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
            }
            ch = this._inputBuffer[this._inputPtr++];
            if (ch == 78) {
                final String match = negative ? "-INF" : "+INF";
                this._matchToken(match, 3);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN(match, negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token '" + match + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
            else if (ch == 110) {
                final String match = negative ? "-Infinity" : "+Infinity";
                this._matchToken(match, 3);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN(match, negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token '" + match + "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
            }
        }
        this.reportUnexpectedNumberChar(ch, "expected digit (0-9) to follow minus sign, for valid numeric value");
        return null;
    }
    
    private final void _verifyRootSpace(final int ch) throws IOException {
        ++this._inputPtr;
        switch (ch) {
            case 9:
            case 32: {}
            case 13: {
                this._skipCR();
            }
            case 10: {
                ++this._currInputRow;
                this._currInputRowStart = this._inputPtr;
            }
            default: {
                this._reportMissingRootWS(ch);
            }
        }
    }
    
    protected final String _parseName() throws IOException {
        int ptr = this._inputPtr;
        int hash = this._hashSeed;
        final int[] codes = ReaderBasedJsonParser._icLatin1;
        while (ptr < this._inputEnd) {
            final int ch = this._inputBuffer[ptr];
            if (ch < codes.length && codes[ch] != 0) {
                if (ch == 34) {
                    final int start = this._inputPtr;
                    this._inputPtr = ptr + 1;
                    return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                }
                break;
            }
            else {
                hash = hash * 33 + ch;
                ++ptr;
            }
        }
        final int start2 = this._inputPtr;
        this._inputPtr = ptr;
        return this._parseName2(start2, hash, 34);
    }
    
    private String _parseName2(final int startPtr, int hash, final int endChar) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, startPtr, this._inputPtr - startPtr);
        char[] outBuf = this._textBuffer.getCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            final int i;
            char c = (char)(i = this._inputBuffer[this._inputPtr++]);
            if (i <= 92) {
                if (i == 92) {
                    c = this._decodeEscaped();
                }
                else if (i <= endChar) {
                    if (i == endChar) {
                        break;
                    }
                    if (i < 32) {
                        this._throwUnquotedSpace(i, "name");
                    }
                }
            }
            hash = hash * 33 + c;
            outBuf[outPtr++] = c;
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
        }
        this._textBuffer.setCurrentLength(outPtr);
        final TextBuffer tb = this._textBuffer;
        final char[] buf = tb.getTextBuffer();
        final int start = tb.getTextOffset();
        final int len = tb.size();
        return this._symbols.findSymbol(buf, start, len, hash);
    }
    
    protected String _handleOddName(final int i) throws IOException {
        if (i == 39 && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return this._parseAposName();
        }
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            this._reportUnexpectedChar(i, "was expecting double-quote to start field name");
        }
        final int[] codes = CharTypes.getInputCodeLatin1JsNames();
        final int maxCode = codes.length;
        boolean firstOk;
        if (i < maxCode) {
            firstOk = (codes[i] == 0);
        }
        else {
            firstOk = Character.isJavaIdentifierPart((char)i);
        }
        if (!firstOk) {
            this._reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int ptr = this._inputPtr;
        int hash = this._hashSeed;
        final int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            do {
                final int ch = this._inputBuffer[ptr];
                if (ch < maxCode) {
                    if (codes[ch] != 0) {
                        final int start = this._inputPtr - 1;
                        this._inputPtr = ptr;
                        return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                    }
                }
                else if (!Character.isJavaIdentifierPart((char)ch)) {
                    final int start = this._inputPtr - 1;
                    this._inputPtr = ptr;
                    return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                }
                hash = hash * 33 + ch;
            } while (++ptr < inputLen);
        }
        final int start2 = this._inputPtr - 1;
        this._inputPtr = ptr;
        return this._handleOddName2(start2, hash, codes);
    }
    
    protected String _parseAposName() throws IOException {
        int ptr = this._inputPtr;
        int hash = this._hashSeed;
        final int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            final int[] codes = ReaderBasedJsonParser._icLatin1;
            final int maxCode = codes.length;
            do {
                final int ch = this._inputBuffer[ptr];
                if (ch == 39) {
                    final int start = this._inputPtr;
                    this._inputPtr = ptr + 1;
                    return this._symbols.findSymbol(this._inputBuffer, start, ptr - start, hash);
                }
                if (ch < maxCode && codes[ch] != 0) {
                    break;
                }
                hash = hash * 33 + ch;
            } while (++ptr < inputLen);
        }
        final int start2 = this._inputPtr;
        this._inputPtr = ptr;
        return this._parseName2(start2, hash, 39);
    }
    
    protected JsonToken _handleOddValue(final int i) throws IOException {
        switch (i) {
            case 39: {
                if (this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
                    return this._handleApos();
                }
                break;
            }
            case 93: {
                if (!this._parsingContext.inArray()) {
                    break;
                }
            }
            case 44: {
                if (this.isEnabled(Feature.ALLOW_MISSING_VALUES)) {
                    --this._inputPtr;
                    return JsonToken.VALUE_NULL;
                }
                break;
            }
            case 78: {
                this._matchToken("NaN", 1);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("NaN", Double.NaN);
                }
                this._reportError("Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break;
            }
            case 73: {
                this._matchToken("Infinity", 1);
                if (this.isEnabled(Feature.ALLOW_NON_NUMERIC_NUMBERS)) {
                    return this.resetAsNaN("Infinity", Double.POSITIVE_INFINITY);
                }
                this._reportError("Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow");
                break;
            }
            case 43: {
                if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                    this._reportInvalidEOFInValue(JsonToken.VALUE_NUMBER_INT);
                }
                return this._handleInvalidNumberStart(this._inputBuffer[this._inputPtr++], false);
            }
        }
        if (Character.isJavaIdentifierStart(i)) {
            this._reportInvalidToken("" + (char)i, "('true', 'false' or 'null')");
        }
        this._reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        return null;
    }
    
    protected JsonToken _handleApos() throws IOException {
        char[] outBuf = this._textBuffer.emptyAndGetCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            final int i;
            char c = (char)(i = this._inputBuffer[this._inputPtr++]);
            if (i <= 92) {
                if (i == 92) {
                    c = this._decodeEscaped();
                }
                else if (i <= 39) {
                    if (i == 39) {
                        break;
                    }
                    if (i < 32) {
                        this._throwUnquotedSpace(i, "string value");
                    }
                }
            }
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
            outBuf[outPtr++] = c;
        }
        this._textBuffer.setCurrentLength(outPtr);
        return JsonToken.VALUE_STRING;
    }
    
    private String _handleOddName2(final int startPtr, int hash, final int[] codes) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, startPtr, this._inputPtr - startPtr);
        char[] outBuf = this._textBuffer.getCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        final int maxCode = codes.length;
        while (true) {
            while (this._inputPtr < this._inputEnd || this._loadMore()) {
                final int i;
                final char c = (char)(i = this._inputBuffer[this._inputPtr]);
                if (i <= maxCode) {
                    if (codes[i] != 0) {
                        break;
                    }
                }
                else if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
                ++this._inputPtr;
                hash = hash * 33 + i;
                outBuf[outPtr++] = c;
                if (outPtr < outBuf.length) {
                    continue;
                }
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
                continue;
                this._textBuffer.setCurrentLength(outPtr);
                final TextBuffer tb = this._textBuffer;
                final char[] buf = tb.getTextBuffer();
                final int start = tb.getTextOffset();
                final int len = tb.size();
                return this._symbols.findSymbol(buf, start, len, hash);
            }
            continue;
        }
    }
    
    @Override
    protected final void _finishString() throws IOException {
        int ptr = this._inputPtr;
        final int inputLen = this._inputEnd;
        if (ptr < inputLen) {
            final int[] codes = ReaderBasedJsonParser._icLatin1;
            final int maxCode = codes.length;
            do {
                final int ch = this._inputBuffer[ptr];
                if (ch < maxCode && codes[ch] != 0) {
                    if (ch == 34) {
                        this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, ptr - this._inputPtr);
                        this._inputPtr = ptr + 1;
                        return;
                    }
                    break;
                }
            } while (++ptr < inputLen);
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, ptr - this._inputPtr);
        this._inputPtr = ptr;
        this._finishString2();
    }
    
    protected void _finishString2() throws IOException {
        char[] outBuf = this._textBuffer.getCurrentSegment();
        int outPtr = this._textBuffer.getCurrentSegmentSize();
        final int[] codes = ReaderBasedJsonParser._icLatin1;
        final int maxCode = codes.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            final int i;
            char c = (char)(i = this._inputBuffer[this._inputPtr++]);
            if (i < maxCode && codes[i] != 0) {
                if (i == 34) {
                    break;
                }
                if (i == 92) {
                    c = this._decodeEscaped();
                }
                else if (i < 32) {
                    this._throwUnquotedSpace(i, "string value");
                }
            }
            if (outPtr >= outBuf.length) {
                outBuf = this._textBuffer.finishCurrentSegment();
                outPtr = 0;
            }
            outBuf[outPtr++] = c;
        }
        this._textBuffer.setCurrentLength(outPtr);
    }
    
    protected final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int inPtr = this._inputPtr;
        int inLen = this._inputEnd;
        final char[] inBuf = this._inputBuffer;
        while (true) {
            if (inPtr >= inLen) {
                this._inputPtr = inPtr;
                if (!this._loadMore()) {
                    this._reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
                }
                inPtr = this._inputPtr;
                inLen = this._inputEnd;
            }
            final int i;
            final char c = (char)(i = inBuf[inPtr++]);
            if (i <= 92) {
                if (i == 92) {
                    this._inputPtr = inPtr;
                    this._decodeEscaped();
                    inPtr = this._inputPtr;
                    inLen = this._inputEnd;
                }
                else {
                    if (i > 34) {
                        continue;
                    }
                    if (i == 34) {
                        break;
                    }
                    if (i >= 32) {
                        continue;
                    }
                    this._inputPtr = inPtr;
                    this._throwUnquotedSpace(i, "string value");
                }
            }
        }
        this._inputPtr = inPtr;
    }
    
    protected final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || this._loadMore()) && this._inputBuffer[this._inputPtr] == '\n') {
            ++this._inputPtr;
        }
        ++this._currInputRow;
        this._currInputRowStart = this._inputPtr;
    }
    
    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return this._skipColon2(false);
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c == ':') {
            int i = this._inputBuffer[++this._inputPtr];
            if (i <= 32) {
                if (i == 32 || i == 9) {
                    i = this._inputBuffer[++this._inputPtr];
                    if (i > 32) {
                        if (i == 47 || i == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return i;
                    }
                }
                return this._skipColon2(true);
            }
            if (i == 47 || i == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return i;
        }
        else {
            if (c == ' ' || c == '\t') {
                c = this._inputBuffer[++this._inputPtr];
            }
            if (c != ':') {
                return this._skipColon2(false);
            }
            int i = this._inputBuffer[++this._inputPtr];
            if (i <= 32) {
                if (i == 32 || i == 9) {
                    i = this._inputBuffer[++this._inputPtr];
                    if (i > 32) {
                        if (i == 47 || i == 35) {
                            return this._skipColon2(true);
                        }
                        ++this._inputPtr;
                        return i;
                    }
                }
                return this._skipColon2(true);
            }
            if (i == 47 || i == 35) {
                return this._skipColon2(true);
            }
            ++this._inputPtr;
            return i;
        }
    }
    
    private final int _skipColon2(boolean gotColon) throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int i = this._inputBuffer[this._inputPtr++];
            if (i > 32) {
                if (i == 47) {
                    this._skipComment();
                }
                else {
                    if (i == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    if (gotColon) {
                        return i;
                    }
                    if (i != 58) {
                        this._reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
                    }
                    gotColon = true;
                }
            }
            else {
                if (i >= 32) {
                    continue;
                }
                if (i == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (i == 13) {
                    this._skipCR();
                }
                else {
                    if (i == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(i);
                }
            }
        }
        this._reportInvalidEOF(" within/between " + this._parsingContext.typeDesc() + " entries", null);
        return -1;
    }
    
    private final int _skipColonFast(int ptr) throws IOException {
        int i = this._inputBuffer[ptr++];
        if (i == 58) {
            i = this._inputBuffer[ptr++];
            if (i > 32) {
                if (i != 47 && i != 35) {
                    this._inputPtr = ptr;
                    return i;
                }
            }
            else if (i == 32 || i == 9) {
                i = this._inputBuffer[ptr++];
                if (i > 32 && i != 47 && i != 35) {
                    this._inputPtr = ptr;
                    return i;
                }
            }
            this._inputPtr = ptr - 1;
            return this._skipColon2(true);
        }
        if (i == 32 || i == 9) {
            i = this._inputBuffer[ptr++];
        }
        final boolean gotColon = i == 58;
        if (gotColon) {
            i = this._inputBuffer[ptr++];
            if (i > 32) {
                if (i != 47 && i != 35) {
                    this._inputPtr = ptr;
                    return i;
                }
            }
            else if (i == 32 || i == 9) {
                i = this._inputBuffer[ptr++];
                if (i > 32 && i != 47 && i != 35) {
                    this._inputPtr = ptr;
                    return i;
                }
            }
        }
        this._inputPtr = ptr - 1;
        return this._skipColon2(gotColon);
    }
    
    private final int _skipComma(int i) throws IOException {
        if (i != 44) {
            this._reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        while (this._inputPtr < this._inputEnd) {
            i = this._inputBuffer[this._inputPtr++];
            if (i > 32) {
                if (i == 47 || i == 35) {
                    --this._inputPtr;
                    return this._skipAfterComma2();
                }
                return i;
            }
            else {
                if (i >= 32) {
                    continue;
                }
                if (i == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (i == 13) {
                    this._skipCR();
                }
                else {
                    if (i == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(i);
                }
            }
        }
        return this._skipAfterComma2();
    }
    
    private final int _skipAfterComma2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int i = this._inputBuffer[this._inputPtr++];
            if (i > 32) {
                if (i == 47) {
                    this._skipComment();
                }
                else {
                    if (i == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    return i;
                }
            }
            else {
                if (i >= 32) {
                    continue;
                }
                if (i == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (i == 13) {
                    this._skipCR();
                }
                else {
                    if (i == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(i);
                }
            }
        }
        throw this._constructError("Unexpected end-of-input within/between " + this._parsingContext.typeDesc() + " entries");
    }
    
    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return this._eofAsNextChar();
        }
        int i = this._inputBuffer[this._inputPtr++];
        if (i <= 32) {
            if (i != 32) {
                if (i == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (i == 13) {
                    this._skipCR();
                }
                else if (i != 9) {
                    this._throwInvalidSpace(i);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                i = this._inputBuffer[this._inputPtr++];
                if (i > 32) {
                    if (i == 47 || i == 35) {
                        --this._inputPtr;
                        return this._skipWSOrEnd2();
                    }
                    return i;
                }
                else {
                    if (i == 32) {
                        continue;
                    }
                    if (i == 10) {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (i == 13) {
                        this._skipCR();
                    }
                    else {
                        if (i == 9) {
                            continue;
                        }
                        this._throwInvalidSpace(i);
                    }
                }
            }
            return this._skipWSOrEnd2();
        }
        if (i == 47 || i == 35) {
            --this._inputPtr;
            return this._skipWSOrEnd2();
        }
        return i;
    }
    
    private int _skipWSOrEnd2() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int i = this._inputBuffer[this._inputPtr++];
            if (i > 32) {
                if (i == 47) {
                    this._skipComment();
                }
                else {
                    if (i == 35 && this._skipYAMLComment()) {
                        continue;
                    }
                    return i;
                }
            }
            else {
                if (i == 32) {
                    continue;
                }
                if (i == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                }
                else if (i == 13) {
                    this._skipCR();
                }
                else {
                    if (i == 9) {
                        continue;
                    }
                    this._throwInvalidSpace(i);
                }
            }
        }
        return this._eofAsNextChar();
    }
    
    private void _skipComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_COMMENTS)) {
            this._reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in a comment", null);
        }
        final char c = this._inputBuffer[this._inputPtr++];
        if (c == '/') {
            this._skipLine();
        }
        else if (c == '*') {
            this._skipCComment();
        }
        else {
            this._reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
        }
    }
    
    private void _skipCComment() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int i = this._inputBuffer[this._inputPtr++];
            if (i <= 42) {
                if (i == 42) {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        break;
                    }
                    if (this._inputBuffer[this._inputPtr] == '/') {
                        ++this._inputPtr;
                        return;
                    }
                    continue;
                }
                else {
                    if (i >= 32) {
                        continue;
                    }
                    if (i == 10) {
                        ++this._currInputRow;
                        this._currInputRowStart = this._inputPtr;
                    }
                    else if (i == 13) {
                        this._skipCR();
                    }
                    else {
                        if (i == 9) {
                            continue;
                        }
                        this._throwInvalidSpace(i);
                    }
                }
            }
        }
        this._reportInvalidEOF(" in a comment", null);
    }
    
    private boolean _skipYAMLComment() throws IOException {
        if (!this.isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        this._skipLine();
        return true;
    }
    
    private void _skipLine() throws IOException {
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final int i = this._inputBuffer[this._inputPtr++];
            if (i < 32) {
                if (i == 10) {
                    ++this._currInputRow;
                    this._currInputRowStart = this._inputPtr;
                    break;
                }
                if (i == 13) {
                    this._skipCR();
                    break;
                }
                if (i == 9) {
                    continue;
                }
                this._throwInvalidSpace(i);
            }
        }
    }
    
    @Override
    protected char _decodeEscaped() throws IOException {
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
        }
        final char c = this._inputBuffer[this._inputPtr++];
        switch (c) {
            case 'b': {
                return '\b';
            }
            case 't': {
                return '\t';
            }
            case 'n': {
                return '\n';
            }
            case 'f': {
                return '\f';
            }
            case 'r': {
                return '\r';
            }
            case '\"':
            case '/':
            case '\\': {
                return c;
            }
            case 'u': {
                int value = 0;
                for (int i = 0; i < 4; ++i) {
                    if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
                        this._reportInvalidEOF(" in character escape sequence", JsonToken.VALUE_STRING);
                    }
                    final int ch = this._inputBuffer[this._inputPtr++];
                    final int digit = CharTypes.charToHex(ch);
                    if (digit < 0) {
                        this._reportUnexpectedChar(ch, "expected a hex-digit for character escape sequence");
                    }
                    value = (value << 4 | digit);
                }
                return (char)value;
            }
            default: {
                return this._handleUnrecognizedCharacterEscape(c);
            }
        }
    }
    
    private final void _matchTrue() throws IOException {
        int ptr = this._inputPtr;
        if (ptr + 3 < this._inputEnd) {
            final char[] b = this._inputBuffer;
            if (b[ptr] == 'r' && b[++ptr] == 'u' && b[++ptr] == 'e') {
                final char c = b[++ptr];
                if (c < '0' || c == ']' || c == '}') {
                    this._inputPtr = ptr;
                    return;
                }
            }
        }
        this._matchToken("true", 1);
    }
    
    private final void _matchFalse() throws IOException {
        int ptr = this._inputPtr;
        if (ptr + 4 < this._inputEnd) {
            final char[] b = this._inputBuffer;
            if (b[ptr] == 'a' && b[++ptr] == 'l' && b[++ptr] == 's' && b[++ptr] == 'e') {
                final char c = b[++ptr];
                if (c < '0' || c == ']' || c == '}') {
                    this._inputPtr = ptr;
                    return;
                }
            }
        }
        this._matchToken("false", 1);
    }
    
    private final void _matchNull() throws IOException {
        int ptr = this._inputPtr;
        if (ptr + 3 < this._inputEnd) {
            final char[] b = this._inputBuffer;
            if (b[ptr] == 'u' && b[++ptr] == 'l' && b[++ptr] == 'l') {
                final char c = b[++ptr];
                if (c < '0' || c == ']' || c == '}') {
                    this._inputPtr = ptr;
                    return;
                }
            }
        }
        this._matchToken("null", 1);
    }
    
    protected final void _matchToken(final String matchStr, int i) throws IOException {
        final int len = matchStr.length();
        if (this._inputPtr + len >= this._inputEnd) {
            this._matchToken2(matchStr, i);
            return;
        }
        do {
            if (this._inputBuffer[this._inputPtr] != matchStr.charAt(i)) {
                this._reportInvalidToken(matchStr.substring(0, i));
            }
            ++this._inputPtr;
        } while (++i < len);
        final int ch = this._inputBuffer[this._inputPtr];
        if (ch >= 48 && ch != 93 && ch != 125) {
            this._checkMatchEnd(matchStr, i, ch);
        }
    }
    
    private final void _matchToken2(final String matchStr, int i) throws IOException {
        final int len = matchStr.length();
        do {
            if ((this._inputPtr >= this._inputEnd && !this._loadMore()) || this._inputBuffer[this._inputPtr] != matchStr.charAt(i)) {
                this._reportInvalidToken(matchStr.substring(0, i));
            }
            ++this._inputPtr;
        } while (++i < len);
        if (this._inputPtr >= this._inputEnd && !this._loadMore()) {
            return;
        }
        final int ch = this._inputBuffer[this._inputPtr];
        if (ch >= 48 && ch != 93 && ch != 125) {
            this._checkMatchEnd(matchStr, i, ch);
        }
    }
    
    private final void _checkMatchEnd(final String matchStr, final int i, final int c) throws IOException {
        final char ch = (char)c;
        if (Character.isJavaIdentifierPart(ch)) {
            this._reportInvalidToken(matchStr.substring(0, i));
        }
    }
    
    protected byte[] _decodeBase64(final Base64Variant b64variant) throws IOException {
        final ByteArrayBuilder builder = this._getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                this._loadMoreGuaranteed();
            }
            char ch = this._inputBuffer[this._inputPtr++];
            if (ch > ' ') {
                int bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (ch == '\"') {
                        return builder.toByteArray();
                    }
                    bits = this._decodeBase64Escape(b64variant, ch, 0);
                    if (bits < 0) {
                        continue;
                    }
                }
                int decodedData = bits;
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                ch = this._inputBuffer[this._inputPtr++];
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    bits = this._decodeBase64Escape(b64variant, ch, 1);
                }
                decodedData = (decodedData << 6 | bits);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                ch = this._inputBuffer[this._inputPtr++];
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (bits != -2) {
                        if (ch == '\"' && !b64variant.usesPadding()) {
                            decodedData >>= 4;
                            builder.append(decodedData);
                            return builder.toByteArray();
                        }
                        bits = this._decodeBase64Escape(b64variant, ch, 2);
                    }
                    if (bits == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            this._loadMoreGuaranteed();
                        }
                        ch = this._inputBuffer[this._inputPtr++];
                        if (!b64variant.usesPaddingChar(ch)) {
                            throw this.reportInvalidBase64Char(b64variant, ch, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                        }
                        decodedData >>= 4;
                        builder.append(decodedData);
                        continue;
                    }
                }
                decodedData = (decodedData << 6 | bits);
                if (this._inputPtr >= this._inputEnd) {
                    this._loadMoreGuaranteed();
                }
                ch = this._inputBuffer[this._inputPtr++];
                bits = b64variant.decodeBase64Char(ch);
                if (bits < 0) {
                    if (bits != -2) {
                        if (ch == '\"' && !b64variant.usesPadding()) {
                            decodedData >>= 2;
                            builder.appendTwoBytes(decodedData);
                            return builder.toByteArray();
                        }
                        bits = this._decodeBase64Escape(b64variant, ch, 3);
                    }
                    if (bits == -2) {
                        decodedData >>= 2;
                        builder.appendTwoBytes(decodedData);
                        continue;
                    }
                }
                decodedData = (decodedData << 6 | bits);
                builder.appendThreeBytes(decodedData);
            }
        }
    }
    
    @Override
    public JsonLocation getTokenLocation() {
        if (this._currToken == JsonToken.FIELD_NAME) {
            final long total = this._currInputProcessed + (this._nameStartOffset - 1L);
            return new JsonLocation(this._getSourceReference(), -1L, total, this._nameStartRow, this._nameStartCol);
        }
        return new JsonLocation(this._getSourceReference(), -1L, this._tokenInputTotal - 1L, this._tokenInputRow, this._tokenInputCol);
    }
    
    @Override
    public JsonLocation getCurrentLocation() {
        final int col = this._inputPtr - this._currInputRowStart + 1;
        return new JsonLocation(this._getSourceReference(), -1L, this._currInputProcessed + this._inputPtr, this._currInputRow, col);
    }
    
    private final void _updateLocation() {
        final int ptr = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + ptr;
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = ptr - this._currInputRowStart;
    }
    
    private final void _updateNameLocation() {
        final int ptr = this._inputPtr;
        this._nameStartOffset = ptr;
        this._nameStartRow = this._currInputRow;
        this._nameStartCol = ptr - this._currInputRowStart;
    }
    
    protected void _reportInvalidToken(final String matchedPart) throws IOException {
        this._reportInvalidToken(matchedPart, "'null', 'true', 'false' or NaN");
    }
    
    protected void _reportInvalidToken(final String matchedPart, final String msg) throws IOException {
        final StringBuilder sb = new StringBuilder(matchedPart);
        while (this._inputPtr < this._inputEnd || this._loadMore()) {
            final char c = this._inputBuffer[this._inputPtr];
            if (!Character.isJavaIdentifierPart(c)) {
                break;
            }
            ++this._inputPtr;
            sb.append(c);
            if (sb.length() >= 256) {
                sb.append("...");
                break;
            }
        }
        this._reportError("Unrecognized token '%s': was expecting %s", sb, msg);
    }
    
    private void _closeScope(final int i) throws JsonParseException {
        if (i == 93) {
            this._updateLocation();
            if (!this._parsingContext.inArray()) {
                this._reportMismatchedEndMarker(i, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (i == 125) {
            this._updateLocation();
            if (!this._parsingContext.inObject()) {
                this._reportMismatchedEndMarker(i, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
    
    static {
        FEAT_MASK_TRAILING_COMMA = Feature.ALLOW_TRAILING_COMMA.getMask();
        _icLatin1 = CharTypes.getInputCodeLatin1();
    }
}
