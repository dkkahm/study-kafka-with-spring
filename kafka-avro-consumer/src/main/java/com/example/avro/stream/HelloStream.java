package com.example.avro.stream;

import com.example.avro.config.KafkaStreamConfig;
import com.example.avro.data.Hello;
import com.example.avro.message.HelloPositiveUppercase;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class HelloStream {

    @Bean
    public KStream<String, HelloPositiveUppercase> kstreamHello(StreamsBuilder builder) {
        var helloSerde = new SpecificAvroSerde<Hello>();
        var helloSerdeConfig = Collections.singletonMap("schema.registry.url", KafkaStreamConfig.SCHEMA_REGISTRY_URL);
        helloSerde.configure(helloSerdeConfig, false);

        var helloPositiveUppercaseStream = builder.stream("sc-hello", Consumed.with(Serdes.String(), helloSerde))
                .mapValues(this::mapHello);

        helloPositiveUppercaseStream.print(Printed.<String, HelloPositiveUppercase>toSysOut().withLabel("Hello Positive Uppercase"));

        return helloPositiveUppercaseStream;
    }

    private HelloPositiveUppercase mapHello(Hello original) {
        var result = new HelloPositiveUppercase();
        result.setPositiveInt(Math.abs(original.getMyIntField()));
        result.setUppercaseString(original.getMyStringField().toString().toUpperCase());
        return result;
    }
}
