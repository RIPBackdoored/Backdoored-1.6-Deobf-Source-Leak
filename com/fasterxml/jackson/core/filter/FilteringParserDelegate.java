package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.util.*;
import java.math.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public class FilteringParserDelegate extends JsonParserDelegate
{
    protected TokenFilter rootFilter;
    protected boolean _allowMultipleMatches;
    protected boolean _includePath;
    @Deprecated
    protected boolean _includeImmediateParent;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;
    protected TokenFilterContext _headContext;
    protected TokenFilterContext _exposedContext;
    protected TokenFilter _itemFilter;
    protected int _matchCount;
    
    public FilteringParserDelegate(final JsonParser p, final TokenFilter f, final boolean includePath, final boolean allowMultipleMatches) {
        super(p);
        this.rootFilter = f;
        this._itemFilter = f;
        this._headContext = TokenFilterContext.createRootContext(f);
        this._includePath = includePath;
        this._allowMultipleMatches = allowMultipleMatches;
    }
    
    public TokenFilter getFilter() {
        return this.rootFilter;
    }
    
    public int getMatchCount() {
        return this._matchCount;
    }
    
    @Override
    public JsonToken getCurrentToken() {
        return this._currToken;
    }
    
    @Override
    public JsonToken currentToken() {
        return this._currToken;
    }
    
    @Override
    public final int getCurrentTokenId() {
        final JsonToken t = this._currToken;
        return (t == null) ? 0 : t.id();
    }
    
    @Override
    public final int currentTokenId() {
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
    public final boolean hasToken(final JsonToken t) {
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
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }
    
    @Override
    public JsonStreamContext getParsingContext() {
        return this._filterContext();
    }
    
    @Override
    public String getCurrentName() throws IOException {
        final JsonStreamContext ctxt = this._filterContext();
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            final JsonStreamContext parent = ctxt.getParent();
            return (parent == null) ? null : parent.getCurrentName();
        }
        return ctxt.getCurrentName();
    }
    
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
    public void overrideCurrentName(final String name) {
        throw new UnsupportedOperationException("Can not currently override name during filtering read");
    }
    
    @Override
    public JsonToken nextToken() throws IOException {
        if (!this._allowMultipleMatches && this._currToken != null && this._exposedContext == null && this._currToken.isScalarValue() && !this._headContext.isStartHandled() && !this._includePath && this._itemFilter == TokenFilter.INCLUDE_ALL) {
            return this._currToken = null;
        }
        TokenFilterContext ctxt = this._exposedContext;
        if (ctxt != null) {
            while (true) {
                JsonToken t = ctxt.nextTokenToRead();
                if (t != null) {
                    return this._currToken = t;
                }
                if (ctxt == this._headContext) {
                    this._exposedContext = null;
                    if (ctxt.inArray()) {
                        t = this.delegate.getCurrentToken();
                        return this._currToken = t;
                    }
                    break;
                }
                else {
                    ctxt = this._headContext.findChildOf(ctxt);
                    if ((this._exposedContext = ctxt) == null) {
                        throw this._constructError("Unexpected problem: chain of filtered context broken");
                    }
                    continue;
                }
            }
        }
        JsonToken t = this.delegate.nextToken();
        if (t == null) {
            return this._currToken = t;
        }
        switch (t.id()) {
            case 3: {
                TokenFilter f = this._itemFilter;
                if (f == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(f, true);
                    return this._currToken = t;
                }
                if (f == null) {
                    this.delegate.skipChildren();
                    break;
                }
                f = this._headContext.checkValue(f);
                if (f == null) {
                    this.delegate.skipChildren();
                    break;
                }
                if (f != TokenFilter.INCLUDE_ALL) {
                    f = f.filterStartArray();
                }
                if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(f, true);
                    return this._currToken = t;
                }
                this._headContext = this._headContext.createChildArrayContext(f, false);
                if (!this._includePath) {
                    break;
                }
                t = this._nextTokenWithBuffering(this._headContext);
                if (t != null) {
                    return this._currToken = t;
                }
                break;
            }
            case 1: {
                TokenFilter f = this._itemFilter;
                if (f == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(f, true);
                    return this._currToken = t;
                }
                if (f == null) {
                    this.delegate.skipChildren();
                    break;
                }
                f = this._headContext.checkValue(f);
                if (f == null) {
                    this.delegate.skipChildren();
                    break;
                }
                if (f != TokenFilter.INCLUDE_ALL) {
                    f = f.filterStartObject();
                }
                if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(f, true);
                    return this._currToken = t;
                }
                this._headContext = this._headContext.createChildObjectContext(f, false);
                if (!this._includePath) {
                    break;
                }
                t = this._nextTokenWithBuffering(this._headContext);
                if (t != null) {
                    return this._currToken = t;
                }
                break;
            }
            case 2:
            case 4: {
                final boolean returnEnd = this._headContext.isStartHandled();
                final TokenFilter f = this._headContext.getFilter();
                if (f != null && f != TokenFilter.INCLUDE_ALL) {
                    f.filterFinishArray();
                }
                this._headContext = this._headContext.getParent();
                this._itemFilter = this._headContext.getFilter();
                if (returnEnd) {
                    return this._currToken = t;
                }
                break;
            }
            case 5: {
                final String name = this.delegate.getCurrentName();
                TokenFilter f = this._headContext.setFieldName(name);
                if (f == TokenFilter.INCLUDE_ALL) {
                    this._itemFilter = f;
                    if (!this._includePath && this._includeImmediateParent && !this._headContext.isStartHandled()) {
                        t = this._headContext.nextTokenToRead();
                        this._exposedContext = this._headContext;
                    }
                    return this._currToken = t;
                }
                if (f == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                    break;
                }
                f = f.includeProperty(name);
                if (f == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                    break;
                }
                if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                    if (this._verifyAllowedMatches()) {
                        if (this._includePath) {
                            return this._currToken = t;
                        }
                    }
                    else {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                    }
                }
                if (!this._includePath) {
                    break;
                }
                t = this._nextTokenWithBuffering(this._headContext);
                if (t != null) {
                    return this._currToken = t;
                }
                break;
            }
            default: {
                TokenFilter f = this._itemFilter;
                if (f == TokenFilter.INCLUDE_ALL) {
                    return this._currToken = t;
                }
                if (f == null) {
                    break;
                }
                f = this._headContext.checkValue(f);
                if ((f == TokenFilter.INCLUDE_ALL || (f != null && f.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                    return this._currToken = t;
                }
                break;
            }
        }
        return this._nextToken2();
    }
    
    protected final JsonToken _nextToken2() throws IOException {
        while (true) {
            JsonToken t = this.delegate.nextToken();
            if (t == null) {
                return this._currToken = t;
            }
            switch (t.id()) {
                case 3: {
                    TokenFilter f = this._itemFilter;
                    if (f == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(f, true);
                        return this._currToken = t;
                    }
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    f = this._headContext.checkValue(f);
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (f != TokenFilter.INCLUDE_ALL) {
                        f = f.filterStartArray();
                    }
                    if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(f, true);
                        return this._currToken = t;
                    }
                    this._headContext = this._headContext.createChildArrayContext(f, false);
                    if (!this._includePath) {
                        continue;
                    }
                    t = this._nextTokenWithBuffering(this._headContext);
                    if (t != null) {
                        return this._currToken = t;
                    }
                    continue;
                }
                case 1: {
                    TokenFilter f = this._itemFilter;
                    if (f == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(f, true);
                        return this._currToken = t;
                    }
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    f = this._headContext.checkValue(f);
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (f != TokenFilter.INCLUDE_ALL) {
                        f = f.filterStartObject();
                    }
                    if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(f, true);
                        return this._currToken = t;
                    }
                    this._headContext = this._headContext.createChildObjectContext(f, false);
                    if (!this._includePath) {
                        continue;
                    }
                    t = this._nextTokenWithBuffering(this._headContext);
                    if (t != null) {
                        return this._currToken = t;
                    }
                    continue;
                }
                case 2:
                case 4: {
                    final boolean returnEnd = this._headContext.isStartHandled();
                    final TokenFilter f = this._headContext.getFilter();
                    if (f != null && f != TokenFilter.INCLUDE_ALL) {
                        f.filterFinishArray();
                    }
                    this._headContext = this._headContext.getParent();
                    this._itemFilter = this._headContext.getFilter();
                    if (returnEnd) {
                        return this._currToken = t;
                    }
                    continue;
                }
                case 5: {
                    final String name = this.delegate.getCurrentName();
                    TokenFilter f = this._headContext.setFieldName(name);
                    if (f == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = f;
                        return this._currToken = t;
                    }
                    if (f == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    f = f.includeProperty(name);
                    if (f == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    if ((this._itemFilter = f) != TokenFilter.INCLUDE_ALL) {
                        if (!this._includePath) {
                            continue;
                        }
                        t = this._nextTokenWithBuffering(this._headContext);
                        if (t != null) {
                            return this._currToken = t;
                        }
                        continue;
                        continue;
                    }
                    if (this._verifyAllowedMatches() && this._includePath) {
                        return this._currToken = t;
                    }
                    continue;
                }
                default: {
                    TokenFilter f = this._itemFilter;
                    if (f == TokenFilter.INCLUDE_ALL) {
                        return this._currToken = t;
                    }
                    if (f == null) {
                        continue;
                    }
                    f = this._headContext.checkValue(f);
                    if ((f == TokenFilter.INCLUDE_ALL || (f != null && f.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                        return this._currToken = t;
                    }
                    continue;
                    continue;
                }
            }
        }
    }
    
    protected final JsonToken _nextTokenWithBuffering(final TokenFilterContext buffRoot) throws IOException {
        while (true) {
            final JsonToken t = this.delegate.nextToken();
            if (t == null) {
                return t;
            }
            switch (t.id()) {
                case 3: {
                    TokenFilter f = this._headContext.checkValue(this._itemFilter);
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (f != TokenFilter.INCLUDE_ALL) {
                        f = f.filterStartArray();
                    }
                    if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildArrayContext(f, true);
                        return this._nextBuffered(buffRoot);
                    }
                    this._headContext = this._headContext.createChildArrayContext(f, false);
                    continue;
                }
                case 1: {
                    TokenFilter f = this._itemFilter;
                    if (f == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(f, true);
                        return t;
                    }
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    f = this._headContext.checkValue(f);
                    if (f == null) {
                        this.delegate.skipChildren();
                        continue;
                    }
                    if (f != TokenFilter.INCLUDE_ALL) {
                        f = f.filterStartObject();
                    }
                    if ((this._itemFilter = f) == TokenFilter.INCLUDE_ALL) {
                        this._headContext = this._headContext.createChildObjectContext(f, true);
                        return this._nextBuffered(buffRoot);
                    }
                    this._headContext = this._headContext.createChildObjectContext(f, false);
                    continue;
                }
                case 2:
                case 4: {
                    final TokenFilter f = this._headContext.getFilter();
                    if (f != null && f != TokenFilter.INCLUDE_ALL) {
                        f.filterFinishArray();
                    }
                    final boolean gotEnd = this._headContext == buffRoot;
                    final boolean returnEnd = gotEnd && this._headContext.isStartHandled();
                    this._headContext = this._headContext.getParent();
                    this._itemFilter = this._headContext.getFilter();
                    if (returnEnd) {
                        return t;
                    }
                    continue;
                }
                case 5: {
                    final String name = this.delegate.getCurrentName();
                    TokenFilter f = this._headContext.setFieldName(name);
                    if (f == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = f;
                        return this._nextBuffered(buffRoot);
                    }
                    if (f == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    f = f.includeProperty(name);
                    if (f == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        continue;
                    }
                    if ((this._itemFilter = f) != TokenFilter.INCLUDE_ALL) {
                        continue;
                    }
                    if (this._verifyAllowedMatches()) {
                        return this._nextBuffered(buffRoot);
                    }
                    this._itemFilter = this._headContext.setFieldName(name);
                    continue;
                }
                default: {
                    TokenFilter f = this._itemFilter;
                    if (f == TokenFilter.INCLUDE_ALL) {
                        return this._nextBuffered(buffRoot);
                    }
                    if (f == null) {
                        continue;
                    }
                    f = this._headContext.checkValue(f);
                    if ((f == TokenFilter.INCLUDE_ALL || (f != null && f.includeValue(this.delegate))) && this._verifyAllowedMatches()) {
                        return this._nextBuffered(buffRoot);
                    }
                    continue;
                }
            }
        }
    }
    
    private JsonToken _nextBuffered(final TokenFilterContext buffRoot) throws IOException {
        this._exposedContext = buffRoot;
        TokenFilterContext ctxt = buffRoot;
        JsonToken t = ctxt.nextTokenToRead();
        if (t != null) {
            return t;
        }
        while (ctxt != this._headContext) {
            ctxt = this._exposedContext.findChildOf(ctxt);
            if ((this._exposedContext = ctxt) == null) {
                throw this._constructError("Unexpected problem: chain of filtered context broken");
            }
            t = this._exposedContext.nextTokenToRead();
            if (t != null) {
                return t;
            }
        }
        throw this._constructError("Internal error: failed to locate expected buffered tokens");
    }
    
    private final boolean _verifyAllowedMatches() throws IOException {
        if (this._matchCount == 0 || this._allowMultipleMatches) {
            ++this._matchCount;
            return true;
        }
        return false;
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
                return this;
            }
            if (t.isStructStart()) {
                ++open;
            }
            else {
                if (t.isStructEnd() && --open == 0) {
                    return this;
                }
                continue;
            }
        }
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
    
    protected JsonStreamContext _filterContext() {
        if (this._exposedContext != null) {
            return this._exposedContext;
        }
        return this._headContext;
    }
}
