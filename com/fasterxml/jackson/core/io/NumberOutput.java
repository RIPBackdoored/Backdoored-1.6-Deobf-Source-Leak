package com.fasterxml.jackson.core.io;

public final class NumberOutput
{
    private static int MILLION;
    private static int BILLION;
    private static long BILLION_L;
    private static long MIN_INT_AS_LONG;
    private static long MAX_INT_AS_LONG;
    static final String SMALLEST_INT;
    static final String SMALLEST_LONG;
    private static final int[] TRIPLET_TO_CHARS;
    private static final String[] sSmallIntStrs;
    private static final String[] sSmallIntStrs2;
    
    public NumberOutput() {
        super();
    }
    
    public static int outputInt(int v, final char[] b, int off) {
        if (v < 0) {
            if (v == Integer.MIN_VALUE) {
                return _outputSmallestI(b, off);
            }
            b[off++] = '-';
            v = -v;
        }
        if (v < NumberOutput.MILLION) {
            if (v >= 1000) {
                final int thousands = v / 1000;
                v -= thousands * 1000;
                off = _leading3(thousands, b, off);
                off = _full3(v, b, off);
                return off;
            }
            if (v < 10) {
                b[off] = (char)(48 + v);
                return off + 1;
            }
            return _leading3(v, b, off);
        }
        else {
            if (v >= NumberOutput.BILLION) {
                v -= NumberOutput.BILLION;
                if (v >= NumberOutput.BILLION) {
                    v -= NumberOutput.BILLION;
                    b[off++] = '2';
                }
                else {
                    b[off++] = '1';
                }
                return _outputFullBillion(v, b, off);
            }
            int newValue = v / 1000;
            final int ones = v - newValue * 1000;
            v = newValue;
            newValue /= 1000;
            final int thousands2 = v - newValue * 1000;
            off = _leading3(newValue, b, off);
            off = _full3(thousands2, b, off);
            return _full3(ones, b, off);
        }
    }
    
    public static int outputInt(int v, final byte[] b, int off) {
        if (v < 0) {
            if (v == Integer.MIN_VALUE) {
                return _outputSmallestI(b, off);
            }
            b[off++] = 45;
            v = -v;
        }
        if (v < NumberOutput.MILLION) {
            if (v < 1000) {
                if (v < 10) {
                    b[off++] = (byte)(48 + v);
                }
                else {
                    off = _leading3(v, b, off);
                }
            }
            else {
                final int thousands = v / 1000;
                v -= thousands * 1000;
                off = _leading3(thousands, b, off);
                off = _full3(v, b, off);
            }
            return off;
        }
        if (v >= NumberOutput.BILLION) {
            v -= NumberOutput.BILLION;
            if (v >= NumberOutput.BILLION) {
                v -= NumberOutput.BILLION;
                b[off++] = 50;
            }
            else {
                b[off++] = 49;
            }
            return _outputFullBillion(v, b, off);
        }
        int newValue = v / 1000;
        final int ones = v - newValue * 1000;
        v = newValue;
        newValue /= 1000;
        final int thousands2 = v - newValue * 1000;
        off = _leading3(newValue, b, off);
        off = _full3(thousands2, b, off);
        return _full3(ones, b, off);
    }
    
    public static int outputLong(long v, final char[] b, int off) {
        if (v < 0L) {
            if (v > NumberOutput.MIN_INT_AS_LONG) {
                return outputInt((int)v, b, off);
            }
            if (v == Long.MIN_VALUE) {
                return _outputSmallestL(b, off);
            }
            b[off++] = '-';
            v = -v;
        }
        else if (v <= NumberOutput.MAX_INT_AS_LONG) {
            return outputInt((int)v, b, off);
        }
        long upper = v / NumberOutput.BILLION_L;
        v -= upper * NumberOutput.BILLION_L;
        if (upper < NumberOutput.BILLION_L) {
            off = _outputUptoBillion((int)upper, b, off);
        }
        else {
            final long hi = upper / NumberOutput.BILLION_L;
            upper -= hi * NumberOutput.BILLION_L;
            off = _leading3((int)hi, b, off);
            off = _outputFullBillion((int)upper, b, off);
        }
        return _outputFullBillion((int)v, b, off);
    }
    
    public static int outputLong(long v, final byte[] b, int off) {
        if (v < 0L) {
            if (v > NumberOutput.MIN_INT_AS_LONG) {
                return outputInt((int)v, b, off);
            }
            if (v == Long.MIN_VALUE) {
                return _outputSmallestL(b, off);
            }
            b[off++] = 45;
            v = -v;
        }
        else if (v <= NumberOutput.MAX_INT_AS_LONG) {
            return outputInt((int)v, b, off);
        }
        long upper = v / NumberOutput.BILLION_L;
        v -= upper * NumberOutput.BILLION_L;
        if (upper < NumberOutput.BILLION_L) {
            off = _outputUptoBillion((int)upper, b, off);
        }
        else {
            final long hi = upper / NumberOutput.BILLION_L;
            upper -= hi * NumberOutput.BILLION_L;
            off = _leading3((int)hi, b, off);
            off = _outputFullBillion((int)upper, b, off);
        }
        return _outputFullBillion((int)v, b, off);
    }
    
