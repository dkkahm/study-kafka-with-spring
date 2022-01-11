package com.example.broker.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class CustomJsonSerde<T> implements Serde {
    private final CustomJsonSerializer<T> serializer;
    private final CustomJsonDeserializer<T> deserializer;

    public CustomJsonSerde(CustomJsonSerializer<T> serializer, CustomJsonDeserializer<T> deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public Serializer<T> serializer() {
        return this.serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return this.deserializer;
    }
}
