package org.reflections.serializers;

import java.lang.reflect.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

class JsonSerializer$1 implements JsonDeserializer<Multimap> {
    final /* synthetic */ JsonSerializer this$0;
    
    JsonSerializer$1(final JsonSerializer this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Multimap deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final SetMultimap<String, String> map = (SetMultimap<String, String>)Multimaps.newSetMultimap((Map)new HashMap(), (Supplier)new Supplier<Set<String>>() {
            final /* synthetic */ JsonSerializer$1 this$1;
            
            JsonSerializer$1$1() {
                this.this$1 = this$1;
                super();
            }
            
            @Override
            public Set<String> get() {
                return (Set<String>)Sets.newHashSet();
            }
            
            @Override
            public /* bridge */ Object get() {
                return this.get();
            }
        });
        for (final Map.Entry<String, JsonElement> entry : ((JsonObject)jsonElement).entrySet()) {
            for (final JsonElement element : entry.getValue()) {
                map.get((Object)entry.getKey()).add(element.getAsString());
            }
        }
        return (Multimap)map;
    }
    
    @Override
    public /* bridge */ Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}