package com.sun.jna;

import java.lang.reflect.*;
import java.nio.*;
import java.util.*;

static class FFIType extends Structure
{
    private static final Map<Object, Object> typeInfoMap;
    private static final int FFI_TYPE_STRUCT = 13;
    public size_t size;
    public short alignment;
    public short type;
    public Pointer elements;
    
    private FFIType(final Structure ref) {
        super();
        this.type = 13;
        Structure.access$1900(ref, true);
        Pointer[] els;
        if (ref instanceof Union) {
            final StructField sf = ((Union)ref).typeInfoField();
            els = new Pointer[] { get(ref.getFieldValue(sf.field), sf.type), null };
        }
        else {
            els = new Pointer[ref.fields().size() + 1];
            int idx = 0;
            for (final StructField sf2 : ref.fields().values()) {
                els[idx++] = ref.getFieldTypeInfo(sf2);
            }
        }
        this.init(els);
    }
    
    private FFIType(final Object array, final Class<?> type) {
        super();
        this.type = 13;
        final int length = Array.getLength(array);
        final Pointer[] els = new Pointer[length + 1];
        final Pointer p = get(null, type.getComponentType());
        for (int i = 0; i < length; ++i) {
            els[i] = p;
        }
        this.init(els);
    }
    
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("size", "alignment", "type", "elements");
    }
    
    private void init(final Pointer[] els) {
        (this.elements = new Memory(Pointer.SIZE * els.length)).write(0L, els, 0, els.length);
        this.write();
    }
    
    static Pointer get(final Object obj) {
        if (obj == null) {
            return FFITypes.ffi_type_pointer;
        }
        if (obj instanceof Class) {
            return get(null, (Class<?>)obj);
        }
        return get(obj, obj.getClass());
    }
    
    private static Pointer get(Object obj, Class<?> cls) {
        final TypeMapper mapper = Native.getTypeMapper(cls);
        if (mapper != null) {
            final ToNativeConverter nc = mapper.getToNativeConverter(cls);
            if (nc != null) {
                cls = nc.nativeType();
            }
        }
        synchronized (FFIType.typeInfoMap) {
            final Object o = FFIType.typeInfoMap.get(cls);
            if (o instanceof Pointer) {
                return (Pointer)o;
            }
            if (o instanceof FFIType) {
                return ((FFIType)o).getPointer();
            }
            if ((Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(cls)) || Callback.class.isAssignableFrom(cls)) {
                FFIType.typeInfoMap.put(cls, FFITypes.ffi_type_pointer);
                return FFITypes.ffi_type_pointer;
            }
            if (Structure.class.isAssignableFrom(cls)) {
                if (obj == null) {
                    obj = Structure.newInstance(cls, Structure.access$2000());
                }
                if (ByReference.class.isAssignableFrom(cls)) {
                    FFIType.typeInfoMap.put(cls, FFITypes.ffi_type_pointer);
                    return FFITypes.ffi_type_pointer;
                }
                final FFIType type = new FFIType((Structure)obj);
                FFIType.typeInfoMap.put(cls, type);
                return type.getPointer();
            }
            else {
                if (NativeMapped.class.isAssignableFrom(cls)) {
                    final NativeMappedConverter c = NativeMappedConverter.getInstance(cls);
                    return get(c.toNative(obj, new ToNativeContext()), c.nativeType());
                }
                if (cls.isArray()) {
                    final FFIType type = new FFIType(obj, cls);
                    FFIType.typeInfoMap.put(obj, type);
                    return type.getPointer();
                }
                throw new IllegalArgumentException("Unsupported type " + cls);
            }
        }
    }
    
    static /* synthetic */ Pointer access$800(final Object x0, final Class x1) {
        return get(x0, x1);
    }
    
    static {
        typeInfoMap = new WeakHashMap<Object, Object>();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        if (FFITypes.ffi_type_void == null) {
            throw new Error("FFI types not initialized");
        }
        FFIType.typeInfoMap.put(Void.TYPE, FFITypes.ffi_type_void);
        FFIType.typeInfoMap.put(Void.class, FFITypes.ffi_type_void);
        FFIType.typeInfoMap.put(Float.TYPE, FFITypes.ffi_type_float);
        FFIType.typeInfoMap.put(Float.class, FFITypes.ffi_type_float);
        FFIType.typeInfoMap.put(Double.TYPE, FFITypes.ffi_type_double);
        FFIType.typeInfoMap.put(Double.class, FFITypes.ffi_type_double);
        FFIType.typeInfoMap.put(Long.TYPE, FFITypes.ffi_type_sint64);
        FFIType.typeInfoMap.put(Long.class, FFITypes.ffi_type_sint64);
        FFIType.typeInfoMap.put(Integer.TYPE, FFITypes.ffi_type_sint32);
        FFIType.typeInfoMap.put(Integer.class, FFITypes.ffi_type_sint32);
        FFIType.typeInfoMap.put(Short.TYPE, FFITypes.ffi_type_sint16);
        FFIType.typeInfoMap.put(Short.class, FFITypes.ffi_type_sint16);
        final Pointer ctype = (Native.WCHAR_SIZE == 2) ? FFITypes.ffi_type_uint16 : FFITypes.ffi_type_uint32;
        FFIType.typeInfoMap.put(Character.TYPE, ctype);
        FFIType.typeInfoMap.put(Character.class, ctype);
        FFIType.typeInfoMap.put(Byte.TYPE, FFITypes.ffi_type_sint8);
        FFIType.typeInfoMap.put(Byte.class, FFITypes.ffi_type_sint8);
        FFIType.typeInfoMap.put(Pointer.class, FFITypes.ffi_type_pointer);
        FFIType.typeInfoMap.put(String.class, FFITypes.ffi_type_pointer);
        FFIType.typeInfoMap.put(WString.class, FFITypes.ffi_type_pointer);
        FFIType.typeInfoMap.put(Boolean.TYPE, FFITypes.ffi_type_uint32);
        FFIType.typeInfoMap.put(Boolean.class, FFITypes.ffi_type_uint32);
    }
    
    public static class size_t extends IntegerType
    {
        private static final long serialVersionUID = 1L;
        
        public size_t() {
            this(0L);
        }
        
        public size_t(final long value) {
            super(Native.SIZE_T_SIZE, value);
        }
    }
    
    private static class FFITypes
    {
        private static Pointer ffi_type_void;
        private static Pointer ffi_type_float;
        private static Pointer ffi_type_double;
        private static Pointer ffi_type_longdouble;
        private static Pointer ffi_type_uint8;
        private static Pointer ffi_type_sint8;
        private static Pointer ffi_type_uint16;
        private static Pointer ffi_type_sint16;
        private static Pointer ffi_type_uint32;
        private static Pointer ffi_type_sint32;
        private static Pointer ffi_type_uint64;
        private static Pointer ffi_type_sint64;
        private static Pointer ffi_type_pointer;
        
        private FFITypes() {
            super();
        }
        
        static /* synthetic */ Pointer access$900() {
            return FFITypes.ffi_type_void;
        }
        
        static /* synthetic */ Pointer access$1000() {
            return FFITypes.ffi_type_float;
        }
        
        static /* synthetic */ Pointer access$1100() {
            return FFITypes.ffi_type_double;
        }
        
        static /* synthetic */ Pointer access$1200() {
            return FFITypes.ffi_type_sint64;
        }
        
        static /* synthetic */ Pointer access$1300() {
            return FFITypes.ffi_type_sint32;
        }
        
        static /* synthetic */ Pointer access$1400() {
            return FFITypes.ffi_type_sint16;
        }
        
        static /* synthetic */ Pointer access$1500() {
            return FFITypes.ffi_type_uint16;
        }
        
        static /* synthetic */ Pointer access$1600() {
            return FFITypes.ffi_type_uint32;
        }
        
        static /* synthetic */ Pointer access$1700() {
            return FFITypes.ffi_type_sint8;
        }
        
        static /* synthetic */ Pointer access$1800() {
            return FFITypes.ffi_type_pointer;
        }
    }
}
