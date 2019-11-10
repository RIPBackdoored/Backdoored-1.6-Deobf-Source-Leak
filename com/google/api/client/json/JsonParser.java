package com.google.api.client.json;

import java.io.*;
import java.math.*;
import java.lang.reflect.*;
import com.google.api.client.util.*;
import java.util.*;
import java.util.concurrent.locks.*;

public abstract class JsonParser
{
    private static WeakHashMap<Class<?>, Field> cachedTypemapFields;
    private static final Lock lock;
    
    public JsonParser() {
        super();
    }
    
    public abstract JsonFactory getFactory();
    
    public abstract void close() throws IOException;
    
    public abstract JsonToken nextToken() throws IOException;
    
    public abstract JsonToken getCurrentToken();
    
    public abstract String getCurrentName() throws IOException;
    
    public abstract JsonParser skipChildren() throws IOException;
    
    public abstract String getText() throws IOException;
    
    public abstract byte getByteValue() throws IOException;
    
    public abstract short getShortValue() throws IOException;
    
    public abstract int getIntValue() throws IOException;
    
    public abstract float getFloatValue() throws IOException;
    
    public abstract long getLongValue() throws IOException;
    
    public abstract double getDoubleValue() throws IOException;
    
    public abstract BigInteger getBigIntegerValue() throws IOException;
    
    public abstract BigDecimal getDecimalValue() throws IOException;
    
    public final <T> T parseAndClose(final Class<T> destinationClass) throws IOException {
        return this.parseAndClose(destinationClass, null);
    }
    
    @Beta
    public final <T> T parseAndClose(final Class<T> destinationClass, final CustomizeJsonParser customizeParser) throws IOException {
        try {
            return (T)this.parse((Class<Object>)destinationClass, customizeParser);
        }
        finally {
            this.close();
        }
    }
    
    public final void skipToKey(final String keyToFind) throws IOException {
        this.skipToKey(Collections.singleton(keyToFind));
    }
    
    public final String skipToKey(final Set<String> keysToFind) throws IOException {
        for (JsonToken curToken = this.startParsingObjectOrArray(); curToken == JsonToken.FIELD_NAME; curToken = this.nextToken()) {
            final String key = this.getText();
            this.nextToken();
            if (keysToFind.contains(key)) {
                return key;
            }
            this.skipChildren();
        }
        return null;
    }
    
    private JsonToken startParsing() throws IOException {
        JsonToken currentToken = this.getCurrentToken();
        if (currentToken == null) {
            currentToken = this.nextToken();
        }
        Preconditions.checkArgument(currentToken != null, (Object)"no JSON input found");
        return currentToken;
    }
    
    private JsonToken startParsingObjectOrArray() throws IOException {
        JsonToken currentToken = this.startParsing();
        switch (currentToken) {
            case START_OBJECT: {
                currentToken = this.nextToken();
                Preconditions.checkArgument(currentToken == JsonToken.FIELD_NAME || currentToken == JsonToken.END_OBJECT, currentToken);
                break;
            }
            case START_ARRAY: {
                currentToken = this.nextToken();
                break;
            }
        }
        return currentToken;
    }
    
    public final void parseAndClose(final Object destination) throws IOException {
        this.parseAndClose(destination, null);
    }
    
    @Beta
    public final void parseAndClose(final Object destination, final CustomizeJsonParser customizeParser) throws IOException {
        try {
            this.parse(destination, customizeParser);
        }
        finally {
            this.close();
        }
    }
    
    public final <T> T parse(final Class<T> destinationClass) throws IOException {
        return this.parse(destinationClass, null);
    }
    
    @Beta
    public final <T> T parse(final Class<T> destinationClass, final CustomizeJsonParser customizeParser) throws IOException {
        final T result = (T)this.parse(destinationClass, false, customizeParser);
        return result;
    }
    
    public Object parse(final Type dataType, final boolean close) throws IOException {
        return this.parse(dataType, close, null);
    }
    
