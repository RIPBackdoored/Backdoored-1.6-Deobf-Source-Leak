package com.fasterxml.jackson.core.filter;

import java.io.*;
import com.fasterxml.jackson.core.*;

public class TokenFilterContext extends JsonStreamContext
{
    protected final TokenFilterContext _parent;
    protected TokenFilterContext _child;
    protected String _currentName;
    protected TokenFilter _filter;
    protected boolean _startHandled;
    protected boolean _needToHandleName;
    
    protected TokenFilterContext(final int type, final TokenFilterContext parent, final TokenFilter filter, final boolean startHandled) {
        super();
        this._type = type;
        this._parent = parent;
        this._filter = filter;
        this._index = -1;
        this._startHandled = startHandled;
        this._needToHandleName = false;
    }
    
    protected TokenFilterContext reset(final int type, final TokenFilter filter, final boolean startWritten) {
        this._type = type;
        this._filter = filter;
        this._index = -1;
        this._currentName = null;
        this._startHandled = startWritten;
        this._needToHandleName = false;
        return this;
    }
    
    public static TokenFilterContext createRootContext(final TokenFilter filter) {
        return new TokenFilterContext(0, null, filter, true);
    }
    
    public TokenFilterContext createChildArrayContext(final TokenFilter filter, final boolean writeStart) {
        TokenFilterContext ctxt = this._child;
        if (ctxt == null) {
            ctxt = (this._child = new TokenFilterContext(1, this, filter, writeStart));
            return ctxt;
        }
        return ctxt.reset(1, filter, writeStart);
    }
    
    public TokenFilterContext createChildObjectContext(final TokenFilter filter, final boolean writeStart) {
        TokenFilterContext ctxt = this._child;
        if (ctxt == null) {
            ctxt = (this._child = new TokenFilterContext(2, this, filter, writeStart));
            return ctxt;
        }
        return ctxt.reset(2, filter, writeStart);
    }
    
    public TokenFilter setFieldName(final String name) throws JsonProcessingException {
        this._currentName = name;
        this._needToHandleName = true;
        return this._filter;
    }
    
    public TokenFilter checkValue(final TokenFilter filter) {
        if (this._type == 2) {
            return filter;
        }
        final int ix = ++this._index;
        if (this._type == 1) {
            return filter.includeElement(ix);
        }
        return filter.includeRootValue(ix);
    }
    
    public void writePath(final JsonGenerator gen) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._parent != null) {
            this._parent._writePath(gen);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                gen.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                gen.writeStartObject();
                gen.writeFieldName(this._currentName);
            }
            else if (this._type == 1) {
                gen.writeStartArray();
            }
        }
    }
    
    public void writeImmediatePath(final JsonGenerator gen) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                gen.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                gen.writeStartObject();
                if (this._needToHandleName) {
                    gen.writeFieldName(this._currentName);
                }
            }
            else if (this._type == 1) {
                gen.writeStartArray();
            }
        }
    }
    
    private void _writePath(final JsonGenerator gen) throws IOException {
        if (this._filter == null || this._filter == TokenFilter.INCLUDE_ALL) {
            return;
        }
        if (this._parent != null) {
            this._parent._writePath(gen);
        }
        if (this._startHandled) {
            if (this._needToHandleName) {
                this._needToHandleName = false;
                gen.writeFieldName(this._currentName);
            }
        }
        else {
            this._startHandled = true;
            if (this._type == 2) {
                gen.writeStartObject();
                if (this._needToHandleName) {
                    this._needToHandleName = false;
                    gen.writeFieldName(this._currentName);
                }
            }
            else if (this._type == 1) {
                gen.writeStartArray();
            }
        }
    }
    
    public TokenFilterContext closeArray(final JsonGenerator gen) throws IOException {
        if (this._startHandled) {
            gen.writeEndArray();
        }
        if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishArray();
        }
        return this._parent;
    }
    
    public TokenFilterContext closeObject(final JsonGenerator gen) throws IOException {
        if (this._startHandled) {
            gen.writeEndObject();
        }
        if (this._filter != null && this._filter != TokenFilter.INCLUDE_ALL) {
            this._filter.filterFinishObject();
        }
        return this._parent;
    }
    
    public void skipParentChecks() {
        this._filter = null;
        for (TokenFilterContext ctxt = this._parent; ctxt != null; ctxt = ctxt._parent) {
            this._parent._filter = null;
        }
    }
    
    @Override
    public Object getCurrentValue() {
        return null;
    }
    
    @Override
    public void setCurrentValue(final Object v) {
    }
    
    @Override
    public final TokenFilterContext getParent() {
        return this._parent;
    }
    
    @Override
    public final String getCurrentName() {
        return this._currentName;
    }
    
    @Override
    public boolean hasCurrentName() {
        return this._currentName != null;
    }
    
    public TokenFilter getFilter() {
        return this._filter;
    }
    
    public boolean isStartHandled() {
        return this._startHandled;
    }
    
    public JsonToken nextTokenToRead() {
        if (!this._startHandled) {
            this._startHandled = true;
            if (this._type == 2) {
                return JsonToken.START_OBJECT;
            }
            return JsonToken.START_ARRAY;
        }
        else {
            if (this._needToHandleName && this._type == 2) {
                this._needToHandleName = false;
                return JsonToken.FIELD_NAME;
            }
            return null;
        }
    }
    
    public TokenFilterContext findChildOf(final TokenFilterContext parent) {
        if (this._parent == parent) {
            return this;
        }
        TokenFilterContext p;
        for (TokenFilterContext curr = this._parent; curr != null; curr = p) {
            p = curr._parent;
            if (p == parent) {
                return curr;
            }
        }
        return null;
    }
    
    protected void appendDesc(final StringBuilder sb) {
        if (this._parent != null) {
            this._parent.appendDesc(sb);
        }
        if (this._type == 2) {
            sb.append('{');
            if (this._currentName != null) {
                sb.append('\"');
                sb.append(this._currentName);
                sb.append('\"');
            }
            else {
                sb.append('?');
            }
            sb.append('}');
        }
        else if (this._type == 1) {
            sb.append('[');
            sb.append(this.getCurrentIndex());
            sb.append(']');
        }
        else {
            sb.append("/");
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        this.appendDesc(sb);
        return sb.toString();
    }
    
    @Override
    public /* bridge */ JsonStreamContext getParent() {
        return this.getParent();
    }
}
