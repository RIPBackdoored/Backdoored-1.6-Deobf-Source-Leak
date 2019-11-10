package com.google.api.client.repackaged.com.google.common.base;

abstract static class NamedFastMatcher extends FastMatcher
{
    private final String description;
    
    NamedFastMatcher(final String description) {
        super();
        this.description = Preconditions.checkNotNull(description);
    }
    
    @Override
    public final String toString() {
        return this.description;
    }
}