    @Beta
    public Object parse(final Type dataType, final boolean close, final CustomizeJsonParser customizeParser) throws IOException {
        try {
            if (!Void.class.equals(dataType)) {
                this.startParsing();
            }
            return this.parseValue(null, dataType, new ArrayList<Type>(), null, customizeParser, true);
        }
        finally {
            if (close) {
                this.close();
            }
        }
    }
    
    public final void parse(final Object destination) throws IOException {
        this.parse(destination, null);
    }
    
    @Beta
    public final void parse(final Object destination, final CustomizeJsonParser customizeParser) throws IOException {
        final ArrayList<Type> context = new ArrayList<Type>();
        context.add(destination.getClass());
        this.parse(context, destination, customizeParser);
    }
    
    private void parse(final ArrayList<Type> context, final Object destination, final CustomizeJsonParser customizeParser) throws IOException {
        if (destination instanceof GenericJson) {
            ((GenericJson)destination).setFactory(this.getFactory());
        }
        JsonToken curToken = this.startParsingObjectOrArray();
        final Class<?> destinationClass = destination.getClass();
        final ClassInfo classInfo = ClassInfo.of(destinationClass);
        final boolean isGenericData = GenericData.class.isAssignableFrom(destinationClass);
        if (!isGenericData && Map.class.isAssignableFrom(destinationClass)) {
            final Map<String, Object> destinationMap = (Map<String, Object>)destination;
            this.parseMap(null, destinationMap, Types.getMapValueParameter(destinationClass), context, customizeParser);
            return;
        }
        while (curToken == JsonToken.FIELD_NAME) {
            final String key = this.getText();
            this.nextToken();
            if (customizeParser != null && customizeParser.stopAt(destination, key)) {
                return;
            }
            final FieldInfo fieldInfo = classInfo.getFieldInfo(key);
            if (fieldInfo != null) {
                if (fieldInfo.isFinal() && !fieldInfo.isPrimitive()) {
                    throw new IllegalArgumentException("final array/object fields are not supported");
                }
                final Field field = fieldInfo.getField();
                final int contextSize = context.size();
                context.add(field.getGenericType());
                final Object fieldValue = this.parseValue(field, fieldInfo.getGenericType(), context, destination, customizeParser, true);
                context.remove(contextSize);
                fieldInfo.setValue(destination, fieldValue);
            }
            else if (isGenericData) {
                final GenericData object = (GenericData)destination;
                object.set(key, this.parseValue(null, null, context, destination, customizeParser, true));
            }
            else {
                if (customizeParser != null) {
                    customizeParser.handleUnrecognizedKey(destination, key);
                }
                this.skipChildren();
            }
            curToken = this.nextToken();
        }
    }
    
    public final <T> Collection<T> parseArrayAndClose(final Class<?> destinationCollectionClass, final Class<T> destinationItemClass) throws IOException {
        return this.parseArrayAndClose(destinationCollectionClass, destinationItemClass, null);
    }
    
    @Beta
    public final <T> Collection<T> parseArrayAndClose(final Class<?> destinationCollectionClass, final Class<T> destinationItemClass, final CustomizeJsonParser customizeParser) throws IOException {
        try {
            return (Collection<T>)this.parseArray(destinationCollectionClass, (Class<Object>)destinationItemClass, customizeParser);
        }
        finally {
            this.close();
        }
    }
    
    public final <T> void parseArrayAndClose(final Collection<? super T> destinationCollection, final Class<T> destinationItemClass) throws IOException {
        this.parseArrayAndClose(destinationCollection, destinationItemClass, null);
    }
    
    @Beta
    public final <T> void parseArrayAndClose(final Collection<? super T> destinationCollection, final Class<T> destinationItemClass, final CustomizeJsonParser customizeParser) throws IOException {
        try {
            this.parseArray((Collection<? super Object>)destinationCollection, (Class<Object>)destinationItemClass, customizeParser);
        }
        finally {
            this.close();
        }
    }
    
    public final <T> Collection<T> parseArray(final Class<?> destinationCollectionClass, final Class<T> destinationItemClass) throws IOException {
        return this.parseArray(destinationCollectionClass, destinationItemClass, null);
    }
    
