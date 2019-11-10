package org.reflections.adapters;

import com.google.common.collect.*;
import javassist.bytecode.annotation.*;
import org.reflections.vfs.*;
import org.reflections.*;
import java.io.*;
import org.reflections.util.*;
import com.google.common.base.*;
import java.util.*;
import javassist.bytecode.*;

public class JavassistAdapter implements MetadataAdapter<ClassFile, FieldInfo, MethodInfo>
{
    public static boolean includeInvisibleTag;
    
    public JavassistAdapter() {
        super();
    }
    
    @Override
    public List<FieldInfo> getFields(final ClassFile cls) {
        return (List<FieldInfo>)cls.getFields();
    }
    
    @Override
    public List<MethodInfo> getMethods(final ClassFile cls) {
        return (List<MethodInfo>)cls.getMethods();
    }
    
    @Override
    public String getMethodName(final MethodInfo method) {
        return method.getName();
    }
    
    @Override
    public List<String> getParameterNames(final MethodInfo method) {
        String descriptor = method.getDescriptor();
        descriptor = descriptor.substring(descriptor.indexOf("(") + 1, descriptor.lastIndexOf(")"));
        return this.splitDescriptorToTypeNames(descriptor);
    }
    
    @Override
    public List<String> getClassAnnotationNames(final ClassFile aClass) {
        return this.getAnnotationNames((AnnotationsAttribute)aClass.getAttribute("RuntimeVisibleAnnotations"), JavassistAdapter.includeInvisibleTag ? ((AnnotationsAttribute)aClass.getAttribute("RuntimeInvisibleAnnotations")) : null);
    }
    
    @Override
    public List<String> getFieldAnnotationNames(final FieldInfo field) {
        return this.getAnnotationNames((AnnotationsAttribute)field.getAttribute("RuntimeVisibleAnnotations"), JavassistAdapter.includeInvisibleTag ? ((AnnotationsAttribute)field.getAttribute("RuntimeInvisibleAnnotations")) : null);
    }
    
    @Override
    public List<String> getMethodAnnotationNames(final MethodInfo method) {
        return this.getAnnotationNames((AnnotationsAttribute)method.getAttribute("RuntimeVisibleAnnotations"), JavassistAdapter.includeInvisibleTag ? ((AnnotationsAttribute)method.getAttribute("RuntimeInvisibleAnnotations")) : null);
    }
    
    @Override
    public List<String> getParameterAnnotationNames(final MethodInfo method, final int parameterIndex) {
        final List<String> result = (List<String>)Lists.newArrayList();
        final List<ParameterAnnotationsAttribute> parameterAnnotationsAttributes = Lists.newArrayList((ParameterAnnotationsAttribute)method.getAttribute("RuntimeVisibleParameterAnnotations"), (ParameterAnnotationsAttribute)method.getAttribute("RuntimeInvisibleParameterAnnotations"));
        if (parameterAnnotationsAttributes != null) {
            for (final ParameterAnnotationsAttribute parameterAnnotationsAttribute : parameterAnnotationsAttributes) {
                if (parameterAnnotationsAttribute != null) {
                    final Annotation[][] annotations = parameterAnnotationsAttribute.getAnnotations();
                    if (parameterIndex >= annotations.length) {
                        continue;
                    }
                    final Annotation[] annotation = annotations[parameterIndex];
                    result.addAll(this.getAnnotationNames(annotation));
                }
            }
        }
        return result;
    }
    
    @Override
    public String getReturnTypeName(final MethodInfo method) {
        String descriptor = method.getDescriptor();
        descriptor = descriptor.substring(descriptor.lastIndexOf(")") + 1);
        return this.splitDescriptorToTypeNames(descriptor).get(0);
    }
    
    @Override
    public String getFieldName(final FieldInfo field) {
        return field.getName();
    }
    
    @Override
    public ClassFile getOfCreateClassObject(final Vfs.File file) {
        InputStream inputStream = null;
        try {
            inputStream = file.openInputStream();
            final DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
            return new ClassFile(dis);
        }
        catch (IOException e) {
            throw new ReflectionsException("could not create class file from " + file.getName(), e);
        }
        finally {
            Utils.close(inputStream);
        }
    }
    
    @Override
    public String getMethodModifier(final MethodInfo method) {
        final int accessFlags = method.getAccessFlags();
        return AccessFlag.isPrivate(accessFlags) ? "private" : (AccessFlag.isProtected(accessFlags) ? "protected" : (this.isPublic(accessFlags) ? "public" : ""));
    }
    
    @Override
    public String getMethodKey(final ClassFile cls, final MethodInfo method) {
        return this.getMethodName(method) + "(" + Joiner.on(", ").join(this.getParameterNames(method)) + ")";
    }
    
    @Override
    public String getMethodFullKey(final ClassFile cls, final MethodInfo method) {
        return this.getClassName(cls) + "." + this.getMethodKey(cls, method);
    }
    
