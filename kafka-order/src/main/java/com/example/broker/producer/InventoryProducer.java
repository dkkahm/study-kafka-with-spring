package com.example.broker.producer;

import com.example.broker.message.InventoryMessage;
import com.example.broker.message.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryProducer {

    @Autowired
    private KafkaTemplate<String, InventoryMessage> kafkaTemplate;

    public void publish(InventoryMessage message) {
        kafkaTemplate.send("t.commodity.inventory", message.getItem(), message);
    }
}