    public static String toString(final int v) {
        if (v < NumberOutput.sSmallIntStrs.length) {
            if (v >= 0) {
                return NumberOutput.sSmallIntStrs[v];
            }
            final int v2 = -v - 1;
            if (v2 < NumberOutput.sSmallIntStrs2.length) {
                return NumberOutput.sSmallIntStrs2[v2];
            }
        }
        return Integer.toString(v);
    }
    
    public static String toString(final long v) {
        if (v <= 2147483647L && v >= -2147483648L) {
            return toString((int)v);
        }
        return Long.toString(v);
    }
    
    public static String toString(final double v) {
        return Double.toString(v);
    }
    
    public static String toString(final float v) {
        return Float.toString(v);
    }
    
    private static int _outputUptoBillion(final int v, final char[] b, int off) {
        if (v >= NumberOutput.MILLION) {
            int thousands = v / 1000;
            final int ones = v - thousands * 1000;
            final int millions = thousands / 1000;
            thousands -= millions * 1000;
            off = _leading3(millions, b, off);
            int enc = NumberOutput.TRIPLET_TO_CHARS[thousands];
            b[off++] = (char)(enc >> 16);
            b[off++] = (char)(enc >> 8 & 0x7F);
            b[off++] = (char)(enc & 0x7F);
            enc = NumberOutput.TRIPLET_TO_CHARS[ones];
            b[off++] = (char)(enc >> 16);
            b[off++] = (char)(enc >> 8 & 0x7F);
            b[off++] = (char)(enc & 0x7F);
            return off;
        }
        if (v < 1000) {
            return _leading3(v, b, off);
        }
        int thousands = v / 1000;
        final int ones = v - thousands * 1000;
        return _outputUptoMillion(b, off, thousands, ones);
    }
    
    private static int _outputFullBillion(final int v, final char[] b, int off) {
        int thousands = v / 1000;
        final int ones = v - thousands * 1000;
        final int millions = thousands / 1000;
        int enc = NumberOutput.TRIPLET_TO_CHARS[millions];
        b[off++] = (char)(enc >> 16);
        b[off++] = (char)(enc >> 8 & 0x7F);
        b[off++] = (char)(enc & 0x7F);
        thousands -= millions * 1000;
        enc = NumberOutput.TRIPLET_TO_CHARS[thousands];
        b[off++] = (char)(enc >> 16);
        b[off++] = (char)(enc >> 8 & 0x7F);
        b[off++] = (char)(enc & 0x7F);
        enc = NumberOutput.TRIPLET_TO_CHARS[ones];
        b[off++] = (char)(enc >> 16);
        b[off++] = (char)(enc >> 8 & 0x7F);
        b[off++] = (char)(enc & 0x7F);
        return off;
    }
    
    private static int _outputUptoBillion(final int v, final byte[] b, int off) {
        if (v >= NumberOutput.MILLION) {
            int thousands = v / 1000;
            final int ones = v - thousands * 1000;
            final int millions = thousands / 1000;
            thousands -= millions * 1000;
            off = _leading3(millions, b, off);
            int enc = NumberOutput.TRIPLET_TO_CHARS[thousands];
            b[off++] = (byte)(enc >> 16);
            b[off++] = (byte)(enc >> 8);
            b[off++] = (byte)enc;
            enc = NumberOutput.TRIPLET_TO_CHARS[ones];
            b[off++] = (byte)(enc >> 16);
            b[off++] = (byte)(enc >> 8);
            b[off++] = (byte)enc;
            return off;
        }
        if (v < 1000) {
            return _leading3(v, b, off);
        }
        int thousands = v / 1000;
        final int ones = v - thousands * 1000;
        return _outputUptoMillion(b, off, thousands, ones);
    }
    
    private static int _outputFullBillion(final int v, final byte[] b, int off) {
        int thousands = v / 1000;
        final int ones = v - thousands * 1000;
        final int millions = thousands / 1000;
        thousands -= millions * 1000;
        int enc = NumberOutput.TRIPLET_TO_CHARS[millions];
        b[off++] = (byte)(enc >> 16);
        b[off++] = (byte)(enc >> 8);
        b[off++] = (byte)enc;
        enc = NumberOutput.TRIPLET_TO_CHARS[thousands];
        b[off++] = (byte)(enc >> 16);
        b[off++] = (byte)(enc >> 8);
        b[off++] = (byte)enc;
        enc = NumberOutput.TRIPLET_TO_CHARS[ones];
        b[off++] = (byte)(enc >> 16);
        b[off++] = (byte)(enc >> 8);
        b[off++] = (byte)enc;
        return off;
    }
    
