package com.example.broker.producer;

import com.example.broker.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
        int surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;

        List<Header> headers = new ArrayList<>();
        var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());
        headers.add(surpriseBonusHeader);

        return new ProducerRecord<>("t.commodity.order", null, message.getOrderNumber(), message, headers);
    }

    public void publish(OrderMessage message) {
        var producerRecord = buildProducerRecord(message);

        kafkaTemplate.send(producerRecord)
                .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Order {}, item {} failed to published, because {}", message.getOrderNumber(), message.getItemName(), ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, OrderMessage> result) {
                        log.info("Order {}, item {} published successfully", message.getOrderNumber(), message.getItemName());
                    }
                });
        log.info("Send order {}", message);
    }
}
