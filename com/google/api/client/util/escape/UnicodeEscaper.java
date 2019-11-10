package com.google.api.client.util.escape;

public abstract class UnicodeEscaper extends Escaper
{
    private static final int DEST_PAD = 32;
    
    public UnicodeEscaper() {
        super();
    }
    
    protected abstract char[] escape(final int p0);
    
    protected abstract int nextEscapeIndex(final CharSequence p0, final int p1, final int p2);
    
    @Override
    public abstract String escape(final String p0);
    
    protected final String escapeSlow(final String s, int index) {
        final int end = s.length();
        char[] dest = Platform.charBufferFromThreadLocal();
        int destIndex = 0;
        int unescapedChunkStart = 0;
        while (index < end) {
            final int cp = codePointAt(s, index, end);
            if (cp < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            final char[] escaped = this.escape(cp);
            final int nextIndex = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
            if (escaped != null) {
                final int charsSkipped = index - unescapedChunkStart;
                final int sizeNeeded = destIndex + charsSkipped + escaped.length;
                if (dest.length < sizeNeeded) {
                    final int destLength = sizeNeeded + end - index + 32;
                    dest = growBuffer(dest, destIndex, destLength);
                }
                if (charsSkipped > 0) {
                    s.getChars(unescapedChunkStart, index, dest, destIndex);
                    destIndex += charsSkipped;
                }
                if (escaped.length > 0) {
                    System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
                    destIndex += escaped.length;
                }
                unescapedChunkStart = nextIndex;
            }
            index = this.nextEscapeIndex(s, nextIndex, end);
        }
        final int charsSkipped2 = end - unescapedChunkStart;
        if (charsSkipped2 > 0) {
            final int endIndex = destIndex + charsSkipped2;
            if (dest.length < endIndex) {
                dest = growBuffer(dest, destIndex, endIndex);
            }
            s.getChars(unescapedChunkStart, end, dest, destIndex);
            destIndex = endIndex;
        }
        return new String(dest, 0, destIndex);
    }
    
    protected static int codePointAt(final CharSequence seq, int index, final int end) {
        if (index >= end) {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        final char c1 = seq.charAt(index++);
        if (c1 < '\ud800' || c1 > '\udfff') {
            return c1;
        }
        if (c1 > '\udbff') {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + (int)c1 + " at index " + (index - 1));
        }
        if (index == end) {
            return -c1;
        }
        final char c2 = seq.charAt(index);
        if (Character.isLowSurrogate(c2)) {
            return Character.toCodePoint(c1, c2);
        }
        throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + (int)c2 + " at index " + index);
    }
    
    private static char[] growBuffer(final char[] dest, final int index, final int size) {
        final char[] copy = new char[size];
        if (index > 0) {
            System.arraycopy(dest, 0, copy, 0, index);
        }
        return copy;
    }
}
