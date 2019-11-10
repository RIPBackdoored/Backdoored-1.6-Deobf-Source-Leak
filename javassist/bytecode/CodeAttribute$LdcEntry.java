package javassist.bytecode;

static class LdcEntry
{
    LdcEntry next;
    int where;
    int index;
    
    LdcEntry() {
        super();
    }
    
    static byte[] doit(byte[] code, final LdcEntry ldc, final ExceptionTable etable, final CodeAttribute ca) throws BadBytecode {
        if (ldc != null) {
            code = CodeIterator.changeLdcToLdcW(code, etable, ca, ldc);
        }
        return code;
    }
}
