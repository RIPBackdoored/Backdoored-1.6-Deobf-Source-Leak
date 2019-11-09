package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.sym.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.format.*;
import java.io.*;

public final class ByteSourceJsonBootstrapper
{
    public static final byte UTF8_BOM_1 = -17;
    public static final byte UTF8_BOM_2 = -69;
    public static final byte UTF8_BOM_3 = -65;
    private final IOContext _context;
    private final InputStream _in;
    private final byte[] _inputBuffer;
    private int _inputPtr;
    private int _inputEnd;
    private final boolean _bufferRecyclable;
    private boolean _bigEndian;
    private int _bytesPerChar;
    
    public ByteSourceJsonBootstrapper(final IOContext ctxt, final InputStream in) {
        super();
        this._bigEndian = true;
        this._context = ctxt;
        this._in = in;
        this._inputBuffer = ctxt.allocReadIOBuffer();
        final int n = 0;
        this._inputPtr = n;
        this._inputEnd = n;
        this._bufferRecyclable = true;
    }
    
    public ByteSourceJsonBootstrapper(final IOContext ctxt, final byte[] inputBuffer, final int inputStart, final int inputLen) {
        super();
        this._bigEndian = true;
        this._context = ctxt;
        this._in = null;
        this._inputBuffer = inputBuffer;
        this._inputPtr = inputStart;
        this._inputEnd = inputStart + inputLen;
        this._bufferRecyclable = false;
    }
    
    public JsonEncoding detectEncoding() throws IOException {
        boolean foundEncoding = false;
        if (this.ensureLoaded(4)) {
            final int quad = this._inputBuffer[this._inputPtr] << 24 | (this._inputBuffer[this._inputPtr + 1] & 0xFF) << 16 | (this._inputBuffer[this._inputPtr + 2] & 0xFF) << 8 | (this._inputBuffer[this._inputPtr + 3] & 0xFF);
            if (this.handleBOM(quad)) {
                foundEncoding = true;
            }
            else if (this.checkUTF32(quad)) {
                foundEncoding = true;
            }
            else if (this.checkUTF16(quad >>> 16)) {
                foundEncoding = true;
            }
        }
        else if (this.ensureLoaded(2)) {
            final int i16 = (this._inputBuffer[this._inputPtr] & 0xFF) << 8 | (this._inputBuffer[this._inputPtr + 1] & 0xFF);
            if (this.checkUTF16(i16)) {
                foundEncoding = true;
            }
        }
        JsonEncoding enc = null;
        if (!foundEncoding) {
            enc = JsonEncoding.UTF8;
        }
        else {
            switch (this._bytesPerChar) {
                case 1: {
                    enc = JsonEncoding.UTF8;
                    break;
                }
                case 2: {
                    enc = (this._bigEndian ? JsonEncoding.UTF16_BE : JsonEncoding.UTF16_LE);
                    break;
                }
                case 4: {
                    enc = (this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE);
                    break;
                }
                default: {
                    throw new RuntimeException("Internal error");
                }
            }
        }
        this._context.setEncoding(enc);
        return enc;
    }
    
