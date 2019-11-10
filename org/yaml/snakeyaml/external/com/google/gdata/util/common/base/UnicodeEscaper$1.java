package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.*;

class UnicodeEscaper$1 implements Appendable {
    int pendingHighSurrogate = -1;
    char[] decodedChars = new char[2];
    final /* synthetic */ Appendable val$out;
    final /* synthetic */ UnicodeEscaper this$0;
    
    UnicodeEscaper$1(final UnicodeEscaper this$0, final Appendable val$out) {
        this.this$0 = this$0;
        this.val$out = val$out;
        super();
    }
    
    @Override
    public Appendable append(final CharSequence csq) throws IOException {
        return this.append(csq, 0, csq.length());
    }
    
    @Override
    public Appendable append(final CharSequence csq, final int start, final int end) throws IOException {
        int index = start;
        if (index < end) {
            int unescapedChunkStart = index;
            if (this.pendingHighSurrogate != -1) {
                final char c = csq.charAt(index++);
                if (!Character.isLowSurrogate(c)) {
                    throw new IllegalArgumentException("Expected low surrogate character but got " + c);
                }
                final char[] escaped = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                if (escaped != null) {
                    this.outputChars(escaped, escaped.length);
                    ++unescapedChunkStart;
                }
                else {
                    this.val$out.append((char)this.pendingHighSurrogate);
                }
                this.pendingHighSurrogate = -1;
            }
            while (true) {
                index = this.this$0.nextEscapeIndex(csq, index, end);
                if (index > unescapedChunkStart) {
                    this.val$out.append(csq, unescapedChunkStart, index);
                }
                if (index == end) {
                    break;
                }
                final int cp = UnicodeEscaper.codePointAt(csq, index, end);
                if (cp < 0) {
                    this.pendingHighSurrogate = -cp;
                    break;
                }
                final char[] escaped = this.this$0.escape(cp);
                if (escaped != null) {
                    this.outputChars(escaped, escaped.length);
                }
                else {
                    final int len = Character.toChars(cp, this.decodedChars, 0);
                    this.outputChars(this.decodedChars, len);
                }
                index = (unescapedChunkStart = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1));
            }
        }
        return this;
    }
    
    @Override
    public Appendable append(final char c) throws IOException {
        if (this.pendingHighSurrogate != -1) {
            if (!Character.isLowSurrogate(c)) {
                throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + (int)c);
            }
            final char[] escaped = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
            if (escaped != null) {
                this.outputChars(escaped, escaped.length);
            }
            else {
                this.val$out.append((char)this.pendingHighSurrogate);
                this.val$out.append(c);
            }
            this.pendingHighSurrogate = -1;
        }
        else if (Character.isHighSurrogate(c)) {
            this.pendingHighSurrogate = c;
        }
        else {
            if (Character.isLowSurrogate(c)) {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + (int)c);
            }
            final char[] escaped = this.this$0.escape(c);
            if (escaped != null) {
                this.outputChars(escaped, escaped.length);
            }
            else {
                this.val$out.append(c);
            }
        }
        return this;
    }
    
    private void outputChars(final char[] chars, final int len) throws IOException {
        for (int n = 0; n < len; ++n) {
            this.val$out.append(chars[n]);
        }
    }
}