    @Beta
    public final <T> Collection<T> parseArray(final Class<?> destinationCollectionClass, final Class<T> destinationItemClass, final CustomizeJsonParser customizeParser) throws IOException {
        final Collection<T> destinationCollection = (Collection<T>)Data.newCollectionInstance(destinationCollectionClass);
        this.parseArray(destinationCollection, destinationItemClass, customizeParser);
        return destinationCollection;
    }
    
    public final <T> void parseArray(final Collection<? super T> destinationCollection, final Class<T> destinationItemClass) throws IOException {
        this.parseArray(destinationCollection, destinationItemClass, null);
    }
    
    @Beta
    public final <T> void parseArray(final Collection<? super T> destinationCollection, final Class<T> destinationItemClass, final CustomizeJsonParser customizeParser) throws IOException {
        this.parseArray(null, destinationCollection, destinationItemClass, new ArrayList<Type>(), customizeParser);
    }
    
    private <T> void parseArray(final Field fieldContext, final Collection<T> destinationCollection, final Type destinationItemType, final ArrayList<Type> context, final CustomizeJsonParser customizeParser) throws IOException {
        for (JsonToken curToken = this.startParsingObjectOrArray(); curToken != JsonToken.END_ARRAY; curToken = this.nextToken()) {
            final T parsedValue = (T)this.parseValue(fieldContext, destinationItemType, context, destinationCollection, customizeParser, true);
            destinationCollection.add(parsedValue);
        }
    }
    
    private void parseMap(final Field fieldContext, final Map<String, Object> destinationMap, final Type valueType, final ArrayList<Type> context, final CustomizeJsonParser customizeParser) throws IOException {
        for (JsonToken curToken = this.startParsingObjectOrArray(); curToken == JsonToken.FIELD_NAME; curToken = this.nextToken()) {
            final String key = this.getText();
            this.nextToken();
            if (customizeParser != null && customizeParser.stopAt(destinationMap, key)) {
                return;
            }
            final Object value = this.parseValue(fieldContext, valueType, context, destinationMap, customizeParser, true);
            destinationMap.put(key, value);
        }
    }
    
