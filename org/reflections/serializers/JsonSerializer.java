package org.reflections.serializers;

import org.reflections.*;
import org.reflections.util.*;
import java.nio.charset.*;
import com.google.common.io.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

public class JsonSerializer implements Serializer
{
    private Gson gson;
    
    public JsonSerializer() {
        super();
    }
    
    @Override
    public Reflections read(final InputStream inputStream) {
        return this.getGson().fromJson(new InputStreamReader(inputStream), Reflections.class);
    }
    
    @Override
    public File save(final Reflections reflections, final String filename) {
        try {
            final File file = Utils.prepareFile(filename);
            Files.write((CharSequence)this.toString(reflections), file, Charset.defaultCharset());
            return file;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public String toString(final Reflections reflections) {
        return this.getGson().toJson(reflections);
    }
    
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = new GsonBuilder().registerTypeAdapter(Multimap.class, new com.google.gson.JsonSerializer<Multimap>() {
                final /* synthetic */ JsonSerializer this$0;
                
                JsonSerializer$2() {
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
            }).registerTypeAdapter(Multimap.class, new JsonDeserializer<Multimap>() {
                final /* synthetic */ JsonSerializer this$0;
                
                JsonSerializer$1() {
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
            }).setPrettyPrinting().create();
        }
        return this.gson;
    }
}
