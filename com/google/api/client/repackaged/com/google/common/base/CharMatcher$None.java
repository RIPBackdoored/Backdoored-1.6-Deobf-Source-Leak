package com.google.api.client.repackaged.com.google.common.base;

private static final class None extends NamedFastMatcher
{
    static final None INSTANCE;
    
    private None() {
        super("CharMatcher.none()");
    }
    
    @Override
    public boolean matches(final char c) {
        return false;
    }
    
    @Override
    public int indexIn(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return -1;
    }
    
    @Override
    public int indexIn(final CharSequence sequence, final int start) {
        final int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        return -1;
    }
    
    @Override
    public int lastIndexIn(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return -1;
    }
    
    @Override
    public boolean matchesAllOf(final CharSequence sequence) {
        return sequence.length() == 0;
    }
    
    @Override
    public boolean matchesNoneOf(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return true;
    }
    
    @Override
    public String removeFrom(final CharSequence sequence) {
        return sequence.toString();
    }
    
    @Override
    public String replaceFrom(final CharSequence sequence, final char replacement) {
        return sequence.toString();
    }
    
    @Override
    public String replaceFrom(final CharSequence sequence, final CharSequence replacement) {
        Preconditions.checkNotNull(replacement);
        return sequence.toString();
    }
    
    @Override
    public String collapseFrom(final CharSequence sequence, final char replacement) {
        return sequence.toString();
    }
    
    @Override
    public String trimFrom(final CharSequence sequence) {
        return sequence.toString();
    }
    
    @Override
    public String trimLeadingFrom(final CharSequence sequence) {
        return sequence.toString();
    }
    
    @Override
    public String trimTrailingFrom(final CharSequence sequence) {
        return sequence.toString();
    }
    
    @Override
    public int countIn(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return 0;
    }
    
    @Override
    public CharMatcher and(final CharMatcher other) {
        Preconditions.checkNotNull(other);
        return this;
    }
    
    @Override
    public CharMatcher or(final CharMatcher other) {
        return Preconditions.checkNotNull(other);
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.any();
    }
    
    static {
        INSTANCE = new None();
    }
}
