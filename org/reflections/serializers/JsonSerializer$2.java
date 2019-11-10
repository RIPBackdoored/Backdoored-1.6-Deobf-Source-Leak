package org.reflections.serializers;

import com.google.common.collect.*;
import java.lang.reflect.*;
import com.google.gson.*;

class JsonSerializer$2 implements JsonSerializer<Multimap> {
    final /* synthetic */ JsonSerializer this$0;
    
    JsonSerializer$2(final JsonSerializer this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public JsonElement serialize(final Multimap multimap, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(multimap.asMap());
    }
    
    @Override
    public /* bridge */ JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((Multimap)o, type, jsonSerializationContext);
    }
}