package javassist.bytecode;

import java.util.*;
import javassist.bytecode.annotation.*;

static class SubCopier extends SubWalker
{
    ConstPool srcPool;
    ConstPool destPool;
    Map classnames;
    TypeAnnotationsWriter writer;
    
    SubCopier(final byte[] attrInfo, final ConstPool src, final ConstPool dest, final Map map, final TypeAnnotationsWriter w) {
        super(attrInfo);
        this.srcPool = src;
        this.destPool = dest;
        this.classnames = map;
        this.writer = w;
    }
    
    @Override
    void typeParameterTarget(final int pos, final int targetType, final int typeParameterIndex) throws Exception {
        this.writer.typeParameterTarget(targetType, typeParameterIndex);
    }
    
    @Override
    void supertypeTarget(final int pos, final int superTypeIndex) throws Exception {
        this.writer.supertypeTarget(superTypeIndex);
    }
    
    @Override
    void typeParameterBoundTarget(final int pos, final int targetType, final int typeParameterIndex, final int boundIndex) throws Exception {
        this.writer.typeParameterBoundTarget(targetType, typeParameterIndex, boundIndex);
    }
    
    @Override
    void emptyTarget(final int pos, final int targetType) throws Exception {
        this.writer.emptyTarget(targetType);
    }
    
    @Override
    void formalParameterTarget(final int pos, final int formalParameterIndex) throws Exception {
        this.writer.formalParameterTarget(formalParameterIndex);
    }
    
    @Override
    void throwsTarget(final int pos, final int throwsTypeIndex) throws Exception {
        this.writer.throwsTarget(throwsTypeIndex);
    }
    
    @Override
    int localvarTarget(final int pos, final int targetType, final int tableLength) throws Exception {
        this.writer.localVarTarget(targetType, tableLength);
        return super.localvarTarget(pos, targetType, tableLength);
    }
    
    @Override
    void localvarTarget(final int pos, final int targetType, final int startPc, final int length, final int index) throws Exception {
        this.writer.localVarTargetTable(startPc, length, index);
    }
    
    @Override
    void catchTarget(final int pos, final int exceptionTableIndex) throws Exception {
        this.writer.catchTarget(exceptionTableIndex);
    }
    
    @Override
    void offsetTarget(final int pos, final int targetType, final int offset) throws Exception {
        this.writer.offsetTarget(targetType, offset);
    }
    
    @Override
    void typeArgumentTarget(final int pos, final int targetType, final int offset, final int typeArgumentIndex) throws Exception {
        this.writer.typeArgumentTarget(targetType, offset, typeArgumentIndex);
    }
    
    @Override
    int typePath(final int pos, final int pathLength) throws Exception {
        this.writer.typePath(pathLength);
        return super.typePath(pos, pathLength);
    }
    
    @Override
    void typePath(final int pos, final int typePathKind, final int typeArgumentIndex) throws Exception {
        this.writer.typePathPath(typePathKind, typeArgumentIndex);
    }
}