    private final Object parseValue(final Field fieldContext, Type valueType, final ArrayList<Type> context, final Object destination, final CustomizeJsonParser customizeParser, final boolean handlePolymorphic) throws IOException {
        valueType = Data.resolveWildcardTypeOrTypeVariable(context, valueType);
        Class<?> valueClass = (Class<?>)((valueType instanceof Class) ? ((Class)valueType) : null);
        if (valueType instanceof ParameterizedType) {
            valueClass = Types.getRawClass((ParameterizedType)valueType);
        }
        if (valueClass == Void.class) {
            this.skipChildren();
            return null;
        }
        final JsonToken token = this.getCurrentToken();
        try {
            switch (this.getCurrentToken()) {
                case START_ARRAY:
                case END_ARRAY: {
                    final boolean isArray = Types.isArray(valueType);
                    Preconditions.checkArgument(valueType == null || isArray || (valueClass != null && Types.isAssignableToOrFrom(valueClass, Collection.class)), "expected collection or array type but got %s", valueType);
                    Collection<Object> collectionValue = null;
                    if (customizeParser != null && fieldContext != null) {
                        collectionValue = customizeParser.newInstanceForArray(destination, fieldContext);
                    }
                    if (collectionValue == null) {
                        collectionValue = Data.newCollectionInstance(valueType);
                    }
                    Type subType = null;
                    if (isArray) {
                        subType = Types.getArrayComponentType(valueType);
                    }
                    else if (valueClass != null && Iterable.class.isAssignableFrom(valueClass)) {
                        subType = Types.getIterableParameter(valueType);
                    }
                    subType = Data.resolveWildcardTypeOrTypeVariable(context, subType);
                    this.parseArray(fieldContext, collectionValue, subType, context, customizeParser);
                    if (isArray) {
                        return Types.toArray(collectionValue, Types.getRawArrayComponentType(context, subType));
                    }
                    return collectionValue;
                }
                case START_OBJECT:
                case FIELD_NAME:
                case END_OBJECT: {
                    Preconditions.checkArgument(!Types.isArray(valueType), "expected object or map type but got %s", valueType);
                    final Field typemapField = handlePolymorphic ? getCachedTypemapFieldFor(valueClass) : null;
                    Object newInstance = null;
                    if (valueClass != null && customizeParser != null) {
                        newInstance = customizeParser.newInstanceForObject(destination, valueClass);
                    }
                    final boolean isMap = valueClass != null && Types.isAssignableToOrFrom(valueClass, Map.class);
                    if (typemapField != null) {
                        newInstance = new GenericJson();
                    }
                    else if (newInstance == null) {
                        if (isMap || valueClass == null) {
                            newInstance = Data.newMapInstance(valueClass);
                        }
                        else {
                            newInstance = Types.newInstance(valueClass);
                        }
                    }
                    final int contextSize = context.size();
                    if (valueType != null) {
                        context.add(valueType);
                    }
                    if (isMap && !GenericData.class.isAssignableFrom(valueClass)) {
                        final Type subValueType = Map.class.isAssignableFrom(valueClass) ? Types.getMapValueParameter(valueType) : null;
                        if (subValueType != null) {
                            final Map<String, Object> destinationMap = (Map<String, Object>)newInstance;
                            this.parseMap(fieldContext, destinationMap, subValueType, context, customizeParser);
                            return newInstance;
                        }
                    }
                    this.parse(context, newInstance, customizeParser);
                    if (valueType != null) {
                        context.remove(contextSize);
                    }
                    if (typemapField == null) {
                        return newInstance;
                    }
                    final Object typeValueObject = ((GenericJson)newInstance).get(typemapField.getName());
                    Preconditions.checkArgument(typeValueObject != null, (Object)"No value specified for @JsonPolymorphicTypeMap field");
                    final String typeValue = typeValueObject.toString();
                    final JsonPolymorphicTypeMap typeMap = typemapField.getAnnotation(JsonPolymorphicTypeMap.class);
                    Class<?> typeClass = null;
                    for (final JsonPolymorphicTypeMap.TypeDef typeDefinition : typeMap.typeDefinitions()) {
                        if (typeDefinition.key().equals(typeValue)) {
                            typeClass = typeDefinition.ref();
                            break;
                        }
                    }
                    Preconditions.checkArgument(typeClass != null, (Object)("No TypeDef annotation found with key: " + typeValue));
                    final JsonFactory factory = this.getFactory();
                    final JsonParser parser = factory.createJsonParser(factory.toString(newInstance));
                    parser.startParsing();
                    return parser.parseValue(fieldContext, typeClass, context, null, null, false);
                }
                case VALUE_TRUE:
                case VALUE_FALSE: {
                    Preconditions.checkArgument(valueType == null || valueClass == Boolean.TYPE || (valueClass != null && valueClass.isAssignableFrom(Boolean.class)), "expected type Boolean or boolean but got %s", valueType);
                    return (token == JsonToken.VALUE_TRUE) ? Boolean.TRUE : Boolean.FALSE;
                }
                case VALUE_NUMBER_FLOAT:
                case VALUE_NUMBER_INT: {
                    Preconditions.checkArgument(fieldContext == null || fieldContext.getAnnotation(JsonString.class) == null, (Object)"number type formatted as a JSON number cannot use @JsonString annotation");
                    if (valueClass == null || valueClass.isAssignableFrom(BigDecimal.class)) {
                        return this.getDecimalValue();
                    }
                    if (valueClass == BigInteger.class) {
                        return this.getBigIntegerValue();
                    }
                    if (valueClass == Double.class || valueClass == Double.TYPE) {
                        return this.getDoubleValue();
                    }
                    if (valueClass == Long.class || valueClass == Long.TYPE) {
                        return this.getLongValue();
                    }
                    if (valueClass == Float.class || valueClass == Float.TYPE) {
                        return this.getFloatValue();
                    }
                    if (valueClass == Integer.class || valueClass == Integer.TYPE) {
                        return this.getIntValue();
                    }
                    if (valueClass == Short.class || valueClass == Short.TYPE) {
                        return this.getShortValue();
                    }
                    if (valueClass == Byte.class || valueClass == Byte.TYPE) {
                        return this.getByteValue();
                    }
                    throw new IllegalArgumentException("expected numeric type but got " + valueType);
                }
                case VALUE_STRING: {
                    final String text = this.getText().trim().toLowerCase(Locale.US);
                    if ((valueClass != Float.TYPE && valueClass != Float.class && valueClass != Double.TYPE && valueClass != Double.class) || (!text.equals("nan") && !text.equals("infinity") && !text.equals("-infinity"))) {
                        Preconditions.checkArgument(valueClass == null || !Number.class.isAssignableFrom(valueClass) || (fieldContext != null && fieldContext.getAnnotation(JsonString.class) != null), (Object)"number field formatted as a JSON string must use the @JsonString annotation");
                    }
                    return Data.parsePrimitiveValue(valueType, this.getText());
                }
                case VALUE_NULL: {
                    Preconditions.checkArgument(valueClass == null || !valueClass.isPrimitive(), (Object)"primitive number field but found a JSON null");
                    if (valueClass != null && 0x0 != (valueClass.getModifiers() & 0x600)) {
                        if (Types.isAssignableToOrFrom(valueClass, Collection.class)) {
                            return Data.nullOf(Data.newCollectionInstance(valueType).getClass());
                        }
                        if (Types.isAssignableToOrFrom(valueClass, Map.class)) {
                            return Data.nullOf(Data.newMapInstance(valueClass).getClass());
                        }
                    }
                    return Data.nullOf(Types.getRawArrayComponentType(context, valueType));
                }
                default: {
                    throw new IllegalArgumentException("unexpected JSON node type: " + token);
                }
            }
        }
        catch (IllegalArgumentException e) {
            final StringBuilder contextStringBuilder = new StringBuilder();
            final String currentName = this.getCurrentName();
            if (currentName != null) {
                contextStringBuilder.append("key ").append(currentName);
            }
            if (fieldContext != null) {
                if (currentName != null) {
                    contextStringBuilder.append(", ");
                }
                contextStringBuilder.append("field ").append(fieldContext);
            }
            throw new IllegalArgumentException(contextStringBuilder.toString(), e);
        }
    }
    
