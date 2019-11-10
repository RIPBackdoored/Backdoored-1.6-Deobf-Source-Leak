package javassist.bytecode;

import javassist.bytecode.annotation.*;
import java.util.*;
import java.io.*;

static class Copier extends Walker
{
    ByteArrayOutputStream output;
    AnnotationsWriter writer;
    ConstPool srcPool;
    ConstPool destPool;
    Map classnames;
    
    Copier(final byte[] info, final ConstPool src, final ConstPool dest, final Map map) {
        this(info, src, dest, map, true);
    }
    
    Copier(final byte[] info, final ConstPool src, final ConstPool dest, final Map map, final boolean makeWriter) {
        super(info);
        this.output = new ByteArrayOutputStream();
        if (makeWriter) {
            this.writer = new AnnotationsWriter(this.output, dest);
        }
        this.srcPool = src;
        this.destPool = dest;
        this.classnames = map;
    }
    
    byte[] close() throws IOException {
        this.writer.close();
        return this.output.toByteArray();
    }
    
    @Override
    void parameters(final int numParam, final int pos) throws Exception {
        this.writer.numParameters(numParam);
        super.parameters(numParam, pos);
    }
    
    @Override
    int annotationArray(final int pos, final int num) throws Exception {
        this.writer.numAnnotations(num);
        return super.annotationArray(pos, num);
    }
    
    @Override
    int annotation(final int pos, final int type, final int numPairs) throws Exception {
        this.writer.annotation(this.copyType(type), numPairs);
        return super.annotation(pos, type, numPairs);
    }
    
    @Override
    int memberValuePair(final int pos, final int nameIndex) throws Exception {
        this.writer.memberValuePair(this.copy(nameIndex));
        return super.memberValuePair(pos, nameIndex);
    }
    
    @Override
    void constValueMember(final int tag, final int index) throws Exception {
        this.writer.constValueIndex(tag, this.copy(index));
        super.constValueMember(tag, index);
    }
    
    @Override
    void enumMemberValue(final int pos, final int typeNameIndex, final int constNameIndex) throws Exception {
        this.writer.enumConstValue(this.copyType(typeNameIndex), this.copy(constNameIndex));
        super.enumMemberValue(pos, typeNameIndex, constNameIndex);
    }
    
    @Override
    void classMemberValue(final int pos, final int index) throws Exception {
        this.writer.classInfoIndex(this.copyType(index));
        super.classMemberValue(pos, index);
    }
    
    @Override
    int annotationMemberValue(final int pos) throws Exception {
        this.writer.annotationValue();
        return super.annotationMemberValue(pos);
    }
    
    @Override
    int arrayMemberValue(final int pos, final int num) throws Exception {
        this.writer.arrayValue(num);
        return super.arrayMemberValue(pos, num);
    }
    
    int copy(final int srcIndex) {
        return this.srcPool.copy(srcIndex, this.destPool, this.classnames);
    }
    
    int copyType(final int srcIndex) {
        final String name = this.srcPool.getUtf8Info(srcIndex);
        final String newName = Descriptor.rename(name, this.classnames);
        return this.destPool.addUtf8Info(newName);
    }
}
