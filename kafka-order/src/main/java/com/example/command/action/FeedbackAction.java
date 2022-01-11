package com.example.command.action;

import com.example.api.request.FeedbackRequest;
import com.example.api.request.PromotionRequest;
import com.example.broker.message.FeedbackMessage;
import com.example.broker.message.PromotionMessage;
import com.example.broker.producer.FeedbackProducer;
import com.example.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class FeedbackAction {
    @Autowired
    private FeedbackProducer producer;

    public void publishToKafka(FeedbackRequest request) {
        var message = new FeedbackMessage(request);

        producer.publish(message);
    }
}
