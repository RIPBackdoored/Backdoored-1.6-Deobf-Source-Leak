package com.fasterxml.jackson.core.base;

import java.math.*;
import java.io.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.util.*;

public abstract class ParserMinimalBase extends JsonParser
{
    protected static final int INT_TAB = 9;
    protected static final int INT_LF = 10;
    protected static final int INT_CR = 13;
    protected static final int INT_SPACE = 32;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_APOS = 39;
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_SLASH = 47;
    protected static final int INT_ASTERISK = 42;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_HASH = 35;
    protected static final int INT_0 = 48;
    protected static final int INT_9 = 57;
    protected static final int INT_MINUS = 45;
    protected static final int INT_PLUS = 43;
    protected static final int INT_PERIOD = 46;
    protected static final int INT_e = 101;
    protected static final int INT_E = 69;
    protected static final char CHAR_NULL = '\0';
    protected static final byte[] NO_BYTES;
    protected static final int[] NO_INTS;
    protected static final int NR_UNKNOWN = 0;
    protected static final int NR_INT = 1;
    protected static final int NR_LONG = 2;
    protected static final int NR_BIGINT = 4;
    protected static final int NR_DOUBLE = 8;
    protected static final int NR_BIGDECIMAL = 16;
    protected static final int NR_FLOAT = 32;
    protected static final BigInteger BI_MIN_INT;
    protected static final BigInteger BI_MAX_INT;
    protected static final BigInteger BI_MIN_LONG;
    protected static final BigInteger BI_MAX_LONG;
    protected static final BigDecimal BD_MIN_LONG;
    protected static final BigDecimal BD_MAX_LONG;
    protected static final BigDecimal BD_MIN_INT;
    protected static final BigDecimal BD_MAX_INT;
    protected static final long MIN_INT_L = -2147483648L;
    protected static final long MAX_INT_L = 2147483647L;
    protected static final double MIN_LONG_D = -9.223372036854776E18;
    protected static final double MAX_LONG_D = 9.223372036854776E18;
    protected static final double MIN_INT_D = -2.147483648E9;
    protected static final double MAX_INT_D = 2.147483647E9;
    protected static final int MAX_ERROR_TOKEN_LENGTH = 256;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;
    
    protected ParserMinimalBase() {
        super();
    }
    
    protected ParserMinimalBase(final int features) {
        super(features);
    }
    
