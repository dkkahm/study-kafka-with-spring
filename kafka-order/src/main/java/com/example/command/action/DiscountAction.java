package com.example.command.action;

import com.example.api.request.DiscountRequest;
import com.example.api.request.PromotionRequest;
import com.example.broker.message.DiscountMessage;
import com.example.broker.message.PromotionMessage;
import com.example.broker.producer.DiscountProducer;
import com.example.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountAction {
    @Autowired
    private DiscountProducer producer;

    public void publishToKafka(DiscountRequest request) {
        var message = new DiscountMessage(request);

        producer.publish(message);
    }
}
