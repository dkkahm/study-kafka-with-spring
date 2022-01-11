package com.example.broker.producer;

import com.example.broker.message.FeedbackMessage;
import com.example.broker.message.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeedbackProducer {

    @Autowired
    private KafkaTemplate<String, FeedbackMessage> kafkaTemplate;

    public void publish(FeedbackMessage message) {
        kafkaTemplate.send("t.commodity.feedback", message);
    }
}
