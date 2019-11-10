package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;

public static class UninitThis extends UninitData
{
    UninitThis(final String className) {
        super(-1, className);
    }
    
    @Override
    public UninitData copy() {
        return new UninitThis(this.getName());
    }
    
    @Override
    public int getTypeTag() {
        return 6;
    }
    
    @Override
    public int getTypeData(final ConstPool cp) {
        return 0;
    }
    
    @Override
    String toString2(final HashSet set) {
        return "uninit:this";
    }
}
