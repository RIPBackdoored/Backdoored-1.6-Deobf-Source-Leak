package org.reflections;

import java.lang.annotation.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import javax.annotation.*;
import java.util.regex.*;
import com.google.common.collect.*;
import org.reflections.util.*;
import java.util.*;

public abstract class ReflectionUtils
{
    public static boolean includeObject;
    private static List<String> primitiveNames;
    private static List<Class> primitiveTypes;
    private static List<String> primitiveDescriptors;
    
    public ReflectionUtils() {
        super();
    }
    
    public static Set<Class<?>> getAllSuperTypes(final Class<?> type, final Predicate<? super Class<?>>... predicates) {
        final Set<Class<?>> result = (Set<Class<?>>)Sets.newLinkedHashSet();
        if (type != null && (ReflectionUtils.includeObject || !type.equals(Object.class))) {
            result.add(type);
            for (final Class<?> supertype : getSuperTypes(type)) {
                result.addAll(getAllSuperTypes(supertype, (Predicate<? super Class<?>>[])new Predicate[0]));
            }
        }
        return filter(result, predicates);
    }
    
    public static Set<Class<?>> getSuperTypes(final Class<?> type) {
        final Set<Class<?>> result = new LinkedHashSet<Class<?>>();
        final Class<?> superclass = type.getSuperclass();
        final Class<?>[] interfaces = type.getInterfaces();
        if (superclass != null && (ReflectionUtils.includeObject || !superclass.equals(Object.class))) {
            result.add(superclass);
        }
        if (interfaces != null && interfaces.length > 0) {
            result.addAll(Arrays.asList(interfaces));
        }
        return result;
    }
    
    public static Set<Method> getAllMethods(final Class<?> type, final Predicate<? super Method>... predicates) {
        final Set<Method> result = (Set<Method>)Sets.newHashSet();
        for (final Class<?> t : getAllSuperTypes(type, (Predicate<? super Class<?>>[])new Predicate[0])) {
            result.addAll(getMethods(t, predicates));
        }
        return result;
    }
    
    public static Set<Method> getMethods(final Class<?> t, final Predicate<? super Method>... predicates) {
        return filter(t.isInterface() ? t.getMethods() : t.getDeclaredMethods(), predicates);
    }
    
    public static Set<Constructor> getAllConstructors(final Class<?> type, final Predicate<? super Constructor>... predicates) {
        final Set<Constructor> result = (Set<Constructor>)Sets.newHashSet();
        for (final Class<?> t : getAllSuperTypes(type, (Predicate<? super Class<?>>[])new Predicate[0])) {
            result.addAll(getConstructors(t, predicates));
        }
        return result;
    }
    
    public static Set<Constructor> getConstructors(final Class<?> t, final Predicate<? super Constructor>... predicates) {
        return (Set<Constructor>)filter(t.getDeclaredConstructors(), (Predicate<? super Constructor<?>>[])predicates);
    }
    
    public static Set<Field> getAllFields(final Class<?> type, final Predicate<? super Field>... predicates) {
        final Set<Field> result = (Set<Field>)Sets.newHashSet();
        for (final Class<?> t : getAllSuperTypes(type, (Predicate<? super Class<?>>[])new Predicate[0])) {
            result.addAll(getFields(t, predicates));
        }
        return result;
    }
    
    public static Set<Field> getFields(final Class<?> type, final Predicate<? super Field>... predicates) {
        return filter(type.getDeclaredFields(), predicates);
    }
    
    public static <T extends AnnotatedElement> Set<Annotation> getAllAnnotations(final T type, final Predicate<Annotation>... predicates) {
        final Set<Annotation> result = (Set<Annotation>)Sets.newHashSet();
        if (type instanceof Class) {
            for (final Class<?> t : getAllSuperTypes((Class<?>)type, (Predicate<? super Class<?>>[])new Predicate[0])) {
                result.addAll(getAnnotations(t, predicates));
            }
        }
        else {
            result.addAll(getAnnotations((AnnotatedElement)type, predicates));
        }
        return result;
    }
    
    public static <T extends AnnotatedElement> Set<Annotation> getAnnotations(final T type, final Predicate<Annotation>... predicates) {
        return filter(type.getDeclaredAnnotations(), (Predicate<? super Annotation>[])predicates);
    }
    
