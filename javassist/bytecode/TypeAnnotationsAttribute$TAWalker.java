package javassist.bytecode;

static class TAWalker extends AnnotationsAttribute.Walker
{
    SubWalker subWalker;
    
    TAWalker(final byte[] attrInfo) {
        super(attrInfo);
        this.subWalker = new SubWalker(attrInfo);
    }
    
    @Override
    int annotationArray(int pos, final int num) throws Exception {
        for (int i = 0; i < num; ++i) {
            final int targetType = this.info[pos] & 0xFF;
            pos = this.subWalker.targetInfo(pos + 1, targetType);
            pos = this.subWalker.typePath(pos);
            pos = this.annotation(pos);
        }
        return pos;
    }
}
