package javassist.bytecode;

import java.util.*;

static class Renamer extends AnnotationsAttribute.Renamer
{
    SubWalker sub;
    
    Renamer(final byte[] attrInfo, final ConstPool cp, final Map map) {
        super(attrInfo, cp, map);
        this.sub = new SubWalker(attrInfo);
    }
    
    @Override
    int annotationArray(int pos, final int num) throws Exception {
        for (int i = 0; i < num; ++i) {
            final int targetType = this.info[pos] & 0xFF;
            pos = this.sub.targetInfo(pos + 1, targetType);
            pos = this.sub.typePath(pos);
            pos = this.annotation(pos);
        }
        return pos;
    }
}
