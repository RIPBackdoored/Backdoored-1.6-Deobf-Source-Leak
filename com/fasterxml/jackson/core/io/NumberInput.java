package com.fasterxml.jackson.core.io;

import java.math.*;

public final class NumberInput
{
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
    static final long L_BILLION = 1000000000L;
    static final String MIN_LONG_STR_NO_SIGN;
    static final String MAX_LONG_STR;
    
    public NumberInput() {
        super();
    }
    
    public static int parseInt(final char[] ch, int off, int len) {
        int num = ch[off] - '0';
        if (len > 4) {
            num = num * 10 + (ch[++off] - '0');
            num = num * 10 + (ch[++off] - '0');
            num = num * 10 + (ch[++off] - '0');
            num = num * 10 + (ch[++off] - '0');
            len -= 4;
            if (len > 4) {
                num = num * 10 + (ch[++off] - '0');
                num = num * 10 + (ch[++off] - '0');
                num = num * 10 + (ch[++off] - '0');
                num = num * 10 + (ch[++off] - '0');
                return num;
            }
        }
        if (len > 1) {
            num = num * 10 + (ch[++off] - '0');
            if (len > 2) {
                num = num * 10 + (ch[++off] - '0');
                if (len > 3) {
                    num = num * 10 + (ch[++off] - '0');
                }
            }
        }
        return num;
    }
    
    public static int parseInt(final String s) {
        char c = s.charAt(0);
        final int len = s.length();
        final boolean neg = c == '-';
        int offset = 1;
        if (neg) {
            if (len == 1 || len > 10) {
                return Integer.parseInt(s);
            }
            c = s.charAt(offset++);
        }
        else if (len > 9) {
            return Integer.parseInt(s);
        }
        if (c > '9' || c < '0') {
            return Integer.parseInt(s);
        }
        int num = c - '0';
        if (offset < len) {
            c = s.charAt(offset++);
            if (c > '9' || c < '0') {
                return Integer.parseInt(s);
            }
            num = num * 10 + (c - '0');
            if (offset < len) {
                c = s.charAt(offset++);
                if (c > '9' || c < '0') {
                    return Integer.parseInt(s);
                }
                num = num * 10 + (c - '0');
                if (offset < len) {
                    do {
                        c = s.charAt(offset++);
                        if (c > '9' || c < '0') {
                            return Integer.parseInt(s);
                        }
                        num = num * 10 + (c - '0');
                    } while (offset < len);
                }
            }
        }
        return neg ? (-num) : num;
    }
    
    public static long parseLong(final char[] ch, final int off, final int len) {
        final int len2 = len - 9;
        final long val = parseInt(ch, off, len2) * 1000000000L;
        return val + parseInt(ch, off + len2, 9);
    }
    
    public static long parseLong(final String s) {
        final int length = s.length();
        if (length <= 9) {
            return parseInt(s);
        }
        return Long.parseLong(s);
    }
    
    public static boolean inLongRange(final char[] ch, final int off, final int len, final boolean negative) {
        final String cmpStr = negative ? NumberInput.MIN_LONG_STR_NO_SIGN : NumberInput.MAX_LONG_STR;
        final int cmpLen = cmpStr.length();
        if (len < cmpLen) {
            return true;
        }
        if (len > cmpLen) {
            return false;
        }
        for (int i = 0; i < cmpLen; ++i) {
            final int diff = ch[off + i] - cmpStr.charAt(i);
            if (diff != 0) {
                return diff < 0;
            }
        }
        return true;
    }
    
    public static boolean inLongRange(final String s, final boolean negative) {
        final String cmp = negative ? NumberInput.MIN_LONG_STR_NO_SIGN : NumberInput.MAX_LONG_STR;
        final int cmpLen = cmp.length();
        final int alen = s.length();
        if (alen < cmpLen) {
            return true;
        }
        if (alen > cmpLen) {
            return false;
        }
        for (int i = 0; i < cmpLen; ++i) {
            final int diff = s.charAt(i) - cmp.charAt(i);
            if (diff != 0) {
                return diff < 0;
            }
        }
        return true;
    }
    
    public static int parseAsInt(String s, final int def) {
        if (s == null) {
            return def;
        }
        s = s.trim();
        int len = s.length();
        if (len == 0) {
            return def;
        }
        int i = 0;
        if (i < len) {
            final char c = s.charAt(0);
            if (c == '+') {
                s = s.substring(1);
                len = s.length();
            }
            else if (c == '-') {
                ++i;
            }
        }
        while (i < len) {
            final char c = s.charAt(i);
            Label_0103: {
                if (c <= '9') {
                    if (c >= '0') {
                        break Label_0103;
                    }
                }
                try {
                    return (int)parseDouble(s);
                }
                catch (NumberFormatException e) {
                    return def;
                }
            }
            ++i;
        }
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e2) {
            return def;
        }
    }
    
    public static long parseAsLong(String s, final long def) {
        if (s == null) {
            return def;
        }
        s = s.trim();
        int len = s.length();
        if (len == 0) {
            return def;
        }
        int i = 0;
        if (i < len) {
            final char c = s.charAt(0);
            if (c == '+') {
                s = s.substring(1);
                len = s.length();
            }
            else if (c == '-') {
                ++i;
            }
        }
        while (i < len) {
            final char c = s.charAt(i);
            Label_0107: {
                if (c <= '9') {
                    if (c >= '0') {
                        break Label_0107;
                    }
                }
                try {
                    return (long)parseDouble(s);
                }
                catch (NumberFormatException e) {
                    return def;
                }
            }
            ++i;
        }
        try {
            return Long.parseLong(s);
        }
        catch (NumberFormatException e2) {
            return def;
        }
    }
    
    public static double parseAsDouble(String s, final double def) {
        if (s == null) {
            return def;
        }
        s = s.trim();
        final int len = s.length();
        if (len == 0) {
            return def;
        }
        try {
            return parseDouble(s);
        }
        catch (NumberFormatException e) {
            return def;
        }
    }
    
    public static double parseDouble(final String s) throws NumberFormatException {
        if ("2.2250738585072012e-308".equals(s)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(s);
    }
    
    public static BigDecimal parseBigDecimal(final String s) throws NumberFormatException {
        try {
            return new BigDecimal(s);
        }
        catch (NumberFormatException e) {
            throw _badBD(s);
        }
    }
    
    public static BigDecimal parseBigDecimal(final char[] b) throws NumberFormatException {
        return parseBigDecimal(b, 0, b.length);
    }
    
    public static BigDecimal parseBigDecimal(final char[] b, final int off, final int len) throws NumberFormatException {
        try {
            return new BigDecimal(b, off, len);
        }
        catch (NumberFormatException e) {
            throw _badBD(new String(b, off, len));
        }
    }
    
    private static NumberFormatException _badBD(final String s) {
        return new NumberFormatException("Value \"" + s + "\" can not be represented as BigDecimal");
    }
    
    static {
        MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
        MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
    }
}
