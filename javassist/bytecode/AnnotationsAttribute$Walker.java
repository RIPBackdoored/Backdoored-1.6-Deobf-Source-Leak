package javassist.bytecode;

static class Walker
{
    byte[] info;
    
    Walker(final byte[] attrInfo) {
        super();
        this.info = attrInfo;
    }
    
    final void parameters() throws Exception {
        final int numParam = this.info[0] & 0xFF;
        this.parameters(numParam, 1);
    }
    
    void parameters(final int numParam, int pos) throws Exception {
        for (int i = 0; i < numParam; ++i) {
            pos = this.annotationArray(pos);
        }
    }
    
    final void annotationArray() throws Exception {
        this.annotationArray(0);
    }
    
    final int annotationArray(final int pos) throws Exception {
        final int num = ByteArray.readU16bit(this.info, pos);
        return this.annotationArray(pos + 2, num);
    }
    
    int annotationArray(int pos, final int num) throws Exception {
        for (int i = 0; i < num; ++i) {
            pos = this.annotation(pos);
        }
        return pos;
    }
    
    final int annotation(final int pos) throws Exception {
        final int type = ByteArray.readU16bit(this.info, pos);
        final int numPairs = ByteArray.readU16bit(this.info, pos + 2);
        return this.annotation(pos + 4, type, numPairs);
    }
    
    int annotation(int pos, final int type, final int numPairs) throws Exception {
        for (int j = 0; j < numPairs; ++j) {
            pos = this.memberValuePair(pos);
        }
        return pos;
    }
    
    final int memberValuePair(final int pos) throws Exception {
        final int nameIndex = ByteArray.readU16bit(this.info, pos);
        return this.memberValuePair(pos + 2, nameIndex);
    }
    
    int memberValuePair(final int pos, final int nameIndex) throws Exception {
        return this.memberValue(pos);
    }
    
    final int memberValue(final int pos) throws Exception {
        final int tag = this.info[pos] & 0xFF;
        if (tag == 101) {
            final int typeNameIndex = ByteArray.readU16bit(this.info, pos + 1);
            final int constNameIndex = ByteArray.readU16bit(this.info, pos + 3);
            this.enumMemberValue(pos, typeNameIndex, constNameIndex);
            return pos + 5;
        }
        if (tag == 99) {
            final int index = ByteArray.readU16bit(this.info, pos + 1);
            this.classMemberValue(pos, index);
            return pos + 3;
        }
        if (tag == 64) {
            return this.annotationMemberValue(pos + 1);
        }
        if (tag == 91) {
            final int num = ByteArray.readU16bit(this.info, pos + 1);
            return this.arrayMemberValue(pos + 3, num);
        }
        final int index = ByteArray.readU16bit(this.info, pos + 1);
        this.constValueMember(tag, index);
        return pos + 3;
    }
    
    void constValueMember(final int tag, final int index) throws Exception {
    }
    
    void enumMemberValue(final int pos, final int typeNameIndex, final int constNameIndex) throws Exception {
    }
    
    void classMemberValue(final int pos, final int index) throws Exception {
    }
    
    int annotationMemberValue(final int pos) throws Exception {
        return this.annotation(pos);
    }
    
    int arrayMemberValue(int pos, final int num) throws Exception {
        for (int i = 0; i < num; ++i) {
            pos = this.memberValue(pos);
        }
        return pos;
    }
}
