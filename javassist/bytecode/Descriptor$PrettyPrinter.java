package javassist.bytecode;

import javassist.*;

static class PrettyPrinter
{
    PrettyPrinter() {
        super();
    }
    
    static String toString(final String desc) {
        final StringBuffer sbuf = new StringBuffer();
        if (desc.charAt(0) == '(') {
            int pos = 1;
            sbuf.append('(');
            while (desc.charAt(pos) != ')') {
                if (pos > 1) {
                    sbuf.append(',');
                }
                pos = readType(sbuf, pos, desc);
            }
            sbuf.append(')');
        }
        else {
            readType(sbuf, 0, desc);
        }
        return sbuf.toString();
    }
    
    static int readType(final StringBuffer sbuf, int pos, final String desc) {
        char c = desc.charAt(pos);
        int arrayDim = 0;
        while (c == '[') {
            ++arrayDim;
            c = desc.charAt(++pos);
        }
        if (c == 'L') {
            while (true) {
                c = desc.charAt(++pos);
                if (c == ';') {
                    break;
                }
                if (c == '/') {
                    c = '.';
                }
                sbuf.append(c);
            }
        }
        else {
            final CtClass t = Descriptor.toPrimitiveClass(c);
            sbuf.append(t.getName());
        }
        while (arrayDim-- > 0) {
            sbuf.append("[]");
        }
        return pos + 1;
    }
}