    @Override
    public boolean isPublic(final Object o) {
        final Integer accessFlags = (o instanceof ClassFile) ? ((ClassFile)o).getAccessFlags() : ((o instanceof FieldInfo) ? ((FieldInfo)o).getAccessFlags() : ((o instanceof MethodInfo) ? Integer.valueOf(((MethodInfo)o).getAccessFlags()) : null));
        return accessFlags != null && AccessFlag.isPublic(accessFlags);
    }
    
    @Override
    public String getClassName(final ClassFile cls) {
        return cls.getName();
    }
    
    @Override
    public String getSuperclassName(final ClassFile cls) {
        return cls.getSuperclass();
    }
    
    @Override
    public List<String> getInterfacesNames(final ClassFile cls) {
        return Arrays.asList(cls.getInterfaces());
    }
    
    @Override
    public boolean acceptsInput(final String file) {
        return file.endsWith(".class");
    }
    
    private List<String> getAnnotationNames(final AnnotationsAttribute... annotationsAttributes) {
        final List<String> result = (List<String>)Lists.newArrayList();
        if (annotationsAttributes != null) {
            for (final AnnotationsAttribute annotationsAttribute : annotationsAttributes) {
                if (annotationsAttribute != null) {
                    for (final Annotation annotation : annotationsAttribute.getAnnotations()) {
                        result.add(annotation.getTypeName());
                    }
                }
            }
        }
        return result;
    }
    
    private List<String> getAnnotationNames(final Annotation[] annotations) {
        final List<String> result = (List<String>)Lists.newArrayList();
        for (final Annotation annotation : annotations) {
            result.add(annotation.getTypeName());
        }
        return result;
    }
    
    private List<String> splitDescriptorToTypeNames(final String descriptors) {
        final List<String> result = (List<String>)Lists.newArrayList();
        if (descriptors != null && descriptors.length() != 0) {
            final List<Integer> indices = (List<Integer>)Lists.newArrayList();
            final Descriptor.Iterator iterator = new Descriptor.Iterator(descriptors);
            while (iterator.hasNext()) {
                indices.add(iterator.next());
            }
            indices.add(descriptors.length());
            for (int i = 0; i < indices.size() - 1; ++i) {
                final String s1 = Descriptor.toString(descriptors.substring(indices.get(i), indices.get(i + 1)));
                result.add(s1);
            }
        }
        return result;
    }
    
    @Override
    public /* bridge */ String getMethodFullKey(final Object o, final Object o2) {
        return this.getMethodFullKey((ClassFile)o, (MethodInfo)o2);
    }
    
    @Override
    public /* bridge */ String getMethodKey(final Object o, final Object o2) {
        return this.getMethodKey((ClassFile)o, (MethodInfo)o2);
    }
    
    @Override
    public /* bridge */ String getMethodModifier(final Object o) {
        return this.getMethodModifier((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ Object getOfCreateClassObject(final Vfs.File file) throws Exception {
        return this.getOfCreateClassObject(file);
    }
    
    @Override
    public /* bridge */ String getFieldName(final Object o) {
        return this.getFieldName((FieldInfo)o);
    }
    
    @Override
    public /* bridge */ String getReturnTypeName(final Object o) {
        return this.getReturnTypeName((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ List getParameterAnnotationNames(final Object o, final int parameterIndex) {
        return this.getParameterAnnotationNames((MethodInfo)o, parameterIndex);
    }
    
    @Override
    public /* bridge */ List getMethodAnnotationNames(final Object o) {
        return this.getMethodAnnotationNames((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ List getFieldAnnotationNames(final Object o) {
        return this.getFieldAnnotationNames((FieldInfo)o);
    }
    
    @Override
    public /* bridge */ List getClassAnnotationNames(final Object o) {
        return this.getClassAnnotationNames((ClassFile)o);
    }
    
    @Override
    public /* bridge */ List getParameterNames(final Object o) {
        return this.getParameterNames((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ String getMethodName(final Object o) {
        return this.getMethodName((MethodInfo)o);
    }
    
    @Override
    public /* bridge */ List getMethods(final Object o) {
        return this.getMethods((ClassFile)o);
    }
    
    @Override
    public /* bridge */ List getFields(final Object o) {
        return this.getFields((ClassFile)o);
    }
    
    @Override
    public /* bridge */ List getInterfacesNames(final Object o) {
        return this.getInterfacesNames((ClassFile)o);
    }
    
    @Override
    public /* bridge */ String getSuperclassName(final Object o) {
        return this.getSuperclassName((ClassFile)o);
    }
    
    @Override
    public /* bridge */ String getClassName(final Object o) {
        return this.getClassName((ClassFile)o);
    }
    
    static {
        JavassistAdapter.includeInvisibleTag = true;
    }
}