    public static int skipUTF8BOM(final DataInput input) throws IOException {
        int b = input.readUnsignedByte();
        if (b != 239) {
            return b;
        }
        b = input.readUnsignedByte();
        if (b != 187) {
            throw new IOException("Unexpected byte 0x" + Integer.toHexString(b) + " following 0xEF; should get 0xBB as part of UTF-8 BOM");
        }
        b = input.readUnsignedByte();
        if (b != 191) {
            throw new IOException("Unexpected byte 0x" + Integer.toHexString(b) + " following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
        }
        return input.readUnsignedByte();
    }
    
    public Reader constructReader() throws IOException {
        final JsonEncoding enc = this._context.getEncoding();
        switch (enc.bits()) {
            case 8:
            case 16: {
                InputStream in = this._in;
                if (in == null) {
                    in = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
                }
                else if (this._inputPtr < this._inputEnd) {
                    in = new MergedStream(this._context, in, this._inputBuffer, this._inputPtr, this._inputEnd);
                }
                return new InputStreamReader(in, enc.getJavaName());
            }
            case 32: {
                return new UTF32Reader(this._context, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, this._context.getEncoding().isBigEndian());
            }
            default: {
                throw new RuntimeException("Internal error");
            }
        }
    }
    
    public JsonParser constructParser(final int parserFeatures, final ObjectCodec codec, final ByteQuadsCanonicalizer rootByteSymbols, final CharsToNameCanonicalizer rootCharSymbols, final int factoryFeatures) throws IOException {
        final JsonEncoding enc = this.detectEncoding();
        if (enc == JsonEncoding.UTF8 && JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(factoryFeatures)) {
            final ByteQuadsCanonicalizer can = rootByteSymbols.makeChild(factoryFeatures);
            return new UTF8StreamJsonParser(this._context, parserFeatures, this._in, codec, can, this._inputBuffer, this._inputPtr, this._inputEnd, this._bufferRecyclable);
        }
        return new ReaderBasedJsonParser(this._context, parserFeatures, this.constructReader(), codec, rootCharSymbols.makeChild(factoryFeatures));
    }
    
    public static MatchStrength hasJSONFormat(final InputAccessor acc) throws IOException {
        if (!acc.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
        }
        byte b = acc.nextByte();
        if (b == -17) {
            if (!acc.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (acc.nextByte() != -69) {
                return MatchStrength.NO_MATCH;
            }
            if (!acc.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (acc.nextByte() != -65) {
                return MatchStrength.NO_MATCH;
            }
            if (!acc.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            b = acc.nextByte();
        }
        int ch = skipSpace(acc, b);
        if (ch < 0) {
            return MatchStrength.INCONCLUSIVE;
        }
        if (ch == 123) {
            ch = skipSpace(acc);
            if (ch < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (ch == 34 || ch == 125) {
                return MatchStrength.SOLID_MATCH;
            }
            return MatchStrength.NO_MATCH;
        }
        else if (ch == 91) {
            ch = skipSpace(acc);
            if (ch < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (ch == 93 || ch == 91) {
                return MatchStrength.SOLID_MATCH;
            }
            return MatchStrength.SOLID_MATCH;
        }
        else {
            final MatchStrength strength = MatchStrength.WEAK_MATCH;
            if (ch == 34) {
                return strength;
            }
            if (ch <= 57 && ch >= 48) {
                return strength;
            }
            if (ch == 45) {
                ch = skipSpace(acc);
                if (ch < 0) {
                    return MatchStrength.INCONCLUSIVE;
                }
                return (ch <= 57 && ch >= 48) ? strength : MatchStrength.NO_MATCH;
            }
            else {
                if (ch == 110) {
                    return tryMatch(acc, "ull", strength);
                }
                if (ch == 116) {
                    return tryMatch(acc, "rue", strength);
                }
                if (ch == 102) {
                    return tryMatch(acc, "alse", strength);
                }
                return MatchStrength.NO_MATCH;
            }
        }
    }
    
    private static MatchStrength tryMatch(final InputAccessor acc, final String matchStr, final MatchStrength fullMatchStrength) throws IOException {
        for (int i = 0, len = matchStr.length(); i < len; ++i) {
            if (!acc.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (acc.nextByte() != matchStr.charAt(i)) {
                return MatchStrength.NO_MATCH;
            }
        }
        return fullMatchStrength;
    }
    
    private static int skipSpace(final InputAccessor acc) throws IOException {
        if (!acc.hasMoreBytes()) {
            return -1;
        }
        return skipSpace(acc, acc.nextByte());
    }
    
    private static int skipSpace(final InputAccessor acc, byte b) throws IOException {
        while (true) {
            final int ch = b & 0xFF;
            if (ch != 32 && ch != 13 && ch != 10 && ch != 9) {
                return ch;
            }
            if (!acc.hasMoreBytes()) {
                return -1;
            }
            b = acc.nextByte();
        }
    }
    
    private boolean handleBOM(final int quad) throws IOException {
        switch (quad) {
            case 65279: {
                this._bigEndian = true;
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                return true;
            }
            case -131072: {
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                this._bigEndian = false;
                return true;
            }
            case 65534: {
                this.reportWeirdUCS4("2143");
                break;
            }
            case -16842752: {
                this.reportWeirdUCS4("3412");
                break;
            }
        }
        final int msw = quad >>> 16;
        if (msw == 65279) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            return this._bigEndian = true;
        }
        if (msw == 65534) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            this._bigEndian = false;
            return true;
        }
        if (quad >>> 8 == 15711167) {
            this._inputPtr += 3;
            this._bytesPerChar = 1;
            return this._bigEndian = true;
        }
        return false;
    }
    
    private boolean checkUTF32(final int quad) throws IOException {
        if (quad >> 8 == 0) {
            this._bigEndian = true;
        }
        else if ((quad & 0xFFFFFF) == 0x0) {
            this._bigEndian = false;
        }
        else if ((quad & 0xFF00FFFF) == 0x0) {
            this.reportWeirdUCS4("3412");
        }
        else {
            if ((quad & 0xFFFF00FF) != 0x0) {
                return false;
            }
            this.reportWeirdUCS4("2143");
        }
        this._bytesPerChar = 4;
        return true;
    }
    
    private boolean checkUTF16(final int i16) {
        if ((i16 & 0xFF00) == 0x0) {
            this._bigEndian = true;
        }
        else {
            if ((i16 & 0xFF) != 0x0) {
                return false;
            }
            this._bigEndian = false;
        }
        this._bytesPerChar = 2;
        return true;
    }
    
    private void reportWeirdUCS4(final String type) throws IOException {
        throw new CharConversionException("Unsupported UCS-4 endianness (" + type + ") detected");
    }
    
    protected boolean ensureLoaded(final int minimum) throws IOException {
        int count;
        for (int gotten = this._inputEnd - this._inputPtr; gotten < minimum; gotten += count) {
            if (this._in == null) {
                count = -1;
            }
            else {
                count = this._in.read(this._inputBuffer, this._inputEnd, this._inputBuffer.length - this._inputEnd);
            }
            if (count < 1) {
                return false;
            }
            this._inputEnd += count;
        }
        return true;
    }
}
