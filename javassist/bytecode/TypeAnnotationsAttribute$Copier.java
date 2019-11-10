package javassist.bytecode;

import java.util.*;
import javassist.bytecode.annotation.*;
import java.io.*;

static class Copier extends AnnotationsAttribute.Copier
{
    SubCopier sub;
    
    Copier(final byte[] attrInfo, final ConstPool src, final ConstPool dest, final Map map) {
        super(attrInfo, src, dest, map, false);
        final TypeAnnotationsWriter w = new TypeAnnotationsWriter(this.output, dest);
        this.writer = w;
        this.sub = new SubCopier(attrInfo, src, dest, map, w);
    }
    
    @Override
    int annotationArray(int pos, final int num) throws Exception {
        this.writer.numAnnotations(num);
        for (int i = 0; i < num; ++i) {
            final int targetType = this.info[pos] & 0xFF;
            pos = this.sub.targetInfo(pos + 1, targetType);
            pos = this.sub.typePath(pos);
            pos = this.annotation(pos);
        }
        return pos;
    }
}
