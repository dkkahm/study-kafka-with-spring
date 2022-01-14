package com.example.broker.producer;

import com.example.broker.message.OnlineOrderMessage;
import com.example.broker.message.OnlinePaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OnlinePaymentProducer {

    @Autowired
    private KafkaTemplate<String, OnlinePaymentMessage> kafkaTemplate;

    public void publish(OnlinePaymentMessage message) {
        kafkaTemplate.send("t.commodity.online-payment", message.getOnlineOrderNumber(), message);
    }
}
