package com.example.avro.consumer;

import com.example.avro.data.Avro01;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class Avro01Consumer {

    @KafkaListener(topics = "sc-avro01")
    public void listen(ConsumerRecord<String, Avro01> record) {
        log.info("Received {} : {}", record.key(), record.value());
    }
}
