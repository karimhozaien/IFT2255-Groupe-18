package main.java.prototype.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PolymorphicTypeAdapterFactory<T> implements TypeAdapterFactory {
    private final Class<T> baseType;
    private final String typeFieldName;
    private final Map<String, Class<? extends T>> labelToSubtype = new HashMap<>();
    private final Map<Class<? extends T>, String> subtypeToLabel = new HashMap<>();

    private PolymorphicTypeAdapterFactory(Class<T> baseType, String typeFieldName) {
        if (baseType == null || typeFieldName == null) {
            throw new NullPointerException("Base type and type field name must not be null");
        }
        this.baseType = baseType;
        this.typeFieldName = typeFieldName;
    }

    public static <T> PolymorphicTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
        return new PolymorphicTypeAdapterFactory<>(baseType, typeFieldName);
    }

    public PolymorphicTypeAdapterFactory<T> registerSubtype(Class<? extends T> subtype, String label) {
        if (subtype == null || label == null) {
            throw new NullPointerException("Subtype and label must not be null");
        }
        if (labelToSubtype.containsKey(label) || subtypeToLabel.containsKey(subtype)) {
            throw new IllegalArgumentException(String.format("Subtype '%s' and label '%s' must be unique.", subtype, label));
        }
        labelToSubtype.put(label, subtype);
        subtypeToLabel.put(subtype, label);
        return this;
    }

    @Override
    public <U> TypeAdapter<U> create(Gson gson, TypeToken<U> typeToken) {
        if (!baseType.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }

        final Map<String, TypeAdapter<?>> labelToDelegate = new HashMap<>();
        final Map<Class<?>, TypeAdapter<?>> subtypeToDelegate = new HashMap<>();

        for (Map.Entry<String, Class<? extends T>> entry : labelToSubtype.entrySet()) {
            TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            labelToDelegate.put(entry.getKey(), delegate);
            subtypeToDelegate.put(entry.getValue(), delegate);
        }

        TypeAdapter<T> adapter = new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter jsonWriter, T value) throws IOException {
                Class<?> srcType = value.getClass();
                String label = subtypeToLabel.get(srcType);

                if (label == null) {
                    throw new JsonParseException("Cannot serialize unregistered subtype: " + srcType.getName());
                }

                TypeAdapter<T> delegate = (TypeAdapter<T>) subtypeToDelegate.get(srcType);
                if (delegate == null) {
                    throw new JsonParseException("Delegate adapter not found for subtype: " + srcType.getName());
                }

                JsonObject jsonObject = delegate.toJsonTree(value).getAsJsonObject();
                JsonObject clone = new JsonObject();
                clone.add(typeFieldName, gson.toJsonTree(label));

                for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
                    clone.add(e.getKey(), e.getValue());
                }

                gson.toJson(clone, jsonWriter);
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
                JsonElement labelElement = jsonObject.remove(typeFieldName);

                if (labelElement == null) {
                    throw new JsonParseException("Field '" + typeFieldName + "' not found in JSON");
                }

                String label = labelElement.getAsString();
                TypeAdapter<T> delegate = (TypeAdapter<T>) labelToDelegate.get(label);

                if (delegate == null) {
                    throw new JsonParseException("Delegate adapter not found for label: " + label);
                }

                return delegate.fromJsonTree(jsonObject);
            }
        }.nullSafe();

        return (TypeAdapter<U>) adapter;
    }
}