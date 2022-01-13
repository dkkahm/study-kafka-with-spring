package com.example.command.action;

import com.example.api.request.InventoryRequest;
import com.example.broker.message.InventoryMessage;
import com.example.broker.producer.InventoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryAction {
    @Autowired
    private InventoryProducer producer;

    public void publishToKafka(InventoryRequest request) {
        var message = new InventoryMessage(request);

        producer.publish(message);
    }
}
