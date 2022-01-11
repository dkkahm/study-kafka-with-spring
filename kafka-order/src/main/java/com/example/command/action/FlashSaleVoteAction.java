package com.example.command.action;

import com.example.api.request.FlashSaleVoteRequest;
import com.example.broker.message.FlashSaleVoteMessage;
import com.example.broker.producer.FlashSaleVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlashSaleVoteAction {
    @Autowired
    private FlashSaleVoteProducer producer;

    public void publishToKafka(FlashSaleVoteRequest request) {
        var message = new FlashSaleVoteMessage(request);

        producer.publish(message);
    }
}
