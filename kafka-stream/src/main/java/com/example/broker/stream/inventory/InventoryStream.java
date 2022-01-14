package com.example.broker.stream.inventory;

import com.example.broker.message.InventoryMessage;
import com.example.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class InventoryStream {

    @Bean
    public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var longSerde = Serdes.Long();
        var inventoryTimestampExtractor = new InventoryTimestampExtractor();

        var inventoryStream = builder.stream("t.commodity.inventory",
                Consumed.with(stringSerde, inventorySerde, inventoryTimestampExtractor, null));
        inventoryStream.print(Printed.<String, InventoryMessage>toSysOut().withLabel("Inventory"));

        var inventoryTotalStream = inventoryStream
                .mapValues((k, v) -> v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity())
                .groupByKey()
                .aggregate(() -> 0L,
                        (aggKey, newValue, aggValue) -> aggValue + newValue,
                        Materialized.with(stringSerde, longSerde)
                )
                .toStream();
        inventoryTotalStream.print(Printed.<String, Long>toSysOut().withLabel("InventoryTotal"));
        inventoryTotalStream.to("t.commodity.inventory-total");

        return inventoryStream;
    }
}