    @Override
    public abstract JsonToken nextToken() throws IOException;
    
    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }
    
    @Override
    public int currentTokenId() {
        final JsonToken t = this._currToken;
        return (t == null) ? 0 : t.id();
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }
    
    @Override
    public int getCurrentTokenId() {
        final JsonToken t = this._currToken;
        return (t == null) ? 0 : t.id();
    }
    
    @Override
    public boolean hasCurrentToken() {
        return this._currToken != null;
    }
    
    @Override
    public boolean hasTokenId(final int id) {
        final JsonToken t = this._currToken;
        if (t == null) {
            return 0 == id;
        }
        return t.id() == id;
    }
    
    @Override
    public boolean hasToken(final JsonToken t) {
        return this._currToken == t;
    }
    
    @Override
    public boolean isExpectedStartArrayToken() {
        return this._currToken == JsonToken.START_ARRAY;
    }
    
    @Override
    public boolean isExpectedStartObjectToken() {
        return this._currToken == JsonToken.START_OBJECT;
    }
    
    @Override
    public JsonToken nextValue() throws IOException {
        JsonToken t = this.nextToken();
        if (t == JsonToken.FIELD_NAME) {
            t = this.nextToken();
        }
        return t;
    }
    
    @Override
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int open = 1;
        while (true) {
            final JsonToken t = this.nextToken();
            if (t == null) {
                this._handleEOF();
                return this;
            }
            if (t.isStructStart()) {
                ++open;
            }
            else if (t.isStructEnd()) {
                if (--open == 0) {
                    return this;
                }
                continue;
            }
            else {
                if (t != JsonToken.NOT_AVAILABLE) {
                    continue;
                }
                this._reportError("Not enough content available for `skipChildren()`: non-blocking parser? (%s)", this.getClass().getName());
            }
        }
    }
    
    protected abstract void _handleEOF() throws JsonParseException;
    
    @Override
    public abstract String getCurrentName() throws IOException;
    
    @Override
    public abstract void close() throws IOException;
    
    @Override
    public abstract boolean isClosed();
    
    @Override
    public abstract JsonStreamContext getParsingContext();
    
    @Override
    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }
    
    @Override
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }
    
    @Override
    public abstract void overrideCurrentName(final String p0);
    
    @Override
    public abstract String getText() throws IOException;
    
    @Override
    public abstract char[] getTextCharacters() throws IOException;
    
    @Override
    public abstract boolean hasTextCharacters();
    
    @Override
    public abstract int getTextLength() throws IOException;
    
    @Override
    public abstract int getTextOffset() throws IOException;
    
    @Override
    public abstract byte[] getBinaryValue(final Base64Variant p0) throws IOException;
    
    @Override
    public boolean getValueAsBoolean(final boolean defaultValue) throws IOException {
        final JsonToken t = this._currToken;
        if (t != null) {
            switch (t.id()) {
                case 6: {
                    final String str = this.getText().trim();
                    if ("true".equals(str)) {
                        return true;
                    }
                    if ("false".equals(str)) {
                        return false;
                    }
                    if (this._hasTextualNull(str)) {
                        return false;
                    }
                    break;
                }
                case 7: {
                    return this.getIntValue() != 0;
                }
                case 9: {
                    return true;
                }
                case 10:
                case 11: {
                    return false;
                }
                case 12: {
                    final Object value = this.getEmbeddedObject();
                    if (value instanceof Boolean) {
                        return (boolean)value;
                    }
                    break;
                }
            }
        }
        return defaultValue;
    }
    
    @Override
    public int getValueAsInt() throws IOException {
        final JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getIntValue();
        }
        return this.getValueAsInt(0);
    }
    
    @Override
    public int getValueAsInt(final int defaultValue) throws IOException {
        final JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getIntValue();
        }
        if (t != null) {
            switch (t.id()) {
                case 6: {
                    final String str = this.getText();
                    if (this._hasTextualNull(str)) {
                        return 0;
                    }
                    return NumberInput.parseAsInt(str, defaultValue);
                }
                case 9: {
                    return 1;
                }
                case 10: {
                    return 0;
                }
                case 11: {
                    return 0;
                }
                case 12: {
                    final Object value = this.getEmbeddedObject();
                    if (value instanceof Number) {
                        return ((Number)value).intValue();
                    }
                    break;
                }
            }
        }
        return defaultValue;
    }
    
    @Override
    public long getValueAsLong() throws IOException {
        final JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getLongValue();
        }
        return this.getValueAsLong(0L);
    }
    
    @Override
    public long getValueAsLong(final long defaultValue) throws IOException {
        final JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return this.getLongValue();
        }
        if (t != null) {
            switch (t.id()) {
                case 6: {
                    final String str = this.getText();
                    if (this._hasTextualNull(str)) {
                        return 0L;
                    }
                    return NumberInput.parseAsLong(str, defaultValue);
                }
                case 9: {
                    return 1L;
                }
                case 10:
                case 11: {
                    return 0L;
                }
                case 12: {
                    final Object value = this.getEmbeddedObject();
                    if (value instanceof Number) {
                        return ((Number)value).longValue();
                    }
                    break;
                }
            }
        }
        return defaultValue;
    }
    
    @Override
    public double getValueAsDouble(final double defaultValue) throws IOException {
        final JsonToken t = this._currToken;
        if (t != null) {
            switch (t.id()) {
                case 6: {
                    final String str = this.getText();
                    if (this._hasTextualNull(str)) {
                        return 0.0;
                    }
                    return NumberInput.parseAsDouble(str, defaultValue);
                }
                case 7:
                case 8: {
                    return this.getDoubleValue();
                }
                case 9: {
                    return 1.0;
                }
                case 10:
                case 11: {
                    return 0.0;
                }
                case 12: {
                    final Object value = this.getEmbeddedObject();
                    if (value instanceof Number) {
                        return ((Number)value).doubleValue();
                    }
                    break;
                }
            }
        }
        return defaultValue;
    }
    
    @Override
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        return this.getValueAsString(null);
    }
    
    @Override
    public String getValueAsString(final String defaultValue) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            return this.getText();
        }
        if (this._currToken == JsonToken.FIELD_NAME) {
            return this.getCurrentName();
        }
        if (this._currToken == null || this._currToken == JsonToken.VALUE_NULL || !this._currToken.isScalarValue()) {
            return defaultValue;
        }
        return this.getText();
    }
    
    protected void _decodeBase64(final String str, final ByteArrayBuilder builder, final Base64Variant b64variant) throws IOException {
        try {
            b64variant.decode(str, builder);
        }
        catch (IllegalArgumentException e) {
            this._reportError(e.getMessage());
        }
    }
    
    protected boolean _hasTextualNull(final String value) {
        return "null".equals(value);
    }
    
    protected void reportUnexpectedNumberChar(final int ch, final String comment) throws JsonParseException {
        String msg = String.format("Unexpected character (%s) in numeric value", _getCharDesc(ch));
        if (comment != null) {
            msg = msg + ": " + comment;
        }
        this._reportError(msg);
    }
    
    protected void reportInvalidNumber(final String msg) throws JsonParseException {
        this._reportError("Invalid numeric value: " + msg);
    }
    
    protected void reportOverflowInt() throws IOException {
        this._reportError(String.format("Numeric value (%s) out of range of int (%d - %s)", this.getText(), Integer.MIN_VALUE, Integer.MAX_VALUE));
    }
    
    protected void reportOverflowLong() throws IOException {
        this._reportError(String.format("Numeric value (%s) out of range of long (%d - %s)", this.getText(), Long.MIN_VALUE, Long.MAX_VALUE));
    }
    
    protected void _reportUnexpectedChar(final int ch, final String comment) throws JsonParseException {
        if (ch < 0) {
            this._reportInvalidEOF();
        }
        String msg = String.format("Unexpected character (%s)", _getCharDesc(ch));
        if (comment != null) {
            msg = msg + ": " + comment;
        }
        this._reportError(msg);
    }
    
    protected void _reportInvalidEOF() throws JsonParseException {
        this._reportInvalidEOF(" in " + this._currToken, this._currToken);
    }
    
    protected void _reportInvalidEOFInValue(final JsonToken type) throws JsonParseException {
        String msg;
        if (type == JsonToken.VALUE_STRING) {
            msg = " in a String value";
        }
        else if (type == JsonToken.VALUE_NUMBER_INT || type == JsonToken.VALUE_NUMBER_FLOAT) {
            msg = " in a Number value";
        }
        else {
            msg = " in a value";
        }
        this._reportInvalidEOF(msg, type);
    }
    
    protected void _reportInvalidEOF(final String msg, final JsonToken currToken) throws JsonParseException {
        throw new JsonEOFException(this, currToken, "Unexpected end-of-input" + msg);
    }
    
    @Deprecated
    protected void _reportInvalidEOFInValue() throws JsonParseException {
        this._reportInvalidEOF(" in a value");
    }
    
    @Deprecated
    protected void _reportInvalidEOF(final String msg) throws JsonParseException {
        throw new JsonEOFException(this, null, "Unexpected end-of-input" + msg);
    }
    
    protected void _reportMissingRootWS(final int ch) throws JsonParseException {
        this._reportUnexpectedChar(ch, "Expected space separating root-level values");
    }
    
    protected void _throwInvalidSpace(final int i) throws JsonParseException {
        final char c = (char)i;
        final String msg = "Illegal character (" + _getCharDesc(c) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens";
        this._reportError(msg);
    }
    
    protected void _throwUnquotedSpace(final int i, final String ctxtDesc) throws JsonParseException {
        if (!this.isEnabled(Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || i > 32) {
            final char c = (char)i;
            final String msg = "Illegal unquoted character (" + _getCharDesc(c) + "): has to be escaped using backslash to be included in " + ctxtDesc;
            this._reportError(msg);
        }
    }
    
    protected char _handleUnrecognizedCharacterEscape(final char ch) throws JsonProcessingException {
        if (this.isEnabled(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)) {
            return ch;
        }
        if (ch == '\'' && this.isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return ch;
        }
        this._reportError("Unrecognized character escape " + _getCharDesc(ch));
        return ch;
    }
    
    protected static final String _getCharDesc(final int ch) {
        final char c = (char)ch;
        if (Character.isISOControl(c)) {
            return "(CTRL-CHAR, code " + ch + ")";
        }
        if (ch > 255) {
            return "'" + c + "' (code " + ch + " / 0x" + Integer.toHexString(ch) + ")";
        }
        return "'" + c + "' (code " + ch + ")";
    }
    
    protected final void _reportError(final String msg) throws JsonParseException {
        throw this._constructError(msg);
    }
    
    protected final void _reportError(final String msg, final Object arg) throws JsonParseException {
        throw this._constructError(String.format(msg, arg));
    }
    
    protected final void _reportError(final String msg, final Object arg1, final Object arg2) throws JsonParseException {
        throw this._constructError(String.format(msg, arg1, arg2));
    }
    
    protected final void _wrapError(final String msg, final Throwable t) throws JsonParseException {
        throw this._constructError(msg, t);
    }
    
    protected final void _throwInternal() {
        VersionUtil.throwInternal();
    }
    
    protected final JsonParseException _constructError(final String msg, final Throwable t) {
        return new JsonParseException(this, msg, t);
    }
    
    protected static byte[] _asciiBytes(final String str) {
        final byte[] b = new byte[str.length()];
        for (int i = 0, len = str.length(); i < len; ++i) {
            b[i] = (byte)str.charAt(i);
        }
        return b;
    }
    
    protected static String _ascii(final byte[] b) {
        try {
            return new String(b, "US-ASCII");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    static {
        NO_BYTES = new byte[0];
        NO_INTS = new int[0];
        BI_MIN_INT = BigInteger.valueOf(-2147483648L);
        BI_MAX_INT = BigInteger.valueOf(2147483647L);
        BI_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        BI_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        BD_MIN_LONG = new BigDecimal(ParserMinimalBase.BI_MIN_LONG);
        BD_MAX_LONG = new BigDecimal(ParserMinimalBase.BI_MAX_LONG);
        BD_MIN_INT = new BigDecimal(ParserMinimalBase.BI_MIN_INT);
        BD_MAX_INT = new BigDecimal(ParserMinimalBase.BI_MAX_INT);
    }
}
