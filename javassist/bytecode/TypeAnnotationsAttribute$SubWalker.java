package javassist.bytecode;

static class SubWalker
{
    byte[] info;
    
    SubWalker(final byte[] attrInfo) {
        super();
        this.info = attrInfo;
    }
    
    final int targetInfo(final int pos, final int type) throws Exception {
        switch (type) {
            case 0:
            case 1: {
                final int index = this.info[pos] & 0xFF;
                this.typeParameterTarget(pos, type, index);
                return pos + 1;
            }
            case 16: {
                final int index = ByteArray.readU16bit(this.info, pos);
                this.supertypeTarget(pos, index);
                return pos + 2;
            }
            case 17:
            case 18: {
                final int param = this.info[pos] & 0xFF;
                final int bound = this.info[pos + 1] & 0xFF;
                this.typeParameterBoundTarget(pos, type, param, bound);
                return pos + 2;
            }
            case 19:
            case 20:
            case 21: {
                this.emptyTarget(pos, type);
                return pos;
            }
            case 22: {
                final int index = this.info[pos] & 0xFF;
                this.formalParameterTarget(pos, index);
                return pos + 1;
            }
            case 23: {
                final int index = ByteArray.readU16bit(this.info, pos);
                this.throwsTarget(pos, index);
                return pos + 2;
            }
            case 64:
            case 65: {
                final int len = ByteArray.readU16bit(this.info, pos);
                return this.localvarTarget(pos + 2, type, len);
            }
            case 66: {
                final int index = ByteArray.readU16bit(this.info, pos);
                this.catchTarget(pos, index);
                return pos + 2;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
                final int offset = ByteArray.readU16bit(this.info, pos);
                this.offsetTarget(pos, type, offset);
                return pos + 2;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                final int offset = ByteArray.readU16bit(this.info, pos);
                final int index2 = this.info[pos + 2] & 0xFF;
                this.typeArgumentTarget(pos, type, offset, index2);
                return pos + 3;
            }
            default: {
                throw new RuntimeException("invalid target type: " + type);
            }
        }
    }
    
    void typeParameterTarget(final int pos, final int targetType, final int typeParameterIndex) throws Exception {
    }
    
    void supertypeTarget(final int pos, final int superTypeIndex) throws Exception {
    }
    
    void typeParameterBoundTarget(final int pos, final int targetType, final int typeParameterIndex, final int boundIndex) throws Exception {
    }
    
    void emptyTarget(final int pos, final int targetType) throws Exception {
    }
    
    void formalParameterTarget(final int pos, final int formalParameterIndex) throws Exception {
    }
    
    void throwsTarget(final int pos, final int throwsTypeIndex) throws Exception {
    }
    
    int localvarTarget(int pos, final int targetType, final int tableLength) throws Exception {
        for (int i = 0; i < tableLength; ++i) {
            final int start = ByteArray.readU16bit(this.info, pos);
            final int length = ByteArray.readU16bit(this.info, pos + 2);
            final int index = ByteArray.readU16bit(this.info, pos + 4);
            this.localvarTarget(pos, targetType, start, length, index);
            pos += 6;
        }
        return pos;
    }
    
    void localvarTarget(final int pos, final int targetType, final int startPc, final int length, final int index) throws Exception {
    }
    
    void catchTarget(final int pos, final int exceptionTableIndex) throws Exception {
    }
    
    void offsetTarget(final int pos, final int targetType, final int offset) throws Exception {
    }
    
    void typeArgumentTarget(final int pos, final int targetType, final int offset, final int typeArgumentIndex) throws Exception {
    }
    
    final int typePath(int pos) throws Exception {
        final int len = this.info[pos++] & 0xFF;
        return this.typePath(pos, len);
    }
    
    int typePath(int pos, final int pathLength) throws Exception {
        for (int i = 0; i < pathLength; ++i) {
            final int kind = this.info[pos] & 0xFF;
            final int index = this.info[pos + 1] & 0xFF;
            this.typePath(pos, kind, index);
            pos += 2;
        }
        return pos;
    }
    
    void typePath(final int pos, final int typePathKind, final int typeArgumentIndex) throws Exception {
    }
}
