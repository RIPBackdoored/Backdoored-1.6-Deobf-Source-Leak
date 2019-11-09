package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.*;

public final class JsonStringEncoder
{
    private static final char[] HC;
    private static final byte[] HB;
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected TextBuffer _text;
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf;
    
    public JsonStringEncoder() {
        super();
        (this._qbuf = new char[6])[0] = '\\';
        this._qbuf[2] = '0';
        this._qbuf[3] = '0';
    }
    
    @Deprecated
    public static JsonStringEncoder getInstance() {
        return BufferRecyclers.getJsonStringEncoder();
    }
    
    public char[] quoteAsString(final String input) {
        TextBuffer textBuffer = this._text;
        if (textBuffer == null) {
            textBuffer = (this._text = new TextBuffer(null));
        }
        char[] outputBuffer = textBuffer.emptyAndGetCurrentSegment();
        final int[] escCodes = CharTypes.get7BitOutputEscapes();
        final int escCodeCount = escCodes.length;
        int inPtr = 0;
        final int inputLen = input.length();
        int outPtr = 0;
    Label_0261:
        while (inPtr < inputLen) {
            while (true) {
                final char c = input.charAt(inPtr);
                if (c < escCodeCount && escCodes[c] != 0) {
                    final char d = input.charAt(inPtr++);
                    final int escCode = escCodes[d];
                    final int length = (escCode < 0) ? this._appendNumeric(d, this._qbuf) : this._appendNamed(escCode, this._qbuf);
                    if (outPtr + length > outputBuffer.length) {
                        final int first = outputBuffer.length - outPtr;
                        if (first > 0) {
                            System.arraycopy(this._qbuf, 0, outputBuffer, outPtr, first);
                        }
                        outputBuffer = textBuffer.finishCurrentSegment();
                        final int second = length - first;
                        System.arraycopy(this._qbuf, first, outputBuffer, 0, second);
                        outPtr = second;
                    }
                    else {
                        System.arraycopy(this._qbuf, 0, outputBuffer, outPtr, length);
                        outPtr += length;
                    }
                    break;
                }
                if (outPtr >= outputBuffer.length) {
                    outputBuffer = textBuffer.finishCurrentSegment();
                    outPtr = 0;
                }
                outputBuffer[outPtr++] = c;
                if (++inPtr >= inputLen) {
                    break Label_0261;
                }
            }
        }
        textBuffer.setCurrentLength(outPtr);
        return textBuffer.contentsAsArray();
    }
    
    public void quoteAsString(final CharSequence input, final StringBuilder output) {
        final int[] escCodes = CharTypes.get7BitOutputEscapes();
        final int escCodeCount = escCodes.length;
        int inPtr = 0;
        final int inputLen = input.length();
    Label_0140:
        while (inPtr < inputLen) {
            while (true) {
                final char c = input.charAt(inPtr);
                if (c < escCodeCount && escCodes[c] != 0) {
                    final char d = input.charAt(inPtr++);
                    final int escCode = escCodes[d];
                    final int length = (escCode < 0) ? this._appendNumeric(d, this._qbuf) : this._appendNamed(escCode, this._qbuf);
                    output.append(this._qbuf, 0, length);
                    break;
                }
                output.append(c);
                if (++inPtr >= inputLen) {
                    break Label_0140;
                }
            }
        }
    }
    