    private static Field getCachedTypemapFieldFor(final Class<?> key) {
        if (key == null) {
            return null;
        }
        JsonParser.lock.lock();
        try {
            if (JsonParser.cachedTypemapFields.containsKey(key)) {
                return JsonParser.cachedTypemapFields.get(key);
            }
            Field value = null;
            final Collection<FieldInfo> fieldInfos = ClassInfo.of(key).getFieldInfos();
            for (final FieldInfo fieldInfo : fieldInfos) {
                final Field field = fieldInfo.getField();
                final JsonPolymorphicTypeMap typemapAnnotation = field.getAnnotation(JsonPolymorphicTypeMap.class);
                if (typemapAnnotation != null) {
                    Preconditions.checkArgument(value == null, "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s", key);
                    Preconditions.checkArgument(Data.isPrimitive(field.getType()), "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s", key, field.getType());
                    value = field;
                    final JsonPolymorphicTypeMap.TypeDef[] typeDefs = typemapAnnotation.typeDefinitions();
                    final HashSet<String> typeDefKeys = Sets.newHashSet();
                    Preconditions.checkArgument(typeDefs.length > 0, (Object)"@JsonPolymorphicTypeMap must have at least one @TypeDef");
                    for (final JsonPolymorphicTypeMap.TypeDef typeDef : typeDefs) {
                        Preconditions.checkArgument(typeDefKeys.add(typeDef.key()), "Class contains two @TypeDef annotations with identical key: %s", typeDef.key());
                    }
                }
            }
            JsonParser.cachedTypemapFields.put(key, value);
            return value;
        }
        finally {
            JsonParser.lock.unlock();
        }
    }
    
    static {
        JsonParser.cachedTypemapFields = new WeakHashMap<Class<?>, Field>();
        lock = new ReentrantLock();
    }
}
