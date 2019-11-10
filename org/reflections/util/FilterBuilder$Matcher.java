package org.reflections.util;

import com.google.common.base.*;
import java.util.regex.*;

public abstract static class Matcher implements Predicate<String>
{
    final Pattern pattern;
    
    public Matcher(final String regex) {
        super();
        this.pattern = Pattern.compile(regex);
    }
    
    @Override
    public abstract boolean apply(final String p0);
    
    @Override
    public String toString() {
        return this.pattern.pattern();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((String)o);
    }
}