    public static <T extends AnnotatedElement> Set<T> getAll(final Set<T> elements, final Predicate<? super T>... predicates) {
        return (Set<T>)(Utils.isEmpty(predicates) ? elements : Sets.newHashSet((Iterable<?>)Iterables.filter((Iterable<? extends E>)elements, Predicates.and((Predicate[])predicates))));
    }
    
    public static <T extends Member> Predicate<T> withName(final String name) {
        return new Predicate<T>() {
            final /* synthetic */ String val$name;
            
            ReflectionUtils$1() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return input != null && input.getName().equals(name);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static <T extends Member> Predicate<T> withPrefix(final String prefix) {
        return new Predicate<T>() {
            final /* synthetic */ String val$prefix;
            
            ReflectionUtils$2() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return input != null && input.getName().startsWith(prefix);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withPattern(final String regex) {
        return new Predicate<T>() {
            final /* synthetic */ String val$regex;
            
            ReflectionUtils$3() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return Pattern.matches(regex, input.toString());
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotation(final Class<? extends Annotation> annotation) {
        return new Predicate<T>() {
            final /* synthetic */ Class val$annotation;
            
            ReflectionUtils$4() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return input != null && input.isAnnotationPresent(annotation);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotations(final Class<? extends Annotation>... annotations) {
        return new Predicate<T>() {
            final /* synthetic */ Class[] val$annotations;
            
            ReflectionUtils$5() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return input != null && Arrays.equals(annotations, annotationTypes(input.getAnnotations()));
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotation(final Annotation annotation) {
        return new Predicate<T>() {
            final /* synthetic */ Annotation val$annotation;
            
            ReflectionUtils$6() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return input != null && input.isAnnotationPresent(annotation.annotationType()) && areAnnotationMembersMatching(input.getAnnotation(annotation.annotationType()), annotation);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotations(final Annotation... annotations) {
        return new Predicate<T>() {
            final /* synthetic */ Annotation[] val$annotations;
            
            ReflectionUtils$7() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                if (input != null) {
                    final Annotation[] inputAnnotations = input.getAnnotations();
                    if (inputAnnotations.length == annotations.length) {
                        for (int i = 0; i < inputAnnotations.length; ++i) {
                            if (!areAnnotationMembersMatching(inputAnnotations[i], annotations[i])) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static Predicate<Member> withParameters(final Class<?>... types) {
        return new Predicate<Member>() {
            final /* synthetic */ Class[] val$types;
            
            ReflectionUtils$8() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member input) {
                return Arrays.equals(parameterTypes(input), types);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withParametersAssignableTo(final Class... types) {
        return new Predicate<Member>() {
            final /* synthetic */ Class[] val$types;
            
            ReflectionUtils$9() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member input) {
                if (input != null) {
                    final Class<?>[] parameterTypes = (Class<?>[])parameterTypes(input);
                    if (parameterTypes.length == types.length) {
                        for (int i = 0; i < parameterTypes.length; ++i) {
                            if (!parameterTypes[i].isAssignableFrom(types[i]) || (parameterTypes[i] == Object.class && types[i] != Object.class)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withParametersCount(final int count) {
        return new Predicate<Member>() {
            final /* synthetic */ int val$count;
            
            ReflectionUtils$10() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member input) {
                return input != null && parameterTypes(input).length == count;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withAnyParameterAnnotation(final Class<? extends Annotation> annotationClass) {
        return new Predicate<Member>() {
            final /* synthetic */ Class val$annotationClass;
            
            ReflectionUtils$11() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member input) {
                return input != null && Iterables.any((Iterable<Object>)annotationTypes(parameterAnnotations(input)), (Predicate<? super Object>)new Predicate<Class<? extends Annotation>>() {
                    final /* synthetic */ ReflectionUtils$11 this$0;
                    
                    ReflectionUtils$11$1() {
                        this.this$0 = this$0;
                        super();
                    }
                    
                    @Override
                    public boolean apply(@Nullable final Class<? extends Annotation> input) {
                        return input.equals(annotationClass);
                    }
                    
                    @Override
                    public /* bridge */ boolean apply(@Nullable final Object o) {
                        return this.apply((Class<? extends Annotation>)o);
                    }
                });
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withAnyParameterAnnotation(final Annotation annotation) {
        return new Predicate<Member>() {
            final /* synthetic */ Annotation val$annotation;
            
            ReflectionUtils$12() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member input) {
                return input != null && Iterables.any((Iterable<Object>)parameterAnnotations(input), (Predicate<? super Object>)new Predicate<Annotation>() {
                    final /* synthetic */ ReflectionUtils$12 this$0;
                    
                    ReflectionUtils$12$1() {
                        this.this$0 = this$0;
                        super();
                    }
                    
                    @Override
                    public boolean apply(@Nullable final Annotation input) {
                        return areAnnotationMembersMatching(annotation, input);
                    }
                    
                    @Override
                    public /* bridge */ boolean apply(@Nullable final Object o) {
                        return this.apply((Annotation)o);
                    }
                });
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static <T> Predicate<Field> withType(final Class<T> type) {
        return new Predicate<Field>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$13() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Field input) {
                return input != null && input.getType().equals(type);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Field)o);
            }
        };
    }
    
    public static <T> Predicate<Field> withTypeAssignableTo(final Class<T> type) {
        return new Predicate<Field>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$14() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Field input) {
                return input != null && type.isAssignableFrom(input.getType());
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Field)o);
            }
        };
    }
    
    public static <T> Predicate<Method> withReturnType(final Class<T> type) {
        return new Predicate<Method>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$15() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Method input) {
                return input != null && input.getReturnType().equals(type);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Method)o);
            }
        };
    }
    
    public static <T> Predicate<Method> withReturnTypeAssignableTo(final Class<T> type) {
        return new Predicate<Method>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$16() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Method input) {
                return input != null && type.isAssignableFrom(input.getReturnType());
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Method)o);
            }
        };
    }
    
    public static <T extends Member> Predicate<T> withModifier(final int mod) {
        return new Predicate<T>() {
            final /* synthetic */ int val$mod;
            
            ReflectionUtils$17() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T input) {
                return input != null && (input.getModifiers() & mod) != 0x0;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Class<?>> withClassModifier(final int mod) {
        return new Predicate<Class<?>>() {
            final /* synthetic */ int val$mod;
            
            ReflectionUtils$18() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Class<?> input) {
                return input != null && (input.getModifiers() & mod) != 0x0;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Class<?>)o);
            }
        };
    }
    
    public static Class<?> forName(final String typeName, final ClassLoader... classLoaders) {
        if (getPrimitiveNames().contains(typeName)) {
            return getPrimitiveTypes().get(getPrimitiveNames().indexOf(typeName));
        }
        String type;
        if (typeName.contains("[")) {
            final int i = typeName.indexOf("[");
            type = typeName.substring(0, i);
            final String array = typeName.substring(i).replace("]", "");
            if (getPrimitiveNames().contains(type)) {
                type = getPrimitiveDescriptors().get(getPrimitiveNames().indexOf(type));
            }
            else {
                type = "L" + type + ";";
            }
            type = array + type;
        }
        else {
            type = typeName;
        }
        final List<ReflectionsException> reflectionsExceptions = (List<ReflectionsException>)Lists.newArrayList();
        final ClassLoader[] classLoaders2 = ClasspathHelper.classLoaders(classLoaders);
        final int length = classLoaders2.length;
        int j = 0;
        while (j < length) {
            final ClassLoader classLoader = classLoaders2[j];
            if (type.contains("[")) {
                try {
                    return Class.forName(type, false, classLoader);
                }
                catch (Throwable e) {
                    reflectionsExceptions.add(new ReflectionsException("could not get type for name " + typeName, e));
                }
            }
            try {
                return classLoader.loadClass(type);
            }
            catch (Throwable e) {
                reflectionsExceptions.add(new ReflectionsException("could not get type for name " + typeName, e));
                ++j;
                continue;
            }
            break;
        }
        if (Reflections.log != null) {
            for (final ReflectionsException reflectionsException : reflectionsExceptions) {
                Reflections.log.warn("could not get type for name " + typeName + " from any class loader", reflectionsException);
            }
        }
        return null;
    }
    
    public static <T> List<Class<? extends T>> forNames(final Iterable<String> classes, final ClassLoader... classLoaders) {
        final List<Class<? extends T>> result = new ArrayList<Class<? extends T>>();
        for (final String className : classes) {
            final Class<?> type = forName(className, classLoaders);
            if (type != null) {
                result.add((Class<? extends T>)type);
            }
        }
        return result;
    }
    
    private static Class[] parameterTypes(final Member member) {
        return (Class[])((member != null) ? ((member.getClass() == Method.class) ? ((Method)member).getParameterTypes() : ((member.getClass() == Constructor.class) ? ((Constructor)member).getParameterTypes() : null)) : null);
    }
    
    private static Set<Annotation> parameterAnnotations(final Member member) {
        final Set<Annotation> result = (Set<Annotation>)Sets.newHashSet();
        final Annotation[][] array;
        final Annotation[][] annotations = array = ((member instanceof Method) ? ((Method)member).getParameterAnnotations() : ((member instanceof Constructor) ? ((Constructor)member).getParameterAnnotations() : null));
        for (final Annotation[] annotation : array) {
            Collections.addAll(result, annotation);
        }
        return result;
    }
    
    private static Set<Class<? extends Annotation>> annotationTypes(final Iterable<Annotation> annotations) {
        final Set<Class<? extends Annotation>> result = (Set<Class<? extends Annotation>>)Sets.newHashSet();
        for (final Annotation annotation : annotations) {
            result.add(annotation.annotationType());
        }
        return result;
    }
    
    private static Class<? extends Annotation>[] annotationTypes(final Annotation[] annotations) {
        final Class<? extends Annotation>[] result = (Class<? extends Annotation>[])new Class[annotations.length];
        for (int i = 0; i < annotations.length; ++i) {
            result[i] = annotations[i].annotationType();
        }
        return result;
    }
    
    private static void initPrimitives() {
        if (ReflectionUtils.primitiveNames == null) {
            ReflectionUtils.primitiveNames = Lists.newArrayList("boolean", "char", "byte", "short", "int", "long", "float", "double", "void");
            ReflectionUtils.primitiveTypes = (List<Class>)Lists.newArrayList(Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE);
            ReflectionUtils.primitiveDescriptors = Lists.newArrayList("Z", "C", "B", "S", "I", "J", "F", "D", "V");
        }
    }
    
    private static List<String> getPrimitiveNames() {
        initPrimitives();
        return ReflectionUtils.primitiveNames;
    }
    
    private static List<Class> getPrimitiveTypes() {
        initPrimitives();
        return ReflectionUtils.primitiveTypes;
    }
    
    private static List<String> getPrimitiveDescriptors() {
        initPrimitives();
        return ReflectionUtils.primitiveDescriptors;
    }
    
    static <T> Set<T> filter(final T[] elements, final Predicate<? super T>... predicates) {
        return (Set<T>)(Utils.isEmpty(predicates) ? Sets.newHashSet((Object[])elements) : Sets.newHashSet((Iterable<?>)Iterables.filter((Iterable<? extends E>)Arrays.asList(elements), Predicates.and((Predicate[])predicates))));
    }
    
    static <T> Set<T> filter(final Iterable<T> elements, final Predicate<? super T>... predicates) {
        return (Set<T>)(Utils.isEmpty(predicates) ? Sets.newHashSet((Iterable<?>)elements) : Sets.newHashSet((Iterable<?>)Iterables.filter((Iterable<? extends E>)elements, Predicates.and((Predicate[])predicates))));
    }
    
    private static boolean areAnnotationMembersMatching(final Annotation annotation1, final Annotation annotation2) {
        if (annotation2 != null && annotation1.annotationType() == annotation2.annotationType()) {
            for (final Method method : annotation1.annotationType().getDeclaredMethods()) {
                try {
                    if (!method.invoke(annotation1, new Object[0]).equals(method.invoke(annotation2, new Object[0]))) {
                        return false;
                    }
                }
                catch (Exception e) {
                    throw new ReflectionsException(String.format("could not invoke method %s on annotation %s", method.getName(), annotation1.annotationType()), e);
                }
            }
            return true;
        }
        return false;
    }
    
    static /* synthetic */ Class[] access$000(final Annotation[] x0) {
        return annotationTypes(x0);
    }
    
    static /* synthetic */ boolean access$100(final Annotation x0, final Annotation x1) {
        return areAnnotationMembersMatching(x0, x1);
    }
    
    static /* synthetic */ Class[] access$200(final Member x0) {
        return parameterTypes(x0);
    }
    
    static /* synthetic */ Set access$300(final Member x0) {
        return parameterAnnotations(x0);
    }
    
    static /* synthetic */ Set access$400(final Iterable x0) {
        return annotationTypes(x0);
    }
    
    static {
        ReflectionUtils.includeObject = false;
    }
}
