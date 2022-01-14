package com.example.command.action;

import com.example.api.request.OnlineOrderRequest;
import com.example.api.request.OnlinePaymentRequest;
import com.example.broker.message.OnlineOrderMessage;
import com.example.broker.message.OnlinePaymentMessage;
import com.example.broker.producer.OnlineOrderProducer;
import com.example.broker.producer.OnlinePaymentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnlinePaymentAction {
    @Autowired
    private OnlinePaymentProducer producer;

    public void publish(OnlinePaymentRequest request) {
        var message = new OnlinePaymentMessage(request);
        producer.publish(message);
    }
}
