package javassist.bytecode;

import javassist.bytecode.annotation.*;

static class Parser extends Walker
{
    ConstPool pool;
    Annotation[][] allParams;
    Annotation[] allAnno;
    Annotation currentAnno;
    MemberValue currentMember;
    
    Parser(final byte[] info, final ConstPool cp) {
        super(info);
        this.pool = cp;
    }
    
    Annotation[][] parseParameters() throws Exception {
        this.parameters();
        return this.allParams;
    }
    
    Annotation[] parseAnnotations() throws Exception {
        this.annotationArray();
        return this.allAnno;
    }
    
    MemberValue parseMemberValue() throws Exception {
        this.memberValue(0);
        return this.currentMember;
    }
    
    @Override
    void parameters(final int numParam, int pos) throws Exception {
        final Annotation[][] params = new Annotation[numParam][];
        for (int i = 0; i < numParam; ++i) {
            pos = this.annotationArray(pos);
            params[i] = this.allAnno;
        }
        this.allParams = params;
    }
    
    @Override
    int annotationArray(int pos, final int num) throws Exception {
        final Annotation[] array = new Annotation[num];
        for (int i = 0; i < num; ++i) {
            pos = this.annotation(pos);
            array[i] = this.currentAnno;
        }
        this.allAnno = array;
        return pos;
    }
    
    @Override
    int annotation(final int pos, final int type, final int numPairs) throws Exception {
        this.currentAnno = new Annotation(type, this.pool);
        return super.annotation(pos, type, numPairs);
    }
    
    @Override
    int memberValuePair(int pos, final int nameIndex) throws Exception {
        pos = super.memberValuePair(pos, nameIndex);
        this.currentAnno.addMemberValue(nameIndex, this.currentMember);
        return pos;
    }
    
    @Override
    void constValueMember(final int tag, final int index) throws Exception {
        final ConstPool cp = this.pool;
        MemberValue m = null;
        switch (tag) {
            case 66: {
                m = new ByteMemberValue(index, cp);
                break;
            }
            case 67: {
                m = new CharMemberValue(index, cp);
                break;
            }
            case 68: {
                m = new DoubleMemberValue(index, cp);
                break;
            }
            case 70: {
                m = new FloatMemberValue(index, cp);
                break;
            }
            case 73: {
                m = new IntegerMemberValue(index, cp);
                break;
            }
            case 74: {
                m = new LongMemberValue(index, cp);
                break;
            }
            case 83: {
                m = new ShortMemberValue(index, cp);
                break;
            }
            case 90: {
                m = new BooleanMemberValue(index, cp);
                break;
            }
            case 115: {
                m = new StringMemberValue(index, cp);
                break;
            }
            default: {
                throw new RuntimeException("unknown tag:" + tag);
            }
        }
        this.currentMember = m;
        super.constValueMember(tag, index);
    }
    
    @Override
    void enumMemberValue(final int pos, final int typeNameIndex, final int constNameIndex) throws Exception {
        this.currentMember = new EnumMemberValue(typeNameIndex, constNameIndex, this.pool);
        super.enumMemberValue(pos, typeNameIndex, constNameIndex);
    }
    
    @Override
    void classMemberValue(final int pos, final int index) throws Exception {
        this.currentMember = new ClassMemberValue(index, this.pool);
        super.classMemberValue(pos, index);
    }
    
    @Override
    int annotationMemberValue(int pos) throws Exception {
        final Annotation anno = this.currentAnno;
        pos = super.annotationMemberValue(pos);
        this.currentMember = new AnnotationMemberValue(this.currentAnno, this.pool);
        this.currentAnno = anno;
        return pos;
    }
    
    @Override
    int arrayMemberValue(int pos, final int num) throws Exception {
        final ArrayMemberValue amv = new ArrayMemberValue(this.pool);
        final MemberValue[] elements = new MemberValue[num];
        for (int i = 0; i < num; ++i) {
            pos = this.memberValue(pos);
            elements[i] = this.currentMember;
        }
        amv.setValue(elements);
        this.currentMember = amv;
        return pos;
    }
}
