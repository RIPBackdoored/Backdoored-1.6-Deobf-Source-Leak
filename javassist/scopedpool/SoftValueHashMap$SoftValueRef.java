package javassist.scopedpool;

import java.lang.ref.*;

private static class SoftValueRef extends SoftReference
{
    public Object key;
    
    private SoftValueRef(final Object key, final Object val, final ReferenceQueue q) {
        super(val, q);
        this.key = key;
    }
    
    private static SoftValueRef create(final Object key, final Object val, final ReferenceQueue q) {
        if (val == null) {
            return null;
        }
        return new SoftValueRef(key, val, q);
    }
    
    static /* synthetic */ SoftValueRef access$000(final Object x0, final Object x1, final ReferenceQueue x2) {
        return create(x0, x1, x2);
    }
}
