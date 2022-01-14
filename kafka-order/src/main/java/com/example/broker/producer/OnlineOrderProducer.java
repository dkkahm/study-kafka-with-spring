package com.example.broker.producer;

import com.example.broker.message.InventoryMessage;
import com.example.broker.message.OnlineOrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OnlineOrderProducer {

    @Autowired
    private KafkaTemplate<String, OnlineOrderMessage> kafkaTemplate;

    public void publish(OnlineOrderMessage message) {
        kafkaTemplate.send("t.commodity.online-order", message.getOnlineOrderNumber(), message);
    }
}
