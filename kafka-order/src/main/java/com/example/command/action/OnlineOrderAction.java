package com.example.command.action;

import com.example.api.request.InventoryRequest;
import com.example.api.request.OnlineOrderRequest;
import com.example.broker.message.InventoryMessage;
import com.example.broker.message.OnlineOrderMessage;
import com.example.broker.producer.InventoryProducer;
import com.example.broker.producer.OnlineOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnlineOrderAction {
    @Autowired
    private OnlineOrderProducer producer;

    public void publish(OnlineOrderRequest request) {
        var message = new OnlineOrderMessage(request);
        producer.publish(message);
    }
}
