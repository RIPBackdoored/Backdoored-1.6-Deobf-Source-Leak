package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.*;

public class JsonWriteContext extends JsonStreamContext
{
    public static final int STATUS_OK_AS_IS = 0;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_EXPECT_NAME = 5;
    protected final JsonWriteContext _parent;
    protected DupDetector _dups;
    protected JsonWriteContext _child;
    protected String _currentName;
    protected Object _currentValue;
    protected boolean _gotName;
    
    protected JsonWriteContext(final int type, final JsonWriteContext parent, final DupDetector dups) {
        super();
        this._type = type;
        this._parent = parent;
        this._dups = dups;
        this._index = -1;
    }
    
    protected JsonWriteContext reset(final int type) {
        this._type = type;
        this._index = -1;
        this._currentName = null;
        this._gotName = false;
        this._currentValue = null;
        if (this._dups != null) {
            this._dups.reset();
        }
        return this;
    }
    
    public JsonWriteContext withDupDetector(final DupDetector dups) {
        this._dups = dups;
        return this;
    }
    
    @Override
    public Object getCurrentValue() {
        return this._currentValue;
    }
    
    @Override
    public void setCurrentValue(final Object v) {
        this._currentValue = v;
    }
    
    @Deprecated
    public static JsonWriteContext createRootContext() {
        return createRootContext(null);
    }
    
    public static JsonWriteContext createRootContext(final DupDetector dd) {
        return new JsonWriteContext(0, null, dd);
    }
    
    public JsonWriteContext createChildArrayContext() {
        JsonWriteContext ctxt = this._child;
        if (ctxt == null) {
            ctxt = (this._child = new JsonWriteContext(1, this, (this._dups == null) ? null : this._dups.child()));
            return ctxt;
        }
        return ctxt.reset(1);
    }
    
    public JsonWriteContext createChildObjectContext() {
        JsonWriteContext ctxt = this._child;
        if (ctxt == null) {
            ctxt = (this._child = new JsonWriteContext(2, this, (this._dups == null) ? null : this._dups.child()));
            return ctxt;
        }
        return ctxt.reset(2);
    }
    
    @Override
    public final JsonWriteContext getParent() {
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
    
    public JsonWriteContext clearAndGetParent() {
        this._currentValue = null;
        return this._parent;
    }
    
    public DupDetector getDupDetector() {
        return this._dups;
    }
    
    public int writeFieldName(final String name) throws JsonProcessingException {
        if (this._type != 2 || this._gotName) {
            return 4;
        }
        this._gotName = true;
        this._currentName = name;
        if (this._dups != null) {
            this._checkDup(this._dups, name);
        }
        return (this._index >= 0) ? 1 : 0;
    }
    
    private final void _checkDup(final DupDetector dd, final String name) throws JsonProcessingException {
        if (dd.isDup(name)) {
            final Object src = dd.getSource();
            throw new JsonGenerationException("Duplicate field '" + name + "'", (src instanceof JsonGenerator) ? ((JsonGenerator)src) : null);
        }
    }
    
    public int writeValue() {
        if (this._type == 2) {
            if (!this._gotName) {
                return 5;
            }
            this._gotName = false;
            ++this._index;
            return 2;
        }
        else {
            if (this._type == 1) {
                final int ix = this._index;
                ++this._index;
                return (ix >= 0) ? 1 : 0;
            }
            ++this._index;
            return (this._index == 0) ? 0 : 3;
        }
    }
    
    @Override
    public /* bridge */ JsonStreamContext getParent() {
        return this.getParent();
    }
}