    public byte[] quoteAsUTF8(final String text) {
        ByteArrayBuilder bb = this._bytes;
        if (bb == null) {
            bb = (this._bytes = new ByteArrayBuilder(null));
        }
        int inputPtr = 0;
        final int inputEnd = text.length();
        int outputPtr = 0;
        byte[] outputBuffer = bb.resetAndGetFirstSegment();
    Label_0492:
        while (inputPtr < inputEnd) {
            final int[] escCodes = CharTypes.get7BitOutputEscapes();
            while (true) {
                int ch = text.charAt(inputPtr);
                if (ch <= 127 && escCodes[ch] == 0) {
                    if (outputPtr >= outputBuffer.length) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte)ch;
                    if (++inputPtr >= inputEnd) {
                        break Label_0492;
                    }
                    continue;
                }
                else {
                    if (outputPtr >= outputBuffer.length) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputPtr = 0;
                    }
                    ch = text.charAt(inputPtr++);
                    if (ch <= 127) {
                        final int escape = escCodes[ch];
                        outputPtr = this._appendByte(ch, escape, bb, outputPtr);
                        outputBuffer = bb.getCurrentSegment();
                        break;
                    }
                    if (ch <= 2047) {
                        outputBuffer[outputPtr++] = (byte)(0xC0 | ch >> 6);
                        ch = (0x80 | (ch & 0x3F));
                    }
                    else if (ch < 55296 || ch > 57343) {
                        outputBuffer[outputPtr++] = (byte)(0xE0 | ch >> 12);
                        if (outputPtr >= outputBuffer.length) {
                            outputBuffer = bb.finishCurrentSegment();
                            outputPtr = 0;
                        }
                        outputBuffer[outputPtr++] = (byte)(0x80 | (ch >> 6 & 0x3F));
                        ch = (0x80 | (ch & 0x3F));
                    }
                    else {
                        if (ch > 56319) {
                            _illegal(ch);
                        }
                        if (inputPtr >= inputEnd) {
                            _illegal(ch);
                        }
                        ch = _convert(ch, text.charAt(inputPtr++));
                        if (ch > 1114111) {
                            _illegal(ch);
                        }
                        outputBuffer[outputPtr++] = (byte)(0xF0 | ch >> 18);
                        if (outputPtr >= outputBuffer.length) {
                            outputBuffer = bb.finishCurrentSegment();
                            outputPtr = 0;
                        }
                        outputBuffer[outputPtr++] = (byte)(0x80 | (ch >> 12 & 0x3F));
                        if (outputPtr >= outputBuffer.length) {
                            outputBuffer = bb.finishCurrentSegment();
                            outputPtr = 0;
                        }
                        outputBuffer[outputPtr++] = (byte)(0x80 | (ch >> 6 & 0x3F));
                        ch = (0x80 | (ch & 0x3F));
                    }
                    if (outputPtr >= outputBuffer.length) {
                        outputBuffer = bb.finishCurrentSegment();
                        outputPtr = 0;
                    }
                    outputBuffer[outputPtr++] = (byte)ch;
                    break;
                }
            }
        }
        return this._bytes.completeAndCoalesce(outputPtr);
    }
    
    public byte[] encodeAsUTF8(final String text) {
        ByteArrayBuilder byteBuilder = this._bytes;
        if (byteBuilder == null) {
            byteBuilder = (this._bytes = new ByteArrayBuilder(null));
        }
        int inputPtr = 0;
        final int inputEnd = text.length();
        int outputPtr = 0;
        byte[] outputBuffer = byteBuilder.resetAndGetFirstSegment();
        int outputEnd = outputBuffer.length;
    Label_0443:
        while (inputPtr < inputEnd) {
            int c;
            for (c = text.charAt(inputPtr++); c <= 127; c = text.charAt(inputPtr++)) {
                if (outputPtr >= outputEnd) {
                    outputBuffer = byteBuilder.finishCurrentSegment();
                    outputEnd = outputBuffer.length;
                    outputPtr = 0;
                }
                outputBuffer[outputPtr++] = (byte)c;
                if (inputPtr >= inputEnd) {
                    break Label_0443;
                }
            }
            if (outputPtr >= outputEnd) {
                outputBuffer = byteBuilder.finishCurrentSegment();
                outputEnd = outputBuffer.length;
                outputPtr = 0;
            }
            if (c < 2048) {
                outputBuffer[outputPtr++] = (byte)(0xC0 | c >> 6);
            }
            else if (c < 55296 || c > 57343) {
                outputBuffer[outputPtr++] = (byte)(0xE0 | c >> 12);
                if (outputPtr >= outputEnd) {
                    outputBuffer = byteBuilder.finishCurrentSegment();
                    outputEnd = outputBuffer.length;
                    outputPtr = 0;
                }
                outputBuffer[outputPtr++] = (byte)(0x80 | (c >> 6 & 0x3F));
            }
            else {
                if (c > 56319) {
                    _illegal(c);
                }
                if (inputPtr >= inputEnd) {
                    _illegal(c);
                }
                c = _convert(c, text.charAt(inputPtr++));
                if (c > 1114111) {
                    _illegal(c);
                }
                outputBuffer[outputPtr++] = (byte)(0xF0 | c >> 18);
                if (outputPtr >= outputEnd) {
                    outputBuffer = byteBuilder.finishCurrentSegment();
                    outputEnd = outputBuffer.length;
                    outputPtr = 0;
                }
                outputBuffer[outputPtr++] = (byte)(0x80 | (c >> 12 & 0x3F));
                if (outputPtr >= outputEnd) {
                    outputBuffer = byteBuilder.finishCurrentSegment();
                    outputEnd = outputBuffer.length;
                    outputPtr = 0;
                }
                outputBuffer[outputPtr++] = (byte)(0x80 | (c >> 6 & 0x3F));
            }
            if (outputPtr >= outputEnd) {
                outputBuffer = byteBuilder.finishCurrentSegment();
                outputEnd = outputBuffer.length;
                outputPtr = 0;
            }
            outputBuffer[outputPtr++] = (byte)(0x80 | (c & 0x3F));
        }
        return this._bytes.completeAndCoalesce(outputPtr);
    }
    
    private int _appendNumeric(final int value, final char[] qbuf) {
        qbuf[1] = 'u';
        qbuf[4] = JsonStringEncoder.HC[value >> 4];
        qbuf[5] = JsonStringEncoder.HC[value & 0xF];
        return 6;
    }
    
    private int _appendNamed(final int esc, final char[] qbuf) {
        qbuf[1] = (char)esc;
        return 2;
    }
    
    private int _appendByte(int ch, final int esc, final ByteArrayBuilder bb, final int ptr) {
        bb.setCurrentSegmentLength(ptr);
        bb.append(92);
        if (esc < 0) {
            bb.append(117);
            if (ch > 255) {
                final int hi = ch >> 8;
                bb.append(JsonStringEncoder.HB[hi >> 4]);
                bb.append(JsonStringEncoder.HB[hi & 0xF]);
                ch &= 0xFF;
            }
            else {
                bb.append(48);
                bb.append(48);
            }
            bb.append(JsonStringEncoder.HB[ch >> 4]);
            bb.append(JsonStringEncoder.HB[ch & 0xF]);
        }
        else {
            bb.append((byte)esc);
        }
        return bb.getCurrentSegmentLength();
    }
    
    private static int _convert(final int p1, final int p2) {
        if (p2 < 56320 || p2 > 57343) {
            throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(p1) + ", second 0x" + Integer.toHexString(p2) + "; illegal combination");
        }
        return 65536 + (p1 - 55296 << 10) + (p2 - 56320);
    }
    
    private static void _illegal(final int c) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(c));
    }
    
    static {
        HC = CharTypes.copyHexChars();
        HB = CharTypes.copyHexBytes();
    }
}
