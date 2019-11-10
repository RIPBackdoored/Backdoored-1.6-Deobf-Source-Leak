package org.json;

private static final class Null
{
    private Null() {
        super();
    }
    
    @Override
    protected final Object clone() {
        return this;
    }
    
    @Override
    public boolean equals(final Object object) {
        return object == null || object == this;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "null";
    }
    
    Null(final JSONObject$1 x0) {
        this();
    }
}
