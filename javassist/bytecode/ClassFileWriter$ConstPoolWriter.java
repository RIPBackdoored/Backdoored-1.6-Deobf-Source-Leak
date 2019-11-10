package javassist.bytecode;

public static final class ConstPoolWriter
{
    ByteStream output;
    protected int startPos;
    protected int num;
    
    ConstPoolWriter(final ByteStream out) {
        super();
        this.output = out;
        this.startPos = out.getPos();
        this.num = 1;
        this.output.writeShort(1);
    }
    
    public int[] addClassInfo(final String[] classNames) {
        final int n = classNames.length;
        final int[] result = new int[n];
        for (int i = 0; i < n; ++i) {
            result[i] = this.addClassInfo(classNames[i]);
        }
        return result;
    }
    
    public int addClassInfo(final String jvmname) {
        final int utf8 = this.addUtf8Info(jvmname);
        this.output.write(7);
        this.output.writeShort(utf8);
        return this.num++;
    }
    
    public int addClassInfo(final int name) {
        this.output.write(7);
        this.output.writeShort(name);
        return this.num++;
    }
    
    public int addNameAndTypeInfo(final String name, final String type) {
        return this.addNameAndTypeInfo(this.addUtf8Info(name), this.addUtf8Info(type));
    }
    
    public int addNameAndTypeInfo(final int name, final int type) {
        this.output.write(12);
        this.output.writeShort(name);
        this.output.writeShort(type);
        return this.num++;
    }
    
    public int addFieldrefInfo(final int classInfo, final int nameAndTypeInfo) {
        this.output.write(9);
        this.output.writeShort(classInfo);
        this.output.writeShort(nameAndTypeInfo);
        return this.num++;
    }
    
    public int addMethodrefInfo(final int classInfo, final int nameAndTypeInfo) {
        this.output.write(10);
        this.output.writeShort(classInfo);
        this.output.writeShort(nameAndTypeInfo);
        return this.num++;
    }
    
    public int addInterfaceMethodrefInfo(final int classInfo, final int nameAndTypeInfo) {
        this.output.write(11);
        this.output.writeShort(classInfo);
        this.output.writeShort(nameAndTypeInfo);
        return this.num++;
    }
    
    public int addMethodHandleInfo(final int kind, final int index) {
        this.output.write(15);
        this.output.write(kind);
        this.output.writeShort(index);
        return this.num++;
    }
    
    public int addMethodTypeInfo(final int desc) {
        this.output.write(16);
        this.output.writeShort(desc);
        return this.num++;
    }
    
    public int addInvokeDynamicInfo(final int bootstrap, final int nameAndTypeInfo) {
        this.output.write(18);
        this.output.writeShort(bootstrap);
        this.output.writeShort(nameAndTypeInfo);
        return this.num++;
    }
    
    public int addStringInfo(final String str) {
        final int utf8 = this.addUtf8Info(str);
        this.output.write(8);
        this.output.writeShort(utf8);
        return this.num++;
    }
    
    public int addIntegerInfo(final int i) {
        this.output.write(3);
        this.output.writeInt(i);
        return this.num++;
    }
    
    public int addFloatInfo(final float f) {
        this.output.write(4);
        this.output.writeFloat(f);
        return this.num++;
    }
    
    public int addLongInfo(final long l) {
        this.output.write(5);
        this.output.writeLong(l);
        final int n = this.num;
        this.num += 2;
        return n;
    }
    
    public int addDoubleInfo(final double d) {
        this.output.write(6);
        this.output.writeDouble(d);
        final int n = this.num;
        this.num += 2;
        return n;
    }
    
    public int addUtf8Info(final String utf8) {
        this.output.write(1);
        this.output.writeUTF(utf8);
        return this.num++;
    }
    
    void end() {
        this.output.writeShort(this.startPos, this.num);
    }
}
