package com.example.broker.stream.inventory;

import com.example.broker.message.InventoryMessage;
import com.example.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
public class InventoryStream {

    @Bean
    public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var longSerde = Serdes.Long();

        var windowLength = Duration.ofMinutes(1);
        var hopLength = Duration.ofSeconds(20);
        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

        var inventoryTimestampExtractor = new InventoryTimestampExtractor();

        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));
        inventoryStream.print(Printed.<String, InventoryMessage>toSysOut().withLabel("Inventory"));

        var inventoryTotalStream = inventoryStream
                .mapValues((k, v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity())
                .groupByKey()
                .windowedBy(TimeWindows.of(windowLength).advanceBy(hopLength))
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream();

        inventoryTotalStream.through("t.commodity.inventory-total", Produced.with(windowSerde, longSerde))
                .print(Printed.toSysOut());

        return inventoryStream;
    }
}
