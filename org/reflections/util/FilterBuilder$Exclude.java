package org.reflections.util;

public static class Exclude extends Matcher
{
    public Exclude(final String patternString) {
        super(patternString);
    }
    
    @Override
    public boolean apply(final String regex) {
        return !this.pattern.matcher(regex).matches();
    }
    
    @Override
    public String toString() {
        return "-" + super.toString();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object o) {
        return this.apply((String)o);
    }
}
