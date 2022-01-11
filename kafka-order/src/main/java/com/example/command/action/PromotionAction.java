package com.example.command.action;

import com.example.api.request.PromotionRequest;
import com.example.broker.message.PromotionMessage;
import com.example.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionAction {
    @Autowired
    private PromotionProducer producer;

    public void publishToKafka(PromotionRequest request) {
        var message = new PromotionMessage(request);

        producer.publish(message);
    }
}
