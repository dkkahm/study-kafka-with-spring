package com.example.broker.consumer;

import com.example.broker.message.DiscountMessage;
import com.example.broker.message.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "t.commodity.promotion")
public class PromotionListener {

    @KafkaHandler
    public void listenPromotion(PromotionMessage message) {
        log.info("Processing promotion : {}", message);
    }

    @KafkaHandler
    public void listenDiscount(DiscountMessage message) {
        log.info("Processing discount : {}", message);
    }

    @KafkaHandler(isDefault = true)
    public void listenDefault(Object object) {
        log.info("listenDefault: object is {}", object.toString());
    }
}
