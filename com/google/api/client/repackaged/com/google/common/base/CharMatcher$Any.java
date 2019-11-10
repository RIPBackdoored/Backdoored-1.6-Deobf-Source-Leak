package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

private static final class Any extends NamedFastMatcher
{
    static final Any INSTANCE;
    
    private Any() {
        super("CharMatcher.any()");
    }
    
    @Override
    public boolean matches(final char c) {
        return true;
    }
    
    @Override
    public int indexIn(final CharSequence sequence) {
        return (sequence.length() == 0) ? -1 : 0;
    }
    
    @Override
    public int indexIn(final CharSequence sequence, final int start) {
        final int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        return (start == length) ? -1 : start;
    }
    
    @Override
    public int lastIndexIn(final CharSequence sequence) {
        return sequence.length() - 1;
    }
    
    @Override
    public boolean matchesAllOf(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return true;
    }
    
    @Override
    public boolean matchesNoneOf(final CharSequence sequence) {
        return sequence.length() == 0;
    }
    
    @Override
    public String removeFrom(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return "";
    }
    
    @Override
    public String replaceFrom(final CharSequence sequence, final char replacement) {
        final char[] array = new char[sequence.length()];
        Arrays.fill(array, replacement);
        return new String(array);
    }
    
    @Override
    public String replaceFrom(final CharSequence sequence, final CharSequence replacement) {
        final StringBuilder result = new StringBuilder(sequence.length() * replacement.length());
        for (int i = 0; i < sequence.length(); ++i) {
            result.append(replacement);
        }
        return result.toString();
    }
    
    @Override
    public String collapseFrom(final CharSequence sequence, final char replacement) {
        return (sequence.length() == 0) ? "" : String.valueOf(replacement);
    }
    
    @Override
    public String trimFrom(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return "";
    }
    
    @Override
    public int countIn(final CharSequence sequence) {
        return sequence.length();
    }
    
    @Override
    public CharMatcher and(final CharMatcher other) {
        return Preconditions.checkNotNull(other);
    }
    
    @Override
    public CharMatcher or(final CharMatcher other) {
        Preconditions.checkNotNull(other);
        return this;
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.none();
    }
    
    static {
        INSTANCE = new Any();
    }
}