    private static int _outputUptoMillion(final char[] b, int off, final int thousands, final int ones) {
        int enc = NumberOutput.TRIPLET_TO_CHARS[thousands];
        if (thousands > 9) {
            if (thousands > 99) {
                b[off++] = (char)(enc >> 16);
            }
            b[off++] = (char)(enc >> 8 & 0x7F);
        }
        b[off++] = (char)(enc & 0x7F);
        enc = NumberOutput.TRIPLET_TO_CHARS[ones];
        b[off++] = (char)(enc >> 16);
        b[off++] = (char)(enc >> 8 & 0x7F);
        b[off++] = (char)(enc & 0x7F);
        return off;
    }
    
    private static int _outputUptoMillion(final byte[] b, int off, final int thousands, final int ones) {
        int enc = NumberOutput.TRIPLET_TO_CHARS[thousands];
        if (thousands > 9) {
            if (thousands > 99) {
                b[off++] = (byte)(enc >> 16);
            }
            b[off++] = (byte)(enc >> 8);
        }
        b[off++] = (byte)enc;
        enc = NumberOutput.TRIPLET_TO_CHARS[ones];
        b[off++] = (byte)(enc >> 16);
        b[off++] = (byte)(enc >> 8);
        b[off++] = (byte)enc;
        return off;
    }
    
    private static int _leading3(final int t, final char[] b, int off) {
        final int enc = NumberOutput.TRIPLET_TO_CHARS[t];
        if (t > 9) {
            if (t > 99) {
                b[off++] = (char)(enc >> 16);
            }
            b[off++] = (char)(enc >> 8 & 0x7F);
        }
        b[off++] = (char)(enc & 0x7F);
        return off;
    }
    
    private static int _leading3(final int t, final byte[] b, int off) {
        final int enc = NumberOutput.TRIPLET_TO_CHARS[t];
        if (t > 9) {
            if (t > 99) {
                b[off++] = (byte)(enc >> 16);
            }
            b[off++] = (byte)(enc >> 8);
        }
        b[off++] = (byte)enc;
        return off;
    }
    
    private static int _full3(final int t, final char[] b, int off) {
        final int enc = NumberOutput.TRIPLET_TO_CHARS[t];
        b[off++] = (char)(enc >> 16);
        b[off++] = (char)(enc >> 8 & 0x7F);
        b[off++] = (char)(enc & 0x7F);
        return off;
    }
    
    private static int _full3(final int t, final byte[] b, int off) {
        final int enc = NumberOutput.TRIPLET_TO_CHARS[t];
        b[off++] = (byte)(enc >> 16);
        b[off++] = (byte)(enc >> 8);
        b[off++] = (byte)enc;
        return off;
    }
    
    private static int _outputSmallestL(final char[] b, final int off) {
        final int len = NumberOutput.SMALLEST_LONG.length();
        NumberOutput.SMALLEST_LONG.getChars(0, len, b, off);
        return off + len;
    }
    
    private static int _outputSmallestL(final byte[] b, int off) {
        for (int len = NumberOutput.SMALLEST_LONG.length(), i = 0; i < len; ++i) {
            b[off++] = (byte)NumberOutput.SMALLEST_LONG.charAt(i);
        }
        return off;
    }
    
    private static int _outputSmallestI(final char[] b, final int off) {
        final int len = NumberOutput.SMALLEST_INT.length();
        NumberOutput.SMALLEST_INT.getChars(0, len, b, off);
        return off + len;
    }
    
    private static int _outputSmallestI(final byte[] b, int off) {
        for (int len = NumberOutput.SMALLEST_INT.length(), i = 0; i < len; ++i) {
            b[off++] = (byte)NumberOutput.SMALLEST_INT.charAt(i);
        }
        return off;
    }
    
    static {
        NumberOutput.MILLION = 1000000;
        NumberOutput.BILLION = 1000000000;
        NumberOutput.BILLION_L = 1000000000L;
        NumberOutput.MIN_INT_AS_LONG = -2147483648L;
        NumberOutput.MAX_INT_AS_LONG = 2147483647L;
        SMALLEST_INT = String.valueOf(Integer.MIN_VALUE);
        SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
        TRIPLET_TO_CHARS = new int[1000];
        int fullIx = 0;
        for (int i1 = 0; i1 < 10; ++i1) {
            for (int i2 = 0; i2 < 10; ++i2) {
                for (int i3 = 0; i3 < 10; ++i3) {
                    final int enc = i1 + 48 << 16 | i2 + 48 << 8 | i3 + 48;
                    NumberOutput.TRIPLET_TO_CHARS[fullIx++] = enc;
                }
            }
        }
        sSmallIntStrs = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        sSmallIntStrs2 = new String[] { "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10" };
    }
}
