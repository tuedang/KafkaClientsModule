package com.fabfitfun.kafka.clients.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {

    public static final String SERIALIZED_CLASS = "serializedClass";
    private Gson gson;
    private Class<T> deserializedClass;
    private Type reflectionTypeToken;

    public JsonDeserializer(Class<T> deserializedClass) {
        this.deserializedClass = deserializedClass;
        init();

    }

    public JsonDeserializer(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
        init();
    }

    private void init () {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public JsonDeserializer() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> map, boolean b) {

        if(deserializedClass == null) { // if we use class in the map
            this.deserializedClass = (Class<T>) map.get(SERIALIZED_CLASS);
            init ();
        }

    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

         Type deserializeFrom = deserializedClass != null ? deserializedClass : reflectionTypeToken;

         return gson.fromJson(new String(bytes),deserializeFrom);

    }

    @Override
    public void close() {

    }
}